package com.zarabandajose.transcripto.domain

interface SaltProvider {
    fun generateSalt(): String
}
