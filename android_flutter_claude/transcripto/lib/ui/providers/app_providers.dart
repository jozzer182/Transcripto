import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:domain/domain.dart';
import 'package:data_crypto/data_crypto.dart';

// Repository providers
final cipherRepositoryProvider = Provider<CipherRepository>((ref) {
  return const CipherRepositoryImpl();
});

final saltRepositoryProvider = Provider<SaltRepository>((ref) {
  return const SaltRepositoryImpl();
});

// Use case providers
final encryptTextProvider = Provider<EncryptText>((ref) {
  final repository = ref.watch(cipherRepositoryProvider);
  return EncryptText(repository);
});

final decryptTextProvider = Provider<DecryptText>((ref) {
  final repository = ref.watch(cipherRepositoryProvider);
  return DecryptText(repository);
});

final detectAndParseEnvelopeProvider = Provider<DetectAndParseEnvelope>((ref) {
  return const DetectAndParseEnvelope();
});

final generateSaltProvider = Provider<GenerateSalt>((ref) {
  final repository = ref.watch(saltRepositoryProvider);
  return GenerateSalt(repository);
});
