package core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.TreeMap;
import java.awt.Font;
import javax.swing.JPanel;

public class DrawRawChannelDataPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final int emptyPredictor = -101;
	Color channel1Color = null;
	Color channel2Color = null;
	Color channel3Color = null;
	Color channel4Color = null;

	int iCount = 1;
	int picWidth = 0;
	int picHeight = 0;
	int xStart = 0;
	int yStart = 0;
	int verticalUnit = 0;
	int verticalSegmentNumber = 0;
	int horizontalUnit = 0;
	int horizontalSegmentNumber = 0;
	byte[] qualScore = null;
	byte[] qualScoreNuc = null;
	short[] channel1Data = null;
	short[] channel2Data = null;
	short[] channel3Data = null;
	short[] channel4Data = null;
	short[] peakLocation = null;
	Color[] colors = null;
	TreeMap<Integer, Integer> predict = null;
	boolean reverse = false;
	boolean basePositionOn = true;
	boolean baseCallOn = true;
	boolean qualityScoreOn = true;
	boolean toggleRegionOn = false;
	float[] baseLoc = null;


	
	public void drawRawData(Graphics2D g2d) {


		ContextMenuMouseListener rightClick = new ContextMenuMouseListener();
		addMouseListener(rightClick);



		int yOffset = yStart + verticalSegmentNumber;
		int xOffset = xStart + verticalSegmentNumber;

		Font currentFont = new Font("Arial", Font.PLAIN, 10);
		g2d.setFont(currentFont);
		g2d.setColor(Color.gray);
		g2d.drawString("Channel 5 (Size Standard) = Black Color", 10, 50);
		g2d.drawLine(xStart, yStart, xStart, yOffset);
		g2d.drawLine(channel2Data.length + yStart, yStart, channel1Data.length + yStart, yOffset);
		drawYaxis(g2d, yOffset, 10);
		drawXaxis(g2d, xOffset, 170);
		baseLocInit(g2d, peakLocation);


		
		
		//get colors 
		channel1Color = colors[1];
		Color colorG = colors[1];
		channel2Color = colors[2];
		Color colorA = colors[2];
		channel3Color = colors[3];
		Color colorT = colors[3];
		channel4Color = colors[4];
		Color colorC = colors[4];
		Color colorThresh = colors[5];
		
		
		if(reverse == false){
			drawIndividualChannel(g2d, channel1Data, channel1Color, yOffset);
			drawIndividualChannel(g2d, channel2Data, channel2Color, yOffset);
			drawIndividualChannel(g2d, channel3Data, channel3Color, yOffset);
			drawIndividualChannel(g2d, channel4Data, channel4Color, yOffset);

			drawPeakLocation(g2d, peakLocation, qualScoreNuc, qualScore, yOffset, colorG, colorA, colorT, colorC);
			drawBasePositionNums(g2d, peakLocation, qualScoreNuc, qualScore, yOffset);
			regions(g2d, predict ,peakLocation, colorThresh);


		}

		else{
			drawReverseIndividualChannel(g2d, channel1Data, channel4Color, yOffset);
			drawReverseIndividualChannel(g2d, channel2Data, channel3Color, yOffset);
			drawReverseIndividualChannel(g2d, channel3Data, channel2Color, yOffset);
			drawReverseIndividualChannel(g2d, channel4Data, channel1Color, yOffset);
			//drawPeakLocation(g2d, peakLocation, qualScoreNuc, qualScore, yOffset);
			drawReversePeakLocation(g2d, peakLocation, qualScoreNuc, qualScore, yOffset, colorG, colorA, colorT, colorC);
			drawReverseBasePositionNums(g2d, peakLocation, qualScoreNuc, qualScore, yOffset);
			reverseRegions(g2d, predict ,peakLocation,colorThresh);
		}

	}

	//relate base calls to pixal location for later mapping 
	public void baseLocInit(Graphics2D g2d, short[] peakLocation){
		baseLoc = new float[peakLocation.length];
		for (int i = 1; i < peakLocation.length; i++) { 
			baseLoc[i] = xStart +peakLocation[i-1];

		}

	}


	public void drawYaxis(Graphics2D g2d, int yOffset, int numberOfTicks) {
		int yCor = 0;
		int increment = verticalSegmentNumber / numberOfTicks;

		for (int i = 0; i < numberOfTicks; i++) {
			yCor = yOffset - i * increment;
			g2d.drawLine(xStart, yCor, channel1Data.length + yStart, yCor);
			g2d.drawString(String.valueOf(i * increment * verticalUnit), xStart - 50, yCor);
		}
	}

	public void drawXaxis(Graphics2D g2d, int xOffset, int numberOfTicks) {

		int xCor = 0;
		int increment = channel1Data.length / numberOfTicks;

		for (int i = 0; i < numberOfTicks; i++) {
			xCor = channel1Data.length - i * increment;
			g2d.drawString(String.valueOf(xCor), xCor + 100, xStart + verticalSegmentNumber + 25);

		}
	}

	// draw channel curve
	public void drawIndividualChannel(Graphics2D g2d, short[] dataList, Color color, int yOffset) {
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke((float) 1.5));
		short data = 0;
		int xCor = xStart;
		int yCor = 0;
		int preXcor = xStart;
		int preYcor = yOffset;


		for (int i = 0; i < dataList.length; i++) {
			data = dataList[i];

			xCor = xCor + 1;
			yCor = yOffset - data / verticalUnit;
			g2d.drawLine(preXcor, preYcor, xCor, yCor);
			preXcor = xCor;
			preYcor = yCor;
		}


	}

	public void drawReverseIndividualChannel(Graphics2D g2d, short[] dataList, Color color, int yOffset) {

		g2d.setColor(color);
		g2d.setStroke(new BasicStroke((float) 1.5));
		short data = 0;
		int xCor = xStart;
		int yCor = 0;
		int preXcor = xStart;
		int preYcor = yOffset;

		for (int i = dataList.length-1; i >=0; i--) {
			data = dataList[i];

			xCor = xCor + 1;
			yCor = yOffset - data / verticalUnit;
			g2d.drawLine(preXcor, preYcor, xCor, yCor);
			preXcor = xCor;
			preYcor = yCor;
		}

	}




	// draw black colored nucleotides based off machine base caller
	public void drawPeakLocation(Graphics2D g2d, short[] peakLocation, byte[] qualScoreNuc, byte[] qualScore,
			int yOffset, Color colorG, Color colorA, Color colorT, Color colorC) {

		g2d.setColor(Color.BLACK);

		for (int i = 0; i < peakLocation.length; i++) {

			if((char)qualScoreNuc[i] ==  'N'){
				g2d.setColor(Color.BLACK);
			}
			else if((char)qualScoreNuc[i] ==  'T'){
				g2d.setColor(colorT);
			}

			else if((char)qualScoreNuc[i] ==  'G'){
				g2d.setColor(colorG);
			}

			else if((char)qualScoreNuc[i] ==  'A'){
				g2d.setColor(colorA);
			}

			else if((char)qualScoreNuc[i] ==  'C'){
				g2d.setColor(colorC);
			}

			if(baseCallOn == true){
				g2d.drawString(String.valueOf((char) qualScoreNuc[i]), xStart + peakLocation[i],
						yOffset - verticalSegmentNumber - 15);
			}

			if(qualityScoreOn ==true){
				g2d.drawString(String.valueOf(qualScore[i]), xStart + peakLocation[i],
						yOffset - verticalSegmentNumber - 25);
			}

		}

	}

	public void drawReversePeakLocation(Graphics2D g2d, short[] peakLocation, byte[] qualScoreNuc, byte[] qualScore,
			int yOffset, Color colorG, Color colorA, Color colorT, Color colorC) {

		byte[] reversedNucs = new byte[qualScoreNuc.length];

		for(int i = 0; i<qualScoreNuc.length; i++){
			if(String.valueOf((char) qualScoreNuc[i]).equals("T") ){
				reversedNucs[i] = 'A';
			}
			else if(String.valueOf((char) qualScoreNuc[i]).equals("A")  ){
				reversedNucs[i] = 'T';

			}
			else if(String.valueOf((char) qualScoreNuc[i]).equals("G") ){
				reversedNucs[i] = 'C';

			}
			else if(String.valueOf((char) qualScoreNuc[i]).equals("C")  ){
				reversedNucs[i] = 'G';

			}

			else if(String.valueOf((char) qualScoreNuc[i]).equals("N") ){
				reversedNucs[i] = 'N';

			}
		}




		g2d.setColor(Color.BLACK);
		int max =0;
		for (int j = 0; j<peakLocation.length;j++){
			if(max<peakLocation[j]){
				max = peakLocation[j];	
			}
		}
 

		for (int i = reversedNucs.length-1; i >= 0; i--) {

			if((char)reversedNucs[i] ==  'N'){
				g2d.setColor(Color.BLACK);
			}
			else if((char)reversedNucs[i] ==  'T'){
				g2d.setColor(colorT);
			}

			else if((char)reversedNucs[i] ==  'G'){
				g2d.setColor(colorG);
			}

			else if((char)reversedNucs[i] ==  'A'){
				g2d.setColor(colorA);
			}

			else if((char)reversedNucs[i] ==  'C'){
				g2d.setColor(colorC);
			}


			if(baseCallOn ==true){
				g2d.drawString(String.valueOf((char) reversedNucs[i]), (-1*(peakLocation[i]))+max +xStart,
						yOffset - verticalSegmentNumber - 15);
			}
			if(qualityScoreOn == true){
				g2d.drawString(String.valueOf(qualScore[i]), (-1*(peakLocation[i]))+max + xStart,
						yOffset - verticalSegmentNumber - 25);
			}


		}



	}


	public void drawBasePositionNums(Graphics2D g2d, short[] peakLocation, byte[] qualScoreNuc, byte[] qualScore,
			int yOffset){

		g2d.setColor(Color.black);

		if(basePositionOn == true){
			for (int i = 1; i < peakLocation.length; i++) {

				if(i%10 == 0 && i != 0){
					

					g2d.drawString(String.valueOf(i), xStart + peakLocation[i-1],
							yOffset - verticalSegmentNumber - 5);
				}


			}

		}

	}
	
	public void drawReverseBasePositionNums(Graphics2D g2d, short[] peakLocation, byte[] qualScoreNuc, byte[] qualScore,
			int yOffset){

		g2d.setColor(Color.black);

		if(basePositionOn == true){
			int max =0;
			for (int j = 0; j<peakLocation.length;j++){
				if(max<peakLocation[j]){
					max = peakLocation[j];	
				}
			}
			for (int i = 1; i < peakLocation.length; i++) {

				if(i%10 == 0 && i != 0){
					g2d.drawString(String.valueOf(peakLocation.length-i), (-1*(peakLocation[i]))+max +xStart,
							yOffset - verticalSegmentNumber - 5);

				}


			}

		}

	}

	//spot bad regions
	public void regions(Graphics2D g2d, TreeMap<Integer, Integer> predict, short[] peakLocation, Color colorThresh){
		if(toggleRegionOn == true){
			//Color c= new Color(1f,0f,0f,.3f );
			Color c = colorThresh;

			g2d.setColor(c);
			int maxKey, maxValue =0;
			
			maxKey = AnalyzeABIFdata.MaxRegionFinder(predict);
			
			

			if(!predict.isEmpty() || maxKey != emptyPredictor ){
				maxValue = predict.get(maxKey);
				
				System.out.println(maxKey + " " + maxValue);
				
				//System.out.println(maxValue + " max");
				//g2d.fillRect(0, 0, 200 + predict.get(0), picHeight);
				//g2d.fillRect(point, 0, picWidth, picHeight);
				g2d.fillRect(0, 0, (int) baseLoc[maxKey], picHeight);
				g2d.fillRect((int) baseLoc[maxValue], 0, picWidth, picHeight);
			}
		}
	}
	
	//spot bad regions
		public void reverseRegions(Graphics2D g2d, TreeMap<Integer, Integer> predict2, short[] peakLocation,Color colorThresh){
			if(toggleRegionOn == true){
				//Color c=new Color(1f,0f,0f,.3f );
				Color c = colorThresh;
				g2d.setColor(c);
				
				int maxKey, maxValue =0;
				maxKey = AnalyzeABIFdata.MaxRegionFinder(predict);

				if(!predict.isEmpty() || maxKey != emptyPredictor ){
					maxValue = predict.get(maxKey);
					System.out.println(maxKey + " " + maxValue);

					g2d.fillRect(0, 0, picWidth- (int) baseLoc[maxValue], picHeight);
					g2d.fillRect(picWidth -(int) baseLoc[maxKey], 0, picWidth, picHeight);
				
				}
			}
		}




	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, picWidth, picHeight);

		Rectangle rect = null;
		g2d.setColor(Color.gray);
		rect = new Rectangle(0, 0, 10, 10);
		g2d.fill(rect);
		rect = new Rectangle(picWidth - 10, picHeight - 10, 10, 10);
		g2d.fill(rect);

		drawRawData(g2d);
	}

	public DrawRawChannelDataPanel(int width, int height,
			byte[] qualScore, byte[] qualScoreNuc, short[] channel1Data, short[] channel2Data, short[] channel3Data,
			short[] channel4Data, short[] peakLocation, Color[] colors, int verticalUnit, int verticalSegmentNumber, int xStart,
			int yStart, TreeMap<Integer, Integer> predict, boolean reverse, boolean basePositionOn, boolean baseCallOn, boolean qualityScoreOn, boolean toggleRegionOn) {
		super();
		this.picWidth = width;
		this.picHeight = height;
		this.xStart = xStart;
		this.yStart = yStart;
		this.verticalUnit = verticalUnit;
		this.verticalSegmentNumber = verticalSegmentNumber;
		this.qualityScoreOn = qualityScoreOn;
		this.reverse = reverse;
		this.baseCallOn = baseCallOn;
		this.basePositionOn = basePositionOn;
		this.toggleRegionOn = toggleRegionOn;
		this.qualScore = qualScore;
		this.qualScoreNuc = qualScoreNuc;
		this.channel1Data = channel1Data;
		this.channel2Data = channel2Data;
		this.channel3Data = channel3Data;
		this.channel4Data = channel4Data;
		this.peakLocation = peakLocation;
		this.colors = colors;
		this.predict = predict;
	}




}
