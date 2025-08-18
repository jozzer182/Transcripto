import 'dart:convert';

import 'package:domain/domain.dart';

class Base64CodecImpl implements CipherMethod {
  @override
  String decrypt(String input, CipherParams params) {
    final bytes = base64Decode(input);
    var text = utf8.decode(bytes);
    if (params.useSalt && params.salt != null) {
      final saltStr = utf8.decode(params.salt!);
      final prefix = '$saltStr:';
      if (text.startsWith(prefix)) {
        text = text.substring(prefix.length);
      }
    }
    return text;
  }

  @override
  String encrypt(String input, CipherParams params) {
    // Optional salt: prepend salt:text before base64 if useSalt true per spec
    var text = input;
    if (params.useSalt && params.salt != null) {
      final saltStr = utf8.decode(params.salt!);
      text = '$saltStr:$text';
    }
    final bytes = utf8.encode(text);
    return base64Encode(bytes);
  }
}
