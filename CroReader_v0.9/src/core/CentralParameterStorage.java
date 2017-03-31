package core;


import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;


//storage for the main frame 
public class CentralParameterStorage {

	private JPanel mainPanel = null;
	private ABIFframe mainFrame = null;
	private JScrollPane scrollPane = null;
	private JPanel sliderHolder = null;
	private JSlider rangeSlider = null;
	private JTextField goTo = null;
	private JTextField seqFinder = null;
	private JPanel inputGoTo = null;
	private JTabbedPane tabbedPane = null; 
	private JMenuItem closeMenuItem = null;
	private JMenuItem exportMenuItem = null;
	private JMenuItem printMenuItem = null;
	private JMenuItem chromInfoItem = null;
	private JMenuItem rawInfoItem = null;
	private JMenuItem thresholdItem = null;
	private JCheckBoxMenuItem reverseItem = null;
	private JCheckBoxMenuItem basePositionItem = null;
	private JCheckBoxMenuItem baseCallItem = null;
	private JCheckBoxMenuItem qualityScoreItem = null;
	private JCheckBoxMenuItem toggleRegion = null;
	private JButton goButton = null;
	private JButton findButton = null;
	private MyNucPalette mnp;
	
	private int verticalUnit;
	private boolean start;
	private int numOpen;
	private int currentFrame;
	private int lastFrame;
	private int horizontalUnit;
	private int verticalSegmentNumber;
	private int scrollVertical;
	private int scrollHorizontal;
	private DrawRawChannelDataPanel paintPanel;
	private int windowHeight;
	private NucButtons[] buttonList;
	private JComponent rBox;
	private JMenuItem batchProcessItem;
	private JMenuItem processCurrentItem;

	
	
	
	public ABIFframe getMainFrame()
	{
		return mainFrame;
	}

	public void setMainFrame(ABIFframe mainFrame)
	{
		this.mainFrame = mainFrame;
	}
	
	public JPanel getMainPanel()
	{
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel)
	{
		this.mainPanel = mainPanel;
	}
	
	
	public JButton getGoButton()
	{
		return goButton;
	}

	public void setGoButton(JButton goButton)
	{
		this.goButton = goButton;
	}
	
	public JButton getFindButton()
	{
		return findButton;
	}

	public void setFindButton(JButton findButton)
	{
		this.findButton = findButton;
	}
	
	
	public JCheckBoxMenuItem getReverseItem()
	{
		return reverseItem;
	}

	public void setReverseItem(JCheckBoxMenuItem reverseItem)
	{
		this.reverseItem= reverseItem;
	}
	
	public JCheckBoxMenuItem getBasePositionItem()
	{
		return basePositionItem;
	}

	public void setBasePositionItem(JCheckBoxMenuItem basePositionItem)
	{
		this.basePositionItem= basePositionItem;
	}
	
	public JCheckBoxMenuItem getToggleRegion()
	{
		return toggleRegion;
	}

	public void setToggleRegion(JCheckBoxMenuItem toggleRegion)
	{
		this.toggleRegion= toggleRegion;
	}
	
	
	
    public boolean getStart()
	{
		return start;
	}

	public void setStart(boolean start)
	{
		this.start = start;
	}
	
	public int getCurrentFrame()
	{
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame)
	{
		this.currentFrame = currentFrame;
	}
	
	public int getLastFrame()
	{
		return lastFrame;
	}

	public void setLastFrame(int lastFrame)
	{
		this.lastFrame = lastFrame;
	}
	
	public int getNumOpen()
	{
		return numOpen;
	}

	public void setNumOpen(int numOpen)
	{
		this.numOpen = numOpen;
	}
	
	
	
	public JTabbedPane getTabbedPane()
	{
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane)
	{
		this.tabbedPane = tabbedPane;
	}
	

	public JTextField getGoTo()
	{
		return goTo;
	}

	public void setGoTo(JTextField goTo)
	{
		this.goTo = goTo;
	}
	
	public JTextField getSeqFinder()
	{
		return seqFinder;
	}

	public void setSeqFinder(JTextField seqFinder)
	{
		this.seqFinder = seqFinder;
	}
	
	public JPanel getInputGoTo()
	{
		return inputGoTo;
	}

	public void setInputGoTo(JPanel inputGoTo)
	{
		this.inputGoTo = inputGoTo;
	}
	
	public int getWindowHeight()
	{
		return windowHeight;
	}

	public void setWindowHeight(int windowHeight)
	{
		this.windowHeight = windowHeight;
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
	
	
	public int getVerticalSegmentNumber()
	{
		return verticalSegmentNumber;
	}

	public void setVerticalSegmentNumber(int verticalSegmentNumber)
	{
		this.verticalSegmentNumber = verticalSegmentNumber;
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
	
	
	public DrawRawChannelDataPanel getPaintPanel()
	{
		return paintPanel;
	}

	public void setPaintPanel(DrawRawChannelDataPanel paintPanel)
	{
		 this.paintPanel = paintPanel;
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
		
	
	public JCheckBoxMenuItem getBaseCallItem()
	{
		return baseCallItem;
	}

	public void setBaseCallItem(JCheckBoxMenuItem baseCallItem) {
		
		this.baseCallItem = baseCallItem;
	}

	
	public JMenuItem getcloseMenuItem()
	{
		return closeMenuItem;
	}

	public void setCloseMenuItem(JMenuItem closeMenuItem) {
		
		this.closeMenuItem = closeMenuItem;
	}
	
	public JMenuItem getExportMenuItem()
	{
		return exportMenuItem;
	}

	public void setExportMenuItem(JMenuItem exportMenuItem) {
		
		this.exportMenuItem = exportMenuItem;
	}
	
	public JMenuItem getPrintMenuItem()
	{
		return printMenuItem;
	}

	public void setPrintMenuItem(JMenuItem printMenuItem) {
		
		this.printMenuItem = printMenuItem;
	}
	
	public JMenuItem getChromInfoItem()
	{
		return chromInfoItem;
	}

	public void setChromInfoItem(JMenuItem chromInfoItem) {
		
		this.chromInfoItem = chromInfoItem;
	}
	
	
	public JMenuItem getRawInfoItem()
	{
		return rawInfoItem;
	}

	public void setRawInfoItem(JMenuItem rawInfoItem) {
		
		this.rawInfoItem = rawInfoItem;
	}
	
	public JCheckBoxMenuItem getQualityScoreItem()
	{
		return qualityScoreItem;
	}
	
	public void setQualityScoreItem(JCheckBoxMenuItem qualityScoreItem) {
		this.qualityScoreItem = qualityScoreItem;
		
	}
	
	public JMenuItem getThresholdItem()
	{
		return thresholdItem;
	}
	

	public void setThresholdItem(JMenuItem thresholdItem) {
		this.thresholdItem =thresholdItem;
	}
	
	public MyNucPalette getMNP() {
		return mnp;
		
	}
	
	public void setMNP(MyNucPalette mnp) {
		this.mnp = mnp;
		
	}

	public NucButtons[] getButtonList() {
		return buttonList;
		
	}
	public void setButtonList(NucButtons[] buttonList) {
		this.buttonList = buttonList;
		
	}
	
	public JComponent getrBox() {
		return rBox;
		
	}

	public void setrBox(JComponent rBox) {
		this.rBox = rBox;
		
	}

	
	public JMenuItem getBatchProcessItem() {
		return batchProcessItem;
	
		
	}
	
	public void setBatchProcessItem(JMenuItem batchProcessItem) {
		this.batchProcessItem = batchProcessItem;
		
	}
	
	public JMenuItem getProcessCurrentItem() {
		return processCurrentItem;
		
	}

	public void setProcessCurrentItem(JMenuItem processCurrentItem) {
		this.processCurrentItem = processCurrentItem;
		
	}
	
}
