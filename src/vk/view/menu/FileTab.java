package vk.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import vk.view.menu.item.*;
import vk.view.windows.*;

public class FileTab extends JMenu implements ActionListener {

	/**
	 * random nummertje
	 */
	private static final long serialVersionUID = 7865087811043667737L;

	public FileTab()
	{
		super("<HTML><U>F</U>ile</HTML>");
		SettingsItem settingsItem = new SettingsItem();
		this.add(settingsItem);
		settingsItem.setActionCommand("settingsItem");
        settingsItem.addActionListener(this);
        settingsItem.setMnemonic(KeyEvent.VK_S);
        
        HelpItem helpItem = new HelpItem();
		this.add(helpItem);
		helpItem.setActionCommand("helpItem");
        helpItem.addActionListener(this);
        helpItem.setMnemonic(KeyEvent.VK_H);
        
	}
	
	public void actionPerformed(ActionEvent event) {
		System.out.println(event.getActionCommand());
		if(event.getActionCommand().equals("settingsItem"))
		{
			SettingsFrame settingsFrame = new SettingsFrame();
			settingsFrame.setVisible(true);
		}
		if(event.getActionCommand().equals("helpItem"))
		{
			HelpFrame helpFrame = new HelpFrame();
			helpFrame.setVisible(true);
		}
		
	}
	
}
