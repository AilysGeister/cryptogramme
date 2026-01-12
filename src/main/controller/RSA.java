package main.controller;
import main.model.*;
import main.view.*;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Date;

import javax.swing.JDialog;

/**
 * Controller for the RSA usage.
 */
public class RSA {
	
	/**
	 * Generate RSA Keys.
	 * @param panel	View
	 * @param keySize Size of the key.
	 */
	public static void generateKeys(GenerateAsynchKeyJPanel panel, int keySize) {
		//Initialization:
		String n, e, d = new String();
		KeyRSA key;
		
		//Generate the key:
		key = new KeyRSA(keySize);
		panel.setKey(key);
		n = key.getHexFormatted(key.getN());
		e = key.getHexFormatted(key.getE());
		d = key.getHexFormatted(key.getD());
		
		//Show the public key:
		panel.keyNPublicJTextArea.setText(n);
		panel.keyEPublicJTextArea.setText(e);
		
		//Show the private key:
		panel.keyNPrivateJTextArea.setText(n);
		panel.keyDPrivateJTextArea.setText(d);
		
		//Enable the exports buttons:
		panel.savePrivateJButton.setEnabled(true);
		panel.savePublicJButton.setEnabled(true);
		
		//Enable the show complete key button:
		panel.showJButton.setEnabled(true);
	}
	
	/**
	 * Show the complete key to the user.
	 * @param panel View
	 */
	public static void showCompleteKey(GenerateAsynchKeyJPanel panel) {
		//Initialization:
		KeyRSA key = panel.getKey();
		
		//Open the dialog bow with the complete key:
		try {
			CompleteKeyRSA dialog = new CompleteKeyRSA(panel.getMainFrame(), key);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Save the RSA key in a file.
	 * @param panel View
	 * @param mode 0 public key - 1 private key
	 */
	public static void saveKey(GenerateAsynchKeyJPanel panel, int mode) {
		try {
			String location = panel.getKey().save(mode);
			panel.returnMsgJLabel.setForeground(new Color(0, 175, 0));
			panel.returnMsgJLabel.setText(PropertiesReader.getString("successSaveKey")+location+" !");
		} catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			panel.returnMsgJLabel.setForeground(new Color(175, 0, 0));
			panel.returnMsgJLabel.setText(PropertiesReader.getString("faileSaveKey"));
		}
	}
	
	/**
	 * Validate the given key correspond to usage, if it the case change the UI.
	 * @param panel View
	 * @param pemPath Location of the saved key.
	 * @return
	 */
	public static void validateKeyButton(CryptRSAJPanel panel, Path pemPath, String type) {
		//Initialization:
		int mode = -1;
		boolean isValid = false, isKey = false, isPrivateKey = false, isPublicKey = false;
		
		//First we verifying that the JTextField isn't blank:
		if (panel.keyLocationJTextField.getText().isBlank()) {
	        showError(panel, "fieldBlank");
	    
	    //Then we verifying that the given past link to a file:
	    } else if (pemPath == null || !Files.exists(pemPath)) {
	        showError(panel, "keyInvalid");
	        
	    //Finally we verifying that the content of the file is a private or complete RSA key:
	    } else {
	    	//Test the type of key:
	        isPublicKey   = KeyRSA.isPublicKeyPEM(pemPath);
	        isPrivateKey  = KeyRSA.isPrivateKeyPEM(pemPath);

	        //Verifictaion in function of the operation type:
	        if (type.equals("encrypt")) {
	            isKey = isPublicKey;
	        } else if (type.equals("decrypt")) {
	            isKey = isPrivateKey;
	        }

	        //Test if the key is valid for the usage:
	        if (!isKey) {
	            showError(panel, "keyInvalid");
	        } else {
	            isValid = true;
	        }
	    }

	    if (isValid) {
	    	//Create the key:
		    try {
		    	if (isPublicKey) mode = 0;
		    	if (isPrivateKey) mode = 1;
		    	panel.setKey(new KeyRSA(pemPath, mode));
		    } catch (Exception e) {
		        return;
		    }
		    
	    	//Disable the keyJPanel:
		    panel.keyLocationJTextField.setEnabled(false);
		    panel.keyFileChooserJButton.setEnabled(false);
		    panel.validateKeyJButton.setEnabled(false);

		    //Enable the decryptJPanel:
		    panel.savedMessageFileChooserJButton.setEnabled(true);
		    panel.filePathJTextField.setEnabled(true);
		    panel.cryptJTextArea.setEnabled(true);
		    panel.cryptJButton.setEnabled(true);
		    panel.maxSizeJLabel.setText(""+panel.getKey().getSize());

		    //Print success in returnMsgJlabel
		    panel.returnMsgJLabel.setForeground(new Color(0, 175, 0));
		    panel.returnMsgJLabel.setText(PropertiesReader.getString("fill_"+type));
	    }
	}
	
	/**
	 * Encrypt a message with the classic RSA algorithms without padding.
	 * @param key Public RSA key used to encrypt the message.
	 * @param content Message to encrypt
	 * @return The crypted message
	 */
	public static String encrypt(KeyRSA publicKey, String content) {
		//Initialization:
		BigInteger c, m;
		
		//Convert the content in ASCII:
		byte[] ascii = content.getBytes(StandardCharsets.UTF_8);
		
		//Convert bytes list to BigInteger:
		m = new BigInteger(1, ascii);
		
		//Classic RSA encryption without padding:
		c = m.modPow(publicKey.getE(), publicKey.getN());
		
		//Encode with bade 64:
		return Base64.getEncoder().encodeToString(c.toByteArray());
	}
	
	/**
	 * Decrypt a message with the classic RSA algorithms without padding.
	 * @param privateKey Private RSA key used to decrypt the message.
	 * @param crypt Message to decrypt
	 * @return The original message
	 */
	public static String decrypt(KeyRSA privateKey, String crypt) {
		//Initialization:
		BigInteger c, m;
		
		//Convert the crypt into bytes list which correspond the ASCII code:
		byte[] bytes = Base64.getDecoder().decode(crypt);
		c = new BigInteger(1, bytes);
		
		//Classic RSA decryption without padding:
		m = c.modPow(privateKey.getD(), privateKey.getN());
		
		//Convert the BigInteger into bytes:
	    byte[] messageBytes = m.toByteArray();
		
		return new String(messageBytes, StandardCharsets.UTF_8);
	}
	
	/**
	 * Encrypt or decrypt the message in the cryptJTextArea and return the result in the resultJTextArea.
	 * @param panel View
	 * @param type "encrypt" or "decrypt"
	 */
	public static void cryptButton(CryptRSAJPanel panel, String type) {
		//Initialization:
		String result = new String(), message = new String();
		boolean isValid = false;
		
		//The import file is taken in priotary:
		if (!panel.filePathJTextField.getText().isBlank()) {
			
			//If the fiels isn't blank we verify that the file exist:
			File inputFile = new File(panel.filePathJTextField.getText());
			if (inputFile.exists() && !inputFile.isDirectory()) {
				
				//If it is good we fill the string message with the content of the file:
				try {
					message = Files.readString(Path.of(panel.filePathJTextField.getText()));
					
					//Verify the lentgh:
					if (message.length() > panel.getKey().getSize()) {
						showError(panel,"tooManyChar");
					} else {
						isValid = true;
					}
				} catch (IOException e) {
					showError(panel,"failLoadFile");
					e.printStackTrace();
				}
			} else {
				//If the file text field isn't valid send error:
				showError(panel,"invalidFile");
			}
		} else {
			//If the text area and the text field are empty return error:
			if (panel.cryptJTextArea.getText().isBlank()) {
				showError(panel,"fieldBlank");
			} else {
				message = panel.cryptJTextArea.getText();
				isValid = true;
			}
		}
		
		if (isValid) {
			//Encrypt or Decrypt in function of type:
			if (type.equals("encrypt")) result = encrypt(panel.getKey(), message);
			if (type.equals("decrypt")) result = decrypt(panel.getKey(), message);
			
			//Disable the encrypt panel:
			panel.filePathJTextField.setEnabled(false);
			panel.filePathJTextField.setText("");
			panel.savedMessageFileChooserJButton.setEnabled(false);
			panel.cryptJTextArea.setEnabled(false);
			panel.cryptJTextArea.setText("");
			panel.cryptJButton.setEnabled(false);
			
			//Enable the result panel:
			panel.resultJTextArea.setEnabled(true);
			panel.resultJTextArea.setText(result);
			panel.resetJButton.setEnabled(true);
			panel.saveJButton.setEnabled(true);
			
			//Return msg:
			panel.returnMsgJLabel.setForeground(new Color(0, 175, 0));
			panel.returnMsgJLabel.setText(PropertiesReader.getString(type+"RSADone"));
		}
	}
	
	/**
	 * Save the result of a RSA encryption or decryption in a file.
	 * @param panel View
	 * @param isCrypted Boolean to know if we work with a crypted message.
	 */
	public static void saveMessageButton(CryptRSAJPanel panel, boolean isCrypted) {
		try {
			//Save the file:
			panel.returnMsgJLabel.setForeground(new Color(0, 175, 0));
			panel.returnMsgJLabel.setText(PropertiesReader.getString("successSaveMessage")+saveMessage(panel.resultJTextArea.getText(), isCrypted)+" !");
		} catch (IOException e) {
			panel.returnMsgJLabel.setForeground(new Color(175, 0, 0));
			panel.returnMsgJLabel.setText(PropertiesReader.getString("failSave"));
			e.printStackTrace();
		}
	}
	
	/**
	 * Save the message (encrypt or decrypt) in a .txt file.
	 * @param content Message to save in a file.
	 * @param isCrypted Boolean to know if we work with a crypted message.
	 * @return The filename where the message as been saved.
	 * @throws IOException 
	 */
	private static String saveMessage(String content, boolean isCrypted) throws IOException {
		//Initialization:
		String directory;
		directory = Settings.getStrParameter("export_directory");
		StringBuilder fileName = fileName(directory, isCrypted);
		
		//Write the file:
		FileWriter writer = new FileWriter(fileName.toString());
		writer.write(content);
		writer.close();
			
		//Return the file name for return JLabel:
		return fileName.toString();	
	}
	
	/**
	 * Reset the CryptRSAJPanel view.
	 * @param panel View
	 */
	public static void resetCrypt(CryptRSAJPanel panel) {
		//Reset key:
		panel.setKey(null);
		
		//Disable & reset result panel:
		panel.resultJTextArea.setEnabled(false);
		panel.resultJTextArea.setText("");
		panel.resetJButton.setEnabled(false);
		panel.saveJButton.setEnabled(false);
		
		//Reset crypt panel:
		panel.filePathJTextField.setEnabled(false);
		panel.filePathJTextField.setText("");
		panel.savedMessageFileChooserJButton.setEnabled(false);
		panel.cryptJTextArea.setText("");
		panel.maxSizeJLabel.setText(PropertiesReader.getString("enterKey"));
		
		//Enable & reset key panel:
		panel.keyLocationJTextField.setEnabled(true);
		panel.keyFileChooserJButton.setEnabled(true);
		panel.validateKeyJButton.setEnabled(true);
		
		//Return msg:
		panel.returnMsgJLabel.setForeground(new Color(0, 175, 0));
		panel.returnMsgJLabel.setText(PropertiesReader.getString("firstValidateKey"));
	}
	
	/**
	 * Generate the saved messages file name.
	 * @param directory Directory where the messages are saved.
	 * @param isCrypted Boolean to know if we work with a crypted message.
	 * @return file name
	 */
	private static StringBuilder fileName(String directory, boolean isCrypted) {
		//Initialization:
		StringBuilder fileName = new StringBuilder(directory);
		Date time = new Date();
		String temp = time.toString();
		
		//Get the date of the generation and replace non-allowed characters by '-':
	    for (int i = 0; i < temp.length(); i++) {
	        char c = temp.charAt(i);
	        if (c == ':' || c == ' ') {
	            fileName.append('-');
	        } else {
	            fileName.append(c);
	        }
	    }
	    
	    //Depends of the case we change the end of the file name:
	    if (isCrypted) {
	    	fileName.append("_CRYPTED.txt");
	    } else {
	    	fileName.append("_UNCRYPTED.txt");
	    }
	    
	    return fileName;
	}
	
	/**
	 * Print the error message in the returnMsgJLabel.
	 * @param panel View
	 * @param key String key for localization.
	 */
	private static void showError(CryptRSAJPanel panel, String key) {
	    panel.returnMsgJLabel.setForeground(new Color(175, 0, 0));
	    panel.returnMsgJLabel.setText(PropertiesReader.getString("error") +": "+PropertiesReader.getString(key)
	    );
	}
}
