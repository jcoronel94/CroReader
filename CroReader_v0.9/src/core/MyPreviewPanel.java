package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/*custom preview panel for all JColorchoosers
* only displays previous color and current color
* in a rectangle
*/

class MyPreviewPanel extends JComponent {
	  Color curColor;
	  Color chaColor;
	  public MyPreviewPanel(JColorChooser chooser) {
	    curColor = chooser.getColor();
	   
	   
	    setPreferredSize(new Dimension(50, 50));
	  }
	  public void paint(Graphics g) {
	    g.setColor(curColor);
	    g.fillRect(0, 0, getWidth() - 1, getHeight() - getHeight()/2);
	    g.setColor(chaColor);
	    g.fillRect(0, getHeight() - getHeight()/2, getWidth() - 1, getHeight() - 1);
	  }
	  
	  public void setColor(Color c){
		  chaColor = c;
	  }
	  
	 
	}