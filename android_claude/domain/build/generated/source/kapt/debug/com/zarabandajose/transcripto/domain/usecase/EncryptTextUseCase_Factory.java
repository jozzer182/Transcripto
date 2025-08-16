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
public final class EncryptTextUseCase_Factory implements Factory<EncryptTextUseCase> {
  private final Provider<CipherRepository> cipherRepositoryProvider;

  public EncryptTextUseCase_Factory(Provider<CipherRepository> cipherRepositoryProvider) {
    this.cipherRepositoryProvider = cipherRepositoryProvider;
  }

  @Override
  public EncryptTextUseCase get() {
    return newInstance(cipherRepositoryProvider.get());
  }

  public static EncryptTextUseCase_Factory create(
      Provider<CipherRepository> cipherRepositoryProvider) {
    return new EncryptTextUseCase_Factory(cipherRepositoryProvider);
  }

  public static EncryptTextUseCase newInstance(CipherRepository cipherRepository) {
    return new EncryptTextUseCase(cipherRepository);
  }
}
