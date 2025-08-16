package com.zarabandajose.transcripto.data.crypto.repository;

/**
 * Implementación del repositorio de cifrado.
 * Delega las operaciones a los diferentes cifradores según el método seleccionado.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ$\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0096@\u00a2\u0006\u0002\u0010\u0011J$\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0096@\u00a2\u0006\u0002\u0010\u0011R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/zarabandajose/transcripto/data/crypto/repository/CipherRepositoryImpl;", "Lcom/zarabandajose/transcripto/domain/repository/CipherRepository;", "caesarCipher", "Lcom/zarabandajose/transcripto/data/crypto/cipher/CaesarCipher;", "base64Codec", "Lcom/zarabandajose/transcripto/data/crypto/cipher/Base64Codec;", "vigenereCipher", "Lcom/zarabandajose/transcripto/data/crypto/cipher/VigenereCipher;", "xorCipher", "Lcom/zarabandajose/transcripto/data/crypto/cipher/XorCipher;", "(Lcom/zarabandajose/transcripto/data/crypto/cipher/CaesarCipher;Lcom/zarabandajose/transcripto/data/crypto/cipher/Base64Codec;Lcom/zarabandajose/transcripto/data/crypto/cipher/VigenereCipher;Lcom/zarabandajose/transcripto/data/crypto/cipher/XorCipher;)V", "decrypt", "Lcom/zarabandajose/transcripto/core/common/Result;", "", "input", "params", "Lcom/zarabandajose/transcripto/domain/model/CipherParams;", "(Ljava/lang/String;Lcom/zarabandajose/transcripto/domain/model/CipherParams;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "encrypt", "crypto_debug"})
public final class CipherRepositoryImpl implements com.zarabandajose.transcripto.domain.repository.CipherRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.zarabandajose.transcripto.data.crypto.cipher.CaesarCipher caesarCipher = null;
    @org.jetbrains.annotations.NotNull()
    private final com.zarabandajose.transcripto.data.crypto.cipher.Base64Codec base64Codec = null;
    @org.jetbrains.annotations.NotNull()
    private final com.zarabandajose.transcripto.data.crypto.cipher.VigenereCipher vigenereCipher = null;
    @org.jetbrains.annotations.NotNull()
    private final com.zarabandajose.transcripto.data.crypto.cipher.XorCipher xorCipher = null;
    
    @javax.inject.Inject()
    public CipherRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.data.crypto.cipher.CaesarCipher caesarCipher, @org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.data.crypto.cipher.Base64Codec base64Codec, @org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.data.crypto.cipher.VigenereCipher vigenereCipher, @org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.data.crypto.cipher.XorCipher xorCipher) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object encrypt(@org.jetbrains.annotations.NotNull()
    java.lang.String input, @org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.domain.model.CipherParams params, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.zarabandajose.transcripto.core.common.Result<java.lang.String>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object decrypt(@org.jetbrains.annotations.NotNull()
    java.lang.String input, @org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.domain.model.CipherParams params, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.zarabandajose.transcripto.core.common.Result<java.lang.String>> $completion) {
        return null;
    }
}