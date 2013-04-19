package net.suberic.util.awt.test;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Icon;
import java.awt.FlowLayout;
import net.suberic.util.awt.test.TestAnimatedIcon;
import net.suberic.util.awt.AnimatedIcon;

public class AnimatedIconTest{
    
    public static void main(String[] args){
	JFrame displayFrame = new JFrame();
	displayFrame.getContentPane().setLayout(new FlowLayout());	
	TestAnimatedIcon testIcon = new TestAnimatedIcon();
	testIcon.setOrigin(0,0);
	testIcon.setDestination(40,60);
	AnimatedIcon animIcon = testIcon;
	JLabel animLabel = new JLabel(animIcon);
	displayFrame.getContentPane().add(animLabel);
	displayFrame.pack();
	displayFrame.setVisible(true);
	for (animIcon.setSteps(16);!animIcon.isFinished();animIcon.incrementStep()){
	    try {
		Thread.sleep(2000);
	    } catch (InterruptedException ie){
		ie.printStackTrace();
		System.exit(1);
	    }
	    displayFrame.repaint();
	}
	
	

    }

}
