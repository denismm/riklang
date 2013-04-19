package net.suberic.rikchik.language;

import java.util.List;
import net.suberic.rikchik.language.Word;
import net.suberic.rikchik.language.LangData;
import net.suberic.rikchik.language.LangFactory;
import net.suberic.rikchik.language.GrammarException;

public class Utterance {
    
    private List sentences; //a List of Words with the End relation.
    private List words;     //a List of Words not yet in a sentence.
    
    public List getSentences(){
	return this.sentences;
    }

    public void setSentences(List value){
	this.sentences = value;
    }

    public void addSentence(Word sentence) throws GrammarException{
	if (sentence.getRelation() == LangData.R_END || sentence.isLenient()){
	    sentences.add(sentence);
	} else {
	    throw new GrammarException("Only end-relation words are sentences.");
	}
    }
    
    public void addWord (Word word) throws GrammarException{
	if (word.isLenient()){
	    throw new GrammarException ("cannot add lenient words to a parsing stack.");
	}
	if (word.isInUse()) {
	    throw new GrammarException("Cannot add a word in use to a parsing stack.");
	}
	int collectNum = word.getDesiredCollector();
	if (collectNum == LangData.C_S){ //adjustCollectNum.
	    collectNum = 0;
	    while (words.size() > collectNum && !((Word)words.get((words.size()-1) - collectNum)).hasChildren()){
		collectNum++;
	    }
	} 
	if (collectNum > words.size()) {
	    throw new GrammarException("Cannot collect more words than available in stack.");
	}
	List collectedList = sentences.subList((words.size()-1) - collectNum, words.size()-1);
	while (collectedList.size() > 0){
	    Word collectedWord = (Word)collectedList.remove(0);
	    word.addChild(collectedWord);
	}
	//add word to words or sentences.
	if (word.getRelation() == LangData.R_END){
	    sentences.add(word);
	} else {
	    words.add(word);
	}
    }
    
}
