package net.suberic.rikchik.language;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import net.suberic.rikchik.language.LangData;
import net.suberic.rikchik.language.Morpheme;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.ArcTentacle;
import net.suberic.rikchik.language.CircleTentacle;
import net.suberic.rikchik.language.EllArcTentacle;
import net.suberic.rikchik.language.FishBendTentacle;
import net.suberic.rikchik.language.GreatArcTentacle;
import net.suberic.rikchik.language.HalfHookTentacle;
import net.suberic.rikchik.language.HookTentacle;
import net.suberic.rikchik.language.LBendTentacle;
import net.suberic.rikchik.language.LineTentacle;
import net.suberic.rikchik.language.LobeTentacle;
import net.suberic.rikchik.language.SquiggleTentacle;
import net.suberic.rikchik.language.WicketTentacle;
import net.suberic.rikchik.language.ZigZagTentacle;
import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.SAXException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LangFactory{

    private static LangFactory instance;

    private static String filename="../../riklang.xml";

    private HashMap glyphs = new HashMap();
    private Tentacle[] collectors = new Tentacle[LangData.COLLECTOR_NUM];
    private Tentacle[] relations = new Tentacle[LangData.RELATION_NUM];
    private Tentacle[] forms = new Tentacle[LangData.FORM_NUM];

    private LangFactory(String filename) throws SAXException, IOException{
	System.out.println ("beginning parsing.");
	//prepare to access inflections.
	HashMap inflections = new HashMap();
	inflections.put("collectors", collectors);
	inflections.put("relations", relations);
	inflections.put("aspects", forms);
    
	//read in XML file.
	DOMParser langParser = new DOMParser();
	langParser.parse (filename);
	Node langNode = langParser.getDocument().getDocumentElement();
	NodeList dictNodes = langNode.getChildNodes();
	for (int dictIndex = 0; 
	     dictIndex < dictNodes.getLength(); 
	     dictIndex++){ //for each dict node:
	    Node dictNode = dictNodes.item(dictIndex);
	    if (dictNode.getNodeType() != Node.ELEMENT_NODE) {
		continue;
	    }
	    String dictName = dictNode.getNodeName();
	    if (dictName.equals("morphemes")){ //if morphemes:
		NodeList morphemeNodes = dictNode.getChildNodes();
		for (int morphemeIndex = 0; 
		     morphemeIndex < morphemeNodes.getLength(); 
		     morphemeIndex++){ //for each morpheme:
		    Node morphemeNode = morphemeNodes.item(morphemeIndex);
		    if ((morphemeNode.getNodeType() != Node.ELEMENT_NODE) || 
			!(morphemeNode.getNodeName().equals("morpheme"))) {
			continue;
		    }
		    //create new Morpheme object.
		    Morpheme tempMorpheme = new Morpheme();
		    //add name.
		    String tempMorphemeName = 
			_getSpecificAttribute(morphemeNode, "name");
		    tempMorpheme.setName(tempMorphemeName);
		    
		    NodeList glyphNodes = morphemeNode.getChildNodes();
		    for (int glyphIndex = 0; 
			 glyphIndex < glyphNodes.getLength();
			 glyphIndex++){
			Node glyphNode = glyphNodes.item(glyphIndex);
			if ((glyphNode.getNodeType() != Node.ELEMENT_NODE) || 
			    !(glyphNode.getNodeName().equals ("glyph") )){
			    continue;
			}
			NodeList tentacleNodes = glyphNode.getChildNodes();
			int tentacleIndex = 0;
			for (int tentacleNodeIndex = 0; 
			     tentacleNodeIndex < tentacleNodes.getLength();
			     tentacleNodeIndex++){ //add tentacles to it.
			    Node tentacleNode = tentacleNodes.item
				(tentacleNodeIndex);
			    if ((tentacleNode.getNodeType() != Node.ELEMENT_NODE) ||
				(tentacleNode.getNodeName().equals("note"))){
				continue;
			    }
			    Tentacle tempTentacle = _createTentacle(tentacleNode);
			    if (tempTentacle == null) System.out.println ("hey! in "+tempMorphemeName);
			    tempMorpheme.setTentacle(tentacleIndex, tempTentacle);
			    tentacleIndex++;
			}
		    }
		    //place in glyphs HashMap.
		    glyphs.put (tempMorphemeName, tempMorpheme);
		}
		System.out.println("placed "+dictName);
	    } else if(dictName.equals ("collectors") || dictName.equals ("relations") || dictName.equals ("aspects")) { //if collectors, relations, or forms:
		Tentacle[] dictTA = (Tentacle[]) inflections.get(dictName);
		NodeList entryNodes = dictNode.getChildNodes();
		for (int entryIndex = 0;
		     entryIndex < entryNodes.getLength();
		     entryIndex++){ //for each entry:
		    Node entryNode = entryNodes.item(entryIndex);
		    if (entryNode.getNodeType() != Node.ELEMENT_NODE) {
			continue;
		    }
		    //get number.
		    String entryName = _getSpecificAttribute(entryNode, "name");
		    int entryNum = LangData.getInflectionValue(entryName);
		    NodeList glyphNodes = entryNode.getChildNodes();
		    for (int glyphIndex = 0; 
			 glyphIndex < glyphNodes.getLength();
			 glyphIndex++){
			Node glyphNode = glyphNodes.item(glyphIndex);
			if ((glyphNode.getNodeType() != Node.ELEMENT_NODE) || 
			    !(glyphNode.getNodeName().equals ("glyph") )){
			    continue;
			}
			NodeList tentacleNodes = glyphNode.getChildNodes();
			for (int tentacleNodeIndex = 0;
			     tentacleNodeIndex < tentacleNodes.getLength();
			     tentacleNodeIndex++){ //get tentacle node.
			    Node tentacleNode = 
				tentacleNodes.item(tentacleNodeIndex);
			    if ((tentacleNode.getNodeType() != Node.ELEMENT_NODE) ||
				((tentacleNode.getNodeName().equals("note")))) {
				continue;
			    }
			    //create new Tentacle object.
			    Tentacle tempTentacle = _createTentacle (tentacleNode);
			    //add to array.
			    dictTA[entryNum] = tempTentacle;
			    if (dictName.equals("collectors")){ //deal with pronomials.
				Tentacle pronomialTentacle = _createTentacle (tentacleNode);
				pronomialTentacle.setY(pronomialTentacle.getY()+20);
				pronomialTentacle.setY2(pronomialTentacle.getY2()+20);
				dictTA[entryNum+LangData.P_P] = pronomialTentacle;
				Tentacle optionPronomialTentacle = _createTentacle (tentacleNode);
				optionPronomialTentacle.setY(optionPronomialTentacle.getY()+40);
				optionPronomialTentacle.setY2(optionPronomialTentacle.getY2()+40);
				dictTA[entryNum+LangData.P_O] = optionPronomialTentacle;
			    }
			}
		    }
		}
		System.out.println("placed "+dictName);
	    }
	}
    }
    
    public static Tentacle getCollector (int id){
	return getInstance()._getCollector(id);
    }
    
    public static Tentacle getForm (int id){
	return getInstance()._getForm(id);
    }
    
    public static Tentacle getRelation (int id){
	return getInstance()._getRelation(id);
    }
    
    private Tentacle _getCollector (int id){
	return collectors[id];
    }
    
    private Tentacle _getForm (int id){
	return forms[id];
    }
    
    private Tentacle _getRelation (int id){
	return relations[id];
    }
    
    
    public static Morpheme getMorpheme(String name){
	return getInstance()._getMorpheme(name);
    }

    private Morpheme _getMorpheme(String name){
	return (Morpheme) glyphs.get(name);
    }

    public static Set getMorphemeNames (){
	return getInstance()._getMorphemeNames();
    }
    
    private Set _getMorphemeNames (){
	return glyphs.keySet();
    }

    private String _getSpecificAttribute (Node node, String name){
	return node.getAttributes().getNamedItem(name).getNodeValue();
    }

    private static Tentacle _createTentacle(Node node){
	//read in type.
	String type = node.getNodeName();
	//read attributes.
	NamedNodeMap attrs = node.getAttributes();
	//create new Tentacle.
	Tentacle retTentacle;
	if (type.equals(LangData.ARC_TENTACLE_NAME)){
	    retTentacle = new ArcTentacle();
	} else if (type.equals(LangData.CIRCLE_TENTACLE_NAME)){
	    retTentacle = new CircleTentacle();
	} else if (type.equals(LangData.ELLARC_TENTACLE_NAME)){
	    retTentacle = new EllArcTentacle();
	} else if (type.equals(LangData.FISHBEND_TENTACLE_NAME)){
	    retTentacle = new FishBendTentacle();
	} else if (type.equals(LangData.GREATARC_TENTACLE_NAME)){
	    retTentacle = new GreatArcTentacle();
	} else if (type.equals(LangData.HALFHOOK_TENTACLE_NAME)){
	    retTentacle = new HalfHookTentacle();
	} else if (type.equals(LangData.HOOK_TENTACLE_NAME)){
	    retTentacle = new HookTentacle();
	} else if (type.equals(LangData.LBEND_TENTACLE_NAME)){
	    retTentacle = new LBendTentacle();
	} else if (type.equals(LangData.LINE_TENTACLE_NAME)){
	    retTentacle = new LineTentacle();
	} else if (type.equals(LangData.LOBE_TENTACLE_NAME)){
	    retTentacle = new LobeTentacle();
	} else if (type.equals(LangData.SQUIGGLE_TENTACLE_NAME)){
	    retTentacle = new SquiggleTentacle();
	} else if (type.equals(LangData.WICKET_TENTACLE_NAME)){
	    retTentacle = new WicketTentacle();
	} else if (type.equals(LangData.ZIGZAG_TENTACLE_NAME)){
	    retTentacle = new ZigZagTentacle();
	} else {
	    retTentacle = null;
	    return retTentacle;
	}
	for (int i = 0; i < attrs.getLength(); i++){//set values.
	    Node attribute = attrs.item(i);
	    String name = attribute.getNodeName();
	    String valueStr = attribute.getNodeValue();
	    //System.out.println(name+":"+valueStr);
	    float valueFlt = _parseVal(valueStr);
	    retTentacle.set(name,valueFlt);
	}
	
	return retTentacle;
    }

    /** recursive function to convert values to floats. */
    private static float _parseVal (String valueStr){
	int slashIndex = valueStr.indexOf('/');
	if (slashIndex > 0){ // (A / B)
	    return (_parseVal(valueStr.substring(0,slashIndex)) 
		    / _parseVal(valueStr.substring(slashIndex+1)));
	} else {
	    int rIndex = valueStr.indexOf('r');
	    if (rIndex > 0) { // ( A * sqrt (B))
		return (_parseVal(valueStr.substring(0,rIndex))
			* (float)Math.sqrt(_parseVal(valueStr.substring(rIndex+1))));
	    } else {
		int sIndex = valueStr.indexOf('s');
		if (sIndex > 0) { // (A * ((B-sqrt(2))/2))
		    return (_parseVal(valueStr.substring(0,sIndex))
			    *((_parseVal(valueStr.substring(sIndex+1))
			       - (float)Math.sqrt(2) ) 
			      / 2));
		} else {
		    try {
			return Float.parseFloat(valueStr);
		    } catch (NumberFormatException e) {
			return 0;
		    }
		}
	    }
	}
    }

    public static LangFactory getInstance(){
	return getInstance(filename);
    } 

    public static LangFactory getInstance(String filename){
	try {
	    if (instance == null){
		instance = new LangFactory(filename);
	    }
	} catch (SAXException se){
	    se.printStackTrace();
	    //something?
	} catch (IOException ioe){
	    ioe.printStackTrace();
	    //something else?
	}
	return instance;
    }

    public static Word getWord(String wordString){
	return getInstance()._getWord(wordString);
    }
    
    private Word _getWord(String wordString){
	Word retWord = new Word();
	//Must be in form "Hat-End-N-0".
	int index1 = wordString.indexOf('-');
	int index2 = wordString.indexOf('-',index1+1);
	int index3 = wordString.indexOf('-',index2+1);
	String glyphString = wordString.substring(0,index1);
	String relationString  = wordString.substring(index1+1,index2);
	String formString = wordString.substring(index2+1,index3);
	String collectorString = wordString.substring(index3+1,wordString.length());
	retWord.setGlyph(_getMorpheme(glyphString));
	retWord.setRelation(LangData.getInflectionValue(relationString));
	retWord.setForm(LangData.getInflectionValue(formString));
	if (collectorString.length() >= 2){
	    switch (collectorString.charAt(1)){
	    case 'P':
		retWord.setPronomialStatus(LangData.P_P);
		break;
	    case 'O':
		retWord.setPronomialStatus(LangData.P_O);
		break;
	    }
	    collectorString = collectorString.substring(0,1);
	}
	retWord.setCollector(LangData.getInflectionValue(collectorString));
	
	return retWord;
    }
    
}
