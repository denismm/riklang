package net.suberic.rikchik.language.display.shape;

import net.suberic.rikchik.language.display.shape.AbstractShape;
import net.suberic.rikchik.language.display.DisplayData;

public class DoubleBend extends AbstractShape{

    
    protected float baseX;
    protected float baseY;
    protected float startLegAngle;
    protected float startLeg;
    protected float arc1Radius;
    protected float arc1Radius2;
    protected boolean arc1Direction; //isCounterClockwise
    protected float arc1Angle;
    protected float midLeg;
    protected float arc2Radius;
    protected float arc2Radius2;
    protected boolean arc2Direction; //isCounterClockwise
    protected float arc2Angle;
    protected float endLeg;

    public DoubleBend (float baseX, float baseY,
		       float startLegAngle, float startLeg,
		       float arc1Radius, float arc1Radius2,
		       boolean arc1Direction, float arc1Angle,
		       float midLeg,
		       float arc2Radius, float arc2Radius2,
		       boolean arc2Direction, float arc2Angle,
		       float endLeg){
	this.baseX = baseX;
	this.baseY = baseY;
	this.startLegAngle = startLegAngle;
	this.startLeg = startLeg;
	this.arc1Radius = arc1Radius;
	this.arc1Radius2 = arc1Radius2;
	this.arc1Direction = arc1Direction;
	this.arc1Angle = arc1Angle;
	this.midLeg = midLeg;
	this.arc2Radius = arc2Radius;
	this.arc2Radius2 = arc2Radius2;
	this.arc2Direction = arc2Direction;
	this.arc2Angle = arc2Angle;
	this.endLeg = endLeg;
    }
	
    public static DoubleBend createMidDoubleBend
	(DoubleBend origin, DoubleBend destination, 
	 boolean useOriginDirections){
	float orBaseX = origin.getBaseX();
	float orBaseY = origin.getBaseY();
	float orStartLegAngle = origin.getStartLegAngle();
	float orStartLeg = origin.getStartLeg();
	float orArc1Radius = origin.getArc1Radius();
	float orArc1Radius2 = origin.getArc1Radius2();
	boolean orArc1Direction = origin.getArc1Direction();
	float orArc1Angle = origin.getArc1Angle();
	float orMidLeg = origin.getMidLeg();
	float orArc2Radius = origin.getArc2Radius();
	float orArc2Radius2 = origin.getArc2Radius2();
	boolean orArc2Direction = origin.getArc2Direction();
	float orArc2Angle = origin.getArc2Angle();
	float orEndLeg = origin.getEndLeg();

	float deBaseX = destination.getBaseX();
	float deBaseY = destination.getBaseY();
	float deStartLegAngle = destination.getStartLegAngle();
	float deStartLeg = destination.getStartLeg();
	float deArc1Radius = destination.getArc1Radius();
	float deArc1Radius2 = destination.getArc1Radius2();
	boolean deArc1Direction = destination.getArc1Direction();
	float deArc1Angle = destination.getArc1Angle();
	float deMidLeg = destination.getMidLeg();
	float deArc2Radius = destination.getArc2Radius();
	float deArc2Radius2 = destination.getArc2Radius2();
	boolean deArc2Direction = destination.getArc2Direction();
	float deArc2Angle = destination.getArc2Angle();
	float deEndLeg = destination.getEndLeg();

	float newBaseX = (orBaseX + deBaseX) / 2;
	float newBaseY = (orBaseY + deBaseY) / 2;
	if ((deStartLegAngle - orStartLegAngle) > 180){ //go the short way!
	    deStartLegAngle -= 360;
	} else if ((deStartLegAngle - orStartLegAngle) < -180){ //go the short way!
	    deStartLegAngle += 360;
	}
	float newStartLegAngle = (orStartLegAngle + deStartLegAngle) / 2;
	float newStartLeg;
	float newArc1Radius;
	float newArc1Radius2;
	boolean newArc1Direction;
	float newArc1Angle;
	if (orArc1Direction == deArc1Direction){
	    newStartLeg = (orStartLeg + deStartLeg) / 2;
	    newArc1Radius = (orArc1Radius + deArc1Radius) / 2;
	    newArc1Radius2 = (orArc1Radius2 + deArc1Radius2) / 2;
	    newArc1Direction = orArc1Direction;
	    newArc1Angle = (orArc1Angle + deArc1Angle) / 2;
	} else {
	    float orStartLength = DisplayData.lengthOfBend
		(orArc1Radius, orArc1Radius2, orArc1Angle, orStartLeg, 0);
	    float deStartLength = DisplayData.lengthOfBend
		(deArc1Radius, deArc1Radius2, deArc1Angle, deStartLeg, 0);
	    newStartLeg = (orStartLength + deStartLength) / 2;
	    if (useOriginDirections){
		newArc1Radius = orArc1Radius;
		newArc1Radius2 = orArc1Radius2;
		newArc1Direction = orArc1Direction;
	    } else {
		newArc1Radius = deArc1Radius;
		newArc1Radius2 = deArc1Radius2;
		newArc1Direction = deArc1Direction;
	    }
	    newArc1Angle = 0;
	}       
	float newMidLeg;
	float newArc2Radius;
	float newArc2Radius2;
	boolean newArc2Direction;
	float newArc2Angle;
	if (orArc2Direction == deArc2Direction){
	    newMidLeg = (orMidLeg + deMidLeg) / 2;
	    newArc2Radius = (orArc2Radius + deArc2Radius) / 2;
	    newArc2Radius2 = (orArc2Radius2 + deArc2Radius2) / 2;
	    newArc2Direction = orArc2Direction;
	    newArc2Angle = (orArc2Angle + deArc2Angle) / 2;
	} else {
	    float orMidLength = DisplayData.lengthOfBend
		(orArc2Radius, orArc2Radius2, orArc2Angle, orMidLeg, 0);
	    float deMidLength = DisplayData.lengthOfBend
		(deArc2Radius, deArc2Radius2, deArc2Angle, deMidLeg, 0);
	    newMidLeg = (orMidLength + deMidLength) / 2;
	    if (useOriginDirections){
		newArc2Radius = orArc2Radius;
		newArc2Radius2 = orArc2Radius2;
		newArc2Direction = orArc2Direction;
	    } else {
		newArc2Radius = deArc2Radius;
		newArc2Radius2 = deArc2Radius2;
		newArc2Direction = deArc2Direction;
	    }
	    newArc2Angle = 0;
	}       
	float newEndLeg = (orEndLeg + deEndLeg) / 2;

	return new DoubleBend
	    (newBaseX, newBaseY, newStartLegAngle, newStartLeg,
	     newArc1Radius, newArc1Radius2,
	     newArc1Direction, newArc1Angle, newMidLeg,
	     newArc2Radius, newArc2Radius2, 
	     newArc2Direction, newArc2Angle, newEndLeg);
	
    }
   
    public float getBaseX(){
	return this.baseX;
    }

    public float getBaseY(){
	return this.baseY;
    }

    public float getStartLegAngle(){
	return this.startLegAngle;
    }

    public float getStartLeg(){
	return this.startLeg;
    }

    public float getArc1Radius(){
	return this.arc1Radius;
    }

    public float getArc1Radius2(){
	return this.arc1Radius2;
    }

    public boolean getArc1Direction(){
	return this.arc1Direction;
    }

    public float getArc1Angle(){
	return this.arc1Angle;
    }

    public float getMidLeg(){
	return this.midLeg;
    }

    public float getArc2Radius(){
	return this.arc2Radius;
    }

    public float getArc2Radius2(){
	return this.arc2Radius2;
    }

    public boolean getArc2Direction(){
	return this.arc2Direction;
    }

    public float getArc2Angle(){
	return this.arc2Angle;
    }

    public float getEndLeg(){
	return this.endLeg;
    }



}
