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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author gerry
 * @version 1.0
 * 
 */
@Entity
@Table(name = "ACCOUNTS")
@Data
@ToString
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
	@NotNull
	private BigDecimal balance = BigDecimal.ZERO;
	@JsonIgnore
	private LocalDateTime createdAt;
	@JsonIgnore
	private LocalDateTime lastUpdated;
	@ManyToOne
	@JoinColumn(name = "customerId", referencedColumnName = "customerId")
	private Customer customer;

	public BigDecimal withdraw(BigDecimal amount) {
		balance = balance.subtract(amount);
		setLastUpdated(LocalDateTime.now());
		return balance;
	}

	public BigDecimal deposit(BigDecimal amount) {
		balance = balance.add(amount);
		setLastUpdated(LocalDateTime.now());
		return balance;
	}

}
