import 'package:enefty_icons/enefty_icons.dart';
import 'package:flutter/material.dart';
import 'package:flutter_toggle_tab/flutter_toggle_tab.dart';
import 'package:dream_job/features/login_form.dart';
import 'package:dream_job/screens/auth/register_form.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key, required this.tabIndex});

  final int tabIndex;

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  late ValueNotifier<int> _tabIndexBasicToggle;
  TextEditingController passController = TextEditingController();
  TextEditingController confirmPassController = TextEditingController();

  @override
  void initState() {
    super.initState();
    int indexScreen = widget.tabIndex;
    _tabIndexBasicToggle = ValueNotifier(indexScreen);
  }

  List<DataTab> get _listTextTabToggle => [DataTab(title: "Sign Up"),
    DataTab(title: "Sign In")];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: <Widget>[
          Image.asset(
            'assets/images/HD-wallpaper-abstract-blue-green-lime.png',
            width: double.infinity,
            height: double.infinity,
            fit: BoxFit.cover,
          ),
          Center(
            child: SingleChildScrollView(
              padding: const EdgeInsets.symmetric(vertical: 36, horizontal: 28),
              child: basicTabToggle(),
            ),
          ),
        ],
      ),
    );
  }

  Widget basicTabToggle() => Column(
    children: [
      const Padding(
        padding: EdgeInsets.only(bottom: 20),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(EneftyIcons.briefcase_bold, color: Colors.white),
            SizedBox(width: 6),
            Text(
              'DREAM JOB',
              style: TextStyle(
                color: Colors.white,
                fontWeight: FontWeight.bold,
                fontSize: 18,
              ),
            ),
          ],
        ),
      ),
      ValueListenableBuilder(
        valueListenable: _tabIndexBasicToggle,
        builder: (context, currentIndex, _) {
          return FlutterToggleTab(
            width: 60,
            borderRadius: 30,
            height: 45,
            selectedIndex: _tabIndexBasicToggle.value,
            marginSelected: const EdgeInsets.all(5.0),
            selectedBackgroundColors: const [
              Color(0xFF44903e),
              Color(0xFF37bd74),
            ],
            selectedTextStyle: const TextStyle(
              color: Colors.white,
              fontSize: 14,
              fontWeight: FontWeight.w700,
            ),
            unSelectedTextStyle: const TextStyle(
              color: Colors.black87,
              fontSize: 13,
            ),
            dataTabs  : _listTextTabToggle, // ✅ Sử dụng labels thay vì dataTabs
            selectedLabelIndex: (index) {
              _tabIndexBasicToggle.value = index;
            },
            isScroll: false,
          );
        },
      ),
      SizedBox(height: heightInPercent(context, 3)),
      ValueListenableBuilder(
        valueListenable: _tabIndexBasicToggle,
        builder: (context, currentIndex, _) {
          if (currentIndex == 0) {
            return const RegisterForm();
          } else {
            return const LoginForm();
          }
        },
      ),
    ],
  );

  /// Helper: Tính chiều cao theo phần trăm của màn hình
  double heightInPercent(BuildContext context, double percent) {
    return MediaQuery.of(context).size.height * percent / 100;
  }
}
