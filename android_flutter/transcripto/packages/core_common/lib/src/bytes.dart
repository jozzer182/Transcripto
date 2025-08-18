import 'dart:math';

List<int> concatBytes(List<int> a, List<int> b) {
  final out = List<int>.from(a)..addAll(b);
  return out;
}

int simpleHashBytes(List<int> bytes) {
  // FNV-1a 32-bit variant in pure Dart
  const int fnvPrime = 0x01000193;
  const int fnvOffset = 0x811C9DC5;
  var hash = fnvOffset;
  for (final b in bytes) {
    hash ^= b & 0xff;
    hash = (hash * fnvPrime) & 0xFFFFFFFF;
  }
  return hash;
}

List<int> randomBytesSecure(int minLen, int maxLen) {
  assert(minLen > 0 && maxLen >= minLen);
  final rand = Random.secure();
  final len = minLen + rand.nextInt(maxLen - minLen + 1);
  return List<int>.generate(len, (_) => rand.nextInt(256));
}
