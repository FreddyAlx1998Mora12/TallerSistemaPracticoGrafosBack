package edu.academic.taller.DAOs.impl;

import edu.academic.taller.DAOs.AdapterDao;
import edu.academic.taller.models.Ruta;
import edu.academic.taller.models.list.MyLinkedList;
import edu.academic.taller.models.list.Node;

public class RutaDao extends AdapterDao<Ruta> {

	private Ruta ruta;
	private MyLinkedList listRuta;

	public RutaDao() {
		super(Ruta.class);
	}

	// Singleton Pattern
	public Ruta getRuta() {
		if (ruta == null) {
			ruta = new Ruta();
		}
		return ruta;
	}

	public void setRuta(Ruta station) {
		this.ruta = station;
	}

	public MyLinkedList getLista_ruta() {
		if (listRuta == null) {
			this.listRuta = listAll();
		}
		return listRuta;
	}

	/*
	 * ########################################## CreateReadUpdateDelete
	 * ##########################################
	 */
	// Create
	public boolean save() throws Exception {
		// incrementar el id
		int id = getLista_ruta().getLength() + 1;
		// validaciones de id

		// asigna el id
		ruta.setIdRuta(id);
		this.persist(ruta);

		// actualiza la lista para ProyectoCensoDAO
		this.listRuta = listAll();

		return true;
	}

	// Read -> obtener listado
	// Read -> obtener un dato por id
	public Ruta obtenerRuta(Integer index) throws IndexOutOfBoundsException, Exception {
		return this.get(index);
	}

	// Update
	public boolean updatebyId(int index, Ruta dato_censo) throws Exception {
		this.merge(dato_censo, index);
		this.listRuta = listAll();

		return true;
	}

	public void deletebyId(int index) throws Exception {
		this.delete(index);
		actualizar_lista_Ids();
		this.listRuta = listAll();
	}

	private void actualizar_lista_Ids() throws Exception {
		int contador = 0; // Comenzar desde 1
		int contadorGenerador = 0;
		Node<Ruta> current = getLista_ruta().getHeader(); // Suponiendo que tienes un método para obtener la cabeza
															// de la lista
		Ruta mensajero;

		while (current != null) {
			contador++; // cuenta 1
			mensajero = current.getInfo(); // obtiene el objeto de NODO
			mensajero.setIdRuta(contador); // actualiza el id del Objeto

			current.setInfo(mensajero); // Asigna o guarda esa info en su respectivo Nodo
			current = current.getNext(); // Moverse al siguiente nodo
		}

//		this.UpdateFile(listRuta); // Actualiza el archivo si es necesario
	}

	/*
	 * Metodos de ordenacion y busqueda 1. Metodos de busqueda
	 */

	// busqueda lineal
	public Ruta buscarporID(int id) throws Exception {

		Ruta censo = null;
		MyLinkedList listita = listAll();

		if (!listAll().isEmptyLinkedList()) {
			Ruta[] aux = (Ruta[]) listita.toArray();

			for (int i = 0; i < aux.length; i++) {

				if (aux[i].getIdRuta() == id) {
					censo = aux[i];
				}
			}
		}

		return censo;

	}

	// busqueda lineal
	public Ruta buscarporDescripcion(String text) throws Exception {

		Ruta censo = null;
		MyLinkedList listita = listAll();

		if (!listAll().isEmptyLinkedList()) {
			Ruta[] aux = (Ruta[]) listita.toArray();

			for (int i = 0; i < aux.length; i++) {

				if (aux[i].getDescripcion().toLowerCase().compareToIgnoreCase(text) == 0) {
					censo = aux[i];
					break;
				}
			}
		}

		return censo;
	}

	public boolean existeRuta(Ruta r) throws Exception {

		boolean censo = false;
		MyLinkedList listita = listAll();

		if (!listAll().isEmptyLinkedList()) {
			Ruta[] aux = (Ruta[]) listita.toArray();

			for (int i = 0; i < aux.length; i++) {

				if (aux[i].getDescripcion().trim()
						.compareToIgnoreCase(r.getDescripcion().trim()) == 0) {
					if (aux[i].getLatitud() == r.getLatitud()) {
						if (aux[i].getLongitud() == r.getLatitud()) {
							censo = true;
							break;
						}
					}
				}
			}
		}

		return censo;
	}

	// Ordenar lista segun el tipo y el atributo o criterio
	public MyLinkedList ordenarLista(Integer type_order, String atributo) {
		// Traemos todos los datos para ordenar
		MyLinkedList listita = listAll();

		if (!listita.isEmptyLinkedList()) {
			// Convierte a un array para recorrer
			Ruta[] lista = (Ruta[]) listita.toArray();

			// resetea la lista para posterior devolver la lista ordenada
			listita.reset();

			for (int i = 1; i < lista.length; i++) {
				Ruta aux = lista[i]; // Valor a ordenar
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

	private Boolean verify(Ruta a, Ruta b, Integer type_order, String atributo) {
		if (type_order == 1) { // Ascendente
			if (atributo.equalsIgnoreCase("id")) {
				return a.getIdRuta() > b.getIdRuta();
			}
		} else {
			if (atributo.equalsIgnoreCase("id")) {
				return a.getIdRuta() < b.getIdRuta();
			}
		}
		return false;
	}

	/*
	 * Metodo de Ordenacion QuickSort
	 */
	public MyLinkedList ordenarListaQuickSort(int tipo_orden, String atributo) {

		MyLinkedList list = getLista_ruta();
		Ruta[] array = (Ruta[]) list.toArray();

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
	private void quickSort(Ruta[] array, int bajo, int alto, int type_order, String atributo) {
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
	private int particion_array(Ruta[] array, int bajo, int alto, int type_order, String atributo) {
		// Tomamos el último elemento como pivote
		// Para comparar con los valores bajos
		Ruta pivote = array[alto];

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

	private void intercambio(Ruta[] arr, int index1, int index2) {
		Ruta temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}
	
	/**
	 * Metodo que permite crear la distancia entre un punto u otro utilizando la Distancia Geodésica (Haversine)
	 * @param v1
	 * @param v2
	 * @return
	 * @throws Exception
	 */
	public Float calcularDistancia(Ruta v1, Ruta v2) throws Exception{
		// Convertir de grados a radianes
        double lat1Rad = Math.toRadians(v1.getLatitud().doubleValue());
        double lon1Rad = Math.toRadians(v1.getLongitud().doubleValue());
        double lat2Rad = Math.toRadians(v2.getLatitud().doubleValue());
        double lon2Rad = Math.toRadians(v2.getLongitud().doubleValue());

        // Diferencias de latitud y longitud
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Fórmula de Haversine
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distancia en kilómetros, 6371.0 equivale al radio del planeta, resultado en km
        Double distancia = (double) (Math.round((6371.0 * c)*100.0) / 100.0);
        Float floatValue = Float.valueOf(distancia.floatValue());

        return floatValue;
	}
}
