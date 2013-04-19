package net.suberic.rikchik.language.test;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Icon;
import java.awt.FlowLayout;
import java.util.Set;
import java.util.Iterator;
import net.suberic.rikchik.language.*;
import net.suberic.rikchik.language.display.*;

public class IconDisplayTest {

    public static void main(String[] args){
	JFrame displayFrame = new JFrame();
	displayFrame.getContentPane().setLayout(new FlowLayout());	
	
	for (int i = 0; i < args.length; i++){
	    Word displayWord = LangFactory.getWord(args[i]);
	    Icon displayIcon = IconFactory.getWordIcon(displayWord);
	    JLabel displayLabel = new JLabel(displayIcon);
	    displayFrame.getContentPane().add(displayLabel);
	}
	displayFrame.pack();
	displayFrame.setVisible(true);
    }

}
