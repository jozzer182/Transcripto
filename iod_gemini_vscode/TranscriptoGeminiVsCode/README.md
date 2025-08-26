# Transcripto

Una sencilla aplicación para iOS que permite cifrar y descifrar texto utilizando varios métodos clásicos. Diseñada con una arquitectura limpia, modular y moderna en SwiftUI.

## Características

- **Múltiples Métodos**: Soporta cifrados César, Vigenère, XOR y codificación Base64.
- **Interfaz Moderna**: UI limpia y fácil de usar construida con SwiftUI, compatible con Dark Mode.
- **Arquitectura Limpia + MVVM**: Separación clara de responsabilidades entre la presentación, la lógica de negocio y los datos.
- **Modularización**: El código está organizado en paquetes Swift (Swift Packages) para una mejor reutilización y mantenimiento.
- **Uso de Salt**: Opción para añadir un "salt" a las claves para aumentar la seguridad (el usuario debe recordar el salt para descifrar).
- **Funcionalidades Nativas**: Integración con el portapapeles y la hoja para compartir de iOS.
- **Sin Dependencias Externas**: Todos los algoritmos de cifrado están implementados en Swift puro.

## Arquitectura

El proyecto sigue los principios de Clean Architecture, con una clara separación de capas, combinado con el patrón MVVM para la capa de presentación.

- `TranscriptoGeminiVsCode` (App): El target principal de la aplicación iOS. Contiene la configuración de la app, la inyección de dependencias y la vista principal.
- `Domain`: Define los modelos de negocio puros (ej: `CipherMethod`, `CipherParams`), los contratos (protocolos) y los casos de uso (ej: `EncryptTextUseCase`). No tiene dependencias de otras capas.
- `DataCrypto`: Implementaciones concretas de los protocolos definidos en `Domain`. Aquí residen los algoritmos de cifrado (`CaesarCipher`, `XorCipher`, etc.) y proveedores (`SecureSaltProvider`).
- `CoreUI`: Componentes de SwiftUI reutilizables, estilos, colores y helpers de UI.
- `CoreCommon`: Utilidades y extensiones compartidas por todos los módulos.
- `TestingSupport`: Utilidades para facilitar la escritura de tests.

## Cómo usar la App

1.  **Introduce el texto**: Escribe o pega el texto que quieres procesar en el campo de entrada.
2.  **Elige el modo**: Selecciona "Cifrar" o "Descifrar".
3.  **Elige el método**: Selecciona uno de los métodos disponibles (César, Vigenère, XOR, Base64).
4.  **Configura los parámetros**:
    - **César**: Introduce un número de desplazamiento.
    - **Vigenère/XOR**: Introduce una clave de texto.
5.  **(Opcional) Usa un Salt**:
    - Activa "Usar salt".
    - Puedes dejar que se genere uno automáticamente o introducir uno manualmente.
    - **Importante**: Si usas un salt para cifrar, necesitarás proporcionar **exactamente el mismo salt** para descifrar.
6.  **Procesa**: Pulsa el botón principal para ver el resultado.
7.  **Copia o Comparte**: Usa los botones en la tarjeta de resultado para copiar al portapapeles o compartir.

### Ejemplo: Cifrado XOR

El cifrado XOR opera a nivel de bytes. Para que el resultado sea visible y manejable como texto, se codifica en Base64.

1.  **Modo**: Cifrar
2.  **Método**: XOR
3.  **Texto**: `Hello`
4.  **Clave**: `key`
5.  **Resultado (Base64)**: `Gg0LDx4=`

Para descifrar:

1.  **Modo**: Descifrar
2.  **Método**: XOR
3.  **Texto**: `Gg0LDx4=` (el Base64 de antes)
4.  **Clave**: `key`
5.  **Resultado**: `Hello`

## Cómo añadir un nuevo método de cifrado

Gracias a la arquitectura modular, añadir un nuevo método es sencillo:

1.  **Añade el tipo a `CipherMethodType`**: Ve al archivo `Domain/Sources/Domain/Models/CipherParams.swift` y añade un nuevo `case` al enum.
2.  **Crea la implementación**: En el paquete `DataCrypto`, crea un nuevo archivo para tu cifrado (ej: `MyNewCipher.swift`).
3.  **Implementa `CipherMethod`**:
    ```swift
    import Domain

    public final class MyNewCipher: CipherMethod {
        public func encrypt(_ input: String, params: CipherParams) throws -> String {
            // Tu lógica de cifrado aquí
        }

        public func decrypt(_ input: String, params: CipherParams) throws -> String {
            // Tu lógica de descifrado aquí
        }
    }
    ```
4.  **Regístralo en la `CipherFactory`**: En `DataCrypto/Sources/DataCrypto/CipherFactory.swift`, añade tu nueva clase al `switch`.
5.  **Actualiza la UI (si es necesario)**: Si tu método necesita parámetros especiales, actualiza la vista `TranscriptoView.swift` para que muestre los campos necesarios.

¡Y eso es todo! La nueva opción aparecerá automáticamente en la interfaz.

## Build y Ejecución

Para construir y ejecutar el proyecto:

1.  Abre `TranscriptoGeminiVsCode.xcodeproj` en Xcode 15 o superior.
2.  Añade los paquetes locales al proyecto:
    - Arrastra cada una de las carpetas de `Packages` (`CoreUI`, `Domain`, etc.) al navegador de proyectos de Xcode.
    - En la configuración del target `TranscriptoGeminiVsCode`, en la pestaña "General", sección "Frameworks, Libraries, and Embedded Content", añade las librerías de los paquetes (`Domain`, `DataCrypto`, `CoreUI`).
3.  Elige un simulador de iOS 16+ o un dispositivo físico.
4.  Pulsa "Run" (Cmd+R).
