package org.weso.acota.core.exceptions;

import java.io.FileNotFoundException;

/**
 * 
 * This class models a generic runtime exception.
 */
public class AcotaModelException extends RuntimeException {

	private static final long serialVersionUID = -2568767031496911589L;

	public AcotaModelException(Exception e) {
		super(e);
	}

	public AcotaModelException(String string) {
		super(string);
	}

	public AcotaModelException(Exception e, String string) {
		super(string, e);
	}

	public AcotaModelException(String string, Exception e) {
		super(string, e);
	}

	public AcotaModelException(FileNotFoundException e, String string) {
		super(string, e);
	}

}
