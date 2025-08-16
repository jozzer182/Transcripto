package com.zarabandajose.transcripto.domain.repository;

/**
 * Interfaz para operaciones de cifrado.
 * Define los contratos que deben implementar los diferentes métodos de cifrado.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J$\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u00a6@\u00a2\u0006\u0002\u0010\bJ$\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u00a6@\u00a2\u0006\u0002\u0010\b\u00a8\u0006\n"}, d2 = {"Lcom/zarabandajose/transcripto/domain/repository/CipherRepository;", "", "decrypt", "Lcom/zarabandajose/transcripto/core/common/Result;", "", "input", "params", "Lcom/zarabandajose/transcripto/domain/model/CipherParams;", "(Ljava/lang/String;Lcom/zarabandajose/transcripto/domain/model/CipherParams;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "encrypt", "domain_debug"})
public abstract interface CipherRepository {
    
    /**
     * Cifra un texto usando los parámetros especificados.
     *
     * @param input Texto a cifrar
     * @param params Parámetros de cifrado
     * @return Result con el texto cifrado o error
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object encrypt(@org.jetbrains.annotations.NotNull()
    java.lang.String input, @org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.domain.model.CipherParams params, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.zarabandajose.transcripto.core.common.Result<java.lang.String>> $completion);
    
    /**
     * Descifra un texto usando los parámetros especificados.
     *
     * @param input Texto a descifrar
     * @param params Parámetros de descifrado
     * @return Result con el texto descifrado o error
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object decrypt(@org.jetbrains.annotations.NotNull()
    java.lang.String input, @org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.domain.model.CipherParams params, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.zarabandajose.transcripto.core.common.Result<java.lang.String>> $completion);
}