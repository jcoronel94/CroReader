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


//Listener for moving to the searched sequence on the chromatogram. Will continue to next matched sequence until one cannot be found

public class SeqFinderActionListener implements ActionListener {
	private CentralParameterStorage centralPara = null;
	ArrayList<CentralPanelStorage> centralList = null;
	private CentralPanelStorage centralPanel = null;
	private byte[] qualScoreNuc;
	private byte[] revQualScoreNuc;
	private short[] peakLocation;
	private short[] reverseLocations;
	private JTextField seqFinder;
	private boolean isReversed;
	JTabbedPane tabbed;


	public SeqFinderActionListener(CentralParameterStorage centralPara, CentralPanelStorage centralPanel,
			ArrayList<CentralPanelStorage> centralList, JTextField seqFinder) {
		this.centralPara = centralPara;
		this.centralPanel = centralPanel;
		this.centralList = centralList;
		this.seqFinder = seqFinder;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		centralPanel = centralList.get(centralPara.getCurrentFrame());
		PlainDocument doc = (PlainDocument) seqFinder.getDocument();
		doc.setDocumentFilter(new MyStringFilter());

		//retrieve user input 
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(doc.getText(0, doc.getLength()));
		} catch (BadLocationException e1) {

			e1.printStackTrace();
		}

		//get scroll to go to correct sequence
		JScrollPane scrollPane = centralPanel.getDrawPanel();
		peakLocation = centralPanel.getPeakLocation();
		qualScoreNuc = centralPanel.getQualScoreNuc();
		revQualScoreNuc = centralPanel.getReverseCompNuc();
		reverseLocations = centralPanel.getReverseLoc();
		isReversed = centralPanel.getReversed();

		//compare to uppercase values in qualscorenucs 
		String seq;
		seq = sb.toString().toUpperCase();

		boolean isWhitespace = seq.matches("^\\s*$");


		StringBuilder qual = new StringBuilder();

		if(!isReversed){
			for(int i = 0; i<qualScoreNuc.length;i++){
				qual.append((char)qualScoreNuc[i]);
			}
		}
		else{
			for(int i = 0; i<revQualScoreNuc.length;i++){
				qual.append((char)revQualScoreNuc[i]);
			}

		}


		//find all match within sequence and move to it, save last location and start from there next time
		//if end of sequence is met catch error and restart location to 0
		if(!isWhitespace){

			int i= centralPanel.getCurrentSeqIndex();
			while(i<qual.length()){
				i++;
				try{
					if(seq.equals(qual.substring(i, i+seq.length()))){
						if(!isReversed){
							scrollPane.getHorizontalScrollBar().setValue(peakLocation[i]+100);
						}
						else{
							scrollPane.getHorizontalScrollBar().setValue(reverseLocations[i]+100);
						}
						centralPanel.setCurrentSeqIndex(i);
						break;
					}
				} catch(StringIndexOutOfBoundsException except){
					centralPanel.setCurrentSeqIndex(-1);
					JOptionPane.showMessageDialog(centralPara.getMainFrame(), "String could not be found  before end of file" );
					break;
				}


				//JOptionPane.showMessageDialog(centralPara.getMainFrame(), "Pattern not found" );


			}
		}

		else{
			JOptionPane.showMessageDialog(centralPara.getMainFrame(), "Please enter a string" );
		}


		//		System.out.println(qual.toString());
		//		System.out.println(sb.toString());
	}
}