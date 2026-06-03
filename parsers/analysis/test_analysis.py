#!/usr/bin/env python

from analysis import Word, Node, Utterance

def test_word():
    word1 = Word.parse_marc_form("Home-R-End-0")
    assert word1.morpheme == "Home"
    assert word1.aspect == "R"
    assert word1.relation == "End"
    assert word1.collector == 0
    word2 = Word.parse_marc_form("Learn-V-Agentrec-S")
    assert word2.morpheme == "Learn"
    assert word2.aspect == "V"
    assert word2.relation == "Agentrec"
    assert word2.collector == -1
    
def test_utt_split_text():
    words1 = Utterance.split_text("Home-P-Source-0.Sun-R-Quality-0.Beach-P-Destination-1.Me-R-Patient-0.Move-V-End-3")
    assert len(words1) == 5
    assert words1[2].morpheme == "Beach"

def test_utt_parse_text():
    utt1 = Utterance()
    utt1.parse_text("Home-P-Source-0.Sun-R-Quality-0.Beach-P-Destination-1.Me-R-Patient-0.Move-V-End-3")
    assert len(utt1.ended) == 1
    assert utt1.ended[0].word.morpheme == "Move"
    assert len(utt1.ended[0].collection) == 3
    #for checking
    print (utt1)
    assert 0 == 1

