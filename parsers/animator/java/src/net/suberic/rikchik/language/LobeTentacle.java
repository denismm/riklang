package net.suberic.rikchik.language;

import net.suberic.rikchik.language.AbstractTentacle;

public class LobeTentacle extends AbstractTentacle{

    protected int sortvalue = 12;

    public LobeTentacle () {}
    public LobeTentacle (float x, float y, float r1, float r2, float a1, float d){
        this.x = x;
        this.y = y;
        this.r1 = r1;
        this.r2 = r2;
        this.a1 = a1;
        this.d = d;
    }

    public String toXMLString() {
        StringBuffer retSB = new StringBuffer();
        retSB.append("<lobe ");
        retSB.append("x=\"" + this.x+"\" ");
        retSB.append("y=\"" + this.y+"\" ");
        retSB.append("r1=\"" + this.r1+"\" ");
        retSB.append("r2=\"" + this.r2+"\" ");
        retSB.append("a1=\"" + this.a1+"\" ");
        retSB.append("d=\"" + this.d+"\" ");
        retSB.append("/>\n");
        return retSB.toString();
    }

}
