import Foundation

/// Protocolo que define un método de cifrado que puede cifrar y descifrar texto.
public protocol CipherMethod {
    /// Cifra el texto de entrada usando los parámetros proporcionados.
    /// - Parameters:
    ///   - input: El `String` a cifrar.
    ///   - params: Los parámetros para la operación de cifrado.
    /// - Returns: El `String` cifrado.
    /// - Throws: `CipherError` si ocurre un error durante el cifrado.
    func encrypt(_ input: String, params: CipherParams) throws -> String

    /// Descifra el texto de entrada usando los parámetros proporcionados.
    /// - Parameters:
    ///   - input: El `String` a descifrar.
    ///   - params: Los parámetros para la operación de descifrado.
    /// - Returns: El `String` descifrado.
    /// - Throws: `CipherError` si ocurre un error durante el descifrado.
    func decrypt(_ input: String, params: CipherParams) throws -> String
}
