package vk.view.windows;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;

import vk.view.textfields.FieldSize;

/**
 * The settings frame
 * @author Mark
 *
 */
public class SettingsFrame extends JDialog {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 267819929729372474L;

	/**
	 * Construct a new settings pane
	 */
	public SettingsFrame() {
		setLocation(100, 50); 
		setTitle("Simulation settings");
		JLabel intro = new JLabel("This is the Simulation settings panel, you can enter things like the depth and with of the field", JLabel.CENTER);
		this.add(intro);
		this.add(new FieldSize());
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		pack();
	}

}