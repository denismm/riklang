package net.suberic.util.awt;

import java.awt.Container;
import java.awt.FlowLayout;

public class MultipleFlowLayout extends FlowLayout{
    
    public void layoutContainer(Container target) {
	synchronized (target.getTreeLock()) {
	    Insets insets = target.getInsets();
	    int maxwidth = target.width - (insets.left + insets.right + hgap*2);
	    int nmembers = target.getComponentCount();
	    int x = 0, y = insets.top + vgap;
	    int rowh = 0, start = 0;
	    
	    boolean ltr = target.getComponentOrientation().isLeftToRight();
	    
	    for (int i = 0 ; i < nmembers ; i++) {
		Component m = target.getComponent(i);
		if (m.visible) {
		    Dimension d = m.getPreferredSize();
		    m.setSize(d.width, d.height);
		    
		    if ((x == 0) || ((x + d.width) <= maxwidth)) {
			if (x > 0) {
			    x += hgap;
			}
			x += d.width;
			rowh = Math.max(rowh, d.height);
		    } else {
			moveComponents(target, insets.left + hgap, y, maxwidth - x, rowh, start, i, ltr);
			x = d.width;
			y += vgap + rowh;
			rowh = d.height;
			start = i;
		    }
		}
	    }
	    moveComponents(target, insets.left + hgap, y, maxwidth - x, rowh, start, nmembers, ltr);
	}	
    }
    
}
