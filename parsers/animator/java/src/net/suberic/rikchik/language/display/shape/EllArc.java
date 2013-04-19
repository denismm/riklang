package net.suberic.rikchik.language.display.shape;

import net.suberic.rikchik.language.display.shape.AbstractShape;
import net.suberic.rikchik.language.display.shape.Bend;
import net.suberic.rikchik.language.display.shape.DoubleBend;
import net.suberic.rikchik.language.display.DisplayData;

public class EllArc extends Bend{
    
    public EllArc (float x, float y, float radius, float radius2, 
		   float startAngle, float endAngle){
	super (x,y,radius,radius2,startAngle,endAngle,0,0,true);
    }
    
    public DoubleBend toDoubleBend(DoubleBend dest){
	float deArc2Angle = dest.getArc2Angle();
	float deArc2Radius = dest.getArc2Radius();
	float deArc2Radius2 = dest.getArc2Radius2();
	
	return new DoubleBend(baseX, baseY, startLegAngle, 0, radius, radius2, true, arcAngle, 0, deArc2Radius, deArc2Radius2, dest.getArc2Direction(), 0, 0);
	
    }
    
}
