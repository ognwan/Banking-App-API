/**
 * 
 */
package com.ognwan.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private long accountNumber;
	@NonNull
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@NotNull
	private BigDecimal balance = BigDecimal.ZERO;
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime createdAt = LocalDateTime.now();
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime lastUpdated;
	@JsonIgnore
	private boolean isDeleted = false;
	@ManyToOne
	@JoinColumn(name = "customerId", referencedColumnName = "customerId")
	@JsonIgnore
	private Customer customer;
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Transaction> transactions;

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
