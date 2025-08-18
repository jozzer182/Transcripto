/// Custom exceptions for the Transcripto app
abstract class TranscriptoException implements Exception {
  const TranscriptoException(this.message);
  
  final String message;
  
  @override
  String toString() => 'TranscriptoException: $message';
}

/// Exception thrown when cipher operation fails
class CipherException extends TranscriptoException {
  const CipherException(super.message);
  
  @override
  String toString() => 'CipherException: $message';
}

/// Exception thrown when validation fails
class ValidationException extends TranscriptoException {
  const ValidationException(super.message);
  
  @override
  String toString() => 'ValidationException: $message';
}

/// Exception thrown when envelope parsing fails
class EnvelopeParseException extends TranscriptoException {
  const EnvelopeParseException(super.message);
  
  @override
  String toString() => 'EnvelopeParseException: $message';
}

/// Exception thrown when input format is invalid
class InvalidInputException extends TranscriptoException {
  const InvalidInputException(super.message);
  
  @override
  String toString() => 'InvalidInputException: $message';
}

/// Exception thrown when key is invalid for the cipher method
class InvalidKeyException extends TranscriptoException {
  const InvalidKeyException(super.message);
  
  @override
  String toString() => 'InvalidKeyException: $message';
}
