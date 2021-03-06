package vk.view;

import java.awt.event.KeyEvent;

import javax.swing.*;

import vk.view.menu.*;

/**
 * The menu bar up top
 * @author Mark
 *
 */
public class VKMenuBar extends JMenuBar {

	private static final long serialVersionUID = 851431801870525729L;

	/**
	 * Constructor for the menu bar up top
	 */
	public VKMenuBar()
	{		
		FileTab fileTab = new FileTab();
		fileTab.setMnemonic(KeyEvent.VK_F);
		this.add(fileTab);
		GraphTab graphTab = new GraphTab();
		graphTab.setMnemonic(KeyEvent.VK_G);
		this.add(graphTab);
	}
	
	
}
