package core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;


// Listener for opening a pop-up window of raw chromatogram

public class RawDataInfoListener implements ActionListener {

	private CentralParameterStorage centralPara = null;
	ArrayList<CentralPanelStorage> centralList = null;
	private CentralPanelStorage centralPanel = null;
	

	public RawDataInfoListener(CentralParameterStorage centralPara, CentralPanelStorage centralPanel,
			ArrayList<CentralPanelStorage> centralList) {
		this.centralPara = centralPara;
		this.centralPanel = centralPanel;
		this.centralList = centralList;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(centralPara.getNumOpen() > 0){
			centralPanel = centralList.get(centralPara.getCurrentFrame());

			String fileName = centralPanel.getFileName();
		

			//create the infopanel and get mainframe for referenence
			JPanel pane = new JPanel();
			pane.setLayout(new BorderLayout());
			ABIFframe parent = centralPara.getMainFrame();

		
			//draw the raw data 
			DrawUnprocessedChannelDataPanel rawPaintPanel = centralPanel.getRawPaintPanel();
			rawPaintPanel.setPreferredSize(new Dimension(centralPanel.getRawChannel1Data().length + 200, centralPanel.getVerticalSegmentNumber() + 200));
			//create the scrollbar for raw data
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setPreferredSize(new Dimension( 600,400));

		
			scrollPane.getViewport().add(rawPaintPanel);

			pane.add(scrollPane, BorderLayout.CENTER);
			
			//create custom dialog box, point to parent, give name, modal true, pass component
			ChromDialog dialogBox= new ChromDialog(parent,"Raw Data: " + fileName, false, pane);
			dialogBox.setVisible(true);


		}
	}


}
