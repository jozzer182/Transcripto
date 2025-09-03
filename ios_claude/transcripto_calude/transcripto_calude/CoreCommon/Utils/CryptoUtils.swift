//
//  CryptoUtils.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import Foundation

/// Utilidades generales para operaciones criptográficas
public class CryptoUtils {
    
    /// Combina el salt con un texto según las reglas especificadas
    /// - Parameters:
    ///   - text: Texto original
    ///   - salt: Salt a aplicar
    ///   - operation: Tipo de operación (cifrar o descifrar)
    /// - Returns: Texto con salt aplicado
    public static func applySaltToText(_ text: String, salt: String, operation: SaltOperation) -> String {
        switch operation {
        case .encrypt:
            return "\(salt):\(text)"
        case .decrypt:
            // Remover el prefijo salt: si existe
            if text.hasPrefix("\(salt):") {
                return String(text.dropFirst(salt.count + 1))
            }
            return text
        }
    }
    
    /// Combina el salt con una clave para métodos que lo requieren
    /// - Parameters:
    ///   - key: Clave original
    ///   - salt: Salt a combinar
    /// - Returns: Clave efectiva con salt
    public static func combineKeyWithSalt(_ key: String, salt: String) -> String {
        return key + salt
    }
    
    /// Calcula el desplazamiento efectivo para César con salt
    /// - Parameters:
    ///   - baseShift: Desplazamiento base
    ///   - salt: Salt a aplicar
    ///   - operation: Tipo de operación
    /// - Returns: Desplazamiento efectivo
    public static func calculateEffectiveShift(_ baseShift: Int, salt: String, operation: SaltOperation) -> Int {
        let saltHash = salt.saltHash() % 26
        switch operation {
        case .encrypt:
            return baseShift + saltHash
        case .decrypt:
            return baseShift - saltHash
        }
    }
}

/// Tipo de operación para salt
public enum SaltOperation {
    case encrypt
    case decrypt
}
