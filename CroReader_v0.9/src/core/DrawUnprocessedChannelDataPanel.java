package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.awt.Font;
import javax.swing.JPanel;

public class DrawUnprocessedChannelDataPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	char[] nucOrder = null;

	short[] rawchannel1Data = null;
	short[] rawchannel2Data = null;
	short[] rawchannel3Data = null;
	short[] rawchannel4Data = null;
	Color[] colors = null;


	public void drawRawData(Graphics2D g2d) {
		int yOffset = yStart + verticalSegmentNumber;
		int xOffset = xStart + verticalSegmentNumber;

		Font currentFont = new Font("Arial", Font.PLAIN, 10);
		g2d.setFont(currentFont);
		g2d.setColor(Color.gray);
		g2d.drawString("Channel 5 (Size Standard) = Black Color", 10, 50);
		g2d.drawLine(xStart, yStart, xStart, yOffset);
		g2d.drawLine(rawchannel2Data.length + yStart, yStart, rawchannel1Data.length + yStart, yOffset);
		drawYaxis(g2d, yOffset, 10);
		drawXaxis(g2d, xOffset, 170);
		
		//get colors 
		
		channel1Color = colors[1];
		
		channel2Color = colors[2];
		
		channel3Color = colors[3];
		
		channel4Color = colors[4];
		

		drawIndividualChannel(g2d, rawchannel1Data, channel1Color, yOffset);
		drawIndividualChannel(g2d, rawchannel2Data, channel2Color, yOffset);
		drawIndividualChannel(g2d, rawchannel3Data, channel3Color, yOffset);
		drawIndividualChannel(g2d, rawchannel4Data, channel4Color, yOffset);
		

	}

	public void drawYaxis(Graphics2D g2d, int yOffset, int numberOfTicks) {
		int yCor = 0;
		int increment = verticalSegmentNumber / numberOfTicks;

		for (int i = 0; i < numberOfTicks; i++) {
			yCor = yOffset - i * increment;
			g2d.drawLine(xStart, yCor, rawchannel1Data.length + yStart, yCor);
			g2d.drawString(String.valueOf(i * increment * verticalUnit), xStart - 50, yCor);
		}
	}

	public void drawXaxis(Graphics2D g2d, int xOffset, int numberOfTicks) {

		int xCor = 0;
		int increment = rawchannel1Data.length / numberOfTicks;

		for (int i = 0; i < numberOfTicks; i++) {
			xCor = rawchannel1Data.length - i * increment;
			g2d.drawString(String.valueOf(xCor), xCor + 100, xStart + verticalSegmentNumber + 25);

		}
	}

	// draw channel curve
	public void drawIndividualChannel(Graphics2D g2d, short[] dataList, Color color, int yOffset) {
		g2d.setColor(color);
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

	public DrawUnprocessedChannelDataPanel(int width, int height,
		 short[] rawchannel1Data, short[] rawchannel2Data, short[] rawchannel3Data,
			short[] rawchannel4Data,  Color[] colors, int verticalUnit, int verticalSegmentNumber, int xStart,
			int yStart) {
		super();
		this.picWidth = width;
		this.picHeight = height;
		this.xStart = xStart;
		this.yStart = yStart;
		this.verticalUnit = verticalUnit;
		this.verticalSegmentNumber = verticalSegmentNumber;

		this.colors = colors;
		this.rawchannel1Data = rawchannel1Data;
		this.rawchannel2Data = rawchannel2Data;
		this.rawchannel3Data = rawchannel3Data;
		this.rawchannel4Data = rawchannel4Data;

		
	}

}

