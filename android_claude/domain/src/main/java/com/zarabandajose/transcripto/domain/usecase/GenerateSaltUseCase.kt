package com.zarabandajose.transcripto.domain.usecase

import com.zarabandajose.transcripto.domain.repository.SaltRepository
import javax.inject.Inject

/**
 * Caso de uso para generar salts seguros.
 */
class GenerateSaltUseCase @Inject constructor(
    private val saltRepository: SaltRepository
) {
    
    /**
     * Genera un salt aleatorio seguro.
     * 
     * @param length Longitud del salt en bytes (por defecto 12)
     * @return Salt codificado en Base64
     */
    operator fun invoke(length: Int = 12): String {
        return saltRepository.generateSalt(length)
    }
}
