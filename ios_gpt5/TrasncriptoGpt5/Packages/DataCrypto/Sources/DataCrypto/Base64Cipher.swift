import Foundation
import Domain
import CoreCommon

public final class Base64Cipher: CipherMethod {
    public init() {}

    public func encrypt(_ input: String, params: CipherParams) throws -> String {
        if params.useSalt, let salt = params.salt, !salt.isEmpty {
            let prefixed = "\(salt):\(input)"
            return Data(prefixed.utf8).base64EncodedString()
        } else {
            return Data(input.utf8).base64EncodedString()
        }
    }

    public func decrypt(_ input: String, params: CipherParams) throws -> String {
        guard let data = Data(base64Encoded: input) else { throw CryptoError.invalidBase64 }
        let text = String(decoding: data, as: UTF8.self)
        if params.useSalt {
            guard let salt = params.salt, !salt.isEmpty else { throw CryptoError.saltRequiredForDecryption }
            let prefix = "\(salt):"
            guard text.hasPrefix(prefix) else { throw CryptoError.saltMismatch }
            return String(text.dropFirst(prefix.count))
        } else {
            return text
        }
    }
}
