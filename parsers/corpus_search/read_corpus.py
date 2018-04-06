#!/usr/bin/env python
import yaml
import os
import sys
corpus_source = "../../data/corpus"
corpus_walk = os.walk(corpus_source)
corpus_files = []
for location in corpus_walk:
    for filename in location[2]:
        if filename.endswith('.yaml'):
            corpus_files.append(location[0] + '/' + filename)

for filename in corpus_files:
    with open(filename, 'r') as f:
        data = f.read()
    for structure in yaml.load_all(data):
        print filename + ": ok"
        # denormalize
        # store
        # iterate through titles etc
