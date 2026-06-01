#!/usr/bin/env python

"""
This is a library and program designed to read assumed-correct Rikchik ASCII into a data structure so that it can be searched and outputted in useful ways.
"""

from dataclasses import dataclass, field

@dataclass
class Word:
    morpheme: str = None
    aspect: str = None
    relation: str = None
    collector: int = 0

    @classmethod
    def initialize(cls, morpheme, aspect, relation, collector = 0):
        word = Word(morpheme, aspect, relation)
        return word

    @classmethod
    def parse_marc_form(cls, marc):
        [m,a,r,c] = marc.split("-")
        if (c in "sS"):
            c = -1
        return Word.initialize(m,a,r,c)
    

@dataclass
class Node:
    word: Word
    collection: list[Word] = field(default_factory=list)


def parse_string(ascii_string):
    uncollected = []
    ended = []
    #split into words
    words = [Word.parse_marc_form(marc) for marc in string.split("[^0-9a-zA-Z]")]
    #for each word
    for (word in words):
        node = Node(word)
        #check collector
        if (word.collector > 0):
            for i in range(word.collector):
                #grab from uncollected
                child = uncollected.pop()
                node.collection.insert(0,child)
        elif (collector < 0):
            #grab all of uncollected
            for i in range(len(uncollected)):
                #grab from uncollected
                child = uncollected.pop()
                node.collection.insert(0,child)
            pass
        if (word.aspect == "End"):
            #add to ended
            ended.append(word)
        else:
            #add to uncollected
            uncollected.append(word)
        
        
    
