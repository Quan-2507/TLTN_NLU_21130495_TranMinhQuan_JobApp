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
              backgroundColor: Colors.white,
              elevation: 0,
              iconTheme: const IconThemeData(color: Colors.black),
              actions: [
                IconButton(
                  onPressed: () async {
                    var userToken = await storage.read(key: "token");
                    if (userToken == null) {
                      showDialog(
                          context: context,
                          builder: (BuildContext dialogContext) {
                            return const LoginRequestModal();
                          });
                    } else {
                      Map<String, String> jsonBody2 = {
                        'job_id': jobId.toString(),
                      };
                      await CandidateJobApi.saveJob.sendRequest(body: jsonBody2, token: userToken);
                      getJobDetail(jobId);
                    }
                  },
                  icon: Icon(
                    isSave == true ? FluentIcons.bookmark_24_filled : FluentIcons.bookmark_24_regular,
                    color: isSave == true ? const Color(0xFF246BFD) : Colors.black,
                  ),
                ),
                IconButton(
                  onPressed: () {},
                  icon: const Icon(FluentIcons.share_24_regular, color: Colors.black),
                ),
                const SizedBox(width: 8),
              ],
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
                    height: 80,
                    padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 12),
                    child: isApplied 
                        ? ElevatedButton(
                            onPressed: () {},
                            style: ElevatedButton.styleFrom(
                              backgroundColor: Colors.grey.shade300,
                              foregroundColor: Colors.grey.shade700,
                              shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(30)),
                              elevation: 0,
                            ),
                            child: const Text('Already Applied', style: TextStyle(fontWeight: FontWeight.bold)),
                          )
                        : ElevatedButton(
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
                            style: ElevatedButton.styleFrom(
                              backgroundColor: const Color(0xFF246BFD),
                              foregroundColor: Colors.white,
                              shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(30)),
                              elevation: 0,
                            ),
                            child: const Text('Apply', style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16)),
                          ),
                    ),
                  )),
            ),
            body: SingleChildScrollView(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
                    child: Container(
                      width: double.infinity,
                      padding: const EdgeInsets.all(24),
                      decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.circular(24),
                        border: Border.all(color: Colors.grey.shade200),
                      ),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          Container(
                            padding: const EdgeInsets.all(16),
                            decoration: BoxDecoration(
                              color: Colors.grey.shade50,
                              shape: BoxShape.circle,
                            ),
                            child: Image.network(
                              companyLogo,
                              width: 60,
                              height: 60,
                              fit: BoxFit.cover,
                            ),
                          ),
                          const SizedBox(height: 16),
                          Text(
                            jobTitle,
                            style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                            textAlign: TextAlign.center,
                          ),
                          const SizedBox(height: 8),
                          Text(
                            companyName,
                            style: const TextStyle(fontSize: 14, color: Color(0xFF246BFD), fontWeight: FontWeight.w600),
                            textAlign: TextAlign.center,
                          ),
                          const SizedBox(height: 16),
                          Text(
                            location + ", United States",
                            style: TextStyle(color: Colors.grey.shade600, fontSize: 13),
                            textAlign: TextAlign.center,
                          ),
                          const SizedBox(height: 6),
                          Text(
                            salary,
                            style: const TextStyle(fontSize: 14, color: Color(0xFF246BFD), fontWeight: FontWeight.bold),
                            textAlign: TextAlign.center,
                          ),
                          const SizedBox(height: 16),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              _buildTag("Full Time"),
                              const SizedBox(width: 8),
                              _buildTag("Onsite"),
                            ],
                          ),
                          const SizedBox(height: 16),
                          Text(
                            "Posted $createdDate ago, ends in 31 Dec.",
                            style: TextStyle(color: Colors.grey.shade500, fontSize: 11),
                            textAlign: TextAlign.center,
                          ),
                        ],
                      ),
                    ),
                  ),
                  const SizedBox(height: 20),
                  // Custom Tab Row
                  SingleChildScrollView(
                    scrollDirection: Axis.horizontal,
                    padding: const EdgeInsets.symmetric(horizontal: 16.0),
                    child: Row(
                      children: [
                        _buildTab("Job Description", true),
                        const SizedBox(width: 24),
                        _buildTab("Minimum Qualifications", false),
                        const SizedBox(width: 24),
                        _buildTab("Perks & Benefits", false),
                        const SizedBox(width: 24),
                        _buildTab("Required Skills", false),
                      ],
                    ),
                  ),
                  const Divider(height: 1, thickness: 1, color: Color(0xFFEEEEEE)),
                  const SizedBox(height: 24),

                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 16.0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        if (jobDescription != '') ...[
                          const Text("Job Description:", style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
                          const SizedBox(height: 8),
                          BulletList(jobDescription.split('\n')),
                          const SizedBox(height: 24),
                        ],
                        if (jobRequired != '') ...[
                          const Text("Minimum Qualifications:", style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
                          const SizedBox(height: 8),
                          BulletList(jobRequired.split('\n')),
                          const SizedBox(height: 24),
                        ],
                        const Text("Perks and Benefits:", style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
                        const SizedBox(height: 12),
                        _buildBenefitItem(EneftyIcons.shield_tick_bold, "Medical / Health Insurance"),
                        _buildBenefitItem(EneftyIcons.health_bold, "Medical, Prescription, or Vision Plans"),
                        _buildBenefitItem(EneftyIcons.star_bold, "Performance Bonus"),
                        _buildBenefitItem(EneftyIcons.heart_bold, "Paid Sick Leave"),
                        _buildBenefitItem(EneftyIcons.briefcase_bold, "Paid Vacation Leave"),
                        _buildBenefitItem(EneftyIcons.location_bold, "Transportation Allowances"),
                        const SizedBox(height: 24),
                        
                        const Text("Required Skills:", style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
                        const SizedBox(height: 12),
                        Wrap(
                          spacing: 8,
                          runSpacing: 8,
                          children: [
                            for (var skill in skills)
                              _buildSkillChip(skill.toString()),
                            // Adding dummy skills to match design if API doesn't return enough
                            if (skills.isEmpty) ...[
                              _buildSkillChip("Creative Thinking"),
                              _buildSkillChip("UI/UX Design"),
                              _buildSkillChip("Figma"),
                              _buildSkillChip("Graphic Design"),
                              _buildSkillChip("Web Design"),
                              _buildSkillChip("Layout"),
                            ]
                          ],
                        ),
                        const SizedBox(height: 24),
                        
                        const Text("Jobs Summary:", style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
                        const SizedBox(height: 12),
                        Row(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Expanded(
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  _buildSummaryItem("Job level", "Associate / Supervisor"),
                                  const SizedBox(height: 16),
                                  _buildSummaryItem("Educational", "Bachelor's Degree"),
                                  const SizedBox(height: 16),
                                  _buildSummaryItem("Vacancy", "2 opening"),
                                ],
                              ),
                            ),
                            Expanded(
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  _buildSummaryItem("Job Category", "IT and Software"),
                                  const SizedBox(height: 16),
                                  _buildSummaryItem("Experience", expRequired.isNotEmpty ? expRequired : "1 - 3 Years"),
                                  const SizedBox(height: 16),
                                  _buildSummaryItem("Website", "www.google.com"),
                                ],
                              ),
                            ),
                          ],
                        ),
                        const SizedBox(height: 24),
                        
                        const Text("About:", style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
                        const SizedBox(height: 8),
                        Text(
                          jobDetail['company'] != null && jobDetail['company']['introduction'] != null 
                              ? jobDetail['company']['introduction']
                              : "Google LLC is an American multinational technology company that focuses on search engine technology, online advertising, cloud computing, computer software, quantum computing, e-commerce, artificial intelligence, and consumer electronics.\n\nGoogle was founded on September 4, 1998, by Larry Page and Sergey Brin.",
                          style: TextStyle(color: Colors.grey[700], height: 1.5, fontSize: 13),
                        ),
                        const SizedBox(height: 40),
                      ],
                    ),
                  )
                ],
              ),
            ),
          );
  }

  Widget _buildTab(String text, bool isSelected) {
    return Container(
      padding: const EdgeInsets.only(bottom: 12),
      decoration: BoxDecoration(
        border: Border(
          bottom: BorderSide(
            color: isSelected ? const Color(0xFF246BFD) : Colors.transparent,
            width: 2,
          ),
        ),
      ),
      child: Text(
        text,
        style: TextStyle(
          color: isSelected ? const Color(0xFF246BFD) : Colors.grey[500],
          fontWeight: isSelected ? FontWeight.bold : FontWeight.w500,
          fontSize: 14,
        ),
      ),
    );
  }

  Widget _buildBenefitItem(IconData icon, String text) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 12.0),
      child: Row(
        children: [
          Icon(icon, color: const Color(0xFF246BFD), size: 20),
          const SizedBox(width: 12),
          Text(text, style: TextStyle(color: Colors.grey[800], fontSize: 13)),
        ],
      ),
    );
  }

  Widget _buildSkillChip(String text) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 6),
      decoration: BoxDecoration(
        color: Colors.white,
        border: Border.all(color: const Color(0xFF246BFD)),
        borderRadius: BorderRadius.circular(20),
      ),
      child: Text(
        text,
        style: const TextStyle(color: Color(0xFF246BFD), fontSize: 12),
      ),
    );
  }

  Widget _buildSummaryItem(String title, String value) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(title, style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 12)),
        const SizedBox(height: 4),
        Text(value, style: const TextStyle(color: Color(0xFF246BFD), fontSize: 12)),
      ],
    );
  }

  Widget _buildTag(String text) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 4),
      decoration: BoxDecoration(
        color: Colors.transparent,
        border: Border.all(color: Colors.grey.shade300),
        borderRadius: BorderRadius.circular(6),
      ),
      child: Text(
        text,
        style: TextStyle(
          color: Colors.grey[700],
          fontSize: 10,
          fontWeight: FontWeight.w500,
        ),
      ),
    );
  }
}




