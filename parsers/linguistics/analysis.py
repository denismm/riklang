#!/usr/bin/env python

"""
This is a library and program designed to read assumed-correct Rikchik ASCII into a data structure so that it can be searched and outputted in useful ways.
"""

from dataclasses import dataclass, field
import re

@dataclass
class Word:
    morpheme: str = None
    aspect: str = None
    relation: str = None
    collector: int = 0
    pronomial: str = None

    @classmethod
    def initialize(cls, morpheme, aspect, relation, collector, pronomial):
        word = Word(morpheme.capitalize(), aspect.upper(), relation.capitalize(), collector, pronomial.upper())
        return word

    @classmethod
    def parse_marc_form(cls, marc):
        [m,a,r,c] = marc.split("-")
        p = ""
        if (c[-1] in "pP"):
            p = "P"
            c = c[:-1]
        if (c[-1] in "oO"):
            p = "O"
            c = c[:-1]
        if (c in "sS"):
            c = -1
        c = int(c)
        return Word.initialize(m,a,r,c,p)

    def get_collector(self):
        s_c = str(self.collector)
        if (self.collector < 0):
            s_c = "S"
        s_c = s_c + self.pronomial.upper()
        return s_c
    
    def serialize(self):
        return "-".join([self.morpheme, self.aspect, self.relation, self.get_collector()])
    

@dataclass
class Node:
    word: Word
    collection: list[Node] = field(default_factory=list)

    def serialize(self):
        ascii = self.word.serialize()
        for child in self.collection:
            ascii = child.serialize() + " " + ascii
        return ascii
        

@dataclass
class Text:
    uncollected: list[Node] = field(default_factory=list)
    ended: list[Node] = field(default_factory=list)
    
    @classmethod
    def split_text(cls, text):
        #split into words
        text_words = re.split(r'[^-0-9a-zA-Z]', text)
        words = [Word.parse_marc_form(marc) for marc in text_words]
        return words

    def add_word(self, word):
        node = Node(word)
        #check collector
        collector = word.collector
        if (collector > 0):
            for i in range(collector):
                #grab from uncollected
                if (len(self.uncollected) == 0):
                    print (word)
                child = self.uncollected.pop()
                node.collection.append(child)
        elif (collector < 0):
            #grab all of uncollected
            for i in range(len(uncollected)):
                #grab from uncollected
                child = self.uncollected.pop()
                node.collection.append(child)
        if (word.relation == "End"):
            #add to ended
            self.ended.append(node)
        else:
            #add to uncollected
            self.uncollected.append(node)
    
    def parse_text(self, text):
        words = Text.split_text(text)
        #for each word
        for word in words:
            self.add_word(word)

    @classmethod
    def initialize_from_text(cls, text):
        new_text = Text()
        new_text.parse_text(text)
        return new_text

    def serialize(self):
        ascii = " ".join(node.serialize() for node in self.uncollected)
        if (len(ascii) > 0):
            ascii += " "
        ascii += " ".join(node.serialize() for node in self.ended)
        return ascii
            
    

@dataclass
class Utterance:
    author: str
    literal: str
    loose: str
    text: Text

    @classmethod
    def get_value(cls, key, item, parent):
        if (key in item):
            return(item[key])
        elif (parent is not None and key in parent):
            return(parent[key])
        else:
            return None
    
    @classmethod
    def initialize_from_json(cls, json_item, parent = None):
        json_author = Utterance.get_value('author', json_item, parent)
        json_literal = Utterance.get_value('literal', json_item, parent)
        json_loose = json_item['loose']
        json_text = Text.initialize_from_text(json_item['text'])
        return Utterance(json_author, json_literal, json_loose, json_text)
            
@dataclass
class Document:
    date: str
    source: str
    color: str
    utterances: list[Utterance] = field(default_factory=list)

    @classmethod
    def initialize_from_json(cls, json_item):
        json_utterances = []
        json_date = json_item['date']
        json_source = json_item['source']
        json_color = json_item['color']
        if ('utterance' in json_item):
            for json_utterance in json_item['utterance']:
                json_utterances.append(Utterance.initialize_from_json(json_utterance, json_item))
        else:
            json_utterances.append(Utterance.initialize_from_json(json_item))
        return Document(json_date, json_source, json_color, json_utterances)
    
