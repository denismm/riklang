package net.suberic.rikchik.language;

import net.suberic.rikchik.language.AbstractTentacle;

public class SquiggleTentacle extends AbstractTentacle{

    protected int sortvalue = 2;

    public SquiggleTentacle () {}
    public SquiggleTentacle (float x, float y, float x2, float y2, float r1, float d){
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        this.r1 = r1;
        this.d = d;
    }

    public String toXMLString() {
        StringBuffer retSB = new StringBuffer();
        retSB.append("<squiggle ");
        retSB.append("x=\"" + this.x+"\" ");
        retSB.append("y=\"" + this.y+"\" ");
        retSB.append("x2=\"" + this.x2+"\" ");
        retSB.append("y2=\"" + this.y2+"\" ");
        retSB.append("r1=\"" + this.r1+"\" ");
        retSB.append("d=\"" + this.d+"\" ");
        retSB.append("/>\n");
        return retSB.toString();
    }

}
