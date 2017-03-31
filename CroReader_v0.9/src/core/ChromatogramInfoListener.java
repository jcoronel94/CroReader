package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;


/*Listener for opening chromatogram information about the active chromatogram.
*note that this class is older and does get reverse information from central panel.
*but instead computes it. This is redunant and should be replaced.  
*/

public class ChromatogramInfoListener implements ActionListener {


	private CentralParameterStorage centralPara = null;
	ArrayList<CentralPanelStorage> centralList = null;
	private CentralPanelStorage centralPanel = null;
	private JSplitPane seqSplit = null;
	private JTextPane goodSeqArea= null;

	public ChromatogramInfoListener(CentralParameterStorage centralPara, CentralPanelStorage centralPanel,
			ArrayList<CentralPanelStorage> centralList) {
		this.centralPara = centralPara;
		this.centralPanel = centralPanel;
		this.centralList = centralList;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		//get current frame
		if(centralPara.getNumOpen() > 0){
			centralPanel = centralList.get(centralPara.getCurrentFrame());


			//JSplitPane seqSplit = centralPanel.getSeqSplit();
			JTextPane genPane = centralPanel.getGenPane(); 
			JTextPane detPane = centralPanel.getDetPane();
		

			//create the infoframe and get mainframe for referenence

			ABIFframe parent = centralPara.getMainFrame();
			JPanel infoPanel = new JPanel();

			//create tab pane for all info 
			JTabbedPane infoTabbedPane = new JTabbedPane();
			infoPanel.add(infoTabbedPane); //add tab to mainpanel
			infoTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

			//
			if (centralPanel.getReversed() == false){
				seqSplit = centralPanel.getSeqSplit();
				goodSeqArea = centralPanel.getGoodSeqArea();
				
				
			}

			//if reversed create complement for tabbed pane
			else if(centralPanel.getReversed() == true){
				goodSeqArea = centralPanel.getRGoodSeqArea();
				//if its the first time, make it
				if(centralPanel.getSeqSplitRev() == null){

					seqSplit = new JSplitPane();
					seqSplit.setDividerSize(0); 

					byte[] qualScoreNuc = centralPanel.getQualScoreNuc();

					byte[] reversedNucs = new byte[qualScoreNuc.length];

					for(int i = 0; i<qualScoreNuc.length; i++){
						if(String.valueOf((char) qualScoreNuc[i]).equals("T") ){
							reversedNucs[qualScoreNuc.length -i-1] = 'A';
						}
						else if(String.valueOf((char) qualScoreNuc[i]).equals("A")  ){
							reversedNucs[qualScoreNuc.length -i-1] = 'T';

						}
						else if(String.valueOf((char) qualScoreNuc[i]).equals("G") ){
							reversedNucs[qualScoreNuc.length -i-1] = 'C';

						}
						else if(String.valueOf((char) qualScoreNuc[i]).equals("C")  ){
							reversedNucs[qualScoreNuc.length -i-1] = 'G';

						}

						else if(String.valueOf((char) qualScoreNuc[i]).equals("N") ){
							reversedNucs[qualScoreNuc.length -i-1] = 'N';

						}
					}



					JTextPane seqArea = new JTextPane();
					seqArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
					seqArea.setBackground(new Color(220,220,220));
					ContextMenuMouseListener rightClick = new ContextMenuMouseListener();
					seqArea.addMouseListener(rightClick);

					//number area 
					JTextPane numArea = new JTextPane();
					numArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
					numArea.setBackground(new Color(200,200,200));
					numArea.setHighlighter(null);
					seqSplit.setRightComponent(seqArea);
					seqSplit.setLeftComponent(numArea);

					appendToPane(numArea, "   1\n", Color.BLUE);
					for(int i = 0; i <reversedNucs.length; i++){

						if(i%40 ==0 && i != 0){
							appendToPane(seqArea, "\n", Color.WHITE);
							if(String.valueOf(i).length() ==2){
								appendToPane(numArea, "  " + (i+1) + "\n", Color.blue);

							}

							else if(String.valueOf(i).length() == 3){
								appendToPane(numArea, " " + (i+1) + "\n", Color.blue);
							}
							else{
								appendToPane(numArea, (i+1) + "\n", Color.blue);

							}
						}

						if((i%10 ==0 || i%20 == 0 || i%30 ==0) && i%40 != 0){
							appendToPane(seqArea, " ", Color.white);
						}

						String nuc = String.valueOf((char) reversedNucs[i]);
						appendToPane(seqArea, nuc, Color.BLACK);
					}
					seqArea.setEditable(false);
					numArea.setEditable(false);
					centralPanel.setSeqSplitRev(seqSplit);
				}

				//if its the second time, pull it from memory
				else{
					seqSplit = centralPanel.getSeqSplitRev();
				}
				
			}//end condition



			//create the scrollbar for sequence area
			JScrollPane seqScrollPane = new JScrollPane();
			seqScrollPane.setSize(485,400);
			seqScrollPane.setPreferredSize(new Dimension( 420,400));
			seqScrollPane.getViewport().add(seqSplit, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


			//create the scrollbar for gen area area
			JScrollPane genScrollPane = new JScrollPane();
			genScrollPane.setSize(485,400);
			genScrollPane.setPreferredSize(new Dimension( 420,400));
			genScrollPane.getViewport().add(genPane, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

			//create the scrollbar for details area area
			JScrollPane detScrollPane = new JScrollPane();
			detScrollPane.setSize(485,400);
			detScrollPane.setPreferredSize(new Dimension( 420,400));
			detScrollPane.getViewport().add(detPane, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

			
			JScrollPane goodSeqAreaPane = new JScrollPane();
			goodSeqAreaPane.setSize(485,400);
			goodSeqAreaPane.setPreferredSize(new Dimension(420, 400));
			goodSeqAreaPane.getViewport().add(goodSeqArea, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


			//infoTabbedPane.setPreferredSize(new Dimension(475,450));


			//add all sections to tabbed pane
			infoTabbedPane.addTab("Sequence", seqScrollPane);
			infoTabbedPane.addTab("Good Region List", goodSeqAreaPane);
			infoTabbedPane.addTab("General",genScrollPane);
			infoTabbedPane.addTab("Details", detScrollPane);

			//create custom dialog box, point to parent, give name, modal true, pass component
			ChromDialog dialogBox= new ChromDialog(parent,"Chromatogram Info", true, infoPanel);

			dialogBox.setVisible(true);

		}

	}

	// method for adding strings to pane
	private void appendToPane(JTextPane tp, String msg, Color c)
	{
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);



		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_CENTER);

		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		tp.replaceSelection(msg);
	}

}
