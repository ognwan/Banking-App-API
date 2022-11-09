/**
 * 
 */
package com.ognwan.serviceImplementation;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ognwan.model.Account;
import com.ognwan.model.Transaction;
import com.ognwan.repository.TransactionRepository;

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
@Transactional
public class TransactionService {
	@Autowired
	TransactionRepository transactionRepo;

	public Transaction getTransactionById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Transaction createTransaction(Account account, String details, BigDecimal amount) {
		Transaction transaction = new Transaction();
		transaction.setDetails(details);
		transaction.setAccount(account);
		transaction.setAmount(amount);
		return transactionRepo.save(transaction);

	}

	public List<Transaction> listTransactionsByAccountNumber(long accountNumber) {
		return transactionRepo.findByAccountAccountNumber(accountNumber);
	}
}
