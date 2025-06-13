import 'package:enefty_icons/enefty_icons.dart';
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


  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: InkWell(
              onTap: (){
                Navigator.of(context).push(MaterialPageRoute(
                    builder: (ctx) => const SearchScreen()));
              },
              child: Container(
                decoration: BoxDecoration(
                  border: Border.all(color: Colors.grey),
                  borderRadius: BorderRadius.circular(30),
                  color: Colors.white
                ),
                padding: const EdgeInsets.all(12.0),
                child: Row(
                  children: [
                    const Text("Type keyword to search"),
                  const Spacer(),
                  Icon(
                            EneftyIcons.search_normal_2_outline,
                            size: 18,
                            color: Colors.grey[800],
                          )
                  ],
                ),
              ),
            )
          ),



          const PartnersCarousel(),

          //Hot for you
          isLoggedIn == true ? const HotJobs() : const SizedBox.shrink(),

          //Latest jobs
          const LatestJobs(),

          //Advertisment
          const AdsCarousel(),

          //Viewed jobs
          isLoggedIn == true ? const ViewedJobs() : const SizedBox.shrink(),

          //Saved jobs
          isLoggedIn == true ? const SavedJobs() : const SizedBox.shrink(),
        ],
      ),
    );
  }
}