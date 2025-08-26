import Foundation
import SwiftUI
import Combine
import Domain
import DataCrypto
import CoreCommon

@MainActor
final class TranscriptoViewModel: ObservableObject {

    // MARK: - State
    @Published var inputText: String = ""
    @Published var outputText: String = ""
    @Published var error: String?

    @AppStorage("last_mode") var mode: OperationMode = .encrypt
    @AppStorage("last_method") var method: CipherMethodType = .caesar
    @AppStorage("last_use_salt") var useSalt: Bool = false
    
    @Published var saltOption: SaltOption = .auto
    @Published var manualSalt: String = ""
    @Published var generatedSalt: String = ""
    
    @Published var vigenereKey: String = ""
    @Published var xorKey: String = ""
    @Published var caesarShift: String = "3"

    @Published var showShareSheet = false

    // MARK: - Dependencies
    private let saltProvider: SaltProvider
    private var cancellables = Set<AnyCancellable>()

    // MARK: - Init
    init(saltProvider: SaltProvider = SecureSaltProvider()) {
        self.saltProvider = saltProvider
    }

    // MARK: - Actions
    func process() {
        self.error = nil
        self.outputText = ""

        do {
            let cipher = CipherFactory.makeCipher(for: method)
            let params = try makeCipherParams()
            
            let useCase: any CipherUseCase = switch mode {
            case .encrypt: EncryptTextUseCase(cipher: cipher)
            case .decrypt: DecryptTextUseCase(cipher: cipher)
            }

            let result = try useCase.execute(input: inputText, params: params)
            self.outputText = result

        } catch let e as CipherError {
            self.error = e.localizedDescription
        } catch {
            self.error = "Error desconocido: \(error.localizedDescription)"
        }
    }

    func copyToClipboard() {
        guard !outputText.isEmpty else { return }
        UIPasteboard.general.string = outputText
        // Opcional: mostrar un feedback al usuario.
    }

    func share() {
        guard !outputText.isEmpty else { return }
        showShareSheet = true
    }

    func clear() {
        inputText = ""
        outputText = ""
        error = nil
        vigenereKey = ""
        xorKey = ""
        caesarShift = "3"
        manualSalt = ""
        generatedSalt = ""
    }
    
    func generateSaltIfNeeded() {
        guard useSalt, saltOption == .auto else {
            generatedSalt = ""
            return
        }
        
        do {
            let generateSaltUseCase = GenerateSaltUseCase(saltProvider: saltProvider)
            generatedSalt = try generateSaltUseCase.execute()
        } catch {
            self.error = "Error al generar salt: \(error.localizedDescription)"
            generatedSalt = ""
        }
    }

    // MARK: - Private Helpers
    private func makeCipherParams() throws -> CipherParams {
        let salt: String?
        if useSalt {
            switch saltOption {
            case .auto:
                if generatedSalt.isEmpty { generateSaltIfNeeded() }
                salt = generatedSalt
            case .manual:
                guard !manualSalt.isEmpty else { throw CipherError.invalidSalt }
                salt = manualSalt
            }
        } else {
            salt = nil
        }

        switch method {
        case .caesar:
            guard let shift = Int(caesarShift) else {
                throw CipherError.invalidKey(reason: "El desplazamiento debe ser un número entero.")
            }
            return CipherParams(method: .caesar, shift: shift, salt: salt)
        case .vigenere:
            return CipherParams(method: .vigenere, key: vigenereKey, salt: salt)
        case .xor:
            return CipherParams(method: .xor, key: xorKey, salt: salt)
        case .base64:
            return CipherParams(method: .base64, salt: salt)
        }
    }
}

// MARK: - Enums
extension TranscriptoViewModel {
    enum OperationMode: String, CaseIterable, Identifiable {
        case encrypt = "Cifrar"
        case decrypt = "Descifrar"
        var id: String { self.rawValue }
    }

    enum SaltOption: String, CaseIterable, Identifiable {
        case auto = "Generar automáticamente"
        case manual = "Ingresar manualmente"
        var id: String { self.rawValue }
    }
}

// Protocolo para unificar los casos de uso
private protocol CipherUseCase {
    func execute(input: String, params: CipherParams) throws -> String
}
extension EncryptTextUseCase: CipherUseCase {}
extension DecryptTextUseCase: CipherUseCase {}
