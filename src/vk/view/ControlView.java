package vk.view;

import java.awt.event.*;
import javax.swing.*;
import vk.simulation.*;

public class ControlView extends JPanel implements ActionListener {
	
	/**
	 * random nummertje zodat hij maar uniek is
	 */
	private static final long serialVersionUID = 9213132259620958842L;
	
    private JButton oneStep, hundredStep;

    public ControlView()
    {
        oneStep = new JButton();
        oneStep.setText("One Step");
        oneStep.setActionCommand("oneStep");
        oneStep.addActionListener(this);
        hundredStep = new JButton();
        hundredStep.setText("Hundred Steps");
        hundredStep.setActionCommand("hundredStep");
        hundredStep.addActionListener(this);
        this.add(oneStep);
        this.add(hundredStep);
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
    }
}
