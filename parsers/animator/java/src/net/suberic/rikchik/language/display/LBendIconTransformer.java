package net.suberic.rikchik.language.display;

import java.awt.Component;
import java.awt.Graphics;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.display.shape.Shape;
import net.suberic.rikchik.language.display.shape.Bend;
import net.suberic.rikchik.language.display.shape.LBend;

public class LBendIconTransformer{

    public static Shape getShape(Tentacle tentacle){
	float orX = tentacle.getX();
	float orY = -(tentacle.getY());
	float orX2 = tentacle.getX2();
	float orY2 = -(tentacle.getY2());
	boolean orD = (tentacle.getD() != 0); //horizFirst
	
	float orCenterX;
	float orCenterY;
	float orStartAngle;
	float orEndAngle;
	float orStartLeg;
	float orEndLeg;
	boolean orIsCCW;
	
	boolean useBend = false;

	if (useBend) {
	    if (orD){
		orCenterX = orX2;
		orCenterY = orY;
		orStartLeg = Math.abs(orX - orX2);
		orEndLeg = Math.abs(orY - orY2);
		if (orY > orY2){
		    orStartAngle = 270;
		} else {
		    orStartAngle = 90;
		}
	    } else {
		orCenterX = orX;
		orCenterY = orY2;
		orStartLeg = Math.abs(orY - orY2);
		orEndLeg = Math.abs(orX - orX2);
		if (orX > orX2){
		    orStartAngle = 0;
		} else {
		    orStartAngle = 180;
		}
	    }
	    orIsCCW = !((orD) ^ (orX > orX2) ^ (orY > orY2));
	    if (orIsCCW){
		orEndAngle = orStartAngle + 90;
	    } else {
		orEndAngle = orStartAngle - 90;
	    }
	    
	    return new Bend(orCenterX, orCenterY, 0,
			    orStartAngle, orEndAngle, 
			    orStartLeg, orEndLeg, orIsCCW);
	} else {
	    
	    if (orD){
		orCenterX = orX2;
		orCenterY = orY;
		orStartLeg = Math.abs(orX - orX2);
		orEndLeg = Math.abs(orY - orY2);
		if (orY > orY2){
		    orStartAngle = 270;
		} else {
		    orStartAngle = 90;
		}
	    } else {
		orCenterX = orX;
		orCenterY = orY2;
		orStartLeg = Math.abs(orY - orY2);
		orEndLeg = Math.abs(orX - orX2);
		if (orX > orX2){
		    orStartAngle = 0;
		} else {
		    orStartAngle = 180;
		}
	    }
	    orIsCCW = !((orD) ^ (orX > orX2) ^ (orY > orY2));
	    if (orIsCCW){
		orEndAngle = orStartAngle + 90;
	    } else {
		orEndAngle = orStartAngle - 90;
	    }
	    
	    return new LBend(orCenterX, orCenterY,
			     orStartAngle, orEndAngle, 
			     orStartLeg, orEndLeg, orIsCCW);
	}
    }
}

