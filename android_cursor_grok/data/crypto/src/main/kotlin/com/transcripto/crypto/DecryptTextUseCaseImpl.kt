package com.transcripto.crypto

import com.transcripto.domain.CipherRepository
import com.transcripto.domain.DecryptTextUseCase
import com.transcripto.domain.DetectAndParseEnvelopeUseCase
import com.transcripto.domain.Method

class DecryptTextUseCaseImpl(
    private val repository: CipherRepository,
    private val parser: DetectAndParseEnvelopeUseCase
) : DecryptTextUseCase {
    override operator fun invoke(input: String, method: Method, key: String?, shift: Int?, salt: String?): String {
        val envelope = parser(input)
        if (envelope != null) {
            val envMethod = envelope["method"] as Method
            val envSalt = envelope["salt"] as String
            val payload = envelope["payload"] as String
            val params = mutableMapOf<String, Any>()
            key?.let { params["key"] = it }
            shift?.let { params["shift"] = it }
            params["salt"] = envSalt
            val cipher = repository.getCipher(envMethod)
            return cipher.decrypt(payload, params)
        } else {
            val params = mutableMapOf<String, Any>()
            key?.let { params["key"] = it }
            shift?.let { params["shift"] = it }
            salt?.let { params["salt"] = it }
            val cipher = repository.getCipher(method)
            return cipher.decrypt(input, params)
        }
    }
}
