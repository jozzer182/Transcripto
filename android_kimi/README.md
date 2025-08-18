# Transcripto - Android Encryption Application

A modern Android application for text encryption and decryption using various cipher methods. Built with Jetpack Compose, Material Design 3, and Hilt for dependency injection.

## Features

### Core Functionality
- **Multiple Cipher Methods**: Caesar, Base64, Vigenère, and XOR ciphers
- **Salt Generation**: Secure random salt generation using SecureRandom
- **Envelope Format**: Support for method;salt;payload format for secure storage
- **Real-time Detection**: Automatic envelope format detection
- **Clipboard Integration**: Copy/paste functionality for input and output
- **Sharing**: Share encrypted/decrypted results via Android share sheet

### User Experience
- **Material Design 3**: Modern, responsive UI with dynamic theming
- **Dark/Light Theme**: Automatic theme switching based on system preferences
- **Accessibility**: Full accessibility support with content descriptions
- **Localization**: English and Spanish language support
- **Responsive Layout**: Optimized for phones and tablets

### Technical Features
- **Clean Architecture**: Domain-driven design with clear separation of concerns
- **Dependency Injection**: Hilt for maintainable and testable code
- **State Management**: ViewModel with Compose state management
- **Error Handling**: Comprehensive error handling with user-friendly messages
- **Input Validation**: Real-time validation of inputs and parameters

## Cipher Methods

### 1. Caesar Cipher
- **Type**: Substitution cipher
- **Key**: Numeric shift value (e.g., "3")
- **Usage**: Simple encryption for educational purposes
- **Example**: "HELLO" with key "3" → "KHOOR"

### 2. Base64
- **Type**: Encoding (not encryption)
- **Key**: Not required
- **Usage**: Data encoding for transmission/storage
- **Example**: "Hello" → "SGVsbG8="

### 3. Vigenère Cipher
- **Type**: Polyalphabetic substitution
- **Key**: Alphabetic keyword (e.g., "KEY")
- **Usage**: Stronger than Caesar, historical significance
- **Example**: "HELLO" with key "KEY" → "RIJVS"

### 4. XOR Cipher
- **Type**: Bitwise operation
- **Key**: Any string
- **Usage**: Simple symmetric encryption
- **Example**: "Hello" with key "secret" → Base64 encoded result

## Architecture

### Project Structure
```
transcripto/
├── app/                          # Android application module
│   ├── src/main/java/com/transcripto/
│   │   ├── MainActivity.kt      # Main activity
│   │   ├── ui/
│   │   │   ├── screen/          # Compose screens
│   │   │   ├── theme/           # Material Design theming
│   │   │   └── viewmodel/       # ViewModels
│   │   └── di/                  # Dependency injection
├── data/crypto/                 # Data layer - crypto implementations
│   └── src/main/java/com/transcripto/data/crypto/
│       ├── cipher/              # Cipher implementations
│       └── parser/              # Envelope parser
├── domain/                      # Domain layer - business logic
│   └── src/main/java/com/transcripto/domain/
│       ├── cipher/              # Cipher interfaces
│       ├── model/               # Domain models
│       ├── repository/          # Repository interfaces
│       └── usecase/             # Use cases
└── testing/                    # Testing utilities
```

### Key Components

#### Domain Layer
- **CipherMethod**: Interface for all cipher implementations
- **Use Cases**: EncryptText, DecryptText, DetectAndParseEnvelope, GenerateSalt
- **Models**: CipherParams, CipherResult, EnvelopeData

#### Data Layer
- **CaesarCipher**: Caesar cipher implementation
- **Base64Codec**: Base64 encoding/decoding
- **VigenereCipher**: Vigenère cipher implementation
- **XorCipher**: XOR cipher implementation
- **SecureRandomSaltProvider**: Secure salt generation
- **EnvelopeParserImpl**: Envelope format parsing

#### Presentation Layer
- **TranscriptoViewModel**: Main ViewModel for UI state
- **HomeScreen**: Main application screen
- **Theme**: Material Design 3 theming configuration

## Usage

### Basic Encryption
1. Enter text in the input field
2. Select a cipher method from the dropdown
3. Enter the required key (varies by method)
4. Toggle "Use Salt" for enhanced security (optional)
5. Click "Encrypt"
6. Copy or share the encrypted result

### Basic Decryption
1. Enter encrypted text or envelope format
2. The app will auto-detect envelope format
3. Enter the correct key
4. Click "Decrypt"
5. View the decrypted text

### Envelope Format
The envelope format is: `method=<METHOD>;salt=<BASE64_SALT>;payload=<ENCRYPTED_TEXT>`

Example:
```
method=CAESAR;salt=YWJjMTIz;payload=Khoor#Zruog
```

## Building and Running

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 21+ (Android 5.0+)
- Kotlin 1.9.0+

### Build Commands
```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test

# Run instrumentation tests
./gradlew connectedAndroidTest
```

### Development Setup
1. Clone the repository
2. Open in Android Studio
3. Sync project with Gradle files
4. Run on emulator or device

## Extension Guide

### Adding New Cipher Methods

1. **Create Cipher Implementation**:
```kotlin
class NewCipher @Inject constructor() : CipherMethod {
    override fun getName(): String = "NEW_CIPHER"
    
    override fun encrypt(params: CipherParams): CipherResult {
        // Implementation
    }
    
    override fun decrypt(params: CipherParams): CipherResult {
        // Implementation
    }
}
```

2. **Register in AppModule**:
```kotlin
@Provides
@IntoMap
@StringKey("NEW_CIPHER")
fun provideNewCipher(): CipherMethod = NewCipher()
```

3. **Update UI** (automatically handled by the system)

### Adding New Languages
1. Create new strings file: `res/values-xx/strings.xml`
2. Translate all string resources
3. Add language-specific content descriptions

## Testing

### Unit Tests
- **Domain Layer**: Test cipher algorithms, use cases
- **Data Layer**: Test envelope parsing, salt generation
- **ViewModel**: Test state management and business logic

### Integration Tests
- **UI Tests**: Test user interactions and flows
- **End-to-End**: Test complete encryption/decryption workflows

### Sample Test Structure
```kotlin
@Test
fun testCaesarEncryption() {
    val caesar = CaesarCipher()
    val params = CipherParams("HELLO", "3")
    val result = caesar.encrypt(params)
    
    assertTrue(result is CipherResult.Success)
    assertEquals("KHOOR", (result as CipherResult.Success).data)
}
```

## Security Considerations

- **Salt Generation**: Uses cryptographically secure random generation
- **Key Storage**: No persistent storage of keys (user must remember)
- **Input Validation**: All inputs are validated before processing
- **Error Handling**: Detailed error messages without exposing sensitive data

## Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Android Jetpack team for Compose and Architecture Components
- Material Design team for Material 3 guidelines
- Cryptography community for educational cipher implementations

## Support

For questions or issues:
- Check existing [GitHub Issues](../../issues)
- Create new issue with detailed description
- Include steps to reproduce and expected behavior