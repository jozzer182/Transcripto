# Cambios Realizados - Mostrar Solo Payload

## Problema Identificado
La aplicación mostraba el resultado completo del cifrado incluyendo los metadatos del envelope en el formato:
```
method=BASE64;salt=;payload=SG9sYSBNdW5kbw==
```

El usuario solicitó que solo se muestre el payload (resultado cifrado/descifrado) sin los metadatos.

## Solución Implementada

### 1. Modificación del HomeViewModel
**Archivo:** `app/src/main/java/com/zarabandajose/transcripto/ui/home/HomeViewModel.kt`

#### Cambios realizados:
- **Nueva función:** `extractPayloadFromEnvelope()` que extrae solo la parte del payload del envelope
- **Modificación en `processText()`:** Ahora extrae el payload del resultado de cifrado antes de mostrarlo al usuario
- **Lógica:** 
  - En **cifrado**: Extrae el payload del envelope generado
  - En **descifrado**: Mantiene el resultado directo (ya es solo el payload)

#### Código agregado:
```kotlin
private fun extractPayloadFromEnvelope(text: String): String {
    // Verificar si tiene formato envelope: method=...;salt=...;payload=...
    if (text.contains("method=") && text.contains("salt=") && text.contains("payload=")) {
        val payloadIndex = text.indexOf("payload=")
        if (payloadIndex != -1) {
            return text.substring(payloadIndex + 8) // 8 es la longitud de "payload="
        }
    }
    // Si no es envelope, devolver el texto original
    return text
}
```

### 2. Actualización de Dependencias
**Archivo:** `gradle/libs.versions.toml`
- Agregada versión de MockK: `mockk = "1.13.8"`
- Agregada librería MockK para testing

**Archivo:** `app/build.gradle.kts`
- Agregada dependencia: `testImplementation(libs.mockk)`

### 3. Pruebas Unitarias
**Archivo:** `app/src/test/java/com/zarabandajose/transcripto/ui/home/HomeViewModelTest.kt`

#### Casos de prueba creados:
1. **`encrypt text should show only payload without envelope metadata`**
   - Verifica que al cifrar solo se muestre el payload
   - Confirma que no aparezcan los metadatos `method=`, `salt=`, `payload=`

2. **`decrypt text should show decrypted payload directly`**
   - Verifica que el descifrado muestre directamente el resultado

3. **`encrypt with Caesar cipher should show only encrypted text`**
   - Prueba específica para cifrado César

4. **Pruebas adicionales** para manejo de errores y funciones básicas

## Resultado Final

### Antes del cambio:
```
Resultado: method=BASE64;salt=;payload=SG9sYSBNdW5kbw==
```

### Después del cambio:
```
Resultado: SG9sYSBNdW5kbw==
```

## Verificación
- ✅ Compilación exitosa
- ✅ Todas las pruebas unitarias pasan
- ✅ APK generado correctamente
- ✅ Funcionalidad de envelope preservada internamente
- ✅ Solo el payload se muestra al usuario

## Archivos Modificados
1. `app/src/main/java/com/zarabandajose/transcripto/ui/home/HomeViewModel.kt`
2. `gradle/libs.versions.toml`
3. `app/build.gradle.kts`
4. `app/src/test/java/com/zarabandajose/transcripto/ui/home/HomeViewModelTest.kt` (nuevo)

## Impacto
- **Positivo:** La UI ahora muestra solo el resultado relevante al usuario
- **Neutro:** La funcionalidad interna del envelope se mantiene intacta
- **Compatibilidad:** El descifrado de envelopes sigue funcionando correctamente
