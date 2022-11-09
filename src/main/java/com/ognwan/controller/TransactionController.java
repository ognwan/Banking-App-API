/**
 * 
 */
package com.ognwan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ognwan.model.Transaction;
import com.ognwan.serviceImplementation.TransactionService;

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
@RequestMapping("/transactions")
public class TransactionController {
	@Autowired
	TransactionService transactionService;

	@GetMapping("/{accountNumber}")
	public List<Transaction> getAllTransactionsPerAccount(@PathVariable long accountNumber) {
		return transactionService.listTransactionsByAccountNumber(accountNumber);
	}
}
