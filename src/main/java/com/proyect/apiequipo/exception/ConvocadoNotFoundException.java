package com.proyect.apiequipo.exception;

public class ConvocadoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConvocadoNotFoundException() {
        super();
    }
 
    public ConvocadoNotFoundException(String message) {
        super(message);
    }
 
    public ConvocadoNotFoundException(long id) {
        super("Convocado not found: " + id);
    }
}
