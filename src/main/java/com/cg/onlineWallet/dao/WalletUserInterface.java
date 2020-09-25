package com.cg.onlineWallet.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.onlineWallet.entity.WalletUser;



@Repository
public interface WalletUserInterface extends JpaRepository<WalletUser,Integer>{

}