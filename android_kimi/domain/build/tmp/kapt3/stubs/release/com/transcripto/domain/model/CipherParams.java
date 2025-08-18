package com.transcripto.domain.model;

/**
 * Data class representing encryption/decryption parameters.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0017\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\nH\u00c6\u0003JF\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001\u00a2\u0006\u0002\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\n2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020\u0007H\u00d6\u0001J\t\u0010 \u001a\u00020\u0005H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006!"}, d2 = {"Lcom/transcripto/domain/model/CipherParams;", "", "method", "Lcom/transcripto/domain/model/CipherMethod;", "key", "", "shift", "", "salt", "useSalt", "", "(Lcom/transcripto/domain/model/CipherMethod;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Z)V", "getKey", "()Ljava/lang/String;", "getMethod", "()Lcom/transcripto/domain/model/CipherMethod;", "getSalt", "getShift", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getUseSalt", "()Z", "component1", "component2", "component3", "component4", "component5", "copy", "(Lcom/transcripto/domain/model/CipherMethod;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Z)Lcom/transcripto/domain/model/CipherParams;", "equals", "other", "hashCode", "toString", "domain_release"})
public final class CipherParams {
    @org.jetbrains.annotations.NotNull()
    private final com.transcripto.domain.model.CipherMethod method = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String key = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer shift = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String salt = null;
    private final boolean useSalt = false;
    
    public CipherParams(@org.jetbrains.annotations.NotNull()
    com.transcripto.domain.model.CipherMethod method, @org.jetbrains.annotations.Nullable()
    java.lang.String key, @org.jetbrains.annotations.Nullable()
    java.lang.Integer shift, @org.jetbrains.annotations.Nullable()
    java.lang.String salt, boolean useSalt) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.transcripto.domain.model.CipherMethod getMethod() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getKey() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getShift() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSalt() {
        return null;
    }
    
    public final boolean getUseSalt() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.transcripto.domain.model.CipherMethod component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.transcripto.domain.model.CipherParams copy(@org.jetbrains.annotations.NotNull()
    com.transcripto.domain.model.CipherMethod method, @org.jetbrains.annotations.Nullable()
    java.lang.String key, @org.jetbrains.annotations.Nullable()
    java.lang.Integer shift, @org.jetbrains.annotations.Nullable()
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