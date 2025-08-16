package com.zarabandajose.transcripto.app.di

import com.zarabandajose.transcripto.data.crypto.*
import com.zarabandajose.transcripto.domain.CipherMethod
import com.zarabandajose.transcripto.domain.CipherMethodType
import com.zarabandajose.transcripto.domain.SaltProvider
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
    fun provideSaltProvider(): SaltProvider = SaltProviderImpl()

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
}
