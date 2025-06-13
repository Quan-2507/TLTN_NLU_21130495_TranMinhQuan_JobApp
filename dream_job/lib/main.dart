import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:one_context/one_context.dart';
import 'package:dream_job/screens/tabs_screen.dart';


final theme = ThemeData(
  useMaterial3: true,
  colorScheme: ColorScheme.fromSeed(
    seedColor: Color(0xFF44903e),
  ),
  fontFamily: GoogleFonts.inter().fontFamily,
  textTheme: GoogleFonts.interTextTheme(),
);

void main() {
  runApp(const App());
}

class App extends StatelessWidget {
  const App({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: theme,
      builder: OneContext().builder,
      navigatorKey: OneContext().key,
      home: const TabsScreen(),
    );
  }
}