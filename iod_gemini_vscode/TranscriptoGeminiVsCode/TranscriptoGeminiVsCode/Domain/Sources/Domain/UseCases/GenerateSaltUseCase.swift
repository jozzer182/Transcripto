import Foundation

public final class GenerateSaltUseCase {
    private let saltProvider: SaltProvider

    public init(saltProvider: SaltProvider) {
        self.saltProvider = saltProvider
    }

    public func execute() throws -> String {
        try saltProvider.generateSalt()
    }
}
