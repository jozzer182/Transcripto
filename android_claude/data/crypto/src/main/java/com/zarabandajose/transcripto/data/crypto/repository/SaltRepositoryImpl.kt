package com.zarabandajose.transcripto.data.crypto.repository

import com.zarabandajose.transcripto.core.common.StringUtils
import com.zarabandajose.transcripto.domain.repository.SaltRepository
import javax.inject.Inject

/**
 * Implementación del repositorio de salt.
 * Utiliza SecureRandom para generar salts criptográficamente seguros.
 */
class SaltRepositoryImpl @Inject constructor() : SaltRepository {
    
    override fun generateSalt(length: Int): String {
        return StringUtils.generateSecureSalt(length)
    }
}
