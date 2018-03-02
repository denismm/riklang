package net.suberic.util.awt;

import javax.swing.Icon;

public interface AnimatedIcon extends Icon{
    
    /**
       Sets the number of steps to be used from the current origin to the current destination.
     */
    public void setSteps (int steps);

    /**
       Moves the icon to the next step.
     */
    public void incrementStep();


    /**
       Whether the current animation is complete.
     */
    public boolean isFinished();

}
