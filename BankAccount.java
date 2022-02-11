package project;

public class BankAccount{
	int id;
	int accountNumber;
	String firstName;
	String lastName;
	String accountType;
	String details;
	double balance;
	
	public BankAccount(int id,String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public BankAccount(int id, int accountNumber,String accountType, String details, double balance) {
		this.id = id;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.details = details;
		this.balance = balance;
	}
		
	public void deposit( double deposit) {
		this.balance += deposit;			
	}
	public void withdraw( double withdraw) {	
			this.balance -= withdraw;	
	}
	
	public String toStringClient() {
		return "ID # " + id +" Name: "+ firstName+" "+lastName;
	}
	public String toStringAccount() {
		return "Account# "+ accountNumber+ " Type: " + accountType+"\nDetails: "+ details+"\nBalance: "+balance +"\n";
	}
	
	public String toStringUserAccount() {
		return "Account# "+ accountNumber+ " Type: " + accountType;
	}
	
	public String writeToFileClient() {
		return id+","+firstName+","+lastName;
	}
	public String writeToFileAccount() {
		return id+","+accountNumber+","+accountType+","+details+","+balance;
	}
}