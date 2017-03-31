package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;


//Listener closing the active chromatogram. 
//If it is the last active chromatogram then buttons are disabled

public class closeFileActionListener implements ActionListener {

	// variables for parameter passing
	public static String inputFsaFileLocation = null;
	private CentralParameterStorage centralPara = null;
	ArrayList<CentralPanelStorage> centralList = null;
	private CentralPanelStorage centralPanel = null;


	DrawRawChannelDataPanel paintPanel;
	JPanel tempPanel;

	public closeFileActionListener(CentralParameterStorage centralPara, CentralPanelStorage centralPanel,
			ArrayList<CentralPanelStorage> centralList) {
		this.centralPara = centralPara;
		this.centralPanel = centralPanel;
		this.centralList = centralList;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int frameNum = 0;
		int numOpen = 0;
		if (centralPara.getNumOpen() != 0) {
			// get current frame
			centralPanel = centralList.get(centralPara.getCurrentFrame());

			// get old panels and delete old paintpanel
			JScrollPane scrollPane = centralPanel.getDrawPanel();
			JPanel tempMainPanel = centralPara.getMainPanel();
			DrawRawChannelDataPanel paintPanel = centralPanel.getPaintPanel();
			scrollPane.remove(paintPanel);
			paintPanel = null;
			centralPara.setPaintPanel(paintPanel);

			JTabbedPane tabbedPane = centralPara.getTabbedPane();
			tabbedPane.remove(centralPara.getCurrentFrame());
			centralPara.setTabbedPane(tabbedPane);


			numOpen = centralPara.getNumOpen(); // lets say 3

			// I believe this works

			for (frameNum = centralPara.getCurrentFrame(); frameNum < numOpen; frameNum++) {
				System.out.println(frameNum);
				centralList.get(frameNum).setNum(frameNum - 1);
				// System.out.println(numOpen);
			}

			centralPara.setNumOpen(numOpen - 1);// now 2



			centralList.remove(centralPara.getCurrentFrame());

			try{
				centralPanel = centralList.get(centralPara.getCurrentFrame());

				MyNucPalette mnp1 = centralPara.getMNP();
				NucButtons[] buttonList1 = centralPara.getButtonList();
				for(int i = 0; i<buttonList1.length;i++){
					mnp1.remove(buttonList1[i]);
				}

				buttonList1 = null;
				buttonList1 = new NucButtons[4];
				//g
				NucButtons g = new NucButtons(mnp1,centralPanel, 'G' );
				GridBagConstraints gbc = new GridBagConstraints();
				//gbc.fill = GridBagConstraints.HORIZONTAL;

				gbc.weightx = .0;
				gbc.gridx = 0;
				gbc.weighty = 0;
				gbc.insets = new Insets(-8,0,0,0);
				mnp1.add(g,gbc);
				buttonList1[0] = g;

				//a
				NucButtons a = new NucButtons(mnp1,centralPanel, 'A' );
				gbc.gridx = 1;
				gbc.insets = new Insets(-8,0,0,0);
				mnp1.add(a, gbc);
				buttonList1[1] = a;

				//t
				NucButtons t = new NucButtons(mnp1,centralPanel, 'T' );
				gbc.gridx = 2;
				gbc.insets = new Insets(-8,0,0,0);
				mnp1.add(t, gbc);
				buttonList1[2] = t;

				//c
				NucButtons c = new NucButtons(mnp1,centralPanel, 'C' );
				gbc.gridx = 3;
				gbc.insets = new Insets(-8,0,0,0);
				mnp1.add(c, gbc);
				buttonList1[3] = c;


				centralPara.setButtonList(buttonList1);
				mnp1.repaint();
				mnp1.revalidate();
				centralPara.setMNP(mnp1);
			}catch(IndexOutOfBoundsException e){
				System.out.println("caught");
			}

			// if only the first window was removed
			if (frameNum == 1) {

				JPanel outputWindow = new JPanel();
				outputWindow.setBackground(new Color(255, 255, 255));
				outputWindow.setPreferredSize(new Dimension(600, 400));
				scrollPane.setSize(600, 400);
				scrollPane.getViewport().add(outputWindow);
				centralPara.setDrawPanel(scrollPane);
				tabbedPane.removeAll();
				tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
				tabbedPane.add(scrollPane);
				TabChangeListener tabListener = new TabChangeListener(centralPara, centralPanel, centralList);
				tabbedPane.addChangeListener(tabListener);
				tabbedPane.setVisible(false);

				//remove nuc buttons
				MyNucPalette mnp = centralPara.getMNP();
				NucButtons[] buttonList = centralPara.getButtonList();
				for(int i = 0; i<buttonList.length;i++){
					mnp.remove(buttonList[i]);
				}

				buttonList = null;
				centralPara.setButtonList(buttonList);
				mnp.repaint();
				mnp.revalidate();
				centralPara.setMNP(mnp);

				// add changes to centralStorage and refresh
				centralPara.setDrawPanel(scrollPane);
				centralPara.setTabbedPane(tabbedPane);
				tempMainPanel.revalidate();
				tempMainPanel.repaint();
				centralPara.setMainPanel(tempMainPanel);

				centralPara.setStart(true);

				//make menu items unavailable
				centralPara.getcloseMenuItem().setEnabled(false);
				centralPara.getPrintMenuItem().setEnabled(false);
				centralPara.getExportMenuItem().setEnabled(false);
				centralPara.getProcessCurrentItem().setEnabled(false);
				centralPara.getBaseCallItem().setEnabled(false);
				centralPara.getThresholdItem().setEnabled(false);
				centralPara.getToggleRegion().setEnabled(false);
				centralPara.getBasePositionItem().setEnabled(false);
				centralPara.getQualityScoreItem().setEnabled(false);
				centralPara.getChromInfoItem().setEnabled(false);
				centralPara.getRawInfoItem().setEnabled(false);
				centralPara.getReverseItem().setEnabled(false);
				centralPara.getGoTo().setEnabled(false);
				centralPara.getSeqFinder().setEnabled(false);
				centralPara.getGoButton().setEnabled(false);
				centralPara.getFindButton().setEnabled(false);
				//make the checks back to default
				centralPara.getBaseCallItem().setSelected(true);
				centralPara.getBasePositionItem().setSelected(true);
				centralPara.getQualityScoreItem().setSelected(true);
				centralPara.getReverseItem().setSelected(false);
				centralPara.getToggleRegion().setSelected(false);
				
				
				//remove the slider
				centralPara.getSliderHolder().removeAll();
			}

		}

	}
}
