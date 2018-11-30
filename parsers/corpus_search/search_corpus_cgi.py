#!/usr/bin/env python
import math
import sys
sys.path.append("/home/dmm/public_html/cgi-bin/rikbin/rikbin/lib/python2.7/site-packages")
import yaml
import jinja2
import re
import os
import copy
import cgi
import cgitb

print "Content-type: text/html\n\n"
using_cgi = False
env = jinja2.Environment(
    loader=jinja2.FileSystemLoader('./templates'),
    autoescape=jinja2.select_autoescape(['html']),
)
corpus_source = "./corpus"
img_url_cgi = "http://www.suberic.net/~dmm/cgi-bin/rikchik.cgi"
corpus_walk = os.walk(corpus_source)
corpus_files = []
for location in corpus_walk:
    for filename in location[2]:
        if filename.endswith('.yaml'):
            corpus_files.append(location[0] + '/' + filename)
if len(sys.argv) <= 1:
    cgitb.enable()
    using_cgi = True
    search = cgi.FieldStorage()['search'].value
else:
    search = sys.argv[1]

corpus = {} 

def insert_entry(id, entry):
    if id.startswith(corpus_source):
        id = id[len(corpus_source):]
    if id.startswith('/'):
        id = id[1:]
    entry['id'] = id
    corpus[id] = entry

for filename in corpus_files:
    with open(filename, 'r') as f:
        data = f.read()
    doc_i = 0
    try:
        yaml_data = yaml.load_all(data)
    except Exception as e:
        # bad data, report this somewhere?
        yaml_data = []
    for structure in yaml_data:
        # denormalize
        defaults = {
            k: v for (k, v) in structure.iteritems() if not isinstance(v, list)
        }
        if 'utterance' in structure:
            utter_i = 0
            for utterance in structure['utterance']:
                entry = copy.copy(defaults)
                for (k, v) in utterance.iteritems():
                    entry[k] = v
                insert_entry("%s:%d:%d" % (filename, doc_i, utter_i), entry)
                utter_i += 1
        else:
            insert_entry("%s:%d:%d" % (filename, doc_i, 0), defaults)
        doc_i += 1

results = []
search_regex = re.compile(search)
def check_utterance(utterance):
    for (field_name, field_data) in utterance.iteritems():
        if isinstance(field_data, str):
            if search_regex.search(field_data):
                results.append(utterance)
                return
for (key, utterance) in corpus.iteritems():
    check_utterance(utterance)

for entry in results:
    text_words = entry['text'].split()
    # lineheight = min(math.ceil(math.sqrt(len(text_words))), 5)
    typesize = 2
    cgi_text = entry['text'].replace(' ', '.')
    entry['cgi_text'] = cgi_text
    entry['img_url'] = "%s?lineheight=s;size=%d;%s" % (img_url_cgi, typesize, cgi_text)
template = env.get_template('corpus_output.html')
render = template.render(results=results, search=search)
print render.encode('ascii', errors='ignore')
