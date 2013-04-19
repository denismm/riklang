package net.suberic.rikchik.language.display;

import net.suberic.rikchik.language.display.AbstractTentacleIcon;
import net.suberic.rikchik.language.display.shape.Shape;
import net.suberic.rikchik.language.Tentacle;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class WicketTentacleIcon extends AbstractTentacleIcon{

    public Shape getShape(){
	return WicketIconTransformer.getShape(tentacle);
    }
}
