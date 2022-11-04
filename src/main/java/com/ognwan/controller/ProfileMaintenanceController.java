/**
 * 
 */
package com.ognwan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
