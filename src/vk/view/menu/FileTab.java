package vk.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;

import vk.view.menu.item.*;
import vk.view.windows.HelpFrame;

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
        
        HelpItem helpItem = new HelpItem();
		this.add(helpItem);
		helpItem.setActionCommand("helpItem");
        helpItem.addActionListener(this);
        
	}
	
	public void actionPerformed(ActionEvent event) {
		System.out.println(event.getActionCommand());
		if(event.getActionCommand().equals("settingsItem"))
		{
			
		}
		if(event.getActionCommand().equals("helpItem"))
		{
			HelpFrame helpFrame = new HelpFrame();
			helpFrame.show();
		}
		
	}
	
}
