import SwiftUI
import CoreUI
import Domain

struct TranscriptoView: View {
    @StateObject private var viewModel = TranscriptoViewModel()

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(spacing: 20) {
                    mainCard
                    resultCard
                }
                .padding()
            }
            .background(Color.background.edgesIgnoringSafeArea(.all))
            .navigationTitle("Transcripto")
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(action: viewModel.clear) {
                        Label("Limpiar", systemImage: "trash")
                    }
                }
            }
            .sheet(isPresented: $viewModel.showShareSheet) {
                ShareSheet(activityItems: [viewModel.outputText])
            }
            .onAppear(perform: viewModel.generateSaltIfNeeded)
        }
    }

    private var mainCard: some View {
        VStack(alignment: .leading, spacing: 15) {
            Text("Texto de Entrada")
                .font(.headline)
                .foregroundColor(.primaryText)
            
            TextEditor(text: $viewModel.inputText)
                .frame(height: 150)
                .padding(4)
                .background(Color.background)
                .cornerRadius(8)
                .overlay(
                    RoundedRectangle(cornerRadius: 8)
                        .stroke(Color.secondaryText.opacity(0.5), lineWidth: 1)
                )
                .accessibilityLabel("Campo de texto de entrada")

            modeSelector
            methodSelector
            
            Divider()
            
            options
            
            Button(action: viewModel.process) {
                Text(viewModel.mode.rawValue)
            }
            .buttonStyle(ProminentButtonStyle())
            .accessibilityLabel("Procesar texto")

        }
        .card()
    }

    private var modeSelector: some View {
        Picker("Modo", selection: $viewModel.mode) {
            ForEach(TranscriptoViewModel.OperationMode.allCases) { mode in
                Text(mode.rawValue).tag(mode)
            }
        }
        .pickerStyle(.segmented)
        .accessibilityLabel("Selector de modo: Cifrar o Descifrar")
    }

    private var methodSelector: some View {
        Picker("Método", selection: $viewModel.method) {
            ForEach(CipherMethodType.allCases) { method in
                Text(method.rawValue).tag(method)
            }
        }
        .pickerStyle(.segmented)
        .accessibilityLabel("Selector de método de cifrado")
    }

    @ViewBuilder
    private var options: some View {
        VStack(alignment: .leading, spacing: 15) {
            dynamicMethodParameters
            saltSection
        }
        .animation(.easeInOut, value: viewModel.method)
        .animation(.easeInOut, value: viewModel.useSalt)
    }

    @ViewBuilder
    private var dynamicMethodParameters: some View {
        switch viewModel.method {
        case .caesar:
            HStack {
                Text("Desplazamiento:")
                TextField("Ej: 3", text: $viewModel.caesarShift)
                    .keyboardType(.numberPad)
                    .textFieldStyle(.roundedBorder)
                    .accessibilityLabel("Campo de desplazamiento para cifrado César")
            }
        case .vigenere:
            HStack {
                Text("Clave:")
                TextField("Clave no vacía", text: $viewModel.vigenereKey)
                    .textFieldStyle(.roundedBorder)
                    .accessibilityLabel("Campo de clave para cifrado Vigenère")
            }
        case .xor:
            HStack {
                Text("Clave:")
                TextField("Clave no vacía", text: $viewModel.xorKey)
                    .textFieldStyle(.roundedBorder)
                    .accessibilityLabel("Campo de clave para cifrado XOR")
            }
        case .base64:
            EmptyView()
        }
    }

    private var saltSection: some View {
        VStack(alignment: .leading) {
            Toggle("Usar salt", isOn: $viewModel.useSalt.animation())
                .onChange(of: viewModel.useSalt) { _, use in
                    if use {
                        viewModel.generateSaltIfNeeded()
                    }
                }

            if viewModel.useSalt {
                Picker("Opción de Salt", selection: $viewModel.saltOption) {
                    ForEach(TranscriptoViewModel.SaltOption.allCases) { option in
                        Text(option.rawValue).tag(option)
                    }
                }
                .pickerStyle(.segmented)
                .padding(.bottom, 5)
                .onChange(of: viewModel.saltOption) { _,_ in
                    viewModel.generateSaltIfNeeded()
                }

                if viewModel.saltOption == .manual {
                    TextField("Ingresar salt (Base64)", text: $viewModel.manualSalt)
                        .textFieldStyle(.roundedBorder)
                        .accessibilityLabel("Campo para ingresar salt manualmente")
                } else {
                    if !viewModel.generatedSalt.isEmpty {
                        HStack {
                            Text("Generado:")
                                .font(.caption)
                                .foregroundColor(.secondaryText)
                            Text(viewModel.generatedSalt)
                                .font(.caption.monospaced())
                                .foregroundColor(.secondaryText)
                                .lineLimit(1)
                                .truncationMode(.middle)
                            Spacer()
                            Button(action: viewModel.generateSaltIfNeeded) {
                                Image(systemName: "arrow.clockwise")
                            }
                        }
                    }
                }
            }
        }
    }

    @ViewBuilder
    private var resultCard: some View {
        if let error = viewModel.error {
            HStack {
                Image(systemName: "xmark.octagon.fill")
                    .foregroundColor(.error)
                Text(error)
                    .foregroundColor(.error)
                Spacer()
            }
            .padding()
            .background(Color.error.opacity(0.2))
            .cornerRadius(10)
        } else if !viewModel.outputText.isEmpty {
            VStack(alignment: .leading, spacing: 15) {
                Text("Resultado")
                    .font(.headline)
                    .foregroundColor(.primaryText)
                
                Text(viewModel.outputText)
                    .font(.body.monospaced())
                    .foregroundColor(.primaryText)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding()
                    .background(Color.background)
                    .cornerRadius(8)
                    .accessibilityLabel("Resultado de la operación")

                HStack {
                    Button(action: viewModel.copyToClipboard) {
                        Label("Copiar", systemImage: "doc.on.doc")
                    }
                    .buttonStyle(.bordered)
                    
                    Button(action: viewModel.share) {
                        Label("Compartir", systemImage: "square.and.arrow.up")
                    }
                    .buttonStyle(.bordered)
                }
            }
            .card()
            .transition(.opacity.combined(with: .scale))
        }
    }
}

struct TranscriptoView_Previews: PreviewProvider {
    static var previews: some View {
        TranscriptoView()
    }
}
