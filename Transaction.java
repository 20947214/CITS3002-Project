package cryptocurrency;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Transaction implements Serializable{
	
	public int amount;
	public String from;
	public String to;
	
	/**
	 * create new transaction
	 * @param amount the amount to be transferred
	 * @param from the sender of the amount
	 * @param to the receiver of the amount
	 */
	public Transaction(int amount, String from, String to){
		this.amount = amount;
		this.from = from;
		this.to= to;
	}
	
	/**
	 * convert the transaction to a string to be SHA256 hashed
	 */
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append(String.valueOf(amount));
		s.append(from);
		s.append(to);
		return s.toString();
	}
}


