package com.cg.onlineWallet.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.onlineWallet.entity.AccountTransactions;


@Repository
public interface AccountTransactionsInterface extends JpaRepository<AccountTransactions,Integer>{

}