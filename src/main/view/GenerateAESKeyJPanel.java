package main.view;

import main.controller.*;
import main.model.KeyAES;
import main.view.components.*;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class GenerateAESKeyJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private MainFrame mainframe;
	
	private KeyAES key;
	
	public JLabel returnMsgJLabel;
	
	public JTextField keyJTextField;
	
	public JButton saveJButton;

	/**
	 * Create the panel.
	 */
	public GenerateAESKeyJPanel(MainFrame mainFrame) {
		this.mainframe = mainFrame;
		setLayout(new BorderLayout(0, 0));
		
		JPanel headerJPanel = new JPanel();
		add(headerJPanel, BorderLayout.NORTH);
		
		BackJButton back = new BackJButton(mainFrame);
		headerJPanel.add(back);
		
		JLabel titleJLabel = new JLabel(PropertiesReader.getString("generateAESKey"));
		titleJLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		headerJPanel.add(titleJLabel);
		
		JPanel bodyJPanel = new JPanel();
		add(bodyJPanel, BorderLayout.CENTER);
		bodyJPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel genrateJPanel = new JPanel();
		bodyJPanel.add(genrateJPanel);
		GridBagLayout gbl_genrateJPanel = new GridBagLayout();
		gbl_genrateJPanel.columnWidths = new int[]{0, 0, 0};
		gbl_genrateJPanel.rowHeights = new int[]{0, 0, 0};
		gbl_genrateJPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_genrateJPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		genrateJPanel.setLayout(gbl_genrateJPanel);
		
		JLabel keySizeJLabel = new JLabel(PropertiesReader.getString("selectKeySize")+":");
		keySizeJLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_keySizeJLabel = new GridBagConstraints();
		gbc_keySizeJLabel.insets = new Insets(0, 0, 5, 5);
		gbc_keySizeJLabel.anchor = GridBagConstraints.EAST;
		gbc_keySizeJLabel.gridx = 0;
		gbc_keySizeJLabel.gridy = 0;
		genrateJPanel.add(keySizeJLabel, gbc_keySizeJLabel);
		
		JComboBox<ComboItem> keySizeJComboBox = new JComboBox<ComboItem>();
		keySizeJComboBox.addItem(new ComboItem("256 bits ("+PropertiesReader.getString("recommended")+")",256));
		keySizeJComboBox.addItem(new ComboItem("192 bits",192));
		keySizeJComboBox.addItem(new ComboItem("128 bits",128));
		GridBagConstraints gbc_keySizeJComboBox = new GridBagConstraints();
		gbc_keySizeJComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_keySizeJComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_keySizeJComboBox.gridx = 1;
		gbc_keySizeJComboBox.gridy = 0;
		genrateJPanel.add(keySizeJComboBox, gbc_keySizeJComboBox);
		
		JButton generateJButton = new JButton(PropertiesReader.getString("generate"));
		generateJButton.addActionListener(e -> {
			ComboItem selected = (ComboItem) keySizeJComboBox.getSelectedItem();
			AES.generateKey(this, selected.getIntValue());
		});
		generateJButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_generateJButton = new GridBagConstraints();
		gbc_generateJButton.insets = new Insets(0, 0, 0, 5);
		gbc_generateJButton.gridx = 0;
		gbc_generateJButton.gridy = 1;
		genrateJPanel.add(generateJButton, gbc_generateJButton);
		
		JPanel resultJPanel = new JPanel();
		bodyJPanel.add(resultJPanel);
		GridBagLayout gbl_resultJPanel = new GridBagLayout();
		gbl_resultJPanel.columnWidths = new int[]{314, 0, 0};
		gbl_resultJPanel.rowHeights = new int[]{0, 0, 0};
		gbl_resultJPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_resultJPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		resultJPanel.setLayout(gbl_resultJPanel);
		
		JLabel resultJLabel = new JLabel(PropertiesReader.getString("result")+":");
		resultJLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_resultJLabel = new GridBagConstraints();
		gbc_resultJLabel.anchor = GridBagConstraints.WEST;
		gbc_resultJLabel.insets = new Insets(0, 0, 5, 5);
		gbc_resultJLabel.gridx = 0;
		gbc_resultJLabel.gridy = 0;
		resultJPanel.add(resultJLabel, gbc_resultJLabel);
		
		this.keyJTextField = new JTextField();
		this.keyJTextField.setEnabled(false);
		this.keyJTextField.setEditable(false);
		GridBagConstraints gbc_keyJTextField = new GridBagConstraints();
		gbc_keyJTextField.insets = new Insets(0, 0, 0, 5);
		gbc_keyJTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_keyJTextField.gridx = 0;
		gbc_keyJTextField.gridy = 1;
		resultJPanel.add(keyJTextField, gbc_keyJTextField);
		this.keyJTextField.setColumns(10);
		
		this.saveJButton = new SaveJButton();
		saveJButton.addActionListener(e -> {
			AES.saveKey(this);
		});
		saveJButton.setEnabled(false);
		GridBagConstraints gbc_saveJButton = new GridBagConstraints();
		gbc_saveJButton.gridx = 1;
		gbc_saveJButton.gridy = 1;
		resultJPanel.add(this.saveJButton, gbc_saveJButton);
		
		JPanel footerJPanel = new JPanel();
		add(footerJPanel, BorderLayout.SOUTH);
		
		this.returnMsgJLabel = new JLabel();
		this.returnMsgJLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		footerJPanel.add(this.returnMsgJLabel);
	}
	
	public void setKey(KeyAES key) {
		this.key = key;
	}

	public KeyAES getKey() {
		return this.key;
	}
}
