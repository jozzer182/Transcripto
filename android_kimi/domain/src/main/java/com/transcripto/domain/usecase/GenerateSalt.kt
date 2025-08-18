package com.transcripto.domain.usecase

import com.transcripto.domain.repository.SaltProvider
import javax.inject.Inject

/**
 * Use case for generating secure random salts.
 */
class GenerateSalt @Inject constructor(
    private val saltProvider: SaltProvider
) {
    /**
     * Generates a random salt string.
     * @param length The length of the salt in bytes (default: 12)
     * @return Base64 encoded salt string
     */
    operator fun invoke(length: Int = 12): String {
        return saltProvider.generateSalt(length)
    }
}