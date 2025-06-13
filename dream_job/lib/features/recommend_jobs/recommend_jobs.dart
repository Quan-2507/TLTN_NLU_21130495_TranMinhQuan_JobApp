import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:dream_job/features/job_card.dart';

class RecommendJobs extends ConsumerWidget {
  const RecommendJobs({super.key});

  @override
  Widget build(BuildContext context, ref) {
    return Column(
      children: [
        Container(
          color: Colors.white,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              SizedBox(
                height: 220,
                child: ListView.builder(
                        physics: const ClampingScrollPhysics(),
                        shrinkWrap: true,
                        scrollDirection: Axis.horizontal,
                        itemCount: 2,
                        itemBuilder: (BuildContext context, int index) =>
                            Container(
                              width: 320,
                              margin: const EdgeInsets.only(right: 14),
                              child: JobCard(jobId: 1, notifyParent: (){}),
                            ),
                    )
                ),
            ],
          ),
        ),
      ],
    );
  }
}