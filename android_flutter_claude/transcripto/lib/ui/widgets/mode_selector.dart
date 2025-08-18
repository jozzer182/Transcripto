import 'package:flutter/material.dart';
import 'package:domain/domain.dart';

class ModeSelector extends StatelessWidget {
  const ModeSelector({
    super.key,
    required this.selectedMode,
    required this.onModeChanged,
  });

  final CipherMode selectedMode;
  final ValueChanged<CipherMode> onModeChanged;

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Modo',
              style: Theme.of(context).textTheme.titleMedium,
            ),
            const SizedBox(height: 12),
            SegmentedButton<CipherMode>(
              segments: CipherMode.values.map((mode) {
                return ButtonSegment<CipherMode>(
                  value: mode,
                  label: Text(mode.displayName),
                  icon: Icon(
                    mode == CipherMode.encrypt 
                        ? Icons.lock_outline 
                        : Icons.lock_open_outlined,
                  ),
                );
              }).toList(),
              selected: {selectedMode},
              onSelectionChanged: (Set<CipherMode> selection) {
                if (selection.isNotEmpty) {
                  onModeChanged(selection.first);
                }
              },
              showSelectedIcon: false,
            ),
          ],
        ),
      ),
    );
  }
}
