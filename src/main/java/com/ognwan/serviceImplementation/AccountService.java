/**
 * 
 */
package com.ognwan.serviceImplementation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ognwan.model.Account;
import com.ognwan.model.AccountType;
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
		return null;
	}

	public List<Account> listAccountsByCustomerId(long customerId) {
		return accountRepo.findByCustomerCustomerId(customerId);
	}

	@Override
	public Account getById(long id) throws AccountNotFoundException {
		return accountRepo.findById(id)
				.orElseThrow(() -> new AccountNotFoundException("account number " + id + " not found"));
	}

	@Override
	public ResponseEntity<Account> delete(long id) {
		accountRepo.deleteById(id);
		return ResponseEntity.ok().build();
	}

	public BigDecimal withdraw(Account account, BigDecimal amount) throws Exception {
		if (account.getAccountType().equals(AccountType.savings) && amount.compareTo(account.getBalance()) == 1) {
			throw new Exception("no overdraft available");
		}
		account.withdraw(amount);
		Account updatedAccount = accountRepo.save(account);
		return updatedAccount.getBalance();
	}

	public BigDecimal deposit(Account account, BigDecimal amount) {
		account.deposit(amount);
		Account updatedAccount = accountRepo.save(account);
		return updatedAccount.getBalance();
	}
}
