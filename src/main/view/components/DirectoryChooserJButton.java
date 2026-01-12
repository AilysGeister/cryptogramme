package main.view.components;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class DirectoryChooserJButton extends IconJButton {

	private static final long serialVersionUID = 1L;

	public DirectoryChooserJButton (JTextField output) {
		//Icon:
		setIcon(resizeIcon("resources/images/open.png", 15, 15));

		setFocusPainted(false);
        setText(null);
        setPreferredSize(new Dimension(20, 20));
		
        //Select a file from the storage:
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Initialization:
				JFileChooser chooser = new JFileChooser();
				JFrame frame = new JFrame();
				
				//Configure the frame:
				Image image;
				try {
					image = ImageIO.read(getClass().getClassLoader().getResource("resources/images/padlock.png"));
					frame.setIconImage(image);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				//Restrict to directory:
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            chooser.setAcceptAllFileFilterUsed(false);
				
				//Open the windows:
				int result = chooser.showOpenDialog(frame);
				
				if (result == JFileChooser.APPROVE_OPTION) {
	                File selectedDirectory = chooser.getSelectedFile();
	                output.setText(selectedDirectory.getAbsolutePath());
	            }
			}
		});
	}
}
