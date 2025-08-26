import Foundation

public final class EncryptTextUseCase {
    private let cipher: CipherMethod

    public init(cipher: CipherMethod) {
        self.cipher = cipher
    }

    public func execute(input: String, params: CipherParams) throws -> String {
        try cipher.encrypt(input, params: params)
    }
}
