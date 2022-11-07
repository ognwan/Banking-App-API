/**
 * 
 */
package com.ognwan.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author gerry
 * @version 1.0
 * 
 */
@Entity
@Table(name = "ACCOUNTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private long accountNumber;
	@NonNull
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	@JsonIgnore
	private BigDecimal balance;
	@JsonIgnore
	private LocalDateTime createdAt;
	@ManyToOne
//	@JsonIgnore
	@JoinColumn(name = "customerId", referencedColumnName = "customerId")
	private Customer customer;
}
