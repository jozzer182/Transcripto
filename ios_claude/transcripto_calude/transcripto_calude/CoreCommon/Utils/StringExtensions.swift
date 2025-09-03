//
//  StringExtensions.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import Foundation

/// Extensiones útiles para String
extension String {
    
    /// Obtiene el hash de un string para usar con salt
    /// - Returns: Valor hash como entero positivo
    func saltHash() -> Int {
        var hash = 0
        for char in self {
            hash = hash &* 31 &+ Int(char.asciiValue ?? 0)
        }
        return abs(hash)
    }
    
    /// Convierte una letra a su valor numérico (A/a=0, B/b=1, etc.)
    /// - Returns: Valor numérico de la letra o nil si no es letra
    func letterValue() -> Int? {
        guard let char = self.first, self.count == 1 else { return nil }
        
        if char >= "A" && char <= "Z" {
            return Int(char.asciiValue! - Character("A").asciiValue!)
        } else if char >= "a" && char <= "z" {
            return Int(char.asciiValue! - Character("a").asciiValue!)
        }
        return nil
    }
    
    /// Convierte un valor numérico a letra preservando el caso
    /// - Parameters:
    ///   - value: Valor numérico (0-25)
    ///   - isUppercase: Si debe ser mayúscula
    /// - Returns: Caracter correspondiente
    static func letterFromValue(_ value: Int, isUppercase: Bool) -> Character {
        let normalizedValue = ((value % 26) + 26) % 26
        let baseValue = isUppercase ? Character("A").asciiValue! : Character("a").asciiValue!
        return Character(UnicodeScalar(baseValue + UInt8(normalizedValue)))
    }
    
    /// Verifica si el string contiene solo letras
    var isAlphabetic: Bool {
        return !isEmpty && range(of: "^[a-zA-Z]+$", options: .regularExpression) != nil
    }
    
    /// Elimina acentos y diacríticos de las letras
    var withoutAccents: String {
        return folding(options: .diacriticInsensitive, locale: .current)
    }
}
