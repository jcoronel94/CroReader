package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;



import javax.swing.colorchooser.AbstractColorChooserPanel;

/*
 * custom buttons for opening jcolorchoosers for each trace line in chromatogram
 */

public class NucButtons extends JButton {
	Color nucColor;
	Color[] colors;
	private int verticalUnit;
	private byte[] qualScore;
	private byte[] qualScoreNuc;
	private short[] channel1Data;
	private short[] channel2Data;
	private short[] channel3Data;
	private short[] channel4Data;
	private short[] peakLocation;
	private boolean reversed;
	private boolean basePositionOn;
	private boolean baseCallOn;
	private boolean qualityScoreOn;
	private boolean toggleRegionOn;
	JTabbedPane tabbed;

	private TreeMap<Integer, Integer> predict;
	private int verticalSegmentNumber;
	
	public NucButtons(MyNucPalette parent, CentralPanelStorage cps, char nuc) {
		super();
		setPreferredSize(new Dimension(32,50));
		//setFont(new Font("Arial", Font.PLAIN, 1));
		setMargin(new Insets(-5, 0, -5, 0));
		setFocusable(false);
		setFocusPainted(false);
		//setBorder(raisedBorder);
		colors = cps.getColors();
		if(nuc == 'G'){
			
			setText("G");
			nucColor = cps.getColors()[1];
		}

		else if(nuc == 'A'){
			
			setText("A");
			nucColor = cps.getColors()[2];

		}
		else if(nuc == 'T'){
			setText("T");
			nucColor = cps.getColors()[3];

		}
		else if(nuc == 'C'){
			setText("C");
			nucColor = cps.getColors()[4];

		}
		
		
		
		
		
		addActionListener(new ActionListener(){
			//addMouseListener(new MouseAdapter(){
			@Override
			public void actionPerformed(ActionEvent evt){
				JColorChooser chooser = new JColorChooser(nucColor);
				AbstractColorChooserPanel[] panels = chooser.getChooserPanels();
				for(AbstractColorChooserPanel panel: panels){
					System.out.println(panel.getDisplayName());
					if(!panel.getDisplayName().equals("RGB")){
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
				JColorChooser.createDialog(
						parent, 
						"Choose a Color", 
						true, 
						chooser, 
						(e)->{
							
							// get all data from central parameter before writing
							verticalSegmentNumber = cps.getVerticalSegmentNumber();
							verticalUnit = cps.getVerticalUnit();
							qualScore = cps.getQualScore();
							qualScoreNuc = cps.getQualScoreNuc();
							channel1Data = cps.getChannel1Data();
							channel2Data = cps.getChannel2Data();
							channel3Data = cps.getChannel3Data();
							channel4Data = cps.getChannel4Data();
							peakLocation = cps.getPeakLocation();
							colors = cps.getColors();
							predict = cps.getPredict();
							
							reversed = cps.getReversed();
							basePositionOn = cps.getBasePositionOn();
							baseCallOn = cps.getBaseCallOn();
							qualityScoreOn= cps.getQualityScoreOn();
							toggleRegionOn = cps.getToggleRegionOn();


							JScrollPane scrollPane = cps.getDrawPanel();
							// get current scroll values to maintain them after repaint
							int scrollVertical = scrollPane.getVerticalScrollBar().getValue();
							cps.setScrollVertical(scrollVertical);
							int scrollHorizontal = scrollPane.getHorizontalScrollBar().getValue();
							cps.setScrollHorizontal(scrollHorizontal);

							
							Color custom =  chooser.getColor();
							
							if(nuc == 'G'){
								colors[1] = custom;
							}

							else if(nuc == 'A'){
								colors[2] = custom;
							}
							else if(nuc == 'T'){
								colors[3] = custom;
							}
							else if(nuc == 'C'){
								colors[4] = custom;
							}
							
							
						    nucColor = custom;
							repaint();
						    revalidate();	
							cps.setColors(colors);
							
			


							DrawRawChannelDataPanel paintPanel = new DrawRawChannelDataPanel(channel1Data.length + 200, verticalSegmentNumber + 200,
									 qualScore, qualScoreNuc, channel1Data, channel2Data, channel3Data,
									channel4Data, peakLocation, colors, verticalUnit, verticalSegmentNumber, 100, 100, predict,reversed,basePositionOn,baseCallOn, qualityScoreOn,toggleRegionOn);

							paintPanel.setPreferredSize(new Dimension(channel1Data.length + 200, verticalSegmentNumber + 200));

							scrollPane.remove(cps.getPaintPanel());
							scrollPane.setSize(600, 400);
							scrollPane.getViewport().add(paintPanel);
							// maintain current position in scroll pane
							scrollPane.getVerticalScrollBar().setValue(scrollVertical);
							scrollPane.getHorizontalScrollBar().setValue(scrollHorizontal);
							//centralPanel.setDrawPanel(scrollPane);
							scrollPane.revalidate();
							scrollPane.repaint();
							
						
						},
						(e)->{
							System.out.println("whatever");
						}
						).setVisible(true);
			}
		});
			
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d =  (Graphics2D) g;
		
		drawRawData(g2d);
		g.setColor(nucColor);
	    g.fillRect(getWidth()/5, 4*getHeight()/6, (2*getWidth())/3, (4*getHeight()/6)-22);
	    
	  }

	private void drawRawData(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}
		
}
