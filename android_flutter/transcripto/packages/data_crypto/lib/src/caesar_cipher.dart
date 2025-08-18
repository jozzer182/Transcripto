
import 'package:domain/domain.dart';
import 'package:core_common/core_common.dart';

class CaesarCipher implements CipherMethod {
  int _normalizeShift(int shift) => ((shift % 26) + 26) % 26;

  @override
  String encrypt(String input, CipherParams params) {
    final shift = _effectiveShift(params);
    final out = input.split('').map((c) => _shiftChar(c, shift)).join();
    return out;
  }

  @override
  String decrypt(String input, CipherParams params) {
    // invert encryption shift (base + saltAdj)
    final encShift = _effectiveShift(params);
    return input.split('').map((c) => _shiftChar(c, -encShift)).join();
  }

  int _effectiveShift(CipherParams p) {
    final base = p.shift ?? 0;
    var saltAdj = 0;
    if (p.useSalt && p.salt != null) {
      saltAdj = simpleHashBytes(p.salt!) % 26;
    }
    return _normalizeShift(base + saltAdj);
  }

  String _shiftChar(String c, int shift) {
    if (c.isEmpty) return c;
    final code = c.codeUnitAt(0);
    if (code >= 65 && code <= 90) {
      // A-Z
      final a = 65;
      final pos = code - a;
      final npos = (pos + shift) % 26;
      return String.fromCharCode(a + (npos < 0 ? npos + 26 : npos));
    } else if (code >= 97 && code <= 122) {
      // a-z
      final a = 97;
      final pos = code - a;
      final npos = (pos + shift) % 26;
      return String.fromCharCode(a + (npos < 0 ? npos + 26 : npos));
    }
    return c; // non alpha unchanged
  }
}
