/**
 * 
 */
package com.ognwan.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ognwan.exceptions.UserNotFoundException;
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

	@PostMapping("/open-new-account")
	public Account openAccount(@RequestParam long id, @RequestBody Account account) throws Exception {
		Customer returnedCustomer = customerService.getById(id);
		return accountService.create(account, returnedCustomer);
	}

	@GetMapping("/customer/{customerId}")
	public List<Account> getAllAccountsByCustomerId(@PathVariable long customerId) {
		return accountService.listAccountsByCustomerId(customerId);
	}

	@GetMapping("/{accountNumber}")
	public ResponseEntity<?> getAccountByAccountNumber(@PathVariable long accountNumber) {
		try {
			Account returnedAccount = accountService.getById(accountNumber);
			return ResponseEntity.ok(returnedAccount);
		} catch (AccountNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/withdraw")
	public ResponseEntity<?> withdraw(@RequestParam long accountNumber, @RequestParam BigDecimal amount) {
		try {
			Account returnedAccount = accountService.getById(accountNumber);
			if (returnedAccount == null) {
				throw new Exception("Account not found");
			}
			accountService.withdraw(returnedAccount, amount);
			Map<String, Object> obj = new HashMap<>();
			obj.put("amount", amount);
			obj.put("balance", returnedAccount.getBalance());
			return ResponseEntity.ok().body(obj);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PostMapping("/deposit")
	public ResponseEntity<?> deposit(@RequestParam long accountNumber, BigDecimal amount)
			throws AccountNotFoundException {

		Account returnedAccount = accountService.getById(accountNumber);
		try {
			if (returnedAccount == null) {
				throw new Exception("Account not found");
			}
			accountService.deposit(returnedAccount, amount);
			Map<String, Object> obj = new HashMap<>();
			obj.put("amount", amount);
			obj.put("balance", returnedAccount.getBalance());
			return ResponseEntity.ok().body(obj);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/delete/{accountNumber}")
	public ResponseEntity<?> delete(@PathVariable long accountNumber) {

		try {
			if (accountService.getById(accountNumber) == null) {
				throw new UserNotFoundException(accountNumber);
			}
			accountService.delete(accountNumber);
			return ResponseEntity.ok().body(accountNumber + " deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
