import 'dart:convert';

/// Common validation utilities
class Validators {
  Validators._();
  
  /// Validates that a string is not empty
  static String? validateNotEmpty(String? value, [String? fieldName]) {
    if (value == null || value.trim().isEmpty) {
      return '${fieldName ?? 'Este campo'} no puede estar vacío';
    }
    return null;
  }
  
  /// Validates Caesar cipher shift value
  static String? validateCaesarShift(String? value) {
    if (value == null || value.trim().isEmpty) {
      return 'El desplazamiento es requerido';
    }
    
    final shift = int.tryParse(value.trim());
    if (shift == null) {
      return 'Debe ser un número entero';
    }
    
    if (shift < -25 || shift > 25) {
      return 'El desplazamiento debe estar entre -25 y 25';
    }
    
    return null;
  }
  
  /// Validates Vigenère cipher key
  static String? validateVigenereKey(String? value) {
    if (value == null || value.trim().isEmpty) {
      return 'La clave es requerida para Vigenère';
    }
    
    final key = value.trim();
    final hasValidLetters = key.runes.any((rune) {
      final char = String.fromCharCode(rune);
      return RegExp(r'[A-Za-z]').hasMatch(char);
    });
    
    if (!hasValidLetters) {
      return 'La clave debe contener al menos una letra (A-Z, a-z)';
    }
    
    return null;
  }
  
  /// Validates XOR cipher key
  static String? validateXorKey(String? value) {
    if (value == null || value.trim().isEmpty) {
      return 'La clave es requerida para XOR';
    }
    
    return null;
  }
  
  /// Validates salt format (base64 or hex)
  static String? validateSalt(String? value) {
    if (value == null || value.trim().isEmpty) {
      return 'El salt no puede estar vacío';
    }
    
    final salt = value.trim();
    
    // Check if it's valid base64
    try {
      final decoded = base64Decode(salt);
      if (decoded.length < 8 || decoded.length > 16) {
        return 'El salt debe tener entre 8 y 16 bytes';
      }
      return null;
    } catch (_) {
      // Not base64, check if it's valid hex
      if (RegExp(r'^[0-9A-Fa-f]+$').hasMatch(salt)) {
        if (salt.length % 2 != 0) {
          return 'El salt hexadecimal debe tener longitud par';
        }
        final byteLength = salt.length ~/ 2;
        if (byteLength < 8 || byteLength > 16) {
          return 'El salt debe tener entre 8 y 16 bytes';
        }
        return null;
      }
    }
    
    return 'El salt debe estar en formato Base64 o hexadecimal';
  }
}
