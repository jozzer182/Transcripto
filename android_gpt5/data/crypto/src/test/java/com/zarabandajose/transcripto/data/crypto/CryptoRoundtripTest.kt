package com.zarabandajose.transcripto.data.crypto

import com.google.common.truth.Truth.assertThat
import com.zarabandajose.transcripto.domain.cipher.CipherMethod
import com.zarabandajose.transcripto.domain.model.Method
import org.junit.Test
import java.util.Base64

class CryptoRoundtripTest {
    private val caesar = CaesarCipher()
    private val base64 = Base64Codec()
    private val vig = VigenereCipher()
    private val xor = XorCipher()

    @Test fun caesar_roundtrip_with_salt() {
        val salt = "salt12".toByteArray()
        val p = CipherMethod.Params(shift = 5, salt = salt, useSalt = true)
        val enc = caesar.encrypt("HolaZz", p)
        val dec = caesar.decrypt(enc, p)
        assertThat(dec).isEqualTo("HolaZz")
    }

    @Test fun base64_with_salt_prefix() {
        val salt = "s".toByteArray()
        val p = CipherMethod.Params(salt = salt, useSalt = true)
        val enc = base64.encrypt("hola", p)
        val dec = base64.decrypt(enc, p)
        assertThat(dec).isEqualTo("hola")
    }

    @Test fun vigenere_roundtrip_emojis() {
        val p = CipherMethod.Params(key = "Clave", salt = "S".toByteArray(), useSalt = true)
        val text = "Hola 😊 Mundo"
        val enc = vig.encrypt(text, p)
        val dec = vig.decrypt(enc, p)
        assertThat(dec).isEqualTo(text)
    }

    @Test fun xor_roundtrip() {
        val p = CipherMethod.Params(key = "k", salt = "S2".toByteArray(), useSalt = true)
        val text = "Árbol 😀"
        val enc = xor.encrypt(text, p)
        val dec = xor.decrypt(enc, p)
        assertThat(dec).isEqualTo(text)
    }
}
