package vk.view.windows;

import javax.swing.JDialog;

import vk.simulation.Simulator;
import vk.view.graph.PieGraph;

public class PieGraphFrame extends JDialog {

	/**
	 * ranomd
	 */
	private static final long serialVersionUID = -2474402795657944620L;

	public PieGraphFrame() {
		createRootPane();
		dialogInit();
		this.add(PieGraph(simulator));
	}
}
