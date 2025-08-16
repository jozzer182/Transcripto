package com.zarabandajose.transcripto.data.crypto.di;

import com.zarabandajose.transcripto.data.crypto.cipher.XorCipher;
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
public final class CryptoModule_ProvideXorCipherFactory implements Factory<XorCipher> {
  @Override
  public XorCipher get() {
    return provideXorCipher();
  }

  public static CryptoModule_ProvideXorCipherFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static XorCipher provideXorCipher() {
    return Preconditions.checkNotNullFromProvides(CryptoModule.INSTANCE.provideXorCipher());
  }

  private static final class InstanceHolder {
    private static final CryptoModule_ProvideXorCipherFactory INSTANCE = new CryptoModule_ProvideXorCipherFactory();
  }
}
