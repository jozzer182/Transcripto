//
//  CipherMethod.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import Foundation

/// Protocolo que define los métodos para cifrar y descifrar texto
public protocol CipherMethod {
    /// Cifra el texto de entrada usando los parámetros especificados
    /// - Parameters:
    ///   - input: Texto a cifrar
    ///   - params: Parámetros de cifrado (método, clave, desplazamiento, salt)
    /// - Returns: Texto cifrado
    /// - Throws: Error de cifrado si los parámetros son inválidos
    func encrypt(_ input: String, params: CipherParams) throws -> String
    
    /// Descifra el texto de entrada usando los parámetros especificados
    /// - Parameters:
    ///   - input: Texto a descifrar
    ///   - params: Parámetros de descifrado (método, clave, desplazamiento, salt)
    /// - Returns: Texto descifrado
    /// - Throws: Error de descifrado si los parámetros son inválidos o el texto no es válido
    func decrypt(_ input: String, params: CipherParams) throws -> String
}
