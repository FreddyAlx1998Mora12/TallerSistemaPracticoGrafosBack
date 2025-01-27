package edu.academic.taller.DAOs.impl;

import edu.academic.taller.DAOs.AdapterDao;
import edu.academic.taller.models.Estacion;
import edu.academic.taller.models.Ruta;
import edu.academic.taller.models.list.MyLinkedList;
import edu.academic.taller.models.list.Node;

public class EstacionDao extends AdapterDao<Estacion> {

	private Estacion estacion;
	private MyLinkedList lista_estacion;

	public EstacionDao() {
		super(Estacion.class);
	}

	// Singleton Pattern
	public Estacion getEstacion() {
		if (estacion == null) {
			estacion = new Estacion();
		}
		return estacion;
	}

	public void setEstacion(Estacion station) {
		this.estacion = station;
	}

	public MyLinkedList getLista_estacion() {
//		System.out.println("Lista de estaciones en metodo dao getlistE: "+lista_estacion);
		if (lista_estacion == null) {
			this.lista_estacion = listAll();
		}
		return lista_estacion;
	}

	// Apply Singleton Pattern
	public void setLista_estacion(MyLinkedList lista_estacion) {
		this.lista_estacion = lista_estacion;
	}

	/*
	 * ########################################## CreateReadUpdateDelete
	 * ##########################################
	 */
	// Create
	public boolean save() throws Exception {
		// incrementar el id
		int id = getLista_estacion().getLength() + 1;
		// validaciones de id
		try {
			estacion.setIdEstacion(id);
			this.persist(estacion);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// asigna el id

		// actualiza la lista para ProyectoCensoDAO
		this.lista_estacion = listAll();

		return true;
	}

	// Read -> obtener listado
	// Read -> obtener un dato por id
	public Estacion obtenerEstacion(Integer index) throws IndexOutOfBoundsException, Exception {
		return this.get(index);
	}

	// Update
	public boolean updatebyId(int index, Estacion dato_censo) throws Exception {
		this.merge(dato_censo, index);
		this.lista_estacion = listAll();

		return true;
	}

	public void deletebyId(int index) throws Exception {
		this.delete(index);
		actualizar_lista_Ids();
		this.lista_estacion = listAll();
	}

	private void actualizar_lista_Ids() throws Exception {
		int contador = 0; // Comenzar desde 1
		int contadorGenerador = 0;
		Node<Estacion> current = getLista_estacion().getHeader(); // Suponiendo que tienes un método para obtener la
																	// cabeza
																	// de la lista
		Estacion mensajero;

		while (current != null) {
			contador++; // cuenta 1
			mensajero = current.getInfo(); // obtiene el objeto de NODO
			mensajero.setIdEstacion(contador); // actualiza el id del Objeto

			current.setInfo(mensajero); // Asigna o guarda esa info en su respectivo Nodo
			current = current.getNext(); // Moverse al siguiente nodo
		}

		this.UpdateFile(lista_estacion); // Actualiza el archivo si es necesario
	}

	/*
	 * Metodos de ordenacion y busqueda 1. Metodos de busqueda
	 */

	// busqueda lineal
	public Estacion buscarporID(int id) throws Exception {

		Estacion censo = null;
		MyLinkedList listita = listAll();

		if (!listAll().isEmptyLinkedList()) {
			Estacion[] aux = (Estacion[]) listita.toArray();

			for (int i = 0; i < aux.length; i++) {

				if (aux[i].getIdEstacion() == id) {
					censo = aux[i];
				}
			}
		}
//		System.out.println("Regresa del dao el siguiente objeto:" + censo.getCodigoEstacion());
		return censo;

	}

	// Ordenar lista segun el tipo y el atributo o criterio
	public MyLinkedList ordenarLista(Integer type_order, String atributo) {
		// Traemos todos los datos para ordenar
		MyLinkedList listita = listAll();

		if (!listita.isEmptyLinkedList()) {
			// Convierte a un array para recorrer
			Estacion[] lista = (Estacion[]) listita.toArray();

			// resetea la lista para posterior devolver la lista ordenada
			listita.reset();

			for (int i = 1; i < lista.length; i++) {
				Estacion aux = lista[i]; // Valor a ordenar
				int j = i - 1; // indice anterior

				// compara el elemento en la posicion i con cada uno de los indices anterior
				while (j >= 0 && (verify(lista[j], aux, type_order, atributo))) {
					lista[j + 1] = lista[j--];
				}
				lista[j + 1] = aux;
			}
			listita.tolist(lista);
		}
		return listita;
	}

	private Boolean verify(Estacion a, Estacion b, Integer type_order, String atributo) {
		if (type_order == 1) { // Ascendente
			if (atributo.equalsIgnoreCase("codigoEstacion")) {
				return a.getCodigoEstacion().toLowerCase().compareTo(b.getCodigoEstacion().toLowerCase()) > 0;
			}
		} else {
			if (atributo.equalsIgnoreCase("codigoEstacion")) {
				return a.getCodigoEstacion().toLowerCase().compareTo(b.getCodigoEstacion().toLowerCase()) < 0;
			}
		}
		return false;
	}

	/*
	 * Metodo de Ordenacion QuickSort
	 */
	public MyLinkedList ordenarListaQuickSort(int tipo_orden, String atributo) {

		MyLinkedList list = getLista_estacion();
		Estacion[] array = (Estacion[]) list.toArray();

		list.reset();

		// Método QuickSort para ordenar el arreglo
		// Parametros , array, valor indic bajo, valor indic alto (ultimo), tipo orden,
		// atributo
		quickSort(array, 0, array.length - 1, tipo_orden, atributo);

		list.tolist(array);

		return list;
	}

	// Método de QuickSort que ordena el arreglo
	/**
	 * Funcion sin valor de retorno que realiza la ordenacion de una lista por tipo
	 * de orden
	 * 
	 * @param array      arreglo del Objeto a ordenar
	 * @param bajo       valor bajo del array principio
	 * @param alto       valor alto del array final
	 * @param type_order tipo de orden 1 asc, 0 desc
	 * @param atributo   criterio
	 */
	private void quickSort(Estacion[] array, int bajo, int alto, int type_order, String atributo) {
		// Controlar de modo que debido a la recursion el metodo se acaba cuando bajo es
		// >= alto
		if (bajo < alto) {

			// Variable indice del pivote
			// Particiona y ordena
			int pi = particion_array(array, bajo, alto, type_order, atributo);

			// Recursivamente ordenamos las dos sublistas
			quickSort(array, bajo, pi - 1, type_order, atributo); // Sublista izquierda
			quickSort(array, pi + 1, alto, type_order, atributo); // Sublista derecha
		}
	}

	// Método para realizar la partición del arreglo, retorna indice del pivote
	private int particion_array(Estacion[] array, int bajo, int alto, int type_order, String atributo) {
		// Tomamos el último elemento como pivote
		// Para comparar con los valores bajos
		Estacion pivote = array[alto];

		// Índice para el menor elemento
		int i = bajo - 1;

		// Comparamos cada elemento con el pivote
		// desde el primer elemento hasta el ultimo elemento de la lista
		for (int j = bajo; j < alto; j++) {
			// Compara los elementos y verifica, ojo
			// Recordemos que para verificar que el elemento pivote es mayor que el objeto
			// primero
			// debo comparar primero el pivote
			if (verify(pivote, array[j], type_order, atributo)) {
				i++; // Aumentamos el índice de menor elemento para seguir comparando con el pivote

				// Intercambiamos elementos ya que existe que el elemento efectivamente es mayor
				// al pivote
				intercambio(array, i, j);

			}
		}

		// El pivote se ubica en su posición natural
		intercambio(array, i + 1, alto);

		return i + 1; // Devolvemos el índice del pivote
	}

	private void intercambio(Estacion[] arr, int index1, int index2) {
		Estacion temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}

//	public void agregarRuta(Ruta r) throws Exception {
//		// Agrega la ruta a la lista
//		System.out.println("En metodo de estacion dao, esta pasando la siguiente ruta: " + r.getIdRuta() + ", "
//				+ r.getDireccionPuntoParada());
//		System.out.println(
//				"En metodo de estacion dao, esta obteniendo el objeto estacion: " + this.estacion.getCodigoEstacion());
//		System.out.println("En metodo de estacion dao, despues de modificar: " + getEstacion().getCodigoEstacion()
//				+ ", " + getEstacion().getListRutas().getLength());
//		try {
//			
//			this.getEstacion().getListRutas().add(r);
//			// Modifica
//			this.updatebyId(this.getEstacion().getIdEstacion()-1, this.getEstacion());
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//
//		// Trae la lista actualizada
//		this.lista_estacion = listAll();
//	}
}
