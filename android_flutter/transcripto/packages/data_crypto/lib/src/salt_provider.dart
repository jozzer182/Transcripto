import 'dart:math';

abstract class SaltProvider {
  Future<List<int>> generate({int min = 8, int max = 16});
}

class RandomSaltProvider implements SaltProvider {
  @override
  Future<List<int>> generate({int min = 8, int max = 16}) async {
    assert(min > 0 && max >= min);
    final r = Random.secure();
    final len = min + r.nextInt(max - min + 1);
    return List<int>.generate(len, (_) => r.nextInt(256));
  }
}
