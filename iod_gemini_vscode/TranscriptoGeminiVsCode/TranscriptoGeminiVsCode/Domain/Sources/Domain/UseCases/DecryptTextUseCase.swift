import Foundation

public final class DecryptTextUseCase {
    private let cipher: CipherMethod

    public init(cipher: CipherMethod) {
        self.cipher = cipher
    }

    public func execute(input: String, params: CipherParams) throws -> String {
        try cipher.decrypt(input, params: params)
    }
}
