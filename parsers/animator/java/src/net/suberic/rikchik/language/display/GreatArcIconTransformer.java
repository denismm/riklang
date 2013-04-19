package net.suberic.rikchik.language.display;

import java.awt.Component;
import java.awt.Graphics;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.display.shape.Shape;
import net.suberic.rikchik.language.display.shape.Arc;

public class GreatArcIconTransformer{

    public static Shape getShape(Tentacle tentacle){
	return new Arc(50, 50, 30,
		       tentacle.getA1(), tentacle.getA2());
	
    }
}
