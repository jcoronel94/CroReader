package core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;



//storage for the paint frame / drawing panel 

public class CentralPanelStorage {

	private JPanel mainPanel = null;
	private JScrollPane scrollPane = null;
	private JPanel sliderHolder = null;
	private JSlider rangeSlider = null;
	private JFormattedTextField goTo = null;
	private JPanel inputGoTo = null;
	private JTabbedPane tabbedPane = null; 
	private JSlider sourceSlider = null;
	private JSplitPane seqSplit = null;
	private JSplitPane seqSplitRev = null;
	private JTextPane genPane = null;
	private JTextPane detPane = null;
	private JTextPane goodSeqArea = null;
	private boolean reversed = false;
	private boolean recent = true;
	private boolean basePositionOn = true; 
	private boolean baseCallOn = true; 
	private boolean qualityScoreOn = true;
	private boolean toggleRegionOn = false;
	private int currentSeqIndex = -1;
	private int referenceHeight = 527;
	

	
	private int verticalUnit;
	private int horizontalUnit;
	private int verticalMaxVal;
	private int verticalSegmentNumber;
	private int horizontalSegmentNumber;
	private int scrollVertical;
	private int scrollHorizontal;
	private int rangeLocation;
	private int num;
	private DrawRawChannelDataPanel paintPanel;
	private DrawUnprocessedChannelDataPanel rawPaintPanel;
	
	//ABIF data
	private short[] channel1Data;
	private short[] channel2Data;
	private short[] channel3Data;
	private short[] channel4Data;
	private short[] rawchannel1Data;
	private short[] rawchannel2Data;
	private short[] rawchannel3Data;
	private short[] rawchannel4Data;
	private short[] peakLocation;
	private byte[] qualScore;
	private byte[] qualScoreNuc;
	private TreeMap<Integer, Integer> predict;
	private String fileName;
	private String sampleName;
	private Color[] colors;
	private ArrayList<Integer> coList;
	private float[] doList;
	private int threshold;
	private int threshold2;
	private int clearanceLevel;
	private int peak2ValleyDiff;
	private byte[] reverseCompNuc;
	private JTextPane rGoodSeqArea;
	private short[] reverseLocations;
	private int window;

	
	
	public JPanel getMainPanel()
	{
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel)
	{
		this.mainPanel = mainPanel;
	}

	public int getNum() 
	{
		return num;
	}

	public void setNum(int num)
	{
		this.num = num;
	}
	
	
	public int getCurrentSeqIndex() 
	{
		return currentSeqIndex;
	}

	public void setCurrentSeqIndex(int currentSeqIndex)
	{
		this.currentSeqIndex = currentSeqIndex;
	}
	
	
	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	
	public String getSampleName()
	{
		return sampleName;
	}

	public void setSampleName(String sampleName)
	{
		this.sampleName = sampleName;
	}
	
	public boolean getReversed()
	{
		return reversed;
	}

	public void setReversed(boolean reversed)
	{
		this.reversed = reversed;
	}
	
	
	public boolean getQualityScoreOn()
	{
		return qualityScoreOn;
	}

	public void setQualityScoreOn(boolean qualityScoreOn)
	{
		this.qualityScoreOn = qualityScoreOn;
	}
	
	public boolean getToggleRegionOn()
	{
		return toggleRegionOn;
	}

	public void setToggleRegionOn(boolean toggleRegionOn)
	{
		this.toggleRegionOn = toggleRegionOn;
	}
	
	public boolean getBasePositionOn()
	{
		return basePositionOn;
	}

	public void setBasePositionOn(boolean basePositionOn)
	{
		this.basePositionOn = basePositionOn;
	}
	
	
	public boolean getBaseCallOn()
	{
		return baseCallOn;
	}

	public void setBaseCallOn(boolean baseCallOn)
	{
		this.baseCallOn = baseCallOn;
	}
	
	public boolean getRecent()
	{
		return recent;
	}

	public void setRecent(boolean recent)
	{
		this.recent = recent;
	}
	
	
	public int getRangeLocation()
	{
		return rangeLocation;
	}

	public void setRangeLocation(int rangeLocation)
	{
		this.rangeLocation = rangeLocation;
	}
	
	
	public int getReferenceHeight()
	{
		return referenceHeight;
	}

	public void setReferenceHeight(int referenceHeight)
	{
		this.referenceHeight = referenceHeight;
	}
	
	public JTabbedPane getTabbedPane()
	{
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane)
	{
		this.tabbedPane = tabbedPane;
	}
	
	
	
	public JTextPane getGenPane()
	{
		return genPane;
	}

	public void setGenPane(JTextPane genPane)
	{
		this.genPane = genPane;
	}
	

	public JTextPane getDetPane()
	{
		return detPane;
	}

	public void setDetPane(JTextPane detPane)
	{
		this.detPane = detPane;
	}
	
	
	public JFormattedTextField getGoTo()
	{
		return goTo;
	}

	public void setGoTo(JFormattedTextField goTo)
	{
		this.goTo = goTo;
	}
	
	public JPanel getInputGoTo()
	{
		return inputGoTo;
	}

	public void setInputGoTo(JPanel inputGoTo)
	{
		this.inputGoTo = inputGoTo;
	}
	
	
	public JSplitPane getSeqSplit()
	{
		return seqSplit;
	}

	public void setSeqSplit(JSplitPane seqSplit)
	{
		this.seqSplit = seqSplit;
	}
	
	public JSplitPane getSeqSplitRev()
	{
		return seqSplitRev;
	}

	public void setSeqSplitRev(JSplitPane seqSplitRev)
	{
		this.seqSplitRev = seqSplitRev;
	}
	
	
	// get and set for drawing window ----------
	public JScrollPane getDrawPanel()
	{
		return scrollPane;
	}

	public void setDrawPanel(JScrollPane scrollPane)
	{
		this.scrollPane = scrollPane;
	}
	//-----------------------------------------	
	
	public int getVerticalUnit()
	{
		return verticalUnit;
	}

	public void setVerticalUnit(int verticalUnit)
	{
		this.verticalUnit = verticalUnit;
	}
	
	public int getHorizontalUnit()
	{
		return horizontalUnit;
	}

	public void setHorizontalUnit(int horizontalUnit)
	{
		this.horizontalUnit = horizontalUnit;
	}
	
	
	public int getVerticalMaxVal()
	{
		return verticalMaxVal;
	}

	public void setVerticalMaxVal(int verticalMaxVal)
	{
		this.verticalMaxVal = verticalMaxVal;
	}
	
	
	public int getVerticalSegmentNumber()
	{
		return verticalSegmentNumber;
	}

	public void setVerticalSegmentNumber(int verticalSegmentNumber)
	{
		this.verticalSegmentNumber = verticalSegmentNumber;
	}
	
	
	public int getHorizontalSegmentNumber()
	{
		return horizontalSegmentNumber;
	}

	public void setHorizontalSegmentNumber(int horizontalSegmentNumber)
	{
		this.horizontalSegmentNumber = horizontalSegmentNumber;
	}
	
	
	
	public int getScrollVertical()
	{
		return scrollVertical;
	}

	public void setScrollVertical(int scrollVertical)
	{
		this.scrollVertical = scrollVertical;
		
	}
	
	public int getScrollHorizontal()
	{
		return scrollHorizontal;
	}

	public void setScrollHorizontal(int scrollHorizontal)
	{
		this.scrollHorizontal = scrollHorizontal;
		
	}
	
	public int getThreshold()
	{
		return threshold;
	}

	public void setThreshold(int threshold)
	{
		this.threshold = threshold;
		
	}
	
	
	
	public byte[] getQualScore()
	{
		return qualScore;
	}

	public void setQualScore(byte[] qualScore)
	{
		 this.qualScore = qualScore;
	}
	
	public byte[] getQualScoreNuc()
	{
		return qualScoreNuc;
	}

	public void setQualScoreNuc(byte[] qualScoreNuc)
	{
		 this.qualScoreNuc = qualScoreNuc;
	}
	
	
	public TreeMap<Integer, Integer>  getPredict()
	{
		return predict;
	}

	public void setPredict(TreeMap<Integer, Integer> predicter)
	{
		 this.predict = predicter;
	}
	
	
	public short[] getChannel1Data()
	{
		return this.channel1Data = Arrays.copyOf(channel1Data, channel1Data.length);
	}

	public void setChannel1Data(short[] channel1Data)
	{
		 this.channel1Data = Arrays.copyOf(channel1Data, channel1Data.length);
	}
	
	public short[] getChannel2Data()
	{
		return this.channel2Data = Arrays.copyOf(channel2Data, channel2Data.length);
	}

	public void setChannel2Data(short[] channel2Data)
	{
		 this.channel2Data = Arrays.copyOf(channel2Data, channel2Data.length);
	}
	
	public short[] getChannel3Data()
	{
		return this.channel3Data = Arrays.copyOf(channel3Data, channel3Data.length);
	}

	public void setChannel3Data(short[] channel3Data)
	{
		 this.channel3Data = Arrays.copyOf(channel3Data, channel3Data.length);
	}
	
	public short[] getChannel4Data()
	{
		return this.channel4Data = Arrays.copyOf(channel4Data, channel4Data.length);
	}

	public void setChannel4Data(short[] channel4Data)
	{
		 this.channel4Data = Arrays.copyOf(channel4Data, channel4Data.length);
	}
	
	
	
	public short[] getRawChannel1Data()
	{
		return this.rawchannel1Data = Arrays.copyOf(rawchannel1Data, rawchannel1Data.length);
	}

	public void setRawChannel1Data(short[] rawchannel1Data)
	{
		 this.rawchannel1Data = Arrays.copyOf(rawchannel1Data, rawchannel1Data.length);
	}
	
	public short[] getRawChannel2Data()
	{
		return this.rawchannel2Data = Arrays.copyOf(rawchannel2Data, rawchannel2Data.length);
	}

	public void setRawChannel2Data(short[] rawchannel2Data)
	{
		 this.rawchannel2Data = Arrays.copyOf(rawchannel2Data, rawchannel2Data.length);
	}
	
	public short[] getRawChannel3Data()
	{
		return this.rawchannel3Data = Arrays.copyOf(rawchannel3Data, rawchannel3Data.length);
	}

	public void setRawChannel3Data(short[] rawchannel3Data)
	{
		 this.rawchannel3Data = Arrays.copyOf(rawchannel3Data, rawchannel3Data.length);
	}
	
	public short[] getRawChannel4Data()
	{
		return this.rawchannel4Data = Arrays.copyOf(rawchannel4Data, rawchannel4Data.length);
	}

	public void setRawChannel4Data(short[] rawchannel4Data)
	{
		 this.rawchannel4Data = Arrays.copyOf(rawchannel4Data, rawchannel4Data.length);
	}
	
	
	public short[] getPeakLocation()
	{
		return this.peakLocation = Arrays.copyOf(peakLocation, peakLocation.length);
	}

	public void setPeakLocation(short[] peakLocation)
	{
		 this.peakLocation = Arrays.copyOf(peakLocation, peakLocation.length);
	}
	
	public DrawRawChannelDataPanel getPaintPanel()
	{
		return paintPanel;
	}

	public void setPaintPanel(DrawRawChannelDataPanel paintPanel)
	{
		 this.paintPanel = paintPanel;
	}
	
	public DrawUnprocessedChannelDataPanel getRawPaintPanel()
	{
		return rawPaintPanel;
	}

	public void setRawPaintPanel(DrawUnprocessedChannelDataPanel rawPaintPanel)
	{
		 this.rawPaintPanel = rawPaintPanel;
	}
	
	
	public JPanel getSliderHolder()
	{
		return sliderHolder;
	}

	public void setSliderHolder(JPanel sliderHolder)
	{
		 this.sliderHolder = sliderHolder;
	}
	
	
	public JSlider getRangeSlider()
	{
		return rangeSlider;
	}

	public void setRangeSlider(JSlider rangeSlider)
	{
		 this.rangeSlider = rangeSlider;
	}
	
	
	public JSlider getSourceSlider()
	{
		return sourceSlider;
	}

	public void setSourceSlider(JSlider sourceSlider)
	{
		 this.sourceSlider = sourceSlider;
	}

	
	public Color[] getColors() {
		return colors;
		
	}
	
	public void setColors(Color[] colors) {
		this.colors = colors;
		
	}

	public ArrayList<Integer> getCoList(){
		return coList;
	}
	public void setCoList(ArrayList<Integer> coList) {
		this.coList = coList;
		
	}

	public float[] getDoList(){
		return doList;
	}
	public void setDoList(float[] doList) {
		this.doList = doList;
		
	}

	public JTextPane getGoodSeqArea() {
		return goodSeqArea;
		
	}
	
	public void setGoodSeqArea(JTextPane goodSeqArea) {
		this.goodSeqArea = goodSeqArea;
		
	}

	
	public int getThreshold2() {
		return threshold2;
		
	}
	public void setThreshold2(int threshold2) {
		this.threshold2 = threshold2;
		
	}
	
	public int getClearanceLevel() {
		return clearanceLevel;
		
	}

	public void setclearanceLevel(int clearanceLevel) {
		this.clearanceLevel = clearanceLevel;
		
	}
	
	public int getPeak2ValleyDiff() {
		return peak2ValleyDiff;
		
	}

	public void setPeak2ValleyDiff(int peak2ValleyDiff) {
		this.peak2ValleyDiff = peak2ValleyDiff;
		
	}

	

	public byte[] getReverseCompNuc() {
		return reverseCompNuc;
		
	}
	public void setReverseCompNuc(byte[] reverseCompNuc) {
		this.reverseCompNuc = reverseCompNuc;
		
	}

	
	public JTextPane getRGoodSeqArea() {
		return rGoodSeqArea;
		
	}
	
	public void setRGoodSeqArea(JTextPane rGoodSeqArea) {
		this.rGoodSeqArea = rGoodSeqArea;
		
	}

	
	public short[] getReverseLoc() {
		return reverseLocations;
		
	}
	
	public void setReverseLoc(short[] reverseLocations) {
		this.reverseLocations = reverseLocations;
		
	}

	
	public int getWindow() {
		return window;
	}
	public void setWindow(int window) {
		this.window = window;
	}
	

	
	

}
