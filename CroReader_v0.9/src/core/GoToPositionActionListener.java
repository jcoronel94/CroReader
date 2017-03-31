package core;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

//not implemented yet
public class GoToPositionActionListener implements ActionListener {
	private CentralParameterStorage centralPara = null;
	ArrayList<CentralPanelStorage> centralList = null;
	private CentralPanelStorage centralPanel = null;
	private JTextField goTo;
	JTabbedPane tabbed;


	public GoToPositionActionListener(CentralParameterStorage centralPara, CentralPanelStorage centralPanel,
			ArrayList<CentralPanelStorage> centralList, JTextField goTo) {
		this.centralPara = centralPara;
		this.centralPanel = centralPanel;
		this.centralList = centralList;
		this.goTo = goTo;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		centralPanel = centralList.get(centralPara.getCurrentFrame());
		PlainDocument doc = (PlainDocument) goTo.getDocument();

		//sb.insert(offset, string);
		doc.setDocumentFilter(new MyIntFilter());
		//System.out.println(sb);

		StringBuilder sb = new StringBuilder();
		try {
			sb.append(doc.getText(0, doc.getLength()));
		} catch (BadLocationException e1) {

			e1.printStackTrace();
		}


		JScrollPane scrollPane = centralPanel.getDrawPanel();
		short[] peaks = centralPanel.getPeakLocation();
		short[] reverseLocations = centralPanel.getReverseLoc();
		boolean isReversed = centralPanel.getReversed();

		//in the case enter is pressed with no input
		int base =-1;
		try{
			base = Integer.valueOf(sb.toString());
		} catch(NumberFormatException nf){
			JOptionPane.showMessageDialog(centralPara.getMainFrame(), "Please insert base value between 0 and " + (peaks.length -1) );
		}

		//move if between range
		if(base<peaks.length-1 && base>0){
			base = base -1;
			if(!isReversed){

				scrollPane.getHorizontalScrollBar().setValue(peaks[base]+100);
			}
			else{
				scrollPane.getHorizontalScrollBar().setValue(reverseLocations[base]+100);
			}
		}

		//error msg if 0 
		else if(base == 0)
			JOptionPane.showMessageDialog(centralPara.getMainFrame(), "Please insert base value greater than 0");

		// error if trying to scroll out of window 
		else if(base >= peaks.length-1 )
			JOptionPane.showMessageDialog(centralPara.getMainFrame(), "Please insert base value lower than " + (peaks.length-1));

		//remove filter to reset vale 
		doc.setDocumentFilter(null);
		goTo.setText(null);

		//reset documentfilter
		doc.setDocumentFilter(new MyIntFilter());


	}
}