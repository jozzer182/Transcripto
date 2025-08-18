package com.transcripto.domain.repository

import com.transcripto.domain.model.CipherMethod
import com.transcripto.domain.model.CipherParams
import com.transcripto.domain.model.CipherResult

/**
 * Repository interface for accessing cipher methods.
 */
interface CipherRepository {
    /**
     * Gets all available cipher methods.
     * @return Map of cipher method to cipher implementation
     */
    fun getCipherMethods(): Map<CipherMethod, CipherMethod>

    /**
     * Gets available cipher method names.
     * @return List of available cipher method names
     */
    fun getAvailableMethods(): List<String>

    /**
     * Gets a specific cipher method by name.
     * @param method The cipher method to get
     * @return The cipher implementation or null if not found
     */
    fun getCipher(method: CipherMethod): CipherMethod?
}