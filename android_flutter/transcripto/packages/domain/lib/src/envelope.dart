class Envelope {
  final String method;
  final String saltBase64; // may be empty
  final String payload;
  const Envelope({required this.method, required this.saltBase64, required this.payload});

  @override
  String toString() => 'method=$method;salt=$saltBase64;payload=$payload';

  static Envelope? tryParse(String input) {
    // Expected: method=...;salt=...;payload=...
    if (!input.startsWith('method=')) return null;
    final parts = input.split(';');
    if (parts.length < 3) return null;
    final map = <String, String>{};
    for (final p in parts) {
      final idx = p.indexOf('=');
      if (idx <= 0) continue;
      final k = p.substring(0, idx);
      final v = p.substring(idx + 1);
      map[k] = v;
    }
    if (!map.containsKey('method') || !map.containsKey('salt') || !map.containsKey('payload')) {
      return null;
    }
    return Envelope(method: map['method']!, saltBase64: map['salt']!, payload: map['payload']!);
  }
}
