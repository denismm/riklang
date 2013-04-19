package net.suberic.rikchik.language.display;

import java.awt.Component;
import java.awt.Graphics;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.display.DisplayData;
import net.suberic.rikchik.language.display.shape.Shape;
import net.suberic.rikchik.language.display.shape.Arc;

public class CircleIconTransformer{

    public static Shape getShape(Tentacle tentacle){
	return new Arc(tentacle.getX(), -(tentacle.getY()), tentacle.getR1(), 90, 450);
    }
}
