import XCTest
@testable import DataCrypto
@testable import Domain

final class TranscriptoGpt5Tests: XCTestCase {
    func testBase64Roundtrip_noSalt() throws {
        let (enc, dec, _) = CryptoFactory.makeUseCases()
        let params = CipherParams(method: .base64)
        let input = "Hola 😀"
        let out = try enc(input, params: params)
        let back = try dec(out, params: params)
        XCTAssertEqual(back, input)
    }
}
