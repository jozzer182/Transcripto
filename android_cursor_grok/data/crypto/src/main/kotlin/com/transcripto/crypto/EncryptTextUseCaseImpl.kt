package com.transcripto.crypto

import com.transcripto.domain.CipherRepository
import com.transcripto.domain.EncryptTextUseCase
import com.transcripto.domain.Method
import java.util.Base64
import java.nio.charset.StandardCharsets

class EncryptTextUseCaseImpl(private val repository: CipherRepository) : EncryptTextUseCase {
    override operator fun invoke(input: String, method: Method, key: String?, shift: Int?, salt: String?): String {
        val params = mutableMapOf<String, Any>()
        key?.let { params["key"] = it }
        shift?.let { params["shift"] = it }
        salt?.let { params["salt"] = it }
        val cipher = repository.getCipher(method)
        val payload = cipher.encrypt(input, params)
        val saltBase64 = salt?.let { Base64.getEncoder().encodeToString(it.toByteArray(StandardCharsets.UTF_8)) } ?: ""
        return "method=${method.name};salt=$saltBase64;payload=$payload"
    }
}
