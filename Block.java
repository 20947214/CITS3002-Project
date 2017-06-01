package cryptocurrency;

import java.io.Serializable;
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
	 * converts the block to a string than can be SHA256 hashed
	 */
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append(String.valueOf(index));
		s.append(String.valueOf(nonce));
		s.append(hashPrevBlock);
		for(int i=0; i<transactions.size()-1; i++) s.append(transactions.get(i).toString());
		return s.toString();
	}
}


