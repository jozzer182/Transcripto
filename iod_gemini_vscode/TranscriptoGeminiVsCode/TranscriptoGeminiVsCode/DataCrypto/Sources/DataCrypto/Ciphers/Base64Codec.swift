import Foundation
import Domain

public final class Base64Codec: CipherMethod {
    public init() {}

    public func encrypt(_ input: String, params: CipherParams) throws -> String {
        guard let data = input.data(using: .utf8) else {
            throw CipherError.invalidInput(reason: "No se pudo codificar el texto a UTF-8.")
        }
        
        var dataToEncode = data
        if let salt = params.salt, !salt.isEmpty {
            let prefix = "\(salt):"
            guard let saltData = prefix.data(using: .utf8) else {
                throw CipherError.internalError(description: "No se pudo codificar el salt.")
            }
            dataToEncode.insert(contentsOf: saltData, at: 0)
        }
        
        return dataToEncode.base64EncodedString()
    }

    public func decrypt(_ input: String, params: CipherParams) throws -> String {
        guard let data = Data(base64Encoded: input) else {
            throw CipherError.invalidInput(reason: "El texto de entrada no es un Base64 válido.")
        }

        var dataToDecode = data
        if let salt = params.salt, !salt.isEmpty {
            let prefix = "\(salt):"
            guard let saltData = prefix.data(using: .utf8),
                  data.starts(with: saltData) else {
                // No es un error fatal, solo una advertencia. Devolvemos la decodificación sin quitar el prefijo.
                // El usuario podría haber olvidado el salt.
                print("Advertencia: El salt proporcionado no se encontró como prefijo en los datos decodificados.")
                return String(data: data, encoding: .utf8) ?? ""
            }
            dataToDecode.removeFirst(saltData.count)
        }

        guard let decodedString = String(data: dataToDecode, encoding: .utf8) else {
            throw CipherError.invalidInput(reason: "No se pudo decodificar el resultado a un texto UTF-8 válido.")
        }
        
        return decodedString
    }
}
