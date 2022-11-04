/**
 * 
 */
package com.ognwan.model;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author gerry
 * @version 1.0
 * 
 */
@Entity
@Table(name = "Customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CustomerProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private long id;
	@NotNull
	@NonNull
	@NotBlank
	@NotEmpty(message = "name field cannot be empty")
	@Size(min = 2, message = "name must have 2 or more characters")
	private String firstName;
	private String middleName;
	@NotNull
	@NonNull
	@NotEmpty(message = "name field cannot be empty")
	@Size(min = 2, message = "name must have 2 or more characters")
	private String lastName;
	@NonNull
	@JsonIgnore
	private String userName;
	@JsonIgnore
	private String password;
	@NotNull
	@NotEmpty(message = "email field cannot be blank")
	@NonNull
	@Email(message = "invalid email")
	private String email;
	@NotNull
	@NonNull
	private String address;
	@NotNull
	@NotBlank(message = "phone number is required")
	@NonNull
	@Pattern(regexp = "^\\d{10}$", message = "phone number is invalid")
	private String phoneNumber;
	@NotNull(message = "Date of Birth field cannot be blank")
	@NonNull
	private LocalDate DoB;
	@JsonIgnore
	private LocalDateTime created;
	@JsonIgnore
	private LocalDateTime lastUpdated;

	public String generatePassword() {
		char[] possibleCharacters = (new String(
				"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?"))
				.toCharArray();
		String password = RandomStringUtils.random(8, 0, possibleCharacters.length - 1, false, false,
				possibleCharacters, new SecureRandom());
		return password;

	}
}
