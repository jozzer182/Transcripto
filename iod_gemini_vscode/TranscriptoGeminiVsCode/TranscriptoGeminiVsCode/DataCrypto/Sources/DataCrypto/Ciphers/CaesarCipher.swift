import Foundation
import Domain

public final class CaesarCipher: CipherMethod {
    public init() {}

    public func encrypt(_ input: String, params: CipherParams) throws -> String {
        guard let shift = params.shift else {
            throw CipherError.invalidKey(reason: "El desplazamiento para el cifrado César es obligatorio.")
        }
        
        let finalShift = adjustShift(baseShift: shift, salt: params.salt)
        return transform(input, shift: finalShift)
    }

    public func decrypt(_ input: String, params: CipherParams) throws -> String {
        guard let shift = params.shift else {
            throw CipherError.invalidKey(reason: "El desplazamiento para el cifrado César es obligatorio.")
        }
        
        let finalShift = adjustShift(baseShift: shift, salt: params.salt)
        return transform(input, shift: -finalShift)
    }

    private func adjustShift(baseShift: Int, salt: String?) -> Int {
        guard let salt = salt, !salt.isEmpty else {
            return baseShift
        }
        let saltHash = abs(salt.hashValue)
        return baseShift + (saltHash % 26)
    }

    private func transform(_ text: String, shift: Int) -> String {
        let aLower = Unicode.Scalar("a").value
        let aUpper = Unicode.Scalar("A").value
        let alphabetCount = 26

        var result = ""
        for char in text.unicodeScalars {
            let value = char.value
            var newScalar: Unicode.Scalar = char

            if value >= aLower && value < aLower + UInt32(alphabetCount) {
                var shiftedValue = Int(value) - Int(aLower) + shift
                shiftedValue = (shiftedValue % alphabetCount + alphabetCount) % alphabetCount
                newScalar = Unicode.Scalar(UInt32(shiftedValue) + aLower)!
            } else if value >= aUpper && value < aUpper + UInt32(alphabetCount) {
                var shiftedValue = Int(value) - Int(aUpper) + shift
                shiftedValue = (shiftedValue % alphabetCount + alphabetCount) % alphabetCount
                newScalar = Unicode.Scalar(UInt32(shiftedValue) + aUpper)!
            }
            
            result.append(String(newScalar))
        }
        return result
    }
}
