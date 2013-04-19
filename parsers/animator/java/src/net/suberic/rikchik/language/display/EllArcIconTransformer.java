package net.suberic.rikchik.language.display;

import java.awt.Component;
import java.awt.Graphics;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.display.shape.Shape;
import net.suberic.rikchik.language.display.shape.EllArc;

public class EllArcIconTransformer{

    public static Shape getShape(Tentacle tentacle){
	return new EllArc(tentacle.getX(), -(tentacle.getY()), 
			  tentacle.getR1(), tentacle.getR2(),
			  tentacle.getA1(), tentacle.getA2());
	
    }
}
