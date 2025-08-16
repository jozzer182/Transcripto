package com.zarabandajose.transcripto.data.crypto.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class SaltRepositoryImpl_Factory implements Factory<SaltRepositoryImpl> {
  @Override
  public SaltRepositoryImpl get() {
    return newInstance();
  }

  public static SaltRepositoryImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SaltRepositoryImpl newInstance() {
    return new SaltRepositoryImpl();
  }

  private static final class InstanceHolder {
    private static final SaltRepositoryImpl_Factory INSTANCE = new SaltRepositoryImpl_Factory();
  }
}
