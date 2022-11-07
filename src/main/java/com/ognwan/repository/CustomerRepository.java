/**
 * 
 */
package com.ognwan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ognwan.model.Customer;

/**
 * @author gerry
 * @version 1.0
 * 
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	@Query()
	public Customer findByEmail(String email);

	public Customer findByUserName(String username);

}
