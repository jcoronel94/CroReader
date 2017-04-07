package core;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.text.PlainDocument;


/*Frame for application along with all components except those spanwed on open.
 * 
 */


public class ABIFframe extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private int windowWidth = 800;
	private int windowHeight = 600;
	Dimension commonButtonDim = new Dimension(50, 50);
	static CentralParameterStorage centralPara = null;

	public ABIFframe(CentralParameterStorage centralPara){
		this.centralPara = centralPara;
		commonPrep();

	}

	private void commonPrep(){
		// set up storage
		CentralParameterStorage centralPara = new CentralParameterStorage();
		CentralPanelStorage centralPanel = new CentralPanelStorage();
		ArrayList<CentralPanelStorage> centralList = new ArrayList<CentralPanelStorage>();
		centralPara.setStart(true);


		centralPara.setWindowHeight(527);
		//jframe behavior
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(windowWidth, windowHeight);
		setMinimumSize(new Dimension(windowWidth, windowHeight));
		setLocationRelativeTo(null);
		setTitle("CroReader: Chromotogram Viewer");
		JPanel mainPanel = new JPanel();
		add(mainPanel);
		
		
		

		//add listener for updating based on resize
		ResizeComponentListener resizeListen = new ResizeComponentListener(centralPara, centralPanel, centralList);
		mainPanel.addComponentListener(resizeListen);
		centralPara.setMainPanel(mainPanel);

		//border for Jcomponents
		Border loweredBorder = BorderFactory.createLoweredBevelBorder();
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border blackline = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black);
				
		//gaps for organization
		JLabel horizontalGap = new JLabel();
		horizontalGap.setPreferredSize(new Dimension(windowWidth,0));

		//layout manager
		mainPanel.setLayout(new BorderLayout());

		//menu bar for holding individual commands 
		JMenuBar menuBar = new JMenuBar();

		
		
		//Icons for menu bar and buttons
		//openicon
		Class c = this.getClass();
		String urlString = "/toolbarButtonGraphics/general/Open24.gif";
		URL url = c.getResource( urlString );
		ImageIcon openIcon = new ImageIcon( url );
		//exporticon
		urlString = "/toolbarButtonGraphics/general/Export24.gif";
		url = c.getResource( urlString );
		ImageIcon exportIcon = new ImageIcon( url );
		//closeicon
		urlString = "/toolbarButtonGraphics/general/Delete24.gif";
		url = c.getResource( urlString );
		ImageIcon closeIcon = new ImageIcon( url );
	


		//file menu
		JMenu fileMenu = new JMenu("File"); 
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);
		
		


		//open file menu item - file
		JMenuItem openMenuItem = new JMenuItem("Open new file", KeyEvent.VK_N);
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		openFileActionListener openFileListener = new openFileActionListener(mainPanel, centralPara, centralList);
		openMenuItem.addActionListener(openFileListener);
		openMenuItem.setIcon(openIcon);
		fileMenu.add(openMenuItem);

		//close menu item - file
		JMenuItem closeMenuItem = new JMenuItem("Close", KeyEvent.VK_W);
		closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		closeMenuItem.setEnabled(false);
		closeFileActionListener closeFileListener = new closeFileActionListener(centralPara, centralPanel, centralList);
		closeMenuItem.addActionListener(closeFileListener);
		closeMenuItem.setIcon(closeIcon);
		fileMenu.add(closeMenuItem);
		centralPara.setCloseMenuItem(closeMenuItem);

		fileMenu.addSeparator();

		//export menu item - file
		JMenuItem exportMenuItem = new JMenuItem("Export PDF");
		exportMenuItem.setEnabled(false);
		exportMenuItem.setIcon(exportIcon);
		exportFileActionListener exportFileListener = new exportFileActionListener(centralPara, centralPanel, centralList);
		exportMenuItem.addActionListener(exportFileListener);
		fileMenu.add(exportMenuItem);
		centralPara.setExportMenuItem(exportMenuItem);


		//print menu item - file
		JMenuItem printMenuItem = new JMenuItem("Print");
		printMenuItem.setEnabled(false);
		fileMenu.add(printMenuItem);
		centralPara.setPrintMenuItem(printMenuItem);

		fileMenu.addSeparator();

		//exit item
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{System.exit(0); }});
		fileMenu.add(exitItem);

		//view menu 
		JMenu viewMenu = new JMenu("View"); 
		
		menuBar.add(viewMenu);


		//base postion call
		JCheckBoxMenuItem basePositionItem = new JCheckBoxMenuItem("Base Position Numbers", true);
		basePositionItem.setEnabled(false);
		BasePositionActionListener basePositionListener = new BasePositionActionListener(centralPara, centralPanel, centralList);
		basePositionItem.addActionListener(basePositionListener);
		viewMenu.add(basePositionItem);
		centralPara.setBasePositionItem(basePositionItem);

		//base  call
		JCheckBoxMenuItem baseCallItem = new JCheckBoxMenuItem("Base Call", true);
		baseCallItem.setEnabled(false);
		BaseCallActionListener baseCallListener = new BaseCallActionListener(centralPara, centralPanel, centralList);
		baseCallItem.addActionListener(baseCallListener);
		viewMenu.add(baseCallItem);
		centralPara.setBaseCallItem(baseCallItem);


		//quality scores
		JCheckBoxMenuItem qualityScoreItem = new JCheckBoxMenuItem("Quality Scores", true);
		qualityScoreItem.setEnabled(false);
		QualityScoreActionListener qualityScoreListener = new QualityScoreActionListener(centralPara, centralPanel, centralList);
		qualityScoreItem.addActionListener(qualityScoreListener);
		viewMenu.add(qualityScoreItem);
		centralPara.setQualityScoreItem(qualityScoreItem);

		viewMenu.addSeparator();

		//Chromatogram info - view
		JMenuItem chromInfoItem = new JMenuItem("Chromatogram Info");
		chromInfoItem.setEnabled(false);
		ChromatogramInfoListener chromInfoListener = new ChromatogramInfoListener(centralPara, centralPanel, centralList);
		chromInfoItem.addActionListener(chromInfoListener);
		viewMenu.add(chromInfoItem);
		centralPara.setChromInfoItem(chromInfoItem);

		viewMenu.addSeparator();

		//raw info viewer - view
		JMenuItem rawInfoItem = new JMenuItem("Raw Data");
		rawInfoItem.setEnabled(false);
		RawDataInfoListener rawListener = new RawDataInfoListener(centralPara, centralPanel, centralList);
		rawInfoItem.addActionListener(rawListener);
		viewMenu.add(rawInfoItem);
		centralPara.setRawInfoItem(rawInfoItem);

		//JMenuItem reverseItem = new JMenuItem("Reverse Complement");
		JCheckBoxMenuItem reverseItem = new JCheckBoxMenuItem("ReverseComplement");
		reverseItem.setEnabled(false);
		ReverseActionListener reverseListener = new ReverseActionListener(centralPara,centralPanel,centralList);
		reverseItem.addActionListener(reverseListener);
		viewMenu.add(reverseItem);
		centralPara.setReverseItem(reverseItem);

		
		viewMenu.addSeparator();
		
		//threshold overview
		JCheckBoxMenuItem toggleRegion = new JCheckBoxMenuItem("Toggle Bad Region Window");
		toggleRegion.setEnabled(false);
		RegionActionListener regionListener = new RegionActionListener(centralPara,centralPanel,centralList);
		toggleRegion.addActionListener(regionListener);
		viewMenu.add(toggleRegion);
		centralPara.setToggleRegion(toggleRegion);
		
		//threshold options
		JMenuItem thresholdItem = new JMenuItem("Threshold Options");
		thresholdItem.setEnabled(false);
		ThresholdActionListener thresholdListener = new ThresholdActionListener(centralPara,centralPanel,centralList);
		thresholdItem.addActionListener(thresholdListener);
		viewMenu.add(thresholdItem);

		centralPara.setThresholdItem(thresholdItem);
		
		
		viewMenu.addSeparator();
		JCheckBoxMenuItem fullScreenItem = new JCheckBoxMenuItem("Full Screen");
		fullScreenItem.setEnabled(false);
		FullScreenListener fullScreenListener = new FullScreenListener(centralPara,centralPanel,centralList);
		fullScreenItem.addActionListener(fullScreenListener);

		viewMenu.add(fullScreenItem);
		
		//process menu 
		JMenu processMenu = new JMenu("Process"); 
		menuBar.add(processMenu);
		
		//current file processed output 
		JMenuItem processCurrentItem = new JMenuItem("Output current file regions");
		processCurrentItem.setEnabled(false);
		ProcessActionListener processListener = new ProcessActionListener(centralPara, centralPanel, centralList);
		processCurrentItem.addActionListener(processListener);
		processMenu.add(processCurrentItem);
		centralPara.setProcessCurrentItem(processCurrentItem);
		

		
		
		//batch processed output 
		JMenuItem batchProcessItem = new JMenuItem("Batch file regions");
		batchProcessItem.setEnabled(true);
		BatchProcessActionListener batchProcessListener = new BatchProcessActionListener(centralPara, centralPanel, centralList);
		batchProcessItem.addActionListener(batchProcessListener);
		processMenu.add(batchProcessItem);
		
		setJMenuBar(menuBar);
	
		
		
		//buttons for open/close/save-------------------------------
		
		JButton openButton = new JButton();
		openButton.setPreferredSize(commonButtonDim);
		//openButton.setText("Open");
		//openButton.setBorder(BorderFactory.createEmptyBorder());
		//openButton.setContentAreaFilled(false);
		openButton.setFocusPainted(false);
		openButton.setToolTipText("Open new file");
		openFileActionListener openFileListener1 = new openFileActionListener(mainPanel, centralPara, centralList);
		openButton.addActionListener(openFileListener1);
		openButton.setIcon(openIcon);
    


		//close button
		JButton closeButton = new JButton();
		closeButton.setPreferredSize(commonButtonDim);
		//closeButton.setText("Close");
		closeButton.setToolTipText("Close current file");
		closeFileActionListener closeFileListener1 = new closeFileActionListener(centralPara, centralPanel, centralList);
		closeButton.addActionListener(closeFileListener1);
		//closeButton.setBorder(BorderFactory.createEmptyBorder());
		//closeButton.setContentAreaFilled(false);
		closeButton.setFocusPainted(false);
		closeButton.setIcon(closeIcon);

		//export button 
		JButton exportButton = new JButton();
		exportButton.setPreferredSize(commonButtonDim);
		//exportButton.setText("Export");
		exportButton.setToolTipText("Export current image as a PDF");
		exportFileActionListener exportFileListener1 = new exportFileActionListener(centralPara, centralPanel, centralList);
		exportButton.addActionListener(exportFileListener1);
		//exportButton.setBorder(BorderFactory.createEmptyBorder());
		//exportButton.setContentAreaFilled(false);
		exportButton.setFocusPainted(false);
		exportButton.setIcon(exportIcon);

		//set up position finder 
		JPanel inputGoTo = new JPanel();
		JLabel input = new JLabel();
		input.setText("Go to Base No: ");
		
		JTextField goTo = new JTextField();
		goTo.setBorder(loweredBorder);
		//need before the actionlistener is used 
		PlainDocument doc = (PlainDocument) goTo.getDocument();
		doc.setDocumentFilter(new MyIntFilter());
		GoToPositionActionListener goToListener = new GoToPositionActionListener(centralPara,centralPanel,centralList, goTo);
		goTo.setPreferredSize(new Dimension(45,30));
		goTo.addActionListener(goToListener);
		ContextMenuMouseListener rightClick = new ContextMenuMouseListener();
		goTo.addMouseListener(rightClick);
		goTo.setEnabled(false);
		centralPara.setGoTo(goTo);
		//add label and textfield to container
		inputGoTo.add(input);
		inputGoTo.add(goTo);
		
		//go button 
		JButton goButton = new JButton("Go"); 
		goButton.setPreferredSize(new Dimension(48,30));
		goButton.addActionListener(goToListener);
		goButton.setEnabled(false);
		goButton.setToolTipText("Search by base");
		goButton.setFocusPainted(false);
//		goButton.setBorder(raisedbevel);
//		goButton.setBorderPainted(true);
//		goButton.setBackground(Color.BLUE);
		centralPara.setGoButton(goButton);


		//set up sequence finder
		JPanel seqPanel = new JPanel();
		JLabel seqInput = new JLabel();
		seqInput.setText("Find Sequence: ");
		JTextField seqFinder= new JTextField();
		seqFinder.setBorder(loweredBorder);
		//need before the actionlistener is used to avoid invalid input
		PlainDocument seqDoc = (PlainDocument) seqFinder.getDocument();
		seqDoc.setDocumentFilter(new MyStringFilter());
		SeqFinderActionListener seqFinderListener = new SeqFinderActionListener(centralPara,centralPanel,centralList, seqFinder);
		seqFinder.setPreferredSize(new Dimension(70,30));
		seqFinder.addActionListener(seqFinderListener);
		ContextMenuMouseListener rightClick1 = new ContextMenuMouseListener();
		seqFinder.addMouseListener(rightClick1);
		seqFinder.setEnabled(false);
		centralPara.setSeqFinder(seqFinder);
		seqPanel.add(seqInput);
		seqPanel.add(seqFinder);
		
		
		//seq finder button
		JButton findButton = new JButton("Find"); 
		findButton.setPreferredSize(new Dimension(60,30));
		findButton.addActionListener(seqFinderListener);
		findButton.setEnabled(false);
		findButton.setToolTipText("Search by sequence");
		findButton.setFocusPainted(false);
		centralPara.setFindButton(findButton);


		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		north.setBorder(blackline);
		//flow layout for buttons
		north.add(openButton);
		north.add(closeButton);
		north.add(exportButton);
		north.add(inputGoTo);
		north.add(goButton);
		north.add(seqPanel);
		north.add(findButton);
		JComponent rBox = (JComponent) Box.createRigidArea(new Dimension(10,0));
		north.add(Box.createRigidArea(new Dimension(10,0)));
		centralPara.setrBox(rBox);
		//color palette for nucs
		MyNucPalette mc = new MyNucPalette(mainPanel);
		
		
		//mc.add(b, gbc);
		north.add(mc);
		centralPara.setMNP(mc);
		//mainPanel.repaint();
		//mainPanel.revalidate();
	
	

		mainPanel.add(north, BorderLayout.NORTH);


		//panel for buffer and slider 
		JPanel west = new JPanel();
		west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
		
		//set slider and place in panel to be replaced
		JPanel sliderHolder = new JPanel();
	
		west.add(Box.createGlue());
		west.add(sliderHolder);
		
		
		//sliderHolder.add(rangeSlider,BorderLayout.WEST);
		centralPara.setSliderHolder(sliderHolder);

		


		//what the viewport of the scroll pane will see
		JPanel outputWindow = new JPanel();
		outputWindow.setBackground(new Color(255,255,255));
		
		//always have a scrollpane even if it is empty
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension( 600,400));
	
		//tabbedpane for holding chromatograms 
		JTabbedPane tabbedPane = new JTabbedPane();	
		tabbedPane.add(scrollPane,"stuff");
		TabChangeListener tabListener = new TabChangeListener(centralPara, centralPanel, centralList);
		tabbedPane.addChangeListener(tabListener);
		tabbedPane.setVisible(false);
		centralPara.setTabbedPane(tabbedPane);
		centralPara.setDrawPanel(scrollPane);


		mainPanel.add(west, BorderLayout.WEST);
		mainPanel.add(tabbedPane, BorderLayout.CENTER);
		mainPanel.add(new JPanel(), BorderLayout.EAST);


		//set south panel 
		mainPanel.add(new JPanel(), BorderLayout.SOUTH);
		

	}

	private void setImageIcon(ImageIcon frameIcon) {
		// TODO Auto-generated method stub
		
	}
        


}
