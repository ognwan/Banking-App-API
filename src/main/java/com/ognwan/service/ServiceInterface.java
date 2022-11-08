/**
 * 
 */
package com.ognwan.service;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.http.ResponseEntity;

import com.ognwan.exceptions.UserNotFoundException;

/**
 * @author gerry
 * @version 1.0
 * 
 */
public interface ServiceInterface<T> {
	public T create(T t) throws Exception;

	public T update(T t);

	public T getById(long id) throws UserNotFoundException, AccountNotFoundException;

	void delete(long id);

}
