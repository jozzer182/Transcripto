import 'package:dynamic_color/dynamic_color.dart';
import 'package:flutter/material.dart';

ThemeData buildTheme(Brightness brightness, {ColorScheme? dynamicScheme}) {
  final base = ThemeData(
    useMaterial3: true,
    brightness: brightness,
    colorScheme: dynamicScheme ?? ColorScheme.fromSeed(seedColor: const Color(0xFF6750A4), brightness: brightness),
    textTheme: Typography.material2021().black,
    visualDensity: VisualDensity.standard,
  );
  return base.copyWith(
    inputDecorationTheme: const InputDecorationTheme(
      border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(16))),
    ),
    cardTheme: const CardTheme(
      elevation: 1,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.all(Radius.circular(24))),
      margin: EdgeInsets.all(16),
    ),
  );
}

class DynamicTheme extends StatelessWidget {
  final Widget child;
  const DynamicTheme({super.key, required this.child});

  @override
  Widget build(BuildContext context) {
    return DynamicColorBuilder(
      builder: (light, dark) {
        return MaterialApp(
          debugShowCheckedModeBanner: false,
          theme: buildTheme(Brightness.light, dynamicScheme: light),
          darkTheme: buildTheme(Brightness.dark, dynamicScheme: dark),
          themeMode: ThemeMode.system,
          home: child,
        );
      },
    );
  }
}
