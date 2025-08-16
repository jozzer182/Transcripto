package com.zarabandajose.transcripto.data.crypto.provider;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import java.security.SecureRandom;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class SecureRandomSaltProvider_Factory implements Factory<SecureRandomSaltProvider> {
  private final Provider<SecureRandom> randomProvider;

  public SecureRandomSaltProvider_Factory(Provider<SecureRandom> randomProvider) {
    this.randomProvider = randomProvider;
  }

  @Override
  public SecureRandomSaltProvider get() {
    return newInstance(randomProvider.get());
  }

  public static SecureRandomSaltProvider_Factory create(Provider<SecureRandom> randomProvider) {
    return new SecureRandomSaltProvider_Factory(randomProvider);
  }

  public static SecureRandomSaltProvider newInstance(SecureRandom random) {
    return new SecureRandomSaltProvider(random);
  }
}
