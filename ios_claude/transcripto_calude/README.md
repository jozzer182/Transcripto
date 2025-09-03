# Transcripto

Una aplicación iOS moderna para cifrado y descifrado de texto, implementada en Swift + SwiftUI con arquitectura Clean + MVVM.

## 🔐 Características

- **Múltiples métodos de cifrado**: César, Base64, Vigenère y XOR
- **Salt opcional**: Automático o manual para mayor seguridad
- **Interfaz moderna**: SwiftUI con soporte para modo oscuro
- **Copiar y compartir**: Integración nativa con iOS
- **Persistencia**: Recordar preferencias del usuario
- **Accesibilidad**: Soporte completo para VoiceOver y Dynamic Type

## 🏗️ Arquitectura

El proyecto sigue Clean Architecture + MVVM organizado en carpetas:

```
transcripto_calude/
├── App/                    # Capa de presentación
│   ├── UI/
│   │   ├── Screens/       # Pantallas principales
│   │   └── Components/    # Componentes reutilizables
│   ├── ViewModel/         # ViewModels (MVVM)
│   └── Theme/             # Tema y estilos
├── CoreCommon/            # Utilidades compartidas
│   ├── Errors/           # Manejo de errores
│   ├── Validation/       # Validaciones
│   └── Utils/            # Utilidades generales
├── Domain/               # Lógica de negocio
│   ├── Contracts/        # Protocolos
│   ├── Models/          # Modelos de dominio
│   └── UseCases/        # Casos de uso
├── DataCrypto/          # Implementaciones de cifrado
│   ├── Caesar/         # Cifrado César
│   ├── Base64Codec/    # Codec Base64
│   ├── Vigenere/       # Cifrado Vigenère
│   ├── XOR/            # Cifrado XOR
│   └── Salt/           # Proveedor de salt
└── TestingSupport/     # Tests unitarios
```

## 🔧 Métodos de Cifrado

### César
- **Descripción**: Desplazamiento de letras en el alfabeto
- **Parámetros**: Desplazamiento (-25 a 25)
- **Con salt**: El salt modifica el desplazamiento usando hash

### Base64
- **Descripción**: Codificación estándar Base64
- **Parámetros**: Ninguno
- **Con salt**: Se añade como prefijo antes de codificar

### Vigenère
- **Descripción**: Cifrado polialfabético usando clave
- **Parámetros**: Clave (solo letras)
- **Con salt**: Se concatena con la clave

### XOR
- **Descripción**: Operación XOR byte a byte
- **Parámetros**: Clave (cualquier texto)
- **Salida**: Base64 del resultado XOR
- **Con salt**: Se concatena con la clave

## 🔑 Uso del Salt

El salt es opcional y puede ser:
- **Automático**: Generado con `SecRandomCopyBytes`
- **Manual**: Especificado por el usuario

### Comportamiento por método:
- **César**: Modifica el desplazamiento
- **Base64**: Se añade como prefijo `salt:texto`
- **Vigenère/XOR**: Se concatena con la clave

## 🚀 Compilación y Pruebas

### Requisitos
- Xcode 15.0+
- iOS 16.0+
- Swift 5.0+

### Script de Build
```bash
# Compilar y ejecutar tests
./scripts/build-and-test.sh

# Ver ayuda
./scripts/build-and-test.sh --help

# Cambiar configuración
SCHEME="transcripto_calude" CONFIG="Release" ./scripts/build-and-test.sh
```

### Comandos individuales
```bash
# Solo compilar
xcodebuild -scheme transcripto_calude build

# Solo tests
xcodebuild -scheme transcripto_calude test

# Ver esquemas disponibles
xcodebuild -list
```

## 🧪 Testing

El proyecto incluye tests exhaustivos:

- **Tests unitarios**: Para todos los métodos de cifrado
- **Tests de ida y vuelta**: Cifrar + descifrar = texto original
- **Tests de edge cases**: Entradas vacías, Unicode, casos límite
- **Golden tests**: Valores conocidos para validar implementación
- **Tests con salt**: Verificar comportamiento con y sin salt

### Ejecutar tests específicos
```bash
# Tests de cifrado César
xcodebuild test -only-testing:transcripto_caludeTests/CaesarCipherTests

# Todos los tests de cifrado
xcodebuild test -only-testing:transcripto_caludeTests
```

## 🎨 Calidad de Código

### SwiftLint
```bash
# Instalar
brew install swiftlint

# Ejecutar
swiftlint

# Solo warnings
swiftlint --strict
```

### SwiftFormat
```bash
# Instalar
brew install swiftformat

# Formatear código
swiftformat transcripto_calude

# Solo verificar
swiftformat --lint transcripto_calude
```

## 📱 Interfaz de Usuario

### Características de UX
- **Card central**: Organiza toda la funcionalidad
- **Controles segmentados**: Para modo y método
- **Campos dinámicos**: Se muestran según el método seleccionado
- **Feedback háptico**: En acciones importantes
- **Animaciones suaves**: Transiciones entre estados
- **Mensajes claros**: Errores y confirmaciones en español

### Accesibilidad
- **VoiceOver**: Labels descriptivos en todos los elementos
- **Dynamic Type**: Soporte para tamaños de fuente
- **Contraste**: Colores que cumplen guidelines de accesibilidad
- **Navegación**: Orden lógico de tabs

## 🔒 Seguridad

- **Sin dependencias externas**: Solo standard library + Security framework
- **Validaciones robustas**: En todas las entradas
- **Manejo seguro de errores**: Sin exposición de datos sensibles
- **Salt criptográficamente seguro**: Usando `SecRandomCopyBytes`

## 📝 Salida

La aplicación produce **únicamente el texto cifrado/descifrado**, sin metadatos adicionales como:
- ❌ `method=cesar;salt=abc;payload=xyz`
- ✅ `xyz` (solo el resultado)

## 🚧 Desarrollo

### Agregar nuevo método de cifrado
1. Crear carpeta en `DataCrypto/`
2. Implementar protocolo `CipherMethod`
3. Añadir a `CipherType` enum
4. Registrar en `TranscriptoViewModel`
5. Crear tests correspondientes

### Estructura de commits
- `feat:` Nueva funcionalidad
- `fix:` Corrección de bugs
- `test:` Añadir o modificar tests
- `refactor:` Refactorización de código
- `docs:` Documentación

## 📄 Licencia

MIT License - Ver archivo LICENSE para detalles.

## 🤝 Contribución

1. Fork el proyecto
2. Crear branch para feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -m 'feat: añadir nueva funcionalidad'`)
4. Push al branch (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

---

**Transcripto** - Cifrado seguro y fácil de usar en iOS 📱🔐
