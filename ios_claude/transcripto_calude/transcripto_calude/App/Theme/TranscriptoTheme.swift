//
//  TranscriptoTheme.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import SwiftUI

/// Tema visual de la aplicación Transcripto
public struct TranscriptoTheme {
    
    // MARK: - Colors
    public struct Colors {
        public static let primary = Color.blue
        public static let secondary = Color.gray
        public static let accent = Color.orange
        public static let success = Color.green
        public static let error = Color.red
        public static let warning = Color.yellow
        
        public static let cardBackground = Color(.systemBackground)
        public static let cardShadow = Color.black.opacity(0.1)
        public static let inputBackground = Color(.secondarySystemBackground)
        public static let textPrimary = Color(.label)
        public static let textSecondary = Color(.secondaryLabel)
    }
    
    // MARK: - Typography
    public struct Typography {
        public static let title = Font.title2.weight(.bold)
        public static let headline = Font.headline.weight(.semibold)
        public static let body = Font.body
        public static let caption = Font.caption
        public static let button = Font.body.weight(.medium)
    }
    
    // MARK: - Spacing
    public struct Spacing {
        public static let xs: CGFloat = 4
        public static let small: CGFloat = 8
        public static let medium: CGFloat = 16
        public static let large: CGFloat = 24
        public static let xl: CGFloat = 32
    }
    
    // MARK: - Layout
    public struct Layout {
        public static let cornerRadius: CGFloat = 12
        public static let cardCornerRadius: CGFloat = 16
        public static let borderWidth: CGFloat = 1
        public static let shadowRadius: CGFloat = 8
        public static let shadowOffset = CGSize(width: 0, height: 2)
        
        public static let buttonHeight: CGFloat = 48
        public static let inputHeight: CGFloat = 44
        public static let cardPadding: CGFloat = 20
    }
    
    // MARK: - Animation
    public struct Animation {
        public static let standard = SwiftUI.Animation.easeInOut(duration: 0.3)
        public static let quick = SwiftUI.Animation.easeInOut(duration: 0.2)
        public static let bouncy = SwiftUI.Animation.spring(response: 0.6, dampingFraction: 0.8)
    }
}

// MARK: - View Extensions
extension View {
    /// Aplica el estilo de card del tema
    public func transcriptoCard() -> some View {
        self
            .background(TranscriptoTheme.Colors.cardBackground)
            .cornerRadius(TranscriptoTheme.Layout.cardCornerRadius)
            .shadow(
                color: TranscriptoTheme.Colors.cardShadow,
                radius: TranscriptoTheme.Layout.shadowRadius,
                x: TranscriptoTheme.Layout.shadowOffset.width,
                y: TranscriptoTheme.Layout.shadowOffset.height
            )
    }
    
    /// Aplica el estilo de botón primario
    public func primaryButton() -> some View {
        self
            .frame(height: TranscriptoTheme.Layout.buttonHeight)
            .frame(maxWidth: .infinity)
            .background(TranscriptoTheme.Colors.primary)
            .foregroundColor(.white)
            .font(TranscriptoTheme.Typography.button)
            .cornerRadius(TranscriptoTheme.Layout.cornerRadius)
    }
    
    /// Aplica el estilo de botón secundario
    public func secondaryButton() -> some View {
        self
            .frame(height: TranscriptoTheme.Layout.buttonHeight)
            .frame(maxWidth: .infinity)
            .background(TranscriptoTheme.Colors.inputBackground)
            .foregroundColor(TranscriptoTheme.Colors.textPrimary)
            .font(TranscriptoTheme.Typography.button)
            .cornerRadius(TranscriptoTheme.Layout.cornerRadius)
            .overlay(
                RoundedRectangle(cornerRadius: TranscriptoTheme.Layout.cornerRadius)
                    .stroke(TranscriptoTheme.Colors.secondary.opacity(0.3), lineWidth: TranscriptoTheme.Layout.borderWidth)
            )
    }
    
    /// Aplica el estilo de campo de entrada
    public func inputField() -> some View {
        self
            .padding(TranscriptoTheme.Spacing.medium)
            .background(TranscriptoTheme.Colors.inputBackground)
            .cornerRadius(TranscriptoTheme.Layout.cornerRadius)
            .overlay(
                RoundedRectangle(cornerRadius: TranscriptoTheme.Layout.cornerRadius)
                    .stroke(TranscriptoTheme.Colors.secondary.opacity(0.2), lineWidth: TranscriptoTheme.Layout.borderWidth)
            )
    }
}
