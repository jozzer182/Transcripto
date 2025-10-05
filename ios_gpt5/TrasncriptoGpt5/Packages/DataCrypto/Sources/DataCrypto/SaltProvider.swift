import Foundation
import Security
import Domain

public struct SystemSaltProvider: SaltProvider {
    public init() {}
    public func generateSalt(length: Int) throws -> String {
        var bytes = [UInt8](repeating: 0, count: length)
        let status = SecRandomCopyBytes(kSecRandomDefault, length, &bytes)
        precondition(status == errSecSuccess)
        return Data(bytes).base64EncodedString()
    }
}
