import Foundation

public struct EncryptText {
    let methods: [CipherMethodType: CipherMethod]

    public init(methods: [CipherMethodType: CipherMethod]) {
        self.methods = methods
    }

    public func callAsFunction(_ input: String, params: CipherParams) throws -> String {
        guard let method = methods[params.method] else { fatalError("Método no implementado") }
        return try method.encrypt(input, params: params)
    }
}

public struct DecryptText {
    let methods: [CipherMethodType: CipherMethod]

    public init(methods: [CipherMethodType: CipherMethod]) {
        self.methods = methods
    }

    public func callAsFunction(_ input: String, params: CipherParams) throws -> String {
        guard let method = methods[params.method] else { fatalError("Método no implementado") }
        return try method.decrypt(input, params: params)
    }
}

public struct GenerateSalt {
    let provider: SaltProvider
    public init(provider: SaltProvider) { self.provider = provider }
    public func callAsFunction(length: Int = 12) throws -> String { try provider.generateSalt(length: length) }
}
