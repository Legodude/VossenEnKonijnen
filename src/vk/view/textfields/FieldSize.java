package vk.view.textfields;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import vk.simulation.Simulator;

/**
 * Textfield to change the field's size
 * @author Mark
 *
 */
public class FieldSize extends JTextField implements ActionListener {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 691328799454515243L;

	/**
	 * Constructor for a text field that can change the field's dimensions
	 */
	public FieldSize()
	{
		this.setText("Width Depth");
		this.addActionListener(this);
		this.setActionCommand("newsize");
	}
	
	/**
	 * This method checks for a button press
	 */
	public void actionPerformed(ActionEvent event) {
		if(event.getActionCommand().equals("newsize")&& !this.getText().equals("Width Depth")) {
			String text = this.getText();
			String[] splitted = text.split("\\s+");
			int width = 0;
			int depth = 0;
			try { width = Integer.parseInt(splitted[0]);
				  depth = Integer.parseInt(splitted[1]);
			}
			catch (NumberFormatException e) {
				System.out.println("Please enter two valid numbers (example: 50 50)");
			}
			catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Please enter two valid numbers (example: 50 50)");
			}
			if(width>0 && depth>0) {
				Simulator.setField(width, depth);
				Simulator.stop();
				Simulator.reset();
				Simulator.reset();
			}
		}
	}
	

}
