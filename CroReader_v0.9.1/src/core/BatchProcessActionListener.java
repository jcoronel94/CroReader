package core;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;


/*
 * batch process listener. Uses a JOptionPane for input 
 */

public class BatchProcessActionListener implements ActionListener {


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

	public BatchProcessActionListener(CentralParameterStorage centralPara, CentralPanelStorage centralPanel,
			ArrayList<CentralPanelStorage> centralList) {
		this.centralPara = centralPara;
		this.centralPanel = centralPanel;
		this.centralList = centralList;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {


		ABIFframe parent = centralPara.getMainFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));


		//threshold senesitivity slider
		JSlider qualityThreshold = new JSlider(JSlider.HORIZONTAL,
				20, 40, 32);

		qualityThreshold.setFocusable(false);
		qualityThreshold.setMajorTickSpacing(10);
		qualityThreshold.setMinorTickSpacing(1);
		qualityThreshold.setPaintLabels(true);
		qualityThreshold.setPaintTicks(true);

		//threshold2 senesitivity slider


		JSlider qualityThreshold2 = new JSlider(JSlider.HORIZONTAL,
				10, 25, 20);

		qualityThreshold2.setFocusable(false);
		qualityThreshold2.setMajorTickSpacing(10);
		qualityThreshold2.setMinorTickSpacing(1);
		qualityThreshold2.setPaintLabels(true);
		qualityThreshold2.setPaintTicks(true);


		//clearance slider


		JSlider clearanceSlider = new JSlider(JSlider.HORIZONTAL,
				20, 50, 30);

		clearanceSlider.setFocusable(false);
		clearanceSlider.setMajorTickSpacing(10);
		clearanceSlider.setMinorTickSpacing(1);
		clearanceSlider.setPaintLabels(true);
		clearanceSlider.setPaintTicks(true);


		//Peak to valley differential 

		JSlider differentialSlider = new JSlider(JSlider.HORIZONTAL,
				20, 50, 32);

		differentialSlider.setFocusable(false);
		differentialSlider.setMajorTickSpacing(10);
		differentialSlider.setMinorTickSpacing(1);
		differentialSlider.setPaintLabels(true);
		differentialSlider.setPaintTicks(true);
		

		//Sliding window size 

		

		JSlider windowSlider = new JSlider(JSlider.HORIZONTAL,
				3, 29, 5 );

		windowSlider.setFocusable(false);
		windowSlider.setMajorTickSpacing(10);
		windowSlider.setMinorTickSpacing(1);
		windowSlider.setPaintLabels(true);
		windowSlider.setPaintTicks(true);





		String[] choices= {"FASTA", "FASTQ"};

		JComboBox fileExt = new JComboBox<>(choices);

		Object[] inputFields = {"Threshold Sensitivity for entering good region", qualityThreshold,
				"Threshold for staying in good region", qualityThreshold2,
				"Peak Clearance", clearanceSlider, 
				"Peak to valley differential", differentialSlider,
				"Sliding window size", windowSlider,
				"Output Type", fileExt };


		int option = JOptionPane.showOptionDialog(parent, inputFields, "Batch Process Good Regions", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Choose Files", "Cancel"},
				"default");

		if (option == JOptionPane.OK_OPTION) {

			JFileChooser chooser = new JFileChooser();
			//chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setMultiSelectionEnabled(true);
			int returnVal = chooser.showOpenDialog(null);
			File[] files = chooser.getSelectedFiles();
			String output = "";
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				for(int i =0; i<files.length;i++){

					String loc = files[i].getAbsolutePath();
					filename = files[i].getName();

					// load key dirEntry data
					ArrayList<AbifDirEntryBean> dirEntryList = ABIFbinaryFileLoader.read(loc);

					// save all channel data
					short[] channel1Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, loc,
							"DATA", 9);

					short[] channel2Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, loc,
							"DATA", 10);

					short[] channel3Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, loc,
							"DATA", 11);

					short[] channel4Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, loc,
							"DATA", 12);

					// save quality score data and peak location
					byte[] qualScore = LoadDataBySpecificNameAndNumber.loadByteData(dirEntryList, loc,
							"PCON", 2);
					byte[] qualScoreNuc = LoadDataBySpecificNameAndNumber.loadByteData(dirEntryList, loc,
							"PBAS", 2);
					short[] peakLocation = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, loc,
							"PLOC", 2);

					ArrayList<Integer> coList = AnalyzeABIFdata.coAlgorithm(channel1Data, channel2Data, channel3Data, channel4Data, peakLocation); 

					short sil[] = AnalyzeABIFdata.getMaxValArray(channel1Data, channel2Data, channel3Data, channel4Data);
					short[] valley = AnalyzeABIFdata.valleyFinder(peakLocation, sil);
					float[] doList = AnalyzeABIFdata.doAlgorithm(peakLocation, valley, sil);
					TreeMap<Integer, Integer> predictor = AnalyzeABIFdata.predictiveAlgorithm(qualScore, qualityThreshold.getValue(), qualityThreshold2.getValue(), coList, clearanceSlider.getValue(), doList, differentialSlider.getValue(), windowSlider.getValue()); 


					if(fileExt.getSelectedItem() == "FASTA"){
						output += AnalyzeABIFdata.processRegionOutputFasta(predictor, qualScoreNuc, filename, false);
					}
					else if(fileExt.getSelectedItem() == "FASTQ"){
						output += AnalyzeABIFdata.processRegionOutputFastq(predictor, qualScoreNuc, qualScore, filename, false);
					}
				}//end for loop

				int n = JOptionPane.showOptionDialog(parent, "Select Save Destination", "Important message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Choose Path", "Cancel"},
						"default");

				if (n == JOptionPane.OK_OPTION) {

					JFileChooser exportFile = new JFileChooser();
					exportFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
					exportFile.setPreferredSize(fileChooserDim);
					int returnVal2 = exportFile.showSaveDialog(null);


					// if user presses save continue
					if (returnVal == JFileChooser.APPROVE_OPTION) {


						inputFsaFileLocation = exportFile.getSelectedFile().getAbsolutePath();


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
					}// inner ok option for save path

				}//end ok option 
			}
		}
	}
}