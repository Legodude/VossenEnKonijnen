package vk.view;

import javax.swing.*;

import vk.simulation.Simulator;
import vk.view.button.*;

public class ControlView extends AbstractView{
	
	/**
	 * random nummertje zodat hij maar uniek is
	 */
	private static final long serialVersionUID = 9213132259620958842L;

    public ControlView()
    {
        this.add(new OneStep());
        this.add(new Run());
        this.add(new Stop());
        this.add(new Nuke());
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }
}
