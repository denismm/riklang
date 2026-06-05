#!/usr/bin/env python

import sys, os, re, json
from analysis import Corpuscle

"""
Converts all the .yaml files in corpus to .json files in the /tmp/ directory.
Is this stupid?
  Yes.
Is it less stupid that trying to deal with the state of yaml support in Python?
  Good question.
"""
def convert_corpus():
    corpus_source = "../../data/corpus"
    corpus_walk = os.walk(corpus_source)
    yaml_files = []
    json_files = []
    for location in corpus_walk:
        if location[0] == corpus_source:
            continue
        for filename in location[2]:
            if filename.endswith('.yaml'):
                yaml_files.append(location[0] + '/' + filename)

    yaml_filename_regex = r'../../data/corpus/(....)/(.*).yaml'
    json_filename_regex = r'/tmp/corpus_\1_\2.json'
    jsonize_template = "yq -o json %(yaml_filename)s > %(json_filename)s"
    for yaml_filename in yaml_files:
        file_count = count_yaml_objects(yaml_filename)
        if (file_count == 0):
            json_filename = re.sub(yaml_filename_regex, json_filename_regex, yaml_filename)
            jsonize_map =  {'json_filename': json_filename, 'yaml_filename': yaml_filename}
            jsonize_command =  jsonize_template % jsonize_map
            os.system(jsonize_command)
            json_files.append(json_filename)
        else:
            print ("Multiple files not supported")
            
    return json_files


def count_yaml_objects(filename):
    count = 0
    with open(filename, 'r') as fh:
        for line in fh:
            if (line.startswith('---')):
                count += 1
    return count


"""
Turn a json filename into a python object
"""
def read_json_file(filename):
    with open(filename, 'r') as fh:
        json_item = json.load(fh)
        return json_item

"""
Process an object:
* Convert to Corpuscle
* Grab utterances.text
* Traverse looking for words with children that have N aspect
* Grab those and add to a list
* return the loose and literal for that utterance along with captured nodes

"""
def process_json_item(item):
    print (json_item)
    corpuscle = Corpuscle.initialize_from_json(json_item)
    print (corpuscle)


    
json_files = convert_corpus()
for i in range(len(json_files)):
    print (json_files[i])
    json_item = read_json_file(json_files[i])
    process_json_item(json_item)
