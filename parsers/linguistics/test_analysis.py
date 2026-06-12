#!/usr/bin/env python

from analysis import Word, Node, Text

def test_word():
    word1 = Word.parse_marc_form("Home-R-Patient-0")
    assert word1.morpheme == "Home"
    assert word1.aspect == "R"
    assert word1.relation == "Patient"
    assert word1.collector == 0
    word2 = Word.parse_marc_form("Learn-V-Agentrec-S")
    assert word2.morpheme == "Learn"
    assert word2.aspect == "V"
    assert word2.relation == "Agentrec"
    assert word2.collector == -1
    word3 = Word.parse_marc_form("tree-m-end-1p")
    assert word3.morpheme == "Tree"
    assert word3.aspect == "M"
    assert word3.relation == "End"
    assert word3.collector == 1
    assert word3.pronomial == "P"
    assert word3.serialize() == "Tree-M-End-1P"
    
    
def test_split_text():
    words1 = Text.split_text("Home-P-Source-0.Sun-R-Quality-0.Beach-P-Destination-1.Me-R-Patient-0.Move-V-End-3")
    assert len(words1) == 5
    assert words1[2].morpheme == "Beach"

def test_parse_text():
    utt1 = Text()
    utt1.parse_text("Home-P-Source-0.Sun-R-Quality-0.Beach-P-Destination-1.Me-R-Patient-0.Move-V-End-3")
    assert len(utt1.ended) == 1
    assert utt1.ended[0].word.morpheme == "Move"
    assert len(utt1.ended[0].collection) == 3
    print (utt1)
    assert utt1.serialize() == "Home-P-Source-0 Sun-R-Quality-0 Beach-P-Destination-1 Me-R-Patient-0 Move-V-End-3"

