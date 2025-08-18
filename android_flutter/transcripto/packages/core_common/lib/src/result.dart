/// Lightweight Result/Either type
sealed class Result<T> {
  const Result();
  bool get isSuccess => this is Success<T>;
  bool get isFailure => this is Failure<T>;
  R fold<R>(R Function(T) onSuccess, R Function(Object error, StackTrace? st) onFailure) {
    final self = this;
    if (self is Success<T>) return onSuccess(self.value);
    if (self is Failure<T>) return onFailure(self.error, self.stackTrace);
    throw StateError('Unknown Result subtype');
  }
}

class Success<T> extends Result<T> {
  final T value;
  const Success(this.value);
}

class Failure<T> extends Result<T> {
  final Object error;
  final StackTrace? stackTrace;
  const Failure(this.error, [this.stackTrace]);
}
