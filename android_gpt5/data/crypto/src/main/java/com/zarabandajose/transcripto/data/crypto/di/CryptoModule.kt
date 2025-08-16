package com.zarabandajose.transcripto.data.crypto.di

import com.zarabandajose.transcripto.data.crypto.Base64Codec
import com.zarabandajose.transcripto.data.crypto.CaesarCipher
import com.zarabandajose.transcripto.data.crypto.VigenereCipher
import com.zarabandajose.transcripto.data.crypto.XorCipher
import com.zarabandajose.transcripto.data.crypto.provider.SecureRandomSaltProvider
import com.zarabandajose.transcripto.domain.cipher.CipherMethod
import com.zarabandajose.transcripto.domain.model.Method
import com.zarabandajose.transcripto.domain.provider.SaltProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import java.security.SecureRandom

@Module
@InstallIn(SingletonComponent::class)
object CryptoModule {
    @Provides
    @Singleton
    fun provideCipherMap(): Map<Method, CipherMethod> = mapOf(
        Method.BASE64 to Base64Codec(),
        Method.CAESAR to CaesarCipher(),
        Method.VIGENERE to VigenereCipher(),
        Method.XOR to XorCipher(),
    )

    @Provides
    @Singleton
    fun provideSaltProvider(random: SecureRandom): SaltProvider = SecureRandomSaltProvider(random)

    @Provides
    @Singleton
    fun provideSecureRandom(): SecureRandom = SecureRandom()
}
