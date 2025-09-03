//
//  ActionButtonsView.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import SwiftUI

/// Vista con los botones de acción principales
public struct ActionButtonsView: View {
    let isProcessing: Bool
    let isProcessButtonEnabled: Bool
    let hasOutput: Bool
    let showSuccessMessage: Bool
    let onProcess: () -> Void
    let onCopy: () -> Void
    let onShare: () -> Void
    let onClear: () -> Void
    
    public init(
        isProcessing: Bool,
        isProcessButtonEnabled: Bool,
        hasOutput: Bool,
        showSuccessMessage: Bool,
        onProcess: @escaping () -> Void,
        onCopy: @escaping () -> Void,
        onShare: @escaping () -> Void,
        onClear: @escaping () -> Void
    ) {
        self.isProcessing = isProcessing
        self.isProcessButtonEnabled = isProcessButtonEnabled
        self.hasOutput = hasOutput
        self.showSuccessMessage = showSuccessMessage
        self.onProcess = onProcess
        self.onCopy = onCopy
        self.onShare = onShare
        self.onClear = onClear
    }
    
    public var body: some View {
        VStack(spacing: TranscriptoTheme.Spacing.medium) {
            // Botón principal de procesar
            Button(action: onProcess) {
                HStack {
                    if isProcessing {
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
            .disabled(!isProcessButtonEnabled)
            .opacity(isProcessButtonEnabled ? 1.0 : 0.6)
            .animation(TranscriptoTheme.Animation.quick, value: isProcessButtonEnabled)
            
            // Botones secundarios
            HStack(spacing: TranscriptoTheme.Spacing.medium) {
                // Copiar
                Button(action: onCopy) {
                    HStack {
                        Image(systemName: showSuccessMessage ? "checkmark" : "doc.on.clipboard")
                            .foregroundColor(showSuccessMessage ? TranscriptoTheme.Colors.success : TranscriptoTheme.Colors.textPrimary)
                        Text(showSuccessMessage ? "¡Copiado!" : "Copiar")
                            .foregroundColor(showSuccessMessage ? TranscriptoTheme.Colors.success : TranscriptoTheme.Colors.textPrimary)
                    }
                }
                .secondaryButton()
                .disabled(!hasOutput)
                .opacity(hasOutput ? 1.0 : 0.6)
                .animation(TranscriptoTheme.Animation.quick, value: showSuccessMessage)
                
                // Compartir
                ShareLink(
                    item: "", // Se configurará dinámicamente
                    subject: Text("Texto procesado - Transcripto"),
                    message: Text("Texto procesado con Transcripto")
                ) {
                    HStack {
                        Image(systemName: "square.and.arrow.up")
                        Text("Compartir")
                    }
                }
                .secondaryButton()
                .disabled(!hasOutput)
                .opacity(hasOutput ? 1.0 : 0.6)
                
                // Limpiar
                Button(action: onClear) {
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

/// Vista de ShareLink personalizada
public struct ShareLinkView: View {
    let text: String
    let isEnabled: Bool
    
    public init(text: String, isEnabled: Bool) {
        self.text = text
        self.isEnabled = isEnabled
    }
    
    public var body: some View {
        if isEnabled && !text.isEmpty {
            ShareLink(
                item: text,
                subject: Text("Texto procesado - Transcripto"),
                message: Text("Texto procesado con Transcripto")
            ) {
                HStack {
                    Image(systemName: "square.and.arrow.up")
                    Text("Compartir")
                }
            }
            .secondaryButton()
        } else {
            Button(action: {}) {
                HStack {
                    Image(systemName: "square.and.arrow.up")
                    Text("Compartir")
                }
            }
            .secondaryButton()
            .disabled(true)
            .opacity(0.6)
        }
    }
}
