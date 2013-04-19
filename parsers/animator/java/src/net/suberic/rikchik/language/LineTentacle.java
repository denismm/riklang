package net.suberic.rikchik.language;

import net.suberic.rikchik.language.AbstractTentacle;

public class LineTentacle extends AbstractTentacle{

    protected int sortvalue = 1;

    public LineTentacle () {}
    public LineTentacle (float x, float y, float x2, float y2){
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
    }

    public String toXMLString() {
        StringBuffer retSB = new StringBuffer();
        retSB.append("<line ");
        retSB.append("x=\"" + this.x+"\" ");
        retSB.append("y=\"" + this.y+"\" ");
        retSB.append("x2=\"" + this.x2+"\" ");
        retSB.append("y2=\"" + this.y2+"\" ");
        retSB.append("/>\n");
        return retSB.toString();
    }

}
