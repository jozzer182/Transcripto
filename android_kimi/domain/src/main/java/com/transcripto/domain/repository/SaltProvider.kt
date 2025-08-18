package com.transcripto.domain.repository

/**
 * Interface for generating secure random salts.
 */
interface SaltProvider {
    /**
     * Generates a random salt string.
     * @param length The length of the salt in bytes
     * @return Base64 encoded salt string
     */
    fun generateSalt(length: Int = 12): String
}