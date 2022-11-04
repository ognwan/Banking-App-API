/**
 * 
 */
package com.ognwan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class CustomerProfileController {
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

	@PostMapping
	public ResponseEntity<?> createProfile(@RequestBody @Validated CustomerProfile customerProfile,
			BindingResult error) {
		try {
			if (error.hasErrors()) {
				Map<String, Object> errors = new HashMap<>();
				for (FieldError fieldError : error.getFieldErrors()) {
					errors.put(fieldError.getField(), fieldError.getDefaultMessage());
				}
				return ResponseEntity.badRequest().body(errors);
			}
			CustomerProfile newCustomerProfile = customerProfileService.create(customerProfile);
			return ResponseEntity.created(null).body(newCustomerProfile);
		} catch (Exception e) {
			System.out.println(e.getClass());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
