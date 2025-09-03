//
//  XorCipher.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import Foundation

// Error local para XOR
enum XorError: LocalizedError {
    case missingKey
    case encryptionFailed(String)
    case invalidBase64
    case decryptionFailed(String)
    
    var errorDescription: String? {
        switch self {
        case .missingKey:
            return "Se requiere una clave para el cifrado XOR"
        case .encryptionFailed(let message):
            return "Error de cifrado: \(message)"
        case .invalidBase64:
            return "El texto no es un Base64 válido"
        case .decryptionFailed(let message):
            return "Error de descifrado: \(message)"
        }
    }
}

/// Implementación del cifrado XOR
public class XorCipher: CipherMethod {
    
    public init() {}
    
    public func encrypt(_ input: String, params: CipherParams) throws -> String {
        try InputValidator.validateOperation(text: input, params: params)
        
        guard let key = params.key else {
            throw XorError.missingKey
        }
        
        let effectiveKey = params.salt != nil
            ? CryptoUtils.combineKeyWithSalt(key, salt: params.salt!)
            : key
        
        guard let inputData = input.data(using: .utf8),
              let keyData = effectiveKey.data(using: .utf8) else {
            throw XorError.encryptionFailed("No se pudo convertir el texto a datos UTF-8")
        }
        
        let xorData = applyXor(data: inputData, key: keyData)
        return xorData.base64EncodedString()
    }
    
    public func decrypt(_ input: String, params: CipherParams) throws -> String {
        try InputValidator.validateOperation(text: input, params: params, isDecryption: true)
        
        guard let key = params.key else {
            throw XorError.missingKey
        }
        
        let effectiveKey = params.salt != nil
            ? CryptoUtils.combineKeyWithSalt(key, salt: params.salt!)
            : key
        
        guard let inputData = Data(base64Encoded: input) else {
            throw XorError.invalidBase64
        }
        
        guard let keyData = effectiveKey.data(using: .utf8) else {
            throw XorError.decryptionFailed("No se pudo convertir la clave a datos UTF-8")
        }
        
        let xorData = applyXor(data: inputData, key: keyData)
        
        guard let result = String(data: xorData, encoding: .utf8) else {
            throw XorError.decryptionFailed("No se pudo convertir el resultado a texto UTF-8")
        }
        
        return result
    }
    
    /// Aplica la operación XOR byte a byte
    /// - Parameters:
    ///   - data: Datos a procesar
    ///   - key: Clave para XOR
    /// - Returns: Datos procesados con XOR
    private func applyXor(data: Data, key: Data) -> Data {
        var result = Data()
        
        for (index, byte) in data.enumerated() {
            let keyByte = key[index % key.count]
            result.append(byte ^ keyByte)
        }
        
        return result
    }
}
