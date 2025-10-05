import XCTest
@testable import DataCrypto
import Domain
import CoreCommon

final class Base64CipherTests: XCTestCase {
    let base64 = Base64Cipher()

    func testRoundtrip_noSalt() throws {
        let params = CipherParams(method: .base64, useSalt: false)
        let input = "Hola Base64"
        let enc = try base64.encrypt(input, params: params)
        let dec = try base64.decrypt(enc, params: params)
        XCTAssertEqual(dec, input)
    }

    func testRoundtrip_withSalt() throws {
        let params = CipherParams(method: .base64, salt: "pepper", useSalt: true)
        let input = "Hola con sal"
        let enc = try base64.encrypt(input, params: params)
        let dec = try base64.decrypt(enc, params: params)
        XCTAssertEqual(dec, input)
    }

    func testDecrypt_invalidBase64() {
        let params = CipherParams(method: .base64)
        XCTAssertThrowsError(try base64.decrypt("***", params: params)) { error in
            XCTAssertEqual(error as? CryptoError, .invalidBase64)
        }
    }

    func testDecrypt_saltMismatch() throws {
        let encParams = CipherParams(method: .base64, salt: "a", useSalt: true)
        let decParams = CipherParams(method: .base64, salt: "b", useSalt: true)
        let input = "hola"
        let enc = try base64.encrypt(input, params: encParams)
        XCTAssertThrowsError(try base64.decrypt(enc, params: decParams)) { error in
            XCTAssertEqual(error as? CryptoError, .saltMismatch)
        }
    }
}
