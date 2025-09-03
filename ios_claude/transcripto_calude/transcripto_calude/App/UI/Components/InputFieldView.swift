//
//  InputFieldView.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import SwiftUI

/// Campo de entrada personalizado
public struct InputFieldView: View {
    let title: String
    @Binding var text: String
    let placeholder: String
    let isRequired: Bool
    let keyboardType: UIKeyboardType
    let isMultiline: Bool
    
    public init(
        title: String,
        text: Binding<String>,
        placeholder: String,
        isRequired: Bool = false,
        keyboardType: UIKeyboardType = .default,
        isMultiline: Bool = false
    ) {
        self.title = title
        self._text = text
        self.placeholder = placeholder
        self.isRequired = isRequired
        self.keyboardType = keyboardType
        self.isMultiline = isMultiline
    }
    
    public var body: some View {
        VStack(alignment: .leading, spacing: TranscriptoTheme.Spacing.small) {
            HStack {
                Text(title)
                    .font(TranscriptoTheme.Typography.body)
                    .foregroundColor(TranscriptoTheme.Colors.textPrimary)
                
                if isRequired {
                    Text("*")
                        .font(TranscriptoTheme.Typography.body)
                        .foregroundColor(TranscriptoTheme.Colors.error)
                }
                
                Spacer()
            }
            
            Group {
                if isMultiline {
                    TextEditor(text: $text)
                        .frame(minHeight: 100)
                        .scrollContentBackground(.hidden)
                } else {
                    TextField(placeholder, text: $text)
                        .keyboardType(keyboardType)
                        .frame(height: TranscriptoTheme.Layout.inputHeight)
                }
            }
            .inputField()
        }
    }
}

/// Campo numérico especializado
public struct NumericFieldView: View {
    let title: String
    @Binding var value: String
    let placeholder: String
    let range: ClosedRange<Int>?
    let isRequired: Bool
    
    public init(
        title: String,
        value: Binding<String>,
        placeholder: String,
        range: ClosedRange<Int>? = nil,
        isRequired: Bool = false
    ) {
        self.title = title
        self._value = value
        self.placeholder = placeholder
        self.range = range
        self.isRequired = isRequired
    }
    
    public var body: some View {
        VStack(alignment: .leading, spacing: TranscriptoTheme.Spacing.small) {
            HStack {
                Text(title)
                    .font(TranscriptoTheme.Typography.body)
                    .foregroundColor(TranscriptoTheme.Colors.textPrimary)
                
                if isRequired {
                    Text("*")
                        .font(TranscriptoTheme.Typography.body)
                        .foregroundColor(TranscriptoTheme.Colors.error)
                }
                
                if let range = range {
                    Text("(\(range.lowerBound) a \(range.upperBound))")
                        .font(TranscriptoTheme.Typography.caption)
                        .foregroundColor(TranscriptoTheme.Colors.textSecondary)
                }
                
                Spacer()
            }
            
            TextField(placeholder, text: $value)
                .keyboardType(.numberPad)
                .frame(height: TranscriptoTheme.Layout.inputHeight)
                .inputField()
                .onChange(of: value) { oldValue, newValue in
                    // Filtrar solo números y signo negativo
                    let filtered = newValue.filter { $0.isNumber || $0 == "-" }
                    if filtered != newValue {
                        value = filtered
                    }
                }
        }
    }
}
