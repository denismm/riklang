package net.suberic.rikchik.language.test;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Icon;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Set;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import net.suberic.rikchik.language.*;
import net.suberic.rikchik.language.display.*;
import net.suberic.util.awt.AnimatedIcon;

public class AnimatedIconDisplayTest {

    private static final int steps = 16;
    private static final int delay = 125;
    private static final int scale = 2;
    private static final int margin = 10;
    private String[] args;
    private JFrame displayFrame;
    private Word displayWord;
    private Icon displayIcon;
    private JLabel displayLabel;
    private static final String[] DEFAULT_ARGS = {"Happy-End-V-0"};
    private BufferedReader br;

    public static void main(String[] args){
	AnimatedIconDisplayTest animTest = new AnimatedIconDisplayTest();
	animTest.run(args);
    }

    public void run(String[] args){
	DisplayData.setScale(scale);
	DisplayData.setMargin(margin);
	if (args.length == 0){
	    args = DEFAULT_ARGS;
	}
	this.args = args;
	
	displayFrame = new JFrame();
	displayFrame.getContentPane().setLayout(new FlowLayout());	
	
	displayWord = LangFactory.getWord(this.args[0]);
	displayIcon = IconFactory.getWordIcon(displayWord);
	displayLabel = new JLabel(displayIcon);
	displayFrame.getContentPane().add(displayLabel);
	displayFrame.addMouseListener(new PlayAnimMouseListener());
	displayFrame.pack();
	displayFrame.setVisible(true);
	
	//System.out.println("args:"+args.length);
	int temp = 0;
	
	br = new BufferedReader 
	    (new InputStreamReader (System.in));
	
	temp = getChar();
	
	while (true){
	    switch (temp) {
	    case 'q':
		System.exit(0);
		break;
	    case 'n':
		getNewArgs();
		break;
	    default:
		playAnimation();	
	    }
	    temp = getChar();
	}
    }

    public int getChar(){
	int temp = 0;
	try {
	    System.out.print("p, n, or q:");
	    String tempLine = br.readLine();
	    if (tempLine.length() > 0){
		temp = tempLine.charAt(0);
	    } else {
		temp = 0;
	    }
	} catch (java.io.IOException ioe){
	    ioe.printStackTrace();
	    System.exit(1);
	}
	return temp;
   }

    public void getNewArgs(){
	System.out.print("Please enter the new utterance in ASCII form:");
	String line = "";
	try {
	    BufferedReader br = new BufferedReader 
		(new InputStreamReader (System.in));
	    line = br.readLine();
	} catch (java.io.IOException ioe){
	    ioe.printStackTrace();
	    System.exit(1);
	}
	
	StringTokenizer argT = new StringTokenizer (line, " ");
	int argNum = argT.countTokens();
	args = new String[argNum];
	for (int i = 0; i < argNum; i++){
	    String tempArg = argT.nextToken();
	    args[i] = tempArg;
	}
	if (args.length == 0){
	    this.args = DEFAULT_ARGS;
	}

	initializeDisplay();
    }

    public void initializeDisplay(){
	displayWord = LangFactory.getWord(args[0]);
	displayIcon = IconFactory.getWordIcon(displayWord);
	displayLabel.setIcon (displayIcon);
	displayFrame.repaint();
    }

    public void playAnimation(){
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
		    } catch (InterruptedException ie){
			ie.printStackTrace();
			System.exit(1);
		    }
		    displayFrame.repaint();
		}
		displayFrame.repaint();
	    }
	    System.out.println("finished");
	    displayFrame.repaint();

	    try {
		Thread.sleep(delay*steps);
	    } catch (InterruptedException ie){
		ie.printStackTrace();
		System.exit(1);
	    }
	    initializeDisplay();
	    
    }
    
	


    public class PlayAnimMouseListener implements MouseListener{
	public void mouseClicked(MouseEvent e){
	    // playAnimation();
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
    }

    
}
