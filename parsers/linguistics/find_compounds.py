#!/usr/bin/env python

import sys, os, re, json, copy
from analysis import Document

"""
Converts all the .yaml files in corpus to .json files in the /tmp/ directory.
Is this stupid?
  Yes.
Is it less stupid than trying to deal with the state of yaml support in Python?
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
* Convert to Document
* Grab utterances.text
* Traverse looking for words with children that have N aspect
* Grab those and add to a list
* return the loose and literal for that utterance along with captured nodes

"""
def process_json_item(item):
    document = Document.initialize_from_json(json_item)
    for utterance in document.utterances:
        text = utterance.text
        found_nodes = []
        for node in text.ended:
            found_nodes.extend(find_N_children(node))
        if (len(found_nodes) > 0):
            print ("\n".join([node.serialize() for node in found_nodes]))
            print (utterance.literal)
            print (utterance.loose)

def find_N_children(node):
    found_nodes = []
    compound = None
    for child in node.collection:
        if (child.word.aspect == 'N'):
            compound = copy.deepcopy(node)
        else:
            found_nodes.extend(find_N_children(child))
    if (compound is not None):
        compound.word.relation = 'End'
        compound.word.pronomial = ''
        for child in compound.collection:
            if (child.word.aspect != 'N'):
                compound.collection.remove(child)
                compound.word.collector -= 1
        found_nodes.append(compound)
    return found_nodes
    
    

    
json_files = convert_corpus()
for i in range(len(json_files)):
    print (json_files[i])
    json_item = read_json_file(json_files[i])
    process_json_item(json_item)
