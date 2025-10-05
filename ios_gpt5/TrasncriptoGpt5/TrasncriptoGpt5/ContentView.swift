//
//  ContentView.swift
//  TrasncriptoGpt5
//
//  Created by JOSE ZARABANDA on 8/21/25.
//

import SwiftUI
import UIKit
#if canImport(Domain)
import Domain
#endif
#if canImport(DataCrypto)
import DataCrypto
#endif

enum UIMethod: String, CaseIterable, Identifiable {
    case base64 = "Base64"
    case caesar = "César"
    case vigenere = "Vigenère"
    case xor = "XOR"
    var id: String { rawValue }
}

enum UIMode: String, CaseIterable, Identifiable { case encrypt = "Cifrar", decrypt = "Descifrar"; var id: String { rawValue } }

final class TranscriptoViewModel: ObservableObject {
    @Published var input: String = ""
    @Published var output: String = ""
    @Published var mode: UIMode = .encrypt
    @Published var method: UIMethod = .base64
    @Published var useSalt: Bool = false
    @Published var saltAuto: Bool = true
    @Published var saltManual: String = ""
    @Published var key: String = ""
    @Published var shift: Int = 3
    @Published var error: String?
    @Published var showCopyAlert: Bool = false
    @Published var showShareAlert: Bool = false

    func process() {
        guard !input.isEmpty else { error = "El texto de entrada está vacío."; output = ""; return }
#if canImport(Domain) && canImport(DataCrypto)
        do {
            // Map UI to Domain types
            let m: Domain.CipherMethodType = {
                switch method { case .base64: return .base64; case .caesar: return .caesar; case .vigenere: return .vigenere; case .xor: return .xor }
            }()

            var salt: String? = nil
            if useSalt {
                if mode == .encrypt && saltAuto {
                    let (_, _, gen) = DataCrypto.CryptoFactory.makeUseCases()
                    salt = try gen(length: 12)
                    saltManual = salt ?? ""
                } else if !saltManual.isEmpty {
                    salt = saltManual
                }
            }

            let params = Domain.CipherParams(method: m,
                                             key: (method == .vigenere || method == .xor) ? key : nil,
                                             shift: (method == .caesar ? shift : nil),
                                             salt: salt,
                                             useSalt: useSalt)
            let (enc, dec, _) = DataCrypto.CryptoFactory.makeUseCases()
            output = try (mode == .encrypt ? enc(input, params: params) : dec(input, params: params))
            error = nil
            UIImpactFeedbackGenerator(style: .light).impactOccurred()
        } catch {
            self.error = (error as NSError).localizedDescription
            self.output = ""
            UINotificationFeedbackGenerator().notificationOccurred(.error)
        }
#else
        // Placeholder when packages are not linked yet
        output = input
        error = "Falta vincular los paquetes locales (Domain/DataCrypto)."
#endif
    }

    func clear() { input = ""; output = ""; error = nil }
    
    func copyToClipboard() {
        UIPasteboard.general.string = output
        showCopyAlert = true
        UIImpactFeedbackGenerator(style: .light).impactOccurred()
        
        // Auto-hide alert after 2 seconds
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            self.showCopyAlert = false
        }
    }
    
    func shareCompleted() {
        showShareAlert = true
        UIImpactFeedbackGenerator(style: .light).impactOccurred()
        
        // Auto-hide alert after 2 seconds
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            self.showShareAlert = false
        }
    }
}

struct ContentView: View {
    @StateObject private var vm = TranscriptoViewModel()
    @AppStorage("ui.mode") private var modeStored: String = UIMode.encrypt.rawValue
    @AppStorage("ui.method") private var methodStored: String = UIMethod.base64.rawValue
    @AppStorage("ui.saltAuto") private var saltAutoStored: Bool = true
    @State private var showingShareSheet = false

    var body: some View {
        ScrollView {
            VStack(spacing: 16) {
                Picker("Modo", selection: $vm.mode) {
                    ForEach(UIMode.allCases) { Text($0.rawValue).tag($0) }
                }
                .pickerStyle(.segmented)
                .accessibilityLabel("Modo de operación")
                .onChange(of: vm.mode) { modeStored = vm.mode.rawValue }

                Picker("Método", selection: $vm.method) {
                    ForEach(UIMethod.allCases) { Text($0.rawValue).tag($0) }
                }
                .pickerStyle(.segmented)
                .accessibilityLabel("Método de cifrado")
                .onChange(of: vm.method) { methodStored = vm.method.rawValue }

                Group {
                    TextEditor(text: $vm.input)
                        .frame(minHeight: 120)
                        .padding(12)
                        .background(RoundedRectangle(cornerRadius: 16).fill(Color(.secondarySystemBackground)))
                        .accessibilityLabel("Texto de entrada")

                    if let error = vm.error { Text(error).foregroundStyle(.red).font(.footnote) }

                    Toggle("Usar salt", isOn: $vm.useSalt)
                    if vm.useSalt {
                        Toggle("Generar salt automáticamente", isOn: $vm.saltAuto)
                            .onChange(of: vm.saltAuto) { _, newValue in
                                saltAutoStored = newValue
                            }
                        if !vm.saltAuto {
                            TextField("Salt manual", text: $vm.saltManual)
                                .textInputAutocapitalization(.never)
                                .autocorrectionDisabled()
                                .padding(12)
                                .background(RoundedRectangle(cornerRadius: 12).fill(Color(.secondarySystemBackground)))
                        }
                    }

                    if vm.method == .caesar {
                        Stepper(value: $vm.shift, in: -26...26) { Text("Desplazamiento: \(vm.shift)") }
                    }
                    if vm.method == .vigenere || vm.method == .xor {
                        SecureField("Clave", text: $vm.key)
                            .textInputAutocapitalization(.never)
                            .autocorrectionDisabled()
                            .padding(12)
                            .background(RoundedRectangle(cornerRadius: 12).fill(Color(.secondarySystemBackground)))
                    }

                    HStack(spacing: 12) {
                        Button("Procesar") { vm.process() }
                            .buttonStyle(.borderedProminent)
                        Button("Limpiar") { vm.clear() }
                            .buttonStyle(.bordered)
                    }

                    TextEditor(text: $vm.output)
                        .frame(minHeight: 120)
                        .padding(12)
                        .background(RoundedRectangle(cornerRadius: 16).fill(Color(.secondarySystemBackground)))
                        .accessibilityLabel("Texto de salida")

                    HStack(spacing: 12) {
                        Button("Copiar") { vm.copyToClipboard() }
                            .buttonStyle(.bordered)
                            .disabled(vm.output.isEmpty)
                        Button("Compartir") { showingShareSheet = true }
                            .buttonStyle(.bordered)
                            .disabled(vm.output.isEmpty)
                    }
                }
            }
            .padding()
        }
        .navigationTitle("Transcripto")
        .sheet(isPresented: $showingShareSheet, onDismiss: { vm.shareCompleted() }) {
            ActivityViewController(activityItems: [vm.output])
        }
        .alert("¡Copiado!", isPresented: $vm.showCopyAlert) {
            Button("OK") { }
        } message: {
            Text("El texto se ha copiado al portapapeles")
        }
        .alert("¡Compartido!", isPresented: $vm.showShareAlert) {
            Button("OK") { }
        } message: {
            Text("Contenido compartido exitosamente")
        }
    }
}

// Wrapper para UIActivityViewController que funciona correctamente en iPad
struct ActivityViewController: UIViewControllerRepresentable {
    let activityItems: [Any]
    let applicationActivities: [UIActivity]? = nil
    
    func makeUIViewController(context: Context) -> UIActivityViewController {
        let controller = UIActivityViewController(
            activityItems: activityItems,
            applicationActivities: applicationActivities
        )
        
        // Configuración específica para iPad - esto previene el crash
        if UIDevice.current.userInterfaceIdiom == .pad {
            controller.popoverPresentationController?.sourceView = UIView()
            controller.popoverPresentationController?.sourceRect = CGRect(x: UIScreen.main.bounds.width / 2, y: UIScreen.main.bounds.height / 2, width: 0, height: 0)
            controller.popoverPresentationController?.permittedArrowDirections = []
        }
        
        return controller
    }
    
    func updateUIViewController(_ uiViewController: UIActivityViewController, context: Context) {
        // No updates needed
    }
}

#Preview {
    ContentView()
}
