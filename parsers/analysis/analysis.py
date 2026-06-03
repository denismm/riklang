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

    @classmethod
    def initialize(cls, morpheme, aspect, relation, collector):
        word = Word(morpheme, aspect, relation, collector)
        return word

    @classmethod
    def parse_marc_form(cls, marc):
        [m,a,r,c] = marc.split("-")
        if (c in "sS"):
            c = -1
        c = int(c)
        return Word.initialize(m,a,r,c)
    

@dataclass
class Node:
    word: Word
    collection: list[Word] = field(default_factory=list)

@dataclass
class Utterance:
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
                child = self.uncollected.pop()
                node.collection.insert(0,child)
        elif (collector < 0):
            #grab all of uncollected
            for i in range(len(uncollected)):
                #grab from uncollected
                child = self.uncollected.pop()
                node.collection.insert(0,child)
        if (word.relation == "End"):
            #add to ended
            self.ended.append(node)
        else:
            #add to uncollected
            self.uncollected.append(node)
    
    def parse_text(self, text):
        words = Utterance.split_text(text)
        #for each word
        for word in words:
            self.add_word(word)
        
    
