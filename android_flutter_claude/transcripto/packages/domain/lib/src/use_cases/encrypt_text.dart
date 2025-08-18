import 'package:core_common/core_common.dart';
import '../entities/cipher_method.dart';
import '../entities/cipher_params.dart';
import '../entities/envelope.dart';
import '../repositories/cipher_repository.dart';

/// Use case for encrypting text
class EncryptText {
  const EncryptText(this._cipherRepository);
  
  final CipherRepository _cipherRepository;
  
  /// Encrypts the input text and optionally wraps in envelope format
  Result<String> call(String input, CipherParams params, {bool useEnvelope = true}) {
    try {
      // Validate input
      if (input.trim().isEmpty) {
        return const Failure(ValidationException('El texto de entrada no puede estar vacío'));
      }
      
      // Validate parameters based on method
      final validation = _validateParams(params);
      if (validation != null) {
        return Failure(ValidationException(validation));
      }
      
      // Perform encryption
      final result = _cipherRepository.encrypt(input, params);
      
      return result.fold(
        (encryptedText) {
          if (useEnvelope) {
            final envelope = Envelope.fromParams(params, encryptedText);
            return Success(envelope.serialize());
          } else {
            return Success(encryptedText);
          }
        },
        (error) => Failure(error),
      );
    } catch (e, stackTrace) {
      return Failure(CipherException('Error durante el cifrado: $e'), stackTrace);
    }
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
}
