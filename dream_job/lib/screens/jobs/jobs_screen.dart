import 'package:enefty_icons/enefty_icons.dart';
import 'package:fluentui_system_icons/fluentui_system_icons.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:dream_job/apis/apis_list.dart';
import 'package:dream_job/features/ads_carousel.dart';
import 'package:dream_job/features/hot_jobs/hot_jobs.dart';
import 'package:dream_job/features/latest_jobs/latest_jobs.dart';
import 'package:dream_job/features/partner_jobs/partners_carousel.dart';
import 'package:dream_job/features/saved_jobs/saved_jobs.dart';
import 'package:dream_job/features/viewed_jobs/viewed_jobs.dart';
import 'package:dream_job/screens/search/search_screen.dart';
import 'package:dream_job/screens/search/filter_options_modal.dart';

var storage = const FlutterSecureStorage();

class JobsScreen extends StatefulWidget{
  const JobsScreen({super.key});

  @override
  State<JobsScreen> createState() => _JobsScreenState();
}

class _JobsScreenState extends State<JobsScreen> {
  bool isLoggedIn = false;

  @override
  void initState() {
    super.initState();
    loginState();
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
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: [
          // Header
          Padding(
            padding: const EdgeInsets.only(left: 16.0, right: 16.0, top: 16.0, bottom: 8.0),
            child: Row(
              children: [
                const CircleAvatar(
                  radius: 24,
                  backgroundImage: NetworkImage('https://i.pravatar.cc/150?img=11'),
                ),
                const SizedBox(width: 12),
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text("Good Morning 👋", style: TextStyle(color: Colors.grey[600], fontSize: 12)),
                    const Text("Andrew Ainsley", style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16)),
                  ],
                ),
                const Spacer(),
                Container(
                  padding: const EdgeInsets.all(8),
                  decoration: BoxDecoration(
                    shape: BoxShape.circle,
                    border: Border.all(color: Colors.grey.shade200),
                  ),
                  child: const Icon(FluentIcons.alert_24_regular, size: 20),
                )
              ],
            ),
          ),

          // Search Bar
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
            child: InkWell(
              onTap: (){
                Navigator.of(context).push(MaterialPageRoute(
                    builder: (ctx) => const SearchScreen()));
              },
              child: Container(
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(12),
                  color: Colors.grey.shade100,
                ),
                padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 14.0),
                child: Row(
                  children: [
                    Icon(EneftyIcons.search_normal_2_outline, size: 20, color: Colors.grey[500]),
                    const SizedBox(width: 12),
                    Text("Search for a job or company", style: TextStyle(fontSize: 14, color: Colors.grey[500])),
                    const Spacer(),
                    GestureDetector(
                      onTap: () {
                        showModalBottomSheet(
                          context: context,
                          isScrollControlled: true,
                          backgroundColor: Colors.transparent,
                          builder: (context) => const FilterOptionsModal(),
                        );
                      },
                      child: const Icon(FluentIcons.options_24_regular, size: 20, color: Color(0xFF246BFD)),
                    ),
                  ],
                ),
              ),
            )
          ),

          // Banner
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 12.0),
            child: Container(
              width: double.infinity,
              height: 160,
              decoration: BoxDecoration(
                color: const Color(0xFF246BFD),
                borderRadius: BorderRadius.circular(24),
              ),
              child: Stack(
                children: [
                  Positioned(
                    right: 0,
                    bottom: 0,
                    top: 0,
                    child: ClipRRect(
                      borderRadius: const BorderRadius.only(
                        topRight: Radius.circular(24),
                        bottomRight: Radius.circular(24),
                      ),
                      child: Image.network(
                        'https://img.freepik.com/free-photo/young-businesswoman-standing-with-laptop-smiling-camera-white-background_114579-79883.jpg', 
                        fit: BoxFit.cover,
                      ),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(20),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        const Text("See how you can\nfind a job quickly!", style: TextStyle(color: Colors.white, fontSize: 18, fontWeight: FontWeight.bold, height: 1.2)),
                        const SizedBox(height: 16),
                        ElevatedButton(
                          onPressed: (){},
                          style: ElevatedButton.styleFrom(
                            backgroundColor: Colors.white,
                            foregroundColor: const Color(0xFF246BFD),
                            shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
                            padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
                            elevation: 0,
                          ),
                          child: const Text("Read more", style: TextStyle(fontWeight: FontWeight.bold, fontSize: 13)),
                        )
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ),

          // Recommendation List
          isLoggedIn ? const HotJobs() : const SizedBox.shrink(),

          // Latest Jobs List
          const LatestJobs(),
          const SizedBox(height: 24),
        ],
      ),
    );
  }
}