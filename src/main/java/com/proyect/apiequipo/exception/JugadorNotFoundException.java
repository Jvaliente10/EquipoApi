package com.proyect.apiequipo.exception;

public class JugadorNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JugadorNotFoundException() {
        super();
    }
 
    public JugadorNotFoundException(String message) {
        super(message);
    }
 
    public JugadorNotFoundException(long id) {
        super("Jugador not found: " + id);
    }


}

