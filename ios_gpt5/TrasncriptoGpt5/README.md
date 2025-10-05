# Transcripto

App iOS (SwiftUI) para cifrar/descifrar texto usando Base64, César, Vigenère y XOR. Arquitectura Clean + MVVM + paquetes locales.

## Scripts

- build y tests:

```
SCHEME="TrasncriptoGpt5" ./scripts/build-and-test.sh
```

- lint:

```
./scripts/lint.sh
```

## Notas de cifrado

- Base64: usa Data(...).base64EncodedString(). Si hay salt activo, se antepone "salt:texto" antes de codificar. Al descifrar con salt, se valida y remueve el prefijo.
- César: A–Z / a–z con wrap-around, otros caracteres intactos. Si salt activo, se ajusta el desplazamiento con hash(salt) % 26 en cifrado y se resta en descifrado.
- Vigenère: opera solo en letras, preserva mayúsc/minúsc, no letras se copian. Con salt, la clave efectiva es clave + salt.
- XOR: byte a byte en UTF-8. El cifrado se muestra en Base64; al descifrar, la entrada debe ser Base64 válido. Con salt, clave efectiva = clave + salt.

## Próximos pasos

- Vincular paquetes locales al target en Xcode (Project > Package Dependencies > Add Local...).
- Implementar tests con XCTest (incluyendo Unicode y errores).
- Integrar los casos de uso reales en el ViewModel.
