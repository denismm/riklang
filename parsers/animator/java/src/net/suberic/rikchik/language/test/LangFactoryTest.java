package net.suberic.rikchik.language.test;

import java.util.Set;
import java.util.Iterator;
import net.suberic.rikchik.language.LangData;
import net.suberic.rikchik.language.LangFactory;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.Morpheme;

public class LangFactoryTest {

    public static void main (String[] args) {
	String[] aspects = {"R","T","V","M","N","P","I"};
	String[] relations = {"agent","source","instrument","quality","destination","patient","includes","task","result","example","means","actor","element","end"};
	String[] collectors = {"0","1","2","3","4","5","6","7","S"};
	/*
	  Set names = LangFactory.getMorphemeNames();
	  for (Iterator nameIt = names.iterator(); nameIt.hasNext(); ){
	  Morpheme tempMorpheme = LangFactory.getMorpheme((String)nameIt.next());
	  System.out.println (tempMorpheme.toXMLString());
	  }
	*/
	Tentacle tempTentacle;
	int tempData;
	for (int i = 0; i < aspects.length; i++){
	    tempData = LangData.getInflectionValue(aspects[i]);
	    tempTentacle = LangFactory.getForm(tempData);
	    System.out.println (aspects[i]+":"+tempTentacle.toXMLString());
	}

    }
}
