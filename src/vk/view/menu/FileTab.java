package vk.view.menu;

import javax.swing.JMenu;
import vk.view.menu.item.*;

public class FileTab extends JMenu {

	/**
	 * random nummertje
	 */
	private static final long serialVersionUID = 7865087811043667737L;

	public FileTab()
	{
		super("<HTML><U>F</U>ile</HTML>");
		this.add(new SettingsItem());
	}
}
