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
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired
	private CustomerService customerService;

	@PostMapping("/register")
	public ResponseEntity<?> createProfile(@RequestBody @Validated Customer customer, BindingResult error) {
		try {
			if (error.hasErrors()) {
				Map<String, Object> errors = new HashMap<>();
				for (FieldError fieldError : error.getFieldErrors()) {
					errors.put(fieldError.getField(), fieldError.getDefaultMessage());
				}
				return ResponseEntity.badRequest().body(errors);
			}
			Customer newcustomer = customerService.create(customer);
			return ResponseEntity.created(null).body(newcustomer);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String email, String password) {
		if (customerService.validate(email, password)) {
			return ResponseEntity.ok().body("Login Successful");
		}
		return ResponseEntity.badRequest().body("Incorrect username or password, please try again");
	}

	@PostMapping("/change-password")
	public ResponseEntity<?> login(@RequestParam String email, String oldPassword, String newPassword,
			String confirmNewPassword) {
		try {
			customerService.changePassword(email, oldPassword, newPassword, confirmNewPassword);
			return ResponseEntity.ok().body("Password changed successfully");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
