abstract class CipherMethod {
  String encrypt(String input, CipherParams params);
  String decrypt(String input, CipherParams params);
}

enum CipherKind { base64, caesar, vigenere, xor }

enum CipherMode { encrypt, decrypt }

class CipherParams {
  final CipherKind method;
  final CipherMode mode;
  final String? key; // for vigenere/xor
  final int? shift; // for caesar
  final List<int>? salt; // optional
  final bool useSalt;
  const CipherParams({
    required this.method,
    required this.mode,
    this.key,
    this.shift,
    this.salt,
    this.useSalt = false,
  });

  CipherParams copyWith({
    CipherKind? method,
    CipherMode? mode,
    String? key,
    int? shift,
    List<int>? salt,
    bool? useSalt,
  }) => CipherParams(
        method: method ?? this.method,
        mode: mode ?? this.mode,
        key: key ?? this.key,
        shift: shift ?? this.shift,
        salt: salt ?? this.salt,
        useSalt: useSalt ?? this.useSalt,
      );
}
