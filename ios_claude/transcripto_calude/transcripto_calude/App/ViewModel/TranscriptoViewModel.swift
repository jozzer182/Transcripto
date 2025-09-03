//
//  TranscriptoViewModel.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import Foundation
import SwiftUI
import UIKit

/// ViewModel principal de la aplicación Transcripto
@MainActor
public class TranscriptoViewModel: ObservableObject {
    
    // MARK: - Published Properties
    @Published var inputText: String = ""
    @Published var outputText: String = ""
    @Published var operationMode: OperationMode = .encrypt
    @Published var selectedMethod: CipherType = .caesar
    @Published var useSalt: Bool = false
    @Published var saltAuto: Bool = true
    @Published var saltManual: String = ""
    @Published var key: String = ""
    @Published var shift: String = "3"
    @Published var errorMessage: String?
    @Published var isProcessing: Bool = false
    @Published var showSuccessMessage: Bool = false
    
    // MARK: - AppStorage for Persistence
    @AppStorage("selectedMethod") private var persistedMethod: String = CipherType.caesar.rawValue
    @AppStorage("operationMode") private var persistedMode: String = OperationMode.encrypt.rawValue
    @AppStorage("useSalt") private var persistedUseSalt: Bool = false
    @AppStorage("saltAuto") private var persistedSaltAuto: Bool = true
    
    // MARK: - Dependencies
    private let encryptUseCase: EncryptTextUseCase
    private let decryptUseCase: DecryptTextUseCase
    private let saltProvider: SaltProvider
    
    // MARK: - Computed Properties
    var currentSalt: String? {
        guard useSalt else { return nil }
        return saltAuto ? generatedSalt : (saltManual.isEmpty ? nil : saltManual)
    }
    
    var shiftValue: Int? {
        Int(shift)
    }
    
    var isProcessButtonEnabled: Bool {
        !inputText.isEmpty && !isProcessing
    }
    
    var methodRequiresKey: Bool {
        selectedMethod.requiresKey
    }
    
    var methodRequiresShift: Bool {
        selectedMethod.requiresShift
    }
    
    // MARK: - Private Properties
    private var generatedSalt: String = ""
    
    // MARK: - Initialization
    public init(
        encryptUseCase: EncryptTextUseCase? = nil,
        decryptUseCase: DecryptTextUseCase? = nil,
        saltProvider: SaltProvider? = nil
    ) {
        // Configurar métodos de cifrado por defecto
        let defaultCipherMethods = TranscriptoViewModel.createDefaultCipherMethods()
        
        self.encryptUseCase = encryptUseCase ?? EncryptTextUseCase(cipherMethods: defaultCipherMethods)
        self.decryptUseCase = decryptUseCase ?? DecryptTextUseCase(cipherMethods: defaultCipherMethods)
        self.saltProvider = saltProvider ?? SaltProvider()
        
        loadPersistedSettings()
        generateNewSalt()
    }
    
    // MARK: - Private Helper Methods
    
    private static func createDefaultCipherMethods() -> [CipherType: CipherMethod] {
        return [
            .caesar: CaesarCipher(),
            .base64: Base64Codec(),
            .vigenere: VigenereCipher(),
            .xor: XorCipher()
        ]
    }
    
    // MARK: - Public Methods
    
    /// Procesa el texto según el modo y método seleccionados
    func process() {
        Task {
            await performProcess()
        }
    }
    
    /// Copia el texto de salida al portapapeles
    func copyToClipboard() {
        guard !outputText.isEmpty else { return }
        
        UIPasteboard.general.string = outputText
        showSuccessMessage = true
        
        // Haptic feedback
        let impactFeedback = UIImpactFeedbackGenerator(style: .light)
        impactFeedback.impactOccurred()
        
        // Auto-hide success message
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            self.showSuccessMessage = false
        }
    }
    
    /// Comparte el texto de salida usando ShareSheet
    func shareText() -> Bool {
        return !outputText.isEmpty
    }
    
    /// Limpia todos los campos
    func clear() {
        inputText = ""
        outputText = ""
        errorMessage = nil
        key = ""
        saltManual = ""
        
        // Haptic feedback
        let impactFeedback = UIImpactFeedbackGenerator(style: .medium)
        impactFeedback.impactOccurred()
    }
    
    /// Genera un nuevo salt automático
    func generateNewSalt() {
        do {
            generatedSalt = try saltProvider.generateSalt()
        } catch {
            generatedSalt = "fallback" + String(Int.random(in: 1000...9999))
        }
    }
    
    /// Maneja el cambio de método de cifrado
    func methodChanged() {
        // Limpiar campos no relevantes
        if !selectedMethod.requiresKey {
            key = ""
        }
        if !selectedMethod.requiresShift {
            shift = "3"
        }
        
        // Limpiar errores y salida
        errorMessage = nil
        outputText = ""
        
        // Persistir cambio
        persistedMethod = selectedMethod.rawValue
    }
    
    /// Maneja el cambio de modo de operación
    func modeChanged() {
        errorMessage = nil
        outputText = ""
        persistedMode = operationMode.rawValue
    }
    
    /// Maneja el cambio en el uso de salt
    func saltUsageChanged() {
        if useSalt && saltAuto {
            generateNewSalt()
        }
        errorMessage = nil
        outputText = ""
        persistedUseSalt = useSalt
    }
    
    /// Maneja el cambio entre salt automático y manual
    func saltModeChanged() {
        if saltAuto {
            generateNewSalt()
        }
        errorMessage = nil
        outputText = ""
        persistedSaltAuto = saltAuto
    }
    
    // MARK: - Private Methods
    
    private func loadPersistedSettings() {
        selectedMethod = CipherType(rawValue: persistedMethod) ?? .caesar
        operationMode = OperationMode(rawValue: persistedMode) ?? .encrypt
        useSalt = persistedUseSalt
        saltAuto = persistedSaltAuto
    }
    
    private func performProcess() async {
        isProcessing = true
        errorMessage = nil
        
        do {
            let params = CipherParams(
                method: selectedMethod,
                key: methodRequiresKey ? key : nil,
                shift: methodRequiresShift ? shiftValue : nil,
                salt: currentSalt
            )
            
            let result: String
            switch operationMode {
            case .encrypt:
                result = try encryptUseCase.execute(text: inputText, params: params)
            case .decrypt:
                result = try decryptUseCase.execute(text: inputText, params: params)
            }
            
            outputText = result
            
            // Haptic feedback de éxito
            let notificationFeedback = UINotificationFeedbackGenerator()
            notificationFeedback.notificationOccurred(.success)
            
        } catch {
            errorMessage = error.localizedDescription
            outputText = ""
            
            // Haptic feedback de error
            let notificationFeedback = UINotificationFeedbackGenerator()
            notificationFeedback.notificationOccurred(.error)
        }
        
        isProcessing = false
    }
}

// MARK: - Supporting Types

/// Modo de operación (cifrar o descifrar)
public enum OperationMode: String, CaseIterable {
    case encrypt = "encrypt"
    case decrypt = "decrypt"
    
    public var displayName: String {
        switch self {
        case .encrypt:
            return "Cifrar"
        case .decrypt:
            return "Descifrar"
        }
    }
}
