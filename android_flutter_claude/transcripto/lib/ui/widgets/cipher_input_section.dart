import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:domain/domain.dart';

class CipherInputSection extends StatelessWidget {
  const CipherInputSection({
    super.key,
    required this.method,
    required this.cipherKey,
    required this.shift,
    required this.useSalt,
    required this.isManualSalt,
    required this.manualSaltInput,
    required this.onKeyChanged,
    required this.onShiftChanged,
    required this.onUseSaltToggled,
    required this.onManualSaltToggled,
    required this.onManualSaltChanged,
    required this.onGenerateSalt,
  });

  final CipherMethod method;
  final String cipherKey;
  final int shift;
  final bool useSalt;
  final bool isManualSalt;
  final String manualSaltInput;
  final ValueChanged<String> onKeyChanged;
  final ValueChanged<int> onShiftChanged;
  final VoidCallback onUseSaltToggled;
  final VoidCallback onManualSaltToggled;
  final ValueChanged<String> onManualSaltChanged;
  final VoidCallback onGenerateSalt;

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Configuración',
              style: Theme.of(context).textTheme.titleMedium,
            ),
            const SizedBox(height: 16),
            
            // Method-specific inputs
            if (method.requiresKey) ...[
              TextField(
                decoration: InputDecoration(
                  labelText: 'Clave',
                  hintText: 'Introduce la clave para ${method.displayName}',
                  border: const OutlineInputBorder(),
                ),
                onChanged: onKeyChanged,
              ),
              const SizedBox(height: 16),
            ],
            
            if (method.requiresShift) ...[
              TextField(
                decoration: const InputDecoration(
                  labelText: 'Desplazamiento',
                  hintText: 'Número entero (-25 a 25)',
                  border: OutlineInputBorder(),
                ),
                keyboardType: TextInputType.number,
                inputFormatters: [
                  FilteringTextInputFormatter.allow(RegExp(r'^-?\d*')),
                ],
                onChanged: (value) {
                  final parsed = int.tryParse(value);
                  if (parsed != null) {
                    onShiftChanged(parsed);
                  }
                },
              ),
              const SizedBox(height: 16),
            ],
            
            // Salt section
            Row(
              children: [
                Switch(
                  value: useSalt,
                  onChanged: (_) => onUseSaltToggled(),
                ),
                const SizedBox(width: 8),
                Text(
                  'Usar salt',
                  style: Theme.of(context).textTheme.bodyLarge,
                ),
              ],
            ),
            
            if (useSalt) ...[
              const SizedBox(height: 12),
              Row(
                children: [
                  Expanded(
                    child: SegmentedButton<bool>(
                      segments: const [
                        ButtonSegment<bool>(
                          value: false,
                          label: Text('Automático'),
                          icon: Icon(Icons.auto_mode, size: 16),
                        ),
                        ButtonSegment<bool>(
                          value: true,
                          label: Text('Manual'),
                          icon: Icon(Icons.edit, size: 16),
                        ),
                      ],
                      selected: {isManualSalt},
                      onSelectionChanged: (Set<bool> selection) {
                        if (selection.isNotEmpty && selection.first != isManualSalt) {
                          onManualSaltToggled();
                        }
                      },
                      showSelectedIcon: false,
                    ),
                  ),
                  if (!isManualSalt) ...[
                    const SizedBox(width: 8),
                    FilledButton.tonal(
                      onPressed: onGenerateSalt,
                      child: const Row(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          Icon(Icons.refresh, size: 16),
                          SizedBox(width: 4),
                          Text('Generar'),
                        ],
                      ),
                    ),
                  ],
                ],
              ),
              const SizedBox(height: 12),
              TextField(
                controller: TextEditingController(text: manualSaltInput),
                decoration: const InputDecoration(
                  labelText: 'Salt (Base64 o Hex)',
                  hintText: 'Introduce el salt manualmente',
                  border: OutlineInputBorder(),
                ),
                enabled: isManualSalt,
                onChanged: onManualSaltChanged,
              ),
            ],
          ],
        ),
      ),
    );
  }
}
