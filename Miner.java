package cryptocurrency;

import java.io.File;
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
		//retrieve block chain or create new one if no blockchain is found
		File file = new File("blockchain.bin");
		if(file.length()==0){
			blockChain = new LinkedList<>();
			currentBlock = new Block(0,"0");
		}
		else{
			readBlockChain();
			currentBlock = getCurrentBlock();
		}
		//verify the block chain downloaded
		if(!verifyBlockChain()){
			System.out.println("Block chain incorrect. Please download again");
			return;
		}
		
		//MINE BLOCKS	
		int nonce = 0;
		Hash hasher = new Hash();
		String hash;
		//iterate the nonce until the target hash is found
		while(currentBlock.index < 10){
			//TODO add transactions from network
			getTransactions();
			//try a nonce value
			currentBlock.nonce = nonce;
			hash = hasher.sha256(currentBlock.toString());
			if(hash.startsWith("000")){
				//add block to blockchain
				System.out.printf("block solved! nonce = %d hash = %s\n",nonce, hash);
				currentBlock.hash = hash;				
				blockChain.add(currentBlock);
				writeBlockChain();
				currentBlock = new Block(currentBlock.index+1,hash);
				nonce = 0;
			}
			else nonce ++;
		}		
	}
	
	/**
	 * Verifies that the block chain has not been tampered with
	 * @return true if data is consistent. false otherwise
	 */
	public static boolean verifyBlockChain(){
		for(int i=blockChain.size()-1; i>0; i--){
			String prevHash = blockChain.get(i).hashPrevBlock;
			String actualPrevHash = blockChain.get(i-1).hash;
			if(!prevHash.equals(actualPrevHash)) return false;
		}
		System.out.println("block chain correct. length = "+blockChain.size()+" blocks.");
		return true;
	}
	
	/**
	 * get current block from the network
	 * @return the current block
	 */
	public static Block getCurrentBlock(){
		//TODO get current block from network
		return new Block(0,blockChain.peekLast().hash);
	}
	
	/**
	 * retrieve transactions from the network
	 */
	public static void getTransactions(){
		//TODO get transactions as they happen from the network
		Transaction t = new Transaction(10,"John","Joan");
		currentBlock.addTransaction(t);
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
