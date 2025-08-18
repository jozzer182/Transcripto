package com.transcripto.domain.model

/**
 * Represents the available cipher methods for encryption/decryption.
 */
enum class CipherMethod {
    CAESAR,
    BASE64,
    VIGENERE,
    XOR
}

/**
 * Represents the operation mode (encrypt or decrypt).
 */
enum class CipherMode {
    ENCRYPT,
    DECRYPT
}

/**
 * Data class representing encryption/decryption parameters.
 */
data class CipherParams(
    val method: CipherMethod,
    val key: String? = null,
    val shift: Int? = null,
    val salt: String? = null,
    val useSalt: Boolean = false
)

/**
 * Result wrapper for encryption/decryption operations.
 */
sealed class CipherResult {
    data class Success(val data: String) : CipherResult()
    data class Error(val message: String) : CipherResult()
}

/**
 * Parsed envelope data structure.
 */
data class EnvelopeData(
    val method: CipherMethod,
    val salt: String? = null,
    val payload: String
)