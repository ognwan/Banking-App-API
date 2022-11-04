/**
 * 
 */
package com.ognwan.exceptions;

/**
 * @author gerry
 * @version 1.0
 * 
 */
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(long id) {
		super("user not found with id: " + id);
	}

}
