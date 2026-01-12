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
import javax.swing.filechooser.FileNameExtensionFilter;

import main.controller.PropertiesReader;
import main.controller.Settings;

public class FileChooserJButton extends IconJButton {

	private static final long serialVersionUID = 1L;

	public FileChooserJButton (JTextField output, String type, boolean isRestricted) {
		//Icon:
		setIcon(resizeIcon("resources/images/open.png", 15, 15));

		setFocusPainted(false);
        setText(null);
        setPreferredSize(new Dimension(20, 20));
		
        //Select a file from the storage:
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Initialization:
				JFileChooser fileChooser = new JFileChooser();
				JFrame frame = new JFrame();
				
				//Configure the open directory:
				try {
					fileChooser.setCurrentDirectory(new File(Settings.getStrParameter(type)));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				//Configure the frame:
				Image image;
				try {
					image = ImageIO.read(getClass().getClassLoader().getResource("resources/images/padlock.png"));
					frame.setIconImage(image);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				//Restrict to PEM files:
				if (isRestricted) {
					FileNameExtensionFilter filter = new FileNameExtensionFilter(PropertiesReader.getString("pemFormat"),"pem");
					fileChooser.setFileFilter(filter);
				}
				
				
				//Open the windows:
				int result = fileChooser.showOpenDialog(frame);
				
				if (result == JFileChooser.APPROVE_OPTION) {
	                File selectedFile = fileChooser.getSelectedFile();
	                output.setText(selectedFile.getAbsolutePath());
	            }
			}
		});
	}
}
