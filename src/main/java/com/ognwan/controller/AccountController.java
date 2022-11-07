/**
 * 
 */
package com.ognwan.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
}
