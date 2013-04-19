package net.suberic.rikchik.language.display;

import net.suberic.rikchik.language.display.AbstractTentacleIcon;
import net.suberic.rikchik.language.display.CircleIconTransformer;
import net.suberic.rikchik.language.display.shape.Shape;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.CircleTentacle;
import java.awt.Component;
import java.awt.Graphics;

public class CircleTentacleIcon extends AbstractTentacleIcon{

    public Shape getShape(){
	return CircleIconTransformer.getShape(tentacle);
    }
}


