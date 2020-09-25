package com.cg.onlineWallet.service;

import java.time.LocalDateTime;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.onlineWallet.dao.AccountTransactionsInterface;
import com.cg.onlineWallet.dao.WalletAccountInterface;
import com.cg.onlineWallet.dao.WalletUserInterface;
import com.cg.onlineWallet.entity.AccountTransactions;
import com.cg.onlineWallet.entity.WalletAccount;
import com.cg.onlineWallet.entity.WalletUser;

@Service
@Transactional
public class ServiceClass {
	@Autowired
	AccountTransactionsInterface transactionsDao;
	@Autowired
	WalletAccountInterface walletAccountDao;
	@Autowired
	WalletUserInterface walletUserDao;
	//Adding User details into the database
	public WalletUser addUser(WalletUser user)
	{
		return walletUserDao.save(user);
	}
	//user login 
	public WalletUser userLogin(int userId,String password)
	{
		if(walletUserDao.existsById(userId))
		{
			WalletUser user=walletUserDao.getOne(userId);
			String pass=user.getPassword();
			if(pass.equals(password))
			{
				return user;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	//Creating wallet account to a particular user
	public WalletAccount addAccount(int userId,WalletAccount account)
	{
		walletAccountDao.save(account);
		 WalletUser user=walletUserDao.getOne(userId);
		 account.setWalletUser(user);
		 user.setWalletAccount(account);
		 walletUserDao.save(user);
		 return walletAccountDao.save(account);
	}
	//Adding money to user wallet account
	public WalletAccount addMoney(WalletAccount walletAccount)
	{
		int accountId=walletAccount.getAccountId();
		if(walletAccountDao.existsById(accountId))
		{
			WalletAccount account=walletAccountDao.getOne(accountId);
			double previousBalance=account.getAccountBalance();
			account.setAccountBalance(walletAccount.getAccountBalance()+previousBalance);
			return walletAccountDao.save(account);
		}
		else
		{
			return null;
		}
	}
	//To show User wallet account balance
	public double retriveBalance(int accountId)
	{
		WalletAccount account=walletAccountDao.getOne(accountId);
		return account.getAccountBalance();
	}
	//To transfer funds from one account to another account
	public String transferFunds(int senderAccountId,int receiverAccountId,double amount)
	{
		if(walletAccountDao.existsById(receiverAccountId))
		{
			WalletAccount senderAccount=walletAccountDao.getOne(senderAccountId);
			if(senderAccount.getAccountBalance()>amount)
			{
				//Amount debit from sender account
				double money=senderAccount.getAccountBalance()-amount;
				senderAccount.setAccountBalance(money);
				walletAccountDao.save(senderAccount);
				//Amount credited into receiver account
				WalletAccount receiverAccount=walletAccountDao.getOne(receiverAccountId);
				double money1=receiverAccount.getAccountBalance()+amount;
				receiverAccount.setAccountBalance(money1);
				walletAccountDao.save(receiverAccount);
				//creating sender account transaction details
				LocalDateTime now = LocalDateTime.now();  
				AccountTransactions senderTransaction=new AccountTransactions();
				senderTransaction.setAccountBalance(senderAccount.getAccountBalance());
				senderTransaction.setAmount(amount);
				senderTransaction.setDateOfTransaction(now);
				senderTransaction.setDiscription("Amount debited");
				senderTransaction.setWalletAccount(senderAccount);
				transactionsDao.save(senderTransaction);
				senderAccount.getWalletTransactions().add(senderTransaction);
				walletAccountDao.save(senderAccount);
				//creating receiver account transaction details
				AccountTransactions receiverTransaction=new AccountTransactions();
				receiverTransaction.setAccountBalance(receiverAccount.getAccountBalance());
				receiverTransaction.setAmount(amount);
				receiverTransaction.setDateOfTransaction(now);
				receiverTransaction.setDiscription("Amount credited");
				receiverTransaction.setWalletAccount(receiverAccount);
				transactionsDao.save(receiverTransaction);
				receiverAccount.getWalletTransactions().add(receiverTransaction);
				walletAccountDao.save(receiverAccount);
				return "SuccessFully Transfered";	
			}
			else
			{
				return "Insufficient account balance";
			}
		}
		else
		{
			return "Receiver AccountId does not exist";
		}
	}
	//Retrieving transaction details 
	public Set<AccountTransactions> transactionDetails(int accountId)
	{
		if(walletAccountDao.existsById(accountId))
		{
			WalletAccount account=walletAccountDao.getOne(accountId);
			return account.getWalletTransactions();
		}
		else
		{
			return null;
		}
	}

}