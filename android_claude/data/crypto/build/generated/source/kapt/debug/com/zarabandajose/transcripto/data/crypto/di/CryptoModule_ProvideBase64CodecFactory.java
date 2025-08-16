package com.zarabandajose.transcripto.data.crypto.di;

import com.zarabandajose.transcripto.data.crypto.cipher.Base64Codec;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class CryptoModule_ProvideBase64CodecFactory implements Factory<Base64Codec> {
  @Override
  public Base64Codec get() {
    return provideBase64Codec();
  }

  public static CryptoModule_ProvideBase64CodecFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static Base64Codec provideBase64Codec() {
    return Preconditions.checkNotNullFromProvides(CryptoModule.INSTANCE.provideBase64Codec());
  }

  private static final class InstanceHolder {
    private static final CryptoModule_ProvideBase64CodecFactory INSTANCE = new CryptoModule_ProvideBase64CodecFactory();
  }
}
