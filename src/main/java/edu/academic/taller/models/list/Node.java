package edu.academic.taller.models.list;

/**
 * Clase Generica que identifica un Nodo o Node generico
 * @param <E> dato generico que representa un elemento
 */
public class Node<E> {

	// E es el tipo de Dato, y info la informacion como tal, lo que contiene
	private E info; 
	// next, es el siguiente nodo a enlazar o nodo vecino	
	private Node<E> next;
	
	// Constructor para instanciar nodos
	public Node(){
		this.info = null;
		this.next = null;
	}
	
	/**
	 * Instanciacion de la clase Node
	 * @param info es la informacion del nodo
	 */
	public Node(E info) {
		this.info = info;
		this.next = null;
	}
	
	public Node(E info, Node<E> next) {
		this.info = info;
		this.next = next;
	}

	// Getters and Setters 
	public E getInfo() {
		return info;
	}

	public void setInfo(E info) {
		this.info = info;
	}

	public Node<E> getNext() {
		return next;
	}

	public void setNext(Node<E> next) {
		this.next = next;
	}
	
}
