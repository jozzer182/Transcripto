package com.example.transcripto.data.crypto.impl

import com.example.transcripto.domain.crypto.CipherMethod
import com.example.transcripto.domain.crypto.CipherMethod.CipherParams
import java.util.Base64
import javax.inject.Inject

class XorCipher @Inject constructor() : CipherMethod.Xor() {
    override fun encrypt(input: String, params: CipherParams): String {
        require(params.key.isNotEmpty()) { "Key is required for XOR cipher" }
        val effectiveKey = (params.key + params.salt).toByteArray()
        val inputBytes = input.toByteArray()
        
        val result = inputBytes.mapIndexed { i, byte -> 
            (byte xor effectiveKey[i % effectiveKey.size]).toByte()
        }.toByteArray()
        
        return Base64.getEncoder().encodeToString(result)
    }

    override fun decrypt(input: String, params: CipherParams): String {
        require(params.key.isNotEmpty()) { "Key is required for XOR cipher" }
        val effectiveKey = (params.key + params.salt).toByteArray()
        val inputBytes = Base64.getDecoder().decode(input)
        
        val result = inputBytes.mapIndexed { i, byte -> 
            (byte xor effectiveKey[i % effectiveKey.size]).toByte()
        }.toByteArray()
        
        return String(result)
    }
}