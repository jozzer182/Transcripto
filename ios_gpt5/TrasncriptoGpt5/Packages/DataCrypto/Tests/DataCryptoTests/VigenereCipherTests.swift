import XCTest
@testable import DataCrypto
import Domain
import CoreCommon

final class VigenereCipherTests: XCTestCase {
    let v = VigenereCipher()

    func testRoundtrip_basic() throws {
        let input = "Hola Mundo"
        let params = CipherParams(method: .vigenere, key: "clave")
        let enc = try v.encrypt(input, params: params)
        let dec = try v.decrypt(enc, params: params)
        XCTAssertEqual(dec, input)
    }

    func testMissingKey_throws() {
        let params = CipherParams(method: .vigenere, key: "")
        XCTAssertThrowsError(try v.encrypt("hola", params: params)) { error in
            XCTAssertEqual(error as? CryptoError, .missingKey)
        }
    }
}
