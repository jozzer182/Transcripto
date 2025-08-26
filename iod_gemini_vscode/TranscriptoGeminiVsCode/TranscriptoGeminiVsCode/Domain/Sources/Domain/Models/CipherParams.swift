import Foundation

/// Define los parámetros para una operación de cifrado o descifrado.
public struct CipherParams {
    public let method: CipherMethodType
    public let key: String?
    public let shift: Int?
    public let salt: String?

    public init(method: CipherMethodType, key: String? = nil, shift: Int? = nil, salt: String? = nil) {
        self.method = method
        self.key = key
        self.shift = shift
        self.salt = salt
    }
}

/// Enumera los tipos de métodos de cifrado disponibles.
public enum CipherMethodType: String, CaseIterable, Identifiable {
    case caesar = "César"
    case vigenere = "Vigenère"
    case xor = "XOR"
    case base64 = "Base64"
    
    public var id: String { self.rawValue }
}
