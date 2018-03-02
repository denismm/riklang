package net.suberic.rikchik.language.display;

import java.awt.Component;
import java.awt.Graphics;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.display.shape.Shape;
import net.suberic.rikchik.language.display.shape.DoubleBend;

public class SquiggleIconTransformer{

    public static Shape getShape(Tentacle tentacle){
	boolean squiggled = (tentacle.getD() != 0); 
	float r      = tentacle.getR1();
	
	//  Here's how this works:
	//  call (t.x,t.y) point alpha and (t.x2,t.y2) point beta.
	float alphax = tentacle.getX();
	float alphay = -tentacle.getY();
	float betax  = tentacle.getX2();
	float betay  = -tentacle.getY2();

	//  determine the midpoint of the squiggle. call it gamma.
	float gammax = (alphax + betax) / 2;
	float gammay = (alphay + betay) / 2;
	//  determine the midpoint of the line between that point and the end of the squiggle. call it zeta.
	float zetax  = (betax + gammax) / 2;
	float zetay  = (betay + gammay) / 2;

	// determine the distance from zeta to beta. call it u.
	float u      = DisplayData.distanceBetweenPoints
	    (betax, betay, zetax, zetay);
	

	// call the midpoint of the second arc eta. 
	// determine angle of line zeta-eta. call it q. 
	// (this varies based on d)
	float q = DisplayData.degreesBetweenPoints
	    (zetax, zetay, betax, betay);
	if (squiggled){
	    q -= 90;
	} else {
	    q += 90;
	}

	//  determine eta. 
	float etax   = DisplayData.xFromPoint (zetax, q, r);
	float etay   = DisplayData.yFromPoint (zetay, q, r);
	
	//  determine the degrees of angle beta-eta-zeta. call it m.
	float m     = (float)( Math.atan(u/r) / DisplayData.RAD_DEG );
	
	//  n (arc angle/2) = 180 - 2m.
	float n     = 180 - 2 * m;

	//  halve the length of line beta-eta. call it t.
	float t      = DisplayData.distanceBetweenPoints(betax, betay, etax, etay) / 2;
	
	//  s (the radius of the sub-circle) = t/cos(m).
	float s     = t / (float)Math.cos(m * DisplayData.RAD_DEG);
	
	// delta (the center of the sub-circle) is the point s units from eta along the line eta-zeta.
	float deltax  = DisplayData.xFromPoint(etax, q+180, s);
	float deltay  = DisplayData.yFromPoint(etay, q+180, s);
	
	// w (startAngle) is by definition the angle of line delta-beta.
	float w      = DisplayData.degreesBetweenPoints
	    (deltax, deltay, betax, betay);
	
	/* DoubleBend info */
	boolean arc1Direction = !squiggled; 
	boolean arc2Direction = squiggled; 
	float baseX = alphax;
	float baseY = alphay;
	float arc1Radius = s;
	float arc1Radius2 = arc1Radius;
	float arc2Radius = arc1Radius;
	float arc2Radius2 = arc1Radius;
	float startLegAngle = squiggled?(w + 90):(w - 90);
	float arc1Angle = n * 2;
	float arc2Angle = arc1Angle;
	float startLeg = 0;
	float midLeg = 0;
	float endLeg = 0;
	
	return new DoubleBend( baseX, baseY, startLegAngle, 0,
			       arc1Radius, arc1Radius2,
			       arc1Direction, arc1Angle, 0,
			       arc2Radius, arc2Radius2,
			       arc2Direction, arc2Angle, 0);	
    }
}



