package com.zarabandajose.transcripto.domain.model

/**
 * Datos de un envelope parseado desde el formato method=...;salt=...;payload=...
 * 
 * @param method Método de cifrado detectado
 * @param salt Salt utilizado (puede estar vacío)
 * @param payload Contenido cifrado
 */
data class EnvelopeData(
    val method: CipherMethod,
    val salt: String,
    val payload: String
)
