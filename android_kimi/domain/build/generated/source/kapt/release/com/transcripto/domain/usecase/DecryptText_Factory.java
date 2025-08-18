package com.transcripto.domain.usecase;

import com.transcripto.domain.model.CipherMethod;
import com.transcripto.domain.repository.EnvelopeParser;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import java.util.Map;
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
  private final Provider<Map<CipherMethod, CipherMethod>> cipherMethodsProvider;

  private final Provider<EnvelopeParser> envelopeParserProvider;

  public DecryptText_Factory(Provider<Map<CipherMethod, CipherMethod>> cipherMethodsProvider,
      Provider<EnvelopeParser> envelopeParserProvider) {
    this.cipherMethodsProvider = cipherMethodsProvider;
    this.envelopeParserProvider = envelopeParserProvider;
  }

  @Override
  public DecryptText get() {
    return newInstance(cipherMethodsProvider.get(), envelopeParserProvider.get());
  }

  public static DecryptText_Factory create(
      Provider<Map<CipherMethod, CipherMethod>> cipherMethodsProvider,
      Provider<EnvelopeParser> envelopeParserProvider) {
    return new DecryptText_Factory(cipherMethodsProvider, envelopeParserProvider);
  }

  public static DecryptText newInstance(Map<CipherMethod, CipherMethod> cipherMethods,
      EnvelopeParser envelopeParser) {
    return new DecryptText(cipherMethods, envelopeParser);
  }
}
