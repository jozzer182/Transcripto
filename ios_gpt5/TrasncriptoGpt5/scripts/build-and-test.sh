#!/usr/bin/env bash
set -euo pipefail

# Detectar esquema por defecto si no se pasa env SCHEME
SCHEME="${SCHEME:-TrasncriptoGpt5}"
DEST="${DEST:-platform=iOS Simulator,name=iPhone 15,OS=latest}"
CONFIG="${CONFIG:-Debug}"

echo "==> Limpiando"
xcodebuild -scheme "$SCHEME" -configuration "$CONFIG" clean -quiet || true

echo "==> Compilando"
if command -v xcpretty >/dev/null 2>&1; then
  xcodebuild \
    -scheme "$SCHEME" \
    -configuration "$CONFIG" \
    -destination "$DEST" \
    -skipPackagePluginValidation \
    -skipMacroValidation \
    build | xcpretty || exit 1
else
  xcodebuild \
    -scheme "$SCHEME" \
    -configuration "$CONFIG" \
    -destination "$DEST" \
    -skipPackagePluginValidation \
    -skipMacroValidation \
    build || exit 1
fi

echo "==> Ejecutando tests"
if command -v xcpretty >/dev/null 2>&1; then
  xcodebuild \
    -scheme "$SCHEME" \
    -configuration "$CONFIG" \
    -destination "$DEST" \
    -skipPackagePluginValidation \
    -skipMacroValidation \
    test | xcpretty || true
else
  xcodebuild \
    -scheme "$SCHEME" \
    -configuration "$CONFIG" \
    -destination "$DEST" \
    -skipPackagePluginValidation \
    -skipMacroValidation \
    test || true
fi
