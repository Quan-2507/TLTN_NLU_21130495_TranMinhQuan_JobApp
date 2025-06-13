import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:admin/widgets/job_chart.dart';
import 'package:admin/widgets/job_view_chart.dart';
import 'package:admin/widgets/receive_cv_chart.dart';
import '../apis/apis_list.dart';

var storage = const FlutterSecureStorage();

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  var data = {};
  List jobChartData = [];
  List rcvCvData = [];
  int openingJob = 0;
  List jobViewData = [];
  int noOfCV = 0;
  double overalPayment = 0;
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    getJobsChartData();
  }

  getJobsChartData() async {
    var loggedUser = await storage.read(key: 'user');
    int userId = jsonDecode(loggedUser!)['id'];
    var rs = await AdminChartApi.getChartData.sendRequest(urlParam: '/${userId.toString()}');
    data = rs;
    if (data.isNotEmpty) {
      setState(() {
        jobChartData = data['jobs'];
        rcvCvData = data['number_of_job_applicated'];
        jobViewData = data['number_of_job_viewed'];
        openingJob = data['opening_jobs'];
        noOfCV = data['total_applicated_by_month'];
        overalPayment = data['overall_payment'];
        isLoading = false;
      });
    }
  }

  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color.fromARGB(255, 39, 41, 45),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : Container(
              margin: const EdgeInsets.symmetric(horizontal: 8),
              child: Column(
                children: [
                  Row(
                    children: [
                      homedata('Opening Jobs', openingJob, Colors.deepPurple),
                      homedata('No. of CV', noOfCV, Colors.green)
                    ],
                  ),
                  const SizedBox(height: 20),
                  const Text(
                    'Your Posted Job Per Month',
                    style: TextStyle(
                        color: Colors.white70,
                        fontSize: 16,
                        fontWeight: FontWeight.bold),
                  ),
                  JobChart(
                    chartData: jobChartData,
                  ),
                  const SizedBox(height: 20),
                  const Text(
                    'Job View Per Month',
                    style: TextStyle(
                        color: Colors.white70,
                        fontSize: 16,
                        fontWeight: FontWeight.bold),
                  ),
                  JobViewChart(chartData: jobViewData),
                  const SizedBox(height: 20),
                  const Text(
                    'CV Received Per Month',
                    style: TextStyle(
                        color: Colors.white70,
                        fontSize: 16,
                        fontWeight: FontWeight.bold),
                  ),

                  ReceivedCVChart(chartData: rcvCvData,)
                ],
              )),
    );
  }

  Expanded homedata(String title, int num, Color color) {
    return Expanded(
      flex: 5,
      child: Container(
        height: 70,
        margin: const EdgeInsets.all(8),
        padding: const EdgeInsets.all(8),
        decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(10), color: color),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(title),
              Text(
                num.toString(),
                style: const TextStyle(
                    color: Colors.white70,
                    fontSize: 24,
                    fontWeight: FontWeight.bold),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
