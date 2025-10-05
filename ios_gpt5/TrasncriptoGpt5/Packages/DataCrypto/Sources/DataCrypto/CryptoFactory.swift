import Foundation
import Domain

public struct CryptoFactory {
    public static func makeUseCases() -> (encrypt: EncryptText, decrypt: DecryptText, salt: GenerateSalt) {
        let base64 = Base64Cipher()
        let caesar = CaesarCipher()
        let vigenere = VigenereCipher()
        let xor = XORCipher()
        let salt = SystemSaltProvider()
        let map: [CipherMethodType: CipherMethod] = [
            .base64: base64,
            .caesar: caesar,
            .vigenere: vigenere,
            .xor: xor
        ]
        return (EncryptText(methods: map), DecryptText(methods: map), GenerateSalt(provider: salt))
    }
}
