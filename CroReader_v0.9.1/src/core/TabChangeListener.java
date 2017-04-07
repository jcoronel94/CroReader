package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/*
 * class used as listener to keep track of current viewing tab. All features 
 * work for the tab currently selected
 */

public class TabChangeListener implements ChangeListener{

	private CentralParameterStorage centralPara = null;
	private CentralPanelStorage centralPanel = null;
	ArrayList<CentralPanelStorage> centralList = null;

	private byte[] qualScore;
	private byte[] qualScoreNuc;
	private short[] channel1Data;
	private short[] channel2Data;
	private short[] channel3Data;
	private short[] channel4Data;
	private short[] peakLocation;
	private Color[] colors;
	private TreeMap<Integer, Integer> predict;
	private int verticalSegmentNumber;



	public TabChangeListener(CentralParameterStorage centralPara, CentralPanelStorage centralPanel, ArrayList<CentralPanelStorage> centralList){
		this.centralPara = centralPara;
		this.centralPanel = centralPanel;
		this.centralList = centralList;

	}


	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub

		JTabbedPane source = (JTabbedPane)arg0.getSource();
		source.getSelectedIndex();

		// set frame based on what user is on 
		if (source.getSelectedIndex() >= 0){
			System.out.println("this: " + source.getSelectedIndex());
			centralPara.setLastFrame(centralPara.getCurrentFrame());
			System.out.println("last: " + centralPara.getLastFrame());
			centralPara.setCurrentFrame(source.getSelectedIndex());


		}




		/*if(centralPara.getNumOpen() > 1 &&centralPanel.getRecent() == false){
			centralPanel = centralList.get(centralPara.getCurrentFrame());

			//tabbedPane.setSelectedIndex(centralPanel.getNum()-1);

			//System.out.println(tabbedPane.getSelectedIndex());

			//centralPanel = centralList.get(tabbedPane.getSelectedIndex());

		}*/


		if(centralPara.getNumOpen() > 1){
			centralPanel = centralList.get(centralPara.getCurrentFrame());
			
			//reverse checkbox
			JCheckBoxMenuItem reverseItem = centralPara.getReverseItem();
			boolean reversed = centralPanel.getReversed();
			reverseItem.setSelected(reversed);
	
			//base position items
			JCheckBoxMenuItem basePositionItem = centralPara.getBasePositionItem();
			boolean basePositionOn = centralPanel.getBasePositionOn();
			basePositionItem.setSelected(basePositionOn);


			//base call items
			JCheckBoxMenuItem baseCallItem = centralPara.getBaseCallItem();
			boolean baseCallOn = centralPanel.getBaseCallOn();
			baseCallItem.setSelected(baseCallOn);


			//Quality Score items
			JCheckBoxMenuItem qualityScoreItem = centralPara.getQualityScoreItem();
			boolean qualityScoreOn = centralPanel.getQualityScoreOn();
			qualityScoreItem.setSelected(qualityScoreOn);
			
			

			//region items
			JCheckBoxMenuItem toggleRegion = centralPara.getToggleRegion();
			boolean toggleRegionOn = centralPanel.getToggleRegionOn();
			toggleRegion.setSelected(toggleRegionOn);
			if(centralPanel.getRecent() == false){

				JSlider temp = centralPanel.getRangeSlider();
				System.out.println("Location: " +centralPanel.getRangeLocation() );
				temp.setValue(centralPanel.getRangeLocation());
				temp.revalidate();
				temp.repaint();

			}
			
			MyNucPalette mnp = centralPara.getMNP();
			NucButtons[] buttonList = centralPara.getButtonList();
			for(int i = 0; i<buttonList.length;i++){
				mnp.remove(buttonList[i]);
			}
			
			buttonList = null;
			buttonList = new NucButtons[4];
			//g
			NucButtons g = new NucButtons(mnp,centralPanel, 'G' );
			GridBagConstraints gbc = new GridBagConstraints();
			//gbc.fill = GridBagConstraints.HORIZONTAL;

			gbc.weightx = .0;
			gbc.gridx = 0;
			gbc.weighty = 0;
			gbc.insets = new Insets(-8,0,0,0);
			mnp.add(g,gbc);
			buttonList[0] = g;
			
			//a
			NucButtons a = new NucButtons(mnp,centralPanel, 'A' );
			gbc.gridx = 1;
			gbc.insets = new Insets(-8,0,0,0);
			mnp.add(a, gbc);
			buttonList[1] = a;
			
			//t
			NucButtons t = new NucButtons(mnp,centralPanel, 'T' );
			gbc.gridx = 2;
			gbc.insets = new Insets(-8,0,0,0);
			mnp.add(t, gbc);
			buttonList[2] = t;
			
			//c
			NucButtons c = new NucButtons(mnp,centralPanel, 'C' );
			gbc.gridx = 3;
			gbc.insets = new Insets(-8,0,0,0);
			mnp.add(c, gbc);
			buttonList[3] = c;
			
		
			centralPara.setButtonList(buttonList);
			mnp.repaint();
			mnp.revalidate();
			centralPara.setMNP(mnp);



			JPanel frame = centralPara.getMainPanel();		

			//try to manage all draw sizes
			if(centralPanel.getReferenceHeight()>520){

				if(frame.getHeight() > centralPanel.getReferenceHeight()){

					int taller = frame.getHeight() - centralPanel.getReferenceHeight();

					int newVertSeg = centralPanel.getVerticalSegmentNumber() + taller;

					centralPanel.setVerticalSegmentNumber(newVertSeg);
					centralPara.setWindowHeight(frame.getHeight());
					centralPanel.setReferenceHeight(frame.getHeight());
				}

				else if(frame.getHeight() < centralPanel.getReferenceHeight()){
					int shorter =centralPanel.getReferenceHeight() - frame.getHeight();

					int newVertSeg = centralPanel.getVerticalSegmentNumber() - shorter;

					centralPanel.setVerticalSegmentNumber(newVertSeg);
					//centralPara.setVerticalSegmentNumber(newVertSeg);

					centralPara.setWindowHeight(frame.getHeight());
					centralPanel.setReferenceHeight(frame.getHeight());
				}



				qualScore = centralPanel.getQualScore();
				qualScoreNuc = centralPanel.getQualScoreNuc();
				channel1Data = centralPanel.getChannel1Data();
				channel2Data = centralPanel.getChannel2Data();
				channel3Data = centralPanel.getChannel3Data();
				channel4Data = centralPanel.getChannel4Data();
				peakLocation = centralPanel.getPeakLocation();
				colors = centralPanel.getColors();
				predict = centralPanel.getPredict();
				reversed = centralPanel.getReversed();
				basePositionOn = centralPanel.getBasePositionOn();
				baseCallOn = centralPanel.getBaseCallOn();
				qualityScoreOn = centralPanel.getQualityScoreOn();
				toggleRegionOn = centralPanel.getToggleRegionOn();
				verticalSegmentNumber = centralPanel.getVerticalSegmentNumber();



				JScrollPane scrollPane = centralPanel.getDrawPanel();
				JPanel tempMainPanel = centralPara.getMainPanel();
				DrawRawChannelDataPanel paintPanel = centralPanel.getPaintPanel();

				// get current scroll values to maintain them after repaint
				int scrollVertical = scrollPane.getVerticalScrollBar().getValue();
				centralPanel.setScrollVertical(scrollVertical);
				int scrollHorizontal = scrollPane.getHorizontalScrollBar().getValue();
				centralPanel.setScrollHorizontal(scrollHorizontal);



				// change vertical unit based on slider change
				int verticalUnit = centralPanel.getVerticalUnit();
				centralPanel.setVerticalUnit(verticalUnit);

				// remove old paint panel
				scrollPane.remove(paintPanel);

				// refresh new paint panel with new verticalunit
				paintPanel = new DrawRawChannelDataPanel(channel1Data.length + 200, verticalSegmentNumber + 2400,qualScore, qualScoreNuc, channel1Data, channel2Data, channel3Data, channel4Data,
						peakLocation, colors, verticalUnit, verticalSegmentNumber, 100, 100, predict, reversed,basePositionOn,baseCallOn, qualityScoreOn, toggleRegionOn);
				paintPanel.setPreferredSize(new Dimension(channel1Data.length + 200, verticalSegmentNumber + 200));
				centralPanel.setPaintPanel(paintPanel);

				// add new paintpanel to scrollpane
				scrollPane.setSize(600, 400);
				scrollPane.getViewport().add(paintPanel);
				// maintain current position in scroll pane
				scrollPane.getVerticalScrollBar().setValue(scrollVertical);
				scrollPane.getHorizontalScrollBar().setValue(scrollHorizontal);

				JTabbedPane tabbedPane1 = centralPara.getTabbedPane();
				tabbedPane1.revalidate();
				tabbedPane1.repaint();
				centralPara.setTabbedPane(tabbedPane1);
				centralPanel.setDrawPanel(scrollPane);
				tempMainPanel.revalidate();
				tempMainPanel.repaint();
				centralPara.setMainPanel(tempMainPanel);
			}



		}
		JPanel tempPanel = centralPara.getMainPanel();
		tempPanel.revalidate();
		tempPanel.repaint();





	}

}
