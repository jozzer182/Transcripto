import SwiftUI
import UIKit

public struct PrimaryButtonStyle: ButtonStyle {
    public init() {}
    public func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .font(.headline)
            .padding()
            .frame(maxWidth: .infinity)
            .foregroundStyle(.white)
            .background(RoundedRectangle(cornerRadius: 16, style: .continuous)
                .fill(Color.accentColor)
                .shadow(color: Color.black.opacity(configuration.isPressed ? 0.05 : 0.15), radius: 10, x: 0, y: 4))
            .opacity(configuration.isPressed ? 0.9 : 1)
            .scaleEffect(configuration.isPressed ? 0.98 : 1)
            .animation(.easeOut(duration: 0.15), value: configuration.isPressed)
    }
}

public struct Card<Content: View>: View {
    let content: Content
    public init(@ViewBuilder content: () -> Content) { self.content = content() }
    public var body: some View {
        content
            .padding(16)
            .background(
                RoundedRectangle(cornerRadius: 24, style: .continuous)
                    .fill(Color(.secondarySystemBackground))
                    .shadow(color: Color.black.opacity(0.08), radius: 12, x: 0, y: 6)
            )
    }
}
