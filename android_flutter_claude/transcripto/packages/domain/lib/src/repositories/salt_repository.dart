import 'package:core_common/core_common.dart';

/// Abstract repository for salt operations
abstract class SaltRepository {
  /// Generates a random salt of the specified length
  Result<List<int>> generateSalt([int length = 12]);
  
  /// Validates salt format and length
  Result<List<int>> validateSalt(String saltInput);
}
