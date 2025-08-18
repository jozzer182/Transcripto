import 'package:core_common/core_common.dart';
import '../repositories/salt_repository.dart';

/// Use case for generating salt
class GenerateSalt {
  const GenerateSalt(this._saltRepository);
  
  final SaltRepository _saltRepository;
  
  /// Generates a random salt of the specified length
  Result<List<int>> call([int length = 12]) {
    try {
      if (length < 8 || length > 16) {
        return const Failure(ValidationException('La longitud del salt debe estar entre 8 y 16 bytes'));
      }
      
      return _saltRepository.generateSalt(length);
    } catch (e, stackTrace) {
      return Failure(Exception('Error al generar salt: $e'), stackTrace);
    }
  }
}
