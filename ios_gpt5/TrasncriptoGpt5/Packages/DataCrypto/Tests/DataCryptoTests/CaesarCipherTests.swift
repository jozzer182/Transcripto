import XCTest
@testable import DataCrypto
import Domain

final class CaesarCipherTests: XCTestCase {
    let caesar = CaesarCipher()

    func testGolden_shift3() throws {
        let input = "Abc XYZ"
        let params = CipherParams(method: .caesar, shift: 3)
        let enc = try caesar.encrypt(input, params: params)
        XCTAssertEqual(enc, "Def ABC")
        let dec = try caesar.decrypt(enc, params: params)
        XCTAssertEqual(dec, input)
    }

    func testSaltAdjust_affectsShift() throws {
        let input = "Abc"
        let p1 = CipherParams(method: .caesar, shift: 2, salt: "s", useSalt: true)
        let p2 = CipherParams(method: .caesar, shift: 2, salt: "t", useSalt: true)
        let e1 = try caesar.encrypt(input, params: p1)
        let e2 = try caesar.encrypt(input, params: p2)
        XCTAssertNotEqual(e1, e2)
    }
}
