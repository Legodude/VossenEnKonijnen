/**
 * 
 */
package vk.view.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import vk.simulation.Simulator;

public class Nuke extends JButton implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9143799018708022964L;

	public Nuke()
	{
	    this.setText("<HTML><U>N</U>uke</HTML>");
	    this.setActionCommand("nuke");
	    this.addActionListener(this);
	    this.setMnemonic(KeyEvent.VK_N);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getActionCommand().equals("nuke"))
    	{
    		Simulator.stop();
    		Simulator.reset();
    	}
	}
}
