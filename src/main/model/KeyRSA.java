package main.model;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import main.controller.Settings;

/**
 * RSA keys used in the project following the classic definition.
 */
public class KeyRSA {
	
	//Attributes:
	private BigInteger p, q, n, phi, d, e = BigInteger.valueOf(65537); //We use BigInteger to have very long integer values, that the pillar of the RSA encryption.
	//We used e=65537 as standard value because is prime of Fermat (2^16 + 1), and it prevent from the Håstad's attack.
	//The value 65537 is recommended by OpenSSL.
	
	//Constructors:
	/**
	* Generate the secured keys used for the RSA algorithms.
	* @param size Size of the key to generate.
	*/
	public KeyRSA(int size) {
		//Initialization:
		BigInteger[] pq;
		double n, d;
		
		do {
			//Compute P and Q as primes:
			pq = this.generatePrimes(size);
			this.p = pq[0];
			this.q = pq[1];
			
			//Compute N as P*Q:
			this.n = this.p.multiply(this.q);
			
			//Compute Phi(N) as (P-1)*(Q-1):
			this.phi = (this.p.subtract(BigInteger.ONE)).multiply(this.q.subtract(BigInteger.ONE));
			
			//Generate E as a prime of Phi:
			if (!this.e.gcd(this.phi).equals(BigInteger.ONE)) {
				//If E is not prime with Phi we generate a short test n to force the loop to iterate again.
				n=1;
				d=0;
			} else {
				//Generate D as E*D = 1 [N]:
				this.d = this.e.modInverse(this.phi);
				
				//Generate the n and d used to test the Wienner's attack.
				n = this.n.doubleValue();
				d = this.d.doubleValue();
			}
			
		} while (d<0.33*Math.pow(n, 0.25)); //To prevent the Wiener's attack, D must be superior to (1/3)*n^(1/4)
	}
	
	/**
	 * Generate public or private key from hexadecimal format.
	 * @param modulus Public modulus N
	 * @param exponent Exponent E for public keys and invers modular D for private keys.
	 * @param mode 0 Public key - 1 Private key
	 */
	public KeyRSA(String modulus, String hexParam, int mode) {
		//Initialization:
		modulus = modulus.replaceAll("\\s+", "");
		hexParam = hexParam.replaceAll("\\s+", "");
		
		//Modulus is the same for each key:
		this.n = new BigInteger(modulus, 16);
		
		//Get private or public key:
		switch (mode) {
			//Public key:
			case 0:
				this.e = new BigInteger(hexParam, 16);
				break;
			
			//Private key:
			case 1:
				this.d = new BigInteger(hexParam, 16);
				break;
		}
	}
	
	/**
	 * Generate a RSA key from a PEM file.
	 * @param Path Location of the saved key.
	 * @param mode 0 Pblic Key - 1 Private Key
	 */
	public KeyRSA(Path pemPath, int mode) {
        try {
        	//Initialization:
			String pem = Files.readString(pemPath);
			
			//Clear the headers:
			String key = pem.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").replace("-----BEGIN COMPLETE KEY-----", "").replace("-----END COMPLETE KEY-----", "").replaceAll("\\s", "");
			
			//Get the base 64 encoded
			byte[] base64 = Base64.getDecoder().decode(key);
			
			//Get the mode:
			switch (mode) {
				//Public key:
				case 0:
					//Generate the public RSA key:
				    X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(base64);
				    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				    PublicKey publicKey = keyFactory.generatePublic(publicSpec);
				    RSAPublicKey rsaKey = (RSAPublicKey) publicKey;

				    //Fill the attributes:
				    this.n = rsaKey.getModulus();
				    this.e = rsaKey.getPublicExponent();
					break;
					
				//Private key:
				case 1:
					//Get the private key spec:
					PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(base64);
			        KeyFactory keyPrivateFactory = KeyFactory.getInstance("RSA");
			        PrivateKey privateKey = keyPrivateFactory.generatePrivate(privateSpec);
			        RSAPrivateCrtKey rsaPrivateKey = (RSAPrivateCrtKey) privateKey;
			        
			        //Fill the attributes:
			        this.p = rsaPrivateKey.getPrimeP();
			        this.q = rsaPrivateKey.getPrimeQ();
			        this.n = rsaPrivateKey.getModulus();
			        this.phi = (this.p.subtract(BigInteger.ONE)).multiply(this.q.subtract(BigInteger.ONE));
			        this.d = rsaPrivateKey.getPrivateExponent();
			        this.e = rsaPrivateKey.getPublicExponent();
					break;
			}
		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
	}
	
	//Methods:
	
	/**
	 * Return the P value.
	 * @return
	 */
	public BigInteger getP() {
		return this.p;
	}
	
	/**
	 * Return the Q value.
	 * @return
	 */
	public BigInteger getQ() {
		return this.q;
	}
	
	/**
	 * Return the N value.
	 * @return
	 */
	public BigInteger getN() {
		return this.n;
	}
	
	/**
	 * Return the Phi value.
	 * @return
	 */
	public BigInteger getPhi() {
		return this.phi;
	}
	
	/**
	 * Return the E value.
	 * @return
	 */
	public BigInteger getE() {
		return this.e;
	}
	
	/**
	 * Return the D value.
	 * @return
	 */
	public BigInteger getD() {
		return this.d;
	}
	
	/**
	 * @param value The value we want to convert.
	 * @return The value in hexadecimal.
	 */
	public String getHex(BigInteger value) {
	    return value.toString(16).toUpperCase();
	}
	
	/**
	 * @param value The value we want to convert.
	 * @return The value in hexadecimal for a view.
	 */
	public String getHexFormatted(BigInteger value) {
	    String hex = value.toString(16).toUpperCase();
	    return hex.replaceAll("(.{4})", "$1 ");
	}
	
	/**
	 * @return The size of the key.
	 */
	public int getSize() {
		return this.n.bitLength();
	}	
	
	/**
	 * Save the key in a file.
	 * @param mode 0 Public Key - 1 Private Key - 2 Complete Key
	 * @return Location of the saved key.
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	public String save(int mode) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
		//Initialization:
		String directory = Settings.getStrParameter("keys_directory");
		
		//Use the asked mode:
		switch(mode) {
			default:
				return null;
			case 0:
				return savePublic(directory);
			case 1:
			try {
				return savePrivate(directory);
			} catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
				e.printStackTrace();
			}
			case 2:
				return saveComplete(directory);
		}
	}
	
	/**
	 * Save the public key in a standard PEM & Base64 format.
	 * @param directory Directory where keys are saved.
	 * @return Location of th saved key.
	 * @throws IOException
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 */
	public String savePublic(String directory) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
		//Initialization:
		StringBuilder fileName = fileName(directory, 0);
	    
		//Create RSA public key specification:
		RSAPublicKeySpec spec = new RSAPublicKeySpec(this.getN(), this.getE());
		 
		//Generate PublicKey object:
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(spec);

        //Get DER-encoded bytes (X.509 SPKI format):
        byte[] derEncoded = publicKey.getEncoded();
         
        //Base64-encode the DER bytes:
        String base64Encoded = Base64.getEncoder().encodeToString(derEncoded);
	    
	    //Write the PEM file:
	    try (FileWriter writer = new FileWriter(fileName.toString())) {
	    	writer.write("-----BEGIN PUBLIC KEY-----\n");
	    	writer.write(base64Encoded+"\n");
	    	writer.write("-----END PUBLIC KEY-----\n");
	    	
	    	//Once all is over we return the location:
	    	return fileName.toString();
	    } catch (Exception e) {
	    	return null;
	    }
	    
	}
	
	/**
	 * Save the private key in a standard PEM & Base64 format.
	 * @param directory Directory where keys are saved.
	 * @return Location of th saved key.
	 * @throws IOException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 */
	public String savePrivate(String directory) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
		//Initialization:
	    StringBuilder fileName = fileName(directory, 1);

	    //Compute the CRT:
	    BigInteger dp = this.getD().mod(this.getP().subtract(BigInteger.ONE));
	    BigInteger dq = this.getD().mod(q.subtract(BigInteger.ONE));
	    BigInteger qInv = this.getQ().modInverse(this.getP());

	    //Create RSA private key specification:
	    RSAPrivateCrtKeySpec spec = new RSAPrivateCrtKeySpec(this.getN(), this.getE(), this.getD(), this.getP(), this.getQ(), dp, dq, qInv);

	    //Generate PrivateKey object:
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    PrivateKey privateKey = keyFactory.generatePrivate(spec);

	    //Get DER-encoded bytes (X.509 SPKI format):
        byte[] derEncoded = privateKey.getEncoded();

	    //Base64-encode the DER bytes:
        String base64Encoded = Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(derEncoded);

	    try (FileWriter writer = new FileWriter(fileName.toString())) {
	        writer.write("-----BEGIN PRIVATE KEY-----\n");
	        writer.write(base64Encoded);
	        writer.write("\n-----END PRIVATE KEY-----\n");
	        return fileName.toString();
	    } catch (Exception e) {
	    	return null;
	    }
	}

	
	/**
	 * Save all the attributes in a non standard PEM format.
	 * Only for pedagogic use.
	 * @param directory Directory where keys are saved.
	 * @return Location of th saved key.
	 * @throws IOException 
	 */
	public String saveComplete(String directory) throws IOException {
		//Initialization:
		StringBuilder fileName = fileName(directory, 2);
		
		//Write the PEM file:
	    try (FileWriter writer = new FileWriter(fileName.toString())) {
	    	writer.write("-----BEGIN COMPLETE KEY-----\n");
	    	writer.write("P="+this.getHex(this.getP())+"\nQ="+this.getHex(this.getQ())+"\nN="+this.getHex(this.getN())+"\nPhi="+this.getHex(this.getPhi())+"\nD="+this.getHex(this.getD())+"\nE="+this.getHex(this.getE())+"\n");
	    	writer.write("-----END COMPLETE KEY-----\n");
	    	
	    	//Once all is over we return the location:
	    	return fileName.toString();
	    } catch (Exception e) {
	    	return null;
	    }
	}

	/**
	 * Generate P and Q as big primes.
	 * @param size
	 * @return Array of two big primes.
	 */
	private BigInteger[] generatePrimes(int size) {
		//Initialization:
		SecureRandom random = new SecureRandom();
		BigInteger p, q;
		int primeSize = size/2;
		
		//Generate P from scratch:
		p = BigInteger.probablePrime(primeSize, random);
		
		//Once we have P we generate Q as prime number of P:
		do {
			q = BigInteger.probablePrime(primeSize, random);
		} while (p.equals(q)); //P and Q must be different
		
		//When the two primes are generated we return them in a array:
		return new BigInteger[] {p,q};
	}
	
	/**
	 * Generate the file name of the saved key.
	 * @param directory Directory where the key is saved.
	 * @param mode 0: Public Key - 1 Private Key
	 * @return file name
	 */
	private StringBuilder fileName(String directory, int mode) {
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
	    switch(mode) {
	    	case 0:
	    		fileName.append("_PUBLIC_KEY.pem");
	    		break;
	    	case 1:
	    		fileName.append("_PRIVATE_KEY.pem");
	    		break;
	    	case 2:
	    		fileName.append("_COMPLETE_KEY.pem");
	    		break;
	    	default:
	    		break;
	    }
	    
	    return fileName;
	}
	
	/**
	 * @param pemPath Location of the saved key.
	 * @return True if the given path refer to a private RSA key.
	 */
	public static boolean isPrivateKeyPEM(Path pemPath) {
		try {
            //Initialization:
            String pem = Files.readString(pemPath);

            //Verify the PEM header:
            if (!pem.contains("-----BEGIN PRIVATE KEY-----") || !pem.contains("-----END PRIVATE KEY-----")) return false;

            //Get the base 64 encoded:
            String base64 = pem.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").replaceAll("\\s", "");
            byte[] der = Base64.getDecoder().decode(base64);

            //Generate the PKCS#8 key:
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(der);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            //Verify that is a valid RSA key:
            if (!(privateKey instanceof RSAPrivateKey)) {
                return false;
            }

            //If nothing goes wrong we return true:
            return true;
        } catch (Exception e) {
            return false;
        }
	}
	
	/**
	 * @param pemPath Location of the saved key.
	 * @return True if the given path refer to a public RSA key.
	 */
	public static boolean isPublicKeyPEM(Path pemPath) {
		try {
			//Initialization:
	        String pem = Files.readString(pemPath);

	        //Verification of the headers:
	        if (!pem.contains("-----BEGIN PUBLIC KEY-----") || !pem.contains("-----END PUBLIC KEY-----")) return false;

	        //Get the base 64 encoded:
	        String base64 = pem.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replaceAll("\\s", "");
	        byte[] der = Base64.getDecoder().decode(base64);

	        //Generate the X.509 key:
	        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(der);
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        PublicKey publicKey = keyFactory.generatePublic(keySpec);

	        //Verify RSA:
	        return publicKey instanceof RSAPublicKey;
		} catch (Exception e) {
			return false;
		}
	}
}
