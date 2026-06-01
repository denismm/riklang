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
    words = []
    #for each word
    for (word in words):
        node = Node(word)
        #check collector
        if (collector > 0):
            #grab from uncollected
            pass
        elif (collector < 0):
            #grab all of uncollected
            pass
        if (word.aspect == "End"):
            #add to ended
            pass
        else:
            #add to uncollected
            pass
        
        
    
