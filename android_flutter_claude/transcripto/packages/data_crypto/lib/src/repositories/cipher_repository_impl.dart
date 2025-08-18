import 'package:core_common/core_common.dart';
import 'package:domain/domain.dart';
import '../ciphers/caesar_cipher.dart';
import '../ciphers/base64_codec_impl.dart';
import '../ciphers/vigenere_cipher.dart';
import '../ciphers/xor_cipher.dart';

/// Implementation of cipher repository
class CipherRepositoryImpl implements CipherRepository {
  const CipherRepositoryImpl();
  
  static const _caesarCipher = CaesarCipher();
  static const _base64Codec = Base64CodecImpl();
  static const _vigenereCipher = VigenereCipher();
  static const _xorCipher = XorCipher();
  
  @override
  Result<String> encrypt(String input, CipherParams params) {
    try {
      final result = switch (params.method) {
        CipherMethod.caesar => _caesarCipher.encrypt(input, params),
        CipherMethod.base64 => _base64Codec.encrypt(input, params),
        CipherMethod.vigenere => _vigenereCipher.encrypt(input, params),
        CipherMethod.xor => _xorCipher.encrypt(input, params),
      };
      
      return Success(result);
    } catch (e, stackTrace) {
      return Failure(e, stackTrace);
    }
  }
  
  @override
  Result<String> decrypt(String input, CipherParams params) {
    try {
      final result = switch (params.method) {
        CipherMethod.caesar => _caesarCipher.decrypt(input, params),
        CipherMethod.base64 => _base64Codec.decrypt(input, params),
        CipherMethod.vigenere => _vigenereCipher.decrypt(input, params),
        CipherMethod.xor => _xorCipher.decrypt(input, params),
      };
      
      return Success(result);
    } catch (e, stackTrace) {
      return Failure(e, stackTrace);
    }
  }
}
