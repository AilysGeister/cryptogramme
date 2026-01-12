package main.view.components;

import java.awt.Dimension;

public class SaveJButton extends IconJButton {
	
	private static final long serialVersionUID = 1L;
	
	public SaveJButton () {
		//Icon:
		setIcon(resizeIcon("resources/images/file.png", 30, 30));

		setFocusPainted(false);
        setText(null);
        setPreferredSize(new Dimension(35, 35));
	}
}
