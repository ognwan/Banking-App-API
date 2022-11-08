/**
 * 
 */
package com.ognwan.serviceImplementation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ognwan.model.Account;
import com.ognwan.model.AccountType;
import com.ognwan.repository.AccountRepository;
import com.ognwan.service.ServiceInterface;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * .
 * 
 * @author gerry
 * @version 1.0
 * 
 */
@Service
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class AccountService implements ServiceInterface<Account> {
	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	TransactionService transactionService;

	@Override
	public Account create(Account account) throws Exception {
		account.setCreatedAt(LocalDateTime.now());
		accountRepo.save(account);
		transactionService.createTransaction(account, "Account opened", BigDecimal.ZERO);
		return account;
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
	public void delete(long id) {
		accountRepo.deleteById(id);
	}

	public BigDecimal withdraw(Account account, BigDecimal amount) throws Exception {
		if (account.getAccountType().equals(AccountType.savings) && amount.compareTo(account.getBalance()) == 1) {
			throw new Exception("no overdraft available");
		}
		account.withdraw(amount);
		Account updatedAccount = accountRepo.save(account);
		transactionService.createTransaction(updatedAccount, "withdrawal", amount);
		return updatedAccount.getBalance();
	}

	public BigDecimal deposit(Account account, BigDecimal amount) {
		account.deposit(amount);
		Account updatedAccount = accountRepo.save(account);
		transactionService.createTransaction(updatedAccount, "deposit", amount);
		return updatedAccount.getBalance();
	}
}
