package edu.academic.taller.models.list;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import edu.academic.taller.exceptions.MyListException;

/**
 * List Linked Clase generica de elementos, constituye una coleccion de nodos Su
 * estructura se compone de 2 partes, cabeza y cola Cabeza o head viene a ser el
 * primer elemento de la lista Cola o last, el ultimo elemento size un
 * identificador de la cantidad de nodos que se almacena en la lista
 * 
 * @param E -> es el tipo de dato que va a almacenar la listlinked
 */
public class MyLinkedList<E> {

	private Node<E> header;
	private Node<E> last;
	private Integer size;
	
	// Getters and setters
	public Node<E> getHeader() {
		return header;
	}

	public void setHeader(Node<E> header) {
		this.header = header;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setLast(Node<E> last) {
		this.last = last;
	}

	//	Constructor para asignacion por defecto
	// Primera instancia al crear objeto
	/**
	 * Crea una instancia de tipo Linked List con valores por defecto
	 */
	public MyLinkedList() {
		this.header = null;
		this.last = null;
		this.size = 0;
	}
	

	public MyLinkedList(Node<E> header) {
		this.header = header;
	}

//	Operaciones
	public boolean isEmptyLinkedList() {
		return (this.header == null || this.size == 0);
	}

	/**
	 * Funcion para inserter un nuevo elemento en la cabecera Cada elemento se
	 * aniade a la cabecera, mientras que el ultimo elemento sera su cola
	 * 
	 * @param dato ,el dato a aniadir
	 */
	public void addHeader(E dato) {

		Node<E> nodo;

		if (this.isEmptyLinkedList()) {
			// Si la lista esta vacia crea un nodo con next null
			nodo = new Node<>(dato);
			// aniade el nuevo nodo a la cabecera
			this.header = nodo;
			this.size++;

		} else {
			// Si no esta vacia, agrega el nuevo nodo a la cabecera,
			// y el nodo existente, enlazalo a next

			Node<E> aux_header = this.header; // El nodo cabeza, hace una imagen en la nueva variable
			nodo = new Node<E>(dato, aux_header); // instancio el nodo a aniadir y el siguiente enlace
			this.header = nodo;
			this.size++;
		}
	}

	/**
	 * Funcion para aniadir un nodo al final o cola
	 * 
	 * @param dato
	 */
	private void addLast(E dato) {

		Node<E> nodo_last;

		if (isEmptyLinkedList()) { // si esta vacio
			addHeader(dato); // crea un nuevo nodo en la lista
		} else {
			nodo_last = new Node<E>(dato); // instanciamos, obviamente tiene un enlace null
			if(last == null) { // quiere decir que solo tiene cabeza
				this.header.setNext(nodo_last);
				this.last = nodo_last;
			}
			this.last.setNext(nodo_last);
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
	public Node<E> getNode(int index) throws MyListException, IndexOutOfBoundsException {

		// Verificar si la lista esta vacia
		if (isEmptyLinkedList()) {
			throw new MyListException("Error, no se puede encontrar un elemento en una Lista vacia");
		} else if (index < 0 || index >= this.size.intValue()) { // Verificar si el indice no sea >= a la longitud de la list, o
														// menor que 0
			throw new IndexOutOfBoundsException("Error, indice fuera del rango, el indice " + index + " no existe");
		} else if (index == 0) { // si el indice es 0, sera la cabeza de la lista
			return this.header;
		} else if (index == (this.size.intValue() - 1)) { // si el indice es el ultimo
			return this.last;
		} else { // si el indice es menor, condicion que se va a llegar por las condicionales
					// anteriormente evaluads busca la posicion

			Node<E> mensajero = header; // empiezo por el head
			int cont = 0; // contador para controlar
			while (cont < index) { // mientras el contador sea menor al indice, cont = 0
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

	private E getFirst() throws MyListException {
		if (isEmptyLinkedList()) {
			throw new MyListException("Error, no se puede obtener el primer elemento, lista vacia.");
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
	private E getLast() throws MyListException {
		if (isEmptyLinkedList()) {
			throw new MyListException("Error, no se puede obtener el ultimo elemento, lista vacia.");
		}
		return last.getInfo();
	}
	
	public E get(int index) throws MyListException, IndexOutOfBoundsException{
		if(isEmptyLinkedList()) {
			throw new MyListException("Error, la lista esta vacia");
		}else if(index < 0 || index >= size.intValue()) {
			throw new IndexOutOfBoundsException("Error, indice fuera de rango, esta intentando obtener el indice "+index);
		}else if(index == 0){
			return getFirst();
		}else if(index == (size.intValue()-1)) {
			return getLast();
		}else {
			// Recorremos la lista desde el nodo buscador
			Node<E> search = header;
			int cont = 0;
			while (cont < index) {
				cont++;
				search = search.getNext();
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
	public void add(int index, E info) throws MyListException, IndexOutOfBoundsException {
		if (isEmptyLinkedList() || index == 0) { // aniade al inicio
			addHeader(info);
		} else if (index == this.size.intValue()) { // aniade al final
			addLast(info);
		} else {
//			Obtengo los nodos en la respectiva posicion
			Node<E> nodo_anterior = getNode(index - 1); // nodo anterior para enlazar
			Node<E> nodo_posicion = nodo_anterior.getNext(); // nodo para enlazar despues del nuevo
			
			
			Node<E> nuevo_nodo = new Node<E>(info, nodo_posicion); // nodo creado y enlazado
			nodo_anterior.setNext(nuevo_nodo);
			this.size++;
		}
	}

	public void reset() {
		this.header = null;
		this.last = null;
		this.size = 0;
	}

	private void removeLast() throws MyListException {
		// verificar que la lista este vacia
		if (isEmptyLinkedList()) {
			throw new MyListException("Error, no puede eliminar datos de una lista vacia.");
		} else if(getLength() == 1) { // si solo existe un elemento y no tiene cola
			reset();
		}else {
			// capturar el ultimo elemento de la lista, desenlazar
			Node<E> nodo_last = getNode((getLength()-2));
			this.last = nodo_last;
			this.last.setNext(null);
			this.size--;
		}
	}

	private void removeFirst() throws MyListException {
		if (isEmptyLinkedList()) {
			throw new MyListException("Error, no puede eliminar datos de una lista vacia.");
		}else if(getLength()==1) {
			reset();
		}
		else {
			// capturar el primer elemento de la lista, desenlazar
			Node<E> nodo_first = header;
			Node<E> nodo_next_first = nodo_first.getNext();
			this.header = nodo_next_first;
			this.size--;
			
			// caso de que existen 2 elementos y se elimine el primero no habra cola
			if(getLength() == 1) {
				last = null;
			}		

		}
	}

	public void remove(int index) throws MyListException, IndexOutOfBoundsException {
		if (isEmptyLinkedList()) {
			throw new MyListException("Lista vacia, no puede eliminar elementos");
		} else if (index == 0) { // si tiene elementos y el indice es 0
			removeFirst();
		} else if (index == (this.size - 1)) { // si el indice es el ultimo elemento
			removeLast();
		} else {// iterar hasta encontrar el n elemento
			Node<E> mensajero = getNode(index); // el elemento que desea eliminar
			Node<E> mensajero_prev = getNode(index - 1); // elemento anterior al que desea eliminar
			Node<E> mensajero_next = mensajero.getNext(); // elemento vecino al que desea eliminar

			mensajero_prev.setNext(mensajero_next); // al mensajero previo al nodo que se va a elimnar, se enlaza con el
													// siguiente
			this.size--;
		}
	}

	
//	Actualizar un elemento de la lista 
	public void updateNode(int index, E info) throws MyListException {
		if(info == null) {
			throw new MyListException("No puede modificar valores con valores nulos");
		}else if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Indice fuera de rango para modficar");
		}else if (isEmptyLinkedList()) {
			throw new MyListException("Lista vacia, no puede modificar elementos");
		} else if (index == 0) { // si tiene elementos y el indice es 0
			this.header.setInfo(info);
		} else if (index == (this.size - 1)) { // si el indice es el ultimo elemento
			this.last.setInfo(info);
		} else {
			Node<E> aux = getNode(index); 
			aux.setInfo(info);
		}
	}
	
	@Override
	public String toString(){
	    StringBuilder sb = new StringBuilder("Datos Lista:");
	    try{
	        Node<E> help = header;
	        while(help != null){
	            sb.append(help.getInfo()).append(" - "); // agrega al constructor de cadena sb
	            help = help.getNext();// pasa al siguiente nodo 
	        }
	    }catch (Exception e){
	        sb.append(e.getMessage());
	    }
	    return sb.toString();
	}

//	Conversion de MyLinkedList a ARRAY de tipo de DATO E
	public E[] toArray() {
//		defino una matrix donde se va a almacenar los valores
		E[] matrix = null;

		if (!isEmptyLinkedList()) { // verifico que la lista no este vacia
			Class clazz = header.getInfo().getClass();
			matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, size); // convierte el objeto en E[]
			Node<E> aux = header; // obtengo el encabezado de la lista
			for (int i = 0; i < this.size; i++) { //recorre la lista enlazad
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
		return this; //objeto actual es decir un MylinkedList
	}
	
	// metodo que permite ordenar una lista	utilizando nodo
	public MyLinkedList<E> order(String attribut, Integer type) throws Exception{
		
		if (!isEmptyLinkedList()) {
			// Obtengo el dato nodo header
			E dato = header.getInfo();
			// verifico si el dato que estoy obteniendo es una instancia de ....
			if( dato instanceof Object) {
				// almaceno en un array del tipo de dato generic
				E[] lista = this.toArray();
				reset();
				
				for (int i = 1; i < lista.length; i++) {
					E aux = lista[i];
					int j = i - 1; //indice anterior a i, empieza en 0
					while (j >= 0 && compare_atribute(attribut, lista[j], aux, type)) {
						lista[j+1] = lista[j--]; // desplaza elementos hacia la derecha
					}
					lista[j+1] = aux; // insercion del dato
				}
				
				this.tolist(lista);
			}
			
			
		}else {
			// Esta la lista vacia			
		}
		
		return this; // return MylinkedList
	}
	
	private Boolean compare(Object a, Object b, Integer type) throws Exception{
		
		switch (type) {
		case 0: // orden ascendente
			
			if(a instanceof Number) { // si a y b son instancias de Objeto tipo Number,
				Number a1 = (Number) a;
				Number b1 = (Number) b;
				
				return a1.doubleValue() > b1.doubleValue();
			}else
				return (a.toString()).compareTo(b.toString()) > 0; //compareTo retorna un valor int, 1 si es true y 0 false
				
//			break;
		default:
			
			if(a instanceof Number) { // si a y b son instancias de numeros,
				Number a1 = (Number) a; // Convierte a un objeto Number
				Number b1 = (Number) b;
				
				return a1.doubleValue() < b1.doubleValue();
			}else
				return (a.toString()).compareTo(b.toString()) < 0; //compareTo retorna un valor int, 1 si es true y 0 false
			
//			break;
		}
		
	}
	
	// compara class
	private Boolean compare_atribute(String atribut, E a, E b, Integer type) throws Exception {
		return compare(exist_attribute(a, atribut), exist_attribute(b, atribut), type);
//		return false;
	}
	
	// tiempo de ejecucion que verifica si el atributo que se esta buscando existe en el objeto
	// 	
	private Object exist_attribute(E a, String attribut) throws Exception{
		Method method = null;
		
		// capitalizar el atributo que se esta buscando comparar
		attribut = attribut.substring(0, 1).toUpperCase() + attribut.substring(1);
		attribut = "get" + attribut; // el getPersona, getApellido -> es el metodo que se desea obtener
		
		// 1. Obtiene la clase, ese decir el objeto, y luego los metodos que teng en su clase
		// 2. Almacena temporalmente en la variable aux cada metodo que tenga la clase
		
		for(Method aux : a.getClass().getMethods()) {
			// 3. Verifica si el metodo que esta en aux contiene lo que se esta deseando buscar 
			// Ejmpl: getId o getNumber, o getNmbre ... etc
			if (aux.getName().contains(attribut)) {
				// 4. obten este metodo y rompe el ciclo
				method = aux;
				break;
			}
		}
		
		// si no encontro en su clase, busca en su clase superior o clase padre
		if(method == null) {
			for(Method aux : a.getClass().getSuperclass().getMethods()) {
				if(aux.getName().contains(attribut)) {
					method = aux;
					break;
				}
			}
		}
		
		// Si se tiene el metodo, invocame, llama este metodo y trae lo que retorna
		// por ejemplo se encontro el metodo getId, al decirle invoke, estoy accediendo al objeto y llamando el getId(),
		// Es como si se tratara de int idper = p.getId()
		if(method != null) {
			return method.invoke(a);
		}
		
		return null;
	}
	
}
