package net.suberic.rikchik.language.display;

import net.suberic.rikchik.language.Word;
import net.suberic.rikchik.language.Morpheme;
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
import net.suberic.rikchik.language.display.WordIcon;
import net.suberic.rikchik.language.display.MorphemeIcon;
import net.suberic.rikchik.language.display.TentacleIcon;
import net.suberic.rikchik.language.display.ArcTentacleIcon;
import net.suberic.rikchik.language.display.CircleTentacleIcon;
import net.suberic.rikchik.language.display.EllArcTentacleIcon;
import net.suberic.rikchik.language.display.FishBendTentacleIcon;
import net.suberic.rikchik.language.display.GreatArcTentacleIcon;
import net.suberic.rikchik.language.display.HalfHookTentacleIcon;
import net.suberic.rikchik.language.display.HookTentacleIcon;
import net.suberic.rikchik.language.display.LBendTentacleIcon;
import net.suberic.rikchik.language.display.LineTentacleIcon;
import net.suberic.rikchik.language.display.LobeTentacleIcon;
import net.suberic.rikchik.language.display.SquiggleTentacleIcon;
import net.suberic.rikchik.language.display.WicketTentacleIcon;
import net.suberic.rikchik.language.display.ZigZagTentacleIcon;
import net.suberic.util.awt.AnimatedIcon;
import javax.swing.Icon;

public class IconFactory{
    
    public static AnimatedIcon getWordIcon(Word word){
	return getWordIcon (word, null);
    }

    public static AnimatedIcon getWordIcon(Word word, Word destination){
	WordIcon icon = new WordIcon();
	icon.setWord(word);
	icon.setDestination(destination);
	return icon;
    }

    public static AnimatedIcon getMorphemeIcon(Morpheme morpheme){
	return getMorphemeIcon (morpheme, null);
    }
    public static AnimatedIcon getMorphemeIcon(Morpheme morpheme, Morpheme destination){
	MorphemeIcon icon = new MorphemeIcon();
	icon.setMorpheme(morpheme);
	icon.setDestination(destination);
	return icon;	
    }


    public static AnimatedIcon getTentacleIcon(Tentacle tentacle){
	return getTentacleIcon (tentacle,null);
    }
    public static AnimatedIcon getTentacleIcon(Tentacle tentacle, Tentacle destination){
        TentacleIcon retIcon;
	if (tentacle instanceof ArcTentacle) {
	    retIcon = new ArcTentacleIcon();
	} else if (tentacle instanceof CircleTentacle) {
	    retIcon = new CircleTentacleIcon();
	} else if (tentacle instanceof EllArcTentacle) {
	    retIcon = new EllArcTentacleIcon();
	} else if (tentacle instanceof FishBendTentacle) {
	    retIcon = new FishBendTentacleIcon();
	} else if (tentacle instanceof GreatArcTentacle) {
	    retIcon = new GreatArcTentacleIcon();
	} else if (tentacle instanceof HalfHookTentacle) {
	    retIcon = new HalfHookTentacleIcon();
	} else if (tentacle instanceof HookTentacle) {
	    retIcon = new HookTentacleIcon();
	} else if (tentacle instanceof LBendTentacle) {
	    retIcon = new LBendTentacleIcon();
	} else if (tentacle instanceof LineTentacle) {
	    retIcon = new LineTentacleIcon();
	} else if (tentacle instanceof LobeTentacle) {
	    retIcon = new LobeTentacleIcon();
	} else if (tentacle instanceof SquiggleTentacle) {
	    retIcon = new SquiggleTentacleIcon();
	} else if (tentacle instanceof WicketTentacle) {
	    retIcon = new WicketTentacleIcon();
	} else if (tentacle instanceof ZigZagTentacle) {
	    retIcon = new ZigZagTentacleIcon();
	} else { //do something else here
	    retIcon = new CircleTentacleIcon();
	}
        retIcon.setTentacle(tentacle);
	retIcon.setDestination(destination);
        return retIcon;
    }

}


