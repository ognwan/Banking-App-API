/**
 * 
 */
package com.ognwan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ognwan.exceptions.UserNotFoundException;
import com.ognwan.model.CustomerProfile;
import com.ognwan.serviceImplementation.CustomerProfileService;

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
public class ProfileMaintenanceController {
	@Autowired
	private CustomerProfileService customerProfileService;

	@GetMapping
	public List<CustomerProfile> getAllCustomers() {
		return customerProfileService.listAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerProfile> getCustomerById(@PathVariable Long id) {
		try {
			CustomerProfile returnedCustomerProfile = customerProfileService.getById(id);
			return ResponseEntity.ok(returnedCustomerProfile);
		} catch (UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/update-profile")
	public ResponseEntity<?> updateCustomerDetails(@RequestBody CustomerProfile customerProfile,
			@RequestParam String email) {

		CustomerProfile returnedCustomer = customerProfileService.getByEmail(email);

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
			System.out.println(returnedCustomer);
			customerProfileService.update(returnedCustomer);
			return ResponseEntity.ok(returnedCustomer);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
