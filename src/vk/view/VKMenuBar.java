package vk.view;

import java.awt.event.KeyEvent;

import javax.swing.*;

import vk.view.menu.*;


public class VKMenuBar extends JMenuBar {

	private static final long serialVersionUID = 851431801870525729L;

	public VKMenuBar()
	{		
		FileTab fileTab = new FileTab();
		fileTab.setMnemonic(KeyEvent.VK_F);
		this.add(fileTab);
	}
	
	
}
