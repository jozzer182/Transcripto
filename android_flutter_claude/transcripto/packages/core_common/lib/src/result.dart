/// Result type for operation outcomes
sealed class Result<T> {
  const Result();
}

/// Success result containing a value
final class Success<T> extends Result<T> {
  const Success(this.value);
  
  final T value;
  
  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is Success<T> && other.value == value;
  }
  
  @override
  int get hashCode => value.hashCode;
  
  @override
  String toString() => 'Success($value)';
}

/// Failure result containing an error
final class Failure<T> extends Result<T> {
  const Failure(this.error, [this.stackTrace]);
  
  final Object error;
  final StackTrace? stackTrace;
  
  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is Failure<T> && 
           other.error == error && 
           other.stackTrace == stackTrace;
  }
  
  @override
  int get hashCode => Object.hash(error, stackTrace);
  
  @override
  String toString() => 'Failure($error)';
}

/// Extension methods for Result
extension ResultExtension<T> on Result<T> {
  /// Returns true if this is a Success
  bool get isSuccess => this is Success<T>;
  
  /// Returns true if this is a Failure
  bool get isFailure => this is Failure<T>;
  
  /// Returns the value if Success, null if Failure
  T? get valueOrNull => switch (this) {
    Success(:final value) => value,
    Failure() => null,
  };
  
  /// Returns the error if Failure, null if Success
  Object? get errorOrNull => switch (this) {
    Success() => null,
    Failure(:final error) => error,
  };
  
  /// Maps the value if Success, returns Failure unchanged
  Result<U> map<U>(U Function(T value) mapper) => switch (this) {
    Success(:final value) => Success(mapper(value)),
    Failure(:final error, :final stackTrace) => Failure(error, stackTrace),
  };
  
  /// Flat maps the value if Success
  Result<U> flatMap<U>(Result<U> Function(T value) mapper) => switch (this) {
    Success(:final value) => mapper(value),
    Failure(:final error, :final stackTrace) => Failure(error, stackTrace),
  };
  
  /// Returns the value if Success, or the default value if Failure
  T getOrElse(T defaultValue) => switch (this) {
    Success(:final value) => value,
    Failure() => defaultValue,
  };
  
  /// Executes the appropriate callback based on the result type
  U fold<U>(
    U Function(T value) onSuccess,
    U Function(Object error) onFailure,
  ) => switch (this) {
    Success(:final value) => onSuccess(value),
    Failure(:final error) => onFailure(error),
  };
}
