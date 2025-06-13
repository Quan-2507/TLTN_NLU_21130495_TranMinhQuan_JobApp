import 'package:flutter/material.dart';
import 'package:dream_job/apis/apis_list.dart';
import 'package:dream_job/features/company_card.dart';
import 'package:dream_job/features/job_card.dart';

import '../companies/company_detail_screen.dart';

class SearchResultScreen extends StatefulWidget {
  const SearchResultScreen({super.key, required this.keyword, required this.result});
  final String keyword;
  final dynamic result;

  @override
  State<SearchResultScreen> createState() => _SearchResultScreenState();
}

class _SearchResultScreenState extends State<SearchResultScreen> with SingleTickerProviderStateMixin{
  late TabController _tabController;
  final _formSearchMain = GlobalKey<FormState>();
  int indexTab = 0;
  List jobsData = [];
  List companyData = [];
  String _keyword = '';


  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 2, vsync: this);
    _tabController.addListener(_handleTabSelection);
    jobsData = widget.result['jobs'];
    companyData = widget.result['companies'];
    _keyword = widget.keyword;
  }

  void _handleTabSelection() {
    setState(() {
      indexTab = _tabController.index;
    });
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  search(String value) async {
    final isValid = _formSearchMain.currentState!.validate();
    if (!isValid) {
      return;
    }
    _formSearchMain.currentState!.save();
    var data = await SearchApi.search.sendRequest(urlParam: '/$value');
    setState(() {
      jobsData = data['jobs'];
      companyData = data['companies'];
    });
  }

  onGoBack(dynamic value) {
    setState(() {});
  }

  Container myTab(String text) {
    return
        Container(
          height: 30,
          margin: const EdgeInsets.symmetric(horizontal: 8),
          child: Tab(child: Row(
            children: [
              Text(text),
            ],
          )),
        );
  }

  Container noResult() {
    return Container(
      alignment: Alignment.topCenter,
        margin: const EdgeInsets.symmetric(vertical: 30),
        padding: const EdgeInsets.symmetric(horizontal: 12),
        child: const Text('Sorry we did not found any result,\n please try other keyword', style: TextStyle(
          color: Colors.black45
        ), textAlign: TextAlign.center,)
    );
  }

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 2,
      child: Scaffold(
          backgroundColor: const Color.fromARGB(255, 241, 242, 243),
          appBar: AppBar(
            backgroundColor: Color(0xFF44903e),
            leading: BackButton(
                color: Colors.white,
                onPressed: (){Navigator.of(context)..pop()..pop();}
            ),
            bottom: PreferredSize(
              preferredSize: const Size.fromHeight(40),
              child: Align(
                alignment: Alignment.centerLeft,
                child: Column(
                  children: [
                    TabBar(
                      controller: _tabController,
                      indicatorSize: TabBarIndicatorSize.tab,
                      indicator: BoxDecoration(
                          borderRadius: BorderRadius.circular(40), // Creates border
                          color: Colors.white),
                      isScrollable: true,
                      overlayColor: WidgetStateProperty.all(Colors.transparent),
                      dividerColor: Color(0xFF44903e),
                      dividerHeight: 8,
                      padding: const EdgeInsets.symmetric(horizontal: 8),
                      labelPadding: const EdgeInsets.symmetric(horizontal: 8),
                      labelStyle: const TextStyle(fontWeight: FontWeight.bold),
                      unselectedLabelStyle: const TextStyle(fontWeight: FontWeight.normal, color: Colors.white),
                      tabAlignment: TabAlignment.start,
                      tabs: [
                        myTab('Jobs'),
                        myTab('Companies'),
                      ],
                    ),
                    const SizedBox(height: 5)
                  ],
                ),
              ),
            ),
            title: Form(
              key: _formSearchMain,
              child: TextFormField(
                decoration: InputDecoration(
                  focusedBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(30.0),
                    borderSide: const BorderSide(color: Colors.white),
                  ),
                  enabledBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(30.0),
                    borderSide: const BorderSide(color: Colors.white),
                  ),
                  filled: true,
                  hintStyle: TextStyle(color: Colors.grey[800]),
                  hintText: _keyword,
                  fillColor: Colors.white,
                  isDense: true,
                  contentPadding:
                  const EdgeInsets.symmetric(horizontal: 20, vertical: 8),
                ),
                autofocus: true,
                onFieldSubmitted: (value) async {
                  _keyword = value;
                  await search(value);
                },
                textInputAction: TextInputAction.search,
              ),
            ),
          ),
          body: TabBarView(
            controller: _tabController,
            children: [
              SingleChildScrollView(
                child: Container(
                  padding: const EdgeInsets.all(10.0),
                  child: jobsData.isEmpty
                      ? noResult()
                      : Column(children: [
                    for(int i =0; i <jobsData.length; i++)
                      JobCard(jobId: jobsData[i]['id'], notifyParent: (){})
                  ]),
                ),
              ),
              SingleChildScrollView(
                child: Container(
                  padding: const EdgeInsets.all(10.0),
                  child: companyData.isEmpty ? noResult() : Column(children: [
                    for(int i =0; i <companyData.length; i++)
                      InkWell(
                          onTap: () async{
                            if (companyData[i]['id'] <= 0) return;
                            Navigator.of(context).push(MaterialPageRoute(
                                builder: (ctx) => CompaniesDetailScreen(companyId: companyData[i]['id']))).then(onGoBack);
                          },
                          child: CompanyCard(company: companyData[i]))
                  ]),
                ),
              ),
            ],
          )),
    );
  }
}
