//
//  VigenereCipher.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import Foundation

// Error local para Vigenère
enum VigenereError: LocalizedError {
    case missingKey
    
    var errorDescription: String? {
        switch self {
        case .missingKey:
            return "Se requiere una clave para el cifrado Vigenère"
        }
    }
}

/// Implementación del cifrado Vigenère
public class VigenereCipher: CipherMethod {
    
    public init() {}
    
    public func encrypt(_ input: String, params: CipherParams) throws -> String {
        try InputValidator.validateOperation(text: input, params: params)
        
        guard let key = params.key else {
            throw VigenereError.missingKey
        }
        
        let effectiveKey = params.salt != nil 
            ? CryptoUtils.combineKeyWithSalt(key, salt: params.salt!)
            : key
        
        return applyVigenere(to: input, key: effectiveKey, encrypt: true)
    }
    
    public func decrypt(_ input: String, params: CipherParams) throws -> String {
        try InputValidator.validateOperation(text: input, params: params)
        
        guard let key = params.key else {
            throw VigenereError.missingKey
        }
        
        let effectiveKey = params.salt != nil
            ? CryptoUtils.combineKeyWithSalt(key, salt: params.salt!)
            : key
        
        return applyVigenere(to: input, key: effectiveKey, encrypt: false)
    }
    
    /// Aplica el cifrado/descifrado Vigenère
    /// - Parameters:
    ///   - text: Texto a procesar
    ///   - key: Clave para el cifrado
    ///   - encrypt: true para cifrar, false para descifrar
    /// - Returns: Texto procesado
    private func applyVigenere(to text: String, key: String, encrypt: Bool) -> String {
        let normalizedKey = key.withoutAccents.uppercased()
        let keyArray = Array(normalizedKey)
        var keyIndex = 0
        
        return String(text.map { char in
            let normalizedChar = String(char).withoutAccents
            
            if let letterValue = normalizedChar.letterValue() {
                let keyChar = keyArray[keyIndex % keyArray.count]
                if let keyValue = String(keyChar).letterValue() {
                    let shift = encrypt ? keyValue : -keyValue
                    let newValue = letterValue + shift
                    keyIndex += 1
                    
                    let isUppercase = char.isUppercase
                    return String.letterFromValue(newValue, isUppercase: isUppercase)
                }
            }
            
            return char
        })
    }
}
