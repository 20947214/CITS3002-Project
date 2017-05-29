package cryptocurrency;

import java.util.Base64;
import java.io.IOException;
import java.math.BigInteger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Reads a given public or private RSA key file in PEM format and holds the modulus and exponent
 * @author Marcus Green 20947214
 *
 */
public class readPEM {
	
	public BigInteger exponent;
	public BigInteger modulus;
	
	//Execute sample read
	public static void main(String[] args) {
		//System.out.println("Working Directory = " + System.getProperty("user.dir"));
		readPEM pem = new readPEM("public.pem");
	    System.out.println("exp: " + pem.exponent);
	    System.out.println("mod: " + pem.modulus);
		
	}

	/**
	 * Constructor
	 * Holds the key exponent and modulus
	 * @param fileName String of the key filename, eg "public.pem"
	 */
	public readPEM(String fileName) {
		
		try {
			//Create an object to hold the key
		    File file = new File(fileName);
			FileReader fr = new FileReader(file);
			
			//Create a char array the size of the key
		    char [] a = new char[(int)file.length()];
		    
		    //Iterates through the file, storing it in the char array
		    fr.read(a);

		    System.out.print(a);
		    
		    fr.close();
		    
		    //Create a String of the char array
		    String key = new String(a);
		    
		    //Detects the private key format
		    if (key.indexOf("-----BEGIN RSA PRIVATE KEY-----") !=-1 && key.indexOf("-----END RSA PRIVATE KEY-----") !=-1) {
		    	//Selects the String between the header and footer of the key
			    key = key.split("-----BEGIN RSA PRIVATE KEY-----")[1].split("-----END RSA PRIVATE KEY-----")[0];
			    //Convert the base64 String into a byte array
			    byte[] keyBytes = Base64.getMimeDecoder().decode(key.getBytes());
			    //Declare an RSA keyFactory object to hold the key
			    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			    RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
			    
			    //Set the objects modulus and exponent
			    modulus = privateKey.getModulus();
			    exponent = privateKey.getPrivateExponent();

		    //Detects the public key format
		    } else if (key.indexOf("-----BEGIN RSA PUBLIC KEY-----") !=-1 && key.indexOf("-----END RSA PUBLIC KEY-----") !=-1) {
		    	//Selects the String between the header and footer of the key
			    key = key.split("-----BEGIN RSA PUBLIC KEY-----")[1].split("-----END RSA PUBLIC KEY-----")[0];
			    //Convert the base64 String into a byte array
			    byte[] keyBytes = Base64.getMimeDecoder().decode(key.getBytes());
			    //Declare an RSA keyFactory object to hold the key
			    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			    RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
			    
			    //Set the objects modulus and exponent
			    modulus = publicKey.getModulus();
			    exponent = publicKey.getPublicExponent();
   
		    //If no PEM key format is detected
		    } else System.out.println("Invalid RSA key format");
		    
		    
			} catch (FileNotFoundException e) {
				e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        } catch (InvalidKeySpecException e) {
	            e.printStackTrace();
	        }
	}



}
