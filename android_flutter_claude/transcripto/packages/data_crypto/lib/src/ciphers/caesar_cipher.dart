import 'package:core_common/core_common.dart';
import 'package:domain/domain.dart';

/// Caesar cipher implementation
class CaesarCipher {
  const CaesarCipher();
  
  /// Encrypts text using Caesar cipher
  String encrypt(String input, CipherParams params) {
    final shift = _getEffectiveShift(params);
    return _processText(input, shift);
  }
  
  /// Decrypts text using Caesar cipher
  String decrypt(String input, CipherParams params) {
    final shift = _getEffectiveShift(params);
    return _processText(input, -shift);
  }
  
  int _getEffectiveShift(CipherParams params) {
    int baseShift = params.shift ?? 0;
    
    if (params.useSalt && params.salt != null) {
      final saltHash = Utils.simpleHash(params.salt!);
      final saltShift = saltHash % 26;
      baseShift = params.mode == CipherMode.encrypt 
          ? (baseShift + saltShift) % 26
          : (baseShift - saltShift) % 26;
    }
    
    return baseShift;
  }
  
  String _processText(String input, int shift) {
    final buffer = StringBuffer();
    
    for (int i = 0; i < input.length; i++) {
      final char = input[i];
      final code = char.codeUnitAt(0);
      
      if (code >= 65 && code <= 90) { // A-Z
        final shifted = ((code - 65 + shift) % 26 + 26) % 26;
        buffer.write(String.fromCharCode(shifted + 65));
      } else if (code >= 97 && code <= 122) { // a-z
        final shifted = ((code - 97 + shift) % 26 + 26) % 26;
        buffer.write(String.fromCharCode(shifted + 97));
      } else {
        buffer.write(char); // Keep non-alphabetic characters unchanged
      }
    }
    
    return buffer.toString();
  }
}
