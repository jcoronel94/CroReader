package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;


//Listener for export button. Sends active chromatogram as a PDF to the local machince. 
//Path selected by user. 

public class exportFileActionListener implements ActionListener {

	// variables for parameter passing
	public static String inputFsaFileLocation = null;
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
	private TreeMap<Integer, Integer> predict;
	private int verticalSegmentNumber;
	Dimension fileChooserDim = new Dimension(700, 600);
	// private String buttonType = null;

	public exportFileActionListener(CentralParameterStorage centralPara, CentralPanelStorage centralPanel,
			ArrayList<CentralPanelStorage> centralList) {
		this.centralPara = centralPara;
		this.centralPanel = centralPanel;
		this.centralList = centralList;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (centralPara.getNumOpen() != 0) {

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
			reversed = centralPanel.getReversed();
			basePositionOn = centralPanel.getBasePositionOn();
			baseCallOn = centralPanel.getBaseCallOn();
			qualityScoreOn = centralPanel.getQualityScoreOn();

			// set up save box with appropriate pdf filters
			JFileChooser exportFile = new JFileChooser(System.getProperty("user.home"));
			exportFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("PDF", "pdf");
			exportFile.setAcceptAllFileFilterUsed(false);
			exportFile.addChoosableFileFilter(pdfFilter);
			exportFile.setPreferredSize(fileChooserDim);
			int returnVal = exportFile.showSaveDialog(null);

			// if user presses save continue
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				inputFsaFileLocation = exportFile.getSelectedFile().getAbsolutePath();

				// check to see if file has pdf extension, if not append on to file
				// name
				if (!inputFsaFileLocation.substring(inputFsaFileLocation.length() - 3, inputFsaFileLocation.length())
						.equals("pdf") || inputFsaFileLocation.length() < 3) {
					inputFsaFileLocation += ".pdf";
				}

				// try writing pdf, if successful close everything after
				Document document = new Document(new Rectangle(channel1Data.length + 200, verticalSegmentNumber + 200));

				try {
					PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(inputFsaFileLocation));
					document.open();

					PdfContentByte cb = writer.getDirectContent();
					Graphics2D g2 = cb.createGraphics(channel1Data.length + 200, verticalSegmentNumber + 200);
					DrawRawChannelDataPanel paintPanel = new DrawRawChannelDataPanel(channel1Data.length + 200,
							verticalSegmentNumber + 200, qualScore, qualScoreNuc, channel1Data,
							channel2Data, channel3Data, channel4Data, peakLocation, colors, verticalUnit, verticalSegmentNumber,
							100, 100, predict,reversed, basePositionOn, baseCallOn, qualityScoreOn,toggleRegionOn);
					paintPanel.paintComponent(g2);
					g2.dispose();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				document.close();

			}
		}

	}

}
