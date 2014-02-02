package vk.view;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;

import vk.simulation.Simulator;

public abstract class AbstractView extends JPanel {

	protected Simulator simulator;
	public Graphics g;
	public int xScale, yScale;

	protected int gridWidth, gridHeight;
    protected final int GRID_VIEW_SCALING_FACTOR = 6;
    
    protected Dimension size;
    protected Image fieldImage;
    

	private static final long serialVersionUID = 1L;

	public AbstractView(Simulator newSimulator) {
		simulator = newSimulator;
		simulator.addView(this);

		size = new Dimension();
		gridWidth = 125;
		gridHeight = 125;
	}

	public Simulator getModel() {
		return simulator;
	}

	public void updateView(){
		repaint();
	}

	public void preparePaint() {
	}
}