package main.view;

import main.controller.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Desktop;
import java.awt.Image;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.imageio.ImageIO;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private CardLayout cardLayout;

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public MainFrame() throws IOException{
		//Window informations:
		setTitle("Cryptogramme");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 793, 574);
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
	    Image image = ImageIO.read(getClass().getClassLoader().getResource("resources/images/padlock.png"));
	    setIconImage(image);
		
		//Menu Bar:
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu(PropertiesReader.getString("file"));
		menuBar.add(fileMenu);
		
		JMenuItem settingsMenuItem = new JMenuItem(PropertiesReader.getString("settings"));
		settingsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPage("SettingMenu");
			}
		});
		
		//Go back to the title screen when the JMenuItem is clicked:
		JMenuItem titleScreenMenuItem = new JMenuItem(PropertiesReader.getString("titleScreen"));
		titleScreenMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPage("TitleScreen");
			}
		});
		fileMenu.add(titleScreenMenuItem);
		fileMenu.add(settingsMenuItem);
		
		//Quit the program when the quit JMenuItem is clicked:
		JMenuItem quitMenuItem = new JMenuItem(PropertiesReader.getString("quit"));
		quitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenuItem openKeysDirectory = new JMenuItem(PropertiesReader.getString("openKeysDirectory"));
		openKeysDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().open(new File(Settings.getStrParameter("keys_directory")));
				} catch (IOException e1) {
					System.out.println(PropertiesReader.getString("error")+": "+PropertiesReader.getString("failOpenKeyDirectory"));
				}

			}
		});
		fileMenu.add(openKeysDirectory);
		
		JMenuItem openExportsDirectory = new JMenuItem(PropertiesReader.getString("openExportsDirectory"));
		openExportsDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().open(new File(Settings.getStrParameter("keys_directory")));
				} catch (IOException e1) {
					System.out.println(PropertiesReader.getString("error")+": "+PropertiesReader.getString("failOpenExportDirectory"));
				}

			}
		});
		fileMenu.add(openExportsDirectory);
		fileMenu.add(quitMenuItem);
		
		JMenu cryptMenu = new JMenu(PropertiesReader.getString("encrypt"));
		menuBar.add(cryptMenu);
		
		JMenuItem newEncryptRSA = new JMenuItem(PropertiesReader.getString("encryptRSA"));
		newEncryptRSA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPage("EncryptRSAJPanel");
			}
		});
		cryptMenu.add(newEncryptRSA);
		
		JMenuItem newEncryptAES = new JMenuItem(PropertiesReader.getString("encryptAES"));
		newEncryptAES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPage("DecryptAESJPanel");
			}
		});
		cryptMenu.add(newEncryptAES);
		
		JMenu uncryptMenu = new JMenu(PropertiesReader.getString("decrypt"));
		menuBar.add(uncryptMenu);
		
		JMenuItem newDecryptRSA = new JMenuItem(PropertiesReader.getString("decryptRSA"));
		newDecryptRSA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPage("EncryptRSAJPanel");
			}
		});
		uncryptMenu.add(newDecryptRSA);
		
		JMenuItem newDecryptAES = new JMenuItem(PropertiesReader.getString("decryptAES"));
		newDecryptAES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPage("DecryptAESJPanel");
			}
		});
		uncryptMenu.add(newDecryptAES);
		
		JMenu helpMenu = new JMenu(PropertiesReader.getString("help"));
		menuBar.add(helpMenu);
		
		//When this item is clicked it load the public repository on github via the default browser:
		JMenuItem sourceMenuItem = new JMenuItem(PropertiesReader.getString("sourceCode"));
		sourceMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/AilysGeister/cryptogramme"));
				} catch (IOException e1) {
					System.out.println(PropertiesReader.getString("error")+": "+PropertiesReader.getString("failOpenRepo"));
				}
			}
		});
		helpMenu.add(sourceMenuItem);
		
		JMenuItem documentationMenuItem = new JMenuItem(PropertiesReader.getString("documentation"));
		documentationMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://ailysgeister.github.io/cryptogramme/"));
				} catch (IOException e1) {
					System.out.println(PropertiesReader.getString("error")+": "+PropertiesReader.getString("failOpenDoc"));
				}
			}
		});
		
		helpMenu.add(documentationMenuItem);
		JMenuItem tutorialMenuItem = new JMenuItem(PropertiesReader.getString("tutorials"));
		tutorialMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://www.youtube.com/playlist?list=PLmaPrCz1uPNNV1YovH_GiaoEVQfG54Cfr"));
				} catch (IOException e1) {
					System.out.println(PropertiesReader.getString("error")+": "+PropertiesReader.getString("failOpenTuto"));
				}
			}
		});
		helpMenu.add(tutorialMenuItem);

		/*
		 * Page Navigator:
		 */
		contentPane = new JPanel();
        cardLayout = new CardLayout();
        contentPane.setLayout(cardLayout);
        setContentPane(contentPane);
		
		//All the "sub-page" as JPanel:
		contentPane.add(new TitleScreen(this), "TitleScreen");
        contentPane.add(new SettingsMenu(this), "SettingMenu");
        contentPane.add(new CryptRSAJPanel(this, "encrypt"), "EncryptRSAJPanel");
        contentPane.add(new CryptRSAJPanel(this, "decrypt"), "DecryptRSAJPanel");
        contentPane.add(new CryptAESJPanel(this, "encrypt"), "EncryptAESJPanel");
        contentPane.add(new CryptAESJPanel(this, "decrypt"), "DecryptAESJPanel");
        contentPane.add(new GenerateAsynchKeyJPanel(this), "GenerateKeyRSA");
        contentPane.add(new GenerateAESKeyJPanel(this), "GenerateKeyAES");
        
        cardLayout.show(contentPane, "TitleScreen");
	}
	
	/**
     * Classic show page without extra parameters.
     * @param name
     */
    public void showPage(String name) {
        cardLayout.show(contentPane, name);
    }

}
