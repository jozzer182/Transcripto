import 'dart:convert';

import 'package:domain/domain.dart';

class XorCipher implements CipherMethod {
  List<int> _effectiveKeyBytes(CipherParams p) {
    var key = p.key ?? '';
    if (p.useSalt && p.salt != null) {
      key = key + utf8.decode(p.salt!);
    }
    final kb = utf8.encode(key);
    if (kb.isEmpty) throw ArgumentError('Clave inválida');
    return kb;
  }

  @override
  String encrypt(String input, CipherParams params) {
    final keyBytes = _effectiveKeyBytes(params);
    final inputBytes = utf8.encode(input);
    final outBytes = _xorBytes(inputBytes, keyBytes);
    return base64Encode(outBytes);
  }

  @override
  String decrypt(String input, CipherParams params) {
    final keyBytes = _effectiveKeyBytes(params);
    final payloadBytes = base64Decode(input);
    final outBytes = _xorBytes(payloadBytes, keyBytes);
    return utf8.decode(outBytes);
  }

  List<int> _xorBytes(List<int> data, List<int> key) {
    final out = List<int>.generate(data.length, (i) => data[i] ^ key[i % key.length]);
    return out;
  }
}
