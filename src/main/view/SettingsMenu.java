package main.view;

import main.controller.PropertiesReader;
import main.controller.Settings;
import main.view.components.*;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;

public class SettingsMenu extends JPanel {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private MainFrame mainFrame;
	public JTextField txtDefaultExportLocation, txtDefaultkeysLocation;
	public JLabel returnMsgJLabel;

	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public SettingsMenu(MainFrame mainFrame) throws IOException {
		this.mainFrame = mainFrame;
		setLayout(new BorderLayout(0, 0));
		
		JPanel footerJPanel = new JPanel();
		add(footerJPanel, BorderLayout.SOUTH);
		
		this.returnMsgJLabel = new JLabel();
		returnMsgJLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		footerJPanel.add(returnMsgJLabel);
		
		JPanel bodyJPanel = new JPanel();
		add(bodyJPanel, BorderLayout.CENTER);
		GridBagLayout gbl_bodyJPanel = new GridBagLayout();
		gbl_bodyJPanel.columnWeights = new double[]{0.0, 1.0, 0.0};
		gbl_bodyJPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		bodyJPanel.setLayout(gbl_bodyJPanel);
		
		JLabel localizationLabel = new JLabel("Language:");
		localizationLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_localizationLabel = new GridBagConstraints();
		gbc_localizationLabel.insets = new Insets(0, 0, 5, 5);
		gbc_localizationLabel.gridx = 0;
		gbc_localizationLabel.gridy = 0;
		bodyJPanel.add(localizationLabel, gbc_localizationLabel);
		
		JComboBox<ComboItem> languageComboBox = new JComboBox<ComboItem>();
		languageComboBox.addItem(new ComboItem(PropertiesReader.getString("english"), "en"));
		languageComboBox.addItem(new ComboItem(PropertiesReader.getString("french"), "fr"));
		languageComboBox.addItem(new ComboItem(PropertiesReader.getString("german"), "de"));
		GridBagConstraints gbc_languageComboBox = new GridBagConstraints();
		gbc_languageComboBox.anchor = GridBagConstraints.WEST;
		gbc_languageComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_languageComboBox.gridx = 1;
		gbc_languageComboBox.gridy = 0;
		bodyJPanel.add(languageComboBox, gbc_languageComboBox);
		
		JLabel defaultExportLocationLabel = new JLabel("Default export location:");
		defaultExportLocationLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_defaultExportLocationLabel = new GridBagConstraints();
		gbc_defaultExportLocationLabel.insets = new Insets(0, 0, 5, 5);
		gbc_defaultExportLocationLabel.gridx = 0;
		gbc_defaultExportLocationLabel.gridy = 1;
		bodyJPanel.add(defaultExportLocationLabel, gbc_defaultExportLocationLabel);
		txtDefaultExportLocation = new JTextField();
		txtDefaultExportLocation.setText(Settings.getStrParameter("export_directory"));
		GridBagConstraints gbc_txtDefaultExportLocation = new GridBagConstraints();
		gbc_txtDefaultExportLocation.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDefaultExportLocation.insets = new Insets(0, 0, 5, 5);
		gbc_txtDefaultExportLocation.gridx = 1;
		gbc_txtDefaultExportLocation.gridy = 1;
		bodyJPanel.add(txtDefaultExportLocation, gbc_txtDefaultExportLocation);
		txtDefaultExportLocation.setColumns(10);
		
		DirectoryChooserJButton exportLocationDirectoryChooserJButton_1 = new DirectoryChooserJButton(txtDefaultExportLocation);
		GridBagConstraints gbc_exportLocationDirectoryChooserJButton_1 = new GridBagConstraints();
		gbc_exportLocationDirectoryChooserJButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_exportLocationDirectoryChooserJButton_1.gridx = 2;
		gbc_exportLocationDirectoryChooserJButton_1.gridy = 1;
		bodyJPanel.add(exportLocationDirectoryChooserJButton_1, gbc_exportLocationDirectoryChooserJButton_1);
		
		JLabel defaultKeysLocationLabel = new JLabel("Default keys location:");
		defaultKeysLocationLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_defaultKeysLocationLabel = new GridBagConstraints();
		gbc_defaultKeysLocationLabel.insets = new Insets(0, 0, 5, 5);
		gbc_defaultKeysLocationLabel.gridx = 0;
		gbc_defaultKeysLocationLabel.gridy = 2;
		bodyJPanel.add(defaultKeysLocationLabel, gbc_defaultKeysLocationLabel);
		
		txtDefaultkeysLocation = new JTextField();
		txtDefaultkeysLocation.setText(Settings.getStrParameter("keys_directory"));
		GridBagConstraints gbc_txtDefaultkeysLocation = new GridBagConstraints();
		gbc_txtDefaultkeysLocation.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDefaultkeysLocation.insets = new Insets(0, 0, 5, 5);
		gbc_txtDefaultkeysLocation.gridx = 1;
		gbc_txtDefaultkeysLocation.gridy = 2;
		bodyJPanel.add(txtDefaultkeysLocation, gbc_txtDefaultkeysLocation);
		txtDefaultkeysLocation.setColumns(10);
		
		DirectoryChooserJButton keyLocationDirectoryChooserJButton = new DirectoryChooserJButton(txtDefaultkeysLocation);
		GridBagConstraints gbc_keyLocationDirectoryChooserJButton = new GridBagConstraints();
		gbc_keyLocationDirectoryChooserJButton.insets = new Insets(0, 0, 5, 0);
		gbc_keyLocationDirectoryChooserJButton.gridx = 2;
		gbc_keyLocationDirectoryChooserJButton.gridy = 2;
		bodyJPanel.add(keyLocationDirectoryChooserJButton, gbc_keyLocationDirectoryChooserJButton);
		
		JButton saveJButton = new JButton(PropertiesReader.getString("save"));
		GridBagConstraints gbc_saveJButton = new GridBagConstraints();
		gbc_saveJButton.insets = new Insets(0, 0, 5, 5);
		gbc_saveJButton.gridx = 0;
		gbc_saveJButton.gridy = 3;
		bodyJPanel.add(saveJButton, gbc_saveJButton);
		saveJButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!txtDefaultExportLocation.getText().isBlank() || !txtDefaultkeysLocation.getText().isBlank()) {
						ComboItem selected = (ComboItem) languageComboBox.getSelectedItem();
						Settings.updateSettings(true, selected.getStrValue(), txtDefaultExportLocation.getText(), txtDefaultkeysLocation.getText());
						returnMsgJLabel.setForeground(new Color(0, 175, 0));
						returnMsgJLabel.setText(PropertiesReader.getString("saveSettingsSuccess"));
					} else {
						returnMsgJLabel.setForeground(new Color(175, 0, 0));
						returnMsgJLabel.setText(PropertiesReader.getString("saveSettingsFaile"));
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JLabel warning = new JLabel(PropertiesReader.getString("warningSettings"));
		GridBagConstraints gbc_warning = new GridBagConstraints();
		gbc_warning.insets = new Insets(0, 0, 0, 5);
		gbc_warning.gridx = 1;
		gbc_warning.gridy = 4;
		bodyJPanel.add(warning, gbc_warning);
		warning.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JPanel headerJPanel = new JPanel();
		add(headerJPanel, BorderLayout.NORTH);
		
		BackJButton backButton = new BackJButton(mainFrame);
		headerJPanel.add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showPage("TitleScreen");
			}
		});
		
		JLabel settingsLabel = new JLabel(PropertiesReader.getString("settings"));
		headerJPanel.add(settingsLabel);
		settingsLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
	}

}
