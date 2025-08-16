package com.zarabandajose.transcripto.domain.model;

/**
 * Parámetros de configuración para las operaciones de cifrado.
 *
 * @param method Método de cifrado a utilizar
 * @param key Clave para Vigenère y XOR, ignorada para otros métodos
 * @param shift Desplazamiento para César, ignorado para otros métodos
 * @param salt Salt opcional para todos los métodos
 * @param useSalt Indica si se debe usar salt
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0018\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\nH\u00c6\u0003J;\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\n2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u0010\u001d\u001a\u00020\u0005J\u0006\u0010\u001e\u001a\u00020\u0007J\t\u0010\u001f\u001a\u00020\u0007H\u00d6\u0001J\u0006\u0010 \u001a\u00020\nJ\t\u0010!\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\""}, d2 = {"Lcom/zarabandajose/transcripto/domain/model/CipherParams;", "", "method", "Lcom/zarabandajose/transcripto/domain/model/CipherMethod;", "key", "", "shift", "", "salt", "useSalt", "", "(Lcom/zarabandajose/transcripto/domain/model/CipherMethod;Ljava/lang/String;ILjava/lang/String;Z)V", "getKey", "()Ljava/lang/String;", "getMethod", "()Lcom/zarabandajose/transcripto/domain/model/CipherMethod;", "getSalt", "getShift", "()I", "getUseSalt", "()Z", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "getEffectiveKey", "getEffectiveShift", "hashCode", "isValid", "toString", "domain_debug"})
public final class CipherParams {
    @org.jetbrains.annotations.NotNull()
    private final com.zarabandajose.transcripto.domain.model.CipherMethod method = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String key = null;
    private final int shift = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String salt = null;
    private final boolean useSalt = false;
    
    public CipherParams(@org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.domain.model.CipherMethod method, @org.jetbrains.annotations.NotNull()
    java.lang.String key, int shift, @org.jetbrains.annotations.NotNull()
    java.lang.String salt, boolean useSalt) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.zarabandajose.transcripto.domain.model.CipherMethod getMethod() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getKey() {
        return null;
    }
    
    public final int getShift() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSalt() {
        return null;
    }
    
    public final boolean getUseSalt() {
        return false;
    }
    
    /**
     * Obtiene la clave efectiva combinando la clave base con el salt si está habilitado.
     * Solo aplica para Vigenère y XOR.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEffectiveKey() {
        return null;
    }
    
    /**
     * Obtiene el desplazamiento efectivo para César incluyendo el hash del salt si está habilitado.
     */
    public final int getEffectiveShift() {
        return 0;
    }
    
    /**
     * Valida que los parámetros sean correctos para el método seleccionado.
     * @return true si los parámetros son válidos
     */
    public final boolean isValid() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.zarabandajose.transcripto.domain.model.CipherMethod component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.zarabandajose.transcripto.domain.model.CipherParams copy(@org.jetbrains.annotations.NotNull()
    com.zarabandajose.transcripto.domain.model.CipherMethod method, @org.jetbrains.annotations.NotNull()
    java.lang.String key, int shift, @org.jetbrains.annotations.NotNull()
    java.lang.String salt, boolean useSalt) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}