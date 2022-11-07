/**
 * 
 */
package com.ognwan.service;

import java.util.List;

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

	public List<T> listAll();

	public T getById(long id) throws UserNotFoundException;

	ResponseEntity<T> delete(long id);

}
