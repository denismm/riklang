package net.suberic.rikchik.language.display;

import java.awt.Component;
import java.awt.Graphics;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.display.shape.Shape;
import net.suberic.rikchik.language.display.shape.DoubleBend;

public class ZigZagIconTransformer{

    public static Shape getShape(Tentacle tentacle){
	float baseX = tentacle.getX();
	float baseY = -(tentacle.getY());
	float endX = tentacle.getX2();
	float endY = -(tentacle.getY2());
	boolean direction = (tentacle.getD() != 0); //horizFirst.
	float xDist = Math.abs(baseX-endX);
	float yDist = Math.abs(baseY-endY);


	float startLeg;
	float midLeg;

	float startLegAngle;
	boolean arc1Direction = 
	    !((direction) ^ (baseX > endX) ^ (baseY > endY));

	if (direction) {
	    startLeg = xDist / 2;
	    midLeg = yDist;
	    if (baseX < endX){
		startLegAngle = 0;
	    } else {
		startLegAngle = 180;
	    }
	} else {
	    startLeg = yDist / 2;
	    midLeg = xDist;
	    if (baseY < endY){
		startLegAngle = 270;
	    } else {
		startLegAngle = 90;
	    }
	}

	float endLeg = startLeg;
	boolean arc2Direction = !arc1Direction;
	float arc1Radius = 0;
	float arc1Radius2 = arc1Radius;
	float arc2Radius = arc1Radius;
	float arc2Radius2 = arc1Radius;
	float arc1Angle = 90;
	float arc2Angle = 90;

	return new DoubleBend( baseX, baseY, startLegAngle, 
			       startLeg,0,0,
			       arc1Direction, arc1Angle, 
			       midLeg,0,0,
			       arc2Direction, arc2Angle, endLeg);	

    }
}



