package core;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.TreeMap;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;



// Listener to shut off or turn on base call information on chromatogram

public class BaseCallActionListener implements ActionListener {
	private CentralParameterStorage centralPara = null;
	ArrayList<CentralPanelStorage> centralList = null;
	private CentralPanelStorage centralPanel = null;

	private int verticalUnit;
	private byte[] qualScore;
	private byte[] qualScoreNuc;
	private short[] channel1Data;
	private short[] channel2Data;
	private short[] channel3Data;
	private short[] channel4Data;
	private short[] peakLocation;
	private Color[] colors;
	private boolean reversed;
	private boolean basePositionOn;
	private boolean baseCallOn;
	private boolean qualityScoreOn;
	private boolean toggleRegionOn;
	JTabbedPane tabbed;
	private TreeMap<Integer, Integer> predict;
	private int verticalSegmentNumber;

	public BaseCallActionListener(CentralParameterStorage centralPara, CentralPanelStorage centralPanel,
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

			// get all data from central parameter before writing
			verticalSegmentNumber = centralPanel.getVerticalSegmentNumber();
			verticalUnit = centralPanel.getVerticalUnit();
			qualScore = centralPanel.getQualScore();
			qualScoreNuc = centralPanel.getQualScoreNuc();
			channel1Data = centralPanel.getChannel1Data();
			channel2Data = centralPanel.getChannel2Data();
			channel3Data = centralPanel.getChannel3Data();
			channel4Data = centralPanel.getChannel4Data();
			peakLocation = centralPanel.getPeakLocation();
			colors = centralPanel.getColors();
			
			predict = centralPanel.getPredict();
			tabbed = centralPara.getTabbedPane();
			reversed = centralPanel.getReversed();
			basePositionOn = centralPanel.getBasePositionOn();
			baseCallOn = centralPanel.getBaseCallOn();
			qualityScoreOn= centralPanel.getQualityScoreOn();
			toggleRegionOn = centralPanel.getToggleRegionOn();


			JScrollPane scrollPane = centralPanel.getDrawPanel();
			// get current scroll values to maintain them after repaint
			int scrollVertical = scrollPane.getVerticalScrollBar().getValue();
			centralPanel.setScrollVertical(scrollVertical);
			int scrollHorizontal = scrollPane.getHorizontalScrollBar().getValue();
			centralPanel.setScrollHorizontal(scrollHorizontal);
			

			if(baseCallOn == true){

				baseCallOn = !baseCallOn;
				DrawRawChannelDataPanel paintPanel = new DrawRawChannelDataPanel(channel1Data.length + 200, verticalSegmentNumber + 200,
					 qualScore, qualScoreNuc, channel1Data, channel2Data, channel3Data,
						channel4Data, peakLocation, colors, verticalUnit, verticalSegmentNumber, 100, 100, predict, reversed,basePositionOn,baseCallOn, qualityScoreOn, toggleRegionOn);

				paintPanel.setPreferredSize(new Dimension(channel1Data.length + 200, verticalSegmentNumber + 200));
			
				scrollPane.remove(centralPanel.getPaintPanel());
				scrollPane.setSize(600, 400);
				scrollPane.getViewport().add(paintPanel);
				// maintain current position in scroll pane
				scrollPane.getVerticalScrollBar().setValue(scrollVertical);
				scrollPane.getHorizontalScrollBar().setValue(scrollHorizontal);
				//centralPanel.setDrawPanel(scrollPane);
				scrollPane.revalidate();
				scrollPane.repaint();

				centralPanel.setBaseCallOn(baseCallOn);

			}

			else if(baseCallOn == false){
				baseCallOn = !baseCallOn;
				DrawRawChannelDataPanel paintPanel = new DrawRawChannelDataPanel(channel1Data.length + 200, verticalSegmentNumber + 200,
						 qualScore, qualScoreNuc, channel1Data, channel2Data, channel3Data,
						channel4Data, peakLocation, colors, verticalUnit, verticalSegmentNumber, 100, 100, predict,reversed,basePositionOn,baseCallOn,qualityScoreOn, toggleRegionOn);

				paintPanel.setPreferredSize(new Dimension(channel1Data.length + 200, verticalSegmentNumber + 200));
				scrollPane.remove(centralPanel.getPaintPanel());
				scrollPane.setSize(600, 400);
				scrollPane.getViewport().add(paintPanel);
				
				// maintain current position in scroll pane
				scrollPane.getVerticalScrollBar().setValue(scrollVertical);
				scrollPane.getHorizontalScrollBar().setValue(scrollHorizontal);
	
				scrollPane.revalidate();
				scrollPane.repaint();

				centralPanel.setBaseCallOn(baseCallOn);

			}
		}
	}
	
}



