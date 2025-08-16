package com.zarabandajose.transcripto.data.crypto.di;

import com.zarabandajose.transcripto.domain.provider.SaltProvider;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class CryptoModule_ProvideSaltProviderFactory implements Factory<SaltProvider> {
  private final Provider<SecureRandom> randomProvider;

  public CryptoModule_ProvideSaltProviderFactory(Provider<SecureRandom> randomProvider) {
    this.randomProvider = randomProvider;
  }

  @Override
  public SaltProvider get() {
    return provideSaltProvider(randomProvider.get());
  }

  public static CryptoModule_ProvideSaltProviderFactory create(
      Provider<SecureRandom> randomProvider) {
    return new CryptoModule_ProvideSaltProviderFactory(randomProvider);
  }

  public static SaltProvider provideSaltProvider(SecureRandom random) {
    return Preconditions.checkNotNullFromProvides(CryptoModule.INSTANCE.provideSaltProvider(random));
  }
}
