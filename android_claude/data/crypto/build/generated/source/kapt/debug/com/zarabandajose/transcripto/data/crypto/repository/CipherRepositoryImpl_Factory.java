package com.zarabandajose.transcripto.data.crypto.repository;

import com.zarabandajose.transcripto.data.crypto.cipher.Base64Codec;
import com.zarabandajose.transcripto.data.crypto.cipher.CaesarCipher;
import com.zarabandajose.transcripto.data.crypto.cipher.VigenereCipher;
import com.zarabandajose.transcripto.data.crypto.cipher.XorCipher;
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
public final class CipherRepositoryImpl_Factory implements Factory<CipherRepositoryImpl> {
  private final Provider<CaesarCipher> caesarCipherProvider;

  private final Provider<Base64Codec> base64CodecProvider;

  private final Provider<VigenereCipher> vigenereCipherProvider;

  private final Provider<XorCipher> xorCipherProvider;

  public CipherRepositoryImpl_Factory(Provider<CaesarCipher> caesarCipherProvider,
      Provider<Base64Codec> base64CodecProvider, Provider<VigenereCipher> vigenereCipherProvider,
      Provider<XorCipher> xorCipherProvider) {
    this.caesarCipherProvider = caesarCipherProvider;
    this.base64CodecProvider = base64CodecProvider;
    this.vigenereCipherProvider = vigenereCipherProvider;
    this.xorCipherProvider = xorCipherProvider;
  }

  @Override
  public CipherRepositoryImpl get() {
    return newInstance(caesarCipherProvider.get(), base64CodecProvider.get(), vigenereCipherProvider.get(), xorCipherProvider.get());
  }

  public static CipherRepositoryImpl_Factory create(Provider<CaesarCipher> caesarCipherProvider,
      Provider<Base64Codec> base64CodecProvider, Provider<VigenereCipher> vigenereCipherProvider,
      Provider<XorCipher> xorCipherProvider) {
    return new CipherRepositoryImpl_Factory(caesarCipherProvider, base64CodecProvider, vigenereCipherProvider, xorCipherProvider);
  }

  public static CipherRepositoryImpl newInstance(CaesarCipher caesarCipher, Base64Codec base64Codec,
      VigenereCipher vigenereCipher, XorCipher xorCipher) {
    return new CipherRepositoryImpl(caesarCipher, base64Codec, vigenereCipher, xorCipher);
  }
}
