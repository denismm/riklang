package net.suberic.rikchik.language.display;

import java.awt.Component;
import java.awt.Graphics;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.display.shape.Shape;
import net.suberic.rikchik.language.display.shape.Bend;

public class HookIconTransformer{

    public static Shape getShape(Tentacle tentacle){
	float orX = tentacle.getX();
	float orY = -(tentacle.getY());
	float orR1 = tentacle.getR1();
	float orStartAngle = tentacle.getA1(); 
	float orLeg = tentacle.getLeg();
	boolean orD = (tentacle.getD() != 0); //isCCW
	float orEndAngle;
	if (orD){
	    orStartAngle += 90;
	    orEndAngle = orStartAngle+180;
	} else {
	    orStartAngle -= 90;
	    orEndAngle = orStartAngle-180;
	}
       
	return new Bend (orX,orY,orR1,
			 orStartAngle, orEndAngle, 
			 0, orLeg, orD);
    }
}
