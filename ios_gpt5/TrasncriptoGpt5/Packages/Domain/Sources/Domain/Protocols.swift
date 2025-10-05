import Foundation

public protocol CipherMethod {
    func encrypt(_ input: String, params: CipherParams) throws -> String
    func decrypt(_ input: String, params: CipherParams) throws -> String
}

public protocol SaltProvider {
    func generateSalt(length: Int) throws -> String
}
