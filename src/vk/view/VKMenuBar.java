package vk.view;

import javax.swing.*;

import vk.view.menu.*;


public class VKMenuBar extends JMenuBar {

	private static final long serialVersionUID = 851431801870525729L;

	public VKMenuBar()
	{		
		this.add(new FileTab());
	}
	
	
}
