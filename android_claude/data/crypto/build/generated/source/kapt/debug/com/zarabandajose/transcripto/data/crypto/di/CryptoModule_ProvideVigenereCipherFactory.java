package com.zarabandajose.transcripto.data.crypto.di;

import com.zarabandajose.transcripto.data.crypto.cipher.VigenereCipher;
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
public final class CryptoModule_ProvideVigenereCipherFactory implements Factory<VigenereCipher> {
  @Override
  public VigenereCipher get() {
    return provideVigenereCipher();
  }

  public static CryptoModule_ProvideVigenereCipherFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static VigenereCipher provideVigenereCipher() {
    return Preconditions.checkNotNullFromProvides(CryptoModule.INSTANCE.provideVigenereCipher());
  }

  private static final class InstanceHolder {
    private static final CryptoModule_ProvideVigenereCipherFactory INSTANCE = new CryptoModule_ProvideVigenereCipherFactory();
  }
}
