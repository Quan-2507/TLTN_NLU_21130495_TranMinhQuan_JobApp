import 'package:flutter/material.dart';
import 'package:dream_job/screens/auth/login_screen.dart';
import 'package:fluentui_system_icons/fluentui_system_icons.dart';

class LetsYouInScreen extends StatelessWidget {
  const LetsYouInScreen({super.key});

  Widget _buildSocialButton(String text, IconData icon, Color iconColor) {
    return Container(
      width: double.infinity,
      margin: const EdgeInsets.symmetric(vertical: 8),
      height: 56,
      decoration: BoxDecoration(
        border: Border.all(color: Colors.grey.shade300),
        borderRadius: BorderRadius.circular(16),
      ),
      child: InkWell(
        onTap: () {}, // Backend API will be attached here
        borderRadius: BorderRadius.circular(16),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(icon, color: iconColor),
            const SizedBox(width: 12),
            Text(
              text,
              style: const TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.w600,
              ),
            ),
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: Colors.black),
          onPressed: () => Navigator.pop(context),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          children: [
            Expanded(
              child: SingleChildScrollView(
                child: Column(
                  children: [
                    const SizedBox(height: 20),
                    // Illustration Placeholder
                    Image.network(
                      'https://cdn3d.iconscout.com/3d/premium/thumb/login-verified-5749216-4813589.png', // Abstract login illustration
                      height: 200,
                    ),
                    const SizedBox(height: 32),
                    const Text(
                      "Let's you in",
                      style: TextStyle(
                        fontSize: 32,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const SizedBox(height: 32),
                    _buildSocialButton("Continue with Facebook", Icons.facebook, Colors.blue),
                    _buildSocialButton("Continue with Google", FluentIcons.globe_24_regular, Colors.red),
                    _buildSocialButton("Continue with Apple", Icons.apple, Colors.black),
                    const SizedBox(height: 24),
                    const Row(
                      children: [
                        Expanded(child: Divider()),
                        Padding(
                          padding: EdgeInsets.symmetric(horizontal: 16),
                          child: Text("or", style: TextStyle(color: Colors.grey)),
                        ),
                        Expanded(child: Divider()),
                      ],
                    ),
                    const SizedBox(height: 24),
                    SizedBox(
                      width: double.infinity,
                      child: ElevatedButton(
                        onPressed: () {
                          Navigator.of(context).push(
                            MaterialPageRoute(builder: (_) => const LoginScreen(tabIndex: 1)),
                          );
                        },
                        child: const Text('Sign in with password'),
                      ),
                    ),
                  ],
                ),
              ),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Text("Don't have an account? ", style: TextStyle(color: Colors.grey)),
                GestureDetector(
                  onTap: () {
                    Navigator.of(context).push(
                      MaterialPageRoute(builder: (_) => const LoginScreen(tabIndex: 0)),
                    );
                  },
                  child: const Text(
                    "Sign up",
                    style: TextStyle(
                      color: Color(0xFF246BFD),
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
