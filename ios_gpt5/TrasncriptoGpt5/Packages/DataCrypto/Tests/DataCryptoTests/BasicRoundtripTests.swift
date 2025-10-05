import XCTest
@testable import DataCrypto
import Domain

final class BasicRoundtripTests: XCTestCase {
    func testRoundtrip_XOR_Base64() throws {
        let (enc, dec, _) = CryptoFactory.makeUseCases()
        let params = Domain.CipherParams(method: .xor, key: "clave", salt: nil, useSalt: false)
        let input = "Prueba 😀"
        let cipher = try enc(input, params: params)
        XCTAssertNotEqual(cipher, input)
        let plain = try dec(cipher, params: params)
        XCTAssertEqual(plain, input)
    }
}
