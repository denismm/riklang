package net.suberic.rikchik.language.display;

import net.suberic.util.awt.AnimatedIcon;

public abstract class AbstractRikchikAnimatedIcon implements AnimatedIcon{
    
    protected int width = DisplayData.roundDimension(100)+1;
    protected int height = DisplayData.roundDimension(100)+1;

    protected int steps = 8;
    protected int currentStep = 0;

    public int getIconHeight() {
	return this.height;
    }

    public int getIconWidth() {
	return this.width;
    }

}
