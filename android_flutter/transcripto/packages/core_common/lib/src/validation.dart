class ValidationError implements Exception {
  final String message;
  ValidationError(this.message);
  @override
  String toString() => message;
}

class Validators {
  static void requireNotEmpty(String value, String message) {
    if (value.trim().isEmpty) throw ValidationError(message);
  }

  static void requireNotNull<T>(T? value, String message) {
    if (value == null) throw ValidationError(message);
  }
}
