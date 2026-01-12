package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.controller.PropertiesReader;
import main.model.KeyRSA;
import main.view.components.SaveJButton;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JTextArea;

public class CompleteKeyRSA extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public CompleteKeyRSA(MainFrame mainframe, KeyRSA key) {
		setTitle("RSA: "+PropertiesReader.getString("completeKey"));
		setIconImage(new ImageIcon("resources/images/key.png").getImage());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 720);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel pJLabel = new JLabel("P:");
			pJLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			GridBagConstraints gbc_pJLabel = new GridBagConstraints();
			gbc_pJLabel.anchor = GridBagConstraints.EAST;
			gbc_pJLabel.insets = new Insets(0, 0, 5, 5);
			gbc_pJLabel.gridx = 0;
			gbc_pJLabel.gridy = 0;
			contentPanel.add(pJLabel, gbc_pJLabel);
		}
		{
			JTextArea pJTextArea = new JTextArea();
			pJTextArea.setLineWrap(true);
			pJTextArea.setWrapStyleWord(true);
			pJTextArea.setEditable(false);
			pJTextArea.setText(key.getHexFormatted(key.getP()));
			GridBagConstraints gbc_pJTextArea = new GridBagConstraints();
			gbc_pJTextArea.insets = new Insets(0, 0, 5, 0);
			gbc_pJTextArea.fill = GridBagConstraints.BOTH;
			gbc_pJTextArea.gridx = 1;
			gbc_pJTextArea.gridy = 0;
			contentPanel.add(pJTextArea, gbc_pJTextArea);
		}
		{
			JLabel qJLabel = new JLabel("Q:");
			qJLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			GridBagConstraints gbc_qJLabel = new GridBagConstraints();
			gbc_qJLabel.insets = new Insets(0, 0, 5, 5);
			gbc_qJLabel.gridx = 0;
			gbc_qJLabel.gridy = 1;
			contentPanel.add(qJLabel, gbc_qJLabel);
		}
		{
			JTextArea qJTextArea = new JTextArea();
			qJTextArea.setWrapStyleWord(true);
			qJTextArea.setLineWrap(true);
			qJTextArea.setEditable(false);
			qJTextArea.setText(key.getHexFormatted(key.getQ()));
			GridBagConstraints gbc_qJTextArea = new GridBagConstraints();
			gbc_qJTextArea.insets = new Insets(0, 0, 5, 0);
			gbc_qJTextArea.fill = GridBagConstraints.BOTH;
			gbc_qJTextArea.gridx = 1;
			gbc_qJTextArea.gridy = 1;
			contentPanel.add(qJTextArea, gbc_qJTextArea);
		}
		{
			JLabel NJLabel = new JLabel("N:");
			NJLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			GridBagConstraints gbc_NJLabel = new GridBagConstraints();
			gbc_NJLabel.insets = new Insets(0, 0, 5, 5);
			gbc_NJLabel.gridx = 0;
			gbc_NJLabel.gridy = 2;
			contentPanel.add(NJLabel, gbc_NJLabel);
		}
		{
			JTextArea nJTextArea = new JTextArea();
			nJTextArea.setWrapStyleWord(true);
			nJTextArea.setLineWrap(true);
			nJTextArea.setEditable(false);
			nJTextArea.setText(key.getHexFormatted(key.getN()));
			GridBagConstraints gbc_nJTextArea = new GridBagConstraints();
			gbc_nJTextArea.insets = new Insets(0, 0, 5, 0);
			gbc_nJTextArea.fill = GridBagConstraints.BOTH;
			gbc_nJTextArea.gridx = 1;
			gbc_nJTextArea.gridy = 2;
			contentPanel.add(nJTextArea, gbc_nJTextArea);
		}
		{
			JLabel PhiLabel = new JLabel("Phi:");
			PhiLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			GridBagConstraints gbc_PhiLabel = new GridBagConstraints();
			gbc_PhiLabel.insets = new Insets(0, 0, 5, 5);
			gbc_PhiLabel.gridx = 0;
			gbc_PhiLabel.gridy = 3;
			contentPanel.add(PhiLabel, gbc_PhiLabel);
		}
		{
			JTextArea phiJTextArea = new JTextArea();
			phiJTextArea.setWrapStyleWord(true);
			phiJTextArea.setLineWrap(true);
			phiJTextArea.setEditable(false);
			phiJTextArea.setText(key.getHexFormatted(key.getPhi()));
			GridBagConstraints gbc_phiJTextArea = new GridBagConstraints();
			gbc_phiJTextArea.insets = new Insets(0, 0, 5, 0);
			gbc_phiJTextArea.fill = GridBagConstraints.BOTH;
			gbc_phiJTextArea.gridx = 1;
			gbc_phiJTextArea.gridy = 3;
			contentPanel.add(phiJTextArea, gbc_phiJTextArea);
		}
		{
			JLabel dJLabel = new JLabel("D:");
			dJLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			GridBagConstraints gbc_dJLabel = new GridBagConstraints();
			gbc_dJLabel.insets = new Insets(0, 0, 5, 5);
			gbc_dJLabel.gridx = 0;
			gbc_dJLabel.gridy = 4;
			contentPanel.add(dJLabel, gbc_dJLabel);
		}
		{
			JTextArea dJTextArea = new JTextArea();
			dJTextArea.setWrapStyleWord(true);
			dJTextArea.setLineWrap(true);
			dJTextArea.setEditable(false);
			dJTextArea.setText(key.getHexFormatted(key.getD()));
			GridBagConstraints gbc_dJTextArea = new GridBagConstraints();
			gbc_dJTextArea.insets = new Insets(0, 0, 5, 0);
			gbc_dJTextArea.fill = GridBagConstraints.BOTH;
			gbc_dJTextArea.gridx = 1;
			gbc_dJTextArea.gridy = 4;
			contentPanel.add(dJTextArea, gbc_dJTextArea);
		}
		{
			JLabel EJLabel = new JLabel("E:");
			EJLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			GridBagConstraints gbc_EJLabel = new GridBagConstraints();
			gbc_EJLabel.insets = new Insets(0, 0, 0, 5);
			gbc_EJLabel.gridx = 0;
			gbc_EJLabel.gridy = 5;
			contentPanel.add(EJLabel, gbc_EJLabel);
		}
		{
			JTextArea eJTextArea = new JTextArea();
			eJTextArea.setWrapStyleWord(true);
			eJTextArea.setLineWrap(true);
			eJTextArea.setEditable(false);
			eJTextArea.setText(key.getHexFormatted(key.getE()));
			GridBagConstraints gbc_eJTextArea = new GridBagConstraints();
			gbc_eJTextArea.fill = GridBagConstraints.BOTH;
			gbc_eJTextArea.gridx = 1;
			gbc_eJTextArea.gridy = 5;
			contentPanel.add(eJTextArea, gbc_eJTextArea);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				
				JLabel returnMsgJLabel = new JLabel("");
				returnMsgJLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
				buttonPane.add(returnMsgJLabel);
				
				SaveJButton saveButton = new SaveJButton();
				saveButton.setToolTipText(PropertiesReader.getString("saveToolTip"));
				saveButton.addActionListener(e -> {
						try {
							String location = key.save(2);
							returnMsgJLabel.setForeground(new Color(0,175,0));
							returnMsgJLabel.setText(PropertiesReader.getString("successSaveKey")+location+" !");
						} catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e1) {
							e1.printStackTrace();
							returnMsgJLabel.setForeground(new Color(175,0,0));
							returnMsgJLabel.setText(PropertiesReader.getString("faileSaveKey"));
						}
					}
				);
			
				buttonPane.add(saveButton);
			}
			{
				JButton closeButton = new JButton(PropertiesReader.getString("close"));
				closeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(closeButton);
			}
		}
	}

}
