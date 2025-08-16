package com.zarabandajose.transcripto.domain.provider

/** Proveedor de salt aleatorio. */
interface SaltProvider {
    fun nextSalt(): ByteArray
}
