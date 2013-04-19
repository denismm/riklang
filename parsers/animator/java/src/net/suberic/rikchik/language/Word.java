package net.suberic.rikchik.language;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import net.suberic.rikchik.language.GrammarException;
import net.suberic.rikchik.language.Morpheme;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.LangFactory;
import net.suberic.rikchik.language.LangData;

public class Word {
    
    private Morpheme glyph;
    private int collector = LangData.C_0;
    private int relation ;
    private int form;
    private boolean mayBeScholastic = true;
    private boolean lenient = true;

    private List children = new ArrayList(); //a List of Words.
    private Word parent = null;
    private int pronomialStatus = LangData.P_NULL;
    private Word pronomial = null;
    private int desiredCollector = LangData.C_0;
    
    public void setGlyph(Morpheme value){
	this.glyph = value;
    }
    
    /**
       Sets the collector for this word.
       If this Word is not lenient, has no effect. (is superseded by addWord.)
     */
    public void setCollector (int value){
	if (isLenient()){
	    this.collector = value;
	}
    }

    public void setPronomialStatus (int value){
	this.pronomialStatus = value;
    }

    public void setDesiredCollector (int value){
	this.desiredCollector = value;
    }

    public void setRelation (int value){
	this.relation = value;
    }
    
    public void setForm (int value){
	this.form = value;
    }

    public void setPronomial (Word value){
	this.pronomial = value;
    }

    public Morpheme getGlyph(){
	return this.glyph;
    }
    
    public int getCollector (){
	return this.collector;
    }

    public int getPronomialStatus(){
	return this.pronomialStatus;
    }

    public int getFullCollector(){
	return this.collector + this.pronomialStatus;
    }

    public int getDesiredCollector(){
	return this.desiredCollector;
    }
    
    public int getRelation (){
	return this.relation;
    }
    
    public int getForm (){
	return this.form;
    }

    public Word getPronomial(){
	return this.pronomial;
    }

    public boolean hasChildren(){
	return (children.size() > 0);
    }
    
    public void setLenient(boolean value) throws GrammarException{
	if (children.size() > 0 || parent != null){
	    throw new GrammarException ("Cannot set lenient on a word in use.");
	} else {
	    this.lenient = value;
	}
    }
    
    public boolean isInUse(){
	return (children.size() > 0 || parent != null);
    }
    
    public boolean isLenient(){
	return lenient;
    }

    protected void setParent (Word value){
	this.parent = value;
    }

    public Word getParent(){
	return this.parent;
    }

    /**
       Adds a word to the list of children. If word is not lenient, does significant collector checking.
     */
    public void addChild(Word child) throws GrammarException{
	if (lenient){
	    children.add(child);
	} else {
	    boolean thisHas7Children = (children.size() >= 7);
	    boolean childIsLenient = (child.isLenient());
	    boolean childHasParent = (child.getParent() != null);
	    boolean childHasChildren = (child.getChildren().size() > 0);
	    if (childHasParent) {
		throw new GrammarException("Cannot add a child that already has another parent.");
	    }
	    if (childIsLenient){
		throw new GrammarException("Cannot add a lenient child to a non-lenient parent.");
	    }
	    if (thisHas7Children || this.collector == LangData.C_S){ 
		if (!mayBeScholastic || childHasChildren ){
		    throw new GrammarException("Cannot add children with children to a scholastic pronomial");
		} else {
		    children.add(child);
		    this.collector = LangData.C_S;
		}
	    } else { //add normally.
		if (childHasChildren){
		    mayBeScholastic = false;
		}
		children.add(child);
		this.collector++;
	    }
	}	
    }

    public void setScholastic(boolean value) throws GrammarException{
	if (value) {
	    if (!mayBeScholastic){
		throw new GrammarException("Cannot collect children with children with a scholastic pronomial");
	    } else {
		this.collector = LangData.C_S;
	    }
	} else {
	    if (children.size() > 7){
		throw new GrammarException("Cannot collect more than 7 children with a non-scholastic pronomial");
	    } else {
		this.collector = children.size(); 
	    }
	}
    }

    public List getChildren (){
	if (lenient){
	    return this.children;
	} else {
	    return Collections.unmodifiableList(this.children);
	}
    }

    public List getStatedForm(){
	List retList = new ArrayList();
	for (Iterator childrenIt = children.iterator(); childrenIt.hasNext();){
	    Word tempChild = (Word) childrenIt.next();
	    retList.addAll (tempChild.getStatedForm());
	}
	retList.add(this);
	return retList;	
    }
    
    public Word getEmptyForm(){
	Word retWord = new Word();
	try {
	    retWord.setLenient(false);
	} catch (GrammarException ge) {
	    //this should never happen, so we ignore it.
	}
	retWord.setGlyph(glyph);
	retWord.setRelation(relation);
	retWord.setForm(form);
	retWord.setCollector(collector);
	retWord.setPronomialStatus(pronomialStatus);
	return retWord;
    }
    

}
