/// Result of a cipher operation
class CipherResult {
  const CipherResult({
    required this.output,
    this.isSuccess = true,
    this.errorMessage,
  });
  
  final String output;
  final bool isSuccess;
  final String? errorMessage;
  
  /// Creates a successful result
  factory CipherResult.success(String output) {
    return CipherResult(output: output);
  }
  
  /// Creates a failed result
  factory CipherResult.failure(String errorMessage) {
    return CipherResult(
      output: '',
      isSuccess: false,
      errorMessage: errorMessage,
    );
  }
  
  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is CipherResult &&
           other.output == output &&
           other.isSuccess == isSuccess &&
           other.errorMessage == errorMessage;
  }
  
  @override
  int get hashCode {
    return Object.hash(output, isSuccess, errorMessage);
  }
  
  @override
  String toString() {
    return 'CipherResult('
           'output: $output, '
           'isSuccess: $isSuccess, '
           'errorMessage: $errorMessage'
           ')';
  }
}
