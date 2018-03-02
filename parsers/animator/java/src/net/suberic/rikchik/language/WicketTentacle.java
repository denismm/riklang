package net.suberic.rikchik.language;

import net.suberic.rikchik.language.AbstractTentacle;

public class WicketTentacle extends AbstractTentacle{

    protected int sortvalue = 6;

    public WicketTentacle () {}
    public WicketTentacle (float x, float y, float r1, float leg, float a1){
        this.x = x;
        this.y = y;
        this.r1 = r1;
        this.leg = leg;
        this.a1 = a1;
    }

    public String toXMLString() {
        StringBuffer retSB = new StringBuffer();
        retSB.append("<wicket ");
        retSB.append("x=\"" + this.x+"\" ");
        retSB.append("y=\"" + this.y+"\" ");
        retSB.append("r1=\"" + this.r1+"\" ");
        retSB.append("leg=\"" + this.leg+"\" ");
        retSB.append("a1=\"" + this.a1+"\" ");
        retSB.append("/>\n");
        return retSB.toString();
    }

}
