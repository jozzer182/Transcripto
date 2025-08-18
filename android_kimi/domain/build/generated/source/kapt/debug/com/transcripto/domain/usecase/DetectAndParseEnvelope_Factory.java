package com.transcripto.domain.usecase;

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
public final class DetectAndParseEnvelope_Factory implements Factory<DetectAndParseEnvelope> {
  private final Provider<EnvelopeParser> envelopeParserProvider;

  public DetectAndParseEnvelope_Factory(Provider<EnvelopeParser> envelopeParserProvider) {
    this.envelopeParserProvider = envelopeParserProvider;
  }

  @Override
  public DetectAndParseEnvelope get() {
    return newInstance(envelopeParserProvider.get());
  }

  public static DetectAndParseEnvelope_Factory create(
      Provider<EnvelopeParser> envelopeParserProvider) {
    return new DetectAndParseEnvelope_Factory(envelopeParserProvider);
  }

  public static DetectAndParseEnvelope newInstance(EnvelopeParser envelopeParser) {
    return new DetectAndParseEnvelope(envelopeParser);
  }
}
