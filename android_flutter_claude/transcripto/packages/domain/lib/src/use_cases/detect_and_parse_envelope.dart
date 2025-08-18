import 'package:core_common/core_common.dart';
import '../entities/envelope.dart';
import '../entities/cipher_params.dart';

/// Use case for detecting and parsing envelope format
class DetectAndParseEnvelope {
  const DetectAndParseEnvelope();
  
  /// Detects if input is in envelope format and parses it
  Result<EnvelopeParseResult> call(String input) {
    try {
      if (input.trim().isEmpty) {
        return const Failure(ValidationException('El texto de entrada no puede estar vacío'));
      }
      
      // Check if it's envelope format
      if (!Envelope.isEnvelopeFormat(input)) {
        return const Success(EnvelopeParseResult(isEnvelope: false));
      }
      
      // Parse envelope
      final envelope = Envelope.parse(input);
      if (envelope == null) {
        return const Failure(EnvelopeParseException('Formato de envelope inválido'));
      }
      
      // Create cipher params from envelope
      final params = CipherParams(
        method: envelope.method,
        mode: CipherMode.decrypt,
        salt: envelope.salt,
        useSalt: envelope.salt != null,
      );
      
      return Success(EnvelopeParseResult(
        isEnvelope: true,
        envelope: envelope,
        suggestedParams: params,
      ));
    } catch (e, stackTrace) {
      return Failure(EnvelopeParseException('Error al parsear envelope: $e'), stackTrace);
    }
  }
}

/// Result of envelope parsing operation
class EnvelopeParseResult {
  const EnvelopeParseResult({
    required this.isEnvelope,
    this.envelope,
    this.suggestedParams,
  });
  
  final bool isEnvelope;
  final Envelope? envelope;
  final CipherParams? suggestedParams;
  
  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is EnvelopeParseResult &&
           other.isEnvelope == isEnvelope &&
           other.envelope == envelope &&
           other.suggestedParams == suggestedParams;
  }
  
  @override
  int get hashCode {
    return Object.hash(isEnvelope, envelope, suggestedParams);
  }
  
  @override
  String toString() {
    return 'EnvelopeParseResult('
           'isEnvelope: $isEnvelope, '
           'envelope: $envelope, '
           'suggestedParams: $suggestedParams'
           ')';
  }
}
