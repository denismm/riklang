package net.suberic.rikchik.language.display;

import javax.swing.Icon;
import java.awt.Graphics;
import java.awt.Component;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.Morpheme;
import net.suberic.rikchik.language.display.IconFactory;
import net.suberic.rikchik.language.display.AbstractRikchikAnimatedIcon;
import net.suberic.util.awt.AnimatedIcon;

public class MorphemeIcon extends AbstractRikchikAnimatedIcon{

    protected Morpheme morpheme;
    protected Morpheme destination;
    
    private AnimatedIcon[] tIcon = new AnimatedIcon[4];

    public void setSteps(int steps){
	getIcons();
	
	for (int i = 0; i < 4; i++){
	    tIcon[i].setSteps(steps);
	}
	
	this.steps = steps;	
    }
    
    public void incrementStep(){
	if (currentStep < steps){
	    getIcons();
	    
	    for (int i = 0; i < 4; i++){
		tIcon[i].incrementStep();
	    }
	    
	    currentStep++;
	}
    }

    public boolean isFinished(){
	return (currentStep >= steps);
    }

    public void setMorpheme(Morpheme w){
	this.morpheme = w;
    }

    public Morpheme getMorpheme(){
	return this.morpheme;
    }

    public void setDestination(Morpheme d){
	this.destination = d;
    }
    
    private void getIcons(){
	for  (int i = 0; i < 4; i++){
	    if (tIcon[i] == null){
		if (destination != null){
		    tIcon[i] = IconFactory.getTentacleIcon
			(morpheme.getTentacle(i),
			 destination.getTentacle(i));
		} else {
		    tIcon[i] = IconFactory.getTentacleIcon
			(morpheme.getTentacle(i));
		}
	    }
	}
    }
    
    public void paintIcon(Component c, Graphics g, int x, int y){
	getIcons();
	for (int i = 0; i < 4; i++){
	    tIcon[i].paintIcon(c,g,x,y);
	}
    }
    
}
