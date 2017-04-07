package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.MenuBar;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

public class ABIFseqFileReaderEntry
{

	public static void main(String args[]) {
		try {
			//javax.swing.plaf.multi.MultiLookAndFeel
			//UIManager.setLookAndFeel("javax.swing.plaf.multi.MultiLookAndFeel");
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			//System.out.println(UIManager.getSystemLookAndFeelClassName());


		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//     //</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				CentralParameterStorage centralPara = new CentralParameterStorage();
				ABIFframe mainFrame = new ABIFframe(centralPara);
				Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/CR.gif"));
				//URL url = getClass().getResource("/CR.gif");
				mainFrame.setIconImage(image);
				//mainFrame.setUndecorated(true);
				mainFrame.setVisible(true);
				centralPara.setMainFrame(mainFrame);
				//new ABIFframe(centralPara).setVisible(true);
			}
		});
	}
//	



}
