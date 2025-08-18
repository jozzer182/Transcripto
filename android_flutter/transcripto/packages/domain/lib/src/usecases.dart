import 'dart:convert';

import 'package:core_common/core_common.dart';

import 'cipher_method.dart';
import 'envelope.dart';

abstract class GenerateSalt {
  Future<List<int>> call({int min, int max});
}

class DetectAndParseEnvelope {
  const DetectAndParseEnvelope();
  Result<Envelope> call(String input) {
    final env = Envelope.tryParse(input);
    if (env == null) return Failure(Exception('Formato de envelope inválido'));
    return Success(env);
  }
}

class EncryptText {
  final Map<CipherKind, CipherMethod> methods;
  EncryptText(this.methods);

  Result<String> call(String input, CipherParams params) {
    try {
      final method = methods[params.method];
      if (method == null) return Failure(Exception('Método no soportado'));
  final payload = method.encrypt(input, params);
  // Output only the payload (plain cifrado), no envelope.
  return Success(payload);
    } catch (e, st) {
      return Failure(e, st);
    }
  }

}

class DecryptText {
  final Map<CipherKind, CipherMethod> methods;
  DecryptText(this.methods);

  Result<String> call(String input, CipherParams params) {
    try {
      // If input contains envelope, prefer that
      final env = Envelope.tryParse(input);
      if (env != null) {
        final m = _parseMethod(env.method);
        if (m == null) return Failure(Exception('Método en envelope inválido'));
        final method = methods[m];
        if (method == null) return Failure(Exception('Método no soportado'));
        final salt = env.saltBase64.isEmpty ? null : base64Decode(env.saltBase64);
        final p = CipherParams(
          method: m,
          mode: CipherMode.decrypt,
          key: params.key,
          shift: params.shift,
          salt: salt,
          useSalt: salt != null,
        );
        final out = method.decrypt(env.payload, p);
        return Success(out);
      }
      final method = methods[params.method];
      if (method == null) return Failure(Exception('Método no soportado'));
      final out = method.decrypt(input, params);
      return Success(out);
    } catch (e, st) {
      return Failure(e, st);
    }
  }

  CipherKind? _parseMethod(String s) {
    switch (s.toUpperCase()) {
      case 'BASE64':
        return CipherKind.base64;
      case 'CAESAR':
        return CipherKind.caesar;
      case 'VIGENERE':
        return CipherKind.vigenere;
      case 'XOR':
        return CipherKind.xor;
    }
    return null;
  }
}
