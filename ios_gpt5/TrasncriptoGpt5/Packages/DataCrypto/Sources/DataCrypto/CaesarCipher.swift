import Foundation
import Domain
import CoreCommon

public final class CaesarCipher: CipherMethod {
    public init() {}

    public func encrypt(_ input: String, params: CipherParams) throws -> String {
    let total = (params.shift ?? 0) + ((params.useSalt ? (params.salt.map(Self.saltAdjust) ?? 0) : 0) % 26)
    return Self.caesar(text: input, shift: total)
    }

    public func decrypt(_ input: String, params: CipherParams) throws -> String {
    let total = (params.shift ?? 0) + ((params.useSalt ? (params.salt.map(Self.saltAdjust) ?? 0) : 0) % 26)
    return Self.caesar(text: input, shift: -total)
    }

    private static func saltAdjust(_ salt: String) -> Int {
        var hash = 0
        for u in salt.utf8 { hash = (hash &* 31 &+ Int(u)) & 0x7fffffff }
        return hash
    }

    private static func caesar(text: String, shift: Int) -> String {
        guard shift != 0 else { return text }
        let upper = Array("ABCDEFGHIJKLMNOPQRSTUVWXYZ")
        let lower = Array("abcdefghijklmnopqrstuvwxyz")
        let uCount = upper.count
        let lCount = lower.count
        var result = String()
        result.reserveCapacity(text.count)
        for ch in text {
            if let idx = upper.firstIndex(of: ch) {
                let pos = upper.distance(from: upper.startIndex, to: idx)
                let newPos = (pos + shift % uCount + uCount) % uCount
                result.append(upper[newPos])
            } else if let idx = lower.firstIndex(of: ch) {
                let pos = lower.distance(from: lower.startIndex, to: idx)
                let newPos = (pos + shift % lCount + lCount) % lCount
                result.append(lower[newPos])
            } else {
                result.append(ch)
            }
        }
        return result
    }
}
