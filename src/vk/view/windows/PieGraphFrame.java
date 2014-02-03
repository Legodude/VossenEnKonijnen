package vk.view.windows;

import javax.swing.JDialog;

import vk.main.Main;
import vk.simulation.Simulator;
import vk.view.graph.PieGraph;

/**
 * The Pie Graph frame
 * @author Mark
 *
 */
public class PieGraphFrame extends JDialog {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -2474402795657944620L;

	public PieGraphFrame() {
		createRootPane();
		dialogInit();
		this.add(new PieGraph(Main.returnSim()));
	}
}
