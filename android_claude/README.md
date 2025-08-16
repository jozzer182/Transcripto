# Transcripto

Una aplicación Android moderna y elegante para cifrado y descifrado de texto, desarrollada con Jetpack Compose, Material 3 y arquitectura Clean Architecture + MVVM.

## 🚀 Características

- **Métodos de cifrado soportados**: César, Base64, Vigenère y XOR
- **Interfaz moderna**: Material 3 con soporte para Dynamic Color y modo oscuro
- **Funcionalidad de salt**: Opcional, con generación automática o manual
- **Formato de envelope**: Portabilidad de datos cifrados con metadatos
- **Copiar y compartir**: Integración nativa con el portapapeles y Android Sharesheet
- **Accesibilidad**: Soporte completo para TalkBack y navegación por teclado
- **Validación en tiempo real**: Feedback inmediato sobre la configuración

## 📱 Captura de pantalla

La aplicación presenta una interfaz limpia y moderna con:
- Selector de modo (Cifrar/Descifrar) con botones segmentados
- Selección de método de cifrado con radio buttons
- Configuración de parámetros específicos para cada método
- Sección de configuración de salt colapsible
- Resultado con opciones de copiar y compartir

## 🔐 Métodos de cifrado

### César
- **Descripción**: Cifrado por desplazamiento sobre letras latinas (A-Z, a-z)
- **Parámetros**: Desplazamiento entero (-25 a 25)
- **Comportamiento**: Preserva caracteres no alfabéticos, wrap-around automático
- **Con salt**: Se suma `hash(salt) % 26` al desplazamiento

### Base64
- **Descripción**: Codificación Base64 estándar (¡no es cifrado real!)
- **Parámetros**: Ninguno adicional
- **Comportamiento**: Soporte UTF-8 completo
- **Con salt**: Se preprende `salt:` al texto antes de codificar

### Vigenère
- **Descripción**: Cifrado polialfabético clásico
- **Parámetros**: Clave alfabética (se normaliza automáticamente)
- **Comportamiento**: Opera sobre A-Z y a-z, preserva caso y caracteres especiales
- **Con salt**: Se concatena salt a la clave (`clave + salt`)

### XOR
- **Descripción**: Cifrado XOR byte a byte
- **Parámetros**: Clave de cualquier longitud
- **Comportamiento**: Opera sobre bytes UTF-8, resultado en Base64
- **Con salt**: Se concatena salt a la clave (`clave + salt`)

## 📝 Formato de Envelope

Los textos cifrados se envuelven en un formato estandardizado para portabilidad:

```
method=<METHOD>;salt=<BASE64_SALT>;payload=<ENCRYPTED_TEXT>
```

### Ejemplos:

```
method=CAESAR;salt=dGVzdA==;payload=Khoor Zruog

method=XOR;salt=;payload=SGVsbG8gV29ybGQ=

method=VIGENERE;salt=c2FsdDEy;payload=Dloeq Yspnh
```

Al descifrar, la aplicación detecta automáticamente este formato y extrae los parámetros necesarios.

## 🏗️ Arquitectura

El proyecto utiliza **Clean Architecture** con **MVVM** y está modularizado de la siguiente manera:

```
📦 Transcripto
├── 📂 app/ (Android Application)
│   ├── UI (Compose)
│   ├── ViewModels
│   ├── DI wiring
│   └── Navigation
├── 📂 core/
│   ├── 📂 ui/ (Compose components, themes)
│   └── 📂 common/ (Result, utils, validations)
├── 📂 domain/ (Business logic)
│   ├── Models
│   ├── Repository interfaces
│   └── Use Cases
├── 📂 data/
│   └── 📂 crypto/ (Cipher implementations)
└── 📂 testing/ (Test utilities)
```

### Capas principales:

- **Presentation**: ViewModels con StateFlow + Compose UI
- **Domain**: Use Cases y modelos de negocio puros
- **Data**: Implementaciones de cifrado y repositorios
- **Core**: Utilidades compartidas y componentes UI

### Tecnologías:

- **UI**: Jetpack Compose + Material 3
- **DI**: Hilt
- **Async**: Coroutines + StateFlow
- **Testing**: JUnit 5 + Truth + Compose Testing
- **Build**: Gradle Kotlin DSL

## 🧪 Testing

### Pruebas unitarias

El proyecto incluye tests exhaustivos para:

- **Cifrados**: Tests de ida y vuelta para todos los métodos
- **Edge cases**: Strings vacíos, Unicode, casos límite
- **Golden tests**: Verificación de resultados específicos de César
- **Envelope parsing**: Validación del formato de metadatos

Ejecutar pruebas:
```bash
./gradlew test
```

### Pruebas instrumentadas

- Tests de UI con Compose
- Validación de accesibilidad
- Tests de integración

Ejecutar pruebas instrumentadas:
```bash
./gradlew connectedAndroidTest
```

## 🔧 Compilación

### Requisitos

- Android Studio Hedgehog | 2023.1.1 o superior
- JDK 11 o superior
- Android SDK API 34+
- Gradle 8.0+

### Compilar

```bash
# Compilar debug
./gradlew assembleDebug

# Compilar release
./gradlew assembleRelease

# Ejecutar en dispositivo/emulador
./gradlew installDebug
```

### Configuración de lint

El proyecto usa configuración estricta de lint para mantener calidad de código:

```bash
./gradlew lint
./gradlew ktlintCheck
```

## 🎯 Ejemplos de uso

### Cifrado básico

1. Ingrese texto: `"Hola Mundo"`
2. Seleccione método: `César`
3. Configure desplazamiento: `3`
4. Presione **Cifrar**
5. Resultado: `method=CAESAR;salt=;payload=Krod Pxqgr`

### Con salt automático

1. Active "Usar salt" → "Generar automáticamente"
2. El salt se genera y aplica automáticamente
3. El resultado incluye el salt en el envelope

### Descifrado automático

1. Pegue un texto con formato envelope
2. La app detecta automáticamente método y salt
3. Solo necesita ingresar la clave si es requerida
4. Presione **Descifrar**

## 🚀 Extensiones

### Agregar un nuevo método de cifrado

1. **Crear el cifrador** en `data/crypto/cipher/`:
```kotlin
class MiNuevoCipher {
    fun encrypt(input: String, params: CipherParams): Result<String>
    fun decrypt(input: String, params: CipherParams): Result<String>
}
```

2. **Agregar al enum** en `domain/model/CipherMethod.kt`:
```kotlin
enum class CipherMethod {
    // ... existentes
    MI_NUEVO_METODO
}
```

3. **Registrar en DI** en `data/crypto/di/CryptoModule.kt`

4. **Actualizar repositorio** en `CipherRepositoryImpl.kt`:
```kotlin
override suspend fun encrypt(input: String, params: CipherParams): Result<String> {
    return when (params.method) {
        // ... casos existentes
        CipherMethod.MI_NUEVO_METODO -> miNuevoCipher.encrypt(input, params)
    }
}
```

5. **Agregar a la UI** en `HomeScreen.kt` (selector de método)

6. **Escribir tests** siguiendo los patrones existentes

## 🐛 Limitaciones conocidas

- **Base64**: Es encoding, no cifrado real (se documenta claramente en UI)
- **Vigenère**: Solo opera sobre letras latinas básicas
- **Salt en César**: Usa hash simple, no criptográficamente seguro
- **Tamaño**: Sin límite de texto (puede afectar performance con textos muy grandes)

## 📋 Roadmap

- [ ] Soporte para archivos
- [ ] Métodos adicionales (AES, RSA)
- [ ] Importar/exportar configuraciones
- [ ] Historial de operaciones
- [ ] Widget de acceso rápido
- [ ] Modo por lotes
- [ ] Integración con gestores de contraseñas

## 📄 Licencia

Este proyecto es de código abierto bajo la licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:

1. Fork el proyecto
2. Crea una rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

### Estándares de código

- Sigue las convenciones de Kotlin
- Ejecuta `./gradlew ktlintFormat` antes de commitear
- Incluye tests para nueva funcionalidad
- Documenta APIs públicas con KDoc

## 👥 Autores

- **Desarrollador Principal** - Implementación inicial y arquitectura

## 🙏 Reconocimientos

- Material Design 3 por las guías de diseño
- Jetpack Compose por la moderna toolkit de UI
- Android Architecture Components por los patrones recomendados
