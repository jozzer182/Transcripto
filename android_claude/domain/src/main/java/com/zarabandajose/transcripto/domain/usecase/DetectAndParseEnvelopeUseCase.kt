package com.zarabandajose.transcripto.domain.usecase

import com.zarabandajose.transcripto.core.common.Result
import com.zarabandajose.transcripto.domain.model.CipherMethod
import com.zarabandajose.transcripto.domain.model.EnvelopeData
import java.util.Base64
import javax.inject.Inject

/**
 * Caso de uso para detectar y parsear el formato envelope method=...;salt=...;payload=...
 */
class DetectAndParseEnvelopeUseCase @Inject constructor() {
    
    /**
     * Detecta si el texto tiene formato envelope y lo parsea.
     * 
     * @param input Texto a analizar
     * @return Result con EnvelopeData si es válido, Error si no es envelope o formato inválido
     */
    operator fun invoke(input: String): Result<EnvelopeData> {
        // Verificar formato básico
        if (!input.startsWith("method=") || !input.contains(";salt=") || !input.contains(";payload=")) {
            return Result.Error("No es formato envelope")
        }
        
        return try {
            val parts = input.split(";")
            if (parts.size != 3) {
                return Result.Error("Formato envelope inválido: número incorrecto de partes")
            }
            
            // Parsear método
            val methodPart = parts[0]
            if (!methodPart.startsWith("method=")) {
                return Result.Error("Formato envelope inválido: falta method=")
            }
            val methodString = methodPart.substring("method=".length)
            val method = CipherMethod.fromEnvelopeString(methodString)
                ?: return Result.Error("Método de cifrado desconocido: $methodString")
            
            // Parsear salt
            val saltPart = parts[1]
            if (!saltPart.startsWith("salt=")) {
                return Result.Error("Formato envelope inválido: falta salt=")
            }
            val saltEncoded = saltPart.substring("salt=".length)
            val salt = if (saltEncoded.isEmpty()) {
                ""
            } else {
                try {
                    String(Base64.getDecoder().decode(saltEncoded))
                } catch (e: IllegalArgumentException) {
                    return Result.Error("Salt en Base64 inválido")
                }
            }
            
            // Parsear payload
            val payloadPart = parts[2]
            if (!payloadPart.startsWith("payload=")) {
                return Result.Error("Formato envelope inválido: falta payload=")
            }
            val payload = payloadPart.substring("payload=".length)
            if (payload.isEmpty()) {
                return Result.Error("Payload no puede estar vacío")
            }
            
            Result.Success(EnvelopeData(method, salt, payload))
            
        } catch (e: Exception) {
            Result.Error("Error al parsear envelope: ${e.message}")
        }
    }
}
