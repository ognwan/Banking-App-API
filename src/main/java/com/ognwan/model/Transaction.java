/**
 * 
 */
package com.ognwan.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Transactions")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private long txnId;
	private BigDecimal amount = BigDecimal.ZERO;
	private LocalDateTime timestamp = LocalDateTime.now();
	private String details;
	@ManyToOne
	@JoinColumn(name = "accountNumber", referencedColumnName = "accountNumber")
	@JsonIgnore
	private Account account;
}
