import 'package:enefty_icons/enefty_icons.dart';
import 'package:fluentui_system_icons/fluentui_system_icons.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:admin/screens/job_detail_screen.dart';
import 'package:admin/screens/pdf_view_screen.dart';

import '../apis/apis_list.dart';

var storage = const FlutterSecureStorage();

class MyCvScreen extends StatefulWidget {
  const MyCvScreen({super.key});

  @override
  State<MyCvScreen> createState() => _MyCvScreenState();
}

class _MyCvScreenState extends State<MyCvScreen> {
  List cvList = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    getJobsData(0,0);
  }

  getJobsData(int size, int page) async {
    var token = await storage.read(key: 'token');
    List rs = await AdminEmployerApi.getSaveCV.sendRequest(token: token, urlParam: '?size=$size&page=$page');
    setState(() {
      cvList = rs;
      isLoading = false;
    });
  }

  onGoBack(dynamic value) {
    getJobsData(0,0);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color.fromARGB(255, 39, 41, 45),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : (cvList.isEmpty
          ? const Center(
        child: Text('You still not save any CV', style: TextStyle(color: Colors.white70),),
      )
          :  Container(
          margin: const EdgeInsets.symmetric(horizontal: 14.0),
          child: SingleChildScrollView(
            child: Column(
              children: [
                ListView.builder(
                            physics: const ClampingScrollPhysics(),
                            shrinkWrap: true,
                            scrollDirection: Axis.vertical,
                            itemCount: cvList.length,
                            itemBuilder:
                                (BuildContext context, int index) =>
                                    Container(
                                      margin: const EdgeInsets.all(10),
                                      padding: const EdgeInsets.all(20),
                                      decoration: BoxDecoration(
                                          borderRadius: BorderRadius.circular(10),
                                          color: const Color.fromARGB(255, 32, 34, 37),
                                          boxShadow: [
                                            BoxShadow(
                                              color: Colors.black.withOpacity(0.4),
                                              spreadRadius: 0,
                                              blurRadius: 2,
                                            ),
                                          ]),
                                      child: Row(
                                        crossAxisAlignment: CrossAxisAlignment.start,
                                        children: [
                                          const Icon(EneftyIcons.document_favorite_outline,
                                              color: Colors.white70,
                                              size: 35),
                                          const SizedBox(width: 20),
                                          Expanded(
                                            child: Column(
                                              crossAxisAlignment: CrossAxisAlignment.start,
                                              children: [
                                                Text(cvList[index]['cv']['name'].toString().toUpperCase(),
                                                  style: const TextStyle(
                                                    fontWeight: FontWeight.bold,
                                                    color: Colors.white70,),
                                                ),
                                                Text(
                                                  'Applied date: ${cvList[index]['date_applied'].toString().substring(0, 10)}',
                                                  style: const TextStyle(fontSize: 12, color: Colors.white54),
                                                ),
                                                const SizedBox(height: 8,),
                                                SizedBox(
                                                  height: 35,
                                                  child: ElevatedButton.icon(
                                                      onPressed: (){
                                                        Navigator.of(context).push(MaterialPageRoute(builder: (ctx) => PDFViewScreen(cvId: cvList[index]['application_id'], isSave: true, path: cvList[index]['cv']['file_name']))).then(onGoBack);
                                                      },
                                                      style: ElevatedButton.styleFrom(
                                                          backgroundColor: Colors.blue,
                                                          foregroundColor: Colors.white
                                                      ),
                                                      icon: const Icon(EneftyIcons.eye_outline, size: 18), label: const Text('Preview')),
                                                )
                                              ],
                                            ),
                                          ),
                                        ],
                                      ),
                                    ),
                          ),
              ]),
            ),
          )),
    );
  }
}
