package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class ResizeComponentListener implements ComponentListener {

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


	public ResizeComponentListener(CentralParameterStorage centralPara, CentralPanelStorage centralPanel,
			ArrayList<CentralPanelStorage> centralList)  {
		this.centralPara = centralPara;
		this.centralPanel = centralPanel;
		this.centralList = centralList;


	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub

		if(centralPara.getNumOpen() > 0){
			centralPanel = centralList.get(centralPara.getCurrentFrame());
			JPanel frame = centralPara.getMainPanel();
			//centralPara.setWindowHeight(frame.getHeight());

			if(frame.getHeight()>520){

				if(frame.getHeight() > centralPara.getWindowHeight()){

					int taller = frame.getHeight() - centralPara.getWindowHeight();

					int newVertSeg = centralPanel.getVerticalSegmentNumber() + taller;

					centralPanel.setVerticalSegmentNumber(newVertSeg);
					//centralPara.setVerticalSegmentNumber(newVertSeg);

					centralPara.setWindowHeight(frame.getHeight());
					centralPanel.setReferenceHeight(frame.getHeight());
					
//					JComponent rBox = centralPara.getrBox();
//					
//					
//					rBox.setPreferredSize(new Dimension(10+taller,0));
//					//setSize(new Dimension(10+taller,0));
//					rBox.repaint();
				}

				else{
					int shorter = centralPara.getWindowHeight() - frame.getHeight();

					int newVertSeg = centralPanel.getVerticalSegmentNumber() - shorter;

					centralPanel.setVerticalSegmentNumber(newVertSeg);
					//centralPara.setVerticalSegmentNumber(newVertSeg);

					centralPara.setWindowHeight(frame.getHeight());
					centralPanel.setReferenceHeight(frame.getHeight());
					
//					JComponent rBox = centralPara.getrBox();
//					
//					rBox.setSize(new Dimension(10+shorter,0));
//					rBox.repaint();
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
				verticalSegmentNumber = centralPanel.getVerticalSegmentNumber();
				toggleRegionOn = centralPanel.getToggleRegionOn();
				



				JScrollPane scrollPane = centralPanel.getDrawPanel();
				JPanel tempMainPanel = centralPara.getMainPanel();
				DrawRawChannelDataPanel paintPanel = centralPanel.getPaintPanel();

				// get current scroll values to maintain them after repaint
				int scrollVertical = scrollPane.getVerticalScrollBar().getValue();
				centralPanel.setScrollVertical(scrollVertical);
				int scrollHorizontal = scrollPane.getHorizontalScrollBar().getValue();
				centralPanel.setScrollHorizontal(scrollHorizontal);



				// change vertical unit based on slider changep
				//int verticalUnit = (int) Math.ceil((double) centralPanel.getVerticalMaxVal() / (double) verticalSegmentNumber);
				int verticalUnit = centralPanel.getVerticalUnit();
				centralPanel.setVerticalUnit(verticalUnit);

				// remove old paint panel
				scrollPane.remove(paintPanel);

				// refresh new paint panel with new verticalunit
				paintPanel = new DrawRawChannelDataPanel(channel1Data.length + 200, verticalSegmentNumber + 2400, qualScore, qualScoreNuc, channel1Data, channel2Data, channel3Data, channel4Data,
						peakLocation, colors, verticalUnit, verticalSegmentNumber, 100, 100, predict,reversed,basePositionOn,baseCallOn, qualityScoreOn, toggleRegionOn);
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
			}
			else{
				centralPara.setWindowHeight(527);
			}

		}
		
		
		centralPara.setWindowHeight(centralPara.getMainPanel().getHeight());


	}
	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}
}
