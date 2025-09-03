//
//  Base64Codec.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import Foundation

// Error local para Base64
enum Base64Error: LocalizedError {
    case encryptionFailed(String)
    case invalidBase64
    case decryptionFailed(String)
    
    var errorDescription: String? {
        switch self {
        case .encryptionFailed(let message):
            return "Error de cifrado: \(message)"
        case .invalidBase64:
            return "El texto no es un Base64 válido"
        case .decryptionFailed(let message):
            return "Error de descifrado: \(message)"
        }
    }
}

/// Implementación del codec Base64
public class Base64Codec: CipherMethod {
    
    public init() {}
    
    public func encrypt(_ input: String, params: CipherParams) throws -> String {
        try InputValidator.validateOperation(text: input, params: params)
        
        var textToEncode = input
        
        // Aplicar salt antes de codificar si está presente
        if let salt = params.salt {
            textToEncode = CryptoUtils.applySaltToText(input, salt: salt, operation: .encrypt)
        }
        
        guard let data = textToEncode.data(using: .utf8) else {
            throw Base64Error.encryptionFailed("No se pudo convertir el texto a datos UTF-8")
        }
        
        return data.base64EncodedString()
    }
    
    public func decrypt(_ input: String, params: CipherParams) throws -> String {
        try InputValidator.validateOperation(text: input, params: params, isDecryption: true)
        
        guard let data = Data(base64Encoded: input) else {
            throw Base64Error.invalidBase64
        }
        
        guard let decodedText = String(data: data, encoding: .utf8) else {
            throw Base64Error.decryptionFailed("No se pudo convertir los datos a texto UTF-8")
        }
        
        var result = decodedText
        
        // Remover salt si está presente
        if let salt = params.salt {
            result = CryptoUtils.applySaltToText(decodedText, salt: salt, operation: .decrypt)
        }
        
        return result
    }
}
