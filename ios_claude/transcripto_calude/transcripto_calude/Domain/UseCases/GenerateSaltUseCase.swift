//
//  GenerateSaltUseCase.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import Foundation
import Security

/// Caso de uso para generar salt aleatorio
public class GenerateSaltUseCase {
    
    public init() {}
    
    /// Genera un salt aleatorio usando SecRandomCopyBytes
    /// - Parameter length: Longitud del salt en bytes (por defecto 16)
    /// - Returns: Salt en formato hexadecimal
    /// - Throws: Error si no se puede generar el salt
    public func execute(length: Int = 16) throws -> String {
        var bytes = [UInt8](repeating: 0, count: length)
        let status = SecRandomCopyBytes(kSecRandomDefault, length, &bytes)
        
        guard status == errSecSuccess else {
            throw UseCaseError.unsupportedMethod("Salt generation failed")
        }
        
        return bytes.map { String(format: "%02x", $0) }.joined()
    }
}
