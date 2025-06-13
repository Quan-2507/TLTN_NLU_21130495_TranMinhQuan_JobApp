import 'package:enefty_icons/enefty_icons.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:admin/main.dart';
import 'package:admin/screens/auth/login_screen.dart';
import 'package:admin/screens/home_screen.dart';
import 'package:admin/screens/my_cv_screen.dart';
import 'package:admin/screens/my_jobs_screen.dart';
import 'package:admin/screens/profile_screen.dart';

var storage = const FlutterSecureStorage();

class TabsScreen extends StatefulWidget {
  const TabsScreen({super.key});

  @override
  State<TabsScreen> createState() {
    return _TabsScreenState();
  }
}

class _TabsScreenState extends State<TabsScreen> {
  int _selectedIndex = 0;
  NavigationRailLabelType labelType = NavigationRailLabelType.all;
  double groupAlignment = -1.0;
  Color bgColor = const Color.fromARGB(255, 39, 41, 45);

  void selectPage(int index) async {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    Widget activePage = const HomeScreen();
    String titlePate = 'Home';

    switch (_selectedIndex) {
      case 0:
        activePage = const HomeScreen();
        titlePate = 'Home';
        break;
      case 1:
        activePage = const MyJobsScreen();
        titlePate = 'My Jobs';
        break;
      case 2:
        activePage = const MyCvScreen();
        titlePate = 'My CV';
        break;
      case 3:
        activePage = const ProfileScreen();
        titlePate = 'Profile';
        break;
    }

    return Row(
      children: <Widget>[
        NavigationRail(
          selectedIndex: _selectedIndex,
          groupAlignment: groupAlignment,
          backgroundColor: const Color.fromARGB(255, 45, 48, 54),
          onDestinationSelected: selectPage,
          labelType: labelType,
          indicatorColor: Colors.transparent,
          selectedIconTheme: const IconThemeData(color: Colors.indigoAccent),
          unselectedIconTheme: const IconThemeData(color: Colors.white),
          minWidth: 30,
          destinations: const [
            NavigationRailDestination(
              icon: Icon(EneftyIcons.chart_2_outline),
              selectedIcon: Icon(EneftyIcons.chart_2_bold),
              label: Text('Home', style: TextStyle(color: Colors.white)),
            ),
            NavigationRailDestination(
              icon: Icon(EneftyIcons.star_outline),
              selectedIcon: Icon(EneftyIcons.star_bold),
              label: Text('My Jobs', style: TextStyle(color: Colors.white)),
            ),
            NavigationRailDestination(
              icon: Icon(EneftyIcons.document_text_outline),
              selectedIcon: Icon(EneftyIcons.document_text_bold),
              label: Text('My CV', style: TextStyle(color: Colors.white)),
            ),
            NavigationRailDestination(
              icon: Icon(EneftyIcons.user_outline),
              selectedIcon: Icon(EneftyIcons.user_bold),
              label: Text('Profile', style: TextStyle(color: Colors.white)),
            ),
          ],
         trailing: Expanded(
           child: Column(
             mainAxisAlignment: MainAxisAlignment.end,
             children: [
               IconButton(
                 onPressed: () async{
                   showDialog(
                     context: context,
                     builder: (context) {
                       return AlertDialog(
                         title: const Text('You are signing out?',style: TextStyle(color: Colors.white)),
                         content: const SingleChildScrollView(
                           child: ListBody(
                             children: <Widget>[
                               Text('You are about to sign out our app', style: TextStyle(color: Colors.white70),),
                               Text('Would you please confirm?', style: TextStyle(color: Colors.white70)),
                             ],
                           ),
                         ),
                         backgroundColor: const Color.fromARGB(255, 45, 48, 54),
                         elevation: 0,
                         actions: <Widget>[
                           TextButton(
                             child: const Text('Cancel', style: TextStyle(color: Colors.white70)),
                             onPressed: () {
                               Navigator.of(context).pop();
                             },
                           ),
                           ElevatedButton(
                             style: ElevatedButton.styleFrom(
                                 backgroundColor: Colors.indigo,
                                 foregroundColor: Colors.white,
                              ),
                             onPressed: () async {
                               await storage.deleteAll();
                               Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => const LoginScreen(),));
                             },
                             child: const Text('Signout'),
                           ),

                         ],
                       );
                     },
                   );
                 },
                 icon: const Column(
                   children: [
                     Icon(EneftyIcons.logout_2_outline, color: Colors.white),
                     Text("Signout", style: TextStyle(color: Colors.white, fontSize: 12),)
                   ],
                 ),
               ),
             ],
           ),
         ),
        ),
        // This is the main content.
        Expanded(
          child: Scaffold(
            appBar: AppBar(
              centerTitle: true,
              backgroundColor: bgColor,
              title: Row(
                mainAxisAlignment: MainAxisAlignment.end,
                children: [
                  Text(titlePate, style: const TextStyle(color: Colors.white, fontWeight: FontWeight.bold),),
                ],
              ),
            ),
            body: Center(
              child: Container(
                width: double.infinity,
                color: bgColor,
                child: activePage
              ),
            ),
          ),
        ),
      ],
    );
  }
}
