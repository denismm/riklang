package net.suberic.rikchik.language.display.shape;
 
import net.suberic.rikchik.language.display.shape.AbstractShape;
import net.suberic.rikchik.language.display.shape.Bend;
import net.suberic.rikchik.language.display.shape.DoubleBend;
import net.suberic.rikchik.language.display.DisplayData;

public class Line extends AbstractShape{

    protected float x;
    protected float y;
    protected float x2;
    protected float y2;
    protected float degrees;
    protected float length;

    public float getBaseX(){
	return this.x;
    }
    
    public float getBaseY(){
	return this.y;
    }
    
    public float getEndX(){
	return this.x2;
    }
    
    public float getEndY(){
	return this.y2;
    }
    
    public Line (float x, float y, float x2, float y2){
	this.x = x;
	this.y = y;
	this.x2 = x2;
	this.y2 = y2;
	this.degrees = DisplayData.degreesBetweenPoints(x,y,x2,y2);
	this.length = DisplayData.distanceBetweenPoints(x,y,x2,y2);
    }


    /**
       boolean is ignored, used to differentiate constructors.
     */
    public Line (float x, float y, 
		 float degrees, float length, boolean isLine){
	this.x = x;
	this.y = y;
	this.degrees = degrees;
	this.length = length;
	this.x2 = DisplayData.xFromPoint(x,degrees,length);
	this.y2 = DisplayData.yFromPoint(y,degrees,length);
    }


    public Bend toBend(Bend dest){
	float radius = dest.getRadius();
	float radius2 = dest.getRadius2();
	boolean direction = dest.getDirection();
	float startLeg = dest.getStartLeg();
	float endLeg = dest.getEndLeg();
	float legRatio;
	if (startLeg + endLeg > 0) {
	    legRatio = startLeg / (startLeg + endLeg);
	} else {
	    legRatio = 0;
	}
	float newStartLeg = length * legRatio;
	float newEndLeg = length - newStartLeg;
	float newMidX = DisplayData.xFromPoint(x,degrees,newStartLeg);
	float newMidY = DisplayData.yFromPoint(y,degrees,newStartLeg);
	float newLineAngle = DisplayData.convertAngle(degrees,radius2,radius);
	float newArcAngle;
	if (direction){
	    newArcAngle = newLineAngle - 90;
	} else {
	    newArcAngle = newLineAngle + 90;
	}
	float arcX = DisplayData.xFromPoint(newMidX, newArcAngle, -radius);
	float arcY = DisplayData.yFromPoint(newMidY, newArcAngle, -radius2);

	return new Bend (arcX, arcY, radius, radius2, 
			 newArcAngle, newArcAngle, 
			 newStartLeg, newEndLeg, direction);
    }

    public DoubleBend toDoubleBend(DoubleBend dest){
	float deStartLeg = dest.getStartLeg();
	float deMidLeg = dest.getMidLeg();
	float deEndLeg = dest.getEndLeg();
	float deArc1Angle = dest.getArc1Angle();
	float deArc1Radius = dest.getArc1Radius();
	float deArc1Radius2 = dest.getArc1Radius2();
	float deStartLength = DisplayData.lengthOfBend(deArc1Radius, deArc1Radius2, deArc1Angle, deStartLeg, 0);
	float deArc2Angle = dest.getArc2Angle();
	float deArc2Radius = dest.getArc2Radius();
	float deArc2Radius2 = dest.getArc2Radius2();
	float deMidLength = DisplayData.lengthOfBend(deArc2Radius, deArc2Radius2, deArc2Angle, 0, deEndLeg);
	float deLength = deStartLength + deMidLength + deEndLeg;
	float startLegRatio = deLength > 0 ? (deStartLength / deLength) : 0;
	float midLegRatio = deLength > 0 ? (deMidLength / deLength) : 0;
	float newStartLeg = length * startLegRatio;
	float newMidLeg = length * midLegRatio;
	float newEndLeg = length  - (newMidLeg + newStartLeg) ;
	
	return new DoubleBend(x, y, degrees, newStartLeg, deArc1Radius, deArc1Radius2, dest.getArc1Direction(), 0, newMidLeg, deArc2Radius, deArc2Radius2, dest.getArc2Direction(), 0, newEndLeg);

    }

    public float getX(){
	return this.x;
    }

    public float getY(){
	return this.y;
    }

    public float getX2(){
	return this.x2;
    }

    public float getY2(){
	return this.y2;
    }

    public float getDegrees(){
	return this.degrees;
    }

    public float getLength(){
	return this.length;
    }



}
