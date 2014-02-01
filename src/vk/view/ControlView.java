package vk.view;

import java.awt.event.*;
import java.awt.Image.*;
import javax.swing.*;
import vk.simulation.*;

public class ControlView extends JPanel implements ActionListener {
	
	/**
	 * random nummertje zodat hij maar uniek is
	 */
	private static final long serialVersionUID = 9213132259620958842L;
	
    private JButton oneStep, run, nuke,stop;

    public ControlView()
    {
    	// Simulate one step
        oneStep = new JButton();
        oneStep.setText("<HTML><U>O</U>ne Step</HTML>");
        oneStep.setActionCommand("oneStep");
        oneStep.addActionListener(this);
        oneStep.setMnemonic(KeyEvent.VK_O);
        
        // nuke the simulation
        nuke = new JButton();
        nuke.setText("<HTML><U>N</U>uke</HTML>");
        nuke.setActionCommand("nuke");
        nuke.addActionListener(this);
        nuke.setMnemonic(KeyEvent.VK_N);
        
        // Simulate
        run = new JButton();
        run.setText("<HTML>R<U>u</U>n</HTML>");
        run.setActionCommand("run");
        run.setMnemonic(KeyEvent.VK_U);
        run.addActionListener(this);
        
        // Stop
        stop = new JButton();
        stop.setText("<HTML><U>S</U>top</HTML>");
        stop.setActionCommand("run");
        stop.setMnemonic(KeyEvent.VK_S);
        stop.addActionListener(this);
        this.add(oneStep);
        this.add(run);
        this.add(stop);
        this.add(nuke);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }
    
	public void actionPerformed(ActionEvent event)
    {
    	if(event.getActionCommand().equals("oneStep"))
    	{
    		Simulator.stop();
    		Simulator.simulate(1);
    		Simulator.stop();
    	}
    	if(event.getActionCommand().equals("run"))
    	{
    		Simulator.start();
    	}
    	if(event.getActionCommand().equals("stop"))
    	{
    		Simulator.stop();
    	}
    	if(event.getActionCommand().equals("nuke"))
    	{
    		Simulator.stop();
    		Simulator.reset();
    	}
    }
}
