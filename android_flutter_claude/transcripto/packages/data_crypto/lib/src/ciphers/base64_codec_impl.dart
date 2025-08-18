import 'dart:convert';
import 'package:core_common/core_common.dart';
import 'package:domain/domain.dart';

/// Base64 codec implementation
class Base64CodecImpl {
  const Base64CodecImpl();
  
  /// Encodes text to Base64
  String encrypt(String input, CipherParams params) {
    String textToEncode = input;
    
    // Handle salt for Base64
    if (params.useSalt && params.salt != null) {
      final saltBase64 = base64Encode(params.salt!);
      textToEncode = '$saltBase64:$input';
    }
    
    final bytes = utf8.encode(textToEncode);
    return base64Encode(bytes);
  }
  
  /// Decodes text from Base64
  String decrypt(String input, CipherParams params) {
    try {
      final decodedBytes = base64Decode(input);
      final decodedText = utf8.decode(decodedBytes);
      
      // Handle salt for Base64
      if (params.useSalt && decodedText.contains(':')) {
        final parts = decodedText.split(':');
        if (parts.length >= 2) {
          // Remove salt prefix and return the original text
          return parts.sublist(1).join(':');
        }
      }
      
      return decodedText;
    } catch (e) {
      throw CipherException('Invalid Base64 format: $e');
    }
  }
}
