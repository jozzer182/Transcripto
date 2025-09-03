#!/usr/bin/env bash
# Script de compilación y pruebas para Transcripto
# Uso: ./scripts/build-and-test.sh

set -euo pipefail

# Configuración por defecto
SCHEME="${SCHEME:-transcripto_calude}"
DEST="${DEST:-platform=iOS Simulator,name=iPhone 15,OS=latest}"
CONFIG="${CONFIG:-Debug}"
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Función para imprimir con colores
print_step() {
    echo -e "${BLUE}==> $1${NC}"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

# Función para verificar si una herramienta está instalada
check_tool() {
    local tool=$1
    local install_hint=$2
    
    if ! command -v "$tool" &> /dev/null; then
        print_warning "$tool no está instalado. $install_hint"
        return 1
    fi
    return 0
}

# Función principal
main() {
    print_step "Iniciando build y test para Transcripto"
    
    # Verificar que estamos en el directorio correcto
    if [[ ! -f "$PROJECT_DIR/transcripto_calude.xcodeproj/project.pbxproj" ]]; then
        print_error "No se encontró el proyecto Xcode. Ejecute desde la raíz del proyecto."
        exit 1
    fi
    
    cd "$PROJECT_DIR"
    
    # Verificar herramientas opcionales
    HAS_XCPRETTY=false
    if check_tool "xcpretty" "Instalar con: gem install xcpretty"; then
        HAS_XCPRETTY=true
    fi
    
    # Mostrar configuración
    echo
    print_step "Configuración:"
    echo "  Esquema: $SCHEME"
    echo "  Destino: $DEST"
    echo "  Configuración: $CONFIG"
    echo "  Directorio: $PROJECT_DIR"
    echo "  xcpretty: $HAS_XCPRETTY"
    echo
    
    # Limpiar proyecto
    print_step "Limpiando proyecto"
    if $HAS_XCPRETTY; then
        xcodebuild -scheme "$SCHEME" -configuration "$CONFIG" clean -quiet | xcpretty --color || {
            print_error "Error durante la limpieza"
            exit 1
        }
    else
        xcodebuild -scheme "$SCHEME" -configuration "$CONFIG" clean -quiet || {
            print_error "Error durante la limpieza"
            exit 1
        }
    fi
    print_success "Proyecto limpiado"
    
    # Compilar proyecto
    print_step "Compilando proyecto"
    if $HAS_XCPRETTY; then
        xcodebuild \
            -scheme "$SCHEME" \
            -configuration "$CONFIG" \
            -destination "$DEST" \
            -skipPackagePluginValidation \
            -skipMacroValidation \
            build | xcpretty --color || {
            print_error "Error durante la compilación"
            exit 1
        }
    else
        xcodebuild \
            -scheme "$SCHEME" \
            -configuration "$CONFIG" \
            -destination "$DEST" \
            -skipPackagePluginValidation \
            -skipMacroValidation \
            build || {
            print_error "Error durante la compilación"
            exit 1
        }
    fi
    print_success "Proyecto compilado exitosamente"
    
    # Verificar si hay tests para ejecutar
    print_step "Verificando tests"
    if find transcripto_calude -name "*Tests.swift" -o -name "*Test.swift" | grep -q .; then
        print_step "Ejecutando tests"
        if $HAS_XCPRETTY; then
            xcodebuild \
                -scheme "$SCHEME" \
                -configuration "$CONFIG" \
                -destination "$DEST" \
                -skipPackagePluginValidation \
                -skipMacroValidation \
                test | xcpretty --color --test || {
                print_error "Los tests fallaron"
                exit 1
            }
        else
            xcodebuild \
                -scheme "$SCHEME" \
                -configuration "$CONFIG" \
                -destination "$DEST" \
                -skipPackagePluginValidation \
                -skipMacroValidation \
                test || {
                print_error "Los tests fallaron"
                exit 1
            }
        fi
        print_success "Tests ejecutados exitosamente"
    else
        print_warning "No se encontraron archivos de tests para ejecutar"
    fi
    
    # Ejecutar SwiftLint si está disponible
    if check_tool "swiftlint" "Instalar con: brew install swiftlint"; then
        print_step "Ejecutando SwiftLint"
        swiftlint || {
            print_warning "SwiftLint encontró problemas de estilo"
        }
        print_success "Análisis de SwiftLint completado"
    fi
    
    # Ejecutar SwiftFormat si está disponible
    if check_tool "swiftformat" "Instalar con: brew install swiftformat"; then
        print_step "Verificando formato con SwiftFormat"
        if swiftformat --lint transcripto_calude; then
            print_success "Código tiene formato correcto"
        else
            print_warning "Código necesita formateo. Ejecute: swiftformat transcripto_calude"
        fi
    fi
    
    echo
    print_success "¡Build y test completados exitosamente!"
    echo
    print_step "Comandos útiles:"
    echo "  Ver esquemas disponibles: xcodebuild -list"
    echo "  Solo compilar: SCHEME=\"$SCHEME\" $0"
    echo "  Cambiar destino: DEST=\"platform=iOS Simulator,name=iPhone 14,OS=latest\" $0"
    echo "  Formatear código: swiftformat transcripto_calude"
    echo "  Linting: swiftlint"
}

# Mostrar ayuda
if [[ "${1:-}" == "--help" ]] || [[ "${1:-}" == "-h" ]]; then
    echo "Script de compilación y pruebas para Transcripto"
    echo
    echo "Uso: $0 [opciones]"
    echo
    echo "Variables de entorno:"
    echo "  SCHEME    Esquema de Xcode (default: transcripto_calude)"
    echo "  DEST      Destino de compilación (default: iPhone 15 Simulator)"
    echo "  CONFIG    Configuración (default: Debug)"
    echo
    echo "Ejemplos:"
    echo "  $0                                    # Usar configuración por defecto"
    echo "  SCHEME=\"MiEsquema\" $0               # Cambiar esquema"
    echo "  CONFIG=\"Release\" $0                 # Compilar en Release"
    echo "  DEST=\"generic/platform=iOS\" $0      # Compilar para dispositivo"
    echo
    exit 0
fi

# Ejecutar función principal
main "$@"
