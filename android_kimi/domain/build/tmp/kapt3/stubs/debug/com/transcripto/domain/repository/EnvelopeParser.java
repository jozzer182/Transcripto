package com.transcripto.domain.repository;

/**
 * Interface for parsing envelope format strings.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J$\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0006\u001a\u00020\u0003H&J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H&J\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\u0003H&\u00a8\u0006\r"}, d2 = {"Lcom/transcripto/domain/repository/EnvelopeParser;", "", "createEnvelope", "", "method", "salt", "payload", "isEnvelopeFormat", "", "input", "parseEnvelope", "Lcom/transcripto/domain/model/EnvelopeData;", "envelope", "domain_debug"})
public abstract interface EnvelopeParser {
    
    /**
     * Parses an envelope string into its components.
     * @param envelope The envelope string to parse
     * @return EnvelopeData if parsing is successful, null otherwise
     */
    @org.jetbrains.annotations.Nullable()
    public abstract com.transcripto.domain.model.EnvelopeData parseEnvelope(@org.jetbrains.annotations.NotNull()
    java.lang.String envelope);
    
    /**
     * Creates an envelope string from the given components.
     * @param method The cipher method used
     * @param salt The salt used (optional)
     * @param payload The encrypted payload
     * @return The formatted envelope string
     */
    @org.jetbrains.annotations.NotNull()
    public abstract java.lang.String createEnvelope(@org.jetbrains.annotations.NotNull()
    java.lang.String method, @org.jetbrains.annotations.Nullable()
    java.lang.String salt, @org.jetbrains.annotations.NotNull()
    java.lang.String payload);
    
    /**
     * Checks if a string appears to be an envelope format.
     * @param input The string to check
     * @return true if the string matches envelope format
     */
    public abstract boolean isEnvelopeFormat(@org.jetbrains.annotations.NotNull()
    java.lang.String input);
    
    /**
     * Interface for parsing envelope format strings.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}