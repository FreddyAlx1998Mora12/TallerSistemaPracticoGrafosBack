package edu.academic.taller.models.list;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Lista Doblemente Enlazada
 * 
 * @param <E>
 */
public class MyLinkedList<E> {
	private Integer size;
	private Node<E> header, last;

	public MyLinkedList() {
		this.size = 0;
		this.header = null;
		this.last = null;
	}

	public MyLinkedList(Node<E> header) {
		this.header = header;
	}

	public Node<E> getHeader() {
		return header;
	}

	public void setHeader(Node<E> header) {
		this.header = header;
	}

	public Node<E> getLast() {
		return last;
	}

	public void setLast(Node<E> last) {
		this.last = last;
	}
	
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

//	#########################################################
	public boolean isEmptyLinkedList() {
		return (this.header == null || this.size == 0);
	}

	public void addHeader(E dato) {

		Node<E> nodo;

		if (this.isEmptyLinkedList()) {
			nodo = new Node<>(dato);
			this.header = nodo;
			this.size++;

		} else {

			Node<E> aux_header = this.header; // El nodo cabeza, hace una imagen en la nueva variable
			nodo = new Node<E>(dato, aux_header); // siguiente enlace
			aux_header.setPrev(nodo); // enlace anterior
			this.header = nodo;
			this.size++;
		}
	}

	/**
	 * Funcion para aniadir un nodo al final o cola de la lista doblemente enlazada
	 * 
	 * @param dato
	 */
	private void addLast(E dato) {

		Node<E> nodo_last;

		if (isEmptyLinkedList()) { // si esta vacio
			addHeader(dato);
		} else if (getLength() == 1) { // si solo contiene un elemento, es decir solo tiene cabez
			nodo_last = new Node<E>(dato, header, null);
			this.header.setNext(nodo_last); // enlaza el primero con el siguiente
			this.setLast(nodo_last);
			size++;
		} else {
			nodo_last = new Node<E>(dato, this.last, null); // instanciamos, obviamente tiene un enlace null
			this.last.setNext(nodo_last); // el ultimo elemento o nodo cola enlaza siguient con el nuevo nod
			this.last = nodo_last; // actualizo ultimo
			this.size++;
		}

	}

	public void add(E dato) {
		addLast(dato);
	}

	/**
	 * Function to get a Node, based on your index of position
	 * 
	 * @param index , index of position
	 * @return a Node<E>
	 */
	public Node<E> getNode(int index) throws Exception, IndexOutOfBoundsException {

		// Verificar si la lista esta vacia
		if (isEmptyLinkedList()) {
			throw new Exception("Error, no se puede encontrar un elemento en una Lista vacia");
		} else if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Error, indice fuera del rango, el indice " + index + " no existe");
		} else if (index == 0) {
			return this.header;
		} else if (index == (this.size - 1)) { // si el indice es el ultimo
			return this.last;
		} else {
			Node<E> mensajero = header; // empiezo por el head
			int cont = 0; // contador para controlar
			while (cont < index) {
				cont++;
				mensajero = mensajero.getNext(); // obten el siguiente nodo enlazad
			}
			return mensajero;
		}
	}

	/**
	 * Function to get info of the first Node
	 * 
	 * @return informacion o dato del NODO
	 * @throws MyListException lanza la excepcion cuando la lista esta vacia y no
	 *                         devuelve ningun dato
	 */

	private E getFirst() throws Exception {
		if (isEmptyLinkedList()) {
			throw new Exception("Error, no se puede obtener el primer elemento, lista vacia.");
		}
		return header.getInfo();
	}

	/**
	 * Function to get info of the last Node
	 * 
	 * @return info o dato del NODO
	 * @throws MyListException lanza la excepcion cuando la lista esta vacia y no
	 *                         devuelve ningun dato
	 */
	private E getLastE() throws Exception {
		if (isEmptyLinkedList()) {
			throw new Exception("Error, no se puede obtener el ultimo elemento, lista vacia.");
		}
		return last.getInfo();
	}

	/**
	 * Metodo que obtiene la informacion de un nodo
	 * 
	 * @param index
	 * @return
	 * @throws Exception                 -> excepcion general
	 * @throws IndexOutOfBoundsException -> indice fuera de rango
	 */
	public E get(Integer index) throws Exception, IndexOutOfBoundsException {
		if (isEmptyLinkedList()) {
			throw new Exception("Error, la lista esta vacia");
		} else if (index < 0 || index >= size.intValue()) {
			throw new IndexOutOfBoundsException(
					"Error, indice fuera de rango, esta intentando obtener el indice " + index);
		} else if (index == 0) {
			return getFirst();
		} else if (index == (size.intValue() - 1)) {
			return getLastE();
		} else {
			Node<E> search = this.header;
			int cont = 0;
			while (cont < index) { //
				cont++;
				search = search.getNext(); //
			}
			return search.getInfo();
		}
	}

	public int getLength() {
		return this.size.intValue();
	}

	/**
	 * Aniade un Nodo en dicha posicion
	 * 
	 * @param info  = tipo de dato y la informacion
	 * @param index = position
	 * @throws MyListException           = exception de lista, hereda de Exception
	 * @throws IndexOutOfBoundsException = excepcion de indice fuera del rango
	 */
	public void addPos(int index, E info) throws Exception, IndexOutOfBoundsException {
		if (isEmptyLinkedList() || index == 0) { // aniade al inicio
			addHeader(info);
		} else if (index == getLength()) { // aniade al final
			addLast(info);
		} else {

			Node<E> nodo_posicion = getNode(index); // nodo para enlazar despues del nuevo
			Node<E> nodo_anterior = nodo_posicion.getPrev();

			Node<E> nuevo_nodo = new Node<E>(info, nodo_posicion.getPrev(), nodo_posicion); // nodo creado y enlazado

			nodo_anterior.setNext(nuevo_nodo);
			nodo_posicion.setPrev(nuevo_nodo);

			this.size++;
		}
	}

	public void reset() {
		this.header = null;
		this.last = null;
		this.size = 0;
	}

	private void removeLast() throws Exception {
		// verificar que la lista este vacia
		if (isEmptyLinkedList()) {
			throw new Exception("Error, no puede eliminar datos de una lista vacia.");
		} else {
			// capturar el ultimo elemento de la lista, desenlazar
			Node<E> nodo_last = getNode((getLength() - 1));
			this.last = nodo_last.getPrev(); // obtiene el nodo anterior al nodo ultimo
			this.last.setNext(null); // rompe enlace
			this.size--;
		}
	}

	private void removeFirst() throws Exception {
		if (isEmptyLinkedList()) {
			throw new Exception("Error, no puede eliminar datos de una lista vacia.");
		} else {
			// capturar el primer elemento de la lista, desenlazar
			Node<E> nodo_first = header;
			if (nodo_first.getNext() == null) { // si el primer elemento no tiene siguiente es decir es el unico
				reset();
			} else {
				Node<E> nodo_next_first = nodo_first.getNext();
				this.header = nodo_next_first;
				this.header.setPrev(null); // rompe enlace
				this.size--;
			}

		}
	}

	public void remove(int index) throws Exception, IndexOutOfBoundsException {
		if (isEmptyLinkedList()) {
			throw new Exception("Lista vacia, no puede eliminar elementos");
		} else if (index == 0) { // si tiene elementos y el indice es 0
			removeFirst();
		} else if (index == (getLength() - 1)) { // si el indice es el ultimo elemento
			removeLast();
		} else {
			Node<E> dato = getNode(index); // el elemento que desea eliminar
			Node<E> dato_prev = dato.getPrev(); // elemento anterior al que desea eliminar
			Node<E> dato_next = dato.getNext(); // elemento vecino al que desea eliminar

			dato_prev.setNext(dato_next); // al mensajero previo al nodo que se va a elimnar, se enlaza con el siguiente
			dato_next.setPrev(dato_prev);
			this.size--;
		}
	}

//		Actualizar informacion de un elemento de la lista 
	public void updateElement(int index, E info) throws Exception, IndexOutOfBoundsException {
		Node<E> nodo;
		if (info == null) {
			throw new Exception("No puede ingresar valores nulos");
		} else if (index < 0 || index >= getLength()) {
			throw new IndexOutOfBoundsException("Indice fuera de rango para modficar");
		} else if (index == 0) { // cabecera
			this.header.setInfo(info);
		} else if (index == (getLength() - 1)) { // ultimo
			this.last.setInfo(info);
		} else {
			nodo = getNode(index);
			nodo.setInfo(info);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Datos de Lista: ");
		try {
			Node<E> help = header;
			while (help != null) {
				sb.append(help.getInfo()).append(" - "); // agrega al constructor de cadena sb
				help = help.getNext();// pasa al siguiente nodo
			}
		} catch (Exception e) {
			sb.append(e.getMessage());
		}
		return sb.toString();
	}

//		Conversion de MyLinkedList a ARRAY de tipo de DATO E
	public E[] toArray() {
//			defino una matrix donde se va a almacenar los valores
		E[] matrix = null;

		if (!isEmptyLinkedList()) { // verifico que la lista no este vacia
			Class clazz = header.getInfo().getClass();
			matrix = (E[]) Array.newInstance(clazz, size); // convierte el objeto en E[]
			Node<E> aux = header; // obtengo el encabezado de la lista
			for (int i = 0; i < this.size; i++) { // recorre la lista enlazad
				matrix[i] = aux.getInfo(); // agrega el objeto con la informacion
				aux = aux.getNext();
			}
		}
		return matrix;
	}

	public MyLinkedList<E> tolist(E[] matrix) {
		reset(); // es como crear una instancia vacia de lista enlazada
		for (int i = 0; i < matrix.length; i++) {
			this.add(matrix[i]);
		}
		return this; // objeto actual es decir un MylinkedList
	}

	private Boolean compare(Object a, Object b, Integer type) {
		switch (type) {
		case 0:
			if (a instanceof Number) {
				Number a1 = (Number) a;
				Number b1 = (Number) b;
				return a1.doubleValue() > b1.doubleValue();
			} else {
				return (a.toString()).compareTo(b.toString()) > 0;
			}
			// break;

		default:
			if (a instanceof Number) {
				Number a1 = (Number) a;
				Number b1 = (Number) b;
				return a1.doubleValue() < b1.doubleValue();
			} else {

				return (a.toString()).compareTo(b.toString()) < 0;
			}
		}

	}

	// compare class
	private Boolean atrribute_compare(String attribute, E a, E b, Integer type) throws Exception {
		return compare(exist_attribute(a, attribute), exist_attribute(b, attribute), type);
	}

	private Object exist_attribute(E a, String attribute) throws Exception {
		Method method = null;
		attribute = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
		attribute = "get" + attribute;
		for (Method aux : a.getClass().getMethods()) {
			if (aux.getName().contains(attribute)) {
				method = aux;
				break;
			}
		}
		if (method == null) {
			for (Method aux : a.getClass().getSuperclass().getMethods()) {
				if (aux.getName().contains(attribute)) {
					method = aux;
					break;
				}
			}
		}
		if (method != null) {
			return method.invoke(a);
		}

		return null;
	}

	/*
	 * Metodos de ordenamiento
	 */
	public MyLinkedList<E> quick_Sort(String attribute, Integer type) throws Exception {
		if (!isEmptyLinkedList()) {
			E[] lista = this.toArray();
			reset();
			quicksortHelper(lista, 0, lista.length - 1, attribute, type);
			this.tolist(lista);
		}
		return this;
	}

	private void quicksortHelper(E[] lista, int low, int high, String attribute, Integer type) throws Exception {
		if (low < high) {
			int pi = particion(lista, low, high, attribute, type);
			quicksortHelper(lista, low, pi - 1, attribute, type);
			quicksortHelper(lista, pi + 1, high, attribute, type);
		}
	}

	private int particion(E[] lista, int low, int high, String attribute, Integer type) throws Exception {
		E pivot = lista[high];
		int i = (low - 1); // Indice del elemento más pequeño

		for (int j = low; j < high; j++) {
			if (atrribute_compare(attribute, lista[j], pivot, type)) {
				i++;
				E temp = lista[i];
				lista[i] = lista[j];
				lista[j] = temp;
			}
		}

		E temp = lista[i + 1];
		lista[i + 1] = lista[high];
		lista[high] = temp;

		return i + 1;
	}

	public MyLinkedList<E> quick_Sort_byOrder(int orderType) throws Exception {
		if (!isEmptyLinkedList()) {
			E[] lista = this.toArray();
			reset();
			quicksortHelper(lista, 0, lista.length - 1, orderType);
			this.tolist(lista);
		}
		return this;
	}

	private void quicksortHelper(E[] lista, int low, int high, int orderType) throws Exception {
		if (low < high) {
			int pi = particion(lista, low, high, orderType);
			quicksortHelper(lista, low, pi - 1, orderType);
			quicksortHelper(lista, pi + 1, high, orderType);
		}
	}

	private int particion(E[] lista, int low, int high, int orderType) throws Exception {
		E pivot = lista[high];
		int i = (low - 1); // Indice del elemento más pequeño

		for (int j = low; j < high; j++) {
			if (compare(lista[j], pivot, orderType)) {
				i++;
				// intercambia
				E temp = lista[i];
				lista[i] = lista[j];
				lista[j] = temp;
			}
		}

		E temp = lista[i + 1];
		lista[i + 1] = lista[high];
		lista[high] = temp;

		return i + 1;
	}

	// MergeSort
	public MyLinkedList<E> merge_Sort(String attribute, Integer type) throws Exception {
		if (!isEmptyLinkedList()) {
			E[] lista = this.toArray();
			reset();
			lista = mergeSortHelper(lista, attribute, type);
			this.tolist(lista);
		}
		return this;
	}

	private E[] mergeSortHelper(E[] lista, String attribute, Integer type) throws Exception {
		if (lista.length <= 1) {
			return lista;
		}

		int middle = lista.length / 2;
		E[] left = Arrays.copyOfRange(lista, 0, middle);
		E[] right = Arrays.copyOfRange(lista, middle, lista.length);

		left = mergeSortHelper(left, attribute, type);
		right = mergeSortHelper(right, attribute, type);

		return mergeAttribute(left, right, attribute, type);
	}

	private E[] mergeAttribute(E[] left, E[] right, String attribute, Integer type) throws Exception {
		E[] result = (E[]) new Object[left.length + right.length];
		int i = 0, j = 0, k = 0;

		while (i < left.length && j < right.length) {
			if (atrribute_compare(attribute, left[i], right[j], type)) {
				result[k++] = left[i++];
			} else {
				result[k++] = right[j++];
			}
		}

		while (i < left.length) {
			result[k++] = left[i++];
		}

		while (j < right.length) {
			result[k++] = right[j++];
		}

		return result;
	}

	public MyLinkedList<E> merge_Sort_byOrder(int orderType) throws Exception {
		if (!isEmptyLinkedList()) {
			E[] lista = this.toArray();
			reset();
			lista = mergesortHelper(lista, orderType);
			this.tolist(lista);
		}
		return this;
	}

	private E[] mergesortHelper(E[] lista, int orderType) throws Exception {
		if (lista.length <= 1) {
			return lista;
		}

		int middle = lista.length / 2;
		E[] left = Arrays.copyOfRange(lista, 0, middle);
		E[] right = Arrays.copyOfRange(lista, middle, lista.length);

		left = mergesortHelper(left, orderType);
		right = mergesortHelper(right, orderType);

		return merge(left, right, orderType);
	}

	private E[] merge(E[] izq, E[] der, int orderType) throws Exception {
		E[] result = (E[]) new Object[izq.length + der.length];
		int i = 0, j = 0, k = 0;

		while (i < izq.length && j < der.length) {
			if (compare(izq[i], der[j], orderType)) {
				result[k++] = izq[i++];
			} else {
				result[k++] = der[j++];
			}
		}

		while (i < izq.length) {
			result[k++] = izq[i++];
		}

		while (j < der.length) {
			result[k++] = der[j++];
		}

		return result;
	}

//	Metodo Shell Sort
	public MyLinkedList<E> shell_Sort(String attribute, Integer type) throws Exception {
		if (!isEmptyLinkedList()) {
			E[] lista = this.toArray();
			int n = lista.length;
			// gap -> intervalo de separacion
			for (int gap = n / 2; gap > 0; gap /= 2) {
				for (int i = gap; i < n; i++) {
					E temp = lista[i];
					int j;

					// Compara elementos
					for (j = i; j >= gap && atrribute_compare(attribute, lista[j - gap], temp, type); j -= gap) {
						lista[j] = lista[j - gap];
					}
					lista[j] = temp;
				}
			}

			this.tolist(lista);
		}
		return this;
	}

	public MyLinkedList<E> shellSort_byOrder(int orderType) throws Exception {
		if (!isEmptyLinkedList()) {
			E[] lista = this.toArray();
			reset();
			lista = shellSortHelper(lista, orderType);
			this.tolist(lista);
		}
		return this;
	}

	private E[] shellSortHelper(E[] lista, int orderType) throws Exception {
		int n = lista.length;
		// Comienza con un gran gap y lo reduce a la mitad
		for (int gap = n / 2; gap > 0; gap /= 2) {
			// Realiza una inserción por salto (gap)
			for (int i = gap; i < n; i++) {
				E temp = lista[i];
				int j = i;

				while (j >= gap && compare(lista[j - gap], temp, orderType)) {
					lista[j] = lista[j - gap];
					j -= gap;
				}
				lista[j] = temp;
			}
		}
		return lista;
	}
}
