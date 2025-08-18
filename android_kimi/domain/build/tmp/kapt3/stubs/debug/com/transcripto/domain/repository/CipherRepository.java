package com.transcripto.domain.repository;

/**
 * Repository interface for accessing cipher methods.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u0006H&J\u0014\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\tH&\u00a8\u0006\n"}, d2 = {"Lcom/transcripto/domain/repository/CipherRepository;", "", "getAvailableMethods", "", "", "getCipher", "Lcom/transcripto/domain/model/CipherMethod;", "method", "getCipherMethods", "", "domain_debug"})
public abstract interface CipherRepository {
    
    /**
     * Gets all available cipher methods.
     * @return Map of cipher method to cipher implementation
     */
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.Map<com.transcripto.domain.model.CipherMethod, com.transcripto.domain.model.CipherMethod> getCipherMethods();
    
    /**
     * Gets available cipher method names.
     * @return List of available cipher method names
     */
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<java.lang.String> getAvailableMethods();
    
    /**
     * Gets a specific cipher method by name.
     * @param method The cipher method to get
     * @return The cipher implementation or null if not found
     */
    @org.jetbrains.annotations.Nullable()
    public abstract com.transcripto.domain.model.CipherMethod getCipher(@org.jetbrains.annotations.NotNull()
    com.transcripto.domain.model.CipherMethod method);
}