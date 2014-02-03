package vk.view.button;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import vk.simulation.Simulator;

import java.awt.event.*;

public class OneStep extends JButton implements ActionListener {
	/**
	 * Serial
	 */
	private static final long serialVersionUID = -7533985062848505640L;

	/**
	 * Constructor for a button that simulates one step
	 */
	public OneStep()
	{
		this.setText("<HTML><U>O</U>ne Step</HTML>");
        this.setActionCommand("oneStep");
        this.addActionListener(this);
        this.setMnemonic(KeyEvent.VK_O);
	}

	/**
	 * This method checks for a button press
	 */
	public void actionPerformed(ActionEvent event) {
		if(event.getActionCommand().equals("oneStep"))
    	{
    		Simulator.simulateOneStep();
    	}
	}
}
