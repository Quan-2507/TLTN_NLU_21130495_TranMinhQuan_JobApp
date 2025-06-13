import 'package:flutter/material.dart';
import 'package:dream_job/features/popular_companies/companies_list.dart';
import 'package:dream_job/features/popular_companies/spotlight_com_carousel.dart';

class CompaniesScreen extends StatelessWidget{
  const CompaniesScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return const SingleChildScrollView(
      child: Column(
        children: [
          SpotlightComCarousel(),
          CompaniesList()
        ],
      )
    );
  }
}