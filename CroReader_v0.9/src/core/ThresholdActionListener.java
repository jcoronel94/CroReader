package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.colorchooser.AbstractColorChooserPanel;


/*
 * Listener for threshold options
 */

public class ThresholdActionListener implements ActionListener {

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
	private String filename;
	private TreeMap<Integer, Integer> predict;
	private int verticalSegmentNumber;
	private int verticalUnit;


	public ThresholdActionListener(CentralParameterStorage centralPara, CentralPanelStorage centralPanel,
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
			verticalUnit = centralPanel.getVerticalUnit();

			//---------------------------------------------------
			// get all data from central parameter before writing


			qualScore = centralPanel.getQualScore();
			qualScoreNuc = centralPanel.getQualScoreNuc();
			channel1Data = centralPanel.getChannel1Data();
			channel2Data = centralPanel.getChannel2Data();
			channel3Data = centralPanel.getChannel3Data();
			channel4Data = centralPanel.getChannel4Data();
			peakLocation = centralPanel.getPeakLocation();
			filename = centralPanel.getFileName();


			ABIFframe parent = centralPara.getMainFrame();
			JPanel dialog = new JPanel();
			dialog.setLayout(new BoxLayout(dialog, BoxLayout.PAGE_AXIS));



			int threshold = centralPanel.getThreshold();


			JSlider qualityThreshold = new JSlider(JSlider.HORIZONTAL,
					20, 40, threshold);

			qualityThreshold.setFocusable(false);
			qualityThreshold.setMajorTickSpacing(10);
			qualityThreshold.setMinorTickSpacing(1);
			qualityThreshold.setPaintLabels(true);
			qualityThreshold.setPaintTicks(true);




			int threshold2 = centralPanel.getThreshold2();

			JSlider qualityThreshold2 = new JSlider(JSlider.HORIZONTAL,
					10, 25, threshold2);

			qualityThreshold2.setFocusable(false);
			qualityThreshold2.setMajorTickSpacing(10);
			qualityThreshold2.setMinorTickSpacing(1);
			qualityThreshold2.setPaintLabels(true);
			qualityThreshold2.setPaintTicks(true);




			int clearance = centralPanel.getClearanceLevel();

			JSlider clearanceSlider = new JSlider(JSlider.HORIZONTAL,
					20, 50, clearance);

			clearanceSlider.setFocusable(false);
			clearanceSlider.setMajorTickSpacing(10);
			clearanceSlider.setMinorTickSpacing(1);
			clearanceSlider.setPaintLabels(true);
			clearanceSlider.setPaintTicks(true);


			int differential = centralPanel.getPeak2ValleyDiff();

			JSlider differentialSlider = new JSlider(JSlider.HORIZONTAL,
					20, 50, differential);

			differentialSlider.setFocusable(false);
			differentialSlider.setMajorTickSpacing(10);
			differentialSlider.setMinorTickSpacing(1);
			differentialSlider.setPaintLabels(true);
			differentialSlider.setPaintTicks(true);


		
			JButton color = new JButton();
			color.setPreferredSize( new Dimension(125, 50));
			color.setText("Select");
			color.setFocusPainted(false);
			JColorChooser chooser = new JColorChooser(colors[5]);
			AbstractColorChooserPanel[] panels = chooser.getChooserPanels();
			for(AbstractColorChooserPanel panel: panels){
				System.out.println(panel.getDisplayName());
				if(!panel.getDisplayName().equals("HSV")){
					chooser.removeChooserPanel(panel);
				} else{
					//modify the chooser panel.

				}
			}

			//create custom preview
			MyPreviewPanel pview = new MyPreviewPanel(chooser);
			//check for color change and apply it to preview
			chooser.getSelectionModel().addChangeListener(e->{
				Color col = chooser.getColor();
				pview.setColor(col);

			});


			chooser.setPreviewPanel(pview);
			


			Object[] inputFields = {"Threshold Sensitivity for entering good region", qualityThreshold,
					"Threshold for staying in good region", qualityThreshold2,
					"Peak Clearance", clearanceSlider, 
					"Peak to valley differential", differentialSlider,
					"Choose color", chooser};

			int option = JOptionPane.showOptionDialog(parent, inputFields, "Modify Region Extraction Specs", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Apply", "Cancel"},
					"default");
			if (option == JOptionPane.OK_OPTION) {

				JScrollPane scrollPane = centralPanel.getDrawPanel();
				// get current scroll values to maintain them after repaint
				int scrollVertical = scrollPane.getVerticalScrollBar().getValue();
				centralPanel.setScrollVertical(scrollVertical);
				int scrollHorizontal = scrollPane.getHorizontalScrollBar().getValue();
				centralPanel.setScrollHorizontal(scrollHorizontal);

				ArrayList<Integer> coList = AnalyzeABIFdata.coAlgorithm(channel1Data, channel2Data, channel3Data, channel4Data, peakLocation); 

				short sil[] = AnalyzeABIFdata.getMaxValArray(channel1Data, channel2Data, channel3Data, channel4Data);
				short[] valley = AnalyzeABIFdata.valleyFinder(peakLocation, sil);
				float[] doList = AnalyzeABIFdata.doAlgorithm(peakLocation, valley, sil);
				TreeMap<Integer, Integer> predicter = AnalyzeABIFdata.predictiveAlgorithm(qualScore, qualityThreshold.getValue(), qualityThreshold2.getValue(), coList, clearanceSlider.getValue(), doList, differentialSlider.getValue(), 5); 
				//centralPanel.setPredict(predicter);

				Color custom =  chooser.getColor();
				colors[5] = custom;
				//					
				//		

				DrawRawChannelDataPanel paintPanel = new DrawRawChannelDataPanel(channel1Data.length + 200, verticalSegmentNumber + 200,
						qualScore, qualScoreNuc, channel1Data, channel2Data, channel3Data,
						channel4Data, peakLocation, colors, verticalUnit, verticalSegmentNumber, 100, 100, predicter,reversed,basePositionOn,baseCallOn, qualityScoreOn,toggleRegionOn);

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

				centralPanel.setThreshold(qualityThreshold.getValue());
				centralPanel.setThreshold2(qualityThreshold2.getValue());
				centralPanel.setclearanceLevel(clearanceSlider.getValue());
				centralPanel.setPeak2ValleyDiff(differentialSlider.getValue());
			}


















			//			
			//			
			//			//ABIFframe parent = centralPara.getMainFrame();
			//			JPanel disCombo = new JPanel();
			//			JPanel colorCombo = new JPanel();
			//
			//			//JPanel panel = new JPanel();
			//			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
			//
			//
			//
			//			JLabel sliderLabel = new JLabel("Threshold Sensitivity", JLabel.CENTER);
			//
			//			//int threshold = centralPanel.getThreshold();
			//
			//			//JSlider qualityThreshold = new JSlider(JSlider.HORIZONTAL,
			//				//	0, 60, threshold);
			//
			//			qualityThreshold.setFocusable(false);
			//
			//			qualityThreshold.setMajorTickSpacing(10);
			//			qualityThreshold.setMinorTickSpacing(1);
			//			qualityThreshold.setPaintLabels(true);
			//			qualityThreshold.setPaintTicks(true);
			//
			//			ThresholdChangeListener thresholdChange = new ThresholdChangeListener(centralPara, centralPanel, centralList);
			//			qualityThreshold.addChangeListener(thresholdChange);
			//
			//			disCombo.add(sliderLabel);
			//			disCombo.add(qualityThreshold);
			//
			//
			//
			//
			//			//color swapper
			//
			////			JLabel colorLabel = new JLabel("Choose color", JLabel.LEFT);
			////			JButton color = new JButton();
			////			color.setPreferredSize( new Dimension(125, 50));
			////			color.setText("Select");
			////			color.setFocusPainted(false);
			////			color.addActionListener(new ActionListener(){
			////				//addMouseListener(new MouseAdapter(){
			////				@Override
			////				public void actionPerformed(ActionEvent evt){
			////					JColorChooser chooser = new JColorChooser(colors[5]);
			////					AbstractColorChooserPanel[] panels = chooser.getChooserPanels();
			////					for(AbstractColorChooserPanel panel: panels){
			////						System.out.println(panel.getDisplayName());
			////						if(!panel.getDisplayName().equals("HSV")){
			////							chooser.removeChooserPanel(panel);
			////						} else{
			////							//modify the chooser panel.
			////
			////						}
			////					}
			////					
			////					//create custom preview
			////					MyPreviewPanel pview = new MyPreviewPanel(chooser);
			////					//check for color change and apply it to preview
			////					chooser.getSelectionModel().addChangeListener(e->{
			////							Color col = chooser.getColor();
			////							pview.setColor(col);
			////							
			////					});
			////				
			////					
			////					chooser.setPreviewPanel(pview);
			////					JColorChooser.createDialog(
			////							panel, 
			////							"Choose a Color", 
			////							true, 
			////							chooser, 
			////							(e)->{
			////
			////								JScrollPane scrollPane = centralPanel.getDrawPanel();
			////								// get current scroll values to maintain them after repaint
			////								int scrollVertical = scrollPane.getVerticalScrollBar().getValue();
			////								centralPanel.setScrollVertical(scrollVertical);
			////								int scrollHorizontal = scrollPane.getHorizontalScrollBar().getValue();
			////								centralPanel.setScrollHorizontal(scrollHorizontal);
			////								TreeMap<Integer, Integer> predict = centralPanel.getPredict();
			////								
			////								Color custom =  chooser.getColor();
			////								colors[5] = custom;
			////				
			////
			////
			////								DrawRawChannelDataPanel paintPanel = new DrawRawChannelDataPanel(channel1Data.length + 200, verticalSegmentNumber + 200,
			////										 qualScore, qualScoreNuc, channel1Data, channel2Data, channel3Data,
			////										channel4Data, peakLocation, colors, verticalUnit, verticalSegmentNumber, 100, 100, predict,reversed,basePositionOn,baseCallOn, qualityScoreOn,toggleRegionOn);
			////
			////								paintPanel.setPreferredSize(new Dimension(channel1Data.length + 200, verticalSegmentNumber + 200));
			////
			////								scrollPane.remove(centralPanel.getPaintPanel());
			////								scrollPane.setSize(600, 400);
			////								scrollPane.getViewport().add(paintPanel);
			////								// maintain current position in scroll pane
			////								scrollPane.getVerticalScrollBar().setValue(scrollVertical);
			////								scrollPane.getHorizontalScrollBar().setValue(scrollHorizontal);
			////								//centralPanel.setDrawPanel(scrollPane);
			////								scrollPane.revalidate();
			////								scrollPane.repaint();
			////								
			////							
			////							},
			////							(e)->{
			////								System.out.println("whatever");
			////							}
			////							).setVisible(true);
			////				}
			////			});
			//
			//			//colorCombo.add(colorLabel);
			//			colorCombo.add(Box.createRigidArea(new Dimension(50,0)));
			//			//colorCombo.add(color);
			//
			//			panel.add(disCombo);
			//			panel.add(Box.createRigidArea(new Dimension(0,20)));
			//			panel.add(colorCombo);
			//
			//
			//			//create custom dialog box, point to parent, give name, modal true, pass component
			//			//ChromDialog dialogBox= new ChromDialog(parent,"Threshold Options", false, panel);
			//
			//			//dialogBox.setVisible(true);
		}


	}
}
