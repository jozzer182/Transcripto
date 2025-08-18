package com.transcripto.domain.usecase;

import com.transcripto.domain.model.CipherMethod;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import java.util.Map;
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
public final class EncryptText_Factory implements Factory<EncryptText> {
  private final Provider<Map<CipherMethod, CipherMethod>> cipherMethodsProvider;

  public EncryptText_Factory(Provider<Map<CipherMethod, CipherMethod>> cipherMethodsProvider) {
    this.cipherMethodsProvider = cipherMethodsProvider;
  }

  @Override
  public EncryptText get() {
    return newInstance(cipherMethodsProvider.get());
  }

  public static EncryptText_Factory create(
      Provider<Map<CipherMethod, CipherMethod>> cipherMethodsProvider) {
    return new EncryptText_Factory(cipherMethodsProvider);
  }

  public static EncryptText newInstance(Map<CipherMethod, CipherMethod> cipherMethods) {
    return new EncryptText(cipherMethods);
  }
}
