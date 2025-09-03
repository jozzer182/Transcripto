//
//  CaesarCipher.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import Foundation

// Error local para cifrado César
enum CaesarError: LocalizedError {
    case missingShift
    
    var errorDescription: String? {
        switch self {
        case .missingShift:
            return "Se requiere un valor de desplazamiento para el cifrado César"
        }
    }
}

/// Implementación del cifrado César
public class CaesarCipher: CipherMethod {
    
    public init() {}
    
    public func encrypt(_ input: String, params: CipherParams) throws -> String {
        try InputValidator.validateOperation(text: input, params: params)
        
        guard let shift = params.shift else {
            throw CaesarError.missingShift
        }
        
        let effectiveShift = params.salt != nil 
            ? CryptoUtils.calculateEffectiveShift(shift, salt: params.salt!, operation: .encrypt)
            : shift
        
        var result = applyCaesarShift(to: input, shift: effectiveShift)
        
        // Aplicar salt al resultado si está presente
        if let salt = params.salt {
            result = CryptoUtils.applySaltToText(result, salt: salt, operation: .encrypt)
        }
        
        return result
    }
    
    public func decrypt(_ input: String, params: CipherParams) throws -> String {
        try InputValidator.validateOperation(text: input, params: params)
        
        guard let shift = params.shift else {
            throw CaesarError.missingShift
        }
        
        var textToDecrypt = input
        
        // Remover salt si está presente
        if let salt = params.salt {
            textToDecrypt = CryptoUtils.applySaltToText(input, salt: salt, operation: .decrypt)
        }
        
        let effectiveShift = params.salt != nil
            ? CryptoUtils.calculateEffectiveShift(shift, salt: params.salt!, operation: .decrypt)
            : shift
        
        return applyCaesarShift(to: textToDecrypt, shift: -effectiveShift)
    }
    
    /// Aplica el desplazamiento César a un texto
    /// - Parameters:
    ///   - text: Texto a procesar
    ///   - shift: Cantidad de desplazamiento
    /// - Returns: Texto con desplazamiento aplicado
    private func applyCaesarShift(to text: String, shift: Int) -> String {
        return String(text.map { char in
            if let letterValue = String(char).letterValue() {
                let isUppercase = char.isUppercase
                let shiftedValue = letterValue + shift
                return String.letterFromValue(shiftedValue, isUppercase: isUppercase)
            } else {
                return char
            }
        })
    }
}
