import 'package:flutter/material.dart';
import 'package:dream_job/apis/apis_list.dart';
import 'package:dream_job/features/job_card.dart';
import 'package:dream_job/screens/viewmore_screen.dart';

class LatestJobs extends StatefulWidget {
  const LatestJobs({super.key});

  @override
  State<LatestJobs> createState() => _LatestJobsState();
}

class _LatestJobsState extends State<LatestJobs> {
  var jobList = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    getJobs(10,1);
  }

  getJobs(int size, int page) async {
    var data = await JobApi.getLatestJobs.sendRequest(urlParam: '?size=$size&page=$page');
    jobList = data.map((e) => e).toList();
    if(data != null){
      setState(() {
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
    return isLoading
        ? const CircularProgressIndicator()
        : Column(
            children: [
              Container(
                color: Colors.white,
                child: Padding(
                  padding: const EdgeInsets.all(12.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Row(
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          const Text(
                            'Latest jobs',
                            style: TextStyle(
                              fontWeight: FontWeight.bold,
                              fontSize: 18,
                            ),
                          ),
                          const Spacer(),
                          SizedBox(
                            height: 35,
                            child: TextButton(
                              onPressed: (){
                                Navigator.of(context).push(MaterialPageRoute(builder: (ctx) => const ViewmoreScreen(dataType: 'Latest Jobs',)));
                              },
                              child: const Text('View more',style: TextStyle(
                                  fontSize: 14, color: Colors.blueAccent)),
                            ),
                          ),
                        ],
                      ),
                      const SizedBox(height: 10),
                      ListView.builder(
                          physics: const ClampingScrollPhysics(),
                          shrinkWrap: true,
                          scrollDirection: Axis.vertical,
                          itemCount: jobList.sublist(0,8).length,
                          itemBuilder: (BuildContext context, int index) =>
                              JobCard(jobId: jobList[index]['id'], notifyParent: (){},)
                      ),
                    ],
                  ),
                ),
              ),
              const SizedBox(height: 10),
            ],
          );
  }
}
