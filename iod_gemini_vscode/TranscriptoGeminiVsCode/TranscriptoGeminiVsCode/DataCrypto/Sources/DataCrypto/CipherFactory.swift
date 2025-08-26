import Foundation
import Domain

public struct CipherFactory {
    public static func makeCipher(for method: CipherMethodType) -> CipherMethod {
        switch method {
        case .caesar:
            return CaesarCipher()
        case .vigenere:
            return VigenereCipher()
        case .xor:
            return XorCipher()
        case .base64:
            return Base64Codec()
        }
    }
}
