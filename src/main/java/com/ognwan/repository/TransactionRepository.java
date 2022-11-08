/**
 * 
 */
package com.ognwan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ognwan.model.Transaction;

/**
 * @author gerry
 * @version 1.0
 * 
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByAccountAccountNumber(long accountNumber);
}
