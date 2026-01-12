package main.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import main.controller.Settings;

public class KeyAES {
	
	//Attributes:
	private SecretKey secretKey;
	private GCMParameterSpec iv;
	
	//Constructors:
	/**
	 * Generate a AES key and IV with a given size.
	 * @param keySize
	 * @throws NoSuchAlgorithmException 
	 */
	public KeyAES (int keySize) throws NoSuchAlgorithmException {
		//Generate the AES key:
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
	    keyGenerator.init(keySize);
	    this.secretKey = keyGenerator.generateKey();
	    
	    //Generate the initialization vector:
	  	byte[] ivBytes = new byte[12];
	  	SecureRandom.getInstanceStrong().nextBytes(ivBytes);
		this.iv = new GCMParameterSpec(128, ivBytes);
	}
	
	public KeyAES(SecretKey secretKey, GCMParameterSpec iv) {
		this.secretKey = secretKey;
		this.iv = iv;
	}
	
	//METHODS:
	public SecretKey getSecretKey() {
		return this.secretKey;
	}
	
	/**
	 * @return The initialization Vector
	 */
	public GCMParameterSpec getIv() {
		return this.iv;
	}
	
	public String save() throws IOException {
		//Initialization:
		String fileName = fileName(Settings.getStrParameter("keys_directory")).toString();
		byte[] keyBytes, ivBytes;
		
		//Convert the key in byte array:
		keyBytes = this.secretKey.getEncoded();
		ivBytes = this.iv.getIV();
		
		//Save the key in a file:
		try (FileOutputStream  file = new FileOutputStream(fileName)) {
			//Save the SecretKey:
			file.write(keyBytes.length);
			file.write(keyBytes);
			
			//Save the IV:
			file.write(ivBytes.length);
			file.write(ivBytes);
		}
		
		return fileName;
	}
	
	/**
	 * Generate a AES key from a saved file from the save() method.
	 * @param filePath Path of the saved key.
	 * @return KeyAES object
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static KeyAES load(String filePath) throws IOException, NoSuchAlgorithmException {
	    try (FileInputStream file = new FileInputStream(filePath)) {
	    	//Read the SecretKey:
	        int keyLength = file.read();
	        byte[] keyBytes = file.readNBytes(keyLength);

	        //Read the initialization vector:
	        int ivLength = file.read();
	        byte[] ivBytes = file.readNBytes(ivLength);

	        //Create the key:
	        return new KeyAES(new SecretKeySpec(keyBytes, "AES"), new GCMParameterSpec(128, ivBytes));
	    }
	}
	
	/**
	 * Verify if a file correspond to a KeyAES object.
	 * @param filePath Location of the saved AES key.
	 * @return True the key is valid - False not a KeyAES object
	 */
	public static boolean isValidKeyFile(String filePath) {
        try (FileInputStream file = new FileInputStream(filePath)) {
            //Read the key's length:
            int keyLength = file.read();
            if (keyLength <= 0) return false;

            //Read the key bytes:
            byte[] keyBytes = file.readNBytes(keyLength);
            if (keyBytes.length != keyLength) return false;

            //Read the initialization vector's length:
            int ivLength = file.read();
            if (ivLength <= 0) return false;

            //Read the initialization vector:
            byte[] ivBytes = file.readNBytes(ivLength);
            if (ivBytes.length != ivLength) return false;

            //Try to creat the KeyAES attributes:
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
            GCMParameterSpec iv = new GCMParameterSpec(128, ivBytes);

            //If the creation failes it return false, otherwise it return true:
            return secretKey.getEncoded() != null && iv.getIV() != null;

        } catch (IOException e) {
            return false;
        }
    }
	
	/**
	 * Generate the file name of the saved key.
	 * @param directory Directory where the key is saved.
	 * @param mode 0 Complete Key - 1: Public Key - 2 Private Key
	 * @return file name
	 */
	private StringBuilder fileName(String directory) {
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
	    
	    //Write the rest of the name:
	    fileName.append("_AES_KEY");
	    
	    return fileName;
	}
}
