package cryptocurrency;

import java.util.Base64;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * Randomly generates an RSA public/private KeyPair and export to PEM formatted files
 * @author Marcus Green 20947214
 *
 */
public class keygen {
	
	
	//Execute generation
	public static void main(String[] args) {
		generateKeys();
	}
	
	/**
	 * Method to randomly generate RSA public/private keys
	 */
	public static void generateKeys() {

		try {
		
		//Uses the RSA algorithm for key generation
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		
		//Initialise keys of 2048 bits
        keyGen.initialize(2048);
        KeyPair kp = keyGen.genKeyPair();
        
        //Declares a KeyFactory to hold the keys as key specifications
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        
        //Places the keys into their respective RSA specifications
        RSAPrivateKeySpec privKeySpec = (RSAPrivateKeySpec) keyFactory.getKeySpec(kp.getPrivate(), RSAPrivateKeySpec.class);
        RSAPublicKeySpec pubKeySpec = (RSAPublicKeySpec) keyFactory.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class);
        
        System.out.println("priv mod:" + privKeySpec.getModulus());
        System.out.println("priv exp:" + privKeySpec.getPrivateExponent());
        System.out.println("pub mod:" + pubKeySpec.getModulus());
        System.out.println("pub exp:" + pubKeySpec.getPublicExponent());
        
        //Creates generic interfaces for the keys to allow use of getEncoded method which converts the objects to byte arrays
        PrivateKey privateKey = keyFactory.generatePrivate(privKeySpec);
        PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);
        
        //Saves the keys to drive
        exportPrivatePEM(privateKey.getEncoded());
        exportPublicPEM(publicKey.getEncoded());
        
        
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	/**
	 * Saves the private key object as a PEM formatted file
	 * @param key Byte array of private key object to be exported
	 * @throws IOException if there is an input/output error
	 */
	public static void exportPrivatePEM(byte[] key) throws IOException {
        //Declare and name key file
		File file = new File("private.pem");
        
        //Creates the new key file
        file.createNewFile();
        
        //Create FileWriter object
	    FileWriter fw = new FileWriter(file);
	    
	    //Write Strings to the file
        fw.write("-----BEGIN RSA PRIVATE KEY-----\n");
        //Converts the byte array into a base64 encoded String
        fw.write(Base64.getMimeEncoder(64,"\n".getBytes()).encodeToString(key));
        fw.write("\n-----END RSA PRIVATE KEY-----\n");
        
        fw.flush();
        fw.close();
	}
	
	/**
	 * Saves the public key object as a PEM formatted file
	 * @param key Byte array of public key object to be exported
	 * @throws IOException if there is an input/output error
	 */
	public static void exportPublicPEM(byte[] key) throws IOException {
        //Declare and name key file
		File file = new File("public.pem");
        
        //Creates the new key file
        file.createNewFile();
        
        //Create FileWriter object
	    FileWriter fw = new FileWriter(file);
	    
	    //Write Strings to the file
        fw.write("-----BEGIN RSA PUBLIC KEY-----\n");
        //Converts the byte array into a base64 encoded String
        fw.write(Base64.getMimeEncoder(64,"\n".getBytes()).encodeToString(key));
        fw.write("\n-----END RSA PUBLIC KEY-----\n");
        
        fw.flush();
        fw.close();
	}
	
	
}
