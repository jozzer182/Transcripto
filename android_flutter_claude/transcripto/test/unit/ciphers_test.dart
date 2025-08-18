import 'package:flutter_test/flutter_test.dart';
import 'package:domain/domain.dart';
import 'package:data_crypto/data_crypto.dart';
import 'package:core_common/core_common.dart';

void main() {
  group('Cipher Integration Tests', () {
    late CipherRepositoryImpl repository;

    setUp(() {
      repository = CipherRepositoryImpl();
    });

    test('should encrypt and decrypt text with Caesar cipher', () async {
      const params = CipherParams(
        method: CipherMethod.caesar,
        mode: CipherMode.encrypt,
        shift: 3,
        useSalt: false,
      );

      const input = 'HELLO WORLD';
      
      // Encrypt
      final encryptResult = repository.encrypt(input, params);
      expect(encryptResult.isLeft, true);
      
      String encrypted = '';
      encryptResult.fold(
        (result) => encrypted = result,
        (error) => fail('Expected successful encryption but got error: $error'),
      );
      
      expect(encrypted.isNotEmpty, true);

      // Decrypt
      final decryptParams = params.copyWith(mode: CipherMode.decrypt);
      final decryptResult = repository.decrypt(encrypted, decryptParams);
      expect(decryptResult.isLeft, true);
      
      decryptResult.fold(
        (result) => expect(result, input),
        (error) => fail('Expected successful decryption but got error: $error'),
      );
    });

    test('should encrypt and decrypt text with Base64 codec', () async {
      const params = CipherParams(
        method: CipherMethod.base64,
        mode: CipherMode.encrypt,
        useSalt: false,
      );

      const input = 'Hello, World!';
      
      // Encrypt (encode)
      final encryptResult = repository.processText(input, params);
      expect(encryptResult.isLeft, true);
      
      String encrypted = '';
      encryptResult.fold(
        (result) => encrypted = result.data,
        (error) => fail('Expected successful encoding but got error: $error'),
      );
      
      expect(encrypted, 'SGVsbG8sIFdvcmxkIQ==');

      // Decrypt (decode)
      final decryptParams = params.copyWith(mode: CipherMode.decrypt);
      final decryptResult = repository.processText(encrypted, decryptParams);
      expect(decryptResult.isLeft, true);
      
      decryptResult.fold(
        (result) => expect(result.data, input),
        (error) => fail('Expected successful decoding but got error: $error'),
      );
    });

    test('should encrypt and decrypt text with Vigenère cipher', () async {
      const params = CipherParams(
        method: CipherMethod.vigenere,
        mode: CipherMode.encrypt,
        key: 'SECRET',
        useSalt: false,
      );

      const input = 'HELLO WORLD';
      
      // Encrypt
      final encryptResult = repository.processText(input, params);
      expect(encryptResult.isLeft, true);
      
      String encrypted = '';
      encryptResult.fold(
        (result) => encrypted = result.data,
        (error) => fail('Expected successful encryption but got error: $error'),
      );
      
      expect(encrypted.isNotEmpty, true);
      expect(encrypted, isNot(equals(input)));

      // Decrypt
      final decryptParams = params.copyWith(mode: CipherMode.decrypt);
      final decryptResult = repository.processText(encrypted, decryptParams);
      expect(decryptResult.isLeft, true);
      
      decryptResult.fold(
        (result) => expect(result.data, input),
        (error) => fail('Expected successful decryption but got error: $error'),
      );
    });

    test('should encrypt and decrypt text with XOR cipher', () async {
      const params = CipherParams(
        method: CipherMethod.xor,
        mode: CipherMode.encrypt,
        key: 'mykey',
        useSalt: false,
      );

      const input = 'Hello, World!';
      
      // Encrypt
      final encryptResult = repository.processText(input, params);
      expect(encryptResult.isLeft, true);
      
      String encrypted = '';
      encryptResult.fold(
        (result) => encrypted = result.data,
        (error) => fail('Expected successful encryption but got error: $error'),
      );
      
      expect(encrypted.isNotEmpty, true);
      expect(encrypted, isNot(equals(input)));

      // Decrypt
      final decryptParams = params.copyWith(mode: CipherMode.decrypt);
      final decryptResult = repository.processText(encrypted, decryptParams);
      expect(decryptResult.isLeft, true);
      
      decryptResult.fold(
        (result) => expect(result.data, input),
        (error) => fail('Expected successful decryption but got error: $error'),
      );
    });

    test('should handle encryption with salt', () async {
      final saltProvider = SaltProvider();
      final salt = saltProvider.generateSalt();
      
      const params = CipherParams(
        method: CipherMethod.caesar,
        mode: CipherMode.encrypt,
        shift: 5,
        useSalt: true,
        salt: [1, 2, 3, 4, 5, 6, 7, 8], // Fixed salt for testing
      );

      const input = 'TEST MESSAGE';
      
      // Encrypt
      final encryptResult = repository.processText(input, params);
      expect(encryptResult.isLeft, true);
      
      String encrypted = '';
      encryptResult.fold(
        (result) => encrypted = result.data,
        (error) => fail('Expected successful encryption but got error: $error'),
      );
      
      expect(encrypted.isNotEmpty, true);
      
      // Should contain envelope format with salt
      expect(encrypted.contains('method='), true);
      expect(encrypted.contains('salt='), true);
      expect(encrypted.contains('payload='), true);
    });

    test('should validate required key for key-based ciphers', () async {
      const params = CipherParams(
        method: CipherMethod.vigenere,
        mode: CipherMode.encrypt,
        key: '', // Empty key should cause error
        useSalt: false,
      );

      const input = 'TEST MESSAGE';
      
      final result = repository.processText(input, params);
      expect(result.isRight, true);
      
      result.fold(
        (success) => fail('Expected error but got success: ${success.data}'),
        (error) => expect(error, isA<InvalidKeyException>()),
      );
    });
  });

  group('Envelope Tests', () {
    test('should parse valid envelope format', () {
      const envelopeString = 'method=caesar;salt=AQIDBAU=;payload=KHOOR ZRUOG';
      
      final envelope = Envelope.parse(envelopeString);
      expect(envelope, isNotNull);
      expect(envelope!.method, CipherMethod.caesar);
      expect(envelope.salt, [1, 2, 3, 4, 5]);
      expect(envelope.payload, 'KHOOR ZRUOG');
    });

    test('should serialize envelope to string format', () {
      final envelope = Envelope(
        method: CipherMethod.caesar,
        salt: [1, 2, 3, 4, 5],
        payload: 'KHOOR ZRUOG',
      );
      
      final serialized = envelope.serialize();
      expect(serialized, 'method=caesar;salt=AQIDBAU=;payload=KHOOR ZRUOG');
    });

    test('should handle envelope without salt', () {
      const envelopeString = 'method=base64;salt=;payload=SGVsbG8gV29ybGQ=';
      
      final envelope = Envelope.parse(envelopeString);
      expect(envelope, isNotNull);
      expect(envelope!.method, CipherMethod.base64);
      expect(envelope.salt, isNull);
      expect(envelope.payload, 'SGVsbG8gV29ybGQ=');
    });

    test('should return null for invalid envelope format', () {
      const invalidEnvelope = 'invalid format';
      
      final envelope = Envelope.parse(invalidEnvelope);
      expect(envelope, isNull);
    });
  });
}
