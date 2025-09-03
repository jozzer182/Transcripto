//
//  EncryptTextUseCase.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import Foundation

/// Error para casos de uso
enum UseCaseError: LocalizedError {
    case unsupportedMethod(String)
    
    var errorDescription: String? {
        switch self {
        case .unsupportedMethod(let method):
            return "Método de cifrado no soportado: \(method)"
        }
    }
}

/// Caso de uso para cifrar texto
public class EncryptTextUseCase {
    private let cipherMethods: [CipherType: CipherMethod]
    
    public init(cipherMethods: [CipherType: CipherMethod]) {
        self.cipherMethods = cipherMethods
    }
    
    /// Cifra el texto usando el método especificado
    /// - Parameters:
    ///   - text: Texto a cifrar
    ///   - params: Parámetros de cifrado
    /// - Returns: Texto cifrado
    /// - Throws: Error si el método no está disponible o los parámetros son inválidos
    public func execute(text: String, params: CipherParams) throws -> String {
        guard let cipherMethod = cipherMethods[params.method] else {
            throw UseCaseError.unsupportedMethod(params.method.displayName)
        }
        
        return try cipherMethod.encrypt(text, params: params)
    }
}
