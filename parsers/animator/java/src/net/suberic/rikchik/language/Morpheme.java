package net.suberic.rikchik.language;

import net.suberic.rikchik.language.Tentacle;

public class Morpheme{

    private Tentacle[] tentacles = new Tentacle[4];

    private String name;

    public void setName (String value){
	this.name = value;
    }

    public String getName () {
	return this.name;
    }

    public void setTentacle (int i, Tentacle value){
	if (i < tentacles.length && i >= 0){
	    if (value == null){
		System.out.println ("hey at Morpheme "+ this.name+" t"+i);
	    } else {
		this.tentacles [i] = value;
	    }
	}
    }

    public Tentacle getTentacle(int i){
	if (i < tentacles.length && i >= 0){
	    return this.tentacles[i];
	} else {
	    return null;
	}
    }

    public String toXMLString (){
	StringBuffer retSB = new StringBuffer();
	retSB.append ("<entry name=\"").append (this.name).append("\">\n");
	for (int i = 0; i < tentacles.length; i++){
	    Tentacle tempTentacle = tentacles[i];
	    if (tempTentacle == null) {
		System.out.println ("hey at XML Morpheme "+ this.name+" t"+i); 
	    } else {
		String tentacleXML = tempTentacle.toXMLString();
		retSB.append(tentacleXML);
	    }
	}
	retSB.append ("</entry>\n");
	return retSB.toString();
    }

}
