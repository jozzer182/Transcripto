package com.zarabandajose.transcripto_gpt5.di

import android.content.Context
import com.zarabandajose.transcripto.domain.cipher.CipherMethod
import com.zarabandajose.transcripto.domain.model.Method
import com.zarabandajose.transcripto.domain.usecase.DecryptText
import com.zarabandajose.transcripto.domain.usecase.DetectAndParseEnvelope
import com.zarabandajose.transcripto.domain.usecase.EncryptText
import com.zarabandajose.transcripto.domain.usecase.GenerateSalt
import com.zarabandajose.transcripto.domain.provider.SaltProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideEncryptText(cipherMap: Map<Method, @JvmSuppressWildcards CipherMethod>) = EncryptText(cipherMap)

    @Provides
    @Singleton
    fun provideDecryptText(cipherMap: Map<Method, @JvmSuppressWildcards CipherMethod>) = DecryptText(cipherMap)

    @Provides
    @Singleton
    fun provideDetectAndParseEnvelope() = DetectAndParseEnvelope()

    @Provides
    @Singleton
    fun provideGenerateSalt(provider: SaltProvider) = GenerateSalt(provider)

    @Provides
    @Singleton
    fun provideAppContext(@ApplicationContext context: Context): Context = context
}
