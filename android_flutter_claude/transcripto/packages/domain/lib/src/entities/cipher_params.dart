import 'cipher_method.dart';

/// Operating mode for cipher operations
enum CipherMode {
  encrypt('Cifrar'),
  decrypt('Descifrar');
  
  const CipherMode(this.displayName);
  
  final String displayName;
}

/// Parameters for cipher operations
class CipherParams {
  const CipherParams({
    required this.method,
    required this.mode,
    this.key,
    this.shift,
    this.salt,
    this.useSalt = false,
  });
  
  final CipherMethod method;
  final CipherMode mode;
  final String? key;
  final int? shift;
  final List<int>? salt;
  final bool useSalt;
  
  /// Creates a copy with modified parameters
  CipherParams copyWith({
    CipherMethod? method,
    CipherMode? mode,
    String? key,
    int? shift,
    List<int>? salt,
    bool? useSalt,
  }) {
    return CipherParams(
      method: method ?? this.method,
      mode: mode ?? this.mode,
      key: key ?? this.key,
      shift: shift ?? this.shift,
      salt: salt ?? this.salt,
      useSalt: useSalt ?? this.useSalt,
    );
  }
  
  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is CipherParams &&
           other.method == method &&
           other.mode == mode &&
           other.key == key &&
           other.shift == shift &&
           _listEquals(other.salt, salt) &&
           other.useSalt == useSalt;
  }
  
  @override
  int get hashCode {
    return Object.hash(
      method,
      mode,
      key,
      shift,
      salt?.fold<int>(0, (prev, element) => prev ^ element.hashCode) ?? 0,
      useSalt,
    );
  }
  
  @override
  String toString() {
    return 'CipherParams('
           'method: $method, '
           'mode: $mode, '
           'key: $key, '
           'shift: $shift, '
           'salt: $salt, '
           'useSalt: $useSalt'
           ')';
  }
}

bool _listEquals<T>(List<T>? a, List<T>? b) {
  if (identical(a, b)) return true;
  if (a == null || b == null) return false;
  if (a.length != b.length) return false;
  for (int i = 0; i < a.length; i++) {
    if (a[i] != b[i]) return false;
  }
  return true;
}
