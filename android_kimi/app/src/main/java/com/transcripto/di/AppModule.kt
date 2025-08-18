package com.transcripto.di

import com.transcripto.data.crypto.cipher.CaesarCipher
import com.transcripto.data.crypto.cipher.Base64Codec
import com.transcripto.data.crypto.cipher.VigenereCipher
import com.transcripto.data.crypto.cipher.XorCipher
import com.transcripto.data.crypto.parser.EnvelopeParserImpl
import com.transcripto.data.crypto.provider.SecureRandomSaltProvider
import com.transcripto.domain.cipher.CipherMethod
import com.transcripto.domain.repository.EnvelopeParser
import com.transcripto.domain.repository.SaltProvider
import com.transcripto.domain.usecase.DecryptText
import com.transcripto.domain.usecase.DetectAndParseEnvelope
import com.transcripto.domain.usecase.EncryptText
import com.transcripto.domain.usecase.GenerateSalt
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSaltProvider(): SaltProvider = SecureRandomSaltProvider()

    @Provides
    @Singleton
    fun provideEnvelopeParser(): EnvelopeParser = EnvelopeParserImpl()

    @Provides
    @Singleton
    fun provideCaesarCipher(): CipherMethod = CaesarCipher()

    @Provides
    @Singleton
    fun provideBase64Codec(): CipherMethod = Base64Codec()

    @Provides
    @Singleton
    fun provideVigenereCipher(): CipherMethod = VigenereCipher()

    @Provides
    @Singleton
    fun provideXorCipher(): CipherMethod = XorCipher()

    @Provides
    @Singleton
    fun provideCipherMethods(
        caesarCipher: CipherMethod,
        base64Codec: CipherMethod,
        vigenereCipher: CipherMethod,
        xorCipher: CipherMethod
    ): Map<CipherMethod, CipherMethod> = mapOf(
        CipherMethod.CAESAR to caesarCipher,
        CipherMethod.BASE64 to base64Codec,
        CipherMethod.VIGENERE to vigenereCipher,
        CipherMethod.XOR to xorCipher
    )

    @Provides
    @Singleton
    fun provideEncryptText(
        cipherMethods: Map<CipherMethod, CipherMethod>,
        envelopeParser: EnvelopeParser
    ): EncryptText = EncryptText(cipherMethods, envelopeParser)

    @Provides
    @Singleton
    fun provideDecryptText(
        cipherMethods: Map<CipherMethod, CipherMethod>
    ): DecryptText = DecryptText(cipherMethods)

    @Provides
    @Singleton
    fun provideDetectAndParseEnvelope(
        envelopeParser: EnvelopeParser
    ): DetectAndParseEnvelope = DetectAndParseEnvelope(envelopeParser)

    @Provides
    @Singleton
    fun provideGenerateSalt(
        saltProvider: SaltProvider
    ): GenerateSalt = GenerateSalt(saltProvider)
}