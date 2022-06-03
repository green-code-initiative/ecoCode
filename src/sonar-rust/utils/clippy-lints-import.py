#!/usr/local/bin/python3

import urllib.request, json
clippy_lints_url = "https://rust-lang.github.io/rust-clippy/master/lints.json"


def get_name_from_docstring(docstring):
    length = 100
    start_index = len(" ### What it does\n")
    s = docstring[start_index:].replace('\n',' ')
    idx = s.find('###')
    if idx < 100:
        length=idx
    return s[:length].strip()



with urllib.request.urlopen(clippy_lints_url) as url:
    data = json.loads(url.read().decode())
    
rules = []



for lint in data:

   if lint["group"] == "deprecated":
       continue

   id =  lint["id"]
   key="clippy::" + id
   name = get_name_from_docstring(lint["docs"])
   url="https://rust-lang.github.io/rust-clippy/master/index.html#"+id
   sonar_rule={}
   sonar_rule["key"] = key
   sonar_rule["name"] = name
   sonar_rule["url"] = url
   rules.append(sonar_rule)

print(json.dumps(rules))


