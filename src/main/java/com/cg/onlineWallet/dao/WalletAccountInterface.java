package com.cg.onlineWallet.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.onlineWallet.entity.WalletAccount;


@Repository
public interface WalletAccountInterface extends JpaRepository<WalletAccount,Integer>{

}