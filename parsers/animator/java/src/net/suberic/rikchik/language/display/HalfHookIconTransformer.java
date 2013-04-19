package net.suberic.rikchik.language.display;

import java.awt.Component;
import java.awt.Graphics;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.display.shape.Shape;
import net.suberic.rikchik.language.display.shape.Bend;

public class HalfHookIconTransformer{

    public static Shape getShape(Tentacle tentacle){
	float orX = tentacle.getX();
	float orY = -(tentacle.getY());
	float orR1 = tentacle.getR1();
	float orStartAngle = tentacle.getA1() + 180; //A1 is the direction the line is pointing.
	float orLeg = tentacle.getLeg();
	boolean orD = (tentacle.getD() != 0); //isCCW
	float orEndAngle;
	if (orD){
	    orEndAngle = orStartAngle+90;
	} else {
	    orEndAngle = orStartAngle-90;
	}

	return new Bend(orX,orY,orR1,
			orStartAngle, orEndAngle, 
			0, orLeg, orD);
	    

    }
}
