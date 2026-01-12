package main.view;

import main.controller.AES;
import main.controller.PropertiesReader;
import main.view.components.*;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.IOException;

public class CryptAESJPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private MainFrame mainFrame;
	public JTextField keyLocationJTextField, fileLocationJTextField, outputFileJTextField;
	public JLabel returnMsgJLabel;
	
	public CryptAESJPanel(MainFrame mainFrame, String type) {
		this.mainFrame = mainFrame;
		setLayout(new BorderLayout(0, 0));
		
		JPanel headerJPanel = new JPanel();
		add(headerJPanel, BorderLayout.NORTH);
		
		BackJButton back = new BackJButton(mainFrame);
		headerJPanel.add(back);
		
		JLabel title = new JLabel("AES: "+PropertiesReader.getString(type));
		title.setFont(new Font("Tahoma", Font.BOLD, 14));
		headerJPanel.add(title);
		
		JPanel footerJPanel = new JPanel();
		add(footerJPanel, BorderLayout.SOUTH);
		
		this.returnMsgJLabel = new JLabel("");
		this.returnMsgJLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		footerJPanel.add(this.returnMsgJLabel);
		
		JPanel bodyJPanel = new JPanel();
		add(bodyJPanel, BorderLayout.CENTER);
		GridBagLayout gbl_bodyJPanel = new GridBagLayout();
		gbl_bodyJPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_bodyJPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_bodyJPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_bodyJPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		bodyJPanel.setLayout(gbl_bodyJPanel);
		
		JLabel helpJLabel = new JLabel(PropertiesReader.getString("helpAES"));
		GridBagConstraints gbc_helpJLabel = new GridBagConstraints();
		gbc_helpJLabel.fill = GridBagConstraints.BOTH;
		gbc_helpJLabel.insets = new Insets(0, 0, 5, 5);
		gbc_helpJLabel.gridx = 1;
		gbc_helpJLabel.gridy = 0;
		bodyJPanel.add(helpJLabel, gbc_helpJLabel);
		
		JLabel keyJLabel = new JLabel(PropertiesReader.getString("keyAESLocation")+":");
		GridBagConstraints gbc_keyJLabel = new GridBagConstraints();
		gbc_keyJLabel.anchor = GridBagConstraints.EAST;
		gbc_keyJLabel.insets = new Insets(0, 0, 5, 5);
		gbc_keyJLabel.gridx = 0;
		gbc_keyJLabel.gridy = 1;
		bodyJPanel.add(keyJLabel, gbc_keyJLabel);
		
		this.keyLocationJTextField = new JTextField(PropertiesReader.getString("toolTipKeyLocation"));
		this.keyLocationJTextField.setToolTipText(PropertiesReader.getString("toolTipKeyLocation"));
		GridBagConstraints gbc_keyLocationJTextField = new GridBagConstraints();
		gbc_keyLocationJTextField.insets = new Insets(0, 0, 5, 5);
		gbc_keyLocationJTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_keyLocationJTextField.gridx = 1;
		gbc_keyLocationJTextField.gridy = 1;
		bodyJPanel.add(this.keyLocationJTextField, gbc_keyLocationJTextField);
		this.keyLocationJTextField.setColumns(10);
		
		FileChooserJButton keyFileChooserJButton = new FileChooserJButton(keyLocationJTextField, "keys_directory", false);
		GridBagConstraints gbc_keyFileChooserJButton = new GridBagConstraints();
		gbc_keyFileChooserJButton.insets = new Insets(0, 0, 5, 0);
		gbc_keyFileChooserJButton.gridx = 2;
		gbc_keyFileChooserJButton.gridy = 1;
		bodyJPanel.add(keyFileChooserJButton, gbc_keyFileChooserJButton);
		
		JLabel fileLocationJLabel = new JLabel(PropertiesReader.getString("fileLocation")+":");
		GridBagConstraints gbc_fileLocationJLabel = new GridBagConstraints();
		gbc_fileLocationJLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fileLocationJLabel.anchor = GridBagConstraints.EAST;
		gbc_fileLocationJLabel.gridx = 0;
		gbc_fileLocationJLabel.gridy = 2;
		bodyJPanel.add(fileLocationJLabel, gbc_fileLocationJLabel);
		
		this.fileLocationJTextField = new JTextField(PropertiesReader.getString("toolTipKeyLocation"));
		this.fileLocationJTextField.setToolTipText(PropertiesReader.getString("toolTipKeyLocation"));
		GridBagConstraints gbc_fileLocationJTextField = new GridBagConstraints();
		gbc_fileLocationJTextField.insets = new Insets(0, 0, 5, 5);
		gbc_fileLocationJTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_fileLocationJTextField.gridx = 1;
		gbc_fileLocationJTextField.gridy = 2;
		bodyJPanel.add(this.fileLocationJTextField, gbc_fileLocationJTextField);
		this.fileLocationJTextField.setColumns(10);
		
		FileChooserJButton cryptFileChooserJButton = new FileChooserJButton(fileLocationJTextField, "export_directory", false);
		GridBagConstraints gbc_cryptFileChooserJButton = new GridBagConstraints();
		gbc_cryptFileChooserJButton.insets = new Insets(0, 0, 5, 0);
		gbc_cryptFileChooserJButton.gridx = 2;
		gbc_cryptFileChooserJButton.gridy = 2;
		bodyJPanel.add(cryptFileChooserJButton, gbc_cryptFileChooserJButton);
		
		JButton save = new JButton(PropertiesReader.getString(type));
		save.setFont(new Font("Tahoma", Font.BOLD, 12));
		save.addActionListener(e -> {
			boolean isValid = false;
			
			//Verifying that the common fields aren't blank:
			if (this.keyLocationJTextField.getText().isBlank() || this.fileLocationJTextField.getText().isBlank()) {
				this.returnMsgJLabel.setForeground(new Color(175,0,0));
				this.returnMsgJLabel.setText(PropertiesReader.getString("error")+": "+PropertiesReader.getString("fieldBlank"));
			} else {
				isValid = true;
			}
			
			//Verifying that the output file JTextField isn't blank:
			if (type.equals("decrypt")) {
				if (this.outputFileJTextField.getText().isBlank()) {
					isValid = false;
					
					//Return message:
					this.returnMsgJLabel.setForeground(new Color(175,0,0));
					this.returnMsgJLabel.setText(PropertiesReader.getString("error")+": "+PropertiesReader.getString("fieldBlank"));
				} else {
					isValid = true;
				}
			}
			
			//If all needed parameters are presents we do the choosen operation:
			if (isValid) {
				try {
					AES.cryptButton(this, type);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}		
		});
		
		JLabel outputJLabel = new JLabel(PropertiesReader.getString("outPutFileName")+":");
		outputJLabel.setVisible(false);
		outputJLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_outputJLabel = new GridBagConstraints();
		gbc_outputJLabel.insets = new Insets(0, 0, 5, 5);
		gbc_outputJLabel.anchor = GridBagConstraints.EAST;
		gbc_outputJLabel.gridx = 0;
		gbc_outputJLabel.gridy = 3;
		bodyJPanel.add(outputJLabel, gbc_outputJLabel);
		
		this.outputFileJTextField = new JTextField();
		this.outputFileJTextField.setToolTipText(PropertiesReader.getString("outputFilaNameToolTip"));
		this.outputFileJTextField.setVisible(false);
		GridBagConstraints gbc_outputFileJTextField = new GridBagConstraints();
		gbc_outputFileJTextField.insets = new Insets(0, 0, 5, 5);
		gbc_outputFileJTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_outputFileJTextField.gridx = 1;
		gbc_outputFileJTextField.gridy = 3;
		bodyJPanel.add(this.outputFileJTextField, gbc_outputFileJTextField);
		this.outputFileJTextField.setColumns(10);
		GridBagConstraints gbc_save = new GridBagConstraints();
		gbc_save.insets = new Insets(0, 0, 5, 5);
		gbc_save.gridx = 0;
		gbc_save.gridy = 4;
		bodyJPanel.add(save, gbc_save);
		
		if (type.equals("decrypt")) {
			outputJLabel.setVisible(true);
			this.outputFileJTextField.setVisible(true);
		}
	}

}
