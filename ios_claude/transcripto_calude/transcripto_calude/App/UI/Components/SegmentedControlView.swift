//
//  SegmentedControlView.swift
//  transcripto_calude
//
//  Created by JOSE ZARABANDA on 9/3/25.
//

import SwiftUI

/// Control segmentado personalizado para la aplicación
public struct SegmentedControlView<T: CaseIterable & Hashable>: View where T.AllCases.Element == T {
    @Binding var selection: T
    let options: [T]
    let displayNames: (T) -> String
    let onChange: () -> Void
    
    public init(
        selection: Binding<T>,
        options: [T],
        displayNames: @escaping (T) -> String,
        onChange: @escaping () -> Void = {}
    ) {
        self._selection = selection
        self.options = options
        self.displayNames = displayNames
        self.onChange = onChange
    }
    
    public var body: some View {
        HStack(spacing: 0) {
            ForEach(Array(options.enumerated()), id: \.element) { index, option in
                Button(action: {
                    withAnimation(TranscriptoTheme.Animation.quick) {
                        selection = option
                        onChange()
                    }
                }) {
                    Text(displayNames(option))
                        .font(TranscriptoTheme.Typography.body)
                        .foregroundColor(selection == option ? .white : TranscriptoTheme.Colors.textPrimary)
                        .frame(maxWidth: .infinity)
                        .frame(height: TranscriptoTheme.Layout.inputHeight)
                        .background(
                            Group {
                                if selection == option {
                                    TranscriptoTheme.Colors.primary
                                } else {
                                    Color.clear
                                }
                            }
                        )
                        .cornerRadius(
                            TranscriptoTheme.Layout.cornerRadius,
                            corners: cornerRadiusForIndex(index)
                        )
                }
                .buttonStyle(PlainButtonStyle())
            }
        }
        .background(TranscriptoTheme.Colors.inputBackground)
        .cornerRadius(TranscriptoTheme.Layout.cornerRadius)
        .overlay(
            RoundedRectangle(cornerRadius: TranscriptoTheme.Layout.cornerRadius)
                .stroke(TranscriptoTheme.Colors.secondary.opacity(0.2), lineWidth: TranscriptoTheme.Layout.borderWidth)
        )
    }
    
    private func cornerRadiusForIndex(_ index: Int) -> UIRectCorner {
        if options.count == 1 {
            return .allCorners
        } else if index == 0 {
            return [.topLeft, .bottomLeft]
        } else if index == options.count - 1 {
            return [.topRight, .bottomRight]
        } else {
            return []
        }
    }
}

// MARK: - Supporting Extensions
extension View {
    func cornerRadius(_ radius: CGFloat, corners: UIRectCorner) -> some View {
        clipShape(RoundedCorner(radius: radius, corners: corners))
    }
}

struct RoundedCorner: Shape {
    var radius: CGFloat = .infinity
    var corners: UIRectCorner = .allCorners

    func path(in rect: CGRect) -> Path {
        let path = UIBezierPath(
            roundedRect: rect,
            byRoundingCorners: corners,
            cornerRadii: CGSize(width: radius, height: radius)
        )
        return Path(path.cgPath)
    }
}
