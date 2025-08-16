package com.zarabandajose.transcripto.data.crypto.di;

import com.zarabandajose.transcripto.domain.repository.SaltRepository;
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
public final class CryptoModule_ProvideSaltRepositoryFactory implements Factory<SaltRepository> {
  @Override
  public SaltRepository get() {
    return provideSaltRepository();
  }

  public static CryptoModule_ProvideSaltRepositoryFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SaltRepository provideSaltRepository() {
    return Preconditions.checkNotNullFromProvides(CryptoModule.INSTANCE.provideSaltRepository());
  }

  private static final class InstanceHolder {
    private static final CryptoModule_ProvideSaltRepositoryFactory INSTANCE = new CryptoModule_ProvideSaltRepositoryFactory();
  }
}
