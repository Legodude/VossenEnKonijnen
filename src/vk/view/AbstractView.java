package vk.view;

import javax.swing.*;

import vk.simulation.Simulator;

public abstract class AbstractView extends JPanel {
	private static final long serialVersionUID = -2767764579227738552L;
	protected Simulator simulator;

	public AbstractView(Simulator simulator) {
		this.simulator=simulator;
		simulator.addView(this);
	}
	
	public Simulator getsimulator() {
		return simulator;
	}
	
	public void updateView() {
		repaint();
	}
}