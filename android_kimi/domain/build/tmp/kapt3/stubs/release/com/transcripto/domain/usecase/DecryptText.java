package com.transcripto.domain.usecase;

/**
 * Use case for decrypting text using various cipher methods.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B(\b\u0007\u0012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\t\u0012\u00070\u0004\u00a2\u0006\u0002\b\u00050\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0019\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086\u0002R\u001f\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\t\u0012\u00070\u0004\u00a2\u0006\u0002\b\u00050\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/transcripto/domain/usecase/DecryptText;", "", "cipherMethods", "", "Lcom/transcripto/domain/model/CipherMethod;", "Lkotlin/jvm/JvmSuppressWildcards;", "envelopeParser", "Lcom/transcripto/domain/repository/EnvelopeParser;", "(Ljava/util/Map;Lcom/transcripto/domain/repository/EnvelopeParser;)V", "invoke", "Lcom/transcripto/domain/model/CipherResult;", "input", "", "params", "Lcom/transcripto/domain/model/CipherParams;", "domain_release"})
public final class DecryptText {
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<com.transcripto.domain.model.CipherMethod, com.transcripto.domain.model.CipherMethod> cipherMethods = null;
    @org.jetbrains.annotations.NotNull()
    private final com.transcripto.domain.repository.EnvelopeParser envelopeParser = null;
    
    @javax.inject.Inject()
    public DecryptText(@org.jetbrains.annotations.NotNull()
    java.util.Map<com.transcripto.domain.model.CipherMethod, com.transcripto.domain.model.CipherMethod> cipherMethods, @org.jetbrains.annotations.NotNull()
    com.transcripto.domain.repository.EnvelopeParser envelopeParser) {
        super();
    }
    
    /**
     * Decrypts the input text using the specified method and parameters.
     * @param input The text to decrypt
     * @param params The decryption parameters
     * @return CipherResult containing decrypted text or error
     */
    @org.jetbrains.annotations.NotNull()
    public final com.transcripto.domain.model.CipherResult invoke(@org.jetbrains.annotations.NotNull()
    java.lang.String input, @org.jetbrains.annotations.NotNull()
    com.transcripto.domain.model.CipherParams params) {
        return null;
    }
}