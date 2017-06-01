package cryptocurrency;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class Block implements Serializable {
	//block number
	public int index;
	//nonce used to find target hash
	public int nonce;
	//hash of the previous block
	public String hashPrevBlock;
	//list of transactions in the block
	public LinkedList<Transaction> transactions;
	//hash of this block
	public String hash;	
	
	/**
	 * creates a new block
	 * @param index the block number in the chain
	 * @param hashPrevBlock the hash of the previous block in the chain
	 */
	public Block(int index, String hashPrevBlock){
		this.index = index;
		this.hashPrevBlock = hashPrevBlock;
		transactions = new LinkedList<Transaction>();
	}
	
	/**
	 * adds a transaction to the block
	 * @param t the transaction to be added
	 */
	public void addTransaction(Transaction t){
		transactions.add(t);
	}	
	
	/**
	 * write the object to a base64 string
	 */
	public String toString(){		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			oos.close();
			return Base64.getEncoder().encodeToString(baos.toByteArray());
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		
		StringBuilder s = new StringBuilder();
		s.append(String.valueOf(index));
		s.append(String.valueOf(nonce));
		s.append(hashPrevBlock);
		for(int i=0; i<transactions.size()-1; i++) s.append(transactions.get(i).toString());
		return s.toString();
	}
}


