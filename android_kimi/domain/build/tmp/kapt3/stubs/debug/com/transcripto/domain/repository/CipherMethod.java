package com.transcripto.domain.repository;

/**
 * Interface that all cipher methods must implement.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\t\u001a\u00020\u0005H&\u00a8\u0006\n"}, d2 = {"Lcom/transcripto/domain/repository/CipherMethod;", "", "decrypt", "Lcom/transcripto/domain/model/CipherResult;", "input", "", "params", "Lcom/transcripto/domain/model/CipherParams;", "encrypt", "getName", "domain_debug"})
public abstract interface CipherMethod {
    
    /**
     * Encrypts the input text using the provided parameters.
     * @param input The text to encrypt
     * @param params The encryption parameters
     * @return CipherResult containing encrypted text or error
     */
    @org.jetbrains.annotations.NotNull()
    public abstract com.transcripto.domain.model.CipherResult encrypt(@org.jetbrains.annotations.NotNull()
    java.lang.String input, @org.jetbrains.annotations.NotNull()
    com.transcripto.domain.model.CipherParams params);
    
    /**
     * Decrypts the input text using the provided parameters.
     * @param input The text to decrypt
     * @param params The decryption parameters
     * @return CipherResult containing decrypted text or error
     */
    @org.jetbrains.annotations.NotNull()
    public abstract com.transcripto.domain.model.CipherResult decrypt(@org.jetbrains.annotations.NotNull()
    java.lang.String input, @org.jetbrains.annotations.NotNull()
    com.transcripto.domain.model.CipherParams params);
    
    /**
     * Returns the name of this cipher method.
     */
    @org.jetbrains.annotations.NotNull()
    public abstract java.lang.String getName();
}