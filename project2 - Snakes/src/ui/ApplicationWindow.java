package ui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * The main application window.
 * 
 */
public class ApplicationWindow {

	private JFrame frame;
	private GridPanel gridPanel;
	
	/**
	 * Constructs the ApplicationWindow.
	 * @param gridPanel grid panel to draw on.
	 */
	public ApplicationWindow(GridPanel gridPanel) {
		this.gridPanel = gridPanel;
	    initialize();
	}
	
	/**
	 * Initialize the JFrame with bounds x=50,y=50,width=500, height=520 parameters;
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(50, 50, 500, 520);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(gridPanel);
		frame.pack();
	}
	
	/**
	 * Returns the JFrame
	 * @return the created JFrame object
	 */
	public JFrame getFrame() {
	    return frame;
    }

}