import 'package:flutter/material.dart';
import 'package:dream_job/apis/apis_list.dart';
import 'package:dream_job/screens/auth/login_screen.dart';

class RegisterForm extends StatefulWidget {
  const RegisterForm({super.key});

  @override
  State<RegisterForm> createState() {
    return _RegisterFormState();
  }
}

class _RegisterFormState extends State<RegisterForm> {
  final _formKeyRegister = GlobalKey<FormState>();
  TextEditingController passController = TextEditingController();
  TextEditingController confirmPassController = TextEditingController();
  bool _obscureText1 = true;
  bool _obscureText2 = true;

  @override
  void initState() {
    super.initState();
  }

  bool termCheck = false;

  void _termCheck(bool newValue) => setState(() {
        termCheck = newValue;
      });

  void _toggle1() {
    setState(() {
      _obscureText1 = !_obscureText1;
    });
  }

  void _toggle2() {
    setState(() {
      _obscureText2 = !_obscureText2;
    });
  }

  var _enteredNameRegister = '';
  var _enteredEmailRegister = '';
  var _enteredPasswordRegister = '';

  _submitRegister() async {
    print('ahihi');
    final isValid = _formKeyRegister.currentState!.validate();

    if (!isValid || termCheck == false) {
      return;
    }

    _formKeyRegister.currentState!.save();

    Map<String, String> jsonBody = {
      'email': _enteredEmailRegister,
      'password': _enteredPasswordRegister,
      'full_name': _enteredNameRegister,
    };
    var result = await AuthApi.register.sendRequest(body: jsonBody);
    if(result != null){
      showDialog(
                  context: context,
                  builder: (BuildContext dialogContext) {
                    return AlertDialog(
                      elevation: 0,
                      backgroundColor: Colors.white,
                      title: const Text('Success'),
                      content: const Text("Register successfully\nPlease login to continue"),
                      actions: <Widget>[
                        ElevatedButton(
                          onPressed: () async {
                            Navigator.of(context, rootNavigator: true).pop();
                            Navigator.of(context).pushReplacement(MaterialPageRoute(builder: (ctx) => const LoginScreen(tabIndex: 1,)));
                          },
                          style: ElevatedButton.styleFrom(
                            backgroundColor: Color(0xFF44903e),
                            foregroundColor: Colors.white
                          ),
                          child: const Text('Login'),
                        ),
                      ],
                    );
                  });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Form(
      key: _formKeyRegister,
      child: Column(
        children: [
          TextFormField(
            decoration: InputDecoration(
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(40.0),
              ),
              filled: true,
              hintStyle: TextStyle(color: Colors.grey[800]),
              hintText: "Full Name",
              fillColor: Colors.white,
              contentPadding:
                  const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
            ),
            onSaved: (value) {
              _enteredNameRegister = value!;
            },
          ),
          const SizedBox(height: 12),
          TextFormField(
            decoration: InputDecoration(
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(40.0),
              ),
              filled: true,
              hintStyle: TextStyle(color: Colors.grey[800]),
              hintText: "Email",
              fillColor: Colors.white,
              errorStyle: const TextStyle(color: Colors.black87),
              contentPadding:
                  const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
            ),
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
              _enteredEmailRegister = value!;
            },
          ),
          const SizedBox(height: 12),
          TextFormField(
            decoration: InputDecoration(
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(40.0),
              ),
              filled: true,
              hintStyle: TextStyle(color: Colors.grey[800]),
              hintText: "Password (*)",
              fillColor: Colors.white,
              errorStyle: const TextStyle(color: Colors.black87),
              contentPadding:
                  const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
              suffixIcon: IconButton(
                  icon: Icon(
                    _obscureText1 ? Icons.visibility_off : Icons.visibility,
                    size: 18,
                    color: Colors.grey[800],
                  ),
                  onPressed: _toggle1),
            ),
            obscureText: _obscureText1,
            controller: passController,
            validator: (value) {
              if (value == null || value.trim().isEmpty) {
                return 'Password cannot be empty';
              } else if (value.length < 6) {
                return 'Passwords must be at least 8 characters';
              }
              return null;
            },
            onSaved: (value) {
              _enteredPasswordRegister = value!;
            },
          ),
          const SizedBox(height: 12),
          TextFormField(
            decoration: InputDecoration(
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(40.0),
              ),
              filled: true,
              hintStyle: TextStyle(color: Colors.grey[800]),
              hintText: "Confirm password (*)",
              fillColor: Colors.white,
              errorStyle: const TextStyle(color: Colors.black87),
              contentPadding:
                  const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
              suffixIcon: IconButton(
                  icon: Icon(
                    _obscureText2 ? Icons.visibility_off : Icons.visibility,
                    size: 18,
                    color: Colors.grey[800],
                  ),
                  onPressed: _toggle2),
            ),
            obscureText: _obscureText2,
            controller: confirmPassController,
            validator: (value) {
              if (value == null || value.trim().isEmpty) {
                return 'Password cannot be empty';
              } else if (passController.text != confirmPassController.text) {
                return 'Password is not matched';
              }
              return null;
            },
          ),
          const SizedBox(height: 12),
          Row(
            children: [
              Checkbox(
                checkColor: Colors.white,
                value: termCheck,
                onChanged: (bool? value) {
                  _termCheck(value!);
                },
              ),
              const Text(
                'Accept terms and conditions',
              ), //Text
            ],
          ),
          ElevatedButton(
            style: ElevatedButton.styleFrom(
                backgroundColor: Color(0xFF44903e), foregroundColor: Colors.white),
            onPressed: termCheck == true ? _submitRegister : null,
            child: const Text(
              'Sign up',
              style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 15,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
