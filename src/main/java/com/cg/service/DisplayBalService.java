package com.cg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dao.AccountBalanceDaoI;
import com.cg.entity.WalletAccount;

@Service
public class DisplayBalService implements DisplayBalServiceI {
	@Autowired
	AccountBalanceDaoI accBalDao;
	
    public WalletAccount createAccount(WalletAccount acc) {
		// TODO Auto-generated method stub
		return  accBalDao.save(acc);
	}
	
	

}
