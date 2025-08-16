package com.zarabandajose.transcripto.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class DetectAndParseEnvelopeUseCase_Factory implements Factory<DetectAndParseEnvelopeUseCase> {
  @Override
  public DetectAndParseEnvelopeUseCase get() {
    return newInstance();
  }

  public static DetectAndParseEnvelopeUseCase_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DetectAndParseEnvelopeUseCase newInstance() {
    return new DetectAndParseEnvelopeUseCase();
  }

  private static final class InstanceHolder {
    private static final DetectAndParseEnvelopeUseCase_Factory INSTANCE = new DetectAndParseEnvelopeUseCase_Factory();
  }
}
