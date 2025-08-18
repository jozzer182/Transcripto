import 'package:core_common/core_common.dart';
import '../entities/cipher_method.dart';
import '../entities/cipher_params.dart';
import '../entities/envelope.dart';
import '../repositories/cipher_repository.dart';

/// Use case for decrypting text
class DecryptText {
  const DecryptText(this._cipherRepository);
  
  final CipherRepository _cipherRepository;
  
  /// Decrypts the input text, handling both envelope and raw formats
  Result<String> call(String input, CipherParams params) {
    try {
      // Validate input
      if (input.trim().isEmpty) {
        return const Failure(ValidationException('El texto de entrada no puede estar vacío'));
      }
      
      // Check if input is in envelope format
      if (Envelope.isEnvelopeFormat(input)) {
        return _decryptEnvelopeFormat(input);
      } else {
        return _decryptRawFormat(input, params);
      }
    } catch (e, stackTrace) {
      return Failure(CipherException('Error durante el descifrado: $e'), stackTrace);
    }
  }
  
  Result<String> _decryptEnvelopeFormat(String input) {
    // Parse envelope
    final envelope = Envelope.parse(input);
    if (envelope == null) {
      return const Failure(EnvelopeParseException('Formato de envelope inválido'));
    }
    
    // Create params from envelope
    final params = CipherParams(
      method: envelope.method,
      mode: CipherMode.decrypt,
      salt: envelope.salt,
      useSalt: envelope.salt != null,
    );
    
    // Validate method-specific requirements
    final validation = _validateMethodRequirements(envelope.method);
    if (validation != null) {
      return Failure(ValidationException(validation));
    }
    
    // Perform decryption
    return _cipherRepository.decrypt(envelope.payload, params);
  }
  
  Result<String> _decryptRawFormat(String input, CipherParams params) {
    // Validate parameters based on method
    final validation = _validateParams(params);
    if (validation != null) {
      return Failure(ValidationException(validation));
    }
    
    // Perform decryption
    return _cipherRepository.decrypt(input, params);
  }
  
  String? _validateParams(CipherParams params) {
    switch (params.method) {
      case CipherMethod.caesar:
        if (params.shift == null) {
          return 'El desplazamiento es requerido para César';
        }
        if (params.shift! < -25 || params.shift! > 25) {
          return 'El desplazamiento debe estar entre -25 y 25';
        }
        break;
      case CipherMethod.vigenere:
        if (params.key == null || params.key!.trim().isEmpty) {
          return 'La clave es requerida para Vigenère';
        }
        break;
      case CipherMethod.xor:
        if (params.key == null || params.key!.trim().isEmpty) {
          return 'La clave es requerida para XOR';
        }
        break;
      case CipherMethod.base64:
        // No additional validation needed
        break;
    }
    
    return null;
  }
  
  String? _validateMethodRequirements(CipherMethod method) {
    switch (method) {
      case CipherMethod.vigenere:
        return 'Para descifrar Vigenère desde envelope, la clave debe proporcionarse en la UI';
      case CipherMethod.xor:
        return 'Para descifrar XOR desde envelope, la clave debe proporcionarse en la UI';
      case CipherMethod.caesar:
      case CipherMethod.base64:
        return null; // These methods don't need additional keys from UI
    }
  }
}
