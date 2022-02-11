package project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Bank {
	static String clients = "clientInfo.txt";
	static String accounts = "accountInfo.txt";
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		int exit = 0;
		do {
			System.out.println("Please choose");
			System.out.println("1. Create and manage bank clients");
			System.out.println("2. Accsess to personal bank account");
			System.out.println("0. Exit");
			int choice = sc.nextInt();
			
			switch(choice) {
			  case 1:	
				  int user_choice = 5;
				  do {
					  System.out.println("Please choose");
					  System.out.println("1. Create new clients");
					  System.out.println("2. Manage exist clients");
					  System.out.println("0. Back");
					  user_choice = sc.nextInt();
					  switch(user_choice) {
					  case 1:
						  createAccount();
					    break;
					  case 2:
						  System.out.println("Clients list");
					      manageClients();
					    break;
					  case 0:
					     user_choice = 0;
					    break;
					  default:
						  System.out.println("Option not found try again");
					}
				} while (user_choice!=0);
				  
			    break;
			  case 2:
				  personalAccount();
				 
			    break;
			  case 0:
			      exit = 3;		
			    break;   
			  default:
				  System.out.println("Option not found try again");				 
			}
		} while (exit == 0);
		
		System.out.println("Thak you for using our bank");	
	}					
			
	public static void createAccount() {
		BankAccount client;
		Scanner sc=new Scanner(System.in);
		int userID = 0;
		try(FileWriter fw = new FileWriter("clientInfo.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			int num = 1;
			while(num!=0){												
				if(num!=0) {
					System.out.println("Creating new client");
					System.out.println("Please enter first name ");
					String fname=sc.nextLine();
					System.out.println("Please enter last name ");
					String lname=sc.nextLine();
					userID = (int) ((Math.random() * (90 - 10)) + 10);
					client = new BankAccount(userID,fname, lname);
					out.println(client.writeToFileClient());
					
					try(FileWriter fw1 = new FileWriter("accountInfo.txt", true);
						    BufferedWriter bw1 = new BufferedWriter(fw1);
						    PrintWriter out1 = new PrintWriter(bw1))
						{
						int num2 = 1;
						while(num2!=0){												
							if(num2!=0) {
								System.out.println("Creating accounts for new client");
								System.out.println("Please chose account type");
								String[] accType = {"Student", "Family", "Saving", "Credit"};
								System.out.println("1. Student 2. Family 3. Saving 4. Credit ");
								int type=sc.nextInt();
								System.out.println("Please enter start balance");
								double balance = sc.nextDouble();
								sc.nextLine();
								System.out.println("Please enter details");
								String details=sc.nextLine();
								int accountNum = (int) ((Math.random() * (900 - 100)) + 100);
								client = new BankAccount(userID,accountNum, accType[type-1], details, balance);
								out1.println(client.writeToFileAccount());
							}
							System.out.println("Press 0 to stop or any number to enter more accounts:");				
							num2 = sc.nextInt();
						};
						
						} catch (IOException e) {
						    //exception 
						}		  					
				}
				System.out.println("Press 0 to stop or any number to enter more client:");				
				num = sc.nextInt();
			};
			
			} catch (IOException e) {
			    //exception
			}		    
	}
	
	public static ArrayList<BankAccount> readDataFromFile( String fileName) {	
		ArrayList<BankAccount> list = new ArrayList<BankAccount>();
		
		try {	
			  File clientInfo = new File(fileName);
		      Scanner myReader = new Scanner(clientInfo);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        String fields []=data.split(",");
		        int userID=Integer.parseInt(fields[0]);		        
		        if (fileName.equalsIgnoreCase("clientInfo.txt")) {
		        	 String fname = fields[1];
				     String lname = fields[2];
				     list.add(new BankAccount(userID, fname, lname));
		        }		       
		        if(fileName.equalsIgnoreCase("accountInfo.txt")) {
		        	int accountNum = Integer.parseInt(fields[1]);
		        	String type = fields[2];
			        String details = fields[3];
			        double balance =Double.parseDouble(fields[4]);
			        list.add(new BankAccount(userID, accountNum,type, details, balance));
		        }		        
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		return list;
	}
	public static void updateData(ArrayList<BankAccount>accounts,String fileName) throws IOException {		
		FileWriter accFile=new FileWriter(fileName);
		PrintWriter pw=new PrintWriter(accFile);
		for(BankAccount acc:accounts)
			pw.println(acc.writeToFileAccount());
		pw.close();	
	}
	
	public static void manageClients() throws IOException {
		Scanner sc=new Scanner(System.in);
		ArrayList<BankAccount> listClients = readDataFromFile(clients);
		ArrayList<BankAccount> listAccounts = readDataFromFile(accounts);
					
		for (int i = 0; i < listClients.size(); i++) {
			System.out.println(listClients.get(i).toStringClient());			
		}
	
		System.out.println("Please enter user ID to see appropriate accounts ");
		int id = sc.nextInt();
	
		System.out.println("client accounts");			
		for (int i = 0; i < listAccounts.size(); i++) {
			if(id == listAccounts.get(i).id) {			
				System.out.println(listAccounts.get(i).toStringAccount());
			}			
		}
		System.out.println("Please enter account number to change details");
		int accountNum = sc.nextInt();
		for (int i = 0; i < listAccounts.size(); i++) {
			if(accountNum == listAccounts.get(i).accountNumber) {			
				System.out.println("Enter new details");
				Scanner enter=new Scanner(System.in);
				String newDetail = enter.nextLine();
				listAccounts.get(i).details = newDetail;
			}			
		}
		updateData(listAccounts, accounts);
  
	}
	
	public static void accountOperations(BankAccount account, ArrayList<BankAccount> listClients, 
			ArrayList<BankAccount> listAccounts) throws IOException {
		Scanner sc=new Scanner(System.in);
		int choice = 0;
		do {
			System.out.println("1. Display accounts balance");
	        System.out.println("2. Deposit money");
	        System.out.println("3. Withdraw money");
	        System.out.println("4. Transfer money ");
	        System.out.println("5. Pay utility bills ");
	        System.out.println("0. Exit \n");
	        System.out.print("Enter operation number : ");
	        
		 choice = sc.nextInt();
			
			switch(choice) {
			  case 1:
			    System.out.println("Current balance: " + account.balance);
			    break;
			  case 2:
				  System.out.println("Enter amount to deposit");
				  double deposit = sc.nextDouble();
				  account.deposit(deposit);
				  System.out.println("amount deposited");
				  updateData(listAccounts, accounts);
			    break;
			  case 3:
				  System.out.println("Enter amount to withdraw");
				  double withdraw = sc.nextDouble();
				  if(withdraw > account.balance) {
					  System.out.println("Not enough balance");
				  }else {
					  account.withdraw(withdraw);
					  System.out.println("amount withdrawed");
					  updateData(listAccounts, accounts);
				  }
				    break;
			  case 4:
				    System.out.println("Enter amount to transfer");
				    double transfer = sc.nextDouble();
				    if(transfer > account.balance) {
				    	System.out.println("Not enough balance");
				    }else {
				    	System.out.println("Please enter transfered User ID");
				    	for (int i = 0; i < listClients.size(); i++) {
				    		if (account.id == listClients.get(i).id) {
								continue;
							}
							System.out.println(listClients.get(i).toStringClient());
						}
				    	int id = sc.nextInt();
				    	boolean check = true;
				    	for (int i = 0; i < listAccounts.size(); i++) {
							if (id == listAccounts.get(i).id ) {
								listAccounts.get(i).deposit(transfer);
								account.withdraw(transfer);
								System.out.println("money transfered");
								updateData(listAccounts, accounts);
								check = false;
								break;
							}						
						}
				    	if(check) {
				    		System.out.println("user not found");
				    	}    	
					}			    			    
				    break;
			  case 5:
				  System.out.println("Please enter bill name");
				  String billName = sc.nextLine();
				  System.out.println("Please enter bill amount");
				  double billAmount = sc.nextDouble();
				  		  
				  	if(billAmount > account.balance) {
				    	System.out.println("Not enough balance");
				    	break;
				    }
				  	account.withdraw(billAmount);
				    System.out.println("bill paid");
				    updateData(listAccounts, accounts);
				    
				    break;
			  case 0:
				    // code block
				    break;
			  default:
				  System.out.println("Option not found try again");
			}		
		} while (choice!=0);
		
	}
	
	public static void personalAccount() throws IOException {
		Scanner sc=new Scanner(System.in);
		int id;
		boolean check = false;
		ArrayList<BankAccount> listClients = readDataFromFile(clients);
		ArrayList<BankAccount> listAccounts = readDataFromFile(accounts);
		
		do {
			System.out.println("Please enter your ID");
			id = sc.nextInt();
			for (int i = 0; i < listClients.size(); i++) {
				if (id == listClients.get(i).id ) {
					System.out.println(listClients.get(i).toStringClient());
					check = true;
				}						
			}
			if(!check)
			System.out.println("ID not found please try onther ID");
		} while (!check);
		
		
		if (check) {
			for (int i = 0; i < listAccounts.size(); i++) {
				if (id == listAccounts.get(i).id ) {
					System.out.println(listAccounts.get(i).toStringUserAccount());
				}						
			}
			System.out.println("Please enter account number");
			int accNum = sc.nextInt();
			for (int i = 0; i < listAccounts.size(); i++) {
				if (accNum == listAccounts.get(i).accountNumber ) {
					accountOperations(listAccounts.get(i), listClients, listAccounts);
				}						
			}
		}
		
		
  
	}
	
	
}			

