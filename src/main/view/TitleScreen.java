package main.view;

import main.controller.PropertiesReader;
import main.view.components.*;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class TitleScreen extends JPanel {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private MainFrame mainFrame;

	/**
	 * Create the panel.
	 */
	public TitleScreen(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		setLayout(new BorderLayout(0, 0));
		
		JPanel boodyJPanel = new JPanel();
		add(boodyJPanel, BorderLayout.CENTER);
		boodyJPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel algorithmsJPanel = new JPanel();
		boodyJPanel.add(algorithmsJPanel);
		algorithmsJPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel messagePanel = new JPanel();
		algorithmsJPanel.add(messagePanel);
		GridBagLayout gbl_messagePanel = new GridBagLayout();
		gbl_messagePanel.columnWidths = new int[]{188, 0};
		gbl_messagePanel.rowHeights = new int[]{17, 31, 21, 10, 21, 0};
		gbl_messagePanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_messagePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		messagePanel.setLayout(gbl_messagePanel);
		
		JLabel rsaLabel = new JLabel(PropertiesReader.getString("message")+" (RSA)");
		rsaLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_rsaLabel = new GridBagConstraints();
		gbc_rsaLabel.insets = new Insets(0, 0, 5, 0);
		gbc_rsaLabel.gridx = 0;
		gbc_rsaLabel.gridy = 0;
		messagePanel.add(rsaLabel, gbc_rsaLabel);
		
		JButton generateKeysRsaButton = new JButton(PropertiesReader.getString("generateRSAKey"));
		generateKeysRsaButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		generateKeysRsaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showPage("GenerateKeyRSA");
			}
		});
		GridBagConstraints gbc_generateKeysRsaButton = new GridBagConstraints();
		gbc_generateKeysRsaButton.insets = new Insets(0, 0, 5, 0);
		gbc_generateKeysRsaButton.gridx = 0;
		gbc_generateKeysRsaButton.gridy = 1;
		messagePanel.add(generateKeysRsaButton, gbc_generateKeysRsaButton);
		
		JButton encryptMessageButton = new JButton(PropertiesReader.getString("encryptRSA"));
		encryptMessageButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		encryptMessageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showPage("EncryptRSAJPanel");
			}
		});
		GridBagConstraints gbc_encryptMessageButton = new GridBagConstraints();
		gbc_encryptMessageButton.insets = new Insets(0, 0, 5, 0);
		gbc_encryptMessageButton.gridx = 0;
		gbc_encryptMessageButton.gridy = 2;
		messagePanel.add(encryptMessageButton, gbc_encryptMessageButton);
		
		JButton decryptMessageButton = new JButton(PropertiesReader.getString("decryptRSA"));
		decryptMessageButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		decryptMessageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showPage("DecryptRSAJPanel");
			}
		});
		GridBagConstraints gbc_decryptMessageButton = new GridBagConstraints();
		gbc_decryptMessageButton.insets = new Insets(0, 0, 5, 0);
		gbc_decryptMessageButton.gridx = 0;
		gbc_decryptMessageButton.gridy = 3;
		messagePanel.add(decryptMessageButton, gbc_decryptMessageButton);
		
		JPanel filePanel = new JPanel();
		algorithmsJPanel.add(filePanel);
		GridBagLayout gbl_filePanel = new GridBagLayout();
		gbl_filePanel.columnWidths = new int[]{69, 0};
		gbl_filePanel.rowHeights = new int[]{17, 0, 0, 0, 0};
		gbl_filePanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_filePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		filePanel.setLayout(gbl_filePanel);
		
		JLabel filesLabel = new JLabel(PropertiesReader.getString("file")+" (AES)");
		filesLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_filesLabel = new GridBagConstraints();
		gbc_filesLabel.insets = new Insets(0, 0, 5, 0);
		gbc_filesLabel.gridx = 0;
		gbc_filesLabel.gridy = 0;
		filePanel.add(filesLabel, gbc_filesLabel);
		
		JButton generateKeyAesButton = new JButton(PropertiesReader.getString("generateAESKey"));
		generateKeyAesButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		generateKeyAesButton.addActionListener(e -> {
			mainFrame.showPage("GenerateKeyAES");
		});
		GridBagConstraints gbc_generateKeyAesButton = new GridBagConstraints();
		gbc_generateKeyAesButton.insets = new Insets(0, 0, 5, 0);
		gbc_generateKeyAesButton.gridx = 0;
		gbc_generateKeyAesButton.gridy = 1;
		filePanel.add(generateKeyAesButton, gbc_generateKeyAesButton);
		
		JButton cryptFileButton = new JButton(PropertiesReader.getString("encryptAES"));
		cryptFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showPage("EncryptAESJPanel");
			}
		});
		cryptFileButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_cryptFileButton = new GridBagConstraints();
		gbc_cryptFileButton.insets = new Insets(0, 0, 5, 0);
		gbc_cryptFileButton.gridx = 0;
		gbc_cryptFileButton.gridy = 2;
		filePanel.add(cryptFileButton, gbc_cryptFileButton);
		
		JButton uncryptFileButton = new JButton(PropertiesReader.getString("decryptAES"));
		uncryptFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showPage("DecryptAESJPanel");
			}
		});
		uncryptFileButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_uncryptFileButton = new GridBagConstraints();
		gbc_uncryptFileButton.gridx = 0;
		gbc_uncryptFileButton.gridy = 3;
		filePanel.add(uncryptFileButton, gbc_uncryptFileButton);
		
		JPanel appJPanel = new JPanel();
		boodyJPanel.add(appJPanel);
		GridBagLayout gbl_appJPanel = new GridBagLayout();
		gbl_appJPanel.columnWidths = new int[]{141, 321, 0};
		gbl_appJPanel.rowHeights = new int[]{148, 0};
		gbl_appJPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_appJPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		appJPanel.setLayout(gbl_appJPanel);
		
		JPanel logoJPanel = new ImageJPanel("resources/images/padlock.png");
		GridBagConstraints gbc_logoJPanel = new GridBagConstraints();
		gbc_logoJPanel.fill = GridBagConstraints.BOTH;
		gbc_logoJPanel.insets = new Insets(0, 0, 0, 5);
		gbc_logoJPanel.gridx = 0;
		gbc_logoJPanel.gridy = 0;
		appJPanel.add(logoJPanel, gbc_logoJPanel);
		logoJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel infoJPanel = new JPanel();
		GridBagConstraints gbc_infoJPanel = new GridBagConstraints();
		gbc_infoJPanel.fill = GridBagConstraints.BOTH;
		gbc_infoJPanel.gridx = 1;
		gbc_infoJPanel.gridy = 0;
		appJPanel.add(infoJPanel, gbc_infoJPanel);
		GridBagLayout gbl_infoJPanel = new GridBagLayout();
		gbl_infoJPanel.columnWidths = new int[]{0, 0};
		gbl_infoJPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_infoJPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_infoJPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		infoJPanel.setLayout(gbl_infoJPanel);
		
		JLabel lblNewLabel = new JLabel("Cryptogramme");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		infoJPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel versionID = new JLabel("Version: 1.0");
		versionID.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_versionID = new GridBagConstraints();
		gbc_versionID.insets = new Insets(0, 0, 5, 0);
		gbc_versionID.anchor = GridBagConstraints.WEST;
		gbc_versionID.gridx = 0;
		gbc_versionID.gridy = 1;
		infoJPanel.add(versionID, gbc_versionID);
		
		JLabel versionType = new JLabel("Version Pedagogique");
		versionType.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_versionType = new GridBagConstraints();
		gbc_versionType.insets = new Insets(0, 0, 5, 0);
		gbc_versionType.anchor = GridBagConstraints.WEST;
		gbc_versionType.gridx = 0;
		gbc_versionType.gridy = 2;
		infoJPanel.add(versionType, gbc_versionType);
		
		JLabel lblNewLabel_1 = new JLabel("Projet AC20 A25 UTBM");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 3;
		infoJPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

	}
}
