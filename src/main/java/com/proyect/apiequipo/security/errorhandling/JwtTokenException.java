package com.proyect.apiequipo.security.errorhandling;

public class JwtTokenException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JwtTokenException(String msg) {
        super(msg);
    }


}
