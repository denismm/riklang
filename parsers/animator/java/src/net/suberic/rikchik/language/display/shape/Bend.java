package net.suberic.rikchik.language.display.shape;

import net.suberic.rikchik.language.display.shape.AbstractShape;
import net.suberic.rikchik.language.display.shape.DoubleBend;
import net.suberic.rikchik.language.display.DisplayData;

public class Bend extends AbstractShape{

    protected float x; 
    protected float y;
    protected float radius; 
    protected float radius2; 
    protected float startAngle; 
    protected float endAngle;
    protected float startLeg; 
    protected float endLeg; 
    protected boolean direction; //isCounterClockwise
    
    protected float baseX;
    protected float baseY;
    protected float startLegAngle;
    protected float arcAngle;
    protected float endX;
    protected float endY;
    
    /** base-to-angle version. */
    public Bend (float baseX, float baseY, float radius, float radius2,
		 float startLegAngle, float arcAngle,
		 float startLeg, float endLeg, boolean direction, boolean useBase){
	
	this.baseX = baseX;
	this.baseY = baseY;
	this.radius = radius; 
	this.radius2 = radius2;
	this.startLegAngle = startLegAngle;
	this.arcAngle = arcAngle;
	this.startLeg = startLeg; 
	this.endLeg = endLeg; 
	this.direction = direction;


	float arcStartX = DisplayData.xFromPoint(baseX, startLegAngle, startLeg);
	float arcStartY = DisplayData.yFromPoint(baseY, startLegAngle, startLeg);
	if (direction){
	    this.startAngle = DisplayData.convertAngle
		(startLegAngle - 90, radius2, radius);
	    this.x = DisplayData.xFromPoint(arcStartX, startAngle, -radius);
	    this.y = DisplayData.yFromPoint(arcStartY, startAngle, -radius2);
	    float endLegAngle = startAngle + arcAngle;
	    this.endAngle = DisplayData.convertAngle
		(endLegAngle - 90, radius2, radius);
	    float arcEndX = DisplayData.xFromPoint(x, endAngle, radius);
	    float arcEndY = DisplayData.yFromPoint(y, endAngle, radius2);
	    this.endX = DisplayData.xFromPoint(arcEndX, endLegAngle, endLeg);
	    this.endY = DisplayData.yFromPoint(arcEndY, endLegAngle, endLeg);
	} else {
	    this.startAngle = DisplayData.convertAngle
		(startLegAngle + 90, radius2, radius);
	    this.x = DisplayData.xFromPoint(arcStartX, startAngle, -radius);
	    this.y = DisplayData.yFromPoint(arcStartY, startAngle, -radius2);
	    float endLegAngle = startAngle - arcAngle;
	    this.endAngle = DisplayData.convertAngle
		(endLegAngle + 90, radius2, radius);
	    float arcEndX = DisplayData.xFromPoint(x, endAngle, radius);
	    float arcEndY = DisplayData.yFromPoint(y, endAngle, radius2);
	    this.endX = DisplayData.xFromPoint(arcEndX, endLegAngle, endLeg);
	    this.endY = DisplayData.yFromPoint(arcEndY, endLegAngle, endLeg);
	}

    }
		 


    /** elliptical version */
    public Bend (float x, float y, float radius, float radius2,
		 float startAngle, float endAngle,
		 float startLeg, float endLeg, boolean direction){
	
	this.x = x; 
	this.y = y;
	this.radius = radius; 
	this.radius2 = radius2; 
	this.startAngle = startAngle; 
	this.endAngle = endAngle;
	this.startLeg = startLeg; 
	this.endLeg = endLeg; 
	this.direction = direction;

	float arcStartX = DisplayData.xFromPoint(x, startAngle, radius);
	float arcStartY = DisplayData.yFromPoint(y, startAngle, radius2);
	if (direction){
	    this.startLegAngle = DisplayData.convertAngle
		(startAngle + 90, radius, radius2);
	    this.baseX = DisplayData.xFromPoint
		(arcStartX, startLegAngle, -startLeg); 
	    this.baseY = DisplayData.yFromPoint
		(arcStartY, startLegAngle, -startLeg);
	    float endLegAngle = DisplayData.convertAngle
		(endAngle + 90, radius, radius2);
	    this.arcAngle = endLegAngle - startLegAngle;
	    while (arcAngle < 0){
		arcAngle += 360;
	    }
	    while (arcAngle > 360) {
		arcAngle -= 360;
	    }
	    float arcEndX = DisplayData.xFromPoint(x, endAngle, radius);
	    float arcEndY = DisplayData.yFromPoint(y, endAngle, radius2);
	    this.endX = DisplayData.xFromPoint(arcEndX, endLegAngle, endLeg);
	    this.endY = DisplayData.yFromPoint(arcEndY, endLegAngle, endLeg);
	} else {
	    this.startLegAngle = DisplayData.convertAngle
		(startAngle - 90, radius, radius2);
	    this.baseX = DisplayData.xFromPoint
		(arcStartX, startLegAngle, -startLeg); 
	    this.baseY = DisplayData.yFromPoint
		(arcStartY, startLegAngle, -startLeg); 
	    float endLegAngle = DisplayData.convertAngle
		(endAngle - 90, radius, radius2);
	    this.arcAngle = startLegAngle - endLegAngle;
	    while (arcAngle < 0){
		arcAngle += 360;
	    }
	    while (arcAngle > 360) {
		arcAngle -= 360;
	    }
	    float arcEndX = DisplayData.xFromPoint(x, endAngle, radius);
	    float arcEndY = DisplayData.yFromPoint(y, endAngle, radius2);
	    this.endX = DisplayData.xFromPoint(arcEndX, endLegAngle, endLeg);
	    this.endY = DisplayData.yFromPoint(arcEndY, endLegAngle, endLeg);
	}

    }

    /** circular version */
    public Bend (float x, float y, float radius,
		 float startAngle, float endAngle,
		 float startLeg, float endLeg, boolean direction){
	
	this(x,y,radius,radius,startAngle,endAngle,startLeg,endLeg,direction);
    }
    
    public DoubleBend toDoubleBend(DoubleBend dest){
	float deMidLeg = dest.getMidLeg();
	float deEndLeg = dest.getEndLeg();
	float deArc2Angle = dest.getArc2Angle();
	float deArc2Radius = dest.getArc2Radius();
	float deArc2Radius2 = dest.getArc2Radius2();
	float deEndLength = DisplayData.lengthOfBend(deArc2Radius, deArc2Radius2, deArc2Angle, deMidLeg, deEndLeg);
	float legRatio = deEndLength > 0 ? (deEndLeg / deEndLength) : 0;
	float newEndLeg = endLeg * legRatio;
	float newMidLeg = endLeg  - newEndLeg;
	
	return new DoubleBend(baseX, baseY, startLegAngle, startLeg, radius, radius2, direction, arcAngle, newMidLeg, deArc2Radius, deArc2Radius2, dest.getArc2Direction(), 0, newEndLeg);
	
    }

    public float getX(){
	return this.x;
    }
 
    public float getY(){
	return this.y;
    }

    public float getRadius(){
	return this.radius;
    }
 
    public float getRadius2(){
	return this.radius2;
    }
 
    public float getStartAngle(){
	return this.startAngle;
    }
 
    public float getEndAngle(){
	return this.endAngle;
    }

    public float getStartLeg(){
	return this.startLeg;
    }
 
    public float getEndLeg(){
	return this.endLeg;
    }
 
    public boolean getDirection(){
	return this.direction;
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

    public float getArcAngle(){
	return this.arcAngle;
    }

    public float getEndX(){
	return this.endX;
    }
   
    public float getEndY(){
	return this.endY;
    }
   
	

}
