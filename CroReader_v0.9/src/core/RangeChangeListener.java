package core;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


//Listener for magnifying or shrinking the chromatogram

public class RangeChangeListener implements ChangeListener {

	private CentralParameterStorage centralPara = null;
	ArrayList<CentralPanelStorage> centralList = null;
	private CentralPanelStorage centralPanel = null;
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
	private TreeMap<Integer, Integer> predict;
	private int verticalSegmentNumber;

	public RangeChangeListener(CentralParameterStorage centralPara, CentralPanelStorage centralPanel,
			ArrayList<CentralPanelStorage> centralList) {
		this.centralPara = centralPara;
		this.centralList = centralList;
		this.centralPanel = centralPanel;
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {

		centralPanel = centralList.get(centralPara.getCurrentFrame());
		// get all data from central parameter storage before try
		verticalSegmentNumber = centralPanel.getVerticalSegmentNumber();

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
		

		JSlider source = (JSlider) arg0.getSource();
		// get old panels
		JScrollPane scrollPane = centralPanel.getDrawPanel();
		JPanel tempMainPanel = centralPara.getMainPanel();
		DrawRawChannelDataPanel paintPanel = centralPanel.getPaintPanel();

		// get current scroll values to maintain them after repaint
		int scrollVertical = scrollPane.getVerticalScrollBar().getValue();
		centralPanel.setScrollVertical(scrollVertical);
		int scrollHorizontal = scrollPane.getHorizontalScrollBar().getValue();
		centralPanel.setScrollHorizontal(scrollHorizontal);

		if (source.getValueIsAdjusting()) {
			System.out.println("Something changed " + source.getValue());
			centralPanel.setRangeLocation(source.getValue());

			// change vertical unit based on slider change
			int verticalUnit = (int) Math.ceil((double) source.getValue() / (double) verticalSegmentNumber);
			centralPanel.setVerticalUnit(verticalUnit);

			// remove old paint panel
			scrollPane.remove(paintPanel);

			// refresh new paint panel with new verticalunit
			paintPanel = new DrawRawChannelDataPanel(channel1Data.length + 200, verticalSegmentNumber + 200, qualScore, qualScoreNuc, channel1Data, channel2Data, channel3Data, channel4Data,
					peakLocation, colors,verticalUnit, verticalSegmentNumber, 100, 100, predict,reversed,basePositionOn,baseCallOn, qualityScoreOn, toggleRegionOn);
			paintPanel.setPreferredSize(new Dimension(channel1Data.length + 200, verticalSegmentNumber + 200));
			centralPanel.setPaintPanel(paintPanel);

			// add new paintpanel to scrollpane
			scrollPane.setSize(600, 400);
			scrollPane.getViewport().add(paintPanel);
			// maintain current position in scroll pane
			scrollPane.getVerticalScrollBar().setValue(scrollVertical);
			scrollPane.getHorizontalScrollBar().setValue(scrollHorizontal);

			JTabbedPane tabbedPane = centralPara.getTabbedPane();
			tabbedPane.revalidate();
			tabbedPane.repaint();
			centralPara.setTabbedPane(tabbedPane);
			centralPanel.setDrawPanel(scrollPane);
			tempMainPanel.revalidate();
			tempMainPanel.repaint();
			centralPara.setMainPanel(tempMainPanel);

			centralPanel.setRangeSlider(source);
		}

	}

}
