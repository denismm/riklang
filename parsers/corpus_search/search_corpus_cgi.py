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

relation_map = {
    'Task': 'Agentrec',
    'Result': 'Sourcerec',
    'Example': 'Qualityrec',
    'Means': 'Destinationrec',
    'Actor': 'Patientrec',
}

def web_main():
    print "Content-type: text/html\n\n"
    env = jinja2.Environment(
        loader=jinja2.FileSystemLoader('./templates'),
        autoescape=jinja2.select_autoescape(['html']),
    )
    img_url_cgi = "http://www.suberic.net/~dmm/cgi-bin/rikchik.cgi"
    cgitb.enable()
    search = cgi.FieldStorage().getfirst('search', None)
    field = cgi.FieldStorage().getfirst('field', None)

    if search:
        corpus = read_corpus()
        results = search_corpus(corpus, search, field)
        for entry in results:
            text_words = entry['text'].split()
            # lineheight = min(math.ceil(math.sqrt(len(text_words))), 5)
            typesize = 2
            cgi_text = entry['text'].replace(' ', '.')
            entry['cgi_text'] = cgi_text
            entry['img_url'] = "%s?lineheight=s;size=%d;%s" % (img_url_cgi, typesize, cgi_text)
    else:
        results = []
    template = env.get_template('corpus_output.html')
    render = template.render(results=results, search=search)
    print render.encode('ascii', errors='ignore')

def read_corpus():
    corpus_source = "./corpus"
    corpus_walk = os.walk(corpus_source)
    corpus_files = []
    corpus = {}
    for location in corpus_walk:
        if location[0] == corpus_source:
            continue
        for filename in location[2]:
            if filename.endswith('.yaml'):
                corpus_files.append(location[0] + '/' + filename)
    def massage_id(id):
        if id.startswith(corpus_source):
            id = id[len(corpus_source):]
        if id.startswith('/'):
            id = id[1:]
        return id

    def insert_entry(id, entry):
        entry['id'] = massage_id(id)
        # change old relations to modern names
        text = entry['text'].split(' ')
        for i in range(len(text)):
            word = text[i]
            components = word.split('-')
            relation = components[2]
            if relation in relation_map:
                components[2] = relation_map[relation]
                word = '-'.join(components)
                text[i] = word
        entry['text'] = ' '.join(text)
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
                utterances = structure['utterance']
                defaults['source_utterance_count'] = len(utterances)
                defaults['source_id'] = massage_id("%s:%d:" % (filename, doc_i))
                for utterance in utterances:
                    entry = copy.copy(defaults)
                    for (k, v) in utterance.iteritems():
                        entry[k] = v
                    insert_entry("%s:%d:%d" % (filename, doc_i, utter_i), entry)
                    utter_i += 1
            else:
                defaults['source_utterance_count'] = 1
                insert_entry("%s:%d:%d" % (filename, doc_i, 0), defaults)
            doc_i += 1

    return corpus

searchable_fields = ['literal', 'loose', 'text', 'note']
def search_corpus(corpus, search, field):
    results = []
    search_regex = re.compile(search)
    if field:
        field_list = [field]
    else:
        field_list = searchable_fields
    field_set = set(field_list)
    def check_utterance(utterance):
        for (field_name, field_data) in utterance.iteritems():
            if field_name in field_set and isinstance(field_data, str):
                if search_regex.search(field_data):
                    results.append(utterance)
                    return
    for (key, utterance) in corpus.iteritems():
        check_utterance(utterance)
    return results


web_main()
