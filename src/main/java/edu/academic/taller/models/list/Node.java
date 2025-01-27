package edu.academic.taller.models.list;

public class Node<E> {
//	Utilizaremos doblemente enlazada

	private E info;
	private Node<E> next, prev;

//	Constructores
	public Node() {
		this.info = null;
		this.next = null;
		this.prev = null;
	}

	public Node(E dato) {
		this.info = dato;
		this.next = null;
		this.prev = null;
	}

	/**
	 * Instanciar nodo con informacion o dato y el enlace al siguiente
	 * 
	 * @param info
	 * @param next
	 */
	public Node(E info, Node<E> next) {
		this.info = info;
		this.next = next;
		this.prev = null;
	}

	/**
	 * Instanciar un nodo con 3 args
	 * 
	 * @param info dato
	 * @param prev nodo anterior
	 * @param next nodo siguiente
	 */
	public Node(E info, Node<E> prev, Node<E> next) {
		this.info = info;
		this.prev = prev;
		this.next = next;
	}

//	Getters and Setters
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

	public Node<E> getPrev() {
		return prev;
	}

	public void setPrev(Node<E> prev) {
		this.prev = prev;
	}
}
