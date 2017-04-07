package core;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

class ChromDialog extends JDialog {

	public ChromDialog(JFrame frame, String title, boolean modal, JPanel panel) {
		super(frame, title, modal);

		//setLayout(new BorderLayout());
		//		add(new JButton("NORTH"), BorderLayout.NORTH);
		//		add(new JButton("SOUTH"), BorderLayout.SOUTH);
		//		add(new JButton("EAST"), BorderLayout.EAST);
		//		add(new JButton("WEST"), BorderLayout.WEST);
		//		add(new JButton("CENTER"), BorderLayout.CENTER);
		add(panel);
		pack();
		
		setLocationRelativeTo(null);

	}
}
