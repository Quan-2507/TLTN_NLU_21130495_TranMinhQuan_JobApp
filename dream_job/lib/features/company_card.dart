import 'package:enefty_icons/enefty_icons.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

var storage = const FlutterSecureStorage();

class CompanyCard extends StatefulWidget{
  const CompanyCard({
    super.key,
    required this.company,
  });

  final dynamic company;

  @override
  State<CompanyCard> createState() => _CompanyCardState();
}

class _CompanyCardState extends State<CompanyCard> {
  Set industries = {};
  Set skills = {};
  
  @override
  Widget build(BuildContext context) {
    int companyId = widget.company['id'] ?? 0;
    String companyLogo = widget.company['logo_image'] ?? 'https://i.pravatar.cc/40';
    String companyName = widget.company['name'] ?? '';
    String address = widget.company['city_province']['name']?? '';
    String companySize = widget.company['size'] ?? '';
    int openingJobs = widget.company['opening_jobs'] ?? 0;

    if(widget.company['skills'] != null){
      for (var i in widget.company['skills']) {
        industries.add(i['industry']['name']);
        if(skills.length > 4) break;
        String s = i['name'];
        s = s.length < 10 ? s : '${s.substring(0,10)}...';
        skills.add(s);
      }
    }


    return Card(
      margin: const EdgeInsets.only(bottom: 12, left: 4, right: 4),
      elevation: 4,
      shadowColor: Colors.black.withOpacity(0.4),
      child: Padding(
        padding: const EdgeInsets.all(12.0),
        child: SizedBox(
          width: double.infinity,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisSize: MainAxisSize.min,
            children: [
              Row(
                children: [
                  Container(
                    width: 40.0,
                    height: 40.0,
                    decoration: BoxDecoration(
                      color: const Color(0xff7c94b6),
                      image: DecorationImage(
                        image: NetworkImage(companyLogo),
                        fit: BoxFit.cover,
                      ),
                      borderRadius: const BorderRadius.all( Radius.circular(50.0)),
                      border: Border.all(
                        color: Colors.grey,
                        width: 1.0,
                      ),
                    ),
                  ),
                  const SizedBox(width: 6),
                  Expanded(
                    child: Text(
                      companyName,
                      maxLines: 2,
                      overflow: TextOverflow.ellipsis,
                      softWrap: true,
                      style: const TextStyle(
                        fontWeight: FontWeight.bold,
                        color: Colors.black87
                      ),
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 6),
              Wrap(
                spacing: 6,
                runSpacing: 6,
                children: [
                  for(int i = 0; i < skills.length; i++)
                    Chip(
                      label: Text(skills.elementAtOrNull(i) ?? ''),
                      visualDensity: const VisualDensity(horizontal: -4, vertical: -4),
                      materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
                    ),
                ],
              ),
              const SizedBox(height: 6),
              Row(
                children: [
                  const Icon(EneftyIcons.location_outline, size: 18),
                  const SizedBox(width: 4),
                  Expanded(
                    child: Text(address,
                      maxLines: 2,
                      overflow: TextOverflow.ellipsis,
                      softWrap: true,
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 6),
              Row(
                children: [
                  const Icon(EneftyIcons.profile_2user_outline, size: 18),
                  const SizedBox(width: 4),
                  Text(companySize),
                  const SizedBox(width: 26),
                  const Icon(EneftyIcons.briefcase_outline, size: 18),
                  const SizedBox(width: 4),
                  Text("$openingJobs job"),
                ],
              ),
              const SizedBox(height: 6),
              Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Icon(EneftyIcons.tag_outline, size: 18),
                  const SizedBox(width: 4),
                  Expanded(child: Text(industries.join(", "), maxLines: 2, softWrap: true,overflow: TextOverflow.ellipsis,)),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}