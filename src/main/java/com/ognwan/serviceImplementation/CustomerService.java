/**
 * 
 */
package com.ognwan.serviceImplementation;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ognwan.exceptions.UserNotFoundException;
import com.ognwan.model.Customer;
import com.ognwan.repository.CustomerRepository;
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
public class CustomerService implements ServiceInterface<Customer> {
	@Autowired
	CustomerRepository customerRepo;

	@Override
	public Customer create(Customer customer) throws Exception {
		if (customerRepo.findByEmail(customer.getEmail()) == null) {
			customer.setCreated(LocalDateTime.now());
			customer.setLastUpdated(LocalDateTime.now());
			customer.setPassword(customer.generatePassword());
			return customerRepo.save(customer);
		} else {
			throw new Exception("email already exists");
		}

	}

	@Override
	public Customer update(Customer customer) {
		Customer returnedcustomer = customerRepo.save(customer);
		return returnedcustomer;
	}

	@Override
	public Customer getById(long id) throws UserNotFoundException {
		return customerRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	public Customer getByUserName(String username) {
		return customerRepo.findByUserName(username);
	}

	public Customer getByEmail(String email) {
		return customerRepo.findByEmail(email);
	}

	public boolean validate(String email, String password) {
		Customer returnedCustomer = customerRepo.findByEmail(email);
		return returnedCustomer != null && returnedCustomer.getPassword().equals(password);
	}

	public boolean changePassword(String email, String oldPassword, String newPassword, String confirmNewPassword)
			throws Exception {

		Customer returnedCustomer = customerRepo.findByEmail(email);
		if (!newPassword.equals(confirmNewPassword)) {
			throw new Exception("passwords do not match");
		}
		if (!isPasswordValid(newPassword)) {
			throw new Exception("password doesn't meet requirements");
		}
		if (newPassword.equals(oldPassword)) {
			throw new Exception("new password cannot be the same as a previous password");
		}
		if (validate(email, oldPassword)) {
			returnedCustomer.setPassword(newPassword);
			customerRepo.save(returnedCustomer);
			return true;
		}
		throw new Exception("username or password incorrect");
	}

	public boolean isEmailUnique(String email) throws Exception {
		if (customerRepo.findByEmail(email) != null) {
			throw new Exception("email already exists");
		}
		return true;
	}

	public boolean isPasswordValid(String password) {
		Pattern pattern = Pattern.compile(
				"^(?=[a-zA-Z0-9!@#$%^&*_=+;:,.?]{8,}$)(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[!@#$%^&*_=+;:,.?]).*");
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	@Override
	public ResponseEntity<Customer> delete(long id) {
		customerRepo.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
