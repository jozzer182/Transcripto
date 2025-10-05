import Foundation
import CoreCommon

public enum CipherMethodType: String, CaseIterable, Codable, Sendable {
    case base64
    case caesar
    case vigenere
    case xor
}

public struct CipherParams: Sendable {
    public var method: CipherMethodType
    public var key: String?
    public var shift: Int?
    public var salt: String?
    public var useSalt: Bool

    public init(method: CipherMethodType, key: String? = nil, shift: Int? = nil, salt: String? = nil, useSalt: Bool = false) {
        self.method = method
        self.key = key
        self.shift = shift
        self.salt = salt
        self.useSalt = useSalt
    }
}

public enum CipherMode: String, CaseIterable, Codable, Sendable {
    case encrypt
    case decrypt
}
