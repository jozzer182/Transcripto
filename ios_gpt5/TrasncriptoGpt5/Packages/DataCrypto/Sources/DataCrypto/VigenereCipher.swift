import Foundation
import Domain
import CoreCommon

public final class VigenereCipher: CipherMethod {
    public init() {}

    public func encrypt(_ input: String, params: CipherParams) throws -> String {
        let key = try effectiveKey(params)
        return try Self.vigenere(text: input, key: key, encrypting: true)
    }

    public func decrypt(_ input: String, params: CipherParams) throws -> String {
        let key = try effectiveKey(params)
        return try Self.vigenere(text: input, key: key, encrypting: false)
    }

    private func effectiveKey(_ params: CipherParams) throws -> String {
        let base = try Validation.requireKey(params.key)
        if params.useSalt, let salt = params.salt, !salt.isEmpty {
            return base + salt
        }
        return base
    }

    private static func vigenere(text: String, key: String, encrypting: Bool) throws -> String {
        guard !key.isEmpty else { throw CryptoError.missingKey }
        let letters = Array("ABCDEFGHIJKLMNOPQRSTUVWXYZ")
        let lower = Array("abcdefghijklmnopqrstuvwxyz")
        let keyArr = Array(key)
        var out = String(); out.reserveCapacity(text.count)
        var j = 0
        for ch in text {
            let kch = keyArr[j % keyArr.count]
            if let idx = letters.firstIndex(of: ch) {
                let k = letters.firstIndex(of: Character(kch.uppercased()))!
                let pos = letters.distance(from: letters.startIndex, to: idx)
                let shift = encrypting ? k : -k
                let newPos = (pos + shift % 26 + 26) % 26
                out.append(letters[newPos])
                j += 1
            } else if let idx = lower.firstIndex(of: ch) {
                let k = lower.firstIndex(of: Character(kch.lowercased())) ?? lower.firstIndex(of: Character(kch.uppercased()))
                let kpos = k.map { lower.distance(from: lower.startIndex, to: $0) } ?? 0
                let pos = lower.distance(from: lower.startIndex, to: idx)
                let shift = encrypting ? kpos : -kpos
                let newPos = (pos + shift % 26 + 26) % 26
                out.append(lower[newPos])
                j += 1
            } else {
                out.append(ch)
            }
        }
        return out
    }
}
