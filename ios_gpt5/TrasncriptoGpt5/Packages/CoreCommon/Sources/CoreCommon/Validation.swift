import Foundation

public enum Validation {
    public static func requireNonEmpty(_ s: String?) throws -> String {
        guard let s, !s.isEmpty else { throw CryptoError.emptyInput }
        return s
    }

    public static func requireKey(_ s: String?) throws -> String {
        guard let s, !s.isEmpty else { throw CryptoError.missingKey }
        return s
    }
}
