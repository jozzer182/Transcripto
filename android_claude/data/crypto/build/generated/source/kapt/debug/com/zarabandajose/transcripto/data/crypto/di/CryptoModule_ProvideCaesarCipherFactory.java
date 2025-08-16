package com.zarabandajose.transcripto.data.crypto.di;

import com.zarabandajose.transcripto.data.crypto.cipher.CaesarCipher;
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
public final class CryptoModule_ProvideCaesarCipherFactory implements Factory<CaesarCipher> {
  @Override
  public CaesarCipher get() {
    return provideCaesarCipher();
  }

  public static CryptoModule_ProvideCaesarCipherFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CaesarCipher provideCaesarCipher() {
    return Preconditions.checkNotNullFromProvides(CryptoModule.INSTANCE.provideCaesarCipher());
  }

  private static final class InstanceHolder {
    private static final CryptoModule_ProvideCaesarCipherFactory INSTANCE = new CryptoModule_ProvideCaesarCipherFactory();
  }
}
