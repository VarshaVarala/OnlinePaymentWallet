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
	
	//Adding new User details into the database 
	public WalletUser addUser(WalletUser user)
	{
		if(walletUserDao.existsById(user.getUserId()))
		{
			return null;
		}
		else
		{
			return walletUserDao.save(user);
		}
	}
	//user login check
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
	//Creating wallet account for a particular user ID, only one account per user
	public String addAccount(int userId,WalletAccount account)
	{
		if(walletUserDao.existsById(userId))
		{
			if(walletAccountDao.existsById(account.getAccountId()))
			{
				return "AccountId already exist";
			}
			else
			{
				walletAccountDao.save(account);
				WalletUser user=walletUserDao.getOne(userId);
				account.setWalletUser(user);
				user.setWalletAccount(account);
				walletUserDao.save(user);
				walletAccountDao.save(account);
				return "Successfully created";
			}
		}
		else
		{
			return "UserId does not exist";
		}
	}
	
	//Adding money to user wallet account and updating the balance
	public WalletAccount addMoney(WalletAccount walletAccount,int userId)
	{
		int accountId=walletAccount.getAccountId();
		WalletUser user=walletUserDao.getOne(userId);
		int accountId1=user.getWalletAccount().getAccountId();
		if(accountId1==accountId)
		{
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
		else
		{
			return null;
		}
	}
	//To show User wallet account balance
	public double retriveBalance(int accountId,int userId)
	{
		WalletAccount account=walletAccountDao.getOne(accountId);
		WalletUser user=walletUserDao.getOne(userId);
		int accountId1=user.getWalletAccount().getAccountId();
		if(accountId1==accountId)
		{
			return account.getAccountBalance();
		}
		else
		{
			return -1;
		}
	}
	//To transfer funds from one account to another account
	public String transferFunds(int userId,int senderAccountId,int receiverAccountId,double amount)
	{
		WalletUser user=walletUserDao.getOne(userId);
		int accountId=user.getWalletAccount().getAccountId();
		if(accountId==senderAccountId)
		{
			if(walletAccountDao.existsById(receiverAccountId))
			{
				WalletAccount senderAccount=walletAccountDao.getOne(senderAccountId);
				//when sender is trying to transfer amount greater than his balance
				if(senderAccount.getAccountBalance()>amount)
				{
					//Amount debited from sender account
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
		else
		{
			return "Invalid AccountId";
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