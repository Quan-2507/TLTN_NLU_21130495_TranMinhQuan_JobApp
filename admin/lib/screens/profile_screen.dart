import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:intl/intl.dart';
import '../apis/apis_list.dart';

var storage = const FlutterSecureStorage();

class ProfileScreen extends StatefulWidget {
  const ProfileScreen({super.key});

  @override
  State<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  var data = {};
  String companyName = '';
  String companyLogo = '';
  String combo = '';
  String comboStart = '';
  String comboEnd = '';
  String introduction = '';
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    getProfileData();
  }

  getProfileData() async {
    var loggedUser = await storage.read(key: 'user');
    int userId = jsonDecode(loggedUser!)['id'];
    var rs = await AdminChartApi.getProfile.sendRequest(urlParam: '/${userId.toString()}');
    if (rs != null && rs['companyForEmployer'] != null) {
      data = rs['companyForEmployer'];
      if (data.isNotEmpty) {
        setState(() {
          companyName = data['name'] ?? '';
          companyLogo = data['logo_image'] ?? '';
          combo = data['subcriptionPlan']?['name'] ?? 'No Plan';
          comboStart = data['subcriptionPlan']?['start_date'] ?? '';
          comboEnd = data['subcriptionPlan']?['end_date'] ?? '';
          introduction = data['introduction'] ?? '';
          isLoading = false;

          // Debug log
          print("Combo start: $comboStart");
          print("Combo end: $comboEnd");
        });
      }
    } else {
      setState(() {
        isLoading = false;
      });
    }
  }

  String formatDate(String dateStr) {
    try {
      if (dateStr.isEmpty) return "N/A";
      return DateFormat("dd/MM/yyyy").format(DateTime.parse(dateStr));
    } catch (e) {
      print("Invalid date format: $dateStr");
      return "Invalid date";
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color.fromARGB(255, 39, 41, 45),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : SingleChildScrollView(
        child: Container(
          margin: const EdgeInsets.only(right: 14.0, left: 14.0, bottom: 14.0),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              CircleAvatar(
                backgroundImage: NetworkImage(companyLogo),
                backgroundColor: Colors.white,
                radius: 38,
              ),
              const SizedBox(height: 12),
              Text(
                companyName,
                textAlign: TextAlign.center,
                style: const TextStyle(
                  color: Colors.white70,
                  fontWeight: FontWeight.bold,
                ),
              ),
              Container(
                width: double.infinity,
                margin: const EdgeInsets.only(top: 10),
                padding: const EdgeInsets.all(12.0),
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(10),
                  color: Colors.indigoAccent,
                  boxShadow: const [
                    BoxShadow(
                      color: Colors.black12,
                      spreadRadius: 0,
                      blurRadius: 4,
                    ),
                  ],
                ),
                child: Column(
                  children: [
                    Text(
                      combo,
                      style: const TextStyle(
                          fontWeight: FontWeight.bold, color: Colors.white),
                    ),
                    Text(
                      'From: ${formatDate(comboStart)} - To: ${formatDate(comboEnd)}',
                      style: const TextStyle(fontSize: 12, color: Colors.white70),
                    ),
                  ],
                ),
              ),
              Container(
                width: double.infinity,
                margin: const EdgeInsets.only(top: 10),
                padding: const EdgeInsets.all(12.0),
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(10),
                  color: const Color.fromARGB(255, 32, 34, 37),
                  boxShadow: const [
                    BoxShadow(
                      color: Colors.black12,
                      spreadRadius: 0,
                      blurRadius: 4,
                    ),
                  ],
                ),
                child: Text(
                  introduction,
                  style: const TextStyle(
                      fontWeight: FontWeight.bold, color: Colors.white54),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
