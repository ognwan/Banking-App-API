/**
 * 
 */
package com.ognwan.service;

import javax.security.auth.login.AccountNotFoundException;

import com.ognwan.exceptions.UserNotFoundException;

/**
 * @author gerry
 * @version 1.0
 * @param <A>
 * 
 */
public interface ServiceInterface<T, A> {
	public T create(T t, A a) throws Exception;

	public T update(T t, long id) throws Exception;

	public T getById(long id) throws UserNotFoundException, AccountNotFoundException;

	void delete(long id);

}
