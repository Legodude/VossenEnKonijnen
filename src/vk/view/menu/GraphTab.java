package vk.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import vk.view.menu.item.PieItem;
import vk.view.windows.PieGraphFrame;

public class GraphTab extends JMenu implements ActionListener {
	/**
	 * random nummertje
	 */
	private static final long serialVersionUID = 7865087811043667737L;

	public GraphTab()
	{
		super("<HTML><U>G</U>raph</HTML>");
		PieItem pieItem = new PieItem();
		this.add(pieItem);
		pieItem.setActionCommand("pieItem");
		pieItem.addActionListener(this);
		pieItem.setMnemonic(KeyEvent.VK_P);
    }
	
	public void actionPerformed(ActionEvent event) {
		System.out.println(event.getActionCommand());
		if(event.getActionCommand().equals("pieItem"))
		{
			PieGraphFrame pieFrame = new PieGraphFrame();
			pieFrame.setVisible(true);
		}
		
	}
}
