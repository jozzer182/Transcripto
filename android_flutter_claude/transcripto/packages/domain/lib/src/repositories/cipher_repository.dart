import 'package:core_common/core_common.dart';
import '../entities/cipher_params.dart';

/// Abstract repository for cipher operations
abstract class CipherRepository {
  /// Encrypts text using the specified parameters
  Result<String> encrypt(String input, CipherParams params);
  
  /// Decrypts text using the specified parameters
  Result<String> decrypt(String input, CipherParams params);
}
