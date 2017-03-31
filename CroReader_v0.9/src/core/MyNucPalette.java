package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

class MyNucPalette extends JComponent {
	 Color curColor;
	
	 public MyNucPalette(JPanel mainPanel) {
	
		  setPreferredSize(new Dimension(131, 46));
		  
		 // curColor = new Color(242, 242, 242);	
		  curColor = Color.GRAY;
				  //new Color(140, 140, 140);
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(curColor);
	    g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
	   
	  }

}
