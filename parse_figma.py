import urllib.request
import json

TOKEN = 'YOUR_FIGMA_TOKEN_HERE'
FILE_KEY = "avFrNcK17LQ5fX9LeeX94f"
NODE_ID = "1627:22833"

url = f"https://api.figma.com/v1/files/{FILE_KEY}/nodes?ids={NODE_ID}"
req = urllib.request.Request(url, headers={"X-Figma-Token": TOKEN})

try:
    with urllib.request.urlopen(req) as response:
        data = json.loads(response.read().decode('utf-8'))
        nodes = data.get('nodes', {})
        node = nodes.get(NODE_ID, {}).get('document', {})
        
        def print_tree(n, indent=0):
            if not n: return
            prefix = "  " * indent
            name = n.get('name', 'Unnamed')
            t = n.get('type', 'UNKNOWN')
            chars = n.get('characters', '')
            
            fills = n.get('fills', [])
            color = ""
            if fills and isinstance(fills, list) and len(fills) > 0 and fills[0].get('type') == 'SOLID':
                c = fills[0].get('color', {})
                color = f" color=rgba({c.get('r',0)*255:.0f},{c.get('g',0)*255:.0f},{c.get('b',0)*255:.0f},{c.get('a',1):.2f})"
            
            text_str = f" text='{chars}'" if chars else ""
            print(f"{prefix}- [{t}] {name}{color}{text_str}")
            
            for child in n.get('children', []):
                print_tree(child, indent + 1)
                
        print_tree(node)
except Exception as e:
    print(f"Error: {e}")
