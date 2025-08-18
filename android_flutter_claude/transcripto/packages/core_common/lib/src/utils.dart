import 'dart:convert';
import 'dart:math';

/// Common utility functions
class Utils {
  Utils._();
  
  /// Generates a secure random salt of the specified length
  static List<int> generateRandomSalt([int length = 12]) {
    final random = Random.secure();
    return List.generate(length, (_) => random.nextInt(256));
  }
  
  /// Converts a hex string to bytes
  static List<int> hexToBytes(String hex) {
    final result = <int>[];
    for (int i = 0; i < hex.length; i += 2) {
      final hexByte = hex.substring(i, i + 2);
      result.add(int.parse(hexByte, radix: 16));
    }
    return result;
  }
  
  /// Converts bytes to hex string
  static String bytesToHex(List<int> bytes) {
    return bytes.map((b) => b.toRadixString(16).padLeft(2, '0')).join();
  }
  
  /// Simple hash function for salt integration (FNV-1a variant)
  static int simpleHash(List<int> data) {
    const fnvOffsetBasis = 2166136261;
    const fnvPrime = 16777619;
    
    int hash = fnvOffsetBasis;
    for (final byte in data) {
      hash ^= byte;
      hash = (hash * fnvPrime) & 0xFFFFFFFF;
    }
    return hash;
  }
  
  /// Normalizes a string by removing accents and converting to basic ASCII
  static String normalizeString(String input) {
    const accentMap = {
      'á': 'a', 'à': 'a', 'ä': 'a', 'â': 'a', 'ā': 'a', 'ą': 'a',
      'é': 'e', 'è': 'e', 'ë': 'e', 'ê': 'e', 'ē': 'e', 'ę': 'e',
      'í': 'i', 'ì': 'i', 'ï': 'i', 'î': 'i', 'ī': 'i', 'į': 'i',
      'ó': 'o', 'ò': 'o', 'ö': 'o', 'ô': 'o', 'ō': 'o', 'ø': 'o',
      'ú': 'u', 'ù': 'u', 'ü': 'u', 'û': 'u', 'ū': 'u', 'ų': 'u',
      'ñ': 'n', 'ç': 'c',
      'Á': 'A', 'À': 'A', 'Ä': 'A', 'Â': 'A', 'Ā': 'A', 'Ą': 'A',
      'É': 'E', 'È': 'E', 'Ë': 'E', 'Ê': 'E', 'Ē': 'E', 'Ę': 'E',
      'Í': 'I', 'Ì': 'I', 'Ï': 'I', 'Î': 'I', 'Ī': 'I', 'Į': 'I',
      'Ó': 'O', 'Ò': 'O', 'Ö': 'O', 'Ô': 'O', 'Ō': 'O', 'Ø': 'O',
      'Ú': 'U', 'Ù': 'U', 'Ü': 'U', 'Û': 'U', 'Ū': 'U', 'Ų': 'U',
      'Ñ': 'N', 'Ç': 'C',
    };
    
    return input.split('').map((char) => accentMap[char] ?? char).join();
  }
  
  /// Checks if a character is a letter (A-Z, a-z)
  static bool isLetter(String char) {
    if (char.length != 1) return false;
    final code = char.codeUnitAt(0);
    return (code >= 65 && code <= 90) || (code >= 97 && code <= 122);
  }
  
  /// Checks if a character is uppercase
  static bool isUppercase(String char) {
    if (char.length != 1) return false;
    final code = char.codeUnitAt(0);
    return code >= 65 && code <= 90;
  }
  
  /// Converts a string to UTF-8 bytes
  static List<int> stringToBytes(String text) {
    return utf8.encode(text);
  }
  
  /// Converts UTF-8 bytes to string
  static String bytesToString(List<int> bytes) {
    return utf8.decode(bytes);
  }
}
