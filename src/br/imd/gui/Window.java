package br.imd.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author Sara
 */
public class Window extends JFrame {

	private String id;
	private JButton buttonUserId;
	private JTextField textUserId;

	public Window() {
		buttonUserId = new JButton();
		textUserId = new JTextField();

		textUserId.setText("Type in the user id: ");
		textUserId.getText();
		textUserId.setBounds(500, 600, 120, 15);
		buttonUserId.setBounds(500, 700, 120, 15);
		getContentPane().add(textUserId);
		getContentPane().add(buttonUserId);

		initComponents();
	}

	private void initComponents() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		UserVisualization frame = new UserVisualization();
		frame.setVisible(true);
		getContentPane().add(frame);
	}

	public void pause() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Window w = new Window();
		w.setVisible(true);
	}

}
