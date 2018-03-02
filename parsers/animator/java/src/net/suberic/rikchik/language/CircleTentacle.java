package net.suberic.rikchik.language;

import net.suberic.rikchik.language.AbstractTentacle;

public class CircleTentacle extends AbstractTentacle{

    protected int sortvalue = 0;

    public CircleTentacle () {}
    public CircleTentacle (float x, float y, float r1){
        this.x = x;
        this.y = y;
        this.r1 = r1;
    }

    public String toXMLString() {
        StringBuffer retSB = new StringBuffer();
        retSB.append("<circle ");
        retSB.append("x=\"" + this.x+"\" ");
        retSB.append("y=\"" + this.y+"\" ");
        retSB.append("r1=\"" + this.r1+"\" ");
        retSB.append("/>\n");
        return retSB.toString();
    }

}
