# Transcripto

Transcripto es una aplicación multiplataforma que permite **cifrar y descifrar texto** de manera sencilla, con un diseño moderno y un enfoque en modularidad.  
El proyecto tiene implementaciones tanto en **Android (Kotlin/Jetpack Compose)**, **iOS (Swift/SwiftUI)**, como en **Flutter** para una solución multiplataforma unificada.  
Además, el desarrollo fue asistido con **inteligencias artificiales de última generación** para acelerar el diseño, la arquitectura y la calidad del código.

## 📲 Descargar

La aplicación está disponible para iOS en la App Store:

[![Download on the App Store](https://developer.apple.com/assets/elements/badges/download-on-the-app-store.svg)](https://apps.apple.com/us/app/transcripto/id6752245146)

---

## 🚀 Características

- Cifrado y descifrado con diferentes algoritmos:
  - **César**
  - **Base64**
  - **Vigenère**
  - **XOR**
- Opción de usar **salt** (manual o automático).
- Interfaz moderna y accesible:
  - **Android**: Jetpack Compose + Material 3.
  - **iOS**: SwiftUI + MVVM.
  - **Flutter**: multiplataforma (Android, iOS, Web, Desktop).
- Acciones rápidas:
  - **Copiar al portapapeles**.
  - **Compartir** el resultado por mensajería.
  - **Limpiar** los campos.
- **Dark/Light Mode**, animaciones y accesibilidad.

---

## 🧩 Arquitectura

El proyecto se diseñó siguiendo principios de **Clean Architecture** y **MVVM**, con separación clara de responsabilidades:

- **Domain**: contratos, modelos y casos de uso.
- **DataCrypto**: implementaciones de los métodos de cifrado/descifrado.
- **CoreCommon**: validaciones, errores y utilidades.
- **UI/ViewModel**: lógica de presentación y pantallas.

Cada plataforma mantiene esta organización de acuerdo con sus prácticas nativas.

---

## 🛠️ Tecnologías utilizadas

### Android
- **Kotlin**  
- **Jetpack Compose + Material 3**  
- **Hilt** para DI  
- **JUnit5** + **Compose Testing**  

### iOS
- **Swift**  
- **SwiftUI + MVVM**  
- **XCTest**  
- **SwiftLint/SwiftFormat**  

### Flutter
- **Flutter 3.x**  
- **Dart**  
- **Material Design**  
- Multiplataforma (Android, iOS, Web, Desktop)  

### Inteligencias Artificiales usadas en el desarrollo
El diseño, arquitectura y documentación se apoyó con múltiples modelos de IA de última generación:
- **GPT‑5**  
- **Claude Sonnet 4**  
- **Gemini 2.5 Pro**  
- **Grok 3**  
- **Kimi**  
- **DeepSeek 3**  

---

## 📦 Instalación y ejecución

### Android
```bash
# Abrir en Android Studio o compilar desde terminal
./gradlew assembleDebug
```

### iOS
```bash
# Compilar y probar con Xcode desde terminal
./scripts/build-and-test.sh
```

### Flutter
```bash
# Ejecutar en Flutter
flutter pub get
flutter run
```

---

## 🧪 Pruebas

Cada implementación incluye tests de:
- **Ida y vuelta** (cifrar → descifrar).
- **Casos borde** (texto vacío, Unicode, claves inválidas).  
- **Golden tests** para verificar César con distintos desplazamientos.

```bash
# Android
./gradlew test

# iOS
./scripts/build-and-test.sh

# Flutter
flutter test
```

---

## 📱 Diseño de interfaz

- Interfaz **minimalista, moderna y accesible**.  
- Basada en **Material Design 3** (Android/Flutter) y **Human Interface Guidelines** (iOS).  
- Compatible con **Dark/Light mode**.  
- Animaciones sutiles y soporte **VoiceOver/TalkBack**.

---

## 📜 Licencia

Este proyecto es de uso libre para fines educativos y experimentales.  
El uso en producción requiere revisión y adecuación de los métodos de cifrado, ya que **no se recomienda emplearlos para seguridad real**.
