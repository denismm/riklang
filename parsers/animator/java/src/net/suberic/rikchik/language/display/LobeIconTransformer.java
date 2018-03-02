package net.suberic.rikchik.language.display;

import java.awt.Component;
import java.awt.Graphics;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.display.shape.Shape;
import net.suberic.rikchik.language.display.shape.DoubleBend;

public class LobeIconTransformer{

    public static Shape getShape(Tentacle tentacle){

	float centerX = tentacle.getX();
	float centerY = -(tentacle.getY());
	float startLegAngle = tentacle.getA1() + 180;
	boolean isHoriz = (startLegAngle % 180 == 0); 
	float arc1Radius = tentacle.getR1();
	float arc1Radius2 = arc1Radius;
	float arc2Radius;
	float arc2Radius2;
	float arc1Angle = 180;
	float arc2Angle = 90;
	boolean arc1Direction = (tentacle.getD() != 0);
	boolean arc2Direction = arc1Direction;
	float baseX;
	float baseY;
	if (arc1Direction){
	    baseX = DisplayData.xFromPoint(centerX, startLegAngle - 90, arc1Radius); 
	    baseY = DisplayData.yFromPoint(centerY, startLegAngle - 90, arc1Radius2); 
	} else {
	    baseX = DisplayData.xFromPoint(centerX, startLegAngle + 90, arc1Radius);
	    baseY = DisplayData.yFromPoint(centerY, startLegAngle + 90, arc1Radius2); 
	}
	if (isHoriz){
	    arc2Radius = tentacle.getR2();
	    arc2Radius2 = arc1Radius * 2;
	} else {
	    arc2Radius = arc1Radius * 2;
	    arc2Radius2 = tentacle.getR2();
	}
	
	return new DoubleBend( baseX, baseY, startLegAngle, 0,
			       arc1Radius, arc1Radius2,
			       arc1Direction, arc1Angle, 0,
			       arc2Radius, arc2Radius2,
			       arc2Direction, arc2Angle, 0);	
    }
}
