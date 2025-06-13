import 'package:enefty_icons/enefty_icons.dart';
import 'package:fluentui_system_icons/fluentui_system_icons.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:dream_job/apis/apis_list.dart';
import 'package:dream_job/common_widgets/body_text.dart';
import 'package:dream_job/common_widgets/bullet_list.dart';
import 'package:dream_job/common_widgets/header_text.dart';
import 'package:dream_job/modals/login_request.dart';
import 'package:dream_job/screens/companies/company_detail_screen.dart';
import 'package:dream_job/screens/my_cv/my_cv_screen.dart';
import 'package:dream_job/utils/date_utils.dart';

import '../../features/hot_jobs/hot_jobs.dart';
import '../pdf_view_screen.dart';

var storage = const FlutterSecureStorage();

class JobsDetailScreen extends StatefulWidget {
  const JobsDetailScreen({super.key, required this.jobId});

  final int jobId;

  @override
  State<JobsDetailScreen> createState() => _JobsDetailScreenState();
}

class _JobsDetailScreenState extends State<JobsDetailScreen>
    with SingleTickerProviderStateMixin {
  late TabController _tabController;
  var jobDetail = {};
  var cvList = [];
  bool isLoading = true;
  int jobId = 0;
  String jobTitle = '';
  String companyName = '';
  String companyLogo = '';
  String companyBg = '';
  int amount = 0;
  String expRequired = '';
  String salary = '';
  String location = '';
  String jobDescription = '';
  String jobResponsibility = '';
  String jobRequired = '';
  String jobBenefit = '';
  String createdDate = '';
  Set skills = {};
  Set industries = {};

  bool isLoggedIn = false;
  int selectCvId = 0;
  bool isSave = false;
  bool isApplied = false;

  @override
  void initState() {
    _tabController = TabController(length: 2, vsync: this);
    _tabController.addListener(_handleTabSelection);
    super.initState();
    loginState();
    getJobDetail(widget.jobId);
  }

  loginState() async {
    String? userToken = await storage.read(key: 'token');
    if(userToken != null && userToken != '') {
      Map<String, String> jsonBody = {
        'token': userToken
      };
      var data = await AuthApi.checkToken.sendRequest(body: jsonBody);
      if (data != null) {
        setState(() {
          isLoggedIn = true;
        });
      }else{
        await storage.deleteAll();
      }
    }else{
      await storage.deleteAll();
    }
  }

  _handleTabSelection() {
    if (_tabController.indexIsChanging) {
      setState(() {});
    }
  }

  getJobDetail(int id) async {
    String? userToken = await storage.read(key: 'token');
    if(userToken == null || userToken == ''){
      jobDetail = await JobApi.getJobDetail.sendRequest(urlParam: '/${id.toString()}');
    }else{
      jobDetail = await JobApi.getJobDetailAuth.sendRequest(token: userToken, urlParam: '/${id.toString()}');
    }

    getAllCV(10,1);

    if(jobDetail.isNotEmpty){
      setState(() {
        jobId = jobDetail['id'] ?? 0;
        jobTitle = jobDetail['title'] ?? '';
        companyName = jobDetail['company']['name'] ?? '';
        companyLogo = jobDetail['company']['logo_image'] ?? 'https://i.pravatar.cc/160';
        companyBg = jobDetail['company']['background_image'] ?? 'https://i.pravatar.cc/200';
        amount = jobDetail['amount'] ?? 0;
        expRequired = jobDetail['experience_required'] ?? '';
        salary = (jobDetail['salary_min'] != null ? jobDetail['salary_min'] + ' - ' : '') + (jobDetail['salary_max'] ?? '');
        location = jobDetail['company']['location'] ?? 0;
        jobDescription = jobDetail['description'] ?? '';
        jobResponsibility = jobDetail['reponsibility'] ?? '';
        jobRequired = jobDetail['skill_required'] ?? '';
        jobBenefit = jobDetail['benefit'] ?? '';
        createdDate = AppDateUtils.daysBetween(jobDetail['start_date']);
        for (var i in jobDetail['company']['skills']) {
          skills.add(i['name']);
          industries.add(i['industry']['name']);
        }
        isApplied = jobDetail['job_is_apply'] ?? false;
        isSave = jobDetail['job_is_save'] ?? false;
        isLoading = false;
      });
    }
  }

  getAllCV(int size, int page) async {
    String? userToken = await storage.read(key: 'token');
    if (userToken != null && userToken != '') {
      var data = await CandidateCVApi.getAllCVs.sendRequest(token: userToken, urlParam: '?size=$size&page=$page');
      if (data != null) {
        cvList = data.map((e) => e).toList();
      }
    }
  }

  applyJob() async {
    var userToken = await storage.read(key: 'token');
    Map<String, String> jsonBody = {
      'job_id': jobId.toString(),
      'cv_id' : selectCvId.toString(),
      'note' : ''
    };

    var data = await CandidateJobApi.applyJob.sendRequest(body: jsonBody, token: userToken);
    if (data != null){
      setState(() {
        isApplied = true;
      });
      Navigator.of(context).pop();
    }
  }


  @override
  Widget build(BuildContext context) {
    return  isLoading
        ? const Center(child: CircularProgressIndicator())
        : Scaffold(
            appBar: AppBar(
              title: const Text(
                'Job Details', style: TextStyle(
                  color: Colors.white,
                  fontWeight: FontWeight.bold
              ),
              ),
              backgroundColor: Color(0xFF44903e),
              leading: const BackButton(
                  color: Colors.white
              ),
              centerTitle: true,
            ),
            backgroundColor: Colors.white,
            bottomNavigationBar: Container(
              width: double.infinity,
              decoration: const BoxDecoration(
                borderRadius: BorderRadius.only(
                    topRight: Radius.circular(20),
                    topLeft: Radius.circular(20)),
                boxShadow: [
                  BoxShadow(
                      color: Colors.black12, spreadRadius: 0, blurRadius: 10),
                ],
              ),
              child: ClipRRect(
                  borderRadius: const BorderRadius.only(
                    topLeft: Radius.circular(20.0),
                    topRight: Radius.circular(20.0),
                  ),
                  child: BottomAppBar(
                    color: Colors.white,
                    elevation: 0,
                    height: 60,
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        ElevatedButton(
                          onPressed: () async {
                            var userToken = await storage.read(key: "token");
                            if (userToken == null) {
                              showDialog(
                                  context: context,
                                  builder: (BuildContext dialogContext) {
                                    return const LoginRequestModal();
                                  });
                            }else{
                              Map<String, String> jsonBody2 = {
                                'job_id': jobId.toString(),
                              };
                              await CandidateJobApi.saveJob.sendRequest(body: jsonBody2, token: userToken);
                              getJobDetail(jobId);
                            }

                          },
                          style: ButtonStyle(
                              backgroundColor: isSave ? WidgetStateProperty.all(Color(0xFF44903e)) : WidgetStateProperty.all(Colors.white),
                              shape: WidgetStateProperty.all(
                                RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(6),
                                ),
                              ),
                              side: WidgetStateProperty.all(const BorderSide(color: Color(0xFF44903e)))),
                          child: Row(
                            children: [
                              Text(isSave ? 'SAVED' : 'SAVE'),
                              const SizedBox(width: 12),
                              const Icon(
                                FluentIcons.bookmark_16_regular,
                                size: 18,
                              )
                            ],
                          ),
                        ),
                        const SizedBox(width: 14),
                        Expanded(
                          child: isApplied ? ElevatedButton(
                            onPressed: () {},
                            style: ButtonStyle(
                              backgroundColor: WidgetStateProperty.all(Colors.white.withOpacity(0.1)),
                              foregroundColor: WidgetStateProperty.all(Colors.black54),
                              shape: WidgetStateProperty.all(
                                RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(6),
                                ),
                              ),
                            ),
                            child: const Text('You already applied'),
                          ): ElevatedButton(
                            onPressed: () async {
                              var userToken = await storage.read(key: "token");
                              if (userToken == null) {
                                showDialog(
                                    context: context,
                                    builder: (BuildContext dialogContext) {
                                      return const LoginRequestModal();
                                    });
                              }else{
                                showModalBottomSheet(
                                    context: context,
                                    isScrollControlled: true,
                                    shape: const RoundedRectangleBorder(
                                      borderRadius: BorderRadius.vertical(
                                        top: Radius.circular(20),
                                      ),
                                    ),
                                    clipBehavior: Clip.antiAliasWithSaveLayer,
                                    builder: (BuildContext context) {
                                      return StatefulBuilder(
                                        builder: (BuildContext context, setState) => Container(
                                            width: double.infinity,
                                            height: MediaQuery.of(context).size.height * 0.85,
                                            color: Colors.white,
                                            child: Stack(
                                              children: [
                                                Positioned.fill(
                                                    top: 0,
                                                    child: Container(
                                                      padding: const EdgeInsets.symmetric(vertical: 16),
                                                      child: const Text('Please choose one CV to apply',
                                                        textAlign: TextAlign.center,
                                                        style: TextStyle(
                                                            fontSize: 18,
                                                            fontWeight: FontWeight.bold
                                                        ),),)
                                                ),
                                                Positioned(
                                                    child: cvList.isEmpty
                                                        ? Container(
                                                        height: 110,
                                                        alignment: Alignment.center,
                                                        child: const Text('You do not have any CV'))
                                                        : Container(
                                                      margin: const EdgeInsets.only(top: 50),
                                                      // padding: const EdgeInsets.all(8),
                                                      height: 550,
                                                      child: SingleChildScrollView(
                                                        child: Column(
                                                          children: [
                                                            ListView.builder(
                                                              physics: const ClampingScrollPhysics(),
                                                              shrinkWrap: true,
                                                              scrollDirection: Axis.vertical,
                                                              itemCount: cvList.length,
                                                              itemBuilder: (BuildContext context, int index) =>
                                                                  RadioListTile(
                                                                    title: Container(
                                                                      padding: const EdgeInsets.all(15),
                                                                      decoration: BoxDecoration(
                                                                          borderRadius: BorderRadius.circular(10),
                                                                          color: cvList[index]['id'] == selectCvId ? const Color.fromARGB(255, 252, 203, 203): Colors.white,
                                                                          boxShadow: [
                                                                            BoxShadow(
                                                                              color: Colors.grey.withOpacity(0.4),
                                                                              spreadRadius: 0,
                                                                              blurRadius: 6,
                                                                            ),
                                                                          ]
                                                                      ),
                                                                      child: Row(
                                                                        crossAxisAlignment: CrossAxisAlignment.start,
                                                                        children: [
                                                                          const Icon(EneftyIcons.document_favorite_outline, color: Color(0xFF44903e), size: 35),
                                                                          const SizedBox(width: 20),
                                                                          Expanded(
                                                                            child: Column(
                                                                              crossAxisAlignment: CrossAxisAlignment.start,
                                                                              children: [
                                                                                Text(cvList[index]['name'].toString().toUpperCase(), style: const TextStyle(
                                                                                    fontWeight: FontWeight.bold,
                                                                                    color: Color(0xFF44903e)
                                                                                ),),
                                                                                Text('Upload date: ${cvList[index]['create_at'].toString().substring(0,10)}', style: const TextStyle(fontSize: 12),),
                                                                                const SizedBox(height: 8),
                                                                                SizedBox(
                                                                                    height:25,
                                                                                    child: ElevatedButton.icon(onPressed: (){
                                                                                      Navigator.of(context).push(MaterialPageRoute(builder: (ctx) => PDFViewScreen(path: cvList[index]['file_name'])));
                                                                                    }, icon: const Icon(EneftyIcons.eye_outline, size: 18), label: const Text('Preview')))
                                                                              ],
                                                                            ),
                                                                          ),
                                                                        ],
                                                                      ),
                                                                    ),
                                                                    value: cvList[index]['id'],
                                                                    groupValue: selectCvId,
                                                                    onChanged: (value) {
                                                                      setState(() {
                                                                        selectCvId = value;
                                                                      });
                                                                    },
                                                                  ),
                                                            ),
                                                          ],),
                                                      ),
                                                    )),
                                                Positioned.fill(
                                                    bottom: 10,
                                                    child: Align(
                                                      alignment: Alignment.bottomCenter,
                                                      child: Padding(
                                                        padding: const EdgeInsets.symmetric(horizontal: 12),
                                                        child: Row(
                                                          children: [
                                                            SizedBox(
                                                              child: ElevatedButton(
                                                                onPressed: (){
                                                                  Navigator.of(context).pushReplacement(MaterialPageRoute(
                                                                      builder: (ctx) => const MyCVScreen()));
                                                                },
                                                                style: ButtonStyle(
                                                                    backgroundColor: WidgetStateProperty.all(Colors.white),
                                                                    shape: WidgetStateProperty.all(
                                                                      RoundedRectangleBorder(
                                                                        borderRadius: BorderRadius.circular(6),
                                                                      ),
                                                                    ),
                                                                    side: WidgetStateProperty.all(
                                                                        const BorderSide(color: Color(0xFF44903e)))
                                                                ),
                                                                child: const Text('Manage CV'),
                                                              ),
                                                            ),
                                                            const SizedBox(width: 12),
                                                            Expanded(
                                                              child: SizedBox(
                                                                child: ElevatedButton(
                                                                  onPressed: applyJob,
                                                                  style: ButtonStyle(
                                                                    backgroundColor: WidgetStateProperty.all(Color(0xFF44903e)),
                                                                    foregroundColor: WidgetStateProperty.all(Colors.white),
                                                                    shape: WidgetStateProperty.all(
                                                                      RoundedRectangleBorder(
                                                                        borderRadius: BorderRadius.circular(6),
                                                                      ),
                                                                    ),
                                                                  ),
                                                                  child: const Text('Apply This Job'),
                                                                ),
                                                              ),
                                                            ),
                                                          ],
                                                        ),
                                                      ),
                                                    ))
                                              ],
                                            )
                                        ),
                                      );
                                    });
                              }

                            },
                            style: ButtonStyle(
                              backgroundColor: WidgetStateProperty.all(Color(0xFF44903e)),
                              foregroundColor: WidgetStateProperty.all(Colors.white),
                              shape: WidgetStateProperty.all(
                                RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(6),
                                ),
                              ),
                            ),
                            child: const Text('APPLY NOW'),
                          ),
                        )
                      ],
                    ),
                  )),
            ),
            body: SingleChildScrollView(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Stack(
                    children: [
                      Image.network(companyBg,
                          height: 200,
                          width: double.infinity,
                          fit: BoxFit.cover),
                      Stack(
                        alignment: Alignment.topCenter,
                        children: [
                          Container(
                            margin: const EdgeInsets.only(
                                top: 110, left: 10, right: 10),
                            decoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(8),
                                color: Colors.white,
                                boxShadow: [
                                  BoxShadow(
                                    color: Colors.grey.withOpacity(0.2),
                                    spreadRadius: 5,
                                    blurRadius: 7,
                                    offset: const Offset(0, 3),
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
                                          color: Color(0xFF44903e)),
                                    ),
                                    const SizedBox(height: 8),
                                    Text(
                                      companyName.toUpperCase(),
                                      textAlign: TextAlign.center,
                                      style: const TextStyle(
                                        fontWeight: FontWeight.bold,
                                      ),
                                    ),
                                    const SizedBox(height: 8),
                                    Wrap(
                                      spacing: 4,
                                      runSpacing: 4,
                                      children: [
                                        for (var item in skills) Container(
                                          padding: const EdgeInsets.symmetric(
                                              horizontal: 6, vertical: 4),
                                          decoration: BoxDecoration(
                                            borderRadius:
                                                BorderRadius.circular(4),
                                            color: Colors.grey.withOpacity(0.5),
                                          ),
                                          child: Text(
                                            item,
                                            style: const TextStyle(
                                              color: Colors.white,
                                              fontSize: 12
                                            ),
                                          ),
                                        ),
                                      ],
                                    ),
                                    const SizedBox(height: 8),
                                    Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: [
                                        const Icon(EneftyIcons.clock_2_outline,
                                            size: 18),
                                        const SizedBox(width: 4),
                                        Text(createdDate),
                                      ],
                                    ),
                                  ],
                                ),
                              ),
                            ),
                          ),
                          Container(
                            margin: const EdgeInsets.only(top: 20),
                            decoration: BoxDecoration(
                              color: Colors.white,
                                borderRadius: BorderRadius.circular(12),
                                boxShadow: [
                                  BoxShadow(
                                    color: Colors.black87.withOpacity(0.4),
                                    spreadRadius: 5,
                                    blurRadius: 7,
                                    offset: const Offset(0, 3),
                                  ),
                                ]),
                            child: ClipRRect(
                              borderRadius: BorderRadius.circular(8.0),
                              child: Image.network(
                                companyLogo,
                                height: 70.0,
                                width: 70.0,
                              ),
                            ),
                          )
                        ],
                      ),
                    ],
                  ),
                  const SizedBox(height: 20),
                  DefaultTabController(
                    length: 2,
                    child: Column(
                      children: <Widget>[
                        Container(
                          decoration: BoxDecoration(
                            color: Colors.grey[300],
                            borderRadius: BorderRadius.circular(
                              25.0,
                            ),
                          ),
                          height: 38,
                          child: TabBar(
                            isScrollable: true,
                            indicator: BoxDecoration(
                              borderRadius: BorderRadius.circular(40),
                              // Creates border
                              color: Color(0xFF44903e),
                            ),
                            overlayColor: WidgetStateProperty.all(Colors.transparent),
                            padding: const EdgeInsets.symmetric(horizontal: 6),
                            labelColor: Colors.white,
                            labelStyle:
                                const TextStyle(fontWeight: FontWeight.bold),
                            unselectedLabelStyle:
                                const TextStyle(fontWeight: FontWeight.normal),
                            tabAlignment: TabAlignment.center,
                            dividerColor: Colors.transparent,
                            indicatorSize: TabBarIndicatorSize.tab,
                            controller: _tabController,
                            tabs: [
                              myTab('Job Details', 0),
                              myTab('Company', 0),
                            ],
                          ),
                        ),
                        Center(
                          child: [
                            jobDetailsTab(jobDescription, jobResponsibility, jobRequired, jobBenefit),
                            companyTab(jobDetail)
                          ][_tabController.index],
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          );
  }

  Container myTab(String text, int count) {
    return Container(
      height: 28,
      padding: const EdgeInsets.symmetric(horizontal: 20),
      child: Tab(
          child: Row(
            children: [
              Text(
                text,
              ),
              if (count > 0) const SizedBox(width: 4),
              if (count > 0)
                Badge(
                  label: Text(count > 0 ? count.toString() : ''),
                  backgroundColor: Color(0xFF44903e),
                )
            ],
          )),
    );
  }


  Container companyTab(dynamic jobDetail) {
    var companyDetail = jobDetail['company'];
    String linkweb = companyDetail['link_website'] ?? '';
    String companySize = companyDetail['size'] ?? '';
    String location = companyDetail['location'] ?? '';
    int companyId = companyDetail['id'];
    Set skills = {};
    Set industries = {};
    if(jobDetail['skills'] != null){
      for (var i in jobDetail['skills']) {
        skills.add(i['name']);
        industries.add(i['industry']['name']);
      }
    }

    return Container(
        padding: const EdgeInsets.symmetric(horizontal: 12),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const HeaderText('Information'),
            Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                companyDetailRow(EneftyIcons.link_outline, linkweb),
                const SizedBox(width: 8),
                const Icon(EneftyIcons.export_outline, size: 16),
              ],
            ),
            companyDetailRow(EneftyIcons.location_outline, location),
            companyDetailRow(EneftyIcons.profile_2user_outline, companySize),
            companyDetailRow(EneftyIcons.tag_outline, industries.join(", ")),

            const SizedBox(height: 8),
            Container(
              decoration: BoxDecoration(
                  border: Border.all(color: Color(0xFF44903e)),
                  borderRadius: BorderRadius.circular(8)
              ),
              margin: const EdgeInsets.symmetric(horizontal: 30, vertical: 4),
              padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 6),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text('FOLLOW THIS COMPANY', style: TextStyle(color: Color(0xFF44903e)),),
                  const SizedBox(width: 6),
                  Icon(FluentIcons.mail_alert_24_regular, color: Color(0xFF44903e),)
                ],
              ),
            ),
            Container(
              decoration: BoxDecoration(
                  border: Border.all(color: Colors.grey.shade400),
                  borderRadius: BorderRadius.circular(8)
              ),
              margin: const EdgeInsets.symmetric(horizontal: 30, vertical: 4),
              padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 6),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  InkWell(
                    onTap: () {
                      if (companyId <= 0) return;
                      Navigator.of(context).push(MaterialPageRoute(
                          builder: (ctx) => CompaniesDetailScreen(companyId: companyId)));
                    },
                      child: const Text('VIEW DETAIL', style: TextStyle(color: Colors.black54),)),
                  const SizedBox(width: 6),
                  const Icon(FluentIcons.eye_24_regular, color: Colors.black54,)
                ],
              ),
            ),
            const HeaderText('Information'),
            BodyText(companyDetail['introduction']),
          ],
        ));
  }

  Container jobDetailsTab(String jobDes, String jobRes, String jobReq, String jobBen) {
    return Container(
        padding: const EdgeInsets.symmetric(horizontal: 12),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisSize: MainAxisSize.min,
          children: [
            const HeaderText('Information'),
            companyDetailRow(EneftyIcons.location_outline, location),
            companyDetailRow(EneftyIcons.people_outline, 'Amount: ${amount.toString()}'),
            companyDetailRow(EneftyIcons.document_text_outline, 'Exp Required: $expRequired'),
            companyDetailRow(EneftyIcons.dollar_circle_outline, 'Salary: $salary'),
            companyDetailRow(EneftyIcons.tag_outline, industries.join(", ")),

            if(jobDes != '') const HeaderText('Job Description'),
            if(jobDes != '') BulletList(jobDes.split('\n')),

            if(jobRes != '') const HeaderText('Your Role & Responsibilities'),
            if(jobRes != '') BulletList(jobRes.split('\n')),

            if(jobReq != '') const HeaderText('Your Skills & Qualifications'),
            if(jobReq != '') BulletList(jobReq.split('\n')),

            if(jobBen != '') const HeaderText('Benefits For You'),
            if(jobBen != '') BulletList(jobBen.split('\n')),

            const SizedBox(height: 22),
            Container(
              color: const Color.fromARGB(255, 241, 242, 243),
              height: 8,
            ),
            isLoggedIn == true ? const HotJobs() : const SizedBox.shrink(),
          ],
        ));
  }

  Container companyDetailRow(IconData icon, String detail) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 4),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisSize: MainAxisSize.min,
        children: [
          Icon(icon, size: 18),
          const SizedBox(width: 6),
          Flexible(child: Text(detail, softWrap: true, style: TextStyle(
            color: Colors.black.withOpacity(0.6),
          ),)),
        ],
      ),
    );
  }
}




