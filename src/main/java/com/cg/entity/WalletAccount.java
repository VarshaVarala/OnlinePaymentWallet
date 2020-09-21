package com.cg.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="WalletAccount")

public class WalletAccount {

			
@Id
@GeneratedValue
@Column(length=10)
private Integer account_Id; // primary key of the wallet account table
@Column(length=10)
private Double account_Balance;
@Column(length=20)
private String status;


@OneToOne(cascade=CascadeType.ALL)
@JoinColumn(name="user_Id")
private WalletUser WalletUser;
//one to one relation between user and wallet account table  


@OneToMany (targetEntity=WalletTransactions.class, cascade = CascadeType.ALL)
@JoinColumn	(name = "account_Id" , referencedColumnName = "account_Id")
private List<WalletTransactions> wallettransactions;
//one to many relation between wallet account and transactions table
// used list type for displaying transactions from wallet account

//setters and getters
public WalletUser getWalletUser() {
	return WalletUser;
}
public void setWalletUser(WalletUser walletUser) {
	WalletUser = walletUser;
}
public Integer getAccount_Id() {
	return account_Id;
}
public void setAccount_Id(Integer account_Id) {
	this.account_Id = account_Id;
}
public Double getAccount_Balance() {   // to retrieve the balance of the account
	return account_Balance;
}
public void setAccount_Balance(Double account_Balance) {
	this.account_Balance = account_Balance;
	// to set the account balance while creating a new account
}

public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public List<WalletTransactions> getWallettransactions() {
	return wallettransactions;
}
public void setWallettransactions(List<WalletTransactions> wallettransactions) {
	this.wallettransactions = wallettransactions;
}

@Override
public String toString() {
	return "WalletAccount [account_Id=" + account_Id + ", account_Balance=" + account_Balance + ", status=" + status
			+ ", WalletUser=" + WalletUser + ", wallettransactions=" + wallettransactions + "]";
}


}
