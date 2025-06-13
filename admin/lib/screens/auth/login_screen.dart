import 'dart:convert' show json, utf8;
import 'package:enefty_icons/enefty_icons.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:admin/screens/tabs_screen.dart';
import '../../apis/apis_list.dart';

const storage = FlutterSecureStorage();

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() {
    return _LoginScreenState();
  }
}

class _LoginScreenState extends State<LoginScreen> {
  final _formKeyLogin = GlobalKey<FormState>();
  TextEditingController passController = TextEditingController();
  TextEditingController confirmPassController = TextEditingController();

  var _enteredEmail = '';
  var _enteredPassword = '';
  bool _obscureText = true;

  @override
  void initState() {
    super.initState();
    loginState();
  }

  loginState() async {
    String? userToken = await storage.read(key: 'token');
    if(userToken != null && userToken != '') {
      Map<String, String> jsonBody = {
        'token': userToken
      };
      var data = await AuthApi.checkToken.sendRequest(body: jsonBody);
      if (data != null) {
        Navigator.pushAndRemoveUntil(context, MaterialPageRoute(builder: (context) => const TabsScreen(),), (route) => false);
      }else{
        await storage.deleteAll();
      }
    }else{
      await storage.deleteAll();
    }
  }

  void _toggle() {
    setState(() {
      _obscureText = !_obscureText;
    });
  }

  _submitLogin() async {
    final isValid = _formKeyLogin.currentState!.validate();
    if (!isValid) {
      return;
    }
    _formKeyLogin.currentState!.save();
    Map<String, String> jsonBody = {
      'email': _enteredEmail,
      'password': _enteredPassword
    };
    var result = await AuthApi.login.sendRequest(body: jsonBody);
    if(result != null){
      String accessToken = result['token'];
      await storage.write(key: 'token', value: accessToken);
      await storage.write(key: 'user', value: json.encode(result['account']));
      Navigator.pushAndRemoveUntil(context, MaterialPageRoute(builder: (context) => const TabsScreen(),), (route) => false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Stack(children: <Widget>[
          Image.asset(
            'assets/images/loginbg.png',
            width: double.infinity,
            height: double.infinity,
            fit: BoxFit.cover,
          ),
      Center(
          child: SingleChildScrollView(
              padding: const EdgeInsets.symmetric(vertical: 36, horizontal: 28),
              child: Column(
                children: [
                  const Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Icon(EneftyIcons.briefcase_bold, color: Colors.white,),
                      SizedBox(width: 6),
                      Text('DREAM JOB', style: TextStyle(
                          color: Colors.white,
                          fontWeight: FontWeight.bold,
                          fontSize: 18
                      ),),
                    ],
                  ),
                  const SizedBox(height: 32),
                  Form(
                    key: _formKeyLogin,
                    child: Column(
                      children: [
                        TextFormField(
                          decoration: InputDecoration(
                            border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(40.0),
                            ),
                            enabledBorder: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(25.0),
                              borderSide: const BorderSide(
                                color: Colors.black12,
                              ),
                            ),
                            focusedBorder: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(25.0),
                              borderSide: const BorderSide(
                                color: Colors.black12,
                              ),
                            ),
                            filled: true,
                            focusColor: Colors.white,
                            hintStyle: const TextStyle(color: Colors.white38),
                            hintText: "Email",
                            fillColor: Colors.black,
                            contentPadding:
                            const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
                          ),
                          style: const TextStyle(color: Colors.white70),
                          keyboardType: TextInputType.emailAddress,
                          autocorrect: false,
                          validator: (value) {
                            if (value == null ||
                                value.trim().isEmpty ||
                                !value.contains('@') ||
                                !value.contains('.')) {
                              return 'Invalid email!';
                            }
                            return null;
                          },
                          onSaved: (value) {
                            _enteredEmail = value!;
                          },
                        ),
                        const SizedBox(height: 12),
                        TextFormField(
                          decoration: InputDecoration(
                            border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(40.0),
                            ),
                            enabledBorder: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(25.0),
                              borderSide: const BorderSide(
                                color: Colors.black12,
                              ),
                            ),
                            focusedBorder: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(25.0),
                              borderSide: const BorderSide(
                                color: Colors.black12,
                              ),
                            ),
                            filled: true,
                            hintStyle: const TextStyle(color: Colors.white38),
                            hintText: "Password (*)",
                            fillColor: Colors.black,
                            suffixIcon: IconButton(
                                icon: Icon(
                                  _obscureText ? Icons.visibility_off : Icons.visibility,
                                  size: 18,
                                  color: Colors.grey[800],
                                ),
                                onPressed: _toggle),
                            contentPadding:
                            const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
                          ),
                          style: const TextStyle(color: Colors.white70),
                          obscureText: _obscureText,
                          validator: (value) {
                            if (value == null || value.trim().isEmpty) {
                              return 'Password cannot be empty';
                            }
                            return null;
                          },
                          onSaved: (value) {
                            _enteredPassword = value!;
                          },
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.end,
                          children: [
                            TextButton(
                              child: const Text('Forgot password'),
                              onPressed: () {},
                            ),
                          ],
                        ),
                        ElevatedButton(
                          onPressed: _submitLogin,
                          style: ElevatedButton.styleFrom(
                              backgroundColor: Colors.indigo,
                              foregroundColor: Colors.white,
                              fixedSize: const Size(150, 45)
                          ),
                          child: const Text(
                            'Sign in',
                            style: TextStyle(
                              fontWeight: FontWeight.bold,
                              fontSize: 15,
                            ),
                          ),
                        ),
                        const SizedBox(height: 18),
                      ],
                    ),
                  ),
                ],
              )
          ))
    ]));
  }
}
