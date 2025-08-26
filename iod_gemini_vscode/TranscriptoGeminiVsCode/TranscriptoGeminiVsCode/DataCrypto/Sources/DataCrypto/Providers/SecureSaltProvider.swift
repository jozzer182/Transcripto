import Foundation
import Security
import Domain

public final class SecureSaltProvider: SaltProvider {
    public init() {}

    public func generateSalt() throws -> String {
        var keyData = Data(count: 16)
        let result = keyData.withUnsafeMutableBytes {
            SecRandomCopyBytes(kSecRandomDefault, 16, $0.baseAddress!)
        }

        if result == errSecSuccess {
            return keyData.base64EncodedString()
        } else {
            throw CipherError.internalError(description: "No se pudo generar el salt aleatorio.")
        }
    }
}
