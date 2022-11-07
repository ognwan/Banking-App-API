/**
 * 
 */
package com.ognwan.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ognwan.model.Account;
import com.ognwan.model.Customer;
import com.ognwan.serviceImplementation.AccountService;
import com.ognwan.serviceImplementation.CustomerService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author gerry
 * @version 1.0
 * 
 */
@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private CustomerService customerService;

	@PostMapping("/openNewAccount")
	public Account openAccount(@RequestParam long id, @RequestBody Account account) throws Exception {
		Customer returnedCustomer = customerService.getById(id);
		account.setCustomer(returnedCustomer);
		return accountService.create(account);
	}

	@GetMapping("/{customerId}")
	public List<Account> getAllAccountsByCustomerId(@PathVariable long customerId) {
		return accountService.listAccountsByCustomerId(customerId);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<?> withdraw(@PathVariable long accountNumber, @RequestParam BigDecimal amount) {
		try {
			Account returnedAccount = accountService.getById(accountNumber);
			if (returnedAccount == null) {
				throw new Exception("Account not found");
			} else {
				accountService.withdraw(returnedAccount, amount);
				return ResponseEntity.ok().body("$" + amount + " withdrawn successfully");

			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PostMapping("/deposit")
	public ResponseEntity<?> deposit(@PathVariable long accountNumber, BigDecimal amount)
			throws AccountNotFoundException {
		Account returnedAccount = accountService.getById(accountNumber);
		try {
			if (returnedAccount == null) {
				throw new Exception("Account not found");
			} else {
				accountService.deposit(returnedAccount, amount);
				return ResponseEntity.ok().body("$" + amount + " deposited successfully");
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
