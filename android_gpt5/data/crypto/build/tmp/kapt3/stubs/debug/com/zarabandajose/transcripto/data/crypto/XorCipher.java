package com.zarabandajose.transcripto.data.crypto;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000eH\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/zarabandajose/transcripto/data/crypto/XorCipher;", "Lcom/zarabandajose/transcripto/domain/cipher/CipherMethod;", "()V", "method", "Lcom/zarabandajose/transcripto/domain/model/Method;", "getMethod", "()Lcom/zarabandajose/transcripto/domain/model/Method;", "decrypt", "", "input", "params", "Lcom/zarabandajose/transcripto/domain/cipher/CipherMethod$Params;", "encrypt", "xorBytes", "", "message", "key", "crypto_debug"})
public final class XorCipher implements com.zarabandajose.transcripto.domain.cipher.CipherMethod {
    @org.jetbrains.annotations.NotNull()
    private final com.zarabandajose.transcripto.domain.model.Method method = com.zarabandajose.transcripto.domain.model.Method.XOR;
    
    public XorCipher() {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.zarabandajose.transcripto.domain.model.Method getMethod() {
        return null;
    }
    
    private final byte[] xorBytes(byte[] message, byte[] key) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String encrypt(@org.jetbrains.annotations.NotNull()
    java.lang.String input, @org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.domain.cipher.CipherMethod.Params params) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String decrypt(@org.jetbrains.annotations.NotNull()
    java.lang.String input, @org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.domain.cipher.CipherMethod.Params params) {
        return null;
    }
}