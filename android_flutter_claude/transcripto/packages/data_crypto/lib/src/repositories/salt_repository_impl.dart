import 'package:core_common/core_common.dart';
import 'package:domain/domain.dart';
import '../providers/salt_provider.dart';

/// Implementation of salt repository
class SaltRepositoryImpl implements SaltRepository {
  const SaltRepositoryImpl();
  
  static const _saltProvider = SaltProvider();
  
  @override
  Result<List<int>> generateSalt([int length = 12]) {
    try {
      final salt = _saltProvider.generateSalt(length);
      return Success(salt);
    } catch (e, stackTrace) {
      return Failure(e, stackTrace);
    }
  }
  
  @override
  Result<List<int>> validateSalt(String saltInput) {
    try {
      final salt = _saltProvider.parseSalt(saltInput);
      return Success(salt);
    } catch (e, stackTrace) {
      return Failure(e, stackTrace);
    }
  }
}
