package net.suberic.rikchik.language;

import net.suberic.rikchik.language.AbstractTentacle;

public class GreatArcTentacle extends AbstractTentacle{

    protected int sortvalue = 5;

    public GreatArcTentacle () {}
    public GreatArcTentacle (float a1, float a2){
        this.a1 = a1;
        this.a2 = a2;
    }

    public String toXMLString() {
        StringBuffer retSB = new StringBuffer();
        retSB.append("<greatarc ");
        retSB.append("a1=\"" + this.a1+"\" ");
        retSB.append("a2=\"" + this.a2+"\" ");
        retSB.append("/>\n");
        return retSB.toString();
    }

}
