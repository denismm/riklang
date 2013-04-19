package net.suberic.rikchik.language;

import net.suberic.rikchik.language.AbstractTentacle;

public class HookTentacle extends AbstractTentacle{

    protected int sortvalue = 7;

    public HookTentacle () {}
    public HookTentacle (float x, float y, float r1, float leg, float a1, float d){
        this.x = x;
        this.y = y;
        this.r1 = r1;
        this.leg = leg;
        this.a1 = a1;
        this.d = d;
    }

    public String toXMLString() {
        StringBuffer retSB = new StringBuffer();
        retSB.append("<hook ");
        retSB.append("x=\"" + this.x+"\" ");
        retSB.append("y=\"" + this.y+"\" ");
        retSB.append("r1=\"" + this.r1+"\" ");
        retSB.append("leg=\"" + this.leg+"\" ");
        retSB.append("a1=\"" + this.a1+"\" ");
        retSB.append("d=\"" + this.d+"\" ");
        retSB.append("/>\n");
        return retSB.toString();
    }

}
