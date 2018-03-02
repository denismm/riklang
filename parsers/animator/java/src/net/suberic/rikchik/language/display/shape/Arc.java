package net.suberic.rikchik.language.display.shape;

import net.suberic.rikchik.language.display.shape.AbstractShape;
import net.suberic.rikchik.language.display.shape.EllArc;
import net.suberic.rikchik.language.display.DisplayData;

public class Arc extends EllArc {

    public Arc (float x, float y, float radius, float startAngle, float endAngle){
	super(x,y,radius,radius,startAngle,endAngle);
    }
}
