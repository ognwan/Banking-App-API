/**
 * 
 */
package com.ognwan.serviceImplementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ognwan.exceptions.UserNotFoundException;
import com.ognwan.model.Account;
import com.ognwan.repository.AccountRepository;
import com.ognwan.service.ServiceInterface;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author gerry
 * @version 1.0
 * 
 */
@Service
@NoArgsConstructor
@AllArgsConstructor
public class AccountService implements ServiceInterface<Account> {
	@Autowired
	AccountRepository accountRepo;

	@Override
	public Account create(Account account) throws Exception {
		account.setCreatedAt(LocalDateTime.now());
		return accountRepo.save(account);
	}

	@Override
	public Account update(Account account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getById(long id) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Account> delete(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
