package main.view;

import main.controller.*;
import main.model.KeyRSA;
import main.view.components.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;


public class GenerateAsynchKeyJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	private KeyRSA key = null;
	public JLabel returnMsgJLabel;
	public JTextArea keyNPublicJTextArea, keyEPublicJTextArea, keyNPrivateJTextArea, keyDPrivateJTextArea;
	public JButton savePublicJButton, savePrivateJButton, showJButton;

	/**
	 * Create the panel.
	 */
	public GenerateAsynchKeyJPanel(MainFrame mainframe) {
		this.mainFrame = mainframe;
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel generatePanel = new JPanel();
		add(generatePanel, BorderLayout.NORTH);
		
		JButton backButton = new BackJButton(mainFrame);
		generatePanel.add(backButton);
		
		JLabel title = new JLabel(PropertiesReader.getString("KeyRSA"));
		title.setFont(new Font("Tahoma", Font.BOLD, 14));
		generatePanel.add(title);
		
		JLabel sizeLabel = new JLabel(PropertiesReader.getString("selectKeySize")+":");
		generatePanel.add(sizeLabel);
		
		JComboBox<ComboItem> keySizeJComboBox = new JComboBox<ComboItem>();
		keySizeJComboBox.addItem(new ComboItem("4096 bits ("+PropertiesReader.getString("recommended")+")", 4096));
		keySizeJComboBox.addItem(new ComboItem("2048 bits", 2048));
		generatePanel.add(keySizeJComboBox);
		
		JButton generateJButton = new JButton(PropertiesReader.getString("generate"));
		generateJButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		generateJButton.addActionListener(e -> {
			ComboItem selected = (ComboItem) keySizeJComboBox.getSelectedItem();
			RSA.generateKeys(this, selected.getIntValue());
		});
		generatePanel.add(generateJButton);
		
		this.returnMsgJLabel = new JLabel();
		add(returnMsgJLabel, BorderLayout.SOUTH);
		returnMsgJLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JPanel middlePanel = new JPanel();
		add(middlePanel, BorderLayout.CENTER);
		middlePanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		//Scroll bar:
		JScrollPane scroll = new JScrollPane(middlePanel);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scroll, BorderLayout.CENTER);
		
		
		JPanel publicPanel = new JPanel();
		middlePanel.add(publicPanel);
		GridBagLayout gbl_publicPanel = new GridBagLayout();
		gbl_publicPanel.rowHeights = new int[]{17, 0, 0, 0, 0};
		gbl_publicPanel.columnWeights = new double[]{0.0, 1.0};
		gbl_publicPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		publicPanel.setLayout(gbl_publicPanel);
		
		JLabel publicLabel = new JLabel(PropertiesReader.getString("publicKey")+":");
		publicLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_publicLabel = new GridBagConstraints();
		gbc_publicLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_publicLabel.insets = new Insets(0, 0, 5, 5);
		gbc_publicLabel.gridx = 0;
		gbc_publicLabel.gridy = 0;
		publicPanel.add(publicLabel, gbc_publicLabel);
		
		JLabel nPublicLabel = new JLabel("N:");
		nPublicLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_nPublicLabel = new GridBagConstraints();
		gbc_nPublicLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nPublicLabel.anchor = GridBagConstraints.EAST;
		gbc_nPublicLabel.gridx = 0;
		gbc_nPublicLabel.gridy = 1;
		publicPanel.add(nPublicLabel, gbc_nPublicLabel);
		
		this.keyNPublicJTextArea = new JTextArea();
		keyNPublicJTextArea.setText("("+PropertiesReader.getString("generateKey")+")");
		this.keyNPublicJTextArea.setLineWrap(true);
		this.keyNPublicJTextArea.setWrapStyleWord(true);
		this.keyNPublicJTextArea.setEditable(false);
		GridBagConstraints gbc_keyNPublicJTextArea = new GridBagConstraints();
		gbc_keyNPublicJTextArea.insets = new Insets(0, 0, 5, 0);
		gbc_keyNPublicJTextArea.fill = GridBagConstraints.BOTH;
		gbc_keyNPublicJTextArea.gridx = 1;
		gbc_keyNPublicJTextArea.gridy = 1;
		publicPanel.add(this.keyNPublicJTextArea, gbc_keyNPublicJTextArea);
		
		JLabel ePublicLabel = new JLabel("E:\r\n");
		ePublicLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_ePublicLabel = new GridBagConstraints();
		gbc_ePublicLabel.anchor = GridBagConstraints.EAST;
		gbc_ePublicLabel.insets = new Insets(0, 0, 5, 5);
		gbc_ePublicLabel.gridx = 0;
		gbc_ePublicLabel.gridy = 2;
		publicPanel.add(ePublicLabel, gbc_ePublicLabel);
		
		this.keyEPublicJTextArea = new JTextArea();
		keyEPublicJTextArea.setText("("+PropertiesReader.getString("generateKey")+")");
		this.keyEPublicJTextArea.setWrapStyleWord(true);
		this.keyEPublicJTextArea.setLineWrap(true);
		this.keyEPublicJTextArea.setEditable(false);
		GridBagConstraints gbc_keyEPublicJTextArea = new GridBagConstraints();
		gbc_keyEPublicJTextArea.insets = new Insets(0, 0, 5, 0);
		gbc_keyEPublicJTextArea.fill = GridBagConstraints.BOTH;
		gbc_keyEPublicJTextArea.gridx = 1;
		gbc_keyEPublicJTextArea.gridy = 2;
		publicPanel.add(this.keyEPublicJTextArea, gbc_keyEPublicJTextArea);
		
		this.savePublicJButton = new SaveJButton();
		this.savePublicJButton.addActionListener(e -> {
			RSA.saveKey(this, 0);
		});
		savePublicJButton.setEnabled(false);
		savePublicJButton.setToolTipText(PropertiesReader.getString("saveToolTip"));
		GridBagConstraints gbc_savePublicJButton = new GridBagConstraints();
		gbc_savePublicJButton.insets = new Insets(0, 0, 0, 5);
		gbc_savePublicJButton.gridx = 0;
		gbc_savePublicJButton.gridy = 3;
		publicPanel.add(savePublicJButton, gbc_savePublicJButton);
		
		JPanel privatePanel = new JPanel();
		middlePanel.add(privatePanel);
		GridBagLayout gbl_privatePanel = new GridBagLayout();
		gbl_privatePanel.columnWidths = new int[]{0, 0, 0};
		gbl_privatePanel.rowHeights = new int[]{17, 0, 0, 0, 0};
		gbl_privatePanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_privatePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		privatePanel.setLayout(gbl_privatePanel);
		
		JLabel privateLabel = new JLabel(PropertiesReader.getString("privateKey")+":");
		privateLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_privateLabel = new GridBagConstraints();
		gbc_privateLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_privateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_privateLabel.gridx = 0;
		gbc_privateLabel.gridy = 0;
		privatePanel.add(privateLabel, gbc_privateLabel);
		
		JLabel nPrivateLabel = new JLabel("N:");
		nPrivateLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_nPrivateLabel = new GridBagConstraints();
		gbc_nPrivateLabel.anchor = GridBagConstraints.EAST;
		gbc_nPrivateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nPrivateLabel.gridx = 0;
		gbc_nPrivateLabel.gridy = 1;
		privatePanel.add(nPrivateLabel, gbc_nPrivateLabel);
		
		this.keyNPrivateJTextArea = new JTextArea();
		keyNPrivateJTextArea.setText("("+PropertiesReader.getString("generateKey")+")");
		this.keyNPrivateJTextArea.setLineWrap(true);
		this.keyNPrivateJTextArea.setWrapStyleWord(true);
		this.keyNPrivateJTextArea.setEditable(false);
		GridBagConstraints gbc_keyNPrivateJTextArea = new GridBagConstraints();
		gbc_keyNPrivateJTextArea.insets = new Insets(0, 0, 5, 0);
		gbc_keyNPrivateJTextArea.fill = GridBagConstraints.BOTH;
		gbc_keyNPrivateJTextArea.gridx = 1;
		gbc_keyNPrivateJTextArea.gridy = 1;
		privatePanel.add(this.keyNPrivateJTextArea, gbc_keyNPrivateJTextArea);
		
		JLabel dPrivateLabel = new JLabel("D:");
		dPrivateLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_dPrivateLabel = new GridBagConstraints();
		gbc_dPrivateLabel.anchor = GridBagConstraints.EAST;
		gbc_dPrivateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dPrivateLabel.gridx = 0;
		gbc_dPrivateLabel.gridy = 2;
		privatePanel.add(dPrivateLabel, gbc_dPrivateLabel);
		
		this.keyDPrivateJTextArea = new JTextArea();
		this.keyDPrivateJTextArea.setText("("+PropertiesReader.getString("generateKey")+")");
		this.keyDPrivateJTextArea.setLineWrap(true);
		this.keyDPrivateJTextArea.setWrapStyleWord(true);
		this.keyDPrivateJTextArea.setEditable(false);
		GridBagConstraints gbc_keyDJTextArea = new GridBagConstraints();
		gbc_keyDJTextArea.insets = new Insets(0, 0, 5, 0);
		gbc_keyDJTextArea.fill = GridBagConstraints.BOTH;
		gbc_keyDJTextArea.gridx = 1;
		gbc_keyDJTextArea.gridy = 2;
		privatePanel.add(this.keyDPrivateJTextArea, gbc_keyDJTextArea);
		
		this.savePrivateJButton = new SaveJButton();
		this.savePrivateJButton.addActionListener(e -> {
			RSA.saveKey(this, 1);
		});
		savePrivateJButton.setToolTipText(PropertiesReader.getString("saveToolTip"));
		savePrivateJButton.setEnabled(false);
		GridBagConstraints gbc_savePrivateJButton = new GridBagConstraints();
		gbc_savePrivateJButton.insets = new Insets(0, 0, 0, 5);
		gbc_savePrivateJButton.gridx = 0;
		gbc_savePrivateJButton.gridy = 3;
		privatePanel.add(savePrivateJButton, gbc_savePrivateJButton);
		
		JPanel completePanel = new JPanel();
		middlePanel.add(completePanel);
		completePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel headerCompleteJPanel = new JPanel();
		completePanel.add(headerCompleteJPanel, BorderLayout.NORTH);
		GridBagLayout gbl_headerCompleteJPanel = new GridBagLayout();
		gbl_headerCompleteJPanel.columnWidths = new int[] {30, 30, 0};
		gbl_headerCompleteJPanel.rowHeights = new int[] {0};
		gbl_headerCompleteJPanel.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_headerCompleteJPanel.rowWeights = new double[]{0.0};
		headerCompleteJPanel.setLayout(gbl_headerCompleteJPanel);
		
		JLabel completeLabel = new JLabel(PropertiesReader.getString("completeKey")+":");
		completeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_completeLabel = new GridBagConstraints();
		gbc_completeLabel.anchor = GridBagConstraints.WEST;
		gbc_completeLabel.insets = new Insets(0, 0, 0, 5);
		gbc_completeLabel.fill = GridBagConstraints.VERTICAL;
		gbc_completeLabel.gridx = 0;
		gbc_completeLabel.gridy = 0;
		headerCompleteJPanel.add(completeLabel, gbc_completeLabel);
		
		this.showJButton = new JButton(PropertiesReader.getString("show"));
		this.showJButton.addActionListener(e -> {
			RSA.showCompleteKey(this);
		});
		this.showJButton.setEnabled(false);
		GridBagConstraints gbc_showJButton = new GridBagConstraints();
		gbc_showJButton.fill = GridBagConstraints.VERTICAL;
		gbc_showJButton.insets = new Insets(0, 0, 0, 5);
		gbc_showJButton.gridx = 1;
		gbc_showJButton.gridy = 0;
		headerCompleteJPanel.add(this.showJButton, gbc_showJButton);

	}
	
	/**
	 * Set the RSA key value of the panel.
	 * @param key
	 */
	public void setKey(KeyRSA key) {
		this.key = key;
	}
	
	/**
	 * Return the current RSA key of the panel.
	 * @return
	 */
	public KeyRSA getKey() {
		return this.key;
	}
	
	/**
	 * Return the MainFrame.
	 * @return
	 */
	public MainFrame getMainFrame() {
		return this.mainFrame;
	}
}
