package com.zarabandajose.transcripto.domain.usecase;

/**
 * Caso de uso para generar salts seguros.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0086\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/zarabandajose/transcripto/domain/usecase/GenerateSaltUseCase;", "", "saltRepository", "Lcom/zarabandajose/transcripto/domain/repository/SaltRepository;", "(Lcom/zarabandajose/transcripto/domain/repository/SaltRepository;)V", "invoke", "", "length", "", "domain_release"})
public final class GenerateSaltUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.zarabandajose.transcripto.domain.repository.SaltRepository saltRepository = null;
    
    @javax.inject.Inject()
    public GenerateSaltUseCase(@org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.domain.repository.SaltRepository saltRepository) {
        super();
    }
    
    /**
     * Genera un salt aleatorio seguro.
     *
     * @param length Longitud del salt en bytes (por defecto 12)
     * @return Salt codificado en Base64
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String invoke(int length) {
        return null;
    }
}