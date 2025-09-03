//
//  TranscriptoScreen.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import SwiftUI

/// Pantalla principal de la aplicación Transcripto
public struct TranscriptoScreen: View {
    @StateObject private var viewModel = TranscriptoViewModel()
    @Environment(\.colorScheme) var colorScheme
    
    public init() {}
    
    public var body: some View {
        NavigationView {
            ScrollView {
                VStack(spacing: TranscriptoTheme.Spacing.large) {
                    // Mensaje de error si existe
                    if let errorMessage = viewModel.errorMessage {
                        ErrorMessageView(message: errorMessage) {
                            withAnimation(TranscriptoTheme.Animation.quick) {
                                viewModel.errorMessage = nil
                            }
                        }
                    }
                    
                    // Card principal
                    VStack(spacing: TranscriptoTheme.Spacing.large) {
                        headerSection
                        configurationSection
                        inputSection
                        outputSection
                        actionSection
                    }
                    .padding(TranscriptoTheme.Layout.cardPadding)
                    .transcriptoCard()
                }
                .padding(TranscriptoTheme.Spacing.medium)
            }
            .navigationTitle("Transcripto")
            .navigationBarTitleDisplayMode(.large)
        }
        .navigationViewStyle(StackNavigationViewStyle())
    }
    
    // MARK: - Sections
    
    private var headerSection: some View {
        VStack(spacing: TranscriptoTheme.Spacing.small) {
            Image(systemName: "shield.lefthalf.filled")
                .font(.system(size: 40))
                .foregroundColor(TranscriptoTheme.Colors.primary)
            
            Text("Cifrado y Descifrado de Texto")
                .font(TranscriptoTheme.Typography.headline)
                .foregroundColor(TranscriptoTheme.Colors.textPrimary)
                .multilineTextAlignment(.center)
        }
    }
    
    private var configurationSection: some View {
        VStack(spacing: TranscriptoTheme.Spacing.medium) {
            // Modo de operación
            VStack(alignment: .leading, spacing: TranscriptoTheme.Spacing.small) {
                Text("Modo")
                    .font(TranscriptoTheme.Typography.body)
                    .foregroundColor(TranscriptoTheme.Colors.textPrimary)
                
                SegmentedControlView(
                    selection: $viewModel.operationMode,
                    options: OperationMode.allCases,
                    displayNames: { $0.displayName },
                    onChange: viewModel.modeChanged
                )
            }
            
            // Método de cifrado
            VStack(alignment: .leading, spacing: TranscriptoTheme.Spacing.small) {
                Text("Método")
                    .font(TranscriptoTheme.Typography.body)
                    .foregroundColor(TranscriptoTheme.Colors.textPrimary)
                
                SegmentedControlView(
                    selection: $viewModel.selectedMethod,
                    options: CipherType.allCases,
                    displayNames: { $0.displayName },
                    onChange: viewModel.methodChanged
                )
            }
            
            // Campos dinámicos según el método
            methodSpecificFields
            
            // Configuración de salt
            SaltConfigurationView(
                useSalt: $viewModel.useSalt,
                saltAuto: $viewModel.saltAuto,
                saltManual: $viewModel.saltManual,
                onUseSaltChanged: viewModel.saltUsageChanged,
                onSaltModeChanged: viewModel.saltModeChanged,
                onGenerateNewSalt: viewModel.generateNewSalt
            )
        }
    }
    
    @ViewBuilder
    private var methodSpecificFields: some View {
        if viewModel.methodRequiresKey {
            InputFieldView(
                title: "Clave",
                text: $viewModel.key,
                placeholder: methodKeyPlaceholder,
                isRequired: true
            )
        }
        
        if viewModel.methodRequiresShift {
            NumericFieldView(
                title: "Desplazamiento",
                value: $viewModel.shift,
                placeholder: "3",
                range: -25...25,
                isRequired: true
            )
        }
    }
    
    private var methodKeyPlaceholder: String {
        switch viewModel.selectedMethod {
        case .vigenere:
            return "Solo letras (ej: CLAVE)"
        case .xor:
            return "Cualquier texto"
        default:
            return "Ingrese la clave"
        }
    }
    
    private var inputSection: some View {
        InputFieldView(
            title: viewModel.operationMode == .encrypt ? "Texto a Cifrar" : "Texto a Descifrar",
            text: $viewModel.inputText,
            placeholder: viewModel.operationMode == .encrypt 
                ? "Ingrese el texto que desea cifrar..."
                : "Ingrese el texto que desea descifrar...",
            isMultiline: true
        )
    }
    
    private var outputSection: some View {
        VStack(alignment: .leading, spacing: TranscriptoTheme.Spacing.small) {
            Text("Resultado")
                .font(TranscriptoTheme.Typography.body)
                .foregroundColor(TranscriptoTheme.Colors.textPrimary)
            
            ScrollView {
                Text(viewModel.outputText.isEmpty ? "El resultado aparecerá aquí..." : viewModel.outputText)
                    .font(TranscriptoTheme.Typography.body)
                    .foregroundColor(viewModel.outputText.isEmpty 
                        ? TranscriptoTheme.Colors.textSecondary 
                        : TranscriptoTheme.Colors.textPrimary)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .frame(minHeight: 100)
                    .padding(TranscriptoTheme.Spacing.medium)
                    .background(TranscriptoTheme.Colors.inputBackground)
                    .cornerRadius(TranscriptoTheme.Layout.cornerRadius)
                    .overlay(
                        RoundedRectangle(cornerRadius: TranscriptoTheme.Layout.cornerRadius)
                            .stroke(TranscriptoTheme.Colors.secondary.opacity(0.2), 
                                   lineWidth: TranscriptoTheme.Layout.borderWidth)
                    )
            }
            .frame(maxHeight: 150)
        }
    }
    
    private var actionSection: some View {
        VStack(spacing: TranscriptoTheme.Spacing.medium) {
            // Botón principal de procesar
            Button(action: viewModel.process) {
                HStack {
                    if viewModel.isProcessing {
                        ProgressView()
                            .progressViewStyle(CircularProgressViewStyle(tint: .white))
                            .scaleEffect(0.9)
                        Text("Procesando...")
                    } else {
                        Image(systemName: "lock.shield")
                        Text("Procesar")
                    }
                }
            }
            .primaryButton()
            .disabled(!viewModel.isProcessButtonEnabled)
            .opacity(viewModel.isProcessButtonEnabled ? 1.0 : 0.6)
            .animation(TranscriptoTheme.Animation.quick, value: viewModel.isProcessButtonEnabled)
            
            // Botones secundarios
            HStack(spacing: TranscriptoTheme.Spacing.medium) {
                // Copiar
                Button(action: viewModel.copyToClipboard) {
                    HStack {
                        Image(systemName: viewModel.showSuccessMessage ? "checkmark" : "doc.on.clipboard")
                            .foregroundColor(viewModel.showSuccessMessage ? TranscriptoTheme.Colors.success : TranscriptoTheme.Colors.textPrimary)
                        Text(viewModel.showSuccessMessage ? "¡Copiado!" : "Copiar")
                            .foregroundColor(viewModel.showSuccessMessage ? TranscriptoTheme.Colors.success : TranscriptoTheme.Colors.textPrimary)
                    }
                }
                .secondaryButton()
                .disabled(viewModel.outputText.isEmpty)
                .opacity(viewModel.outputText.isEmpty ? 0.6 : 1.0)
                .animation(TranscriptoTheme.Animation.quick, value: viewModel.showSuccessMessage)
                
                // Compartir
                ShareLinkView(
                    text: viewModel.outputText,
                    isEnabled: !viewModel.outputText.isEmpty
                )
                
                // Limpiar
                Button(action: viewModel.clear) {
                    HStack {
                        Image(systemName: "trash")
                        Text("Limpiar")
                    }
                }
                .secondaryButton()
            }
        }
    }
}

// MARK: - SaltConfigurationView
// Enum para opciones de salt
public enum SaltMode: String, CaseIterable {
    case automatic = "Automático"
    case manual = "Manual"
    
    public var isAutomatic: Bool {
        return self == .automatic
    }
}

/// Vista para configurar el salt
public struct SaltConfigurationView: View {
    @Binding var useSalt: Bool
    @Binding var saltAuto: Bool
    @Binding var saltManual: String
    let onUseSaltChanged: () -> Void
    let onSaltModeChanged: () -> Void
    let onGenerateNewSalt: () -> Void
    
    @State private var saltMode: SaltMode
    
    public init(
        useSalt: Binding<Bool>,
        saltAuto: Binding<Bool>,
        saltManual: Binding<String>,
        onUseSaltChanged: @escaping () -> Void,
        onSaltModeChanged: @escaping () -> Void,
        onGenerateNewSalt: @escaping () -> Void
    ) {
        self._useSalt = useSalt
        self._saltAuto = saltAuto
        self._saltManual = saltManual
        self.onUseSaltChanged = onUseSaltChanged
        self.onSaltModeChanged = onSaltModeChanged
        self.onGenerateNewSalt = onGenerateNewSalt
        self._saltMode = State(initialValue: saltAuto.wrappedValue ? .automatic : .manual)
    }
    
    public var body: some View {
        VStack(alignment: .leading, spacing: TranscriptoTheme.Spacing.medium) {
            Toggle("Usar Salt", isOn: $useSalt)
                .font(TranscriptoTheme.Typography.body)
                .onChange(of: useSalt) { _ in
                    onUseSaltChanged()
                }
            
            if useSalt {
                VStack(alignment: .leading, spacing: TranscriptoTheme.Spacing.medium) {
                    // Usar Picker en lugar de SegmentedControlView problemático
                    Picker("Modo de Salt", selection: $saltMode) {
                        ForEach(SaltMode.allCases, id: \.self) { mode in
                            Text(mode.rawValue).tag(mode)
                        }
                    }
                    .pickerStyle(SegmentedPickerStyle())
                    .onChange(of: saltMode) { newMode in
                        saltAuto = newMode.isAutomatic
                        onSaltModeChanged()
                    }
                    
                    if saltAuto {
                        HStack {
                            Text("Salt generado automáticamente")
                                .font(TranscriptoTheme.Typography.caption)
                                .foregroundColor(TranscriptoTheme.Colors.textSecondary)
                            
                            Spacer()
                            
                            Button("Regenerar") {
                                onGenerateNewSalt()
                            }
                            .font(TranscriptoTheme.Typography.caption)
                            .foregroundColor(TranscriptoTheme.Colors.accent)
                        }
                    } else {
                        VStack(alignment: .leading, spacing: TranscriptoTheme.Spacing.small) {
                            Text("Salt manual")
                                .font(TranscriptoTheme.Typography.caption)
                                .foregroundColor(TranscriptoTheme.Colors.textSecondary)
                            
                            TextField("Ingresa tu salt personalizado", text: $saltManual)
                                .textFieldStyle(RoundedBorderTextFieldStyle())
                                .font(TranscriptoTheme.Typography.body)
                        }
                    }
                }
                .padding(.leading, TranscriptoTheme.Spacing.medium)
            }
        }
    }
}
