package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractButton;



/*
 * class listener for full screen option. 
 * code is a work in progress and is not finished or functional
 */

public class FullScreenListener implements ActionListener {
	private CentralParameterStorage centralPara = null;
	ArrayList<CentralPanelStorage> centralList = null;
	private CentralPanelStorage centralPanel = null;



	public FullScreenListener(CentralParameterStorage centralPara, CentralPanelStorage centralPanel,
			ArrayList<CentralPanelStorage> centralList) {
		this.centralPara = centralPara;
		this.centralPanel = centralPanel;
		this.centralList = centralList;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		AbstractButton abstractButton = (AbstractButton) ae.getSource();
		boolean selected = abstractButton.getModel().isSelected();
		ABIFframe frame = centralPara.getMainFrame();
		//System.out.println(frame.isValid());

	
		//System.out.print(selected);
		if(selected){
			//frame.setExtendedState(Frame.MAXIMIZED_BOTH); 
			//frame.setVisible(false);
			//frame.setUndecorated(true);
			//frame.setVisible(true);

		}

		else{
		
		}
	}

}
