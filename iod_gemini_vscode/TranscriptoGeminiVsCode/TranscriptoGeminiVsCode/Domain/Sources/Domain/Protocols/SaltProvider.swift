import Foundation

/// Protocolo para un proveedor que puede generar un salt criptográfico.
public protocol SaltProvider {
    /// Genera un salt aleatorio de una longitud específica.
    /// - Returns: Un `String` que representa el salt generado, típicamente en Base64 o formato hexadecimal.
    /// - Throws: `CipherError` si la generación del salt falla.
    func generateSalt() throws -> String
}
