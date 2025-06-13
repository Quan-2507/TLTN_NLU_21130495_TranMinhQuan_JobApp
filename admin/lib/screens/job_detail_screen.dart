import 'package:enefty_icons/enefty_icons.dart';
import 'package:fluentui_system_icons/fluentui_system_icons.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:admin/screens/pdf_view_screen.dart';
import '../apis/apis_list.dart';

var storage = const FlutterSecureStorage();

class JobDetailScreen extends StatefulWidget {
  const JobDetailScreen({super.key, required this.jobId});

  final int jobId;

  @override
  State<JobDetailScreen> createState() => _JobDetailScreenState();
}

class _JobDetailScreenState extends State<JobDetailScreen> {
  var jobDetail = {};
  var cvList = [];
  int jobId = 0;
  String jobTitle = '';
  int amount = 0;
  String expRequired = '';
  String salary = '';
  String startDate = '';
  String endDate = '';
  String jobDescription = '';
  String jobResponsibility = '';
  String jobRequired = '';
  String jobBenefit = '';
  String createdDate = '';
  Set skills = {};
  Set industries = {};
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    getJobData(widget.jobId);
  }

  getJobData(int id) async {
    var token = await storage.read(key: 'token');
    var rs = await JobApi.getJobDetail.sendRequest(token: token, urlParam: '/${id.toString()}');
    jobDetail = rs;
    if (jobDetail.isNotEmpty) {
      var data = await EmployerJobApi.getCvByJob.sendRequest(urlParam: '?job_id=${id.toString()}&size=0&page=0');
      setState(() {
        cvList = data.map((e) => e).toList();
        jobId = jobDetail['id'] ?? 0;
        jobTitle = jobDetail['title'] ?? '';
        amount = jobDetail['amount'] ?? 0;
        expRequired = jobDetail['experience_required'] ?? '';
        salary = (jobDetail['salary_min'] != null
                ? jobDetail['salary_min'] + ' - '
                : '') +
            (jobDetail['salary_max'] ?? '');
        startDate = jobDetail['start_date'];
        endDate = jobDetail['end_date'];
        jobDescription = jobDetail['description'] ?? '';
        jobResponsibility = jobDetail['reponsibility'] ?? '';
        jobRequired = jobDetail['skill_required'] ?? '';
        jobBenefit = jobDetail['benefit'] ?? '';
        for (var i in jobDetail['company']['skills']) {
          skills.add(i['name']);
          industries.add(i['industry']['name']);
        }
        isLoading = false;
      });
    }
  }

  onGoBack(dynamic value) {
    getJobData(widget.jobId);
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
      appBar: AppBar(
        title: const Text(
          'Job Detail',
          style: TextStyle(color: Colors.white70),
        ),
        iconTheme: const IconThemeData(
          color: Colors.white70,
        ),
        backgroundColor: const Color.fromARGB(255, 45, 48, 54),
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : SingleChildScrollView(
              child: Container(
                margin: const EdgeInsets.all(14.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Container(
                      decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(12),
                          color: const Color.fromARGB(255, 45, 48, 54),
                          boxShadow: const [
                            BoxShadow(
                              color: Colors.black12,
                              spreadRadius: 0,
                              blurRadius: 2,
                              // offset: const Offset(0, 1),
                            ),
                          ]),
                      child: SizedBox(
                        width: double.infinity,
                        child: Padding(
                          padding: const EdgeInsets.all(16.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: [
                              Text(
                                jobTitle.toUpperCase(),
                                textAlign: TextAlign.center,
                                style: const TextStyle(
                                    fontSize: 17,
                                    fontWeight: FontWeight.bold,
                                    color: Colors.white70),
                              ),
                              const SizedBox(height: 8),
                              Wrap(
                                spacing: 4,
                                runSpacing: 4,
                                children: [
                                  for (var item in skills)
                                    Container(
                                      padding: const EdgeInsets.symmetric(
                                          horizontal: 6, vertical: 4),
                                      decoration: BoxDecoration(
                                        borderRadius: BorderRadius.circular(4),
                                        color: Colors.grey.withOpacity(0.5),
                                      ),
                                      child: Text(
                                        item,
                                        style: const TextStyle(
                                            color: Colors.white, fontSize: 12),
                                      ),
                                    ),
                                ],
                              ),
                            ],
                          ),
                        ),
                      ),
                    ),
                    const SizedBox(height: 20),
                    Container(
                        width: double.infinity,
                        padding: const EdgeInsets.all(12),
                        decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(12),
                            color: const Color.fromARGB(255, 45, 48, 54),
                            boxShadow: const [
                              BoxShadow(
                                color: Colors.black12,
                                spreadRadius: 0,
                                blurRadius: 2,
                                // offset: const Offset(0, 1),
                              ),
                            ]),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          mainAxisSize: MainAxisSize.min,
                          children: [
                            Text('Amount: $amount',
                                style: const TextStyle(color: Colors.white70)),
                            Text('Exp Required: $expRequired',
                                style: const TextStyle(color: Colors.white70)),
                            Text('Salary: $salary',
                                style: const TextStyle(color: Colors.white70)),
                            Text(
                                'Start date: ${startDate.toString().substring(0, 10)}',
                                style: const TextStyle(color: Colors.white70)),
                            Text(
                                'End date: ${endDate.toString().substring(0, 10)}',
                                style: const TextStyle(color: Colors.white70)),
                          ],
                        )),
                    const SizedBox(height: 20),
                    const Text(
                      'CV List',
                      style: TextStyle(
                          color: Colors.white70,
                      fontWeight: FontWeight.bold,
                      fontSize: 15),
                    ),
                    Container(
                        padding: const EdgeInsets.all(12),
                        decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(12),
                            color: const Color.fromARGB(255, 45, 48, 54),
                            boxShadow: const [
                              BoxShadow(
                                color: Colors.black12,
                                spreadRadius: 0,
                                blurRadius: 2,
                                // offset: const Offset(0, 1),
                              ),
                            ]),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          mainAxisSize: MainAxisSize.min,
                          children: [
                            Container(
                                child: isLoading
                                    ? const Center(
                                        child: CircularProgressIndicator())
                                    : (cvList.isEmpty
                                        ? Container(
                                            height: 110,
                                            alignment: Alignment.center,
                                            child: const Text(
                                              'You still not received any CV',
                                              style: TextStyle(
                                                  color: Colors.white70),
                                            ))
                                        : ListView.builder(
                                            physics: const ClampingScrollPhysics(),
                                            shrinkWrap: true,
                                            scrollDirection: Axis.vertical,
                                            itemCount: cvList.length,
                                            itemBuilder: (BuildContext context,
                                                    int index) =>
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
                                                        Row(
                                                          children: [
                                                            Text(cvList[index]['cv']['name'].toString().toUpperCase(),
                                                              style: const TextStyle(
                                                                      fontWeight: FontWeight.bold,
                                                                      color: Colors.white70,),
                                                            ),
                                                            const Spacer(),
                                                            cvList[index]['cv_is_save'] == true
                                                                ? const Icon(FluentIcons.bookmark_16_filled,
                                                              color:  Colors.indigoAccent)
                                                                : const SizedBox.shrink()
                                                          ],
                                                        ),
                                                        Text(
                                                          'Created date: ${safeDate(cvList[index]['cv']['create_at'])}',
                                                          style: const TextStyle(fontSize: 12, color: Colors.white54),
                                                        ),
                                                        const SizedBox(height: 8,),
                                                        SizedBox(
                                                          height: 35,
                                                          child: ElevatedButton.icon(
                                                              onPressed: (){
                                                                Navigator.of(context).push(MaterialPageRoute(builder: (ctx) => PDFViewScreen(cvId: cvList[index]['id'], isSave: cvList[index]['cv_is_save'], path: cvList[index]['cv']['file_name']))).then(onGoBack);
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
                                          ))),
                          ],
                        ))
                  ],
                ),
              ),
            ),
    );
  }

  String safeDate(String? date) {
    if (date == null || date.length < 10) return 'N/A';
    return date.substring(0, 10);
  }

}
