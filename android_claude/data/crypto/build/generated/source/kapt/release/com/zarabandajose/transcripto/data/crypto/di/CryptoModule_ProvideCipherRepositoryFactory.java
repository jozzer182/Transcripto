package com.zarabandajose.transcripto.data.crypto.di;

import com.zarabandajose.transcripto.data.crypto.cipher.Base64Codec;
import com.zarabandajose.transcripto.data.crypto.cipher.CaesarCipher;
import com.zarabandajose.transcripto.data.crypto.cipher.VigenereCipher;
import com.zarabandajose.transcripto.data.crypto.cipher.XorCipher;
import com.zarabandajose.transcripto.domain.repository.CipherRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
    "KotlinInternalInJava"
})
public final class CryptoModule_ProvideCipherRepositoryFactory implements Factory<CipherRepository> {
  private final Provider<CaesarCipher> caesarCipherProvider;

  private final Provider<Base64Codec> base64CodecProvider;

  private final Provider<VigenereCipher> vigenereCipherProvider;

  private final Provider<XorCipher> xorCipherProvider;

  public CryptoModule_ProvideCipherRepositoryFactory(Provider<CaesarCipher> caesarCipherProvider,
      Provider<Base64Codec> base64CodecProvider, Provider<VigenereCipher> vigenereCipherProvider,
      Provider<XorCipher> xorCipherProvider) {
    this.caesarCipherProvider = caesarCipherProvider;
    this.base64CodecProvider = base64CodecProvider;
    this.vigenereCipherProvider = vigenereCipherProvider;
    this.xorCipherProvider = xorCipherProvider;
  }

  @Override
  public CipherRepository get() {
    return provideCipherRepository(caesarCipherProvider.get(), base64CodecProvider.get(), vigenereCipherProvider.get(), xorCipherProvider.get());
  }

  public static CryptoModule_ProvideCipherRepositoryFactory create(
      Provider<CaesarCipher> caesarCipherProvider, Provider<Base64Codec> base64CodecProvider,
      Provider<VigenereCipher> vigenereCipherProvider, Provider<XorCipher> xorCipherProvider) {
    return new CryptoModule_ProvideCipherRepositoryFactory(caesarCipherProvider, base64CodecProvider, vigenereCipherProvider, xorCipherProvider);
  }

  public static CipherRepository provideCipherRepository(CaesarCipher caesarCipher,
      Base64Codec base64Codec, VigenereCipher vigenereCipher, XorCipher xorCipher) {
    return Preconditions.checkNotNullFromProvides(CryptoModule.INSTANCE.provideCipherRepository(caesarCipher, base64Codec, vigenereCipher, xorCipher));
  }
}
