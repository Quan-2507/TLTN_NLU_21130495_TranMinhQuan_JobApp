import 'dart:io';
import 'dart:convert';

void main() async {
  final token = 'YOUR_FIGMA_TOKEN_HERE';
  final fileKey = 'avFrNcK17LQ5fX9LeeX94f';
  final nodeId = '1627:22833';

  var client = HttpClient();

  print('Fetching node data...');
  final url = Uri.parse('https://api.figma.com/v1/files/$fileKey/nodes?ids=$nodeId');
  final request = await client.getUrl(url);
  request.headers.add('X-Figma-Token', token);
  final response = await request.close();
  final responseBody = await response.transform(utf8.decoder).join();
  final data = json.decode(responseBody);
  
  final document = data['nodes'][nodeId]['document'];
  
  List<String> frameIds = [];
  if (document['children'] != null) {
      for (var child in document['children']) {
          if (child['type'] == 'CANVAS') {
              for (var node in child['children']) {
                  if (node['type'] == 'FRAME' || node['type'] == 'GROUP') {
                      frameIds.add(node['id']);
                  }
              }
          } else if (child['type'] == 'FRAME') {
              frameIds.add(child['id']);
          }
      }
  }
  
  if (frameIds.isEmpty) {
      // If no CANVAS children, maybe it is just directly children
      frameIds.add(nodeId);
  }
  
  print('Found frame IDs: $frameIds');
  
  final idsToFetch = frameIds.take(5).join(',');
  print('Fetching images for: $idsToFetch');
  
  final imgUrlReq = Uri.parse('https://api.figma.com/v1/images/$fileKey?ids=$idsToFetch&format=png');
  final req2 = await client.getUrl(imgUrlReq);
  req2.headers.add('X-Figma-Token', token);
  final res2 = await req2.close();
  final body2 = await res2.transform(utf8.decoder).join();
  final imgData = json.decode(body2);
  
  final images = imgData['images'] as Map<String, dynamic>? ?? {};
  int i = 0;
  for (var entry in images.entries) {
      if (entry.value != null) {
          final imgUrl = entry.value;
          print('Downloading ${entry.key}...');
          final imgReq = await client.getUrl(Uri.parse(imgUrl));
          final imgRes = await imgReq.close();
          final file = File('figma_preview_$i.png');
          await imgRes.pipe(file.openWrite());
          print('Saved figma_preview_$i.png');
          i++;
      }
  }
}
