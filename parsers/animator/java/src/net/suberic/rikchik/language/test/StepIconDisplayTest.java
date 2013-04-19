package net.suberic.rikchik.language.test;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Icon;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Set;
import java.util.Iterator;
import net.suberic.rikchik.language.*;
import net.suberic.rikchik.language.display.*;
import net.suberic.util.awt.AnimatedIcon;

public class StepIconDisplayTest {

    private static final int steps = 16;
    private static final int delay = 500;
    private String[] args;
    private JFrame displayFrame;
    private Word displayWord;
    private Icon displayIcon;
    private JLabel displayLabel;

    public static void main(String[] args){
	AnimatedIconDisplayTest animTest = new AnimatedIconDisplayTest();
	animTest.run(args);
    }

    public void run(String[] args){
	this.args = args;
	
	displayFrame = new JFrame();
	displayFrame.getContentPane().setLayout(new FlowLayout());	
	
	displayWord = LangFactory.getWord(args[0]);
	displayIcon = IconFactory.getWordIcon(displayWord);
	displayLabel = new JLabel(displayIcon);
	displayFrame.getContentPane().add(displayLabel);
	displayFrame.addMouseListener(new PlayAnimMouseListener());
	displayFrame.pack();
	displayFrame.setVisible(true);
	
	//System.out.println("args:"+args.length);
	int temp = 0;
	try {
	    System.out.print(":");
	    temp = System.in.read();
	} catch (java.io.IOException ioe){
	    ioe.printStackTrace();
	    System.exit(1);
	}
	while (temp != 'q'){
	    for (int i = 1; i < args.length; i++){
		displayWord = LangFactory.getWord(args[i-1]);
		Word destinationWord = LangFactory.getWord(args[i]);
		AnimatedIcon animIcon = IconFactory.getWordIcon
		    (displayWord,destinationWord);
		displayLabel.setIcon (animIcon);
		for (animIcon.setSteps(steps);
		     !animIcon.isFinished();
		     animIcon.incrementStep()){
		    try {
			Thread.sleep(delay);
			//} catch (java.io.IOException ioe){
			//ioe.printStackTrace();
			//System.exit(1);
		    } catch (InterruptedException ie){
			ie.printStackTrace();
			System.exit(1);
		    }
		    displayFrame.repaint();
		}
		displayFrame.repaint();
	    }
	    System.out.println("finished");
	    try {
		System.out.print(":");
		temp = System.in.read();
	    } catch (java.io.IOException ioe){
		ioe.printStackTrace();
		System.exit(1);
	    }
	}
	System.exit(0);
	
    }

    public void playAnimation(){
    }
    
	


    public class PlayAnimMouseListener implements MouseListener{
	public void mouseClicked(MouseEvent e){
	    playAnimation();
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
    }

    
}
