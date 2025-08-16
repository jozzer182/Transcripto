package com.zarabandajose.transcripto.data.crypto.provider;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/zarabandajose/transcripto/data/crypto/provider/SecureRandomSaltProvider;", "Lcom/zarabandajose/transcripto/domain/provider/SaltProvider;", "random", "Ljava/security/SecureRandom;", "(Ljava/security/SecureRandom;)V", "nextSalt", "", "crypto_debug"})
public final class SecureRandomSaltProvider implements com.zarabandajose.transcripto.domain.provider.SaltProvider {
    @org.jetbrains.annotations.NotNull()
    private final java.security.SecureRandom random = null;
    
    @javax.inject.Inject()
    public SecureRandomSaltProvider(@org.jetbrains.annotations.NotNull()
    java.security.SecureRandom random) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public byte[] nextSalt() {
        return null;
    }
}