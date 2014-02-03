/**
 * 
 */
package vk.view.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import vk.simulation.Simulator;

/**
 * Button to nuke the field
 * @author Mark
 *
 */
public class Nuke extends JButton implements ActionListener {
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 9143799018708022964L;

	
	/**
	 * Constructor for the nuke button, which resets the simulation
	 */
	public Nuke()
	{
	    this.setText("<HTML><U>N</U>uke & Populate</HTML>");
	    this.setActionCommand("nuke");
	    this.addActionListener(this);
	    this.setMnemonic(KeyEvent.VK_N);
	    this.setIcon(new javax.swing.ImageIcon(getClass().getResource("boom.png")));
	}

	/**
	 * This method checks for a button press
	 */
	public void actionPerformed(ActionEvent event) {
		if(event.getActionCommand().equals("nuke"))
    	{
			Simulator.stop();
    		Simulator.reset();
    	}
	}
}
