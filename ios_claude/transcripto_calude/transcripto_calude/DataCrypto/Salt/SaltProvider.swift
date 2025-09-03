//
//  SaltProvider.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import Foundation

/// Proveedor de salt para operaciones criptográficas
public class SaltProvider {
    private let generateSaltUseCase: GenerateSaltUseCase
    
    public init(generateSaltUseCase: GenerateSaltUseCase? = nil) {
        self.generateSaltUseCase = generateSaltUseCase ?? GenerateSaltUseCase()
    }
    
    /// Genera un salt aleatorio
    /// - Parameter length: Longitud del salt en bytes
    /// - Returns: Salt generado
    /// - Throws: Error si no se puede generar el salt
    public func generateSalt(length: Int = 16) throws -> String {
        return try generateSaltUseCase.execute(length: length)
    }
    
    /// Valida que un salt sea válido
    /// - Parameter salt: Salt a validar
    /// - Returns: true si es válido
    public func isValidSalt(_ salt: String) -> Bool {
        return !salt.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty
    }
}
