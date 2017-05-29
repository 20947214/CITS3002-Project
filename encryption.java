package cryptocurrency;

import java.math.BigInteger;
import java.util.Base64;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Methods for encrypting and decrypting given messages
 * @author Marcus Green 20947214
 *
 */
public class encryption {
	
	//Execute sample encryption and decryption
	public static void main(String[] args) {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		//Encrypt the message using private key
		String encrypted = encrypt("Hello world", "private.pem");
		//Decrypt the encrypted byte array using public key
		System.out.println("\ndecrypted = " + decrypt(encrypted, "public.pem") + "\n");
	}

	/**
	 * Encrypts a given message using the selected PEM formatted key file
	 * @param message String of the message to be encrypted
	 * @param key String of the filename of the key in PEM format used to encrypt the message
	 * @return String containing the encrypted message
	 */
	public static String encrypt(String message, String key) {
	    //Create an object of the modulus and exponent read from a given PEM key file
	    readPEM keyPEM = new readPEM(key);
		BigInteger exp = keyPEM.exponent;
		BigInteger mod = keyPEM.modulus;
		
		String encrypted = "";
		try {
		//Encrypts the message into a byte array using the calculation message^exponent %(mod)
		byte[] encryptedBytes =  new BigInteger(message.getBytes()).modPow(exp,mod).toByteArray();
		
	    StringWriter sw = new StringWriter();
	    
	    //Write Strings
        sw.write("-----BEGIN MESSAGE-----\n");
        //Converts the byte array into a base64 encoded String
        sw.write(Base64.getMimeEncoder(64,"\n".getBytes()).encodeToString(encryptedBytes));
        sw.write("\n-----END MESSAGE-----\n");
        
        encrypted = sw.toString();
        System.out.println(encrypted);
        
        sw.close();
        
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return encrypted;
		
	}
	
	/**
	 * Decrypts a given encrypted message using the selected PEM formatted key file
	 * @param encrypted String of the encrypted message
	 * @param key String of the corresponding public/private key to decrypt the message
	 * @return String of the decrypted message
	 */
	public static String decrypt(String encrypted, String key) {
		//Create an object of the modulus and exponent read from a given PEM key file
	    readPEM keyPEM = new readPEM(key);
		BigInteger exp = keyPEM.exponent;
		BigInteger mod = keyPEM.modulus;
		String decrypted = "";
	    if (encrypted.indexOf("-----BEGIN MESSAGE-----") !=-1 && encrypted.indexOf("-----END MESSAGE-----") !=-1) {
	    	
		    String encString = encrypted.split("-----BEGIN MESSAGE-----")[1].split("-----END MESSAGE-----")[0];
		    
		    //Converts the base64 string into a byte array
		    byte[] encryptedBytes = Base64.getMimeDecoder().decode(encString.getBytes());
		    
			//Decrypts the message using the calculation message^exponent %(mod)
			byte[] decryptedBytes =  new BigInteger(encryptedBytes).modPow(exp,mod).toByteArray();
			//Creates the string from the decrypted bytes
			decrypted = new String(decryptedBytes);
			
	    }
	    return decrypted;
	}
	

}
