import 'dart:convert';

import 'package:core_ui/core_ui.dart';
import 'package:data_crypto/data_crypto.dart';
import 'package:domain/domain.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:share_plus/share_plus.dart';

void main() {
  runApp(const ProviderScope(child: TranscriptoApp()));
}

// Providers wiring
final saltProvider = Provider<RandomSaltProvider>((ref) => RandomSaltProvider());

final cipherMapProvider = Provider<Map<CipherKind, CipherMethod>>((ref) {
  return {
    CipherKind.base64: Base64CodecImpl(),
    CipherKind.caesar: CaesarCipher(),
    CipherKind.vigenere: VigenereCipher(),
    CipherKind.xor: XorCipher(),
  };
});

final encryptUseCaseProvider = Provider<EncryptText>((ref) => EncryptText(ref.read(cipherMapProvider)));
final decryptUseCaseProvider = Provider<DecryptText>((ref) => DecryptText(ref.read(cipherMapProvider)));

class TranscriptoApp extends StatelessWidget {
  const TranscriptoApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      localizationsDelegates: const [
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
        GlobalCupertinoLocalizations.delegate,
      ],
      supportedLocales: const [Locale('es')],
      theme: buildTheme(Brightness.light),
      darkTheme: buildTheme(Brightness.dark),
      themeMode: ThemeMode.system,
      home: const HomeScreen(),
    );
  }
}

enum UiMode { encrypt, decrypt }

class TranscriptoState {
  final String input;
  final String output;
  final CipherKind method;
  final UiMode mode;
  final String key;
  final int shift;
  final bool useSalt;
  final bool saltManual;
  final String manualSalt;
  final String? error;
  const TranscriptoState({
    this.input = '',
    this.output = '',
    this.method = CipherKind.base64,
    this.mode = UiMode.encrypt,
    this.key = '',
    this.shift = 3,
    this.useSalt = false,
    this.saltManual = false,
    this.manualSalt = '',
    this.error,
  });

  TranscriptoState copyWith({
    String? input,
    String? output,
    CipherKind? method,
    UiMode? mode,
    String? key,
    int? shift,
    bool? useSalt,
    bool? saltManual,
    String? manualSalt,
    String? error,
  }) => TranscriptoState(
        input: input ?? this.input,
        output: output ?? this.output,
        method: method ?? this.method,
        mode: mode ?? this.mode,
        key: key ?? this.key,
        shift: shift ?? this.shift,
        useSalt: useSalt ?? this.useSalt,
        saltManual: saltManual ?? this.saltManual,
        manualSalt: manualSalt ?? this.manualSalt,
        error: error,
      );
}

class TranscriptoViewModel extends StateNotifier<TranscriptoState> {
  final EncryptText encrypt;
  final DecryptText decrypt;
  final RandomSaltProvider saltGen;
  TranscriptoViewModel({required this.encrypt, required this.decrypt, required this.saltGen}) : super(const TranscriptoState());

  void setMode(UiMode mode) => state = state.copyWith(mode: mode);
  void setMethod(CipherKind method) => state = state.copyWith(method: method);
  void setInput(String input) => state = state.copyWith(input: input);
  void setShift(int shift) => state = state.copyWith(shift: shift);
  void setKey(String key) => state = state.copyWith(key: key);
  void setUseSalt(bool v) => state = state.copyWith(useSalt: v);
  void setSaltManual(bool v) => state = state.copyWith(saltManual: v);
  void setManualSalt(String v) => state = state.copyWith(manualSalt: v);

  void onProcess() async {
    HapticFeedback.selectionClick();
    state = state.copyWith(error: null, output: '');
    try {
      List<int>? salt;
      if (state.useSalt) {
        if (state.saltManual) {
          salt = utf8.encode(state.manualSalt);
        } else {
          salt = await saltGen.generate();
        }
      }
      final params = CipherParams(
        method: state.method,
        mode: state.mode == UiMode.encrypt ? CipherMode.encrypt : CipherMode.decrypt,
        key: state.key.isEmpty ? null : state.key,
        shift: state.shift,
        salt: salt,
        useSalt: state.useSalt,
      );
      if (state.mode == UiMode.encrypt) {
        final res = encrypt(state.input, params);
  final out = res.fold<String>((ok) => ok, (err, __) {
          state = state.copyWith(error: '$err');
          return '';
        });
        state = state.copyWith(output: out);
      } else {
        final res = decrypt(state.input, params);
  final out = res.fold<String>((ok) => ok, (err, __) {
          state = state.copyWith(error: '$err');
          return '';
        });
        state = state.copyWith(output: out);
      }
    } catch (e) {
      state = state.copyWith(error: '$e');
    }
  }

  void onCopy() {
    if (state.output.isEmpty) return;
    Clipboard.setData(ClipboardData(text: state.output));
    HapticFeedback.selectionClick();
  }

  Future<void> onShare() async {
    if (state.output.isEmpty) return;
    await Share.share(state.output);
  }

  void onClear() {
    state = state.copyWith(input: '', output: '', error: null);
  }
}

final vmProvider = StateNotifierProvider<TranscriptoViewModel, TranscriptoState>((ref) {
  return TranscriptoViewModel(
    encrypt: ref.read(encryptUseCaseProvider),
    decrypt: ref.read(decryptUseCaseProvider),
    saltGen: ref.read(saltProvider),
  );
});

class HomeScreen extends ConsumerWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final s = ref.watch(vmProvider);
    final vm = ref.read(vmProvider.notifier);
    return Scaffold(
      appBar: AppBar(title: const Text('Transcripto')),
      body: SafeArea(
        child: ListView(
          padding: const EdgeInsets.all(16),
          children: [
            Card(
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    SegmentedButton<UiMode>(
                      segments: const [
                        ButtonSegment(value: UiMode.encrypt, label: Text('Cifrar'), icon: Icon(Icons.lock_outline)),
                        ButtonSegment(value: UiMode.decrypt, label: Text('Descifrar'), icon: Icon(Icons.lock_open)),
                      ],
                      selected: {s.mode},
                      onSelectionChanged: (v) => vm.setMode(v.first),
                    ),
                    const SizedBox(height: 12),
                    Wrap(
                      spacing: 8,
                      children: [
                        ChoiceChip(
                          label: const Text('Base64'),
                          selected: s.method == CipherKind.base64,
                          onSelected: (_) => vm.setMethod(CipherKind.base64),
                        ),
                        ChoiceChip(
                          label: const Text('César'),
                          selected: s.method == CipherKind.caesar,
                          onSelected: (_) => vm.setMethod(CipherKind.caesar),
                        ),
                        ChoiceChip(
                          label: const Text('Vigenère'),
                          selected: s.method == CipherKind.vigenere,
                          onSelected: (_) => vm.setMethod(CipherKind.vigenere),
                        ),
                        ChoiceChip(
                          label: const Text('XOR'),
                          selected: s.method == CipherKind.xor,
                          onSelected: (_) => vm.setMethod(CipherKind.xor),
                        ),
                      ],
                    ),
                    const SizedBox(height: 12),
                    TextField(
                      minLines: 3,
                      maxLines: 6,
                      decoration: const InputDecoration(labelText: 'Texto'),
                      onChanged: vm.setInput,
                    ),
                    const SizedBox(height: 12),
                    if (s.method == CipherKind.caesar)
                      TextField(
                        keyboardType: TextInputType.number,
                        decoration: const InputDecoration(labelText: 'Desplazamiento (entero +/-)'),
                        onChanged: (v) {
                          final n = int.tryParse(v) ?? s.shift;
                          vm.setShift(n);
                        },
                      ),
                    if (s.method == CipherKind.vigenere || s.method == CipherKind.xor)
                      TextField(
                        decoration: const InputDecoration(labelText: 'Clave'),
                        onChanged: vm.setKey,
                      ),
                    const SizedBox(height: 8),
                    SwitchListTile(
                      title: const Text('Usar salt'),
                      value: s.useSalt,
                      onChanged: vm.setUseSalt,
                    ),
                    if (s.useSalt)
                      Column(
                        children: [
                          SwitchListTile(
                            title: const Text('Ingresar salt manualmente'),
                            value: s.saltManual,
                            onChanged: vm.setSaltManual,
                          ),
                          if (s.saltManual)
                            TextField(
                              decoration: const InputDecoration(labelText: 'Salt'),
                              onChanged: vm.setManualSalt,
                            ),
                        ],
                      ),
                    const SizedBox(height: 16),
                    Row(
                      children: [
                        Expanded(
                          child: FilledButton(
                            onPressed: vm.onProcess,
                            child: Text(s.mode == UiMode.encrypt ? 'Procesar (Cifrar)' : 'Procesar (Descifrar)'),
                          ),
                        ),
                        const SizedBox(width: 8),
                        IconButton(onPressed: vm.onCopy, icon: const Icon(Icons.copy)),
                        IconButton(onPressed: vm.onShare, icon: const Icon(Icons.share)),
                        IconButton(onPressed: vm.onClear, icon: const Icon(Icons.clear)),
                      ],
                    ),
                    const SizedBox(height: 12),
                    if (s.error != null)
                      Text('Error: ${s.error!}', style: TextStyle(color: Theme.of(context).colorScheme.error)),
                    if (s.output.isNotEmpty)
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          const Text('Resultado'),
                          const SizedBox(height: 8),
                          SelectableText(s.output),
                        ],
                      ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
