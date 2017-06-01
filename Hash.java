package cryptocurrency;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Single method to take a given string and return the SHA-256 hash as a string of hex
 * @author Marcus Green 20947214
 *
 */
public class Hash {
	
	//Execute sample hash
	public static void main(String[] args) {
		
		Hash hasher = new Hash();		
		System.out.println(hasher.sha256("Hello World"));

	}
	
	/**
	 * constructor
	 */
	public Hash(){}
	
	/**
	 * One way hash using the SHA-256 algorithm
	 * @param message String to be hashed
	 * @return String of the SHA-256 hash
	 */
	public String sha256(String message) {
		//Default return if exception is thrown
		String hashed = "";
		try {
		//Get the SHA-256 algorithm
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		//Create the byte array of SHA-256 hashed message
		byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
		
		//StringBuffer for converting byte array to hex
        StringBuffer hexString = new StringBuffer();
        
        //Iterate through byte array hash
        for (int i = 0; i < hash.length; i++) {
        	//Converts signed bytes to positive integer
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            //Append hex character to StringBuffer
            hexString.append(hex);
        }

        hashed = hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hashed;
	}

}
