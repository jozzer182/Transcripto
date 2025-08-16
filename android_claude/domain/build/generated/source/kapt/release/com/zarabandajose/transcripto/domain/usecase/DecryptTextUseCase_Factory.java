package com.zarabandajose.transcripto.domain.usecase;

import com.zarabandajose.transcripto.domain.repository.CipherRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DecryptTextUseCase_Factory implements Factory<DecryptTextUseCase> {
  private final Provider<CipherRepository> cipherRepositoryProvider;

  private final Provider<DetectAndParseEnvelopeUseCase> detectAndParseEnvelopeUseCaseProvider;

  public DecryptTextUseCase_Factory(Provider<CipherRepository> cipherRepositoryProvider,
      Provider<DetectAndParseEnvelopeUseCase> detectAndParseEnvelopeUseCaseProvider) {
    this.cipherRepositoryProvider = cipherRepositoryProvider;
    this.detectAndParseEnvelopeUseCaseProvider = detectAndParseEnvelopeUseCaseProvider;
  }

  @Override
  public DecryptTextUseCase get() {
    return newInstance(cipherRepositoryProvider.get(), detectAndParseEnvelopeUseCaseProvider.get());
  }

  public static DecryptTextUseCase_Factory create(
      Provider<CipherRepository> cipherRepositoryProvider,
      Provider<DetectAndParseEnvelopeUseCase> detectAndParseEnvelopeUseCaseProvider) {
    return new DecryptTextUseCase_Factory(cipherRepositoryProvider, detectAndParseEnvelopeUseCaseProvider);
  }

  public static DecryptTextUseCase newInstance(CipherRepository cipherRepository,
      DetectAndParseEnvelopeUseCase detectAndParseEnvelopeUseCase) {
    return new DecryptTextUseCase(cipherRepository, detectAndParseEnvelopeUseCase);
  }
}
