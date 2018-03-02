package net.suberic.rikchik.language.display;

import net.suberic.rikchik.language.display.AbstractTentacleIcon;
import net.suberic.rikchik.language.display.shape.Shape;
import net.suberic.rikchik.language.Tentacle;
import java.awt.Component;
import java.awt.Graphics;

public class FishBendTentacleIcon extends AbstractTentacleIcon{

    public Shape getShape(){
	return FishBendIconTransformer.getShape(tentacle);
    }
}


