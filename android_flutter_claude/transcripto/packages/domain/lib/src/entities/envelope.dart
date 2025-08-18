import 'dart:convert';
import 'cipher_method.dart';
import 'cipher_params.dart';

/// Represents an envelope format for encrypted data
class Envelope {
  const Envelope({
    required this.method,
    required this.salt,
    required this.payload,
  });
  
  final CipherMethod method;
  final List<int>? salt;
  final String payload;
  
  /// Creates envelope from cipher parameters and payload
  factory Envelope.fromParams(CipherParams params, String payload) {
    return Envelope(
      method: params.method,
      salt: params.useSalt ? params.salt : null,
      payload: payload,
    );
  }
  
  /// Serializes envelope to string format
  /// Format: method=METHOD;salt=BASE64_OR_EMPTY;payload=RESULT
  String serialize() {
    final saltBase64 = salt != null ? base64Encode(salt!) : '';
    return 'method=${method.methodName};salt=$saltBase64;payload=$payload';
  }
  
  /// Parses envelope from string format
  /// Returns null if format is invalid
  static Envelope? parse(String input) {
    try {
      // Check if it starts with method=
      if (!input.startsWith('method=')) return null;
      
      // Split by semicolons
      final parts = input.split(';');
      if (parts.length != 3) return null;
      
      // Parse method
      final methodPart = parts[0];
      if (!methodPart.startsWith('method=')) return null;
      final methodName = methodPart.substring(7); // Remove 'method='
      final method = CipherMethod.fromMethodName(methodName);
      if (method == null) return null;
      
      // Parse salt
      final saltPart = parts[1];
      if (!saltPart.startsWith('salt=')) return null;
      final saltBase64 = saltPart.substring(5); // Remove 'salt='
      List<int>? salt;
      if (saltBase64.isNotEmpty) {
        try {
          salt = base64Decode(saltBase64);
        } catch (_) {
          return null; // Invalid base64
        }
      }
      
      // Parse payload
      final payloadPart = parts[2];
      if (!payloadPart.startsWith('payload=')) return null;
      final payload = payloadPart.substring(8); // Remove 'payload='
      
      return Envelope(
        method: method,
        salt: salt,
        payload: payload,
      );
    } catch (_) {
      return null;
    }
  }
  
  /// Checks if a string is in envelope format
  static bool isEnvelopeFormat(String input) {
    return input.startsWith('method=') && 
           input.contains(';salt=') && 
           input.contains(';payload=');
  }
  
  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is Envelope &&
           other.method == method &&
           _listEquals(other.salt, salt) &&
           other.payload == payload;
  }
  
  @override
  int get hashCode {
    return Object.hash(
      method,
      salt?.fold<int>(0, (prev, element) => prev ^ element.hashCode) ?? 0,
      payload,
    );
  }
  
  @override
  String toString() {
    return 'Envelope('
           'method: $method, '
           'salt: $salt, '
           'payload: $payload'
           ')';
  }
}

bool _listEquals<T>(List<T>? a, List<T>? b) {
  if (identical(a, b)) return true;
  if (a == null || b == null) return false;
  if (a.length != b.length) return false;
  for (int i = 0; i < a.length; i++) {
    if (a[i] != b[i]) return false;
  }
  return true;
}
