import Foundation

public enum CryptoError: LocalizedError, Equatable {
    case emptyInput
    case missingKey
    case invalidKey
    case invalidShift
    case invalidBase64
    case saltRequiredForDecryption
    case saltMismatch

    public var errorDescription: String? {
        switch self {
        case .emptyInput:
            return "El texto de entrada está vacío."
        case .missingKey:
            return "La clave es obligatoria para este método."
        case .invalidKey:
            return "La clave proporcionada no es válida."
        case .invalidShift:
            return "El desplazamiento debe ser un entero (positivo o negativo)."
        case .invalidBase64:
            return "El texto no es un Base64 válido."
        case .saltRequiredForDecryption:
            return "Para descifrar con salt, debes especificar el salt manualmente."
        case .saltMismatch:
            return "El salt no coincide con el contenido decodificado."
        }
    }
}
