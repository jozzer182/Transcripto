import 'dart:convert';
import 'package:core_common/core_common.dart';

/// Provider for salt generation and validation
class SaltProvider {
  const SaltProvider();
  
  /// Generates a secure random salt
  List<int> generateSalt([int length = 12]) {
    if (length < 8 || length > 16) {
      throw ValidationException('Salt length must be between 8 and 16 bytes');
    }
    
    return Utils.generateRandomSalt(length);
  }
  
  /// Validates and parses salt from string input
  List<int> parseSalt(String saltInput) {
    if (saltInput.trim().isEmpty) {
      throw ValidationException('Salt cannot be empty');
    }
    
    final salt = saltInput.trim();
    
    // Try to parse as Base64 first
    try {
      final decoded = base64Decode(salt);
      if (decoded.length < 8 || decoded.length > 16) {
        throw ValidationException('Salt must be between 8 and 16 bytes');
      }
      return decoded;
    } catch (_) {
      // If Base64 fails, try hex
      if (RegExp(r'^[0-9A-Fa-f]+$').hasMatch(salt)) {
        if (salt.length % 2 != 0) {
          throw ValidationException('Hex salt must have even length');
        }
        
        final bytes = Utils.hexToBytes(salt);
        if (bytes.length < 8 || bytes.length > 16) {
          throw ValidationException('Salt must be between 8 and 16 bytes');
        }
        return bytes;
      }
    }
    
    throw ValidationException('Salt must be in Base64 or hexadecimal format');
  }
  
  /// Converts salt bytes to Base64 string for display
  String saltToBase64(List<int> salt) {
    return base64Encode(salt);
  }
  
  /// Converts salt bytes to hex string for display
  String saltToHex(List<int> salt) {
    return Utils.bytesToHex(salt);
  }
}
