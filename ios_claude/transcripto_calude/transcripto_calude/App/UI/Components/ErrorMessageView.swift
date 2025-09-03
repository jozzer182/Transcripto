//
//  ErrorMessageView.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import SwiftUI

/// Vista para mostrar mensajes de error
public struct ErrorMessageView: View {
    let message: String
    let onDismiss: () -> Void
    
    public init(message: String, onDismiss: @escaping () -> Void = {}) {
        self.message = message
        self.onDismiss = onDismiss
    }
    
    public var body: some View {
        HStack {
            Image(systemName: "exclamationmark.triangle.fill")
                .foregroundColor(TranscriptoTheme.Colors.error)
            
            VStack(alignment: .leading, spacing: TranscriptoTheme.Spacing.xs) {
                Text("Error")
                    .font(TranscriptoTheme.Typography.caption.weight(.semibold))
                    .foregroundColor(TranscriptoTheme.Colors.error)
                
                Text(message)
                    .font(TranscriptoTheme.Typography.caption)
                    .foregroundColor(TranscriptoTheme.Colors.textPrimary)
                    .multilineTextAlignment(.leading)
            }
            
            Spacer()
            
            Button(action: onDismiss) {
                Image(systemName: "xmark")
                    .font(.caption2)
                    .foregroundColor(TranscriptoTheme.Colors.textSecondary)
            }
        }
        .padding(TranscriptoTheme.Spacing.medium)
        .background(TranscriptoTheme.Colors.error.opacity(0.1))
        .cornerRadius(TranscriptoTheme.Layout.cornerRadius)
        .overlay(
            RoundedRectangle(cornerRadius: TranscriptoTheme.Layout.cornerRadius)
                .stroke(TranscriptoTheme.Colors.error.opacity(0.3), lineWidth: TranscriptoTheme.Layout.borderWidth)
        )
        .transition(.opacity.combined(with: .move(edge: .top)))
    }
}
