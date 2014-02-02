package vk.view.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import vk.simulation.Simulator;

public class StopStart extends JButton implements ActionListener {

	/**
	 * serialversion
	 */
	private static final long serialVersionUID = -7366873901042910906L;

	public StopStart()
	{
		this.setText("<HTML><U>S</U>tart</HTML>");
        this.setActionCommand("start");
        this.setMnemonic(KeyEvent.VK_S);
        this.addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getActionCommand().equals("stop")) {
    		Simulator.stop();
    		this.setActionCommand("start");
    		this.setText("<HTML><U>S</U>tart</HTML>");
    	}
		else if(event.getActionCommand().equals("start")) {
			Simulator.start();
			this.setActionCommand("stop");
			this.setText("<HTML><U>S</U>top</HTML>");
		}
	}

}