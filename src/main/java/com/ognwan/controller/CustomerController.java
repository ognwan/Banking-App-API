/**
 * 
 */
package com.ognwan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ognwan.exceptions.UserNotFoundException;
import com.ognwan.model.Customer;
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
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerProfileService;

	@GetMapping
	public List<Customer> getAllCustomers() {
		return customerProfileService.listAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
		try {
			Customer returnedCustomerProfile = customerProfileService.getById(id);
			return ResponseEntity.ok(returnedCustomerProfile);
		} catch (UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/update-profile")
	public ResponseEntity<?> updateCustomerDetails(@RequestBody Customer customerProfile, @RequestParam String email) {
		Customer returnedCustomer = customerProfileService.getByEmail(email);

		try {
			if (returnedCustomer == null) {
				throw new Exception("User does not exist");
			}

			if (customerProfile.getEmail() != null
					&& customerProfileService.isEmailUnique(customerProfile.getEmail())) {
				returnedCustomer.setEmail(customerProfile.getEmail());
			}
			if (customerProfile.getFirstName() != null) {
				returnedCustomer.setFirstName(customerProfile.getFirstName());
			}
			if (customerProfile.getMiddleName() != null) {
				returnedCustomer.setMiddleName(customerProfile.getMiddleName());
			}
			if (customerProfile.getLastName() != null) {
				returnedCustomer.setLastName(customerProfile.getLastName());
			}
			if (customerProfile.getAddress() != null) {
				returnedCustomer.setAddress(customerProfile.getAddress());
			}
			if (customerProfile.getPhoneNumber() != null) {
				returnedCustomer.setPhoneNumber(customerProfile.getPhoneNumber());
			}
			customerProfileService.update(returnedCustomer);
			return ResponseEntity.ok(returnedCustomer);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping
	public ResponseEntity<?> delete(@RequestParam String email) throws Exception {
		Customer returnedCustomer = customerProfileService.getByEmail(email);
		if (returnedCustomer == null) {
			return ResponseEntity.badRequest().body("User not found");
		} else {
			customerProfileService.delete(returnedCustomer.getCustomerId());
			return ResponseEntity.ok().build();
		}
	}
}
