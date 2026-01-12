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

public class SettingsMenu extends JPanel {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private MainFrame mainFrame;
	private JTextField txtDefaultExportLocation;
	private JTextField txtDefaultkeysLocation;

	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public SettingsMenu(MainFrame mainFrame) throws IOException {
		this.mainFrame = mainFrame;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		BackJButton backButton = new BackJButton(mainFrame);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showPage("TitleScreen");
			}
		});
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.anchor = GridBagConstraints.SOUTHWEST;
		gbc_backButton.insets = new Insets(0, 0, 5, 5);
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 0;
		add(backButton, gbc_backButton);
		
		JLabel settingsLabel = new JLabel("Settings");
		settingsLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_settingsLabel = new GridBagConstraints();
		gbc_settingsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_settingsLabel.gridx = 0;
		gbc_settingsLabel.gridy = 1;
		add(settingsLabel, gbc_settingsLabel);
		
		JLabel localizationLabel = new JLabel("Language:");
		localizationLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_localizationLabel = new GridBagConstraints();
		gbc_localizationLabel.insets = new Insets(0, 0, 5, 5);
		gbc_localizationLabel.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc_localizationLabel.gridx = 0;
		gbc_localizationLabel.gridy = 2;
		add(localizationLabel, gbc_localizationLabel);
		
		JComboBox<ComboItem> languageComboBox = new JComboBox<ComboItem>();
		languageComboBox.addItem(new ComboItem(PropertiesReader.getString("english"), "en"));
		languageComboBox.addItem(new ComboItem(PropertiesReader.getString("french"), "fr"));
		languageComboBox.addItem(new ComboItem(PropertiesReader.getString("german"), "de"));
		GridBagConstraints gbc_languageComboBox = new GridBagConstraints();
		gbc_languageComboBox.anchor = GridBagConstraints.WEST;
		gbc_languageComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_languageComboBox.gridx = 1;
		gbc_languageComboBox.gridy = 2;
		add(languageComboBox, gbc_languageComboBox);
		
		JLabel defaultExportLocationLabel = new JLabel("Default export location:");
		defaultExportLocationLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_defaultExportLocationLabel = new GridBagConstraints();
		gbc_defaultExportLocationLabel.anchor = GridBagConstraints.EAST;
		gbc_defaultExportLocationLabel.insets = new Insets(0, 0, 5, 5);
		gbc_defaultExportLocationLabel.gridx = 0;
		gbc_defaultExportLocationLabel.gridy = 3;
		add(defaultExportLocationLabel, gbc_defaultExportLocationLabel);
		txtDefaultExportLocation = new JTextField();
		txtDefaultExportLocation.setText(Settings.getStrParameter("export_directory"));
		GridBagConstraints gbc_txtDefaultExportLocation = new GridBagConstraints();
		gbc_txtDefaultExportLocation.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDefaultExportLocation.insets = new Insets(0, 0, 5, 5);
		gbc_txtDefaultExportLocation.gridx = 1;
		gbc_txtDefaultExportLocation.gridy = 3;
		add(txtDefaultExportLocation, gbc_txtDefaultExportLocation);
		txtDefaultExportLocation.setColumns(10);
		
		DirectoryChooserJButton exporyLocationDirectoryChooserJButton_1 = new DirectoryChooserJButton(txtDefaultExportLocation);
		GridBagConstraints gbc_exporyLocationDirectoryChooserJButton_1 = new GridBagConstraints();
		gbc_exporyLocationDirectoryChooserJButton_1.fill = GridBagConstraints.BOTH;
		gbc_exporyLocationDirectoryChooserJButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_exporyLocationDirectoryChooserJButton_1.gridx = 2;
		gbc_exporyLocationDirectoryChooserJButton_1.gridy = 3;
		add(exporyLocationDirectoryChooserJButton_1, gbc_exporyLocationDirectoryChooserJButton_1);
		
		JLabel defaultKeysLocationLabel = new JLabel("Default keys location:");
		defaultKeysLocationLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_defaultKeysLocationLabel = new GridBagConstraints();
		gbc_defaultKeysLocationLabel.anchor = GridBagConstraints.WEST;
		gbc_defaultKeysLocationLabel.insets = new Insets(0, 0, 5, 5);
		gbc_defaultKeysLocationLabel.gridx = 0;
		gbc_defaultKeysLocationLabel.gridy = 4;
		add(defaultKeysLocationLabel, gbc_defaultKeysLocationLabel);
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!txtDefaultExportLocation.getText().isBlank() || !txtDefaultkeysLocation.getText().isBlank()) {
						ComboItem selected = (ComboItem) languageComboBox.getSelectedItem();
						Settings.updateSettings(true, selected.getStrValue(), txtDefaultExportLocation.getText(), txtDefaultkeysLocation.getText());
					} else {
						new ErrorDialogBox();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		txtDefaultkeysLocation = new JTextField();
		txtDefaultkeysLocation.setText(Settings.getStrParameter("keys_directory"));
		GridBagConstraints gbc_txtDefaultkeysLocation = new GridBagConstraints();
		gbc_txtDefaultkeysLocation.insets = new Insets(0, 0, 5, 5);
		gbc_txtDefaultkeysLocation.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDefaultkeysLocation.gridx = 1;
		gbc_txtDefaultkeysLocation.gridy = 4;
		add(txtDefaultkeysLocation, gbc_txtDefaultkeysLocation);
		txtDefaultkeysLocation.setColumns(10);
		
		DirectoryChooserJButton keyLocationDirectoryChooserJButton = new DirectoryChooserJButton(txtDefaultkeysLocation);
		GridBagConstraints gbc_keyLocationDirectoryChooserJButton = new GridBagConstraints();
		gbc_keyLocationDirectoryChooserJButton.fill = GridBagConstraints.BOTH;
		gbc_keyLocationDirectoryChooserJButton.insets = new Insets(0, 0, 5, 0);
		gbc_keyLocationDirectoryChooserJButton.gridx = 2;
		gbc_keyLocationDirectoryChooserJButton.gridy = 4;
		add(keyLocationDirectoryChooserJButton, gbc_keyLocationDirectoryChooserJButton);
		
		JLabel warning = new JLabel(PropertiesReader.getString("warningSettings"));
		warning.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_warning = new GridBagConstraints();
		gbc_warning.insets = new Insets(0, 0, 5, 5);
		gbc_warning.gridx = 1;
		gbc_warning.gridy = 5;
		add(warning, gbc_warning);
		GridBagConstraints gbc_saveButton = new GridBagConstraints();
		gbc_saveButton.insets = new Insets(0, 0, 0, 5);
		gbc_saveButton.gridx = 0;
		gbc_saveButton.gridy = 6;
		add(saveButton, gbc_saveButton);
	}

}
