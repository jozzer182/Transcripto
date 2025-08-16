# Transcripto

Aplicación Android nativa (Kotlin, Jetpack Compose, Material 3) para cifrar y descifrar texto usando métodos simples: Base64, César, Vigenère y XOR. Arquitectura Clean Architecture + MVVM y modularización.

## Módulos
- :app — UI, navegación, DI, Sharesheet.
- :core:ui — componentes y tema Compose.
- :core:common — utilidades y wrappers.
- :domain — contratos y casos de uso.
- :data:crypto — implementaciones de cifrado y DI.
- :testing — utilidades de test.

## Métodos y reglas
- Base64: codificación/decodificación UTF-8 via `java.util.Base64`. Si hay salt al cifrar, se antepone `salt:texto` antes de codificar. Al descifrar, si empieza por `salt:` se elimina.
- César: solo A–Z/a–z con wrap-around; el resto se conserva. Desplazamiento entero (negativo o positivo). Si hay salt se suma `hash(salt) % 26` al desplazamiento en cifrado y se resta en descifrado.
- Vigenère: solo sobre letras (A–Z/a–z), sin acentos (normalización), conserva mayúsculas/minúsculas; el resto de caracteres se copia. La clave efectiva es `clave + salt` si hay salt.
- XOR: byte a byte sobre UTF-8. Cifrar y descifrar es la misma operación. Resultado cifrado se codifica en Base64; al descifrar, primero decodificar Base64 y luego XOR. Clave efectiva `clave + salt` si hay salt.

## Envelope
Al cifrar se incluye por defecto:
```
method=<METHOD>;salt=<BASE64_SALT_O_VACIO>;payload=<RESULT>
```
Ejemplo: `method=XOR;salt=c2FsdDEy;payload=AbCdEf==`

Al descifrar, si el texto de entrada tiene este prefijo se parsea automáticamente.

## Extender con nuevos métodos
1. Implementa `CipherMethod` en `:data:crypto`.
2. Regístralo en `CryptoModule` para DI.
3. Añade casos de prueba y, si aplica, controles de UI.

## Pruebas
Incluye pruebas de ida/vuelta, Unicode y casos límite en `:data:crypto`.

## Nota
Base64 no es un cifrado. Se incluye por conveniencia de portabilidad.
