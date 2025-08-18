package com.transcripto.domain.usecase;

import com.transcripto.domain.repository.CipherRepository;
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
public final class EncryptText_Factory implements Factory<EncryptText> {
  private final Provider<CipherRepository> cipherRepositoryProvider;

  public EncryptText_Factory(Provider<CipherRepository> cipherRepositoryProvider) {
    this.cipherRepositoryProvider = cipherRepositoryProvider;
  }

  @Override
  public EncryptText get() {
    return newInstance(cipherRepositoryProvider.get());
  }

  public static EncryptText_Factory create(Provider<CipherRepository> cipherRepositoryProvider) {
    return new EncryptText_Factory(cipherRepositoryProvider);
  }

  public static EncryptText newInstance(CipherRepository cipherRepository) {
    return new EncryptText(cipherRepository);
  }
}
