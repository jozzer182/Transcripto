package com.zarabandajose.transcripto.data.crypto.di;

/**
 * Módulo de Hilt para las dependencias del módulo crypto.
 */
@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007J\b\u0010\u0005\u001a\u00020\u0006H\u0007J(\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\b\u0010\u000f\u001a\u00020\u0010H\u0007J\b\u0010\u0011\u001a\u00020\fH\u0007J\b\u0010\u0012\u001a\u00020\u000eH\u0007\u00a8\u0006\u0013"}, d2 = {"Lcom/zarabandajose/transcripto/data/crypto/di/CryptoModule;", "", "()V", "provideBase64Codec", "Lcom/zarabandajose/transcripto/data/crypto/cipher/Base64Codec;", "provideCaesarCipher", "Lcom/zarabandajose/transcripto/data/crypto/cipher/CaesarCipher;", "provideCipherRepository", "Lcom/zarabandajose/transcripto/domain/repository/CipherRepository;", "caesarCipher", "base64Codec", "vigenereCipher", "Lcom/zarabandajose/transcripto/data/crypto/cipher/VigenereCipher;", "xorCipher", "Lcom/zarabandajose/transcripto/data/crypto/cipher/XorCipher;", "provideSaltRepository", "Lcom/zarabandajose/transcripto/domain/repository/SaltRepository;", "provideVigenereCipher", "provideXorCipher", "crypto_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class CryptoModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.zarabandajose.transcripto.data.crypto.di.CryptoModule INSTANCE = null;
    
    private CryptoModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.zarabandajose.transcripto.data.crypto.cipher.CaesarCipher provideCaesarCipher() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.zarabandajose.transcripto.data.crypto.cipher.Base64Codec provideBase64Codec() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.zarabandajose.transcripto.data.crypto.cipher.VigenereCipher provideVigenereCipher() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.zarabandajose.transcripto.data.crypto.cipher.XorCipher provideXorCipher() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.zarabandajose.transcripto.domain.repository.CipherRepository provideCipherRepository(@org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.data.crypto.cipher.CaesarCipher caesarCipher, @org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.data.crypto.cipher.Base64Codec base64Codec, @org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.data.crypto.cipher.VigenereCipher vigenereCipher, @org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.data.crypto.cipher.XorCipher xorCipher) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.zarabandajose.transcripto.domain.repository.SaltRepository provideSaltRepository() {
        return null;
    }
}