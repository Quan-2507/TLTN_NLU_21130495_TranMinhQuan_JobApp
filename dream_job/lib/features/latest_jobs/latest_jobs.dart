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

  Widget _buildFilterChip(String label, bool isSelected) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 8),
      decoration: BoxDecoration(
        color: isSelected ? const Color(0xFF246BFD) : Colors.white,
        border: Border.all(color: isSelected ? const Color(0xFF246BFD) : const Color(0xFF246BFD)),
        borderRadius: BorderRadius.circular(24),
      ),
      child: Text(
        label,
        style: TextStyle(
          color: isSelected ? Colors.white : const Color(0xFF246BFD),
          fontWeight: FontWeight.w600,
        ),
      ),
    );
  }

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
                            'Recent Jobs',
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
                              child: const Text('See All',style: TextStyle(
                                  fontSize: 14, color: Color(0xFF246BFD), fontWeight: FontWeight.bold)),
                            ),
                          ),
                        ],
                      ),
                      const SizedBox(height: 10),
                      SingleChildScrollView(
                        scrollDirection: Axis.horizontal,
                        padding: const EdgeInsets.symmetric(vertical: 4.0),
                        child: Row(
                          children: [
                            _buildFilterChip("All", true),
                            const SizedBox(width: 8),
                            _buildFilterChip("Design", false),
                            const SizedBox(width: 8),
                            _buildFilterChip("Technology", false),
                            const SizedBox(width: 8),
                            _buildFilterChip("Finance", false),
                          ],
                        ),
                      ),
                      const SizedBox(height: 12),
                      ListView.builder(
                          physics: const ClampingScrollPhysics(),
                          shrinkWrap: true,
                          scrollDirection: Axis.vertical,
                          itemCount: jobList.length > 8 ? 8 : jobList.length,
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
