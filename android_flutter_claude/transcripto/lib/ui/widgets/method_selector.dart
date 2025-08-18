import 'package:flutter/material.dart';
import 'package:domain/domain.dart';

class MethodSelector extends StatelessWidget {
  const MethodSelector({
    super.key,
    required this.selectedMethod,
    required this.onMethodChanged,
  });

  final CipherMethod selectedMethod;
  final ValueChanged<CipherMethod> onMethodChanged;

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Método de cifrado',
              style: Theme.of(context).textTheme.titleMedium,
            ),
            const SizedBox(height: 12),
            Wrap(
              spacing: 8,
              runSpacing: 8,
              children: CipherMethod.values.map((method) {
                final isSelected = method == selectedMethod;
                return FilterChip(
                  label: Text(
                    method.displayName,
                    style: TextStyle(
                      color: isSelected 
                        ? Theme.of(context).colorScheme.onPrimary
                        : Theme.of(context).colorScheme.onSurface,
                      fontWeight: isSelected ? FontWeight.w600 : FontWeight.w500,
                    ),
                  ),
                  selected: isSelected,
                  onSelected: (selected) {
                    if (selected) {
                      onMethodChanged(method);
                    }
                  },
                  avatar: isSelected ? null : Icon(
                    _getMethodIcon(method),
                    size: 16,
                    color: Theme.of(context).colorScheme.onSurfaceVariant,
                  ),
                  selectedColor: Theme.of(context).colorScheme.primary,
                  backgroundColor: Theme.of(context).colorScheme.surface,
                  checkmarkColor: Theme.of(context).colorScheme.onPrimary,
                  side: BorderSide(
                    color: isSelected 
                      ? Theme.of(context).colorScheme.primary
                      : Theme.of(context).colorScheme.outline,
                    width: isSelected ? 2 : 1,
                  ),
                );
              }).toList(),
            ),
            const SizedBox(height: 8),
            Text(
              _getMethodDescription(selectedMethod),
              style: Theme.of(context).textTheme.bodySmall?.copyWith(
                color: Theme.of(context).colorScheme.onSurfaceVariant,
              ),
            ),
          ],
        ),
      ),
    );
  }

  IconData _getMethodIcon(CipherMethod method) {
    return switch (method) {
      CipherMethod.caesar => Icons.swap_horiz,
      CipherMethod.base64 => Icons.code,
      CipherMethod.vigenere => Icons.key,
      CipherMethod.xor => Icons.dynamic_form,
    };
  }

  String _getMethodDescription(CipherMethod method) {
    return switch (method) {
      CipherMethod.caesar => 'Desplaza las letras del alfabeto según un valor numérico.',
      CipherMethod.base64 => 'Codificación estándar que convierte texto a formato Base64.',
      CipherMethod.vigenere => 'Cifrado polialfabético que usa una clave de texto.',
      CipherMethod.xor => 'Operación XOR bit a bit con una clave repetida.',
    };
  }
}
