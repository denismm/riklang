package net.suberic.rikchik.language.display;

import java.awt.Component;
import java.awt.Graphics;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.display.shape.Shape;
import net.suberic.rikchik.language.display.shape.Line;

public class LineIconTransformer{

    public static Shape getShape(Tentacle tentacle){
	return new Line (tentacle.getX(), -(tentacle.getY()),
			 tentacle.getX2(), -(tentacle.getY2()));
    }
}
