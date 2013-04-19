package net.suberic.rikchik.language.display;

import net.suberic.rikchik.language.Tentacle;
import net.suberic.util.awt.AnimatedIcon;
import javax.swing.Icon;

public interface TentacleIcon extends AnimatedIcon{

    public void setTentacle(Tentacle t);
    public void setDestination (Tentacle d);

    public Tentacle getTentacle();

}
