package com.proyect.apiequipo.exception;

public class PartidoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PartidoNotFoundException() {
        super();
    }
 
    public PartidoNotFoundException(String message) {
        super(message);
    }
 
    public PartidoNotFoundException(long id) {
        super("Partido not found: " + id);
    }

	
	

}
