package com.zarabandajose.transcripto.data.crypto.di

import com.zarabandajose.transcripto.data.crypto.cipher.Base64Codec
import com.zarabandajose.transcripto.data.crypto.cipher.CaesarCipher
import com.zarabandajose.transcripto.data.crypto.cipher.VigenereCipher
import com.zarabandajose.transcripto.data.crypto.cipher.XorCipher
import com.zarabandajose.transcripto.data.crypto.repository.CipherRepositoryImpl
import com.zarabandajose.transcripto.data.crypto.repository.SaltRepositoryImpl
import com.zarabandajose.transcripto.domain.repository.CipherRepository
import com.zarabandajose.transcripto.domain.repository.SaltRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo de Hilt para las dependencias del módulo crypto.
 */
@Module
@InstallIn(SingletonComponent::class)
object CryptoModule {
    
    @Provides
    @Singleton
    fun provideCaesarCipher(): CaesarCipher = CaesarCipher()
    
    @Provides
    @Singleton
    fun provideBase64Codec(): Base64Codec = Base64Codec()
    
    @Provides
    @Singleton
    fun provideVigenereCipher(): VigenereCipher = VigenereCipher()
    
    @Provides
    @Singleton
    fun provideXorCipher(): XorCipher = XorCipher()
    
    @Provides
    @Singleton
    fun provideCipherRepository(
        caesarCipher: CaesarCipher,
        base64Codec: Base64Codec,
        vigenereCipher: VigenereCipher,
        xorCipher: XorCipher
    ): CipherRepository = CipherRepositoryImpl(caesarCipher, base64Codec, vigenereCipher, xorCipher)
    
    @Provides
    @Singleton
    fun provideSaltRepository(): SaltRepository = SaltRepositoryImpl()
}
