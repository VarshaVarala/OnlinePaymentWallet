package com.cg.onlineWallet.controller;



import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.onlineWallet.entity.AccountTransactions;
import com.cg.onlineWallet.entity.WalletAccount;
import com.cg.onlineWallet.entity.WalletUser;
import com.cg.onlineWallet.service.ServiceClass;

import exceptions.IdNotFoundException;

@RestController
@RequestMapping("/onlinewallet")
@CrossOrigin("http://localhost:4200")
public class ControllerClass {
	@Autowired
	private ServiceClass service;
	//Creating User account and saving the data in wallet user object
	@PostMapping("/createuser")
	public ResponseEntity<String> createUser(@RequestBody WalletUser walletUser)
	{
		WalletUser user=service.addUser(walletUser);
		if (user == null) 
		{
			throw new IdNotFoundException("User account not created");
		} 
		else 
		{
			return new ResponseEntity<String>("User account created successfully", new HttpHeaders(), HttpStatus.OK);
		}		
	}
	//User Login with exception for unregistered user
	@GetMapping("/userlogin/{userId}/{password}")
	public ResponseEntity<WalletUser> userLogin(@PathVariable("userId") int userId,@PathVariable("password") String password)
	{
		WalletUser login=service.userLogin(userId, password);
		if(login==null)
		{
			throw new IdNotFoundException("User does not exist");  
		}
		else
		{
			return new ResponseEntity<WalletUser>(login, new HttpHeaders(), HttpStatus.OK);
		}
		
	}
	//Creating a payment wallet account with user Id
	@PostMapping("/createaccount/{userId}")
	public ResponseEntity<String> createAccount(@PathVariable("userId") int userId,@RequestBody WalletAccount walletAccount)
	{
		WalletAccount account=service.addAccount(userId,walletAccount);
		if (account == null) 
		{
			throw new IdNotFoundException("Wallet account not created");
			// Exception for invalid account Id
		} 
		else 
		{
			return new ResponseEntity<String>("Wallet account created successfully", new HttpHeaders(), HttpStatus.OK);
		}	
		
	}
	//Adding amount into wallet account and updating the balance
	@PostMapping("/addmoney")
	public ResponseEntity<String> addMoney(@RequestBody WalletAccount accountDetails)
	{
		WalletAccount money=service.addMoney(accountDetails);
		if (money == null) 
		{
			throw new IdNotFoundException("AccountId is Invalid");
		} 
		else 
		{
			return new ResponseEntity<String>("Money added successfully", new HttpHeaders(), HttpStatus.OK);
		}	
		
	}
	//To show payment wallet account balance using account ID
	@GetMapping("/accountbalance/{accountId}")
	public ResponseEntity<Double> accountBalance(@PathVariable("accountId") int accountId)
	{
		double accountBalance=service.retriveBalance(accountId);
		return new ResponseEntity<Double>(accountBalance, new HttpHeaders(), HttpStatus.OK);
	}
	//To transfer fund from one account to another account
	@GetMapping("/transferfund/{senderaccountId}/{recieveraccountId}/{amount}")
	public ResponseEntity<String> transferFund(@PathVariable("senderaccountId") int senderAccountId,@PathVariable("recieveraccountId") int receiverAccountId,@PathVariable("amount") double amount)
	{
		String status=service.transferFunds(senderAccountId, receiverAccountId, amount);
		return new ResponseEntity<String>(status, new HttpHeaders(), HttpStatus.OK);

		
	}
	//To retrieve the transaction details of particular user using user Id
	@GetMapping("/transactiondetails/{accountId}")
	public ResponseEntity<Set<AccountTransactions>> transactionDetails(@PathVariable("accountId") int accountId)
	{
		Set<AccountTransactions> transactions=service.transactionDetails(accountId);
		if(transactions==null)
		{
			throw new IdNotFoundException("AccountId is Invalid");
		} 
		else 
		{
			return new ResponseEntity<Set<AccountTransactions>>(transactions, new HttpHeaders(), HttpStatus.OK);
		}	
	}

}