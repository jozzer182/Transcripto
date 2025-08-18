import 'package:flutter/material.dart';

class ActionButtons extends StatelessWidget {
  const ActionButtons({
    super.key,
    required this.isProcessing,
    required this.hasOutput,
    required this.onProcess,
    required this.onCopy,
    required this.onShare,
    required this.onClear,
  });

  final bool isProcessing;
  final bool hasOutput;
  final VoidCallback onProcess;
  final VoidCallback onCopy;
  final VoidCallback onShare;
  final VoidCallback onClear;

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        // Primary action button
        SizedBox(
          width: double.infinity,
          child: FilledButton.icon(
            onPressed: isProcessing ? null : onProcess,
            icon: isProcessing 
                ? const SizedBox(
                    width: 16,
                    height: 16,
                    child: CircularProgressIndicator(strokeWidth: 2),
                  )
                : const Icon(Icons.play_arrow),
            label: Text(isProcessing ? 'Procesando...' : 'Procesar'),
            style: FilledButton.styleFrom(
              padding: const EdgeInsets.symmetric(vertical: 16),
            ),
          ),
        ),
        
        if (hasOutput) ...[
          const SizedBox(height: 12),
          
          // Secondary action buttons
          Row(
            children: [
              Expanded(
                child: OutlinedButton.icon(
                  onPressed: onCopy,
                  icon: const Icon(Icons.copy, size: 18),
                  label: const Text('Copiar'),
                ),
              ),
              const SizedBox(width: 8),
              Expanded(
                child: OutlinedButton.icon(
                  onPressed: onShare,
                  icon: const Icon(Icons.share, size: 18),
                  label: const Text('Compartir'),
                ),
              ),
              const SizedBox(width: 8),
              Expanded(
                child: TextButton.icon(
                  onPressed: onClear,
                  icon: const Icon(Icons.clear, size: 18),
                  label: const Text('Limpiar'),
                ),
              ),
            ],
          ),
        ],
      ],
    );
  }
}
