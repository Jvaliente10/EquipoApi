package com.proyect.apiequipo.exception;

public class EquipoNotFoundException extends RuntimeException  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EquipoNotFoundException() {
        super();
    }
 
    public EquipoNotFoundException(String message) {
        super(message);
    }
 
    public EquipoNotFoundException(long id) {
        super("Equipo not found: " + id);
    }

}
