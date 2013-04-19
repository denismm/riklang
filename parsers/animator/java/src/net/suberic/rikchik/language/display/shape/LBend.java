package net.suberic.rikchik.language.display.shape;

import net.suberic.rikchik.language.display.shape.AbstractShape;
import net.suberic.rikchik.language.display.shape.Bend;
import net.suberic.rikchik.language.display.shape.DoubleBend;
import net.suberic.rikchik.language.display.DisplayData;

public class LBend extends Bend{

    public LBend(float baseX, float baseY, 
		 float startLegAngle, float startLeg, 
		 float endLeg, boolean direction, boolean useBase){
	super (baseX, baseY, 0, 0,
	       startLegAngle, 90,
	       startLeg, endLeg, direction, useBase);
    }

    public LBend (float x, float y,
		 float startAngle, float endAngle,
		 float startLeg, float endLeg, boolean direction){
	super (x,y,0,0,
	       startAngle, endAngle, 
	       startLeg, endLeg, direction);
    }

    public DoubleBend toDoubleBend (EllArc dest){
	
	return new DoubleBend(baseX, baseY, startLegAngle, 0,
			      dest.getRadius(), dest.getRadius2(), 
			      dest.getDirection(), 0, startLeg,
			      0, 0, direction, 90, endLeg);
	
    }

}

