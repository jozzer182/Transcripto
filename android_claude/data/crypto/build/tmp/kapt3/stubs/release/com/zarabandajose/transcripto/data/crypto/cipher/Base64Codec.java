package com.zarabandajose.transcripto.data.crypto.cipher;

/**
 * Implementación del codec Base64.
 * Utiliza java.util.Base64 para encoding/decoding con soporte UTF-8.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bJ\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b\u00a8\u0006\n"}, d2 = {"Lcom/zarabandajose/transcripto/data/crypto/cipher/Base64Codec;", "", "()V", "decrypt", "Lcom/zarabandajose/transcripto/core/common/Result;", "", "input", "params", "Lcom/zarabandajose/transcripto/domain/model/CipherParams;", "encrypt", "crypto_release"})
public final class Base64Codec {
    
    public Base64Codec() {
        super();
    }
    
    /**
     * Codifica texto a Base64, con soporte para salt.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.zarabandajose.transcripto.core.common.Result<java.lang.String> encrypt(@org.jetbrains.annotations.NotNull()
    java.lang.String input, @org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.domain.model.CipherParams params) {
        return null;
    }
    
    /**
     * Decodifica texto desde Base64, removiendo salt si está presente.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.zarabandajose.transcripto.core.common.Result<java.lang.String> decrypt(@org.jetbrains.annotations.NotNull()
    java.lang.String input, @org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.domain.model.CipherParams params) {
        return null;
    }
}