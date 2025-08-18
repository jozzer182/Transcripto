package com.example.transcripto.di

import com.example.transcripto.data.crypto.impl.CaesarCipher
import com.example.transcripto.data.crypto.impl.Base64Codec
import com.example.transcripto.data.crypto.impl.VigenereCipher
import com.example.transcripto.data.crypto.impl.XorCipher
import com.example.transcripto.data.crypto.SaltProvider
import com.example.transcripto.domain.crypto.CipherMethod
import com.example.transcripto.domain.usecases.DecryptText
import com.example.transcripto.domain.usecases.DetectAndParseEnvelope
import com.example.transcripto.domain.usecases.EncryptText
import com.example.transcripto.domain.usecases.GenerateSalt
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
    fun provideCaesarCipher(): CipherMethod.Caesar = CaesarCipher()
    
    @Provides
    @Singleton
    fun provideBase64Codec(): CipherMethod.Base64 = Base64Codec()
    
    @Provides
    @Singleton
    fun provideVigenereCipher(): CipherMethod.Vigenere = VigenereCipher()
    
    @Provides
    @Singleton
    fun provideXorCipher(): CipherMethod.XOR = XorCipher()
    
    @Provides
    @Singleton
    fun provideSaltProvider(): SaltProvider = SaltProvider()
    
    @Provides
    @Singleton
    fun provideEncryptText(
        caesar: CipherMethod.Caesar,
        base64: CipherMethod.Base64,
        vigenere: CipherMethod.Vigenere,
        xor: CipherMethod.XOR
    ): EncryptText = EncryptText(caesar, base64, vigenere, xor)
    
    @Provides
    @Singleton
    fun provideDecryptText(
        caesar: CipherMethod.Caesar,
        base64: CipherMethod.Base64,
        vigenere: CipherMethod.Vigenere,
        xor: CipherMethod.XOR
    ): DecryptText = DecryptText(caesar, base64, vigenere, xor)
    
    @Provides
    @Singleton
    fun provideDetectAndParseEnvelope(): DetectAndParseEnvelope = DetectAndParseEnvelope()
    
    @Provides
    @Singleton
    fun provideGenerateSalt(saltProvider: SaltProvider): GenerateSalt = GenerateSalt(saltProvider)
}