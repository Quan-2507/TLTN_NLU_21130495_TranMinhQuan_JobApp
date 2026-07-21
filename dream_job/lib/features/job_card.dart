
import 'package:enefty_icons/enefty_icons.dart';
import 'package:fluentui_system_icons/fluentui_system_icons.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:intl/intl.dart';
import 'package:dream_job/apis/apis_list.dart';
import 'package:dream_job/modals/login_request.dart';
import 'package:dream_job/screens/jobs/jobs_detail_screen.dart';
import 'package:dream_job/utils/date_utils.dart';

var storage = const FlutterSecureStorage();

class JobCard extends StatefulWidget{

  const JobCard({
    super.key,
    this.jobId,
    required this.notifyParent
  });
  final Function notifyParent;
  final dynamic jobId;

  @override
  State<JobCard> createState() => _JobCardState();
}

class _JobCardState extends State<JobCard> {
  var jobInfo = {};
  int jobId = 0;
  String companyName = '';
  String jobTitle = '';
  bool isSaved = false;
  String logoImage = 'https://i.pravatar.cc/40';
  String province = '';
  String salary = '';
  String createdDate = '';
  List jobSkills = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    getJobs(widget.jobId);
  }

  getJobs(int id) async {
    String? userToken = await storage.read(key: 'token');
    if(userToken != null && userToken != '') {
      jobInfo = await JobApi.getJobDetailAuth.sendRequest(token: userToken, urlParam: '/${id.toString()}');
    }else{
      jobInfo = await JobApi.getJobDetail.sendRequest(urlParam: '/${id.toString()}');

    }
    if(jobInfo.isNotEmpty){
      setState(() {
        jobId = jobInfo['id'] ?? 0;
        companyName = jobInfo['company']['name'] ?? 'company name';
        jobTitle = jobInfo['title'] ?? 'job title';
        isSaved = jobInfo['job_is_save'] ?? false;
        logoImage = jobInfo['company']['logo_image'] ?? 'https://i.pravatar.cc/40';
        province = jobInfo['company']['city_province']['name'] ?? 'Ho Chi Minh';
        salary = (jobInfo['salary_min'] != null ? '\$${jobInfo['salary_min']}' : 'Negotiable') + (jobInfo['salary_max'] != null ? ' - \$${jobInfo['salary_max']} /month'  : ' /month');
        createdDate = AppDateUtils.daysBetween(jobInfo['start_date'] ?? DateFormat("dd-MM-yy").format(DateTime.now()));
        jobSkills = jobInfo['skills'];
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {

    Set skills = {};
    if(jobSkills.isNotEmpty){
      for (var i in jobSkills) {
        if(skills.length > 4) break;
        String s = i['name'];
        s = s.length < 10 ? s : '${s.substring(0,10)}...';
        skills.add(s);
      }
    }

    return isLoading ? const Center(child: CircularProgressIndicator()) :  Card(
      margin: const EdgeInsets.only(bottom: 12, left: 4, right: 4),
      child: InkWell(
        borderRadius: BorderRadius.circular(16),
        onTap: () async {
          var userToken = await storage.read(key: 'token');
          if(userToken != null && userToken != ''){
            Map<String, String> jsonBody = {
              'job_id': jobId.toString(),
            };
            await CandidateJobApi.viewJob.sendRequest(body: jsonBody, token: userToken);
            widget.notifyParent();
          }
          Navigator.of(context).push(MaterialPageRoute(builder: (ctx) => JobsDetailScreen(jobId: jobId)));
        },
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: SizedBox(
            width: double.infinity,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisSize: MainAxisSize.min,
              children: [
                Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Container(
                      padding: const EdgeInsets.all(8),
                      decoration: BoxDecoration(
                        color: Colors.grey.shade50,
                        borderRadius: BorderRadius.circular(12),
                      ),
                      child: ClipOval(
                        child: SizedBox.fromSize(
                          size: const Size(40, 40),
                          child: Image.network(
                            logoImage,
                            fit: BoxFit.cover,
                          ),
                        ),
                      ),
                    ),
                    const SizedBox(width: 12),
                    Expanded(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            jobTitle,
                            maxLines: 1,
                            overflow: TextOverflow.ellipsis,
                            style: const TextStyle(
                              color: Colors.black,
                              fontSize: 16,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          const SizedBox(height: 4),
                          Text(
                            companyName,
                            maxLines: 1,
                            overflow: TextOverflow.ellipsis,
                            style: TextStyle(
                              color: Colors.grey[600],
                              fontSize: 13,
                            ),
                          ),
                        ],
                      ),
                    ),
                    IconButton(
                      padding: EdgeInsets.zero,
                      constraints: const BoxConstraints(),
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
                          getJobs(jobId);
                        }
                      },
                      icon: Icon(isSaved == true ?
                      FluentIcons.bookmark_24_filled : FluentIcons.bookmark_24_regular,
                        color: const Color(0xFF246BFD),
                        size: 24,
                      ),
                    ),
                  ],
                ),
                const SizedBox(height: 16),
                Padding(
                  padding: const EdgeInsets.only(left: 68.0), // Align with text above
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        province + ", United States", // Mocking country for design match
                        style: TextStyle(color: Colors.grey[700], fontSize: 13),
                      ),
                      const SizedBox(height: 6),
                      Text(
                        salary,
                        style: const TextStyle(
                          color: Color(0xFF246BFD),
                          fontSize: 14,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      const SizedBox(height: 12),
                      Wrap(
                        spacing: 8,
                        runSpacing: 8,
                        children: [
                          // Hardcoding tags to match design if skills are empty
                          _buildTag("Full Time"),
                          _buildTag("Onsite"),
                          for (int i = 0; i < skills.length; i++)
                            _buildTag(skills.elementAt(i).toString()),
                        ],
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
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