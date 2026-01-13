package main.controller;

import main.model.KeyAES;
import main.view.CryptAESJPanel;
import main.view.GenerateAESKeyJPanel;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JLabel;
import java.awt.Color;

/**
 * Controller for the AES usage.
 */
public class AES {
		
	public static void encrypt(KeyAES key, File inputFile, File outputFile)
		throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		    
		//Initialization:
		byte[] buffer = new byte[64];
		int bytesRead;
		
        //Configure cipher:
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, key.getSecretKey(), key.getIv());
		
		//Opening the files:
		FileInputStream inputStream = new FileInputStream(inputFile);
		FileOutputStream outputStream = new FileOutputStream(outputFile);
		
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			byte[] output = cipher.update(buffer, 0, bytesRead);
			if (output != null) {
				outputStream.write(output);
			}
		}
		byte[] outputBytes = cipher.doFinal();
	    if (outputBytes != null) {
	        outputStream.write(outputBytes);
	    }
	    
	    //Clos the files:
	    inputStream.close();
	    outputStream.close();
	}
	
	
	public static void decrypt(KeyAES key, File inputFile, File outputFile)
		throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

		//Initialization:
		byte[] buffer = new byte[64];
		int bytesRead;
				
		//Configure cipher:
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, key.getSecretKey(), key.getIv());
		
		//Opening the files:
		FileInputStream inputStream = new FileInputStream(inputFile);
		FileOutputStream outputStream = new FileOutputStream(outputFile);
		
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			byte[] output = cipher.update(buffer, 0, bytesRead);
			if (output != null) {
				outputStream.write(output);
			}
		}

		// Vérifie l’intégrité (tag GCM)
		byte[] outputBytes = cipher.doFinal();
		if (outputBytes != null) {
			outputStream.write(outputBytes);
		}

		//Close the files:
		inputStream.close();
		outputStream.close();
	}
	
	/**
	 * Genrate a AES key and show it.
	 * @param panel View
	 * @param keySize Size of the key to generate
	 */
	public static void generateKey(GenerateAESKeyJPanel panel, int keySize) {
		try {
			//Generate the key:
			KeyAES key = new KeyAES(keySize);
			panel.setKey(key);
			
			//Print the key:
			panel.keyJTextField.setText(Base64.getEncoder().encodeToString(key.getSecretKey().getEncoded()));
			
			//Enable the resultJLabel:
			panel.keyJTextField.setEnabled(true);
			panel.saveJButton.setEnabled(true);
			
			//Show success message:
			panel.returnMsgJLabel.setForeground(new Color(0, 175, 0));
			panel.returnMsgJLabel.setText(PropertiesReader.getString("successAESKey"));
		} catch (NoSuchAlgorithmException e) {
			//In case of error show it in the returnMsgJLabel:
			showError(panel.returnMsgJLabel, "failedGenerateAESKey");
		}
	}
	
	/***
	 * Save the generate AES key.
	 * @param panel
	 */
	public static void saveKey(GenerateAESKeyJPanel panel) {
		try {
			panel.returnMsgJLabel.setForeground(new Color(0, 175, 0));
			panel.returnMsgJLabel.setText(PropertiesReader.getString("successSaveKey")+panel.getKey().save()+" !");
		} catch (IOException e) {
			showError(panel.returnMsgJLabel,"failSaveKey");
			e.printStackTrace();
		}
	}
	
	public static void cryptButton(CryptAESJPanel panel, String type) throws IOException {
		//Initialization:
		KeyAES key;
		String keyLocation = panel.keyLocationJTextField.getText(), inputFileLocation = panel.fileLocationJTextField.getText(), outputPath;
		File inputFile = new File(inputFileLocation);
		boolean isValid = false;
		
		//First we verifying the inputs:
		if (KeyAES.isValidKeyFile(keyLocation)) {
			if (inputFile.exists() && !inputFile.isDirectory()) {
				//If the input are valid we can worl on the file:
				isValid = true;
			} else {
				showError(panel.returnMsgJLabel,"");
			}
		} else {
			showError(panel.returnMsgJLabel,"keyInvalid");
		}
		
		//Once the validation is done:
		if (isValid) {
			try {
				//Creat the key:
				key = KeyAES.load(keyLocation);
				
				//Choose the good operation:
				switch (type) {
					case "encrypt":
						try {
							//Creating output file:
							outputPath = fileName(Settings.getStrParameter("export_directory")).toString();
							File outputFile = new File(outputPath);
							
							//Encrypt the file:
							encrypt(key, inputFile, outputFile);
							
							//Return message:
							panel.returnMsgJLabel.setForeground(new Color(0,175,0));
							panel.returnMsgJLabel.setText(PropertiesReader.getString("successSaveFile")+outputPath+" !");
						} catch (InvalidKeyException | NoSuchPaddingException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
							showError(panel.returnMsgJLabel,"failEncryptAES");
							e.printStackTrace();
						}
						break;
						
					case "decrypt":
						try {
							//Creating output file:
							outputPath = Settings.getStrParameter("export_directory")+panel.outputFileJTextField.getText();
							File outputFile = new File(outputPath);
							
							//decrypt the file:
							decrypt(key, inputFile, outputFile);
							
							//Return message:
							panel.returnMsgJLabel.setForeground(new Color(0,175,0));
							panel.returnMsgJLabel.setText(PropertiesReader.getString("successSaveFile")+outputPath+" !");
						} catch (InvalidKeyException | NoSuchPaddingException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
							showError(panel.returnMsgJLabel,"failDecryptAES");
							e.printStackTrace();
						}
						break;
				}
			} catch (NoSuchAlgorithmException | IOException e) {
				showError(panel.returnMsgJLabel,"loadKeyFail");
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Generate the saved messages file name.
	 * @param directory Directory where the messages are saved.
	 * @param isCrypted Boolean to know if we work with a crypted message.
	 * @return file name
	 */
	private static StringBuilder fileName(String directory) {
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
	    
	    fileName.append("_CRYPTED");
	    
	    return fileName;
	}
	
	/**
	 * Show an error in the returnMsgJLabel
	 * @param returnMsgJLabel
	 * @param strKey
	 */
	private static void showError(JLabel returnMsgJLabel, String strKey) {
		returnMsgJLabel.setForeground(new Color(175, 0, 0));
		returnMsgJLabel.setText(PropertiesReader.getString("error")+": "+PropertiesReader.getString(strKey));
	}
}
