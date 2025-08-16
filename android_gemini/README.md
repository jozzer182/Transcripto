# Transcripto

Una aplicación Android nativa para cifrar y descifrar texto con varios métodos.

## Métodos de Cifrado

- **César**: Cifrado por desplazamiento simple.
- **Base64**: Codificación, no un cifrado seguro.
- **Vigenère**: Cifrado polialfabético.
- **XOR**: Cifrado de flujo simple.

### Formato de Salida

Al cifrar, la aplicación genera una salida con metadatos para facilitar el descifrado:

`method=<METHOD>;salt=<BASE64_OF_SALT_OR_EMPTY>;payload=<RESULT>`

Ejemplo: `method=XOR;salt=c2FsdDEy;payload=AbCdEf==`

## Cómo agregar un nuevo método de cifrado

1.  Implementa la interfaz `CipherMethod` en el módulo `:data:crypto`.
2.  Agrega el nuevo tipo de método a `CipherMethodType` en el módulo `:domain`.
3.  Actualiza el `provideCipherMethodFactory` en `AppModule` para incluir tu nuevo cifrado.
4.  Agrega una nueva opción en la UI en `HomeScreen` para tu nuevo método.
