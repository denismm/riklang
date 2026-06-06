#!/usr/bin/env python

import sys, os, re, json, copy
import xml.etree.ElementTree as ET
from analysis import Node, Text, Utterance, Document

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
* return a new utterance

"""
def process_json_item(item):
    document = Document.initialize_from_json(json_item)
    return_utterances = []
    for utterance in document.utterances:
        text = utterance.text
        found_nodes = []
        for node in text.ended:
            found_nodes.extend(find_N_children(node))
        if (len(found_nodes) > 0):
            new_text = Text(ended=found_nodes)
            new_utterance = Utterance(utterance.author, utterance.literal, utterance.loose, new_text)
            return_utterances.append(new_utterance)
    return return_utterances
        

"""
Find all nodes within a tree that have children with aspect=N and return them with their N-aspect children
"""
def find_N_children(node):
    found_nodes = []
    compound = Node(copy.deepcopy(node.word))
    for child in node.collection:
        if (child.word.aspect == 'N'):
            compound.collection.append(copy.deepcopy(child))
        else:
            found_nodes.extend(find_N_children(child))
    if (len(compound.collection) > 0):
        compound.word.relation = 'End'
        compound.word.pronomial = ''
        compound.word.collector = len(compound.collection)
        found_nodes.append(compound)
    return found_nodes
    

# morphemes is a map of morphemes to maps of ascii for compounds to utterances
def write_results(morphemes):
    language_element = ET.fromstring("<language><morphemes></morphemes></language>")
    morphemes_element = language_element.find('morphemes')
    for morpheme in morphemes:
        morpheme_element = ET.SubElement(morphemes_element, 'morpheme')
        morpheme_element.attrib['name'] = morpheme
        compounds = morphemes[morpheme]
        compounds_element = ET.SubElement(morpheme_element, 'compounds')
        for compound_ascii in compounds:
            compound_element = ET.SubElement(compounds_element, 'compound')
            write_addition(compound_ascii, compound_element)
            readings_element = ET.SubElement(compound_element, 'readings')
            aspect = compound_ascii.split(' ')[-1].split('-')[1]
            reading_element = ET.SubElement(readings_element, 'reading')
            reading_element.attrib['aspect'] = aspect
            translation_element = ET.SubElement(reading_element, 'translation')
            text_element = ET.SubElement(translation_element, 'text')
            compound_utterance = compounds[compound_ascii]
            loose = compound_utterance.loose
            literal = compound_utterance.literal
            translation_parts = [""]
            if (loose is not None):
                translation_parts.append(loose)
            if (literal is not None):
                translation_parts.append(literal)
            translation_parts.append("")
            translation_str = "\n".join(translation_parts)
            translation_comment = ET.Comment(translation_str)
            text_element.append(translation_comment)
            
    tree = ET.ElementTree()
    tree._setroot(language_element)
    tree.write('/tmp/compounds.xml', encoding='utf-8')

def write_addition(ascii, element):
    addition_element = ET.SubElement(element, 'addition')
    utterance_element = ET.SubElement(addition_element, 'utterance')    
    words = ascii.split(" ")[:-1]
    for word in words:
        write_word(word, utterance_element)

def write_word(ascii, element):
    word_element = ET.SubElement(element, 'word')
    [m,a,r,c] = ascii.split('-')
    word_element.attrib['morpheme'] = m
    word_element.attrib['aspect'] = a
    word_element.attrib['relation'] = r
    word_element.attrib['collector'] = c
    
#Main functionality 
json_filenames = convert_corpus()
results = {}
for json_filename in json_filenames:
    json_item = read_json_file(json_filename)
    utterances = process_json_item(json_item)
    if (len(utterances) > 0):
        results[json_filename] = utterances

morphemes = {}

for utt_filename in results:
    utterances = results[utt_filename]
    for utterance in utterances:
        for node in utterance.text.ended:
            morpheme = node.word.morpheme
            ascii = node.serialize()
            morpheme_entry = {}
            if (morpheme in morphemes):
                morpheme_entry = morphemes[morpheme]
            if (ascii in morpheme_entry):
                #print (ascii, "already present in", morpheme)
                pass
            else:
                morpheme_entry[ascii] = utterance
            morphemes[morpheme] = morpheme_entry

write_results(morphemes)
