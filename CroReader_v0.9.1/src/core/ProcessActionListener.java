package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;

public class ProcessActionListener implements ActionListener {


	private CentralParameterStorage centralPara = null;
	ArrayList<CentralPanelStorage> centralList = null;
	private CentralPanelStorage centralPanel = null;
	Dimension fileChooserDim = new Dimension(700, 600);
	public static String inputFsaFileLocation = null;

	private byte[] qualScore;
	private byte[] qualScoreNuc;
	private short[] channel1Data;
	private short[] channel2Data;
	private short[] channel3Data;
	private short[] channel4Data;
	private short[] peakLocation;
	private String filename;

	public ProcessActionListener(CentralParameterStorage centralPara, CentralPanelStorage centralPanel,
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


			qualScore = centralPanel.getQualScore();
			qualScoreNuc = centralPanel.getQualScoreNuc();
			channel1Data = centralPanel.getChannel1Data();
			channel2Data = centralPanel.getChannel2Data();
			channel3Data = centralPanel.getChannel3Data();
			channel4Data = centralPanel.getChannel4Data();
			peakLocation = centralPanel.getPeakLocation();
			filename = centralPanel.getFileName();
			byte[] reverseQualScore = AnalyzeABIFdata.reverseQualScore(qualScore);


			ABIFframe parent = centralPara.getMainFrame();
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));


	
			int threshold = centralPanel.getThreshold();
			JSlider qualityThreshold = new JSlider(JSlider.HORIZONTAL,
					20, 40, threshold);

			qualityThreshold.setFocusable(false);
			qualityThreshold.setMajorTickSpacing(10);
			qualityThreshold.setMinorTickSpacing(1);
			qualityThreshold.setPaintLabels(true);
			qualityThreshold.setPaintTicks(true);

			//threshold2 senesitivity slider
			int threshold2 = centralPanel.getThreshold2();

			JSlider qualityThreshold2 = new JSlider(JSlider.HORIZONTAL,
					10, 25, threshold2);

			qualityThreshold2.setFocusable(false);
			qualityThreshold2.setMajorTickSpacing(10);
			qualityThreshold2.setMinorTickSpacing(1);
			qualityThreshold2.setPaintLabels(true);
			qualityThreshold2.setPaintTicks(true);

			//clearance slider
			int clearance = centralPanel.getClearanceLevel();

			JSlider clearanceSlider = new JSlider(JSlider.HORIZONTAL,
					20, 50, clearance);

			clearanceSlider.setFocusable(false);
			clearanceSlider.setMajorTickSpacing(10);
			clearanceSlider.setMinorTickSpacing(1);
			clearanceSlider.setPaintLabels(true);
			clearanceSlider.setPaintTicks(true);

			
			//Peak to valley differential 
	

			int differential = centralPanel.getPeak2ValleyDiff();

			JSlider differentialSlider = new JSlider(JSlider.HORIZONTAL,
					20, 50, differential);

			differentialSlider.setFocusable(false);
			differentialSlider.setMajorTickSpacing(10);
			differentialSlider.setMinorTickSpacing(1);
			differentialSlider.setPaintLabels(true);
			differentialSlider.setPaintTicks(true);
			
			//Sliding window size 
			

			int window = centralPanel.getWindow();

			JSlider windowSlider = new JSlider(JSlider.HORIZONTAL,
					3, 29, window);

			windowSlider.setFocusable(false);
			windowSlider.setMajorTickSpacing(10);
			windowSlider.setMinorTickSpacing(1);
			windowSlider.setPaintLabels(true);
			windowSlider.setPaintTicks(true);


			//			panel.add(thresholdCombo);
			//			panel.add(Box.createRigidArea(new Dimension(0,20)));
			//			panel.add(threshold2Combo);
			//			panel.add(Box.createRigidArea(new Dimension(0,20)));
			//			panel.add(clearanceCombo);
			//			panel.add(Box.createRigidArea(new Dimension(0,20)));
			//			panel.add(differentialCombo);
			//			panel.add(Box.createRigidArea(new Dimension(0,20)));

			String[] choices= {"FASTA", "FASTQ"};

			JComboBox fileExt = new JComboBox<>(choices);

			Object[] inputFields = {"Threshold Sensitivity for entering good region", qualityThreshold,
					"Threshold for staying in good region", qualityThreshold2,
					"Peak Clearance", clearanceSlider, 
					"Peak to valley differential", differentialSlider,
					"Sliding window size", windowSlider,
					"Output Type", fileExt };

			int option = JOptionPane.showOptionDialog(parent, inputFields, "Process Current File Good Regions", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Choose Path", "Cancel"},
					"default");

			if (option == JOptionPane.OK_OPTION) {
				//   String text = qualityThreshold.getValue() + "\n" + (checkBox.isSelected() ? "Checked" : "Unchecked") + "\n" + textField2.getText() + "\n";
				//     textArea.setText(text);

				JFileChooser exportFile = new JFileChooser();
				exportFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
				//FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("txt", "txt");
				//exportFile.setAcceptAllFileFilterUsed(false);
				//exportFile.addChoosableFileFilter(txtFilter);
				exportFile.setPreferredSize(fileChooserDim);
				int returnVal = exportFile.showSaveDialog(null);
				String output = "";

				// if user presses save continue
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					inputFsaFileLocation = exportFile.getSelectedFile().getAbsolutePath();

					// check to see if file has txt extension, if not append on to file
					// name

					//					if(fileExt.getSelectedItem() == "Plain Text"){
					//						if (!inputFsaFileLocation.substring(inputFsaFileLocation.length() - 3, inputFsaFileLocation.length())
					//								.equals("txt") || inputFsaFileLocation.length() < 3) {
					//							inputFsaFileLocation += ".txt";
					//						}
					//					}

					if(fileExt.getSelectedItem() == "FASTA"){
						if (!inputFsaFileLocation.substring(inputFsaFileLocation.length() - 4, inputFsaFileLocation.length())
								.equals(".fna") || inputFsaFileLocation.length() < 4) {
							inputFsaFileLocation += ".fna";
						}
					}

					else if(fileExt.getSelectedItem() == "FASTQ"){
						if (!inputFsaFileLocation.substring(inputFsaFileLocation.length() - 3, inputFsaFileLocation.length())
								.equals(".fq") || inputFsaFileLocation.length() < 3) {
							inputFsaFileLocation += ".fq";
						}
					}

					ArrayList<Integer> coList = AnalyzeABIFdata.coAlgorithm(channel1Data, channel2Data, channel3Data, channel4Data, peakLocation); 

					short sil[] = AnalyzeABIFdata.getMaxValArray(channel1Data, channel2Data, channel3Data, channel4Data);
					short[] valley = AnalyzeABIFdata.valleyFinder(peakLocation, sil);
					float[] doList = AnalyzeABIFdata.doAlgorithm(peakLocation, valley, sil);
					TreeMap<Integer, Integer> predictor = AnalyzeABIFdata.predictiveAlgorithm(qualScore, qualityThreshold.getValue(), qualityThreshold2.getValue(), coList, clearanceSlider.getValue(), doList, differentialSlider.getValue(), windowSlider.getValue()); 

					if(centralPanel.getReversed()){
						if(fileExt.getSelectedItem() == "FASTA"){
							output = AnalyzeABIFdata.processRegionOutputFasta(predictor, centralPanel.getReverseCompNuc(), filename, centralPanel.getReversed());
						}
						else if(fileExt.getSelectedItem() == "FASTQ"){
							output = AnalyzeABIFdata.processRegionOutputFastq(predictor, centralPanel.getReverseCompNuc(), reverseQualScore, filename, centralPanel.getReversed());
						}
					}

					else{
						if(fileExt.getSelectedItem() == "FASTA"){
							output = AnalyzeABIFdata.processRegionOutputFasta(predictor, qualScoreNuc, filename, centralPanel.getReversed());
						}
						else if(fileExt.getSelectedItem() == "FASTQ"){
							output = AnalyzeABIFdata.processRegionOutputFastq(predictor, qualScoreNuc, qualScore, filename, centralPanel.getReversed());
						}
					}

					BufferedWriter bw = null;
					FileWriter fw = null;

					try {
						fw = new FileWriter(inputFsaFileLocation);
						bw = new BufferedWriter(fw);
						bw.write(output);

						System.out.println("Done");

					} catch (IOException e) {

						e.printStackTrace();

					} finally {

						try {

							if (bw != null)
								bw.close();

							if (fw != null)
								fw.close();

						} catch (IOException ex) {

							ex.printStackTrace();

						}
					}
				}
			}

			//JOptionPane dialog = new JOptionPane();

			//create custom dialog box, point to parent, give name, modal true, pass component
			//ChromDialog dialogBox= new ChromDialog(parent,"Threshold Options", false, panel);

			//dialogBox.setVisible(true);



		}
	}
}