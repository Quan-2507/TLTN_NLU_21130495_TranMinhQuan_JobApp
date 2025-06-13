import 'package:fluentui_system_icons/fluentui_system_icons.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:syncfusion_flutter_pdfviewer/pdfviewer.dart';
import 'package:syncfusion_flutter_core/theme.dart';

import '../apis/apis_list.dart';

var storage = const FlutterSecureStorage();

class PDFViewScreen extends StatefulWidget {
  const PDFViewScreen({super.key, required this.cvId, required this.isSave, required this.path});
  final int cvId;
  final bool isSave;
  final String path;

  @override
  State<PDFViewScreen> createState() => _PDFViewScreenState();
}

class _PDFViewScreenState extends State<PDFViewScreen> {
  final GlobalKey<SfPdfViewerState> _pdfViewerKey = GlobalKey();

  late bool isSave;

  @override
  void initState() {
    super.initState();
    isSave = widget.isSave;
  }


  saveCv(int id) async {
    var token = await storage.read(key: 'token');
    var rs = await AdminEmployerApi.saveCV.sendRequest(token: token, urlParam: '/${id.toString()}');
    if(rs != null){
      setState(() {
        isSave = !isSave;
      });
    }

  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Preview CV', style: TextStyle(
          color: Colors.white,
          fontWeight: FontWeight.bold
        ),),
        leading: const BackButton(
            color: Colors.white
        ),
        centerTitle: true,
        backgroundColor: const Color.fromARGB(255, 45, 48, 54),
      ),
      body: SfPdfViewerTheme(
        data: const SfPdfViewerThemeData(
          backgroundColor: const Color.fromARGB(255, 32, 34, 37),
        ),
        child: SfPdfViewer.network(
          widget.path,
          key: _pdfViewerKey,
        ),
      ),
      floatingActionButton: FloatingActionButton.extended(
        onPressed: () async {
          saveCv(widget.cvId);
        },
        label: Text(isSave ? 'Undo Save' : 'Save'),
        icon: const Icon(FluentIcons.bookmark_16_regular),
        foregroundColor: Colors.white70,
        backgroundColor: Colors.blueAccent,
      ),
    );
  }
}