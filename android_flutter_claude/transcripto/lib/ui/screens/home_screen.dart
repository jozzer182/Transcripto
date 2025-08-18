import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../providers/transcripto_provider.dart';
import '../widgets/method_selector.dart';
import '../widgets/mode_selector.dart';
import '../widgets/cipher_input_section.dart';
import '../widgets/action_buttons.dart';
import '../widgets/result_section.dart';
import '../widgets/status_message.dart';

class HomeScreen extends ConsumerWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final state = ref.watch(transcriptoProvider);
    final notifier = ref.read(transcriptoProvider.notifier);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Transcripto'),
        centerTitle: true,
        actions: [
          IconButton(
            icon: const Icon(Icons.clear_all),
            onPressed: () {
              HapticFeedback.lightImpact();
              notifier.clearAll();
            },
            tooltip: 'Limpiar todo',
          ),
        ],
      ),
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              // Mode Selector
              ModeSelector(
                selectedMode: state.selectedMode,
                onModeChanged: (mode) {
                  HapticFeedback.selectionClick();
                  notifier.updateMode(mode);
                },
              ),
              
              const SizedBox(height: 16),
              
              // Method Selector
              MethodSelector(
                selectedMethod: state.selectedMethod,
                onMethodChanged: (method) {
                  HapticFeedback.selectionClick();
                  notifier.updateMethod(method);
                },
              ),
              
              const SizedBox(height: 24),
              
              // Input Section
              Card(
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        'Texto de entrada',
                        style: Theme.of(context).textTheme.titleMedium,
                      ),
                      const SizedBox(height: 8),
                      TextField(
                        maxLines: 4,
                        decoration: const InputDecoration(
                          hintText: 'Introduce el texto a cifrar o descifrar',
                          border: OutlineInputBorder(),
                        ),
                        onChanged: notifier.updateInputText,
                      ),
                    ],
                  ),
                ),
              ),
              
              const SizedBox(height: 16),
              
              // Cipher Configuration
              CipherInputSection(
                method: state.selectedMethod,
                cipherKey: state.key,
                shift: state.shift,
                useSalt: state.useSalt,
                isManualSalt: state.isManualSalt,
                manualSaltInput: state.manualSaltInput,
                onKeyChanged: notifier.updateKey,
                onShiftChanged: notifier.updateShift,
                onUseSaltToggled: notifier.toggleUseSalt,
                onManualSaltToggled: notifier.toggleManualSalt,
                onManualSaltChanged: notifier.updateManualSaltInput,
                onGenerateSalt: notifier.generateSalt,
              ),
              
              const SizedBox(height: 24),
              
              // Action Buttons
              ActionButtons(
                isProcessing: state.isProcessing,
                hasOutput: state.outputText.isNotEmpty,
                onProcess: notifier.processText,
                onCopy: notifier.copyToClipboard,
                onShare: notifier.shareText,
                onClear: notifier.clearAll,
              ),
              
              const SizedBox(height: 16),
              
              // Status Messages
              StatusMessage(
                successMessage: state.lastSuccess,
                errorMessage: state.lastError,
              ),
              
              const SizedBox(height: 16),
              
              // Result Section
              if (state.outputText.isNotEmpty || state.isProcessing)
                ResultSection(
                  output: state.outputText,
                  isProcessing: state.isProcessing,
                ),
            ],
          ),
        ),
      ),
    );
  }
}
