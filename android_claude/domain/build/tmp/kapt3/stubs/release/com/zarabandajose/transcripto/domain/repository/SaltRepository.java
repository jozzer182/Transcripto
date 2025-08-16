package com.zarabandajose.transcripto.domain.repository;

/**
 * Interfaz para generar salts seguros.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2 = {"Lcom/zarabandajose/transcripto/domain/repository/SaltRepository;", "", "generateSalt", "", "length", "", "domain_release"})
public abstract interface SaltRepository {
    
    /**
     * Genera un salt aleatorio seguro.
     *
     * @param length Longitud del salt en bytes
     * @return Salt codificado en Base64
     */
    @org.jetbrains.annotations.NotNull()
    public abstract java.lang.String generateSalt(int length);
    
    /**
     * Interfaz para generar salts seguros.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}