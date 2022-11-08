/**
 * 
 */
package com.ognwan.controller;

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
	private CustomerService customerService;

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
		try {
			Customer returnedcustomer = customerService.getById(id);
			return ResponseEntity.ok(returnedcustomer);
		} catch (UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/update-profile")
	public ResponseEntity<?> updateCustomerDetails(@RequestBody Customer customer, @RequestParam String email) {
		Customer returnedCustomer = customerService.getByEmail(email);

		try {
			if (returnedCustomer == null) {
				throw new Exception("User does not exist");
			}

			if (customer.getEmail() != null && customerService.isEmailUnique(customer.getEmail())) {
				returnedCustomer.setEmail(customer.getEmail());
			}
			if (customer.getFirstName() != null) {
				returnedCustomer.setFirstName(customer.getFirstName());
			}
			if (customer.getMiddleName() != null) {
				returnedCustomer.setMiddleName(customer.getMiddleName());
			}
			if (customer.getLastName() != null) {
				returnedCustomer.setLastName(customer.getLastName());
			}
			if (customer.getAddress() != null) {
				returnedCustomer.setAddress(customer.getAddress());
			}
			if (customer.getPhoneNumber() != null) {
				returnedCustomer.setPhoneNumber(customer.getPhoneNumber());
			}
			customerService.update(returnedCustomer);
			return ResponseEntity.ok(returnedCustomer);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/delete/{customerId}")
	public ResponseEntity<?> delete(@PathVariable long customerId) throws Exception {
		Customer returnedCustomer = customerService.getById(customerId);
		if (returnedCustomer == null) {
			return ResponseEntity.badRequest().body("User not found");
		} else {
			customerService.delete(customerId);
			return ResponseEntity.ok().build();
		}
	}
}
