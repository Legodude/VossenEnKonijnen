package vk.view.textfields;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import vk.simulation.Simulator;

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
			int width = Integer.parseInt(splitted[0]);
			int depth = Integer.parseInt(splitted[1]);
			Simulator.setField(width, depth);
			Simulator.reset();
		}
	}
	

}
