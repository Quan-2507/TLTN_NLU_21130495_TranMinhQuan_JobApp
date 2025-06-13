import 'package:enefty_icons/enefty_icons.dart';
import 'package:fluentui_system_icons/fluentui_system_icons.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:admin/screens/job_detail_screen.dart';

import '../apis/apis_list.dart';

var storage = const FlutterSecureStorage();

class MyJobsScreen extends StatefulWidget {
  const MyJobsScreen({super.key});

  @override
  State<MyJobsScreen> createState() => _MyJobsScreenState();
}

class _MyJobsScreenState extends State<MyJobsScreen> {
  var data = {};
  List jobData = [];
  bool isActive = false;
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    getJobsData();
  }

  getJobsData() async {
    var token = await storage.read(key: 'token');
    var rs = await AdminEmployerApi.getJobs.sendRequest(token: token);

    data = rs;
    if (data.isNotEmpty) {
      setState(() {
        jobData = data['companyForEmployer']['jobsOpening'];
        isLoading = false;
      });
    }
  }

  enableDisableJob(int id) async {
    await EmployerJobApi.enableDisbaleJob.sendRequest(urlParam: '/${id.toString()}');
    getJobsData();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color.fromARGB(255, 39, 41, 45),
      body: Container(
          margin: const EdgeInsets.symmetric(horizontal: 14.0),
          child: SingleChildScrollView(
            child: Column(
              children: [
                Container(
                    child: isLoading
                        ? const Center(child: CircularProgressIndicator())
                        : (jobData.isEmpty
                            ? Container(
                                height: 110,
                                alignment: Alignment.center,
                                child:
                                    const Text('You still not create any Job'))
                            : ListView.builder(
                                physics: const ClampingScrollPhysics(),
                                shrinkWrap: true,
                                scrollDirection: Axis.vertical,
                                itemCount: jobData.length,
                                itemBuilder:
                                    (BuildContext context, int index) =>
                                        InkWell(
                                  onTap: () {
                                    Navigator.of(context).push(MaterialPageRoute(builder: (ctx) => JobDetailScreen(jobId: jobData[index]['id'])));
                                  },
                                  child: Container(
                                    padding: const EdgeInsets.all(14),
                                    decoration: BoxDecoration(
                                        borderRadius: BorderRadius.circular(16),
                                        color: const Color.fromARGB(
                                            255, 47, 49, 54),
                                        boxShadow: const [
                                          BoxShadow(
                                            color: Colors.black12,
                                            spreadRadius: 0,
                                            blurRadius: 2,
                                          ),
                                        ]),
                                    child: Column(
                                      crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                      mainAxisSize: MainAxisSize.min,
                                      children: [
                                        Text(
                                          jobData[index]['title'],
                                          style: const TextStyle(
                                              fontWeight: FontWeight.bold,
                                              color: Colors.indigoAccent),
                                        ),
                                        Text(
                                          'Amount: ${jobData[index]['amount'].toString()}',
                                          style: const TextStyle(
                                              color: Colors.white70),
                                        ),
                                        Row(
                                          crossAxisAlignment:
                                              CrossAxisAlignment.center,
                                          children: [
                                            Expanded(
                                              child: Column(
                                                crossAxisAlignment:
                                                    CrossAxisAlignment.start,
                                                children: [
                                                  Text(
                                                    'Start date: ${jobData[index]['start_date'].toString().substring(0, 10)}',
                                                    style: const TextStyle(
                                                        fontSize: 12,
                                                        color: Colors.white54),
                                                  ),
                                                  Text(
                                                    'End date: ${jobData[index]['end_date'].toString().substring(0, 10)}',
                                                    style: const TextStyle(
                                                        fontSize: 12,
                                                        color: Colors.white54),
                                                  ),
                                                ],
                                              ),
                                            ),
                                            InkWell(
                                                onTap: () async {
                                                  enableDisableJob(
                                                      jobData[index]['id']);
                                                },
                                                child: Icon(
                                                    jobData[index]['_active']
                                                        ? FluentIcons
                                                            .toggle_right_24_filled
                                                        : FluentIcons
                                                            .toggle_left_24_regular,
                                                    size: 40,
                                                    color: jobData[index]
                                                            ['_active']
                                                        ? Colors.indigoAccent
                                                        : const Color.fromARGB(
                                                            255, 32, 34, 37)))
                                          ],
                                        ),
                                      ],
                                    ),
                                  ),
                                ),
                              ))),
              ],
            ),
          )),
    );
  }
}
