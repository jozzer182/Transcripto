import 'dart:convert';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter/services.dart';
import 'package:share_plus/share_plus.dart';

import 'package:domain/domain.dart';
import 'package:core_common/core_common.dart';
import 'package:data_crypto/data_crypto.dart';
import 'app_providers.dart';

/// State for the Transcripto home screen
class TranscriptoState {
  const TranscriptoState({
    this.inputText = '',
    this.outputText = '',
    this.selectedMethod = CipherMethod.caesar,
    this.selectedMode = CipherMode.encrypt,
    this.key = '',
    this.shift = 0,
    this.useSalt = false,
    this.salt,
    this.isManualSalt = false,
    this.manualSaltInput = '',
    this.isProcessing = false,
    this.lastError,
    this.lastSuccess,
  });

  final String inputText;
  final String outputText;
  final CipherMethod selectedMethod;
  final CipherMode selectedMode;
  final String key;
  final int shift;
  final bool useSalt;
  final List<int>? salt;
  final bool isManualSalt;
  final String manualSaltInput;
  final bool isProcessing;
  final String? lastError;
  final String? lastSuccess;

  TranscriptoState copyWith({
    String? inputText,
    String? outputText,
    CipherMethod? selectedMethod,
    CipherMode? selectedMode,
    String? key,
    int? shift,
    bool? useSalt,
    List<int>? salt,
    bool? isManualSalt,
    String? manualSaltInput,
    bool? isProcessing,
    String? lastError,
    String? lastSuccess,
  }) {
    return TranscriptoState(
      inputText: inputText ?? this.inputText,
      outputText: outputText ?? this.outputText,
      selectedMethod: selectedMethod ?? this.selectedMethod,
      selectedMode: selectedMode ?? this.selectedMode,
      key: key ?? this.key,
      shift: shift ?? this.shift,
      useSalt: useSalt ?? this.useSalt,
      salt: salt ?? this.salt,
      isManualSalt: isManualSalt ?? this.isManualSalt,
      manualSaltInput: manualSaltInput ?? this.manualSaltInput,
      isProcessing: isProcessing ?? this.isProcessing,
      lastError: lastError,
      lastSuccess: lastSuccess,
    );
  }
}

/// Notifier for managing Transcripto state
class TranscriptoNotifier extends StateNotifier<TranscriptoState> {
  TranscriptoNotifier(this._ref) : super(const TranscriptoState());
  
  final Ref _ref;

  // Input methods
  void updateInputText(String text) {
    state = state.copyWith(inputText: text, lastError: null, lastSuccess: null);
    _detectAndParseEnvelope(text);
  }

  void updateMethod(CipherMethod method) {
    state = state.copyWith(
      selectedMethod: method,
      key: '', // Reset key when changing method
      shift: 0, // Reset shift when changing method
      lastError: null,
      lastSuccess: null,
    );
  }

  void updateMode(CipherMode mode) {
    state = state.copyWith(selectedMode: mode, lastError: null, lastSuccess: null);
  }

  void updateKey(String key) {
    state = state.copyWith(key: key, lastError: null, lastSuccess: null);
  }

  void updateShift(int shift) {
    state = state.copyWith(shift: shift, lastError: null, lastSuccess: null);
  }

  void toggleUseSalt() {
    state = state.copyWith(
      useSalt: !state.useSalt,
      lastError: null,
      lastSuccess: null,
    );
    
    // Generate salt if enabling and not manual
    if (state.useSalt && !state.isManualSalt && state.salt == null) {
      generateSalt();
    }
  }

  void toggleManualSalt() {
    state = state.copyWith(
      isManualSalt: !state.isManualSalt,
      lastError: null,
      lastSuccess: null,
    );
    
    // Generate salt if switching to automatic
    if (!state.isManualSalt) {
      generateSalt();
    }
  }

  void updateManualSaltInput(String saltInput) {
    state = state.copyWith(manualSaltInput: saltInput, lastError: null, lastSuccess: null);
    
    // Try to parse the salt
    if (saltInput.trim().isNotEmpty) {
      try {
        final saltProvider = const SaltProvider();
        final salt = saltProvider.parseSalt(saltInput);
        state = state.copyWith(salt: salt);
      } catch (e) {
        // Invalid salt format, keep the input but clear the parsed salt
        state = state.copyWith(salt: null);
      }
    } else {
      state = state.copyWith(salt: null);
    }
  }

  // Actions
  void generateSalt() {
    final generateSaltUseCase = _ref.read(generateSaltProvider);
    final result = generateSaltUseCase();
    
    result.fold(
      (salt) {
        state = state.copyWith(
          salt: salt,
          manualSaltInput: base64Encode(salt),
          lastError: null,
        );
      },
      (error) {
        state = state.copyWith(
          lastError: 'Error al generar salt: $error',
          lastSuccess: null,
        );
      },
    );
  }

  Future<void> processText() async {
    if (state.inputText.trim().isEmpty) {
      state = state.copyWith(
        lastError: 'El texto de entrada no puede estar vacío',
        lastSuccess: null,
      );
      return;
    }

    state = state.copyWith(isProcessing: true, lastError: null, lastSuccess: null);

    try {
      final params = CipherParams(
        method: state.selectedMethod,
        mode: state.selectedMode,
        key: state.key.trim().isEmpty ? null : state.key.trim(),
        shift: state.shift,
        salt: state.useSalt ? state.salt : null,
        useSalt: state.useSalt,
      );

      final Result<String> result;
      
      if (state.selectedMode == CipherMode.encrypt) {
        final encryptUseCase = _ref.read(encryptTextProvider);
        result = encryptUseCase(state.inputText, params);
      } else {
        final decryptUseCase = _ref.read(decryptTextProvider);
        result = decryptUseCase(state.inputText, params);
      }

      result.fold(
        (output) {
          // Extract only the payload from envelope format
          final displayText = _extractPayloadFromOutput(output);
          state = state.copyWith(
            outputText: displayText,
            isProcessing: false,
            lastSuccess: state.selectedMode == CipherMode.encrypt ? 'Texto cifrado exitosamente' : 'Texto descifrado exitosamente',
            lastError: null,
          );
        },
        (error) {
          state = state.copyWith(
            outputText: '',
            isProcessing: false,
            lastError: _formatError(error),
            lastSuccess: null,
          );
        },
      );
    } catch (e) {
      state = state.copyWith(
        outputText: '',
        isProcessing: false,
        lastError: 'Error inesperado: $e',
        lastSuccess: null,
      );
    }
  }

  Future<void> copyToClipboard() async {
    if (state.outputText.isEmpty) {
      state = state.copyWith(
        lastError: 'No hay texto para copiar',
        lastSuccess: null,
      );
      return;
    }

    try {
      await Clipboard.setData(ClipboardData(text: state.outputText));
      state = state.copyWith(
        lastSuccess: 'Copiado al portapapeles',
        lastError: null,
      );
    } catch (e) {
      state = state.copyWith(
        lastError: 'Error al copiar: $e',
        lastSuccess: null,
      );
    }
  }

  Future<void> shareText() async {
    if (state.outputText.isEmpty) {
      state = state.copyWith(
        lastError: 'No hay texto para compartir',
        lastSuccess: null,
      );
      return;
    }

    try {
      await Share.share(
        state.outputText,
        subject: 'Resultado de Transcripto',
      );
      state = state.copyWith(
        lastSuccess: 'Texto compartido',
        lastError: null,
      );
    } catch (e) {
      state = state.copyWith(
        lastError: 'Error al compartir: $e',
        lastSuccess: null,
      );
    }
  }

  void clearAll() {
    state = const TranscriptoState();
  }

  void _detectAndParseEnvelope(String input) {
    if (input.trim().isEmpty) return;

    final detectUseCase = _ref.read(detectAndParseEnvelopeProvider);
    final result = detectUseCase(input);

    result.fold(
      (parseResult) {
        if (parseResult.isEnvelope && parseResult.suggestedParams != null) {
          final params = parseResult.suggestedParams!;
          state = state.copyWith(
            selectedMethod: params.method,
            selectedMode: CipherMode.decrypt,
            useSalt: params.useSalt,
            salt: params.salt,
            manualSaltInput: params.salt != null ? base64Encode(params.salt!) : '',
            lastSuccess: 'Envelope detectado automáticamente',
          );
        }
      },
      (error) {
        // Silently ignore envelope parsing errors
      },
    );
  }

  /// Extracts only the payload from envelope format or returns the text as-is
  String _extractPayloadFromOutput(String output) {
    // Try to parse as envelope
    final envelope = Envelope.parse(output);
    if (envelope != null) {
      return envelope.payload;
    }
    // If not an envelope, return the output as-is
    return output;
  }

  String _formatError(Object error) {
    if (error is ValidationException) {
      return error.message;
    } else if (error is CipherException) {
      return error.message;
    } else if (error is InvalidKeyException) {
      return error.message;
    } else if (error is EnvelopeParseException) {
      return error.message;
    } else {
      return 'Error: $error';
    }
  }
}

/// Provider for the Transcripto notifier
final transcriptoProvider = StateNotifierProvider<TranscriptoNotifier, TranscriptoState>((ref) {
  return TranscriptoNotifier(ref);
});
