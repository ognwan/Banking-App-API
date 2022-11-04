/**
 * 
 */
package com.ognwan.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired
	private CustomerProfileService customerProfileService;

	@PostMapping("/register")
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

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String email, String password) {
		if (customerProfileService.validate(email, password)) {
			return ResponseEntity.ok().body("login Successful");
		}
		return ResponseEntity.badRequest().body("incorrect username or password, please try again");
	}
}
