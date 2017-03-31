package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.Popup;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;


public class openFileActionListener implements ActionListener {

	public static String inputFsaFileLocation = "/Users/armasia/ABIFfileTest.d/LZ103_11_G11_Feb-8-2012-A.fsa";
	private static int verticalSegmentNumber = 0;
	private static int horizontalSegmentNumber = 50;
	ArrayList<CentralPanelStorage> centralList = null;
	CentralParameterStorage centralPara = null;
	private DrawRawChannelDataPanel paintPanel;
	private DrawUnprocessedChannelDataPanel rawPaintPanel;
	//private JPanel inputGoTo = null;
	//private JFormattedTextField goTo = null;
	private int halfMaxValue;
	private int maxValue1;
	private int maxValue2;
	private int maxValue3;
	private int maxValue4;
	JComponent parentalComponent = null;
	Dimension fileChooserDim = new Dimension(700, 600);


	public openFileActionListener(JComponent parentalComponent, CentralParameterStorage centralPara,ArrayList<CentralPanelStorage> centralList) {
		this.parentalComponent = parentalComponent;
		this.centralPara = centralPara;
		this.centralList = centralList;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// set up file chooser dialog, no file restrictions
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setPreferredSize(fileChooserDim);
		int returnVal = fileChooser.showOpenDialog(null);

		// if this is the first panel open, do this
		if (centralPara.getStart() == true) {
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				inputFsaFileLocation = fileChooser.getSelectedFile().getAbsolutePath();


				//open new panel storage for this
				CentralPanelStorage centralPanel = new CentralPanelStorage();

				// keep track of number of panes
				int counter = 1;
				centralPara.setNumOpen(counter);

				// do this to get selected file name excluding path info
				File tempFile = new File(inputFsaFileLocation);
				String fileName = tempFile.getName();
				centralPanel.setFileName(fileName);

				// give this panel an instance number starting from 0 (since first panel created)
				centralPanel.setNum(centralPara.getNumOpen() - 1);

				//make menu items available
				centralPara.getcloseMenuItem().setEnabled(true);
				//centralPara.getPrintMenuItem().setEnabled(true);
				centralPara.getExportMenuItem().setEnabled(true);
				centralPara.getBaseCallItem().setEnabled(true);
				centralPara.getBasePositionItem().setEnabled(true);
				centralPara.getQualityScoreItem().setEnabled(true);
				//centralPara.getThresholdItem().setEnabled(true);
				centralPara.getToggleRegion().setEnabled(true);
				centralPara.getChromInfoItem().setEnabled(true);
				centralPara.getRawInfoItem().setEnabled(true);
				centralPara.getReverseItem().setEnabled(true);
				centralPara.getGoTo().setEnabled(true);
				centralPara.getSeqFinder().setEnabled(true);
				centralPara.getGoButton().setEnabled(true);
				centralPara.getFindButton().setEnabled(true);
				//centralPara.getBatchProcessItem().setEnabled(true);
				centralPara.getProcessCurrentItem().setEnabled(true);

				// save segment number for panel
				//verticalSegmentNumber = Integer.parseInt(JOptionPane.showInputDialog(null, "Vertical Segment Number", verticalSegmentNumber));

				verticalSegmentNumber =200;

				if(centralPara.getWindowHeight() > 527){

					int taller =  centralPara.getWindowHeight() - 527 ;
					verticalSegmentNumber = verticalSegmentNumber +taller;

				}


				horizontalSegmentNumber = 200;

				centralPanel.setReferenceHeight(centralPara.getMainPanel().getHeight());
				centralPanel.setVerticalSegmentNumber(verticalSegmentNumber);
				centralPanel.setHorizontalSegmentNumber(horizontalSegmentNumber);


				// load key dirEntry data
				ArrayList<AbifDirEntryBean> dirEntryList = ABIFbinaryFileLoader.read(inputFsaFileLocation);




				Color[] colors = new Color[6];
				colors[0] = Color.black;
				colors[1] = Color.magenta;
				colors[2] = Color.green;
				colors[3] = Color.red;
				colors[4] = Color.blue;
				colors[5] = new Color(1f,0f,0f,.3f );
				centralPanel.setColors(colors);



				//experimental - add buttons for coloring
				MyNucPalette mnp = centralPara.getMNP();
				mnp.setLayout(new GridBagLayout());

				NucButtons[] buttonList = new NucButtons[4];
				//g
				NucButtons g = new NucButtons(mnp,centralPanel, 'G' );
				GridBagConstraints gbc = new GridBagConstraints();
				//gbc.fill = GridBagConstraints.HORIZONTAL;

				gbc.weightx = .0;
				gbc.gridx = 0;
				gbc.weighty = 0;
				gbc.insets = new Insets(-8,0,0,0);
				mnp.add(g,gbc);
				g.setToolTipText("Change color of G trace");
				buttonList[0] = g;

				//a
				NucButtons a = new NucButtons(mnp,centralPanel, 'A' );
				gbc.gridx = 1;
				gbc.insets = new Insets(-8,0,0,0);
				mnp.add(a, gbc);
				a.setToolTipText("Change color of A trace");
				buttonList[1] = a;

				//t
				NucButtons t = new NucButtons(mnp,centralPanel, 'T' );
				gbc.gridx = 2;
				gbc.insets = new Insets(-8,0,0,0);
				mnp.add(t, gbc);
				t.setToolTipText("Change color of T trace");
				buttonList[2] = t;

				//c
				NucButtons c = new NucButtons(mnp,centralPanel, 'C' );
				gbc.gridx = 3;
				gbc.insets = new Insets(-8,0,0,0);
				mnp.add(c, gbc);
				c.setToolTipText("Change color of C trace");
				buttonList[3] = c;

				centralPara.setButtonList(buttonList);



				// save all channel data
				short[] channel1Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"DATA", 9);
				centralPanel.setChannel1Data(channel1Data);
				short[] channel2Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"DATA", 10);
				centralPanel.setChannel2Data(channel2Data);
				short[] channel3Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"DATA", 11);
				centralPanel.setChannel3Data(channel3Data);
				short[] channel4Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"DATA", 12);
				centralPanel.setChannel4Data(channel4Data);

				//raw data 
				short[] rawchannel1Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"DATA", 1);
				centralPanel.setRawChannel1Data(rawchannel1Data);
				short[] rawchannel2Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"DATA", 2);
				centralPanel.setRawChannel2Data(rawchannel2Data);
				short[] rawchannel3Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"DATA", 3);
				centralPanel.setRawChannel3Data(rawchannel3Data);
				short[] rawchannel4Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"DATA", 4);
				centralPanel.setRawChannel4Data(rawchannel4Data);


				// save quality score data and peak location
				byte[] qualScore = LoadDataBySpecificNameAndNumber.loadByteData(dirEntryList, inputFsaFileLocation,
						"PCON", 2);
				byte[] qualScoreNuc = LoadDataBySpecificNameAndNumber.loadByteData(dirEntryList, inputFsaFileLocation,
						"PBAS", 2);
				short[] peakLocation = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"PLOC", 2);
				
				byte[] reverseQualScore = AnalyzeABIFdata.reverseQualScore(qualScore);
				
				byte[] reverseCompNuc = AnalyzeABIFdata.reverseComplementNucs(qualScoreNuc);
				
				short[] reverseLocations = AnalyzeABIFdata.reverseLocations(peakLocation);
		
				centralPanel.setPeakLocation(peakLocation);
				centralPanel.setQualScore(qualScore);
				centralPanel.setQualScoreNuc(qualScoreNuc);
				centralPanel.setReverseCompNuc(reverseCompNuc);
				centralPanel.setReverseLoc(reverseLocations);
				


				//algortihm for good region finding

				ArrayList<Integer> coList = AnalyzeABIFdata.coAlgorithm(channel1Data, channel2Data, channel3Data, channel4Data, peakLocation); 
			
				short sil[] = AnalyzeABIFdata.getMaxValArray(channel1Data, channel2Data, channel3Data, channel4Data);
				short[] valley = AnalyzeABIFdata.valleyFinder(peakLocation, sil);
				float[] doList = AnalyzeABIFdata.doAlgorithm(peakLocation, valley, sil);
				centralPanel.setCoList(coList);
				centralPanel.setDoList(doList);
				//set default vals
				int threshold = 30;
				int threshold2 = 20;
				int clearanceLevel = 20;
				int peak2ValleyDiff = 30;
				TreeMap<Integer,Integer> predictor = AnalyzeABIFdata.predictiveAlgorithm(qualScore, threshold,threshold2, coList, clearanceLevel, doList,peak2ValleyDiff, 5 );
				centralPanel.setThreshold(threshold);
				centralPanel.setThreshold2(threshold2);
				centralPanel.setclearanceLevel(clearanceLevel);
				centralPanel.setPeak2ValleyDiff(peak2ValleyDiff);
				centralPanel.setPredict(predictor);

				//create detail pane 

				JTextPane detPane = new JTextPane();
				detPane.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
				detPane.setBackground(new Color(220,220,220));


				//load general parameters 

				short AEPt2 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "AEPt", 2);//analysis ending scan number for basecalling last analysis 
				appendToPane(detPane, "AEPt2: " + AEPt2 +"\n"  , Color.BLACK);
				String APFN2 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation,"APFN", 2); //Sequecing Analysis parameter file name
				appendToPane(detPane, "APFN2: " + APFN2 +"\n"  , Color.BLACK);
				//String APXV = LoadDataBySpecificNameAndNumber.loadCStringData(dirEntryList, inputFsaFileLocation,
				//"APXV", 1); //Anaylsis Protocol XML schema Version
				//skipping APXV, APrN, APrV, ARTN1
				short ASPF1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "ASPF", 1); //flag if adaptive processing worked
				appendToPane(detPane, "ASPF1: " + ASPF1 +"\n"  , Color.BLACK);
				short ASPt1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "ASPt", 1); //Analysis starting scan number initial analysis 
				appendToPane(detPane, "ASPt1: " + ASPt1 +"\n"  , Color.BLACK);
				short ASPt2 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "ASPt", 2); //Analysis starting scan number ending analysis 
				appendToPane(detPane, "ASPt2: " + ASPt2 +"\n"  , Color.BLACK);
				short B1Pt1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "B1Pt", 1); //Reference Scan number for mobility and spacing curves for first analysis
				appendToPane(detPane, "B1Pt1: " + B1Pt1 +"\n"  , Color.BLACK);
				short B1Pt2 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "B1Pt", 2); //Reference Scan number for mobility and spacing curves for last analysis
				String CMNT1 =  LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation,"CMNT", 1); //Sample comment
				appendToPane(detPane, "CMNT1: " + CMNT1 +"\n"  , Color.BLACK);
				//skipping CTID CTNM
				String CTTL1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation,"CTTL", 1); //comment title  
				appendToPane(detPane, "CTTL1: " + CTTL1 +"\n"  , Color.BLACK);
				//short CpEP1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "CpEP", 1);
				//char[] CpEP = FindDataInOffsetField.findOffsetAsChar(dirEntryList, inputFsaFileLocation, "CpEP", 1); //is capillary machine?
				short DSam1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "DSam", 1); //Downsampling 				
				appendToPane(detPane, "DSam1: " + DSam1 +"\n"  , Color.BLACK);
				String DySN1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation,"DySN", 1); //dye set name 
				appendToPane(detPane, "DySN1: " + DySN1 +"\n"  , Color.BLACK);
				short DyeP1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "Dye#", 1); //Number of dyes
				appendToPane(detPane, "DyeP1: " + DyeP1 +"\n"  , Color.BLACK);
				String DyeN1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "DyeN", 1); // dye name1  
				appendToPane(detPane, "DyeN1: " + DyeN1 +"\n"  , Color.BLACK);
				String DyeN2 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation,	"DyeN", 2); // dye name2
				appendToPane(detPane, "DyeN2: " + DyeN2 +"\n"  , Color.BLACK);
				String DyeN3 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "DyeN", 3); //dye name3 
				appendToPane(detPane, "DyeN3: " + DyeN3 +"\n"  , Color.BLACK);
				String DyeN4 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "DyeN", 4); //dye name4  
				appendToPane(detPane, "DyeN4: " + DyeN4 +"\n"  , Color.BLACK);
				short DyeW1 =  FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "DyeW", 1); //dye1 wavelength 
				appendToPane(detPane, "DyeW1: " + DyeW1 +"\n"  , Color.BLACK);
				short DyeW2 =  FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "DyeW", 2); //dye2 wavelength 
				appendToPane(detPane, "DyeW2: " + DyeW2 +"\n"  , Color.BLACK);
				short DyeW3 =  FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "DyeW", 3); //dye3 wavelength 
				appendToPane(detPane, "DyeW3: " + DyeW3 +"\n"  , Color.BLACK);
				short DyeW4 =  FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "DyeW", 4); //dye4 wavelength
				appendToPane(detPane, "DyeW4: " + DyeW4 +"\n"  , Color.BLACK);
				//long EPVt1 = LoadDataBySpecificNameAndNumber.loadLongData(dirEntryList, inputFsaFileLocation, "EPVt", 1); //Electrophoresis voltage setting
				//System.out.println(EPVt1);
				String EVNT1 =  LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "EVNT", 1); // Start event run
				appendToPane(detPane, "EVNT1: " + EVNT1 +"\n"  , Color.BLACK);
				String EVNT2 =  LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "EVNT", 2); // End event run
				appendToPane(detPane, "EVNT2: " + EVNT2 +"\n"  , Color.BLACK);
				String EVNT3 =  LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "EVNT", 3); // start Collection event
				appendToPane(detPane, "EVNT3: " + EVNT3 +"\n"  , Color.BLACK);
				String EVNT4 =  LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "EVNT", 4); // end collection event
				appendToPane(detPane, "EVNT4: " + EVNT4 +"\n"  , Color.BLACK);
				String GTyp1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "GTyp", 1); //Gel Type Description
				appendToPane(detPane, "GTyp1: " + GTyp1 +"\n"  , Color.BLACK);
				//FTab1
				//FVoc1
				//Feat1
				//long InSc1 injection time
				//long InVt injection voltage
				short LANE1 =  FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "LANE", 1); // Lane / capillary 
				appendToPane(detPane, "LANE1: " + LANE1 +"\n"  , Color.BLACK);
				String LIMS1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "LIMS", 1); // Sample tracking ID
				appendToPane(detPane, "LIMS1: " + LIMS1 +"\n"  , Color.BLACK);
				short LNTD1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "LNTD", 1); //length to dectector 
				appendToPane(detPane, "LNTD1: " + LNTD1 +"\n"  , Color.BLACK);
				//LsrP1
				String MCHN1 =  LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "MCHN", 1); //	Instrument name and serial num 
				appendToPane(detPane, "MCHN1: " + MCHN1 +"\n"  , Color.BLACK);
				String MODF1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "MODF", 1);  // data colleciton module file 
				appendToPane(detPane, "MODF1: " + MODF1 +"\n"  , Color.BLACK);
				String MODL1 = FindDataInOffsetField.findOffsetAsString(dirEntryList, inputFsaFileLocation, "MODL", 1); 
				appendToPane(detPane, "MODL1: " + MODL1 +"\n"  , Color.BLACK);
				short NAVG1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "NAVG", 1); //pixels averaged per lane
				appendToPane(detPane, "NAVG1: " + NAVG1 +"\n"  , Color.BLACK);
				short NLNE1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "NLNE", 1);  //number of capillaries 
				appendToPane(detPane, "NLNE1: " + NLNE1 +"\n"  , Color.BLACK);
				//NOIS
				//OFFS
				//OfSc
				String PDMF1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "PDMF", 1); //Sequencing analysis mobility file name 
				appendToPane(detPane, "PDMF1: " + PDMF1 +"\n"  , Color.BLACK);
				String PDMF2 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "PDMF", 2); // same as 1
				appendToPane(detPane, "PDMF2: " + PDMF2 +"\n"  , Color.BLACK);
				//PXLB1
				//RGNm1
				//RMXV1
				//RMdN1
				//RMdV1
				//RPrN1
				//RPrV1
				String RUND1 = FindDataInOffsetField.findOffsetofDate(dirEntryList, inputFsaFileLocation, "RUND", 1);
				appendToPane(detPane, "RUND1: " + RUND1 +"\n"  , Color.BLACK);
				String RUND2 = FindDataInOffsetField.findOffsetofDate(dirEntryList, inputFsaFileLocation, "RUND", 2);
				appendToPane(detPane, "RUND2: " + RUND2 +"\n"  , Color.BLACK);
				String RUND3 = FindDataInOffsetField.findOffsetofDate(dirEntryList, inputFsaFileLocation, "RUND", 3);
				appendToPane(detPane, "RUND3: " + RUND3 +"\n"  , Color.BLACK);
				String RUND4 = FindDataInOffsetField.findOffsetofDate(dirEntryList, inputFsaFileLocation, "RUND", 4);
				appendToPane(detPane, "RUND4: " + RUND4 +"\n"  , Color.BLACK);
				String RUNT1 = FindDataInOffsetField.findOffsetofTime(dirEntryList, inputFsaFileLocation, "RUNT", 1);
				appendToPane(detPane, "RUNT1: " + RUNT1 +"\n"  , Color.BLACK);
				String RUNT2 = FindDataInOffsetField.findOffsetofTime(dirEntryList, inputFsaFileLocation, "RUNT", 2);
				appendToPane(detPane, "RUNT2: " + RUNT2 +"\n"  , Color.BLACK);
				String RUNT3 = FindDataInOffsetField.findOffsetofTime(dirEntryList, inputFsaFileLocation, "RUNT", 3);
				appendToPane(detPane, "RUNT3: " + RUNT3 +"\n"  , Color.BLACK);
				String RUNT4 = FindDataInOffsetField.findOffsetofTime(dirEntryList, inputFsaFileLocation, "RUNT", 4);
				appendToPane(detPane, "RUNT4: " + RUNT4 +"\n"  , Color.BLACK);
				//Rate1
				//RevC1
				//RunN1
				short SN1[] = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation, "S/N%", 1); //Signal Strength 
				appendToPane(detPane, "S/N%1: " + SN1[0] + " " + SN1[1] + " " + SN1[2] + " " + SN1[3] +"\n"  , Color.BLACK);
				//SCAN1
				String SMED1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "SMED", 1); // Seperation medium lot exp date
				appendToPane(detPane, "SMED1: " + SMED1 +"\n"  , Color.BLACK);
				String SMLt1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "SMLt", 1); // Seperation meduum lot number 
				appendToPane(detPane, "SMLt1: " + SMLt1 +"\n"  , Color.BLACK);
				String SMPL1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "SMPL", 1); //Sequencing analysis sample number
				appendToPane(detPane, "SMPL1: " + SMPL1 +"\n"  , Color.BLACK);
				float SPAC1 = FindDataInOffsetField.findOffsetAsFloat(dirEntryList, inputFsaFileLocation, "SPAC", 1); //average peak spacing in last analysis 
				appendToPane(detPane, "SPAC1: " + SPAC1 +"\n"  , Color.BLACK);
				String SPAC2 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "SPAC", 2); //basecaller name    
				appendToPane(detPane, "SPAC2: " + SPAC2 +"\n"  , Color.BLACK);
				float SPAC3 = FindDataInOffsetField.findOffsetAsFloat(dirEntryList, inputFsaFileLocation, "SPAC", 3); //average peak spacing last calculated by basecaller
				appendToPane(detPane, "SPAC3: " + SPAC3 +"\n"  , Color.BLACK);
				String SVER1 = FindDataInOffsetField.findOffsetAsString(dirEntryList, inputFsaFileLocation, "SVER", 1); //data  collection version number
				appendToPane(detPane, "SVER1: " + SVER1 +"\n"  , Color.BLACK);
				String SVER2 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "SVER", 2); //basecaller version number  
				appendToPane(detPane, "SVER2: " + SVER2 +"\n"  , Color.BLACK);
				String SVER3 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "SVER", 3); //Firmware version number 
				appendToPane(detPane, "SVER3: " + SVER3 +"\n"  , Color.BLACK);
				//Satd1
				//ScSt1
				float Scal1 = FindDataInOffsetField.findOffsetAsFloat(dirEntryList, inputFsaFileLocation, "Scal", 1); 
				appendToPane(detPane, "Scal1: " + Scal1 +"\n"  , Color.BLACK);
				short Scan1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "Scan", 1); // number of scans, redundant with "SCAN" (legacy)
				appendToPane(detPane, "Scan1: " + Scan1 +"\n"  , Color.BLACK);
				String TUBE1 =  FindDataInOffsetField.findOffsetAsString(dirEntryList, inputFsaFileLocation, "TUBE", 1); //autosampler position 
				appendToPane(detPane, "TUBE1: " + TUBE1 +"\n"  , Color.BLACK);
				//Tmpr1
				String User1 = FindDataInOffsetField.findOffsetAsString(dirEntryList, inputFsaFileLocation, "User", 1); //name of plate creator  
				appendToPane(detPane, "User1: " + User1 +"\n"  , Color.BLACK);
				//set details pane at end
				detPane.setEditable(false);
				detPane.setHighlighter(null);
				centralPanel.setDetPane(detPane);

				halfMaxValue = AnalyzeABIFdata.getAllMaxVal(channel1Data, channel2Data, channel3Data, channel4Data) / 2;
				maxValue1 = AnalyzeABIFdata.getMaxVal(channel1Data);
				System.out.println("max val: " + maxValue1);
				maxValue2 = AnalyzeABIFdata.getMaxVal(channel2Data);
				maxValue3 = AnalyzeABIFdata.getMaxVal(channel3Data);
				maxValue4 = AnalyzeABIFdata.getMaxVal(channel4Data);


				


				// find the highest peak in all channels and default at half max
				// peak for now
				int compareMax = 0;
				int compare[] = { maxValue1, maxValue2, maxValue3, maxValue4 };

				for (int i = 0; i < compare.length; i++) {
					if (compare[i] > compareMax)
						compareMax = compare[i];
				}

				halfMaxValue = compareMax;

				int verticalUnit = (int) Math.ceil((double) halfMaxValue / (double) verticalSegmentNumber);
				centralPanel.setVerticalUnit(verticalUnit);
				centralPanel.setVerticalMaxVal(halfMaxValue);

				// all drawing is after this------------------------------------------




				//create panes for info panel 
				//sequence area 
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

				JSplitPane seqSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
				//seqSplit.setPreferredSize(new Dimension(350,350));			
				seqSplit.setRightComponent(seqArea);
				seqSplit.setLeftComponent(numArea);
				seqSplit.setDividerSize(0);
				appendToPane(numArea, "   1\n", Color.BLUE);
				for(int i = 0; i <qualScoreNuc.length; i++){

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

					String nuc = String.valueOf((char) qualScoreNuc[i]);
					appendToPane(seqArea, nuc, Color.BLACK);
				}
				seqArea.setEditable(false);
				numArea.setEditable(false);
				centralPanel.setSeqSplit(seqSplit);
				
				//create good region pane 
				JTextPane goodSeqArea = new JTextPane();
				goodSeqArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
				goodSeqArea.setBackground(new Color(220,220,220));
				ContextMenuMouseListener rightClick2 = new ContextMenuMouseListener();
				goodSeqArea.addMouseListener(rightClick2);
				String goodSeqText = AnalyzeABIFdata.processRegionOutputFasta(predictor, qualScoreNuc, fileName, false);
				appendToPane(goodSeqArea, goodSeqText, Color.BLACK);
			
				goodSeqArea.setEditable(false);
				centralPanel.setGoodSeqArea(goodSeqArea);
				
				
				//create reverse good region pane 
				JTextPane rGoodSeqArea = new JTextPane();
				rGoodSeqArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
				rGoodSeqArea.setBackground(new Color(220,220,220));
				ContextMenuMouseListener rightClick3 = new ContextMenuMouseListener();
				rGoodSeqArea.addMouseListener(rightClick3);
				String rGoodSeqText = AnalyzeABIFdata.processRegionOutputFasta(predictor, reverseCompNuc, fileName, true);
				
				appendToPane(rGoodSeqArea, rGoodSeqText, Color.BLACK);
			
				rGoodSeqArea.setEditable(false);
				centralPanel.setRGoodSeqArea(rGoodSeqArea);				

				//-------------------------------------------------------
				//create general pane 

				JTextPane genPane = new JTextPane();
				genPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
				genPane.setBackground(new Color(220,220,220));
				genPane.setHighlighter(null);
				appendToPane(genPane, "Filename: " + inputFsaFileLocation +"\n", Color.BLACK);
				appendToPane(genPane, "Format: ABIF \n"  , Color.BLACK);
				appendToPane(genPane, "Sample ID: " + TUBE1 + "\n", Color.black);
				appendToPane(genPane, "Sample Name: " + SMPL1 + "\n", Color.black);
				appendToPane(genPane, "Plate Label: \n",  Color.black);
				appendToPane(genPane, "Instr. Model: " + MODL1 + "\n", Color.black);
				appendToPane(genPane, "Instr. Name: " + MCHN1 + "\n", Color.black);
				appendToPane(genPane, "Run Start: " + RUND1 + " " + RUNT1 + "\n", Color.black);
				appendToPane(genPane, "Run Stop: " + RUND2 + " " + RUNT2 + "\n", Color.black);
				appendToPane(genPane, "Lane1: " + LANE1 + "\n", Color.black);
				appendToPane(genPane, "#Lanes: " + NLNE1 + "\n", Color.black);
				appendToPane(genPane, "Spacing: " + SPAC1 + "\n", Color.black);
				appendToPane(genPane, "Signal Strs: " + " " + "A = " + SN1[1] + ", C = "  + SN1[3] + ", G = " + SN1[0] + ", T = "  + SN1[2] + "\n", Color.black);
				appendToPane(genPane, "Mobility: " + PDMF1 + "\n", Color.black);
				appendToPane(genPane, "Matrix: \n", Color.black);
				appendToPane(genPane, "Comment: " + CMNT1 + "\n", Color.black);
				genPane.setEditable(false);
				centralPanel.setGenPane(genPane);


				JTabbedPane tabbedPane = centralPara.getTabbedPane();

				tabbedPane.removeAll();
				tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
				tabbedPane.setVisible(true);



				// create paint panel and the scroll it will go in
				paintPanel = new DrawRawChannelDataPanel(channel1Data.length + 200, verticalSegmentNumber + 2200, qualScore, qualScoreNuc, channel1Data, channel2Data, channel3Data,
						channel4Data, peakLocation, colors, verticalUnit, verticalSegmentNumber, 100, 100, predictor,false, true, true, true, false);
				rawPaintPanel = new DrawUnprocessedChannelDataPanel(rawchannel1Data.length + 200, verticalSegmentNumber + 400,  rawchannel1Data, rawchannel2Data, rawchannel3Data,
						rawchannel4Data, colors, verticalUnit, verticalSegmentNumber, 100, 100);
				centralPanel.setRawPaintPanel(rawPaintPanel);

				JScrollPane scrollPane = new JScrollPane();

				// size paint panel and save it
				paintPanel.setPreferredSize(new Dimension(channel1Data.length + 200, verticalSegmentNumber + 200));


				// centralPara.setPaintPanel(paintPanel);
				centralPanel.setPaintPanel(paintPanel);

				// get old scrollpane but add paint panel to it now
				scrollPane = centralPara.getDrawPanel();
				scrollPane.setSize(600, 400);
				scrollPane.getViewport().add(paintPanel);
				
				
				centralPanel.setDrawPanel(scrollPane);
				tabbedPane.addTab(fileName, scrollPane);

				centralPara.setTabbedPane(tabbedPane);


				// remove old slider from sliderholder
				JPanel sliderHolder = centralPara.getSliderHolder();
				sliderHolder.removeAll();

				// add new slider based off current input and give listener
				JSlider rangeSlider = new JSlider(JSlider.VERTICAL, 1, halfMaxValue * 2, halfMaxValue);

				RangeChangeListener rangeListener = new RangeChangeListener(centralPara, centralPanel, centralList);
				rangeSlider.addChangeListener(rangeListener);
				rangeSlider.setPreferredSize(new Dimension(10,300));
				centralPanel.setRangeSlider(rangeSlider);
				rangeSlider.setToolTipText("Range selector");
				centralPanel.setRangeSlider(rangeSlider);
				sliderHolder.add(rangeSlider);
				centralPanel.setSliderHolder(sliderHolder);
				centralPanel.setRangeLocation(halfMaxValue);


				//add to list of panels and flag the end of start 
				centralPara.setStart(false);
				centralList.add(centralPanel);
				centralPanel.setRecent(false);

			}
		}

		// if not the first tab window do this
		else {
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				// check current number of windows open and update by one
				int currentCount = centralPara.getNumOpen();
				int newCount = currentCount + 1;
				centralPara.setNumOpen(newCount);

				// new panel storage for opened file. give it a number
				CentralPanelStorage centralPanel = new CentralPanelStorage();
				centralPanel.setNum(newCount - 1);
				centralPanel.setRecent(true);
				//add to panel list
				centralList.add(centralPanel);


				inputFsaFileLocation = fileChooser.getSelectedFile().getAbsolutePath();

				// get file name excluding path info
				File tempFile = new File(inputFsaFileLocation);
				String fileName = tempFile.getName();
				centralPanel.setFileName(fileName);

				//hard coded segment 
				verticalSegmentNumber = 200;

				//base veritcal segment number off of how told the window is since opening 
				if(centralPara.getWindowHeight() > 527){

					int taller =  centralPara.getWindowHeight() - 527 ;
					verticalSegmentNumber = verticalSegmentNumber +taller;

				}
				centralPanel.setVerticalSegmentNumber(verticalSegmentNumber);
				centralPanel.setReferenceHeight(centralPara.getMainPanel().getHeight());
			

				// load key dirEntry data
				ArrayList<AbifDirEntryBean> dirEntryList = ABIFbinaryFileLoader.read(inputFsaFileLocation);

			

				// save nucleotide with color which can be changed later

				Color[] colors = new Color[6];
				colors[0] = Color.black;
				colors[1] = Color.magenta;
				colors[2] = Color.green;
				colors[3] = Color.red;
				colors[4] = Color.blue;
				colors[5] = new Color(1f,0f,0f,.3f );
				centralPanel.setColors(colors);

			



				// load raw channel data file and save to central parameter
				// storage
				short[] channel1Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"DATA", 9);
				centralPanel.setChannel1Data(channel1Data);
				short[] channel2Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"DATA", 10);
				centralPanel.setChannel2Data(channel2Data);
				short[] channel3Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"DATA", 11);
				centralPanel.setChannel3Data(channel3Data);
				short[] channel4Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"DATA", 12);
				centralPanel.setChannel4Data(channel4Data);


				//raw data 
				short[] rawchannel1Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"DATA", 1);
				centralPanel.setRawChannel1Data(rawchannel1Data);
				short[] rawchannel2Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"DATA", 2);
				centralPanel.setRawChannel2Data(rawchannel2Data);
				short[] rawchannel3Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"DATA", 3);
				centralPanel.setRawChannel3Data(rawchannel3Data);
				short[] rawchannel4Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"DATA", 4);
				centralPanel.setRawChannel4Data(rawchannel4Data);

				// save quality data
				byte[] qualScore = LoadDataBySpecificNameAndNumber.loadByteData(dirEntryList, inputFsaFileLocation,
						"PCON", 2);
				byte[] qualScoreNuc = LoadDataBySpecificNameAndNumber.loadByteData(dirEntryList, inputFsaFileLocation,
						"PBAS", 2);
				short[] peakLocation = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
						"PLOC", 2);
				byte[] reverseCompNuc = AnalyzeABIFdata.reverseComplementNucs(qualScoreNuc);
				short[] reverseLocations = AnalyzeABIFdata.reverseLocations(peakLocation);
				
				
				
				centralPanel.setPeakLocation(peakLocation);
				centralPanel.setQualScore(qualScore);
				centralPanel.setQualScoreNuc(qualScoreNuc);
				centralPanel.setReverseCompNuc(reverseCompNuc);
				centralPanel.setReverseLoc(reverseLocations);
				
				


				//good region algorithms 

				ArrayList<Integer> coList = AnalyzeABIFdata.coAlgorithm(channel1Data, channel2Data, channel3Data, channel4Data, peakLocation); 
				short sil[] = AnalyzeABIFdata.getMaxValArray(channel1Data, channel2Data, channel3Data, channel4Data);
				short[] valley = AnalyzeABIFdata.valleyFinder(peakLocation, sil);
				float[] doList = AnalyzeABIFdata.doAlgorithm(peakLocation, valley, sil);
				centralPanel.setCoList(coList);
				centralPanel.setDoList(doList);
				//set default vals
				int threshold = 30;
				int threshold2 = 20;
				int clearanceLevel = 20;
				int peak2ValleyDiff = 30;
				TreeMap<Integer,Integer> predictor = AnalyzeABIFdata.predictiveAlgorithm(qualScore, threshold,threshold2, coList, clearanceLevel, doList,peak2ValleyDiff, 5 );
				centralPanel.setThreshold(threshold);
				centralPanel.setThreshold2(threshold2);
				centralPanel.setclearanceLevel(clearanceLevel);
				centralPanel.setPeak2ValleyDiff(peak2ValleyDiff);
				centralPanel.setPredict(predictor);

				
				//create detail pane 

				JTextPane detPane = new JTextPane();
				detPane.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
				detPane.setBackground(new Color(220,220,220));
				//load general parameters 
				short AEPt2 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "AEPt", 2);//analysis ending scan number for basecalling last analysis 
				appendToPane(detPane, "AEPt2: " + AEPt2 +"\n"  , Color.BLACK);
				String APFN2 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation,"APFN", 2); //Sequecing Analysis parameter file name
				appendToPane(detPane, "APFN2: " + APFN2 +"\n"  , Color.BLACK);
				//String APXV = LoadDataBySpecificNameAndNumber.loadCStringData(dirEntryList, inputFsaFileLocation,
				//"APXV", 1); //Anaylsis Protocol XML schema Version
				//skipping APXV, APrN, APrV, ARTN1
				short ASPF1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "ASPF", 1); //flag if adaptive processing worked
				appendToPane(detPane, "ASPF1: " + ASPF1 +"\n"  , Color.BLACK);
				short ASPt1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "ASPt", 1); //Analysis starting scan number initial analysis 
				appendToPane(detPane, "ASPt1: " + ASPt1 +"\n"  , Color.BLACK);
				short ASPt2 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "ASPt", 2); //Analysis starting scan number ending analysis 
				appendToPane(detPane, "ASPt2: " + ASPt2 +"\n"  , Color.BLACK);
				short B1Pt1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "B1Pt", 1); //Reference Scan number for mobility and spacing curves for first analysis
				appendToPane(detPane, "B1Pt1: " + B1Pt1 +"\n"  , Color.BLACK);
				short B1Pt2 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "B1Pt", 2); //Reference Scan number for mobility and spacing curves for last analysis
				String CMNT1 =  LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation,"CMNT", 1); //Sample comment
				appendToPane(detPane, "CMNT1: " + CMNT1 +"\n"  , Color.BLACK);
				//skipping CTID CTNM
				String CTTL1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation,"CTTL", 1); //comment title  
				appendToPane(detPane, "CTTL1: " + CTTL1 +"\n"  , Color.BLACK);
				//short CpEP1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "CpEP", 1);
				//char[] CpEP = FindDataInOffsetField.findOffsetAsChar(dirEntryList, inputFsaFileLocation, "CpEP", 1); //is capillary machine?
				short DSam1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "DSam", 1); //Downsampling 				
				appendToPane(detPane, "DSam1: " + DSam1 +"\n"  , Color.BLACK);
				String DySN1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation,"DySN", 1); //dye set name 
				appendToPane(detPane, "DySN1: " + DySN1 +"\n"  , Color.BLACK);
				short DyeP1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "Dye#", 1); //Number of dyes
				appendToPane(detPane, "DyeP1: " + DyeP1 +"\n"  , Color.BLACK);
				String DyeN1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "DyeN", 1); // dye name1  
				appendToPane(detPane, "DyeN1: " + DyeN1 +"\n"  , Color.BLACK);
				String DyeN2 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation,	"DyeN", 2); // dye name2
				appendToPane(detPane, "DyeN2: " + DyeN2 +"\n"  , Color.BLACK);
				String DyeN3 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "DyeN", 3); //dye name3 
				appendToPane(detPane, "DyeN3: " + DyeN3 +"\n"  , Color.BLACK);
				String DyeN4 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "DyeN", 4); //dye name4  
				appendToPane(detPane, "DyeN4: " + DyeN4 +"\n"  , Color.BLACK);
				short DyeW1 =  FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "DyeW", 1); //dye1 wavelength 
				appendToPane(detPane, "DyeW1: " + DyeW1 +"\n"  , Color.BLACK);
				short DyeW2 =  FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "DyeW", 2); //dye2 wavelength 
				appendToPane(detPane, "DyeW2: " + DyeW2 +"\n"  , Color.BLACK);
				short DyeW3 =  FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "DyeW", 3); //dye3 wavelength 
				appendToPane(detPane, "DyeW3: " + DyeW3 +"\n"  , Color.BLACK);
				short DyeW4 =  FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "DyeW", 4); //dye4 wavelength
				appendToPane(detPane, "DyeW4: " + DyeW4 +"\n"  , Color.BLACK);
				//long EPVt1 = LoadDataBySpecificNameAndNumber.loadLongData(dirEntryList, inputFsaFileLocation, "EPVt", 1); //Electrophoresis voltage setting
				//System.out.println(EPVt1);
				String EVNT1 =  LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "EVNT", 1); // Start event run
				appendToPane(detPane, "EVNT1: " + EVNT1 +"\n"  , Color.BLACK);
				String EVNT2 =  LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "EVNT", 2); // End event run
				appendToPane(detPane, "EVNT2: " + EVNT2 +"\n"  , Color.BLACK);
				String EVNT3 =  LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "EVNT", 3); // start Collection event
				appendToPane(detPane, "EVNT3: " + EVNT3 +"\n"  , Color.BLACK);
				String EVNT4 =  LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "EVNT", 4); // end collection event
				appendToPane(detPane, "EVNT4: " + EVNT4 +"\n"  , Color.BLACK);
				String GTyp1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "GTyp", 1); //Gel Type Description
				appendToPane(detPane, "GTyp1: " + GTyp1 +"\n"  , Color.BLACK);
				//FTab1
				//FVoc1
				//Feat1
				//long InSc1 injection time
				//long InVt injection voltage
				short LANE1 =  FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "LANE", 1); // Lane / capillary 
				appendToPane(detPane, "LANE1: " + LANE1 +"\n"  , Color.BLACK);
				String LIMS1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "LIMS", 1); // Sample tracking ID
				appendToPane(detPane, "LIMS1: " + LIMS1 +"\n"  , Color.BLACK);
				short LNTD1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "LNTD", 1); //length to dectector 
				appendToPane(detPane, "LNTD1: " + LNTD1 +"\n"  , Color.BLACK);
				//LsrP1
				String MCHN1 =  LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "MCHN", 1); //	Instrument name and serial num 
				appendToPane(detPane, "MCHN1: " + MCHN1 +"\n"  , Color.BLACK);
				String MODF1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "MODF", 1);  // data colleciton module file 
				appendToPane(detPane, "MODF1: " + MODF1 +"\n"  , Color.BLACK);
				String MODL1 = FindDataInOffsetField.findOffsetAsString(dirEntryList, inputFsaFileLocation, "MODL", 1); 
				appendToPane(detPane, "MODL1: " + MODL1 +"\n"  , Color.BLACK);
				short NAVG1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "NAVG", 1); //pixels averaged per lane
				appendToPane(detPane, "NAVG1: " + NAVG1 +"\n"  , Color.BLACK);
				short NLNE1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "NLNE", 1);  //number of capillaries 
				appendToPane(detPane, "NLNE1: " + NLNE1 +"\n"  , Color.BLACK);
				//NOIS
				//OFFS
				//OfSc
				String PDMF1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "PDMF", 1); //Sequencing analysis mobility file name 
				appendToPane(detPane, "PDMF1: " + PDMF1 +"\n"  , Color.BLACK);
				String PDMF2 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "PDMF", 2); // same as 1
				appendToPane(detPane, "PDMF2: " + PDMF2 +"\n"  , Color.BLACK);
				//PXLB1
				//RGNm1
				//RMXV1
				//RMdN1
				//RMdV1
				//RPrN1
				//RPrV1
				String RUND1 = FindDataInOffsetField.findOffsetofDate(dirEntryList, inputFsaFileLocation, "RUND", 1);
				appendToPane(detPane, "RUND1: " + RUND1 +"\n"  , Color.BLACK);
				String RUND2 = FindDataInOffsetField.findOffsetofDate(dirEntryList, inputFsaFileLocation, "RUND", 2);
				appendToPane(detPane, "RUND2: " + RUND2 +"\n"  , Color.BLACK);
				String RUND3 = FindDataInOffsetField.findOffsetofDate(dirEntryList, inputFsaFileLocation, "RUND", 3);
				appendToPane(detPane, "RUND3: " + RUND3 +"\n"  , Color.BLACK);
				String RUND4 = FindDataInOffsetField.findOffsetofDate(dirEntryList, inputFsaFileLocation, "RUND", 4);
				appendToPane(detPane, "RUND4: " + RUND4 +"\n"  , Color.BLACK);
				String RUNT1 = FindDataInOffsetField.findOffsetofTime(dirEntryList, inputFsaFileLocation, "RUNT", 1);
				appendToPane(detPane, "RUNT1: " + RUNT1 +"\n"  , Color.BLACK);
				String RUNT2 = FindDataInOffsetField.findOffsetofTime(dirEntryList, inputFsaFileLocation, "RUNT", 2);
				appendToPane(detPane, "RUNT2: " + RUNT2 +"\n"  , Color.BLACK);
				String RUNT3 = FindDataInOffsetField.findOffsetofTime(dirEntryList, inputFsaFileLocation, "RUNT", 3);
				appendToPane(detPane, "RUNT3: " + RUNT3 +"\n"  , Color.BLACK);
				String RUNT4 = FindDataInOffsetField.findOffsetofTime(dirEntryList, inputFsaFileLocation, "RUNT", 4);
				appendToPane(detPane, "RUNT4: " + RUNT4 +"\n"  , Color.BLACK);
				//Rate1
				//RevC1
				//RunN1
				short SN1[] = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation, "S/N%", 1); //Signal Strength 
				appendToPane(detPane, "S/N%1: " + SN1[0] + " " + SN1[1] + " " + SN1[2] + " " + SN1[3] +"\n"  , Color.BLACK);
				//SCAN1
				String SMED1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "SMED", 1); // Seperation medium lot exp date
				appendToPane(detPane, "SMED1: " + SMED1 +"\n"  , Color.BLACK);
				String SMLt1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "SMLt", 1); // Seperation meduum lot number 
				appendToPane(detPane, "SMLt1: " + SMLt1 +"\n"  , Color.BLACK);
				String SMPL1 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "SMPL", 1); //Sequencing analysis sample number
				appendToPane(detPane, "SMPL1: " + SMPL1 +"\n"  , Color.BLACK);
				float SPAC1 = FindDataInOffsetField.findOffsetAsFloat(dirEntryList, inputFsaFileLocation, "SPAC", 1); //average peak spacing in last analysis 
				appendToPane(detPane, "SPAC1: " + SPAC1 +"\n"  , Color.BLACK);
				String SPAC2 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "SPAC", 2); //basecaller name    
				appendToPane(detPane, "SPAC2: " + SPAC2 +"\n"  , Color.BLACK);
				float SPAC3 = FindDataInOffsetField.findOffsetAsFloat(dirEntryList, inputFsaFileLocation, "SPAC", 3); //average peak spacing last calculated by basecaller
				appendToPane(detPane, "SPAC3: " + SPAC3 +"\n"  , Color.BLACK);
				String SVER1 = FindDataInOffsetField.findOffsetAsString(dirEntryList, inputFsaFileLocation, "SVER", 1); //data  collection version number
				appendToPane(detPane, "SVER1: " + SVER1 +"\n"  , Color.BLACK);
				String SVER2 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "SVER", 2); //basecaller version number  
				appendToPane(detPane, "SVER2: " + SVER2 +"\n"  , Color.BLACK);
				String SVER3 = LoadDataBySpecificNameAndNumber.loadPStringData(dirEntryList, inputFsaFileLocation, "SVER", 3); //Firmware version number 
				appendToPane(detPane, "SVER3: " + SVER3 +"\n"  , Color.BLACK);
				//Satd1
				//ScSt1
				float Scal1 = FindDataInOffsetField.findOffsetAsFloat(dirEntryList, inputFsaFileLocation, "Scal", 1); 
				appendToPane(detPane, "Scal1: " + Scal1 +"\n"  , Color.BLACK);
				short Scan1 = FindDataInOffsetField.findOffsetShort(dirEntryList, inputFsaFileLocation, "Scan", 1); // number of scans, redundant with "SCAN" (legacy)
				appendToPane(detPane, "Scan1: " + Scan1 +"\n"  , Color.BLACK);
				String TUBE1 =  FindDataInOffsetField.findOffsetAsString(dirEntryList, inputFsaFileLocation, "TUBE", 1); //autosampler position 
				appendToPane(detPane, "TUBE1: " + TUBE1 +"\n"  , Color.BLACK);
				//Tmpr1
				String User1 = FindDataInOffsetField.findOffsetAsString(dirEntryList, inputFsaFileLocation, "User", 1); //name of plate creator  
				appendToPane(detPane, "User1: " + User1 +"\n"  , Color.BLACK);
				//set details pane at end
				detPane.setEditable(false);
				detPane.setHighlighter(null);
				centralPanel.setDetPane(detPane);

				// figure out half max value and max peak. use half value for
				// segment?
				halfMaxValue = AnalyzeABIFdata.getAllMaxVal(channel1Data, channel2Data, channel3Data, channel4Data) / 2;
				maxValue1 = AnalyzeABIFdata.getMaxVal(channel1Data);
				maxValue2 = AnalyzeABIFdata.getMaxVal(channel2Data);
				maxValue3 = AnalyzeABIFdata.getMaxVal(channel3Data);
				maxValue4 = AnalyzeABIFdata.getMaxVal(channel4Data);


				


				// find the highest peak in all channels and default at half max
				// peak for now
				int compareMax = 0;
				int compare[] = { maxValue1, maxValue2, maxValue3, maxValue4 };

				for (int i = 0; i < compare.length; i++) {
					if (compare[i] > compareMax)
						compareMax = compare[i];
				}

				halfMaxValue = compareMax;

				int verticalUnit = (int) Math.ceil((double) halfMaxValue / (double) verticalSegmentNumber);
				centralPanel.setVerticalUnit(verticalUnit);
				centralPanel.setVerticalMaxVal(halfMaxValue);
				
				
				// all drawing is after this------------------------------------------
				//create panes for info panel 
				//sequence area 
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
				JSplitPane seqSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
				//seqSplit.setPreferredSize(new Dimension(350,350));			
				seqSplit.setRightComponent(seqArea);
				seqSplit.setLeftComponent(numArea);
				seqSplit.setDividerSize(0);
				appendToPane(numArea, "   1\n", Color.BLUE);
				for(int i = 0; i <qualScoreNuc.length; i++){

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

					String nuc = String.valueOf((char) qualScoreNuc[i]);
					appendToPane(seqArea, nuc, Color.BLACK);
				}
				seqArea.setEditable(false);
				numArea.setEditable(false);
				centralPanel.setSeqSplit(seqSplit);


				
				//create good region pane 
				JTextPane goodSeqArea = new JTextPane();
				goodSeqArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
				goodSeqArea.setBackground(new Color(220,220,220));
				ContextMenuMouseListener rightClick2 = new ContextMenuMouseListener();
				goodSeqArea.addMouseListener(rightClick2);
				
				String goodSeqText = AnalyzeABIFdata.processRegionOutputFasta(predictor, qualScoreNuc, fileName, false);
				appendToPane(goodSeqArea, goodSeqText, Color.BLACK);
				goodSeqArea.setEditable(false);
				centralPanel.setGoodSeqArea(goodSeqArea);
				
				
				//create reverse good region pane 
				JTextPane rGoodSeqArea = new JTextPane();
				rGoodSeqArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
				rGoodSeqArea.setBackground(new Color(220,220,220));
				ContextMenuMouseListener rightClick3 = new ContextMenuMouseListener();
				rGoodSeqArea.addMouseListener(rightClick3);
				String rGoodSeqText = AnalyzeABIFdata.processRegionOutputFasta(predictor, reverseCompNuc, fileName, true);
				appendToPane(rGoodSeqArea, rGoodSeqText, Color.BLACK);
				rGoodSeqArea.setEditable(false);
				centralPanel.setRGoodSeqArea(rGoodSeqArea);
				
				//-------------------------------------------------------
				//create general pane 

				JTextPane genPane = new JTextPane();
				genPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
				genPane.setBackground(new Color(220,220,220));
				genPane.setHighlighter(null);

				StyledDocument doc = genPane.getStyledDocument();
				SimpleAttributeSet center = new SimpleAttributeSet();
				StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
				doc.setParagraphAttributes(0, doc.getLength(), center, false);
				appendToPane(genPane, "Filename: " + inputFsaFileLocation +"\n", Color.BLACK);
				appendToPane(genPane, "Format: ABIF \n"  , Color.BLACK);
				appendToPane(genPane, "Sample ID: " + TUBE1 + "\n", Color.black);
				appendToPane(genPane, "Sample Name: " + SMPL1 + "\n", Color.black);
				appendToPane(genPane, "Plate Label: \n",  Color.black);
				appendToPane(genPane, "Instr. Model: " + MODL1 + "\n", Color.black);
				appendToPane(genPane, "Instr. Name: " + MCHN1 + "\n", Color.black);
				appendToPane(genPane, "Run Start: " + RUND1 + " " + RUNT1 + "\n", Color.black);
				appendToPane(genPane, "Run Stop: " + RUND2 + " " + RUNT2 + "\n", Color.black);
				appendToPane(genPane, "Lane1: " + LANE1 + "\n", Color.black);
				appendToPane(genPane, "#Lanes: " + NLNE1 + "\n", Color.black);
				appendToPane(genPane, "Spacing: " + SPAC1 + "\n", Color.black);
				appendToPane(genPane, "Signal Strs: " + " " + "A = " + SN1[1] + ", C = "  + SN1[3] + ", G = " + SN1[0] + ", T = "  + SN1[2] + "\n", Color.black);
				appendToPane(genPane, "Mobility: " + PDMF1 + "\n", Color.black);
				appendToPane(genPane, "Matrix: \n", Color.black);
				appendToPane(genPane, "Comment: " + CMNT1 + "\n", Color.black);
				genPane.setEditable(false);
				centralPanel.setGenPane(genPane);



				JTabbedPane tabbedPane = centralPara.getTabbedPane();
				// tabbedPane.removeAll();
				tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

				// create paint panel and the scroll it will go in
				paintPanel = new DrawRawChannelDataPanel(channel1Data.length + 200, verticalSegmentNumber + 2200,
						 qualScore, qualScoreNuc, channel1Data, channel2Data, channel3Data,
						channel4Data, peakLocation, colors, verticalUnit, verticalSegmentNumber, 100, 100, predictor,false,true, true, true, false);
				rawPaintPanel = new DrawUnprocessedChannelDataPanel(channel1Data.length + 200, verticalSegmentNumber + 200, rawchannel1Data, rawchannel2Data, rawchannel3Data,
						rawchannel4Data, colors, verticalUnit, verticalSegmentNumber, 100, 100);

				centralPanel.setRawPaintPanel(rawPaintPanel);

				JScrollPane scrollPane = new JScrollPane();

				// size paint panel and save it along with tab info
				paintPanel.setPreferredSize(new Dimension(channel1Data.length + 200, verticalSegmentNumber + 200));
				centralPanel.setPaintPanel(paintPanel);
				scrollPane.setSize(600, 400);
				scrollPane.getViewport().add(paintPanel);
				centralPanel.setDrawPanel(scrollPane);
				tabbedPane.addTab(fileName, scrollPane);
				centralPara.setTabbedPane(tabbedPane);

				tabbedPane.setSelectedIndex(centralPara.getNumOpen()-1);



				// remove old slider from sliderholder
				JPanel sliderHolder = centralPara.getSliderHolder();
				sliderHolder.removeAll();

				// add new slider based off current input and give listener
				JSlider rangeSlider = new JSlider(JSlider.VERTICAL, 1, halfMaxValue * 2, halfMaxValue);
				rangeSlider.setPreferredSize(new Dimension(10,300));
				centralPanel.setRangeLocation(halfMaxValue);
				RangeChangeListener rangeListener = new RangeChangeListener(centralPara, centralPanel, centralList);
				rangeSlider.addChangeListener(rangeListener);
				centralPanel.setRangeSlider(rangeSlider);
				rangeSlider.setToolTipText("Range selector");
				sliderHolder.add(rangeSlider);
				centralPanel.setSliderHolder(sliderHolder);


				// refresh mainframe to update all changes
				JPanel tempMainPanel = centralPara.getMainPanel();
				tempMainPanel.revalidate();
				tempMainPanel.repaint();
				centralPara.setMainPanel(tempMainPanel);
				centralPanel.setRecent(false);


			}
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
