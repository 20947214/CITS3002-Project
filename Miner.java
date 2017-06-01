package cryptocurrency;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class Miner {
	
	public static LinkedList<Block> blockChain;
	public static Block currentBlock;

	/**
	 * create a blank Block and mine it until a nonce is found that results in a hash beginning with 000
	 * @param args arguments
	 */
	public static void main(String[] args){
		currentBlock = new Block(0,"0");
		int nonce = 0;
		Hash hasher = new Hash();
		String hash;
		//iterate the nonce until the target hash is found
		while(true){
			currentBlock.nonce = nonce;
			hash = hasher.sha256(currentBlock.toString());
			if(hash.startsWith("000")){
				//TODO add block to blockchain
				System.out.printf("block solved! nonce = %d hash = %s\n",nonce, hash);				
				break;
			}
			else nonce ++;
		}		
	}
	
	/**
	 * writes the block chain to a file
	 */
	public static void writeBlockChain() {	
		
		try {
			FileOutputStream fileOut = new FileOutputStream("blockchain.bin");
			ObjectOutputStream oos = new ObjectOutputStream(fileOut);
			oos.writeObject(blockChain);
			oos.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}		
	}
	
	/**
	 * Reads the block chain from a file
	 */
	@SuppressWarnings("unchecked")
	public static void readBlockChain() {
		try {
			FileInputStream fileIn = new FileInputStream("blockchain.bin");			
			ObjectInputStream ois = new ObjectInputStream(fileIn);
			blockChain = (LinkedList<Block>) ois.readObject();
			ois.close();
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		}		
	}	
}
