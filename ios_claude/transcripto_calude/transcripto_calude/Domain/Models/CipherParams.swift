//
//  CipherParams.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import Foundation

/// Parámetros para operaciones de cifrado y descifrado
public struct CipherParams {
    /// Tipo de método de cifrado
    public let method: CipherType
    /// Clave para métodos que la requieren (Vigenère, XOR)
    public let key: String?
    /// Desplazamiento para cifrado César
    public let shift: Int?
    /// Salt opcional para agregar complejidad
    public let salt: String?
    
    public init(method: CipherType, key: String? = nil, shift: Int? = nil, salt: String? = nil) {
        self.method = method
        self.key = key
        self.shift = shift
        self.salt = salt
    }
}

/// Tipos de cifrado disponibles
public enum CipherType: String, CaseIterable {
    case caesar = "caesar"
    case base64 = "base64"
    case vigenere = "vigenere"
    case xor = "xor"
    
    /// Nombre legible del método
    public var displayName: String {
        switch self {
        case .caesar:
            return "César"
        case .base64:
            return "Base64"
        case .vigenere:
            return "Vigenère"
        case .xor:
            return "XOR"
        }
    }
    
    /// Indica si el método requiere una clave
    public var requiresKey: Bool {
        switch self {
        case .caesar, .base64:
            return false
        case .vigenere, .xor:
            return true
        }
    }
    
    /// Indica si el método requiere un desplazamiento
    public var requiresShift: Bool {
        switch self {
        case .caesar:
            return true
        case .base64, .vigenere, .xor:
            return false
        }
    }
}
