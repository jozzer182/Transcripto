package com.zarabandajose.transcripto.domain.repository

import com.zarabandajose.transcripto.core.common.Result
import com.zarabandajose.transcripto.domain.model.CipherParams

/**
 * Interfaz para operaciones de cifrado.
 * Define los contratos que deben implementar los diferentes métodos de cifrado.
 */
interface CipherRepository {
    
    /**
     * Cifra un texto usando los parámetros especificados.
     * 
     * @param input Texto a cifrar
     * @param params Parámetros de cifrado
     * @return Result con el texto cifrado o error
     */
    suspend fun encrypt(input: String, params: CipherParams): Result<String>
    
    /**
     * Descifra un texto usando los parámetros especificados.
     * 
     * @param input Texto a descifrar
     * @param params Parámetros de descifrado
     * @return Result con el texto descifrado o error
     */
    suspend fun decrypt(input: String, params: CipherParams): Result<String>
}
