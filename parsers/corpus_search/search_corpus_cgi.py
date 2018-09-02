#!/usr/local/bin/python
import jinja2
# import pprint
import math
import yaml
import re
import os
import sys
import copy

env = jinja2.Environment(
    loader=jinja2.FileSystemLoader('./templates'),
    autoescape=jinja2.select_autoescape(['html']),
)
corpus_source = "../../data/corpus"
img_url_cgi = "http://www.suberic.net/~dmm/cgi-bin/rikchik.cgi"
corpus_walk = os.walk(corpus_source)
corpus_files = []
for location in corpus_walk:
    for filename in location[2]:
        if filename.endswith('.yaml'):
            corpus_files.append(location[0] + '/' + filename)

# change this to get it from cgi
search = sys.argv[1]

corpus = {} # ?

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
    for structure in yaml.load_all(data):
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
    lineheight = min(math.ceil(math.sqrt(len(text_words))), 5)
    typesize = 2
    cgi_text = entry['text'].replace(' ', '_')
    entry['img_url'] = "%s?lineheight=%d&size=%d&%s" % (img_url_cgi, lineheight, typesize, cgi_text)
template = env.get_template('corpus_output.html')
print template.render(results=results)
# pprint.pprint(results)
