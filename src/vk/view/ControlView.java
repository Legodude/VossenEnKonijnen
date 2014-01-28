package vk.view;

import java.awt.event.*;
import javax.swing.*;
import vk.simulation.*;

public class ControlView extends JPanel implements ActionListener {
	
	/**
	 * random nummertje zodat hij maar uniek is
	 */
	private static final long serialVersionUID = 9213132259620958842L;
	
    private JButton oneStep, hundredStep, reset;

    public ControlView()
    {
    	// Simulate one step
        oneStep = new JButton();
        oneStep.setText("<HTML><U>O</U>ne Step</HTML>");
        oneStep.setActionCommand("oneStep");
        oneStep.addActionListener(this);
        oneStep.setMnemonic(KeyEvent.VK_O);
        // Reset the simulation
        reset = new JButton();
        reset.setText("<HTML><U>R</U>eset</HTML>");
        reset.setActionCommand("reset");
        reset.addActionListener(this);
        reset.setMnemonic(KeyEvent.VK_R);
        // Simulate a hundred steps
        hundredStep = new JButton();
        hundredStep.setText("<HTML>H<U>u</U>ndred Steps</HTML>");
        hundredStep.setActionCommand("hundredStep");
        hundredStep.setMnemonic(KeyEvent.VK_U);
        hundredStep.addActionListener(this);
        this.add(oneStep);
        this.add(hundredStep);
        this.add(reset);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }
    
	public void actionPerformed(ActionEvent event)
    {
    	if(event.getActionCommand().equals("oneStep"))
    	{
    		Simulator.simulate(1);
    	}
    	if(event.getActionCommand().equals("hundredStep"))
    	{
    		Simulator.simulate(100);
    	}
    	if(event.getActionCommand().equals("reset"))
    	{
    		Simulator.reset();
    	}
    }
}
