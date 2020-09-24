package com.cg.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Account")
public class WalletAccount {
	@Id
	private int accountId;
	private double accountBalance;
	private enum status{active,blocked};
	/* one to many relationship between WalletAccount and WalletTransactions 
	 * one WalletAccount can have multiple WalletTransactions
	 */
	@OneToMany(mappedBy="walletAccount",cascade=CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval=true)
	private Set<AccountTransactions> walletTransactions;
	//one to one relationship between WalletAccount and WalletUser
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private WalletUser walletUser;
	//getters and setters method
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public Set<AccountTransactions> getWalletTransactions() {
		return walletTransactions;
	}
	public void setWalletTransactions(Set<AccountTransactions> walletTransactions) {
		this.walletTransactions = walletTransactions;
	}
	public void setWalletUser(WalletUser walletUser) {
		this.walletUser = walletUser;
	}
	
	
	

}