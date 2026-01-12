package main.view;

import main.controller.*;
import main.model.KeyRSA;
import main.view.components.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.nio.file.Path;

public class CryptRSAJPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	protected MainFrame mainFrame;
	
	private KeyRSA key;
	
	public JLabel maxSizeJLabel, returnMsgJLabel;

	public JTextArea resultJTextArea, cryptJTextArea;
	
	public JTextField keyLocationJTextField, filePathJTextField;
	
	public JButton keyFileChooserJButton, validateKeyJButton, cryptJButton, saveJButton, resetJButton, savedMessageFileChooserJButton;
	
	public CryptRSAJPanel(MainFrame mainFrame, String type) {
		//Initialization:
		this.mainFrame = mainFrame;
		int mode = -1;
		
		//Get the mode:
		if (type.equals("encrypt")) mode = 0; //Public key
		if (type.equals("decrypt")) mode = 1; //Private key
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel headerJPanel = new JPanel();
		add(headerJPanel, BorderLayout.NORTH);
		
		BackJButton backButton = new BackJButton(mainFrame);
		headerJPanel.add(backButton);
		
		JLabel titleJLabel = new JLabel("RSA: "+PropertiesReader.getString(type));
		titleJLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		headerJPanel.add(titleJLabel);
		
		JLabel helpJLabel = new JLabel(PropertiesReader.getString("firstValidateKey"));
		headerJPanel.add(helpJLabel);
		
		JPanel footerJPanel = new JPanel();
		add(footerJPanel, BorderLayout.SOUTH);
		
		this.returnMsgJLabel = new JLabel();
		returnMsgJLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		footerJPanel.add(this.returnMsgJLabel);
		
		JPanel boodyJPanel = new JPanel();
		add(boodyJPanel, BorderLayout.CENTER);
		boodyJPanel.setLayout(new GridLayout(3, 0, 0, 0));
		
		JPanel keyJPanel = new JPanel();
		boodyJPanel.add(keyJPanel);
		GridBagLayout gbl_keyJPanel = new GridBagLayout();
		gbl_keyJPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_keyJPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_keyJPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_keyJPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		keyJPanel.setLayout(gbl_keyJPanel);
		
		String typeKey = new String();
		if (mode == 0) typeKey = "publicKey";
		if (mode == 1) typeKey = "privateKey";
		JLabel keyJLabel = new JLabel(PropertiesReader.getString(typeKey));
		keyJLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_keyJLabel = new GridBagConstraints();
		gbc_keyJLabel.insets = new Insets(0, 0, 5, 5);
		gbc_keyJLabel.gridx = 0;
		gbc_keyJLabel.gridy = 0;
		keyJPanel.add(keyJLabel, gbc_keyJLabel);
		
		JLabel keyLocationJlabel = new JLabel(PropertiesReader.getString("keyLocation")+":");
		keyLocationJlabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_keyLocationJlabel = new GridBagConstraints();
		gbc_keyLocationJlabel.anchor = GridBagConstraints.EAST;
		gbc_keyLocationJlabel.insets = new Insets(0, 0, 5, 5);
		gbc_keyLocationJlabel.gridx = 0;
		gbc_keyLocationJlabel.gridy = 1;
		keyJPanel.add(keyLocationJlabel, gbc_keyLocationJlabel);
		
		this.keyLocationJTextField = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 1;
		keyJPanel.add(this.keyLocationJTextField, gbc_textField_2);
		
		this.keyFileChooserJButton = new FileChooserJButton(this.keyLocationJTextField, "keys_directory", true);
		GridBagConstraints gbc_savedMessageFileChooserJButton = new GridBagConstraints();
		gbc_savedMessageFileChooserJButton.insets = new Insets(0, 0, 5, 0);
		gbc_savedMessageFileChooserJButton.gridx = 2;
		gbc_savedMessageFileChooserJButton.gridy = 1;
		keyJPanel.add(keyFileChooserJButton, gbc_savedMessageFileChooserJButton);
		gbc_savedMessageFileChooserJButton.insets = new Insets(0, 0, 0, 5);
		gbc_savedMessageFileChooserJButton.gridx = 0;
		gbc_savedMessageFileChooserJButton.gridy = 2;
		
		this.validateKeyJButton = new JButton(PropertiesReader.getString("validate"));
		validateKeyJButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		validateKeyJButton.addActionListener(e -> {
			RSA.validateKeyButton(this, Path.of(this.keyLocationJTextField.getText()), type);
		});
		GridBagConstraints gbc_validateKeyJButton = new GridBagConstraints();
		gbc_validateKeyJButton.insets = new Insets(0, 0, 0, 5);
		gbc_validateKeyJButton.gridx = 0;
		gbc_validateKeyJButton.gridy = 2;
		keyJPanel.add(this.validateKeyJButton, gbc_validateKeyJButton);
		
		JPanel cryptJPanel = new JPanel();
		boodyJPanel.add(cryptJPanel);
		GridBagLayout gbl_cryptJPanel = new GridBagLayout();
		gbl_cryptJPanel.columnWidths = new int[]{0, 0};
		gbl_cryptJPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_cryptJPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_cryptJPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		cryptJPanel.setLayout(gbl_cryptJPanel);
		
		JLabel cryptJLabel = new JLabel(PropertiesReader.getString("new_"+type));
		cryptJLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_cryptJLabel = new GridBagConstraints();
		gbc_cryptJLabel.anchor = GridBagConstraints.WEST;
		gbc_cryptJLabel.insets = new Insets(0, 0, 5, 0);
		gbc_cryptJLabel.gridx = 0;
		gbc_cryptJLabel.gridy = 0;
		cryptJPanel.add(cryptJLabel, gbc_cryptJLabel);
		
		JPanel sizeJPanel = new JPanel();
		GridBagConstraints gbc_sizeJPanel = new GridBagConstraints();
		gbc_sizeJPanel.anchor = GridBagConstraints.WEST;
		gbc_sizeJPanel.insets = new Insets(0, 0, 5, 0);
		gbc_sizeJPanel.gridx = 0;
		gbc_sizeJPanel.gridy = 1;
		cryptJPanel.add(sizeJPanel, gbc_sizeJPanel);
		
		JLabel maxSizeTitle = new JLabel(PropertiesReader.getString("maxSize")+":");
		maxSizeTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sizeJPanel.add(maxSizeTitle);
		
		this.maxSizeJLabel = new JLabel(PropertiesReader.getString("enterKey"));
		this.maxSizeJLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sizeJPanel.add(this.maxSizeJLabel);
		
		JLabel filePathJLabel = new JLabel(PropertiesReader.getString("filePath")+":");
		filePathJLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_filePathJLabel = new GridBagConstraints();
		gbc_filePathJLabel.anchor = GridBagConstraints.WEST;
		gbc_filePathJLabel.insets = new Insets(0, 0, 5, 0);
		gbc_filePathJLabel.gridx = 0;
		gbc_filePathJLabel.gridy = 2;
		cryptJPanel.add(filePathJLabel, gbc_filePathJLabel);
		
		JPanel fileJPanel = new JPanel();
		GridBagConstraints gbc_fileJPanel = new GridBagConstraints();
		gbc_fileJPanel.insets = new Insets(0, 0, 5, 0);
		gbc_fileJPanel.fill = GridBagConstraints.BOTH;
		gbc_fileJPanel.gridx = 0;
		gbc_fileJPanel.gridy = 3;
		cryptJPanel.add(fileJPanel, gbc_fileJPanel);
		GridBagLayout gbl_fileJPanel = new GridBagLayout();
		gbl_fileJPanel.columnWeights = new double[]{1.0, 0.0};
		gbl_fileJPanel.rowWeights = new double[]{0.0};
		fileJPanel.setLayout(gbl_fileJPanel);
		
		this.filePathJTextField = new JTextField();
		filePathJTextField.setEnabled(false);
		GridBagConstraints gbc_filePathJTextField = new GridBagConstraints();
		gbc_filePathJTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_filePathJTextField.insets = new Insets(0, 0, 0, 5);
		gbc_filePathJTextField.anchor = GridBagConstraints.NORTH;
		gbc_filePathJTextField.gridx = 0;
		gbc_filePathJTextField.gridy = 0;
		fileJPanel.add(filePathJTextField, gbc_filePathJTextField);
		filePathJTextField.setColumns(10);
		
		this.savedMessageFileChooserJButton = new FileChooserJButton(this.filePathJTextField, "export_directory", false);
		this.savedMessageFileChooserJButton.setEnabled(false);
		GridBagConstraints gbc_savedMessageFileChooserJButton1 = new GridBagConstraints();
		gbc_savedMessageFileChooserJButton1.gridx = 1;
		gbc_savedMessageFileChooserJButton1.gridy = 0;
		fileJPanel.add(this.savedMessageFileChooserJButton, gbc_savedMessageFileChooserJButton1);
		
		JLabel lblNewLabel = new JLabel(PropertiesReader.getString("copyHere")+":");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 4;
		cryptJPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		this.cryptJTextArea = new JTextArea();
		cryptJTextArea.setEnabled(false);
		this.cryptJTextArea.setLineWrap(true);
		this.cryptJTextArea.setWrapStyleWord(true);
		GridBagConstraints gbc_textArea_1 = new GridBagConstraints();
		gbc_textArea_1.insets = new Insets(0, 0, 5, 0);
		gbc_textArea_1.fill = GridBagConstraints.BOTH;
		gbc_textArea_1.gridx = 0;
		gbc_textArea_1.gridy = 5;
		cryptJPanel.add(this.cryptJTextArea, gbc_textArea_1);
		gbc_savedMessageFileChooserJButton1.anchor = GridBagConstraints.WEST;
		gbc_savedMessageFileChooserJButton1.gridx = 0;
		gbc_savedMessageFileChooserJButton1.gridy = 3;
		
		this.cryptJButton = new JButton(PropertiesReader.getString(type));
		cryptJButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		cryptJButton.addActionListener(e -> {
			RSA.cryptButton(this, type);
		});
		cryptJButton.setEnabled(false);
		GridBagConstraints gbc_cryptJButton = new GridBagConstraints();
		gbc_cryptJButton.anchor = GridBagConstraints.WEST;
		gbc_cryptJButton.gridx = 0;
		gbc_cryptJButton.gridy = 6;
		cryptJPanel.add(this.cryptJButton, gbc_cryptJButton);
		
		JPanel resultJPanel = new JPanel();
		boodyJPanel.add(resultJPanel);
		GridBagLayout gbl_resultJPanel = new GridBagLayout();
		gbl_resultJPanel.columnWidths = new int[]{0, 0};
		gbl_resultJPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_resultJPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_resultJPanel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		resultJPanel.setLayout(gbl_resultJPanel);
		
		JLabel resultJLabel = new JLabel(PropertiesReader.getString("result")+":");
		resultJLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_resultJLabel = new GridBagConstraints();
		gbc_resultJLabel.anchor = GridBagConstraints.WEST;
		gbc_resultJLabel.insets = new Insets(0, 0, 5, 5);
		gbc_resultJLabel.gridx = 0;
		gbc_resultJLabel.gridy = 0;
		resultJPanel.add(resultJLabel, gbc_resultJLabel);
		gbc_textArea_1.insets = new Insets(0, 0, 5, 0);
		gbc_textArea_1.fill = GridBagConstraints.BOTH;
		gbc_textArea_1.gridx = 0;
		gbc_textArea_1.gridy = 1;
		
		this.resultJTextArea = new JTextArea();
		this.resultJTextArea.setLineWrap(true);
		this.resultJTextArea.setWrapStyleWord(true);
		this.resultJTextArea.setEditable(false);
		this.resultJTextArea.setEnabled(false);
		GridBagConstraints gbc_resultJTextArea = new GridBagConstraints();
		gbc_resultJTextArea.fill = GridBagConstraints.BOTH;
		gbc_resultJTextArea.insets = new Insets(0, 0, 5, 5);
		gbc_resultJTextArea.gridx = 0;
		gbc_resultJTextArea.gridy = 1;
		resultJPanel.add(this.resultJTextArea, gbc_resultJTextArea);
		
		JPanel buttonsJPanel = new JPanel();
		GridBagConstraints gbc_buttonsJPanel = new GridBagConstraints();
		gbc_buttonsJPanel.insets = new Insets(0, 0, 0, 5);
		gbc_buttonsJPanel.anchor = GridBagConstraints.WEST;
		gbc_buttonsJPanel.fill = GridBagConstraints.VERTICAL;
		gbc_buttonsJPanel.gridx = 0;
		gbc_buttonsJPanel.gridy = 2;
		resultJPanel.add(buttonsJPanel, gbc_buttonsJPanel);
		
		this.saveJButton = new SaveJButton();
		saveJButton.addActionListener( e -> {
			RSA.saveMessageButton(this, type.equals("encrypt"));
		});
		this.saveJButton.setEnabled(false);
		buttonsJPanel.add(this.saveJButton);
		
		this.resetJButton = new JButton(PropertiesReader.getString("reset"));
		resetJButton.addActionListener(e -> {
			RSA.resetCrypt(this);
		});
		this.resetJButton.setEnabled(false);
		buttonsJPanel.add(this.resetJButton);
	}
	
	public void setKey(KeyRSA key) {
		this.key = key;
	}
	
	public KeyRSA getKey() {
		return this.key;
	}
}
