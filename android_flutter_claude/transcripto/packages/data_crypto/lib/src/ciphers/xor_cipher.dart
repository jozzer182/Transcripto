import 'dart:convert';
import 'package:core_common/core_common.dart';
import 'package:domain/domain.dart';

/// XOR cipher implementation
class XorCipher {
  const XorCipher();
  
  /// Encrypts text using XOR cipher (returns Base64 encoded result)
  String encrypt(String input, CipherParams params) {
    final keyBytes = _getEffectiveKey(params);
    final inputBytes = utf8.encode(input);
    final encryptedBytes = _xorBytes(inputBytes, keyBytes);
    return base64Encode(encryptedBytes);
  }
  
  /// Decrypts text using XOR cipher (expects Base64 encoded input)
  String decrypt(String input, CipherParams params) {
    try {
      final keyBytes = _getEffectiveKey(params);
      final encryptedBytes = base64Decode(input);
      final decryptedBytes = _xorBytes(encryptedBytes, keyBytes);
      return utf8.decode(decryptedBytes);
    } catch (e) {
      throw CipherException('Invalid XOR input format: $e');
    }
  }
  
  List<int> _getEffectiveKey(CipherParams params) {
    if (params.key == null || params.key!.trim().isEmpty) {
      throw InvalidKeyException('XOR cipher requires a key');
    }
    
    String effectiveKey = params.key!.trim();
    
    // Add salt to key if enabled
    if (params.useSalt && params.salt != null) {
      final saltString = utf8.decode(params.salt!);
      effectiveKey = effectiveKey + saltString;
    }
    
    return utf8.encode(effectiveKey);
  }
  
  List<int> _xorBytes(List<int> data, List<int> key) {
    if (key.isEmpty) {
      throw InvalidKeyException('XOR key cannot be empty');
    }
    
    final result = <int>[];
    for (int i = 0; i < data.length; i++) {
      final keyByte = key[i % key.length];
      result.add(data[i] ^ keyByte);
    }
    return result;
  }
}
