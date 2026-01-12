package main.view.components;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Class to show images in a JPanel.
 */
public class ImageJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image backgroundImage;

	public ImageJPanel(String resourcePath) {
		URL url = getClass().getClassLoader().getResource(resourcePath);

        if (url == null) {
            throw new IllegalArgumentException("No resource: "+resourcePath);
        }

        backgroundImage = new ImageIcon(url).getImage();
    }

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
