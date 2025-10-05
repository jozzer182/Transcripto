import XCTest
@testable import DataCrypto
import Domain
import CoreCommon

final class XORCipherTests: XCTestCase {
    let xor = XORCipher()

    func testRoundtrip_noSalt() throws {
        let input = "¡XOR con emojis 😀!"
        let params = CipherParams(method: .xor, key: "k")
        let enc = try xor.encrypt(input, params: params)
        XCTAssertNotEqual(enc, input)
        let dec = try xor.decrypt(enc, params: params)
        XCTAssertEqual(dec, input)
    }

    func testDecrypt_invalidBase64_throws() {
        let params = CipherParams(method: .xor, key: "k")
        XCTAssertThrowsError(try xor.decrypt("###", params: params)) { error in
            XCTAssertEqual(error as? CryptoError, .invalidBase64)
        }
    }
}
