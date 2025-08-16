package com.zarabandajose.transcripto.data.crypto.di;

import com.zarabandajose.transcripto.domain.cipher.CipherMethod;
import com.zarabandajose.transcripto.domain.model.Method;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import java.util.Map;
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
public final class CryptoModule_ProvideCipherMapFactory implements Factory<Map<Method, CipherMethod>> {
  @Override
  public Map<Method, CipherMethod> get() {
    return provideCipherMap();
  }

  public static CryptoModule_ProvideCipherMapFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static Map<Method, CipherMethod> provideCipherMap() {
    return Preconditions.checkNotNullFromProvides(CryptoModule.INSTANCE.provideCipherMap());
  }

  private static final class InstanceHolder {
    private static final CryptoModule_ProvideCipherMapFactory INSTANCE = new CryptoModule_ProvideCipherMapFactory();
  }
}
