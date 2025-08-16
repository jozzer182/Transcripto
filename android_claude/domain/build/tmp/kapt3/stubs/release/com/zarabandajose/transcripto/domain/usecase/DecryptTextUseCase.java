package com.zarabandajose.transcripto.domain.usecase;

/**
 * Caso de uso para descifrar texto.
 * Maneja tanto texto en formato envelope como texto directo.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J$\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0086B\u00a2\u0006\u0002\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/zarabandajose/transcripto/domain/usecase/DecryptTextUseCase;", "", "cipherRepository", "Lcom/zarabandajose/transcripto/domain/repository/CipherRepository;", "detectAndParseEnvelopeUseCase", "Lcom/zarabandajose/transcripto/domain/usecase/DetectAndParseEnvelopeUseCase;", "(Lcom/zarabandajose/transcripto/domain/repository/CipherRepository;Lcom/zarabandajose/transcripto/domain/usecase/DetectAndParseEnvelopeUseCase;)V", "invoke", "Lcom/zarabandajose/transcripto/core/common/Result;", "", "input", "params", "Lcom/zarabandajose/transcripto/domain/model/CipherParams;", "(Ljava/lang/String;Lcom/zarabandajose/transcripto/domain/model/CipherParams;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "domain_release"})
public final class DecryptTextUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.zarabandajose.transcripto.domain.repository.CipherRepository cipherRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.zarabandajose.transcripto.domain.usecase.DetectAndParseEnvelopeUseCase detectAndParseEnvelopeUseCase = null;
    
    @javax.inject.Inject()
    public DecryptTextUseCase(@org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.domain.repository.CipherRepository cipherRepository, @org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.domain.usecase.DetectAndParseEnvelopeUseCase detectAndParseEnvelopeUseCase) {
        super();
    }
    
    /**
     * Descifra un texto. Primero intenta detectar si está en formato envelope,
     * si no, usa los parámetros proporcionados.
     *
     * @param input Texto a descifrar
     * @param params Parámetros de descifrado (usados si no hay envelope)
     * @return Result con el texto descifrado o error
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object invoke(@org.jetbrains.annotations.NotNull()
    java.lang.String input, @org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.domain.model.CipherParams params, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.zarabandajose.transcripto.core.common.Result<java.lang.String>> $completion) {
        return null;
    }
}