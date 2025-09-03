//
//  CryptoError.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import Foundation

/// Errores relacionados con operaciones criptográficas
public enum CryptoError: LocalizedError {
    case emptyInput
    case missingKey
    case missingShift
    case invalidShift
    case invalidBase64
    case invalidKey
    case unsupportedMethod(String)
    case saltGenerationFailed
    case encryptionFailed(String)
    case decryptionFailed(String)
    
    public var errorDescription: String? {
        switch self {
        case .emptyInput:
            return "El texto de entrada no puede estar vacío"
        case .missingKey:
            return "Se requiere una clave para este método de cifrado"
        case .missingShift:
            return "Se requiere un valor de desplazamiento para el cifrado César"
        case .invalidShift:
            return "El valor de desplazamiento debe estar entre -25 y 25"
        case .invalidBase64:
            return "El texto no tiene un formato Base64 válido"
        case .invalidKey:
            return "La clave proporcionada no es válida"
        case .unsupportedMethod(let method):
            return "Método de cifrado no soportado: \(method)"
        case .saltGenerationFailed:
            return "No se pudo generar el salt de seguridad"
        case .encryptionFailed(let reason):
            return "Error al cifrar: \(reason)"
        case .decryptionFailed(let reason):
            return "Error al descifrar: \(reason)"
        }
    }
    
    public var recoverySuggestion: String? {
        switch self {
        case .emptyInput:
            return "Ingrese algún texto para procesar"
        case .missingKey:
            return "Proporcione una clave válida"
        case .missingShift:
            return "Ingrese un número entre -25 y 25"
        case .invalidShift:
            return "Use un valor entre -25 y 25"
        case .invalidBase64:
            return "Verifique que el texto esté codificado en Base64"
        case .invalidKey:
            return "Use solo letras en la clave"
        case .unsupportedMethod:
            return "Seleccione un método de cifrado válido"
        case .saltGenerationFailed:
            return "Intente generar el salt nuevamente"
        case .encryptionFailed, .decryptionFailed:
            return "Verifique los parámetros e intente nuevamente"
        }
    }
}
