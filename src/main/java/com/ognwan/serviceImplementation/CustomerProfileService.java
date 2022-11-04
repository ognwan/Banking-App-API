/**
 * 
 */
package com.ognwan.serviceImplementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ognwan.exceptions.UserNotFoundException;
import com.ognwan.model.CustomerProfile;
import com.ognwan.repository.CustomerProfileRepository;
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
public class CustomerProfileService implements ServiceInterface<CustomerProfile> {
	@Autowired
	CustomerProfileRepository customerProfileRepo;

	@Override
	public CustomerProfile create(CustomerProfile customerProfile) throws Exception {
		if (customerProfileRepo.findByEmail(customerProfile.getEmail()) == null) {
			customerProfile.setCreated(LocalDateTime.now());
			customerProfile.setLastUpdated(LocalDateTime.now());
			customerProfile.setPassword(customerProfile.generatePassword());
			return customerProfileRepo.save(customerProfile);
		} else {
			throw new Exception("email already exists");
		}

	}

	@Override
	public ResponseEntity<CustomerProfile> deleteById(long id) {
		customerProfileRepo.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@Override
	public CustomerProfile update(CustomerProfile customerProfile) {
		CustomerProfile returnedCustomerProfile = customerProfileRepo.save(customerProfile);
		return returnedCustomerProfile;
	}

	@Override
	public List<CustomerProfile> listAll() {
		return customerProfileRepo.findAll();
	}

	@Override
	public CustomerProfile getById(long id) throws UserNotFoundException {
		return customerProfileRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	public CustomerProfile getByUserName(String username) {
		return customerProfileRepo.findByUserName(username);
	}

	public CustomerProfile getByEmail(String email) {
		return customerProfileRepo.findByEmail(email);
	}

	public boolean validate(String email, String password) {
		CustomerProfile returnedCustomer = customerProfileRepo.findByEmail(email);
		return returnedCustomer != null && returnedCustomer.getPassword().equals(password)
	}
}
