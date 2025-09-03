//
//  InputValidator.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import Foundation

// Error local para validaciones
enum ValidationError: LocalizedError {
    case emptyInput
    case missingKey
    case invalidKey
    case missingShift
    case invalidShift
    case invalidBase64
    
    var errorDescription: String? {
        switch self {
        case .emptyInput:
            return "El texto de entrada no puede estar vacío"
        case .missingKey:
            return "Se requiere una clave para este método de cifrado"
        case .invalidKey:
            return "La clave proporcionada no es válida"
        case .missingShift:
            return "Se requiere un valor de desplazamiento para el cifrado César"
        case .invalidShift:
            return "El valor de desplazamiento debe estar entre -25 y 25"
        case .invalidBase64:
            return "El texto no es un Base64 válido"
        }
    }
}

/// Validador de entradas para operaciones criptográficas
public class InputValidator {
    
    /// Valida que el texto de entrada no esté vacío
    /// - Parameter text: Texto a validar
    /// - Throws: ValidationError.emptyInput si el texto está vacío
    public static func validateInputText(_ text: String) throws {
        if text.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty {
            throw ValidationError.emptyInput
        }
    }
    
    /// Valida una clave para métodos que la requieren
    /// - Parameters:
    ///   - key: Clave a validar
    ///   - method: Método de cifrado que requiere la clave
    /// - Throws: ValidationError.missingKey o ValidationError.invalidKey
    public static func validateKey(_ key: String?, for method: CipherType) throws {
        guard method.requiresKey else { return }
        
        guard let key = key, !key.isEmpty else {
            throw ValidationError.missingKey
        }
        
        // Para Vigenère, validar que solo contenga letras
        if method == .vigenere {
            let letterCharacterSet = CharacterSet.letters
            if key.rangeOfCharacter(from: letterCharacterSet.inverted) != nil {
                throw ValidationError.invalidKey
            }
        }
    }
    
    /// Valida el desplazamiento para cifrado César
    /// - Parameters:
    ///   - shift: Valor de desplazamiento
    ///   - method: Método de cifrado
    /// - Throws: ValidationError.missingShift o ValidationError.invalidShift
    public static func validateShift(_ shift: Int?, for method: CipherType) throws {
        guard method.requiresShift else { return }
        
        guard let shift = shift else {
            throw ValidationError.missingShift
        }
        
        if shift < -25 || shift > 25 {
            throw ValidationError.invalidShift
        }
    }
    
    /// Valida que un texto sea Base64 válido
    /// - Parameter text: Texto a validar
    /// - Throws: ValidationError.invalidBase64 si no es Base64 válido
    public static func validateBase64(_ text: String) throws {
        guard Data(base64Encoded: text) != nil else {
            throw ValidationError.invalidBase64
        }
    }
    
    /// Valida todos los parámetros para una operación de cifrado/descifrado
    /// - Parameters:
    ///   - text: Texto de entrada
    ///   - params: Parámetros de la operación
    ///   - isDecryption: Indica si es una operación de descifrado
    /// - Throws: Error de validación correspondiente
    public static func validateOperation(text: String, params: CipherParams, isDecryption: Bool = false) throws {
        try validateInputText(text)
        try validateKey(params.key, for: params.method)
        try validateShift(params.shift, for: params.method)
        
        // Para descifrado XOR y Base64, validar formato Base64
        if isDecryption && (params.method == .xor || params.method == .base64) {
            try validateBase64(text)
        }
    }
}
