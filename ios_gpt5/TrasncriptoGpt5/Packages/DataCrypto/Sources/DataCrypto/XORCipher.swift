import Foundation
import Domain
import CoreCommon

public final class XORCipher: CipherMethod {
    public init() {}

    public func encrypt(_ input: String, params: CipherParams) throws -> String {
        let key = try effectiveKey(params)
        let inBytes = [UInt8](input.utf8)
        let keyBytes = [UInt8](key.utf8)
        let outBytes = zip(inBytes.indices, inBytes).map { (i, b) in b ^ keyBytes[i % keyBytes.count] }
        return Data(outBytes).base64EncodedString()
    }

    public func decrypt(_ input: String, params: CipherParams) throws -> String {
        let key = try effectiveKey(params)
        guard let data = Data(base64Encoded: input) else { throw CryptoError.invalidBase64 }
        let inBytes = [UInt8](data)
        let keyBytes = [UInt8](key.utf8)
        let outBytes = zip(inBytes.indices, inBytes).map { (i, b) in b ^ keyBytes[i % keyBytes.count] }
        guard let text = String(bytes: outBytes, encoding: .utf8) else { throw CryptoError.invalidKey }
        return text
    }

    private func effectiveKey(_ params: CipherParams) throws -> String {
        let base = try Validation.requireKey(params.key)
        if params.useSalt, let salt = params.salt, !salt.isEmpty {
            return base + salt
        }
        return base
    }
}
