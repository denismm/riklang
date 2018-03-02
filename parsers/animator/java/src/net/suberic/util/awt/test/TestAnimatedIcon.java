package net.suberic.util.awt.test;

import net.suberic.util.awt.AnimatedIcon;
import java.awt.Component;
import java.awt.Graphics;

/**
   A test icon that moves a circle from point 1 to point 2.
*/
public class TestAnimatedIcon implements AnimatedIcon{
    
    protected int steps = 8;
    protected int currentStep = 0;
    protected int height = 81;
    protected int width = 81;
    protected int orX, orY, deX, deY;
    
    public int getIconHeight() {
	return this.height;
    }

    public int getIconWidth() {
	return this.width;
    }

    public void setSteps (int steps){
	this.steps = steps;
    }

    public void incrementStep(){
	this.currentStep++;
    }

    public boolean isFinished(){
	return (currentStep == steps);
    }

    public void setOrigin(int x, int y){
	this.orX = x;
	this.orY = y;
    }

    public void setDestination(int x, int y){
	this.deX = x;
	this.deY = y;
    }

    public void paintIcon(Component c, Graphics g, int x, int y){
	g.translate (x,y);
	float partDone = (float)currentStep/steps;
	int tempX = (int)(orX + (deX - orX)*partDone);
	int tempY = (int)(orY + (deY - orY)*partDone);
	g.drawRect (orX, orY, 10, 10);
	g.drawRect (deX, deY, 10, 10);
	g.fillOval (tempX, tempY, 10, 10);
	
	g.translate (-x,-y);
	
    }
}
