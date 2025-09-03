//
//  DecryptTextUseCase.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import Foundation

/// Caso de uso para descifrar texto
public class DecryptTextUseCase {
    private let cipherMethods: [CipherType: CipherMethod]
    
    public init(cipherMethods: [CipherType: CipherMethod]) {
        self.cipherMethods = cipherMethods
    }
    
    /// Descifra el texto usando el método especificado
    /// - Parameters:
    ///   - text: Texto a descifrar
    ///   - params: Parámetros de descifrado
    /// - Returns: Texto descifrado
    /// - Throws: Error si el método no está disponible o los parámetros son inválidos
    public func execute(text: String, params: CipherParams) throws -> String {
        guard let cipherMethod = cipherMethods[params.method] else {
            throw UseCaseError.unsupportedMethod(params.method.displayName)
        }
        
        return try cipherMethod.decrypt(text, params: params)
    }
}
