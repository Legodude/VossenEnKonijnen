package vk.view.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import vk.simulation.Simulator;

public class Run extends JButton implements ActionListener {

	/**
	 * raaaaaandoooooom
	 */
	private static final long serialVersionUID = -143685781586456876L;

	public Run() {
		this.setText("<HTML>R<U>u</U>n</HTML>");
        this.setActionCommand("run");
        this.setMnemonic(KeyEvent.VK_U);
        this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getActionCommand().equals("run"))
    	{
			Simulator.start();
    	}
		
	}

}
