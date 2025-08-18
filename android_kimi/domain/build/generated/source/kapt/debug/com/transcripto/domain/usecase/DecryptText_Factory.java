package com.transcripto.domain.usecase;

import com.transcripto.domain.repository.CipherRepository;
import com.transcripto.domain.repository.EnvelopeParser;
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
public final class DecryptText_Factory implements Factory<DecryptText> {
  private final Provider<CipherRepository> cipherRepositoryProvider;

  private final Provider<EnvelopeParser> envelopeParserProvider;

  public DecryptText_Factory(Provider<CipherRepository> cipherRepositoryProvider,
      Provider<EnvelopeParser> envelopeParserProvider) {
    this.cipherRepositoryProvider = cipherRepositoryProvider;
    this.envelopeParserProvider = envelopeParserProvider;
  }

  @Override
  public DecryptText get() {
    return newInstance(cipherRepositoryProvider.get(), envelopeParserProvider.get());
  }

  public static DecryptText_Factory create(Provider<CipherRepository> cipherRepositoryProvider,
      Provider<EnvelopeParser> envelopeParserProvider) {
    return new DecryptText_Factory(cipherRepositoryProvider, envelopeParserProvider);
  }

  public static DecryptText newInstance(CipherRepository cipherRepository,
      EnvelopeParser envelopeParser) {
    return new DecryptText(cipherRepository, envelopeParser);
  }
}
