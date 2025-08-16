# Transcripto

App Android nativa para cifrar y descifrar textos usando varios métodos.

## Métodos soportados
- César: Desplazamiento de letras.
- Base64: Codificación reversible (no es cifrado seguro).
- Vigenère: Cifrado polialfabético.
- XOR: Operación XOR byte a byte.

## Limitaciones
Base64 no es un método de cifrado seguro, solo codificación.

## Ejemplos
Cifrar con XOR y salt: method=XOR;salt=c2FsdDEy;payload=AbCdEf==

## Para agregar nuevos métodos
Implementar la interfaz CipherMethod en :data:crypto y agregar al CipherRepositoryImpl.

## Estructura
- :app: Aplicación principal.
- :core:ui: Componentes UI y theme.
- :core:common: Utilidades comunes.
- :domain: Modelos y use cases.
- :data:crypto: Implementaciones de cifrados.
- :testing: Utilidades de tests.
