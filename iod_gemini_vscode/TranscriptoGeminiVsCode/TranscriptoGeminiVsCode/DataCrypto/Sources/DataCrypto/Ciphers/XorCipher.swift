import Foundation
import Domain

public final class XorCipher: CipherMethod {
    public init() {}

    public func encrypt(_ input: String, params: CipherParams) throws -> String {
        let key = try getEffectiveKey(from: params)
        guard let inputData = input.data(using: .utf8) else {
            throw CipherError.invalidInput(reason: "No se pudo codificar el texto a UTF-8.")
        }
        let keyData = key.data(using: .utf8)!
        
        let resultData = xor(data: inputData, key: keyData)
        return resultData.base64EncodedString()
    }

    public func decrypt(_ input: String, params: CipherParams) throws -> String {
        let key = try getEffectiveKey(from: params)
        guard let inputData = Data(base64Encoded: input) else {
            throw CipherError.invalidInput(reason: "El texto de entrada no es un Base64 válido.")
        }
        let keyData = key.data(using: .utf8)!

        let resultData = xor(data: inputData, key: keyData)
        
        guard let resultString = String(data: resultData, encoding: .utf8) else {
            throw CipherError.invalidInput(reason: "El resultado del descifrado no es un texto UTF-8 válido.")
        }
        return resultString
    }

    private func getEffectiveKey(from params: CipherParams) throws -> String {
        guard var key = params.key, !key.isEmpty else {
            throw CipherError.invalidKey(reason: "La clave para XOR no puede estar vacía.")
        }
        if let salt = params.salt, !salt.isEmpty {
            key += salt
        }
        return key
    }

    private func xor(data: Data, key: Data) -> Data {
        var result = Data(count: data.count)
        for i in 0..<data.count {
            result[i] = data[i] ^ key[i % key.count]
        }
        return result
    }
}
