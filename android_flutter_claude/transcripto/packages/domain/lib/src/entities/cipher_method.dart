/// Supported cipher methods
enum CipherMethod {
  caesar('César'),
  base64('Base64'),
  vigenere('Vigenère'),
  xor('XOR');
  
  const CipherMethod(this.displayName);
  
  final String displayName;
  
  /// Returns the method name for envelope format
  String get methodName => switch (this) {
    CipherMethod.caesar => 'CAESAR',
    CipherMethod.base64 => 'BASE64',
    CipherMethod.vigenere => 'VIGENERE',
    CipherMethod.xor => 'XOR',
  };
  
  /// Parses method name from envelope format
  static CipherMethod? fromMethodName(String methodName) {
    return switch (methodName.toUpperCase()) {
      'CAESAR' => CipherMethod.caesar,
      'BASE64' => CipherMethod.base64,
      'VIGENERE' => CipherMethod.vigenere,
      'XOR' => CipherMethod.xor,
      _ => null,
    };
  }
  
  /// Returns true if this method requires a key
  bool get requiresKey => switch (this) {
    CipherMethod.caesar => false,
    CipherMethod.base64 => false,
    CipherMethod.vigenere => true,
    CipherMethod.xor => true,
  };
  
  /// Returns true if this method requires a shift value
  bool get requiresShift => switch (this) {
    CipherMethod.caesar => true,
    CipherMethod.base64 => false,
    CipherMethod.vigenere => false,
    CipherMethod.xor => false,
  };
}
