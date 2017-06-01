package cryptocurrency;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Transaction implements Serializable{
	
	public int amount;
	public String fromPubKey;
	public String toPubKey;
	
	/**
	 * create new transaction
	 * @param amount the amount to be transferred
	 * @param from the sender of the amount
	 * @param to the receiver of the amount
	 */
	public Transaction(int amount, String from, String to){
		this.amount = amount;
		this.fromPubKey = from;
		this.toPubKey= to;
	}	
}


