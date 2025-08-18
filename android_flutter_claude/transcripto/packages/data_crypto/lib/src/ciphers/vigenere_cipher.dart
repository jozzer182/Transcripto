import 'dart:convert';
import 'package:core_common/core_common.dart';
import 'package:domain/domain.dart';

/// Vigenère cipher implementation
class VigenereCipher {
  const VigenereCipher();
  
  /// Encrypts text using Vigenère cipher
  String encrypt(String input, CipherParams params) {
    final key = _getEffectiveKey(params);
    return _processText(input, key, true);
  }
  
  /// Decrypts text using Vigenère cipher
  String decrypt(String input, CipherParams params) {
    final key = _getEffectiveKey(params);
    return _processText(input, key, false);
  }
  
  String _getEffectiveKey(CipherParams params) {
    if (params.key == null || params.key!.trim().isEmpty) {
      throw InvalidKeyException('Vigenère cipher requires a key');
    }
    
    String effectiveKey = params.key!.trim();
    
    // Add salt to key if enabled
    if (params.useSalt && params.salt != null) {
      final saltString = utf8.decode(params.salt!);
      effectiveKey = effectiveKey + saltString;
    }
    
    // Normalize and filter only letters
    final normalizedKey = Utils.normalizeString(effectiveKey);
    final lettersOnly = normalizedKey
        .split('')
        .where((char) => Utils.isLetter(char))
        .join()
        .toUpperCase();
    
    if (lettersOnly.isEmpty) {
      throw InvalidKeyException('Key must contain at least one letter');
    }
    
    return lettersOnly;
  }
  
  String _processText(String input, String key, bool encrypt) {
    final buffer = StringBuffer();
    int keyIndex = 0;
    
    for (int i = 0; i < input.length; i++) {
      final char = input[i];
      
      if (Utils.isLetter(char)) {
        final isUppercase = Utils.isUppercase(char);
        final charCode = char.toUpperCase().codeUnitAt(0);
        final keyChar = key[keyIndex % key.length];
        final keyCode = keyChar.codeUnitAt(0);
        
        final shift = keyCode - 65; // A=0, B=1, etc.
        int resultCode;
        
        if (encrypt) {
          resultCode = ((charCode - 65 + shift) % 26) + 65;
        } else {
          resultCode = ((charCode - 65 - shift + 26) % 26) + 65;
        }
        
        final resultChar = String.fromCharCode(resultCode);
        buffer.write(isUppercase ? resultChar : resultChar.toLowerCase());
        keyIndex++;
      } else {
        buffer.write(char); // Keep non-alphabetic characters unchanged
      }
    }
    
    return buffer.toString();
  }
}
