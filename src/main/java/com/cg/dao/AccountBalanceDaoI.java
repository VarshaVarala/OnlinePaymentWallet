package com.cg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.WalletAccount;

@Repository
public interface AccountBalanceDaoI extends JpaRepository<WalletAccount, Integer> {
	

}
