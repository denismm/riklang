package net.suberic.rikchik.language.display;

import java.awt.Component;
import java.awt.Graphics;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.display.shape.Shape;
import net.suberic.rikchik.language.display.shape.Bend;

public class WicketIconTransformer{

    public static Shape getShape(Tentacle tentacle){
	float orX = tentacle.getX();
	float orY = -(tentacle.getY());
	float orR1 = tentacle.getR1();
	float orStartAngle = tentacle.getA1()+90;
	float orLeg = tentacle.getLeg();


	return new Bend(orX, orY, orR1,
			orStartAngle, orStartAngle+180, 
			orLeg, orLeg, true);
    }
}
