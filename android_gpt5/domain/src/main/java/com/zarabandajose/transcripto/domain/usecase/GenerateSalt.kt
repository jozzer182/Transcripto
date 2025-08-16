package com.zarabandajose.transcripto.domain.usecase

import com.zarabandajose.transcripto.core.common.AppResult
import com.zarabandajose.transcripto.core.common.runCatchingResult
import com.zarabandajose.transcripto.domain.provider.SaltProvider

class GenerateSalt(
    private val provider: SaltProvider,
) {
    operator fun invoke(): AppResult<ByteArray> = runCatchingResult {
        provider.nextSalt()
    }
}
