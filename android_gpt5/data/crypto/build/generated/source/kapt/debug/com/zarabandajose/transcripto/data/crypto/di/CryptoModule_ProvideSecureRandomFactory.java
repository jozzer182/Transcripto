package com.zarabandajose.transcripto.data.crypto.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import java.security.SecureRandom;
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
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class CryptoModule_ProvideSecureRandomFactory implements Factory<SecureRandom> {
  @Override
  public SecureRandom get() {
    return provideSecureRandom();
  }

  public static CryptoModule_ProvideSecureRandomFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SecureRandom provideSecureRandom() {
    return Preconditions.checkNotNullFromProvides(CryptoModule.INSTANCE.provideSecureRandom());
  }

  private static final class InstanceHolder {
    private static final CryptoModule_ProvideSecureRandomFactory INSTANCE = new CryptoModule_ProvideSecureRandomFactory();
  }
}
