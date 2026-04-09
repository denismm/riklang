#!/usr/bin/env python
import yaml
import os
import sys
import copy
corpus_source = "../../data/corpus"
corpus_walk = os.walk(corpus_source)
corpus_files = []
for location in corpus_walk:
    for filename in location[2]:
        if filename.endswith('.yaml'):
            corpus_files.append(location[0] + '/' + filename)

corpus = {} # ?

def insert_entry(id, entry):
    if id.startswith(corpus_source):
        id = id[len(corpus_source):]
    if id.startswith('/'):
        id = id[1:]
    corpus[id] = entry
    print "%s ok" % id

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
