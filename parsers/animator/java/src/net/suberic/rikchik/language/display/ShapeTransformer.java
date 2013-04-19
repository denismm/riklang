package net.suberic.rikchik.language.display;

import java.awt.Component;
import java.awt.Graphics;
import net.suberic.rikchik.language.display.DisplayData;
import net.suberic.rikchik.language.display.shape.Shape;
import net.suberic.rikchik.language.display.shape.Arc;
import net.suberic.rikchik.language.display.shape.Bend;
import net.suberic.rikchik.language.display.shape.DoubleBend;
import net.suberic.rikchik.language.display.shape.EllArc;
import net.suberic.rikchik.language.display.shape.LBend;
import net.suberic.rikchik.language.display.shape.Line;

public class ShapeTransformer{

    public static void paintBase(Graphics g, float x, float y){
	g.fillOval 
	    (DisplayData.roundCoord(x-2), DisplayData.roundCoord(y-2),
	     DisplayData.roundSize(4), DisplayData.roundSize(4));
    }

    public static void paintShape2Shape //Dispatcher 
	(Component c, Graphics g, int x, int y, int steps, int currentStep, 
	 Shape origin, Shape destination){
	if (origin instanceof Line){
	    Line o = (Line) origin;
	    if (destination instanceof Line){
		Line d = (Line) destination;
		paintLine2Line
		    (c,g,x,y,steps,currentStep,o,d);
	    } else if (destination instanceof Bend){
		Bend d = (Bend) destination;
		Bend oBend = o.toBend(d);
		paintBend2Bend
		    (c,g,x,y,steps,currentStep,oBend,d);
	    } else if (destination instanceof DoubleBend){
		DoubleBend d = (DoubleBend) destination;
		DoubleBend oDoubleBend = o.toDoubleBend(d);
		paintDoubleBend2DoubleBend
		    (c,g,x,y,steps,currentStep,oDoubleBend,d);
	    }
	} else if (origin instanceof EllArc){
	    EllArc o = (EllArc) origin;
	    if (destination instanceof Line){
		Line d = (Line) destination;
		Bend dBend = d.toBend(o);
		paintBend2Bend
		    (c,g,x,y,steps,currentStep,o,dBend);
	    } else if (destination instanceof EllArc){
		EllArc d = (EllArc) destination;
		paintEllArc2EllArc 
		    (c,g,x,y,steps,currentStep,o,d);
	    } else if (destination instanceof LBend){
		LBend d = (LBend) destination;
		DoubleBend dDoubleBend = d.toDoubleBend(o);
		DoubleBend oDoubleBend = o.toDoubleBend(dDoubleBend);
		paintDoubleBend2DoubleBend
		    (c,g,x,y,steps,currentStep,oDoubleBend,dDoubleBend);
	    } else if (destination instanceof Bend){
		Bend d = (Bend) destination;
		paintBend2Bend
		    (c,g,x,y,steps,currentStep,o,d);
	    } else if (destination instanceof DoubleBend){
		DoubleBend d = (DoubleBend) destination;
		DoubleBend oDoubleBend = o.toDoubleBend(d);
		paintDoubleBend2DoubleBend
		    (c,g,x,y,steps,currentStep,oDoubleBend,d);
	    }
	} else if (origin instanceof LBend) {
	    LBend o = (LBend) origin;
	    if (destination instanceof Line){
		Line d = (Line) destination;
		Bend dBend = d.toBend(o);
		paintBend2Bend
		    (c,g,x,y,steps,currentStep,o,dBend);
	    } else if (destination instanceof EllArc){
		EllArc d = (EllArc) destination;
		DoubleBend oDoubleBend = o.toDoubleBend(d);
		DoubleBend dDoubleBend = d.toDoubleBend(oDoubleBend);
		paintDoubleBend2DoubleBend
		    (c,g,x,y,steps,currentStep,oDoubleBend,dDoubleBend);
	    } else if (destination instanceof Bend){
		Bend d = (Bend) destination;
		paintBend2Bend
		    (c,g,x,y,steps,currentStep,o,d);
	    } else if (destination instanceof DoubleBend){
		DoubleBend d = (DoubleBend) destination;
		DoubleBend oDoubleBend = o.toDoubleBend(d);
		paintDoubleBend2DoubleBend
		    (c,g,x,y,steps,currentStep,oDoubleBend,d);
	    }
	} else if (origin instanceof Bend) {
	    Bend o = (Bend) origin;
	    if (destination instanceof Line){
		Line d = (Line) destination;
		Bend dBend = d.toBend(o);
		paintBend2Bend
		    (c,g,x,y,steps,currentStep,o,dBend);
	    } else if (destination instanceof Bend){
		Bend d = (Bend) destination;
		paintBend2Bend
		    (c,g,x,y,steps,currentStep,o,d);
	    } else if (destination instanceof DoubleBend){
		DoubleBend d = (DoubleBend) destination;
		DoubleBend oDoubleBend = o.toDoubleBend(d);
		paintDoubleBend2DoubleBend
		    (c,g,x,y,steps,currentStep,oDoubleBend,d);
	    }
	} else if (origin instanceof DoubleBend) {
	    DoubleBend o = (DoubleBend) origin;
	    if (destination instanceof Line){
		Line d = (Line) destination;
		DoubleBend dDoubleBend = d.toDoubleBend(o);
		paintDoubleBend2DoubleBend
		    (c,g,x,y,steps,currentStep,o,dDoubleBend);
	    } else if (destination instanceof Bend){
		Bend d = (Bend) destination;
		DoubleBend dDoubleBend = d.toDoubleBend(o);
		paintDoubleBend2DoubleBend
		    (c,g,x,y,steps,currentStep,o,dDoubleBend);
	    } else if (destination instanceof DoubleBend){
		DoubleBend d = (DoubleBend) destination;
		paintDoubleBend2DoubleBend
		    (c,g,x,y,steps,currentStep,o,d);
	    }
	}
	float baseX = DisplayData.interpolateValue
	    (origin.getBaseX(), destination.getBaseX(), (float) currentStep/steps);
	float baseY = DisplayData.interpolateValue
	    (origin.getBaseY(), destination.getBaseY(), (float) currentStep/steps);
	paintBase(g, baseX, baseY);
    }


    public static void paintLine2Line
	(Component c, Graphics g, int x, int y, int steps, int currentStep, 
	 Line origin, Line destination){
	float doneRatio = (float) currentStep/steps;
	float orX = origin.getX();
	float orY = origin.getY();
	float orX2 = origin.getX2();
	float orY2 = origin.getY2();

	float deX = destination.getX();
	float deY = destination.getY();
	float deX2 = destination.getX2();
	float deY2 = destination.getY2();

	float orDist = DisplayData.distanceBetweenPoints(orX,orY,orX2,orY2);
	float deDist = DisplayData.distanceBetweenPoints(deX,deY,deX2,deY2);
	float orAngle = DisplayData.degreesBetweenPoints(orX,orY,orX2,orY2);
	float deAngle = DisplayData.degreesBetweenPoints(deX,deY,deX2,deY2);
	if ((deAngle - orAngle) > 180){ //go the short way!
	    deAngle -= 360;
	} else if ((deAngle - orAngle) < -180){ //go the short way!
	    deAngle += 360;
	}
	
	

	float newBaseX = orX + (deX - orX) * doneRatio;
	float newBaseY = orY + (deY - orY) * doneRatio;
	float newDist = orDist + (deDist - orDist) * doneRatio;
	float newAngle = orAngle + (deAngle - orAngle) * doneRatio;
	float newX2 = DisplayData.xFromPoint(newBaseX,newAngle,newDist);
	float newY2 = DisplayData.yFromPoint(newBaseY,newAngle,newDist);

	int startx = DisplayData.roundCoord(newBaseX);
	int starty = DisplayData.roundCoord(newBaseY);
	int endx = DisplayData.roundCoord(newX2);
	int endy = DisplayData.roundCoord(newY2);

	g.drawLine(startx,starty,endx,endy);
    }

    public static void paintEllArc2EllArc
	(Component c, Graphics g, int x, int y, int steps, int currentStep, 
	 EllArc origin, EllArc destination){

	float orX = origin.getX();
	float orY = origin.getY(); 
	float orR1 = origin.getRadius(); 
	float orR2 = origin.getRadius2(); 
	float orStartAngle = origin.getStartAngle(); 
	float orEndAngle = origin.getEndAngle();
	float orBaseX = origin.getBaseX();
	float orBaseY = origin.getBaseY();
	float deX = destination.getX();
	float deY = destination.getY();
	float deR1 = destination.getRadius();
	float deR2 = destination.getRadius2(); 
	float deStartAngle = destination.getStartAngle();
	float deEndAngle = destination.getEndAngle();
	float deBaseX = destination.getBaseX();
	float deBaseY = destination.getBaseY();
	
	float doneRatio = (float) currentStep/steps;
	orStartAngle %= 360;
	float orArcAngle = orEndAngle - orStartAngle;
	while (orArcAngle < 0){
	   orArcAngle += 360;
	}
	while (orArcAngle > 360) {
	    orArcAngle -= 360;
	}
	
	deStartAngle %= 360;
	float deArcAngle = deEndAngle - deStartAngle;
	while (deArcAngle < 0){
	    deArcAngle += 360;
	}
	while (deArcAngle > 360) {
	    deArcAngle -= 360;
	}

	if ((deStartAngle - orStartAngle) > 180){ //go the short way!
	    deStartAngle -= 360;
	} else if ((deStartAngle - orStartAngle) < -180){ //go the short way!
	    deStartAngle += 360;
	}
	
	float newBaseX = orBaseX + (deBaseX - orBaseX)*doneRatio;
	float newBaseY = orBaseY + (deBaseY - orBaseY)*doneRatio;
	float newR1 = orR1 + (deR1 - orR1)*doneRatio;
	float newR2 = orR2 + (deR2 - orR2)*doneRatio;
	float newStartAngle = orStartAngle + (deStartAngle - orStartAngle)*doneRatio;
	float newArcAngle = orArcAngle + (deArcAngle - orArcAngle)*doneRatio;

	float centerX = DisplayData.xFromPoint(newBaseX,newStartAngle+180,newR1);
	float centerY = DisplayData.yFromPoint(newBaseY,newStartAngle+180,newR2);

	int arcx = DisplayData.roundCoord(centerX - newR1);
	int arcy = DisplayData.roundCoord(centerY - newR2);
	int width = DisplayData.roundSize(newR1*2);
	int height = DisplayData.roundSize(newR2*2);
	int startAngle = Math.round (newStartAngle);
	int arcAngle = Math.round (newArcAngle);
	while (arcAngle < 0){
	    arcAngle += 360;
	}
	while (arcAngle > 360) {
	    arcAngle -= 360;
	}

	g.drawArc(arcx, arcy, width, height, startAngle, arcAngle);
    }

    public static void paintBend2SameBend
	(Component c, Graphics g, int x, int y, int steps, int currentStep,
	 Bend origin, Bend destination){

	float orX = origin.getX();
	float orY = origin.getY();
	float orR1 = origin.getRadius();
	float orR2 = origin.getRadius2();
	float orStartAngle = origin.getStartAngle();
	float orEndAngle = origin.getEndAngle();
	float orStartLeg = origin.getStartLeg();
	float orEndLeg = origin.getEndLeg();
	
	float deX = destination.getX();
	float deY = destination.getY();
	float deR1 = destination.getRadius();
	float deR2 = destination.getRadius2();
	float deStartAngle = destination.getStartAngle();
	float deEndAngle = destination.getEndAngle();
	float deStartLeg = destination.getStartLeg();
	float deEndLeg = destination.getEndLeg();
	
	boolean direction = origin.getDirection();
	float doneRatio = (float) currentStep/steps;
	float orArcBaseX = DisplayData.xFromPoint(orX,orStartAngle,orR1);
	float orArcBaseY = DisplayData.yFromPoint(orY,orStartAngle,orR2);
	float orStartLegAngle;
	if (direction) {
	    orStartLegAngle = DisplayData.convertAngle
		(orStartAngle-90, orR1, orR2); 
	} else {
	    orStartLegAngle = DisplayData.convertAngle
		(orStartAngle+90, orR1, orR2); 
	}
	float orBaseX = DisplayData.xFromPoint(orArcBaseX,orStartLegAngle,orStartLeg);
	float orBaseY = DisplayData.yFromPoint(orArcBaseY,orStartLegAngle,orStartLeg);
	orStartAngle %= 360;
	float orArcAngle = orEndAngle - orStartAngle;
	if (!direction) orArcAngle = -orArcAngle; 
	while (orArcAngle < 0){
	   orArcAngle += 360;
	}
	while (orArcAngle > 360) {
	    orArcAngle -= 360;
	}

	float deArcBaseX = DisplayData.xFromPoint(deX,deStartAngle,deR1);
	float deArcBaseY = DisplayData.yFromPoint(deY,deStartAngle,deR2);
	float deStartLegAngle;
	if (direction) {
	    deStartLegAngle = DisplayData.convertAngle
		(deStartAngle-90, deR1, deR2); 
	} else {
	    deStartLegAngle = DisplayData.convertAngle
		(deStartAngle+90, deR1, deR2); 
	}
	float deBaseX = DisplayData.xFromPoint(deArcBaseX,deStartLegAngle,deStartLeg);
	float deBaseY = DisplayData.yFromPoint(deArcBaseY,deStartLegAngle,deStartLeg);
	deStartAngle %= 360;
	float deArcAngle = deEndAngle - deStartAngle;
	if (!direction) deArcAngle = -deArcAngle; 
	while (deArcAngle < 0){
	   deArcAngle += 360;
	}
	while (deArcAngle > 360) {
	    deArcAngle -= 360;
	}

	if (direction){
	    if ((deStartAngle - orStartAngle) > 180){ //go the short way!
		deStartAngle -= 360;
	    } else if ((deStartAngle - orStartAngle) <= -180){ //go the short way!
		deStartAngle += 360;
	    }
	} else {
	    if ((deStartAngle - orStartAngle) >= 180){ //go the short way!
		deStartAngle -= 360;
	    } else if ((deStartAngle - orStartAngle) < -180){ //go the short way!
		deStartAngle += 360;
	    }
	}
	
	float newBaseX = orBaseX + (deBaseX - orBaseX)*doneRatio;
	float newBaseY = orBaseY + (deBaseY - orBaseY)*doneRatio;
	float newR1 = orR1 + (deR1 - orR1)*doneRatio;
	float newR2 = orR2 + (deR2 - orR2)*doneRatio;
	float newStartAngle = orStartAngle + (deStartAngle - orStartAngle)*doneRatio;
	float newArcAngle = orArcAngle + (deArcAngle - orArcAngle)*doneRatio;
	float newStartLeg = orStartLeg + (deStartLeg - orStartLeg)*doneRatio;
	float newEndLeg = orEndLeg + (deEndLeg - orEndLeg)*doneRatio;

	float newEndAngle;
	float newArcStartAngle;
	float newArcEndAngle;
	if (direction){
	    newEndAngle = newStartAngle + newArcAngle;
	    newArcStartAngle = DisplayData.convertAngle
		(newStartAngle+90, newR1, newR2);
	    newArcEndAngle = DisplayData.convertAngle
		(newEndAngle+90, newR1, newR2);
	} else {
	    newEndAngle = newStartAngle - newArcAngle;
	    newArcStartAngle = DisplayData.convertAngle
		(newStartAngle-90, newR1, newR2);
	    newArcEndAngle = DisplayData.convertAngle
		(newEndAngle-90, newR1, newR2);
	}

	float newArcStartX = DisplayData.xFromPoint
	    (newBaseX, newArcStartAngle, newStartLeg);
	float newArcStartY = DisplayData.yFromPoint
	    (newBaseY, newArcStartAngle, newStartLeg);
	float newArcCenterX = DisplayData.xFromPoint
	    (newArcStartX, newStartAngle+180, newR1);
	float newArcCenterY = DisplayData.yFromPoint
	    (newArcStartY, newStartAngle+180, newR2);
	float newArcEndX = DisplayData.xFromPoint
	    (newArcCenterX, newEndAngle, newR1);
	float newArcEndY = DisplayData.yFromPoint
	    (newArcCenterY, newEndAngle, newR2);
	float newTipX = DisplayData.xFromPoint
	    (newArcEndX, newArcEndAngle, newEndLeg);
	float newTipY = DisplayData.yFromPoint
	    (newArcEndY, newArcEndAngle, newEndLeg);
	
	

	if (newStartLeg > 0){
	    int startx = DisplayData.roundCoord(newBaseX);
	    int starty = DisplayData.roundCoord(newBaseY);
	    int startx2 = DisplayData.roundCoord(newArcStartX);
	    int starty2 = DisplayData.roundCoord(newArcStartY);
	    
	    g.drawLine(startx, starty, startx2, starty2);
	}
	if (newR1 * newR2 > 0){
	    int arcx = DisplayData.roundCoord(newArcCenterX - newR1);
	    int arcy = DisplayData.roundCoord(newArcCenterY - newR2);
	    int width = DisplayData.roundSize(newR1*2);
	    int height = DisplayData.roundSize(newR2*2);
	    int startAngle = Math.round (direction?newStartAngle:newEndAngle);
	    int arcAngle = Math.round (newArcAngle);
	    
	    g.drawArc(arcx, arcy, width, height, startAngle, arcAngle);
	}
	if (newEndLeg > 0){
	    int endx = DisplayData.roundCoord(newArcEndX);
	    int endy = DisplayData.roundCoord(newArcEndY);
	    int endx2 = DisplayData.roundCoord(newTipX);
	    int endy2 = DisplayData.roundCoord(newTipY);

	    g.drawLine(endx, endy, endx2, endy2);
	}

    }


    /**
       Switches direction of a bend through line.
    */
    public static void paintBend2OtherBend
	(Component c, Graphics g, int x, int y, int steps, int currentStep,
	 Bend origin, Bend destination){

	if (!origin.getDirection()){
	    paintBend2OtherBend
	    (c,g,x,y,steps,steps-currentStep,
	     destination, origin);
	} else {

	    //origin is CCW, destination is CW!
	    float orX = origin.getX();
	    float orY = origin.getY();
	    float orR1 = origin.getRadius();
	    float orR2 = origin.getRadius2();
	    float orStartAngle = origin.getStartAngle();
	    float orEndAngle = origin.getEndAngle();
	    float orStartLeg = origin.getStartLeg();
	    float orEndLeg = origin.getEndLeg();
	
	    float deX = destination.getX();
	    float deY = destination.getY();
	    float deR1 = destination.getRadius();
	    float deR2 = destination.getRadius2();
	    float deStartAngle = destination.getStartAngle();
	    float deEndAngle = destination.getEndAngle();
	    float deStartLeg = destination.getStartLeg();
	    float deEndLeg = destination.getEndLeg();
		
	    if ((deStartAngle - orStartAngle) > 180){ //go the short way!
		deStartAngle -= 360;
	    } else if ((deStartAngle - orStartAngle) < -180){ //go the short way!
		deStartAngle += 360;
	    }
	
	    float orArcAngle = orEndAngle - orStartAngle;
	    while (orArcAngle < 0){
		orArcAngle += 360;
	    }
	    while (orArcAngle > 360) {
		orArcAngle -= 360;
	    }
	    float orRadius;
	    if (orR1 == orR2){
		orRadius = orR1;
	    } else {
		orRadius = (float)Math.sqrt((orR1*orR1 + orR2*orR2)/2);
	    }
	    float orLength = DisplayData.lengthOfBend(orRadius, orArcAngle, orStartLeg, orEndLeg);
	    float orLineAngle = DisplayData.convertAngle
		(orStartAngle + 90, orR1, orR2);

	    float deArcAngle = deStartAngle - deEndAngle;
	    while (deArcAngle < 0){
		deArcAngle += 360;
	    }
	    while (deArcAngle > 360) {
		deArcAngle -= 360;
	    }
	    float deRadius;
	    if (deR1 == deR2){
		deRadius = deR1;
	    } else {
		deRadius = (float)Math.sqrt((deR1*deR1 + deR2*deR2)/2);
	    }
	    float deLength = DisplayData.lengthOfBend
		(deRadius, deArcAngle, deStartLeg, deEndLeg);
	    float deLineAngle = DisplayData.convertAngle
		(deStartAngle - 90, deR1, deR2);

	    float midLength = (orLength + deLength) / 2;

	    if ((deLineAngle - orLineAngle) > 180){ //go the short way!
		deLineAngle -= 360;
	    } else if ((deLineAngle - orLineAngle) < -180){ //go the short way!
		deLineAngle += 360;
	    }
	    float midLineAngle = ((orLineAngle + deLineAngle) / 2) % 360;
	    //System.out.println ("midLineAngle: " + midLineAngle);
	
	    //figure out bases.
	    float doneRatio = (float) currentStep/steps;
	    float orBaseX = origin.getBaseX();
	    float orBaseY = origin.getBaseY();
	    float deBaseX = destination.getBaseX();
	    float deBaseY = destination.getBaseY();

	    float midBaseX = (orBaseX + deBaseX) / 2;
	    float midBaseY = (orBaseY + deBaseY) / 2;
	
	    //figure out midline.
	    Line midLine = new Line (midBaseX, midBaseY, midLineAngle, midLength, true);
	
	    //decide which to draw.
	    if (currentStep * 2 < steps){ //CCWBend2Line
		//draw.
		paintBend2SameBend
		    (c,g,x,y,steps,currentStep*2,
		     origin, midLine.toBend(origin));
	    } else { //Line2CWBend
		//draw.
		paintBend2SameBend
		    (c,g,x,y,steps,currentStep*2-steps,
		     midLine.toBend(destination), destination);
	    } 
	}
    }

    public static void paintBend2Bend
	(Component c, Graphics g, int x, int y, int steps, int currentStep,
	 Bend origin, Bend destination){
	
	boolean orD = origin.getDirection();
	boolean deD = destination.getDirection();
	if (orD == deD){ //origin matches destination
	    paintBend2SameBend
		(c,g,x,y,steps,currentStep,
		 origin,destination);
	} else { //origin does not match destination
	    paintBend2OtherBend
		(c,g,x,y,steps,currentStep,
		 origin,destination);
	}
    }


    public static void paintDoubleBend2SameDoubleBend
	(Component c, Graphics g,  int x, int y, int steps, int currentStep,
	 DoubleBend origin, DoubleBend destination){
	float doneRatio = (float) currentStep/steps;
		
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


	float newBaseX = DisplayData.interpolateValue
	    (orBaseX, deBaseX, doneRatio);
	float newBaseY  = DisplayData.interpolateValue
	    (orBaseY, deBaseY, doneRatio);
	if ((deStartLegAngle - orStartLegAngle) > 180){ //go the short way!
	    deStartLegAngle -= 360;
	} else if ((deStartLegAngle - orStartLegAngle) < -180){ //go the short way!
	    deStartLegAngle += 360;
	}
	float newStartLegAngle  = DisplayData.interpolateValue
	    (orStartLegAngle, deStartLegAngle, doneRatio);
	float newStartLeg  = DisplayData.interpolateValue
	    (orStartLeg, deStartLeg, doneRatio);
	float newArc1Radius  = DisplayData.interpolateValue
	    (orArc1Radius, deArc1Radius, doneRatio);
	float newArc1Radius2  = DisplayData.interpolateValue
	    (orArc1Radius2, deArc1Radius2, doneRatio);
	boolean newArc1Direction = orArc1Direction;
	float newArc1Angle = DisplayData.interpolateValue
	    (orArc1Angle, deArc1Angle, doneRatio);
	float newMidLeg = DisplayData.interpolateValue
	    (orMidLeg, deMidLeg, doneRatio);
	float newArc2Radius = DisplayData.interpolateValue
	    (orArc2Radius, deArc2Radius, doneRatio);
	float newArc2Radius2 = DisplayData.interpolateValue
	    (orArc2Radius2, deArc2Radius2, doneRatio);
	boolean newArc2Direction = orArc2Direction;
	float newArc2Angle = DisplayData.interpolateValue
	    (orArc2Angle, deArc2Angle, doneRatio);
	float newEndLeg = DisplayData.interpolateValue
	    (orEndLeg, deEndLeg, doneRatio);

	paintDoubleBend(c,g,x,y, new DoubleBend
	    (newBaseX, newBaseY, newStartLegAngle, newStartLeg,
	     newArc1Radius, newArc1Radius2,
	     newArc1Direction, newArc1Angle, newMidLeg,
	     newArc2Radius, newArc2Radius2, 
	     newArc2Direction, newArc2Angle, newEndLeg));
    }

    public static void paintDoubleBend2OtherDoubleBend
	(Component c, Graphics g,  int x, int y, int steps, int currentStep,
	 DoubleBend origin, DoubleBend destination){
	if (currentStep * 2 < steps){
	    //draw.
	    paintDoubleBend2SameDoubleBend
		(c,g,x,y,steps,currentStep*2,
		 origin, DoubleBend.createMidDoubleBend
		 (origin,destination,true));
	} else {
	    //draw.
	    paintDoubleBend2SameDoubleBend
		(c,g,x,y,steps,currentStep*2-steps,
		 DoubleBend.createMidDoubleBend
		 (origin,destination,false), destination);
	} 
    }

    public static void paintDoubleBend
	(Component c, Graphics g, int x, int y, DoubleBend d){

	//I seem to have messed this up in some major way. 
	//But reversing the directions of the lines and 
	//adding 180 to the startLegAngle seems to have 
	//fixed it. I am leaving it in for now.

	float baseX = d.getBaseX();
	float baseY = d.getBaseY();
	float startLegAngle = d.getStartLegAngle() + 180;
	float startLeg = d.getStartLeg();
	float arc1Radius = d.getArc1Radius();
	float arc1Radius2 = d.getArc1Radius2();
	boolean arc1Direction = d.getArc1Direction();
	float arc1Angle = d.getArc1Angle();
	float midLeg = d.getMidLeg();
	float arc2Radius = d.getArc2Radius();
	float arc2Radius2 = d.getArc2Radius2();
	boolean arc2Direction = d.getArc2Direction();
	float arc2Angle = d.getArc2Angle();
	float endLeg = d.getEndLeg();
	
	float startLegX2 = DisplayData.xFromPoint
	    (baseX,startLegAngle,-startLeg); 
	float startLegY2 = DisplayData.yFromPoint
	    (baseY,startLegAngle,-startLeg);

	//angle from center to arc start.
	float arc1StartAngle = DisplayData.convertAngle 
	    (startLegAngle - 90,arc1Radius2,arc1Radius);
	if (arc1Direction){
	    arc1StartAngle += 180; //does not affect convertAngle.
	}
	//from arc start to arc center.
	float arc1CenterX = DisplayData.xFromPoint
	    (startLegX2, arc1StartAngle, -arc1Radius);
	float arc1CenterY = DisplayData.yFromPoint
	    (startLegY2, arc1StartAngle, -arc1Radius2);

	float midLegAngle = startLegAngle + 
	    (arc1Direction ? arc1Angle : -arc1Angle);

	float arc1EndAngle = DisplayData.convertAngle 
	    (midLegAngle-90,arc1Radius2,arc1Radius);
	if (arc1Direction){
	    arc1EndAngle += 180; //does not affect convertAngle.
	}
       	float arc1ArcAngle = Math.abs(arc1EndAngle - arc1StartAngle);
	while (arc1ArcAngle < 0){
	    arc1ArcAngle += 360;
	}
	while (arc1ArcAngle > 360) {
	    arc1ArcAngle -= 360;
	}
	
	
	float midLegX = DisplayData.xFromPoint
	    (arc1CenterX, arc1EndAngle, arc1Radius);
	float midLegY = DisplayData.yFromPoint
	    (arc1CenterY, arc1EndAngle, arc1Radius2);
	float midLegX2 = DisplayData.xFromPoint
	    (midLegX, midLegAngle, -midLeg);
	float midLegY2 = DisplayData.yFromPoint
	    (midLegY, midLegAngle, -midLeg);
	
	//angle from center to arc start.
	float arc2StartAngle = DisplayData.convertAngle 
	    (midLegAngle - 90,arc2Radius2,arc2Radius);
	if (arc2Direction){
	    arc2StartAngle += 180; //does not affect convertAngle.
	}
	//from arc start to arc center.
	float arc2CenterX = DisplayData.xFromPoint
	    (midLegX2, arc2StartAngle, -arc2Radius);
	float arc2CenterY = DisplayData.yFromPoint
	    (midLegY2, arc2StartAngle, -arc2Radius2);
	
	float endLegAngle = midLegAngle + 
	    (arc2Direction ? arc2Angle : -arc2Angle);

	float arc2EndAngle = DisplayData.convertAngle 
	    (endLegAngle - 90,arc2Radius2,arc2Radius);
	if (arc2Direction){
	    arc2EndAngle += 180; //does not affect convertAngle.
	}
	float arc2ArcAngle = Math.abs(arc2EndAngle - arc2StartAngle);
	while (arc2ArcAngle < 0){
	    arc2ArcAngle += 360;
	}
	while (arc2ArcAngle > 360) {
	    arc2ArcAngle -= 360;
	}
	
	
	float endLegX = DisplayData.xFromPoint
	    (arc2CenterX, arc2EndAngle, arc2Radius);
	float endLegY = DisplayData.yFromPoint
	    (arc2CenterY, arc2EndAngle, arc2Radius2);
	float endLegX2 = DisplayData.xFromPoint
	    (endLegX, endLegAngle, -endLeg);
	float endLegY2 = DisplayData.yFromPoint
	    (endLegY, endLegAngle, -endLeg);
	
	int slx = DisplayData.roundCoord (baseX);
	int sly = DisplayData.roundCoord (baseY);
	int slx2 = DisplayData.roundCoord (startLegX2);
	int sly2 = DisplayData.roundCoord (startLegY2);
	g.drawLine(slx, sly, slx2, sly2);
	int a1x = DisplayData.roundCoord (arc1CenterX - arc1Radius);
	int a1y = DisplayData.roundCoord (arc1CenterY - arc1Radius2);
	int a1w = DisplayData.roundSize (2*arc1Radius);
	int a1h = DisplayData.roundSize (2*arc1Radius2);
	int a1sa = Math.round (arc1Direction?arc1StartAngle:arc1EndAngle);
	int a1aa = Math.round (arc1ArcAngle);
	g.drawArc(a1x,a1y,a1w,a1h,a1sa,a1aa);
	int mlx = DisplayData.roundCoord (midLegX);
	int mly = DisplayData.roundCoord (midLegY);
	int mlx2 = DisplayData.roundCoord (midLegX2);
	int mly2 = DisplayData.roundCoord (midLegY2);
	g.drawLine(mlx, mly, mlx2, mly2);
	int a2x = DisplayData.roundCoord (arc2CenterX - arc2Radius);
	int a2y = DisplayData.roundCoord (arc2CenterY - arc2Radius2);
	int a2w = DisplayData.roundSize (2*arc2Radius);
	int a2h = DisplayData.roundSize (2*arc2Radius2);
	int a2sa = Math.round (arc2Direction?arc2StartAngle:arc2EndAngle);
	int a2aa = Math.round (arc2ArcAngle);
	g.drawArc(a2x,a2y,a2w,a2h,a2sa,a2aa);
	int elx = DisplayData.roundCoord (endLegX);
	int ely = DisplayData.roundCoord (endLegY);
	int elx2 = DisplayData.roundCoord (endLegX2);
	int ely2 = DisplayData.roundCoord (endLegY2);
	g.drawLine(elx, ely, elx2, ely2);
	//System.err.println("a1aa:" + arc1ArcAngle + "\t a1a:" + arc1Angle);
	
    }

    public static void paintDoubleBend2DoubleBend
	(Component c, Graphics g,  int x, int y, int steps, int currentStep,
	 DoubleBend origin, DoubleBend destination){
	boolean orD1 = origin.getArc1Direction();
	boolean orD2 = origin.getArc2Direction();
	boolean deD1 = destination.getArc1Direction();
	boolean deD2 = destination.getArc2Direction();
	if ((orD1 == deD1) && (orD2 == deD2)){
	    paintDoubleBend2SameDoubleBend
		(c,g,x,y,steps,currentStep,origin,destination);
	} else {
	    paintDoubleBend2OtherDoubleBend
		(c,g,x,y,steps,currentStep,origin,destination);
	}
    }

}






