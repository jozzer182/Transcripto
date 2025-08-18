package com.transcripto.data.crypto.repository

import com.transcripto.domain.model.CipherMethod
import com.transcripto.domain.repository.CipherRepository
import javax.inject.Inject

/**
 * Implementation of CipherRepository that provides access to cipher methods.
 */
class CipherRepositoryImpl @Inject constructor(
    private val cipherMethods: Map<CipherMethod, @JvmSuppressWildcards com.transcripto.domain.repository.CipherMethod>
) : CipherRepository {

    override fun getCipherMethods(): Map<CipherMethod, com.transcripto.domain.repository.CipherMethod> {
        return cipherMethods
    }

    override fun getAvailableMethods(): List<String> {
        return cipherMethods.keys.map { it.name }
    }

    override fun getCipher(method: CipherMethod): com.transcripto.domain.repository.CipherMethod? {
        return cipherMethods[method]
    }
}