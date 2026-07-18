import 'dart:io';
import 'dart:convert';

void main() async {
  final token = 'YOUR_FIGMA_TOKEN_HERE';
  final fileKey = 'avFrNcK17LQ5fX9LeeX94f';
  final nodeId = '1627:22833';

  final url = Uri.parse('https://api.figma.com/v1/files/$fileKey/nodes?ids=$nodeId');
  
  final request = await HttpClient().getUrl(url);
  request.headers.add('X-Figma-Token', token);
  final response = await request.close();
  
  final responseBody = await response.transform(utf8.decoder).join();
  final data = json.decode(responseBody);
  
  final nodes = data['nodes'] as Map<String, dynamic>? ?? {};
  final node = (nodes[nodeId] as Map<String, dynamic>?)?['document'] as Map<String, dynamic>? ?? {};
  
  void printTree(Map<String, dynamic>? n, [int indent = 0]) {
    if (n == null) return;
    final prefix = '  ' * indent;
    final name = n['name'] ?? 'Unnamed';
    final t = n['type'] ?? 'UNKNOWN';
    final chars = n['characters'] ?? '';
    
    var color = '';
    final fills = n['fills'] as List<dynamic>?;
    if (fills != null && fills.isNotEmpty && fills[0]['type'] == 'SOLID') {
      final c = fills[0]['color'] as Map<String, dynamic>?;
      if (c != null) {
        final r = (c['r'] * 255).toInt();
        final g = (c['g'] * 255).toInt();
        final b = (c['b'] * 255).toInt();
        final a = c['a'];
        color = ' color=rgba($r,$g,$b,$a)';
      }
    }
    
    final textStr = chars.isNotEmpty ? ' text="$chars"' : '';
    print('$prefix- [$t] $name$color$textStr');
    
    final children = n['children'] as List<dynamic>?;
    if (children != null) {
      for (final child in children) {
        printTree(child as Map<String, dynamic>?, indent + 1);
      }
    }
  }
  
  printTree(node);
}
