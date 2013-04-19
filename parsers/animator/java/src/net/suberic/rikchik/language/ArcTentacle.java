package net.suberic.rikchik.language;

import net.suberic.rikchik.language.AbstractTentacle;

public class ArcTentacle extends AbstractTentacle{

    protected int sortvalue = 4;

    public ArcTentacle () {}
    public ArcTentacle (float y, float r1, float a1, float a2){
        this.y = y;
        this.r1 = r1;
        this.a1 = a1;
        this.a2 = a2;
    }

    public String toXMLString() {
        StringBuffer retSB = new StringBuffer();
        retSB.append("<arc ");
        retSB.append("y=\"" + this.y+"\" ");
        retSB.append("r1=\"" + this.r1+"\" ");
        retSB.append("a1=\"" + this.a1+"\" ");
        retSB.append("a2=\"" + this.a2+"\" ");
        retSB.append("/>\n");
        return retSB.toString();
    }

}
