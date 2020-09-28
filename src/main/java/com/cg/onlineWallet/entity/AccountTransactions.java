package com.cg.onlineWallet.entity;

import java.time.LocalDateTime;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Transactions")
public class AccountTransactions {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE1")
	@SequenceGenerator(name="SEQUENCE1", sequenceName="SEQUENCE1", allocationSize=1)
	private int transactionId;
	private String discription;
	private LocalDateTime dateOfTransaction;
	private double amount;
	private double accountBalance;
	//many to one relationship between WalletTransactions and WalletAccount
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable=false)
	private WalletAccount walletAccount;
	//getters and setters methods
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public LocalDateTime getDateOfTransaction() {
		return dateOfTransaction;
	}
	public void setDateOfTransaction(LocalDateTime dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public void setWalletAccount(WalletAccount walletAccount) {
		this.walletAccount = walletAccount;
	}
	

}


