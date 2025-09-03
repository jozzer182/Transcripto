//
//  SaltConfigurationView.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import SwiftUI

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
