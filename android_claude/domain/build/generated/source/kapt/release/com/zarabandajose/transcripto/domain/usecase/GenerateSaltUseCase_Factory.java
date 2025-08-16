package com.zarabandajose.transcripto.domain.usecase;

import com.zarabandajose.transcripto.domain.repository.SaltRepository;
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
public final class GenerateSaltUseCase_Factory implements Factory<GenerateSaltUseCase> {
  private final Provider<SaltRepository> saltRepositoryProvider;

  public GenerateSaltUseCase_Factory(Provider<SaltRepository> saltRepositoryProvider) {
    this.saltRepositoryProvider = saltRepositoryProvider;
  }

  @Override
  public GenerateSaltUseCase get() {
    return newInstance(saltRepositoryProvider.get());
  }

  public static GenerateSaltUseCase_Factory create(
      Provider<SaltRepository> saltRepositoryProvider) {
    return new GenerateSaltUseCase_Factory(saltRepositoryProvider);
  }

  public static GenerateSaltUseCase newInstance(SaltRepository saltRepository) {
    return new GenerateSaltUseCase(saltRepository);
  }
}
