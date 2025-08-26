import Foundation

/// Un error que puede ocurrir durante el cifrado o descifrado.
public enum CipherError: Error, LocalizedError {
    case invalidInput(reason: String)
    case invalidKey(reason: String)
    case invalidSalt
    case internalError(description: String)

    public var errorDescription: String? {
        switch self {
        case .invalidInput(let reason):
            return "Entrada inválida: \(reason)"
        case .invalidKey(let reason):
            return "Clave inválida: \(reason)"
        case .invalidSalt:
            return "Salt inválido."
        case .internalError(let description):
            return "Error interno: \(description)"
        }
    }
}
