import XCTest
@testable import DataCrypto
import Domain

final class CipherTests: XCTestCase {

    // MARK: - Caesar Cipher Tests

    func testCaesarCipher_EncryptDecrypt_Simple() throws {
        let cipher = CaesarCipher()
        let text = "Hello, World!"
        let params = CipherParams(method: .caesar, shift: 3)
        let encrypted = try cipher.encrypt(text, params: params)
        XCTAssertNotEqual(text, encrypted)
        let decrypted = try cipher.decrypt(encrypted, params: params)
        XCTAssertEqual(text, decrypted)
    }

    func testCaesarCipher_EncryptDecrypt_WithNegativeShift() throws {
        let cipher = CaesarCipher()
        let text = "Abc XYZ"
        let params = CipherParams(method: .caesar, shift: -5)
        let encrypted = try cipher.encrypt(text, params: params)
        XCTAssertEqual(encrypted, "Vwx UVW")
        let decrypted = try cipher.decrypt(encrypted, params: params)
        XCTAssertEqual(text, decrypted)
    }
    
    func testCaesarCipher_WithSalt() throws {
        let cipher = CaesarCipher()
        let text = "Attack at dawn"
        let salt = "somesalt"
        let params = CipherParams(method: .caesar, shift: 10, salt: salt)
        let encrypted = try cipher.encrypt(text, params: params)
        let decrypted = try cipher.decrypt(encrypted, params: params)
        XCTAssertEqual(text, decrypted)
    }

    // MARK: - Vigenere Cipher Tests

    func testVigenereCipher_EncryptDecrypt_Simple() throws {
        let cipher = VigenereCipher()
        let text = "Hello World"
        let params = CipherParams(method: .vigenere, key: "KEY")
        let encrypted = try cipher.encrypt(text, params: params)
        XCTAssertEqual(encrypted, "Rijvs Uyvjn")
        let decrypted = try cipher.decrypt(encrypted, params: params)
        XCTAssertEqual(text, decrypted)
    }
    
    func testVigenereCipher_WithSalt() throws {
        let cipher = VigenereCipher()
        let text = "This is a secret message."
        let key = "password"
        let salt = "secretsalt"
        let params = CipherParams(method: .vigenere, key: key, salt: salt)
        let encrypted = try cipher.encrypt(text, params: params)
        let decrypted = try cipher.decrypt(encrypted, params: params)
        XCTAssertEqual(text, decrypted)
    }
    
    func testVigenereCipher_InvalidKey() {
        let cipher = VigenereCipher()
        let text = "test"
        let params = CipherParams(method: .vigenere, key: "123")
        XCTAssertThrowsError(try cipher.encrypt(text, params: params))
    }

    // MARK: - XOR Cipher Tests

    func testXorCipher_EncryptDecrypt_Simple() throws {
        let cipher = XorCipher()
        let text = "Hello, XOR!"
        let params = CipherParams(method: .xor, key: "secret")
        let encryptedB64 = try cipher.encrypt(text, params: params)
        
        // Ensure it's valid Base64
        XCTAssertNotNil(Data(base64Encoded: encryptedB64))
        
        let decrypted = try cipher.decrypt(encryptedB64, params: params)
        XCTAssertEqual(text, decrypted)
    }
    
    func testXorCipher_WithSalt() throws {
        let cipher = XorCipher()
        let text = "Another test with 🤫 emoji"
        let key = "mykey"
        let salt = "mysalt"
        let params = CipherParams(method: .xor, key: key, salt: salt)
        let encrypted = try cipher.encrypt(text, params: params)
        let decrypted = try cipher.decrypt(encrypted, params: params)
        XCTAssertEqual(text, decrypted)
    }
    
    func testXorCipher_InvalidBase64Input() {
        let cipher = XorCipher()
        let invalidInput = "this is not base64"
        let params = CipherParams(method: .xor, key: "key")
        XCTAssertThrowsError(try cipher.decrypt(invalidInput, params: params))
    }

    // MARK: - Base64 Codec Tests

    func testBase64Codec_EncodeDecode() throws {
        let codec = Base64Codec()
        let text = "This is a test. Con ñ y acentos."
        let params = CipherParams(method: .base64)
        let encoded = try codec.encrypt(text, params: params)
        let decoded = try codec.decrypt(encoded, params: params)
        XCTAssertEqual(text, decoded)
    }
    
    func testBase64Codec_WithSalt() throws {
        let codec = Base64Codec()
        let text = "Text with salt"
        let salt = "saltyprefix"
        let params = CipherParams(method: .base64, salt: salt)
        let encoded = try codec.encrypt(text, params: params)
        
        // Manually decode to check prefix
        let data = Data(base64Encoded: encoded)!
        let decodedString = String(data: data, encoding: .utf8)!
        XCTAssertTrue(decodedString.starts(with: "\(salt):"))
        
        let decoded = try codec.decrypt(encoded, params: params)
        XCTAssertEqual(text, decoded)
    }
    
    func testBase64Codec_DecryptWithMissingSalt() throws {
        let codec = Base64Codec()
        let text = "some text"
        let salt = "the_salt"
        
        // Encrypt without salt
        let noSaltParams = CipherParams(method: .base64)
        let encoded = try codec.encrypt(text, params: noSaltParams)
        
        // Try to decrypt with salt
        let saltParams = CipherParams(method: .base64, salt: salt)
        let decoded = try codec.decrypt(encoded, params: saltParams)
        
        // It should just return the decoded string, without crashing
        XCTAssertEqual(text, decoded)
    }
}
