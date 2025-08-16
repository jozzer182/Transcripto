package com.zarabandajose.transcripto.data.crypto.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\b\u0010\u000b\u001a\u00020\nH\u0007\u00a8\u0006\f"}, d2 = {"Lcom/zarabandajose/transcripto/data/crypto/di/CryptoModule;", "", "()V", "provideCipherMap", "", "Lcom/zarabandajose/transcripto/domain/model/Method;", "Lcom/zarabandajose/transcripto/domain/cipher/CipherMethod;", "provideSaltProvider", "Lcom/zarabandajose/transcripto/domain/provider/SaltProvider;", "random", "Ljava/security/SecureRandom;", "provideSecureRandom", "crypto_debug"})
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
    public final java.util.Map<com.zarabandajose.transcripto.domain.model.Method, com.zarabandajose.transcripto.domain.cipher.CipherMethod> provideCipherMap() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.zarabandajose.transcripto.domain.provider.SaltProvider provideSaltProvider(@org.jetbrains.annotations.NotNull()
    java.security.SecureRandom random) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final java.security.SecureRandom provideSecureRandom() {
        return null;
    }
}