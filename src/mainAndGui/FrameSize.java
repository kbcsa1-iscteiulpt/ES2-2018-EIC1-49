package mainAndGui;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class FrameSize {
	/**
	 * Defines the size of the given frame. The second parameter indicates the
	 * number that the screen is divided by.
	 **/
	public static void setFrame(JFrame frame, double size) {
		double frameWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double frameHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

		frame.setSize((int) (frameWidth * size), (int) (frameHeight * size));
		frame.setLocationRelativeTo(null);
		if (frame.getTitle().equals("Problem to be optimized")) {
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else if (frame.getTitle().equals("Criterias") || frame.getTitle().equals("Decision Variables")) {
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		} else {
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}
}
