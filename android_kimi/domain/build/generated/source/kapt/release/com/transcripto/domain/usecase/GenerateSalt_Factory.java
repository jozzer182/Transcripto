package com.transcripto.domain.usecase;

import com.transcripto.domain.repository.SaltProvider;
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
public final class GenerateSalt_Factory implements Factory<GenerateSalt> {
  private final Provider<SaltProvider> saltProvider;

  public GenerateSalt_Factory(Provider<SaltProvider> saltProvider) {
    this.saltProvider = saltProvider;
  }

  @Override
  public GenerateSalt get() {
    return newInstance(saltProvider.get());
  }

  public static GenerateSalt_Factory create(Provider<SaltProvider> saltProvider) {
    return new GenerateSalt_Factory(saltProvider);
  }

  public static GenerateSalt newInstance(SaltProvider saltProvider) {
    return new GenerateSalt(saltProvider);
  }
}
