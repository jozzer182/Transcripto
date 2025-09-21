# Guía para Subir Transcripto a la App Store

## ✅ 1. Configuración de Criptografía (COMPLETADO)

He creado archivos Info.plist actualizados con la configuración necesaria:

- `Info_updated.plist` en el proyecto Flutter
- `Info.plist` en ios_gemini 
- `Info.plist` en ios_claude

**Configuración agregada:**
```xml
<key>ITSAppUsesNonExemptEncryption</key>
<true/>
```

### ⚠️ IMPORTANTE: Reemplazar archivos originales
Debes reemplazar los archivos Info.plist originales con las versiones actualizadas que creé.

## 📋 2. Checklist para App Store

### Configuración Básica Requerida:
- [x] ITSAppUsesNonExemptEncryption configurado
- [ ] Bundle Identifier único configurado
- [ ] Certificados de desarrollo y distribución
- [ ] Provisioning Profiles configurados
- [ ] App Store Connect configurado

### Información de la App:
- [ ] Nombre de la app
- [ ] Descripción detallada
- [ ] Keywords para búsqueda
- [ ] Categoría de la app
- [ ] Precio (gratis o de pago)

### Assets Requeridos:
- [ ] App Icon (1024x1024px)
- [ ] Screenshots (diferentes tamaños para iPhone/iPad)
- [ ] Launch Screen configurado
- [ ] App Store Preview (opcional pero recomendado)

### Compliance y Legal:
- [ ] Política de Privacidad (OBLIGATORIA)
- [ ] Términos de Servicio
- [ ] Declaración de uso de datos
- [ ] Export Compliance (ya configurado con ITSAppUsesNonExemptEncryption)

## 🔧 3. Configuraciones Técnicas Adicionales

### Version y Build Numbers:
```
CFBundleShortVersionString: 1.0 (versión visible al usuario)
CFBundleVersion: 1 (build number, debe incrementarse con cada subida)
```

### Bundle Identifier:
Asegúrate de tener un identificador único como:
`com.tuempresa.transcripto`

### Capabilities requeridas:
Revisa si tu app necesita:
- [ ] Acceso a micrófono (para transcripción)
- [ ] Acceso a archivos
- [ ] Background processing
- [ ] Network requests

## 🚀 4. Proceso de Subida

1. **Xcode Archive & Upload**
2. **App Store Connect Review**
3. **TestFlight (opcional para testing)**
4. **Submit for Review**

## ⚠️ 5. Posibles Problemas Comunes

- Missing Info.plist keys
- Invalid Bundle Identifier
- Missing Privacy Policy
- Encryption export compliance
- App crashes on review devices

## 📞 6. Siguientes Pasos

1. Reemplaza los archivos Info.plist con las versiones actualizadas
2. Configura tu Bundle Identifier en Xcode
3. Prepara los assets (iconos, screenshots)
4. Crea tu cuenta de Apple Developer
5. Configura App Store Connect