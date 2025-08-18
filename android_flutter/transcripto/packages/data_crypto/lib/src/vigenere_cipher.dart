import 'dart:convert';

import 'package:domain/domain.dart';
import 'package:core_common/core_common.dart';

class VigenereCipher implements CipherMethod {
  @override
  String encrypt(String input, CipherParams params) {
    final key = _effectiveKey(params);
    if (key.isEmpty) {
      throw ArgumentError('Clave inválida');
    }
    final k = key;
    final sb = StringBuffer();
    int ki = 0;
    for (final ch in input.split('')) {
      final code = ch.codeUnitAt(0);
      if (_isAlpha(code)) {
        final isUpper = code >= 65 && code <= 90;
        final base = isUpper ? 65 : 97;
        final shift = _charShift(k[ki % k.length]);
        final pos = code - base;
        final npos = (pos + shift) % 26;
        sb.writeCharCode(base + npos);
        ki++;
      } else {
        sb.write(ch);
      }
    }
    return sb.toString();
  }

  @override
  String decrypt(String input, CipherParams params) {
    final key = _effectiveKey(params);
    if (key.isEmpty) {
      throw ArgumentError('Clave inválida');
    }
    final k = key;
    final sb = StringBuffer();
    int ki = 0;
    for (final ch in input.split('')) {
      final code = ch.codeUnitAt(0);
      if (_isAlpha(code)) {
        final isUpper = code >= 65 && code <= 90;
        final base = isUpper ? 65 : 97;
        final shift = _charShift(k[ki % k.length]);
        final pos = code - base;
        final npos = (pos - shift) % 26;
        sb.writeCharCode(base + (npos < 0 ? npos + 26 : npos));
        ki++;
      } else {
        sb.write(ch);
      }
    }
    return sb.toString();
  }

  String _normalizeLetters(String s) {
    final noDia = removeDiacritics(s);
    final lettersOnly = noDia.replaceAll(RegExp('[^A-Za-z]'), '');
    return lettersOnly;
  }

  String _effectiveKey(CipherParams p) {
    var key = _normalizeLetters(p.key ?? '');
    if (p.useSalt && p.salt != null) {
      final saltStr = _normalizeLetters(utf8.decode(p.salt!));
      key = key + saltStr;
    }
    return key;
  }

  bool _isAlpha(int code) {
    return (code >= 65 && code <= 90) || (code >= 97 && code <= 122);
  }

  int _charShift(String ch) {
    if (ch.isEmpty) return 0;
    final c = ch.codeUnitAt(0);
    if (c >= 65 && c <= 90) return c - 65;
    if (c >= 97 && c <= 122) return c - 97;
    return 0;
  }
}
