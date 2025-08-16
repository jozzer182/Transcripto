package com.zarabandajose.transcripto.domain.model;

/**
 * Métodos de cifrado disponibles en la aplicación.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u0000 \t2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\tB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\n"}, d2 = {"Lcom/zarabandajose/transcripto/domain/model/CipherMethod;", "", "(Ljava/lang/String;I)V", "toEnvelopeString", "", "CAESAR", "BASE64", "VIGENERE", "XOR", "Companion", "domain_debug"})
public enum CipherMethod {
    /*public static final*/ CAESAR /* = new CAESAR() */,
    /*public static final*/ BASE64 /* = new BASE64() */,
    /*public static final*/ VIGENERE /* = new VIGENERE() */,
    /*public static final*/ XOR /* = new XOR() */;
    @org.jetbrains.annotations.NotNull()
    public static final com.zarabandajose.transcripto.domain.model.CipherMethod.Companion Companion = null;
    
    CipherMethod() {
    }
    
    /**
     * Convierte el enum a string para usar en el formato de envelope.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String toEnvelopeString() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.zarabandajose.transcripto.domain.model.CipherMethod> getEntries() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/zarabandajose/transcripto/domain/model/CipherMethod$Companion;", "", "()V", "fromEnvelopeString", "Lcom/zarabandajose/transcripto/domain/model/CipherMethod;", "value", "", "domain_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Convierte un string del formato envelope a CipherMethod.
         * @param value String del método
         * @return CipherMethod correspondiente o null si no es válido
         */
        @org.jetbrains.annotations.Nullable()
        public final com.zarabandajose.transcripto.domain.model.CipherMethod fromEnvelopeString(@org.jetbrains.annotations.NotNull()
        java.lang.String value) {
            return null;
        }
    }
}