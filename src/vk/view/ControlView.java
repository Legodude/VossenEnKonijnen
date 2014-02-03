package vk.view;

import javax.swing.*;

import vk.view.button.*;

public class ControlView extends JPanel{
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 9213132259620958842L;

	/**
	 * A constructor that adds the required buttons
	 */
    public ControlView()
    {
        this.add(new OneStep());
        this.add(new StopStart());
        this.add(new Nuke());
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }
}
