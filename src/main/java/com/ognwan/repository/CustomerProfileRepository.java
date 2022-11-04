/**
 * 
 */
package com.ognwan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ognwan.model.CustomerProfile;

/**
 * @author gerry
 * @version 1.0
 * 
 */
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {
	@Query()
	public CustomerProfile findByEmail(String email);

	public CustomerProfile findByUserName(String username);

}
