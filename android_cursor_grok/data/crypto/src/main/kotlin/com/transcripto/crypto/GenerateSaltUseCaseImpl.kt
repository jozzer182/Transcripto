package com.transcripto.crypto

import com.transcripto.domain.GenerateSaltUseCase

class GenerateSaltUseCaseImpl(private val saltProvider: SaltProvider) : GenerateSaltUseCase {
    override fun invoke(): String = saltProvider.generate()
}
