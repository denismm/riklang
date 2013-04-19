package net.suberic.rikchik.language.display;

import javax.swing.Icon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Component;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.Word;
import net.suberic.rikchik.language.Morpheme;
import net.suberic.rikchik.language.LangFactory;
import net.suberic.rikchik.language.display.TentacleIcon;
import net.suberic.rikchik.language.display.IconFactory;
import net.suberic.util.awt.AnimatedIcon;

public class WordIcon extends AbstractRikchikAnimatedIcon{
    
    protected Word word;
    protected Word destination;

    private AnimatedIcon mIcon;
    private AnimatedIcon aIcon;
    private AnimatedIcon rIcon;
    private AnimatedIcon cIcon;

    public void setSteps(int steps){
	getIcons();

	mIcon.setSteps(steps);
	aIcon.setSteps(steps);
	rIcon.setSteps(steps);
	cIcon.setSteps(steps);

	this.steps = steps;
    }
    
    public void incrementStep(){
	if (currentStep < steps) {
	    getIcons();

	    mIcon.incrementStep();
	    aIcon.incrementStep();
	    rIcon.incrementStep();
	    cIcon.incrementStep();

	    currentStep++;
	}
    }

    public boolean isFinished(){
	return (currentStep >= steps);
    }

    public void setWord(Word w){
	this.word = w;
    }

    public Word getWord(){
	return this.word;
    }

    public void setDestination(Word d){
	this.destination = d;
    }

    private void getIcons(){
	if (mIcon == null){
	    if (destination != null){
	    mIcon = IconFactory.getMorphemeIcon
		(word.getGlyph(),
		 destination.getGlyph());
	    } else {
		mIcon = IconFactory.getMorphemeIcon(word.getGlyph());
	    }
	}
	if (aIcon == null){
	    if (destination != null){
		aIcon = IconFactory.getTentacleIcon
		    (LangFactory.getForm(word.getForm()),
		     LangFactory.getForm(destination.getForm()));
	    } else {
		aIcon = IconFactory.getTentacleIcon
		    (LangFactory.getForm(word.getForm()));
	    }
	}
	if (rIcon == null){
	    if (destination != null){
		rIcon = IconFactory.getTentacleIcon
		    (LangFactory.getRelation(word.getRelation()),
		     LangFactory.getRelation(destination.getRelation()));
	    } else {
		rIcon = IconFactory.getTentacleIcon
		    (LangFactory.getRelation(word.getRelation()));
	    }
	}
	if (cIcon == null){
	    if (destination != null){
		cIcon = IconFactory.getTentacleIcon
		    (LangFactory.getCollector(word.getFullCollector()),
		     LangFactory.getCollector(destination.getFullCollector()));
	    } else {
		cIcon = IconFactory.getTentacleIcon
		    (LangFactory.getCollector(word.getFullCollector()));
	    }
	}
    }

    public void paintIcon(Component c, Graphics g, int x, int y){
	if (g instanceof Graphics2D){ //use antialiasing if we can.
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	getIcons();
	mIcon.paintIcon(c,g,x,y);
	aIcon.paintIcon(c,g,x,y);
	rIcon.paintIcon(c,g,x,y);
	cIcon.paintIcon(c,g,x,y);
    }

}
