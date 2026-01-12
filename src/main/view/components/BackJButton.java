package main.view.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.view.*;

public class BackJButton extends IconJButton {
	
	private static final long serialVersionUID = 1L;
	
	public BackJButton (MainFrame mainFrame) {
		//Icon:
		setIcon(resizeIcon("resources/images/back.png", 35, 35));
		setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setText(null);
        setPreferredSize(new Dimension(40, 40));
        
		//Return to the title screen:
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showPage("TitleScreen");
			}
		});
	}
}
