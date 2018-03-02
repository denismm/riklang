package net.suberic.rikchik.language.display;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;
import net.suberic.rikchik.language.Tentacle;
import net.suberic.rikchik.language.ArcTentacle;
import net.suberic.rikchik.language.CircleTentacle;
import net.suberic.rikchik.language.EllArcTentacle;
import net.suberic.rikchik.language.FishBendTentacle;
import net.suberic.rikchik.language.GreatArcTentacle;
import net.suberic.rikchik.language.HalfHookTentacle;
import net.suberic.rikchik.language.HookTentacle;
import net.suberic.rikchik.language.LBendTentacle;
import net.suberic.rikchik.language.LineTentacle;
import net.suberic.rikchik.language.LobeTentacle;
import net.suberic.rikchik.language.SquiggleTentacle;
import net.suberic.rikchik.language.WicketTentacle;
import net.suberic.rikchik.language.ZigZagTentacle;
import net.suberic.rikchik.language.display.TentacleIcon;
import net.suberic.rikchik.language.display.shape.Shape;

abstract public class AbstractTentacleIcon extends AbstractRikchikAnimatedIcon implements TentacleIcon{

        protected Tentacle tentacle;
    protected Tentacle destination;

    protected Shape originShape;
    protected Shape destinationShape;

    public void setSteps(int steps){
	this.steps = steps;
    }
    
    public void incrementStep(){
	if (currentStep < steps){
	    currentStep++;
	}
    }

    public boolean isFinished(){
	return (currentStep >= steps);
    }

    public void setTentacle(Tentacle t){
	this.tentacle = t;
    }

    public Tentacle getTentacle(){
	return this.tentacle;
    }

    public void setDestination(Tentacle d){
	this.destination = d;
    }
    

    public Shape getOriginShape(){
	if (this.originShape == null){
	    this.originShape = getShape();
	}
       
	return this.originShape;
    }

    public abstract Shape getShape();

    public Shape getDestinationShape(){
	
	if (this.destinationShape == null) {
	    if (destination instanceof ArcTentacle){
		destinationShape = 
		    ArcIconTransformer.getShape(destination);
	    } else if (destination instanceof CircleTentacle){
		destinationShape = 
		    CircleIconTransformer.getShape(destination);
	    } else if (destination instanceof EllArcTentacle){
		destinationShape = 
		    EllArcIconTransformer.getShape(destination);
	    } else if (destination instanceof FishBendTentacle){
		destinationShape = 
		    FishBendIconTransformer.getShape(destination);
	    } else if (destination instanceof GreatArcTentacle){
		destinationShape = 
		    GreatArcIconTransformer.getShape(destination);
	    } else if (destination instanceof HalfHookTentacle){
		destinationShape = 
		    HalfHookIconTransformer.getShape(destination);
	    } else if (destination instanceof HookTentacle){
		destinationShape = 
		    HookIconTransformer.getShape(destination);
	    } else if (destination instanceof LBendTentacle){
		destinationShape = 
		    LBendIconTransformer.getShape(destination);
	    } else if (destination instanceof LineTentacle){
		destinationShape = 
		    LineIconTransformer.getShape(destination);
	    } else if (destination instanceof LobeTentacle){
		destinationShape = 
		    LobeIconTransformer.getShape(destination);
	    } else if (destination instanceof SquiggleTentacle){
		destinationShape = 
		    SquiggleIconTransformer.getShape(destination);
	    } else if (destination instanceof WicketTentacle){
		destinationShape = 
		    WicketIconTransformer.getShape(destination);
	    } else if (destination instanceof ZigZagTentacle){
		destinationShape = 
		    ZigZagIconTransformer.getShape(destination);
	    } else {
		this.destinationShape = getOriginShape();
	    }
	}

	return this.destinationShape;
    }
    
    public void paintIcon(Component c, Graphics g, int x, int y){
	Shape orShape = getOriginShape();
	Shape deShape = getDestinationShape();
	g.translate (x,y);
	ShapeTransformer.paintShape2Shape
	    (c,g,x,y,steps,currentStep,orShape,deShape);
	g.translate (-x,-y);
    }
    
}
