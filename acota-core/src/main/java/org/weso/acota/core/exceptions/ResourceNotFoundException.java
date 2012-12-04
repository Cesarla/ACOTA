/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.weso.acota.core.exceptions;

/**
 * 
 * This class models an exception occurred during the access to a resource.
 */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8359039552258803769L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
