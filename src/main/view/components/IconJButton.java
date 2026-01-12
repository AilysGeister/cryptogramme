package main.view.components;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import main.Main;

public class IconJButton extends JButton {
	
	private static final long serialVersionUID = 1L;
	
	protected ImageIcon resizeIcon(String path, int width, int height) {
		URL url = Main.class.getClassLoader().getResource(path);
		if (url == null) {
			throw new IllegalArgumentException("Ressource introuvable : " + path);
		}

		ImageIcon icon = new ImageIcon(url);
		Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

		return new ImageIcon(scaled);
	}
}
