import Foundation
import Domain

public final class VigenereCipher: CipherMethod {
    public init() {}

    public func encrypt(_ input: String, params: CipherParams) throws -> String {
        let effectiveKey = try getEffectiveKey(from: params)
        return transform(text: input, key: effectiveKey, direction: 1)
    }

    public func decrypt(_ input: String, params: CipherParams) throws -> String {
        let effectiveKey = try getEffectiveKey(from: params)
        return transform(text: input, key: effectiveKey, direction: -1)
    }

    private func getEffectiveKey(from params: CipherParams) throws -> String {
        guard var key = params.key, !key.isEmpty else {
            throw CipherError.invalidKey(reason: "La clave para Vigenère no puede estar vacía.")
        }
        if let salt = params.salt, !salt.isEmpty {
            key += salt
        }
        let normalizedKey = key.unicodeScalars
            .filter { CharacterSet.letters.contains($0) }
            .map { String($0) }
            .joined()
        
        if normalizedKey.isEmpty {
            throw CipherError.invalidKey(reason: "La clave efectiva (clave + salt) no contiene caracteres alfabéticos.")
        }
        return normalizedKey
    }

    private func transform(text: String, key: String, direction: Int) -> String {
        let keyScalars = Array(key.uppercased().unicodeScalars)
        var keyIndex = 0
        var result = ""

        for charScalar in text.unicodeScalars {
            if CharacterSet.letters.contains(charScalar) {
                let firstLetter: Unicode.Scalar = charScalar.isUpper ? "A" : "a"
                let keyShift = Int(keyScalars[keyIndex].value - Unicode.Scalar("A").value)
                
                var charValue = Int(charScalar.value - firstLetter.value)
                charValue = (charValue + direction * keyShift + 26) % 26
                
                result.append(String(Unicode.Scalar(UInt32(charValue) + firstLetter.value)!))
                
                keyIndex = (keyIndex + 1) % keyScalars.count
            } else {
                result.append(String(charScalar))
            }
        }
        return result
    }
}

extension Unicode.Scalar {
    var isUpper: Bool {
        return CharacterSet.uppercaseLetters.contains(self)
    }
}
