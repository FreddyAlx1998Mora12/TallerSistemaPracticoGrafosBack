package edu.academic.taller.exceptions;


/**
 * Clase que hereda de excepcion que lanzara un mensaje que elevara a la clase superior
 */
public class MyListException extends Exception{

	public MyListException() {
	}
	
	public MyListException(String msg) {
		super(msg); // pasa el parametro mensaje a la clase padre Exception
	}
	
}
