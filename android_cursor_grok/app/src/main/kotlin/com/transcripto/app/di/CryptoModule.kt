package com.transcripto.app.di

import com.transcripto.crypto.CipherRepositoryImpl
import com.transcripto.crypto.DecryptTextUseCaseImpl
import com.transcripto.crypto.DetectAndParseEnvelopeUseCaseImpl
import com.transcripto.crypto.EncryptTextUseCaseImpl
import com.transcripto.crypto.GenerateSaltUseCaseImpl
import com.transcripto.crypto.SaltProvider
import com.transcripto.domain.CipherRepository
import com.transcripto.domain.DecryptTextUseCase
import com.transcripto.domain.DetectAndParseEnvelopeUseCase
import com.transcripto.domain.EncryptTextUseCase
import com.transcripto.domain.GenerateSaltUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CryptoModule {
    @Provides
    @Singleton
    fun provideSaltProvider(): SaltProvider = SaltProvider()

    @Provides
    @Singleton
    fun provideCipherRepository(): CipherRepository = CipherRepositoryImpl()

    @Provides
    @Singleton
    fun provideGenerateSalt(provider: SaltProvider): GenerateSaltUseCase = GenerateSaltUseCaseImpl(provider)

    @Provides
    @Singleton
    fun provideEncrypt(repo: CipherRepository): EncryptTextUseCase = EncryptTextUseCaseImpl(repo)

    @Provides
    @Singleton
    fun provideParser(): DetectAndParseEnvelopeUseCase = DetectAndParseEnvelopeUseCaseImpl()

    @Provides
    @Singleton
    fun provideDecrypt(repo: CipherRepository, parser: DetectAndParseEnvelopeUseCase): DecryptTextUseCase = DecryptTextUseCaseImpl(repo, parser)
}
