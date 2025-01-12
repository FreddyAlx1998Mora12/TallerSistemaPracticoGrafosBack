package edu.academic.taller.DAOs.impl;

import edu.academic.taller.DAOs.AdapterDAO;
import edu.academic.taller.models.Estacion;
import edu.academic.taller.models.list.MyLinkedList;
import edu.academic.taller.models.list.Node;

public class EstacionDao extends AdapterDAO<Estacion>{
	
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
		return lista_estacion;
	}
	
	// Apply Singleton Pattern
	public void setLista_estacion(MyLinkedList lista_estacion) {
		this.lista_estacion = lista_estacion;
	}
	
	/*
	 * ########################################## 
	 * CreateReadUpdateDelete
	 * ##########################################
	 */
	// Create
	public boolean save() throws Exception {
		// incrementar el id
		int id = getLista_estacion().getLength() + 1;
		// validaciones de id

		// asigna el id
		estacion.setIdEstacion(id);
		this.persist(estacion);

		// actualiza la lista para ProyectoCensoDAO
		this.lista_estacion = listAll();

		return true;
	}

	// Read -> obtener listado
	// Read -> obtener un dato por id
	public Estacion obtenerFamilia(Integer index) throws IndexOutOfBoundsException, Exception {
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
		Node<Estacion> current = getLista_estacion().getHeader(); // Suponiendo que tienes un método para obtener la cabeza
																// de la lista
		Estacion mensajero;

		while (current != null) {
			contador++; // cuenta 1
			mensajero = current.getInfo(); // obtiene el objeto de NODO
			mensajero.setIdEstacion(contadorGenerador); // actualiza el id del Objeto

			current.setInfo(mensajero); // Asigna o guarda esa info en su respectivo Nodo
			current = current.getNext(); // Moverse al siguiente nodo
		}

		this.UpdateFile(lista_estacion); // Actualiza el archivo si es necesario
	}

	/*
	 * Metodos de ordenacion y busqueda 1. Metodos de busqueda
	 */

	// busqueda lineal
	public Familia buscarporIDCenso(int id) throws Exception {

		Familia censo = null;
		MyLinkedList listita = listAll();

		if (!listAll().isEmptyLinkedList()) {
			Familia[] aux = (Familia[]) listita.toArray();

			for (int i = 0; i < aux.length; i++) {

				if (aux[i].getIdCenso() == id) {
					censo = aux[i];
				}
			}
		}

		return censo;

	}
	
	public MyLinkedList buscarporDireccion(String direccion) throws Exception {

//		Familia censo = null;
		MyLinkedList listita = listAll();
		
		if (!listAll().isEmptyLinkedList()) {
			Familia[] aux = (Familia[]) listita.toArray();
			listita.reset();
			for (int i = 0; i < aux.length; i++) {

				if (aux[i].getDireccion().toLowerCase().equalsIgnoreCase(direccion.toLowerCase())) {
					listita.add(aux[i]);
				}
			}
		}

		return listita;

	}
	
	//Busqueda Lineal
	public MyLinkedList buscarporGeneradors(boolean criterio) throws Exception {

		Familia censo = null;
		MyLinkedList listita = listAll();

		if (!listAll().isEmptyLinkedList()) {
			Familia[] aux = (Familia[]) listita.toArray();
			listita.reset();

			for (int i = 0; i < aux.length; i++) {

				if (aux[i].isHaveGenerador() == criterio) {
					listita.add(aux[i]);
				}
			}
		}

		return listita;

	}
	
	public MyLinkedList buscarporNroIntegrantes(int n_integrantes) throws Exception {

		Familia censo = null;
		MyLinkedList listita = listAll();

		if (!listAll().isEmptyLinkedList()) {
			Familia[] aux = (Familia[]) listita.toArray();
			listita.reset();

			for (int i = 0; i < aux.length; i++) {

				if (aux[i].getNroIntegrantesFamilia() == n_integrantes) {
					listita.add(aux[i]);
				}
			}
		}

		return listita;

	}
	
	public Familia buscarporDescripcion(String descripcion) throws Exception {

		Familia censo = null;
		MyLinkedList listita = listAll();

		if (!listAll().isEmptyLinkedList()) {
			Familia[] aux = (Familia[]) listita.toArray();

			for (int i = 0; i < aux.length; i++) {

				if (aux[i].getDescripcion().toLowerCase().equalsIgnoreCase(descripcion.toLowerCase())) {
					censo = aux[i];
				}
			}
		}

		return censo;

	}

	// busqueda binaria
	/*
	 * 1. Lista ordenada (realizado) 2. Divide la lista en mitades mas ppequenias
	 * segun lo que se busca (realizado)
	 */
	public Familia buscarporDescripcion_Binario(String valor) throws Exception {
		// busqueda por Descripcion
		// 1. primero obtenemos la lista ordenada de a - z
		MyLinkedList list_ordenad = ordenarLista(1, "descripcion");

		// 2. convertimos a un array
		Familia[] array = (Familia[]) list_ordenad.toArray();

		// 3. reseteamos la lista
		list_ordenad.reset();

		// 4. Realizamos la búsqueda binaria
		int bajo = 0;
		int alto = array.length - 1;
		try {			
			while (bajo <= alto) {
				int medio = (bajo + alto) / 2;
				int comparacion = valor.compareToIgnoreCase(array[medio].getDescripcion());
				
				// Si encontramos el valor
				if (comparacion == 0) {
					return array[medio]; // Retorna el objeto Familia que tiene la descripción buscada y rompe el ciclo
				} else if (comparacion > 0) {
					// Si el valor es mayor, busca en la mitad derecha
					bajo = medio + 1;
				} else {
					// Si el valor es menor, busca en la mitad izquierda
					alto = medio - 1;
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getLocalizedMessage());
		}

		// Si no se encuentra el valor, podemos devolver null o el objeto adecuado.
		return null; // No se encontró el valor

//		return list_ordenad;

	}
	
	// linked list nro integrantes
	public MyLinkedList buscarporNroIntegrantes_Binario(int valor) throws Exception {
	    // 1. Primero obtenemos la lista ordenada por número de integrantes
	    MyLinkedList list_ordenada = ordenarLista(1, "numero_integrante");

	    // 2. Convertimos la lista a un array
	    Familia[] array = (Familia[]) list_ordenada.toArray();

	    // 3. Reseteamos la lista
	    list_ordenada.reset();

	    // 4. Realizamos la búsqueda binaria
	    int bajo = 0;
	    int alto = array.length - 1;

	    // 5. Búsqueda binaria adaptada para encontrar todas las coincidencias
	    while (bajo <= alto) {
	        int medio = (bajo + alto) / 2;
	        int comparacion = Integer.compare(valor, array[medio].getNroIntegrantesFamilia());

	        // Si encontramos el valor
	        if (comparacion == 0) {
	            // Agregar el objeto encontrado a la lista de resultados
	        	list_ordenada.add(array[medio]);

	            // Buscar también en la mitad izquierda y derecha del elemento encontrado
	            int izquierda = medio - 1;
	            int derecha = medio + 1;

	            // Buscar hacia la izquierda
	            while (izquierda >= bajo && array[izquierda].getNroIntegrantesFamilia() == valor) {
	            	list_ordenada.add(array[izquierda]);
	                izquierda--;
	            }

	            // Buscar hacia la derecha
	            while (derecha <= alto && array[derecha].getNroIntegrantesFamilia() == valor) {
	            	list_ordenada.add(array[derecha]);
	                derecha++;
	            }

	            break;  // No es necesario seguir buscando, ya que hemos encontrado todas las coincidencias
	        } else if (comparacion > 0) {
	            // Si el valor es mayor, busca en la mitad derecha
	            bajo = medio + 1;
	        } else {
	            // Si el valor es menor, busca en la mitad izquierda
	            alto = medio - 1;
	        }
	    }

	    // Retorna la lista con las coincidencias encontradas
	    return list_ordenada;
	}

	
	// linked list havegeneradors
	public MyLinkedList buscarporHaveGenerador_Binario(boolean valor) throws Exception {
	    // 1. Primero obtenemos la lista ordenada
	    MyLinkedList list_ordenada = ordenarLista(1, "haveGenerador");

	    // 2. Convertimos la lista a un array
	    Familia[] array = (Familia[]) list_ordenada.toArray();

	    // 3. Reseteamos la lista
	    list_ordenada.reset();

	    for (Familia familia : array) {
	        if (familia.isHaveGenerador() == valor) {
	        	list_ordenada.add(familia); // Agregar el objeto a la lista de resultados
	        }
	    }

	    // Si no se encuentra el valor, la lista estará vacía
	    return list_ordenada;
	}

	
	// linked list Direccion 
	public MyLinkedList buscarporDireccion_Binario(String direccion) throws Exception {
	    // 1. Primero obtenemos la lista ordenada por dirección (de la a a la z -> ascendente)
	    MyLinkedList list_ordenada = ordenarLista(1, "direccion");

	    // 2. Convertimos la lista a un array
	    Familia[] array = (Familia[]) list_ordenada.toArray();

	    // 3. Reseteamos la lista
	    list_ordenada.reset();

	    // 4. Realizamos la búsqueda binaria
	    int bajo = 0;
	    int alto = array.length - 1;
	    

	    // 5. Búsqueda binaria adaptada para encontrar todas las coincidencias
	    while (bajo <= alto) {
	        int medio = (bajo + alto) / 2;
	        int comparacion = direccion.compareToIgnoreCase(array[medio].getDireccion());

	        // Si encontramos el valor
	        if (comparacion == 0) {
	            // Agregar el objeto encontrado a la lista de resultados
	        	list_ordenada.add(array[medio]);

	            // Buscar también en la mitad izquierda y derecha del elemento encontrado
	            int izquierda = medio - 1;
	            int derecha = medio + 1;

	            // Buscar hacia la izquierda
	            while (izquierda >= bajo && direccion.compareToIgnoreCase(array[izquierda].getDireccion()) == 0) {
	            	list_ordenada.add(array[izquierda]);
	                izquierda--;
	            }

	            // Buscar hacia la derecha
	            while (derecha <= alto && direccion.compareToIgnoreCase(array[derecha].getDireccion()) == 0) {
	            	list_ordenada.add(array[derecha]);
	                derecha++;
	            }

	            break;  // No es necesario seguir buscando, ya que hemos encontrado todas las coincidencias
	        } else if (comparacion > 0) {
	            // Si el valor es mayor, busca en la mitad derecha
	            bajo = medio + 1;
	        } else {
	            // Si el valor es menor, busca en la mitad izquierda
	            alto = medio - 1;
	        }
	    }

	    // Retorna la lista con las coincidencias encontradas
	    return list_ordenada;
	}


	// Ordenar lista segun el tipo y el atributo o criterio
	public MyLinkedList ordenarLista(Integer type_order, String atributo) {
		// Traemos todos los datos para ordenar
		MyLinkedList listita = listAll();

		if (!listita.isEmptyLinkedList()) {
			// Convierte a un array para recorrer
			Familia[] lista = (Familia[]) listita.toArray();

			// resetea la lista para posterior devolver la lista ordenada
			listita.reset();

			for (int i = 1; i < lista.length; i++) {
				Familia aux = lista[i]; // Valor a ordenar
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

	private Boolean verify(Familia a, Familia b, Integer type_order, String atributo) {
		if (type_order == 1) { // Ascendente
			if (atributo.equalsIgnoreCase("direccion")) {
				return a.getDireccion().toLowerCase().compareTo(b.getDireccion().toLowerCase()) > 0;
			} else if (atributo.equalsIgnoreCase("haveGenerador")) {
				return a.isHaveGenerador() == true || b.isHaveGenerador() == true;
			} else if (atributo.equalsIgnoreCase("numero_integrante")) {
				return a.getNroIntegrantesFamilia() > b.getNroIntegrantesFamilia();
			} else if (atributo.equalsIgnoreCase("descripcion")) {
				return a.getDescripcion().toLowerCase().compareTo(b.getDescripcion().toLowerCase()) > 0;
			}
		} else {
			if (atributo.equalsIgnoreCase("direccion")) {
				return a.getDireccion().toLowerCase().compareTo(b.getDireccion().toLowerCase()) < 0;
			} else if (atributo.equalsIgnoreCase("haveGenerador")) {
				return a.isHaveGenerador() == false && b.isHaveGenerador() == false;
			} else if (atributo.equalsIgnoreCase("numero_integrante")) {
				return a.getNroIntegrantesFamilia() < b.getNroIntegrantesFamilia();
			} else if (atributo.equalsIgnoreCase("descripcion")) {
				return a.getDescripcion().toLowerCase().compareTo(b.getDescripcion().toLowerCase()) < 0;
			}
		}
		return false;
	}

	/*
	 * Metodo de Ordenacion QuickSort
	 */
	public MyLinkedList ordenarListaQuickSort(int tipo_orden, String atributo) {
		
		MyLinkedList list = getLista_estacion();
		Familia[] array = (Familia[]) list.toArray();

		list.reset();

		// Método QuickSort para ordenar el arreglo
		// Parametros , array, valor indic bajo, valor indic alto (ultimo), tipo orden, atributo
		quickSort(array, 0, array.length - 1, tipo_orden, atributo);

		list.tolist(array);

		return list;
	}

	// Método de QuickSort que ordena el arreglo
	/**
	 * Funcion sin valor de retorno que realiza la ordenacion de una lista por 
	 * tipo de orden
	 * @param array arreglo del Objeto a ordenar
	 * @param bajo valor bajo del array principio
	 * @param alto valor alto del array final
	 * @param type_order tipo de orden 1 asc, 0 desc
	 * @param atributo criterio
	 */
	private void quickSort(Familia[] array, int bajo, int alto, int type_order, String atributo) {
		// Controlar de modo que debido a la recursion el metodo se acaba cuando bajo es >= alto
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
	private int particion_array(Familia[] array, int bajo, int alto, int type_order, String atributo) {
		// Tomamos el último elemento como pivote
		// Para comparar con los valores bajos		
		Familia pivote = array[alto];

		// Índice para el menor elemento
		int i = bajo - 1;

		// Comparamos cada elemento con el pivote
		// desde el primer elemento hasta el ultimo elemento de la lista		
		for (int j = bajo; j < alto; j++) {
			// Compara los elementos y verifica, ojo
			// Recordemos que para verificar que el elemento pivote es mayor que el objeto primero
			// debo comparar primero el pivote		
			if (verify(pivote,array[j], type_order, atributo)) {
				i++; // Aumentamos el índice de menor elemento para seguir comparando con el pivote

				// Intercambiamos elementos ya que existe que el elemento efectivamente es mayor al pivote
				intercambio(array, i, j);

			}
		}

		// El pivote se ubica en su posición natural
		intercambio(array, i + 1, alto);

		return i + 1; // Devolvemos el índice del pivote
	}

	private void intercambio(Familia[] arr, int index1, int index2) {
		Familia temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}

	/*
	 * Método de Ordenación ShellSort
	 */
	public MyLinkedList ordenarListaShellSort(int tipo_orden, String atributo) {

	    MyLinkedList list = getLista_estacion();

	    Familia[] array = (Familia[]) list.toArray();

	    Familia[] arrayOrdenado = shellSort(array, tipo_orden, atributo);

	    list.tolist(arrayOrdenado);

	    return list;
	}

	/*
	 * Método de ordenación ShellSort
	 */
	private Familia[] shellSort(Familia[] array, int type_order, String atributo) {
	    Familia[] arr_ordenado = null;
	    // Comienza con un valor de gap que es dividir la longitud del arreglo / 2 y va reduciéndolo en mitads
	    for (int gap = (array.length / 2); gap > 0; gap /= 2) {
	        // Realizamos una inserción usando el gap actual
	        for (int i = gap; i < array.length; i++) {
	            Familia temp = array[i];
	            int j = i;
	            
	            // Compara los elementos según el tipo de orden
	            while (j >= gap && verify(array[j - gap], temp, type_order, atributo)) {
	                array[j] = array[j - gap]; // Desplazamos el elemento hacia la derecha
	                j -= gap;
	            }
	            
	            array[j] = temp; // Colocamos el elemento en la posición correcta
	        }
	    }
	    
	    arr_ordenado = array;
	    return arr_ordenado;
	}
	
	/*
	 * Método de Ordenación MergeSort
	 */
	public MyLinkedList ordenarListaMergeSort(int tipo_orden, String atributo) {
	    // Obtenemos la lista desordenada
	    MyLinkedList list = getLista_estacion();

	    // Convertimos la lista a un arreglo de Familia[]
	    Familia[] array = (Familia[]) list.toArray();

	    // Llamamos al método MergeSort para ordenar el arreglo
	    mergeSort(array, 0, array.length - 1, tipo_orden, atributo);

	    // Convertimos el arreglo ordenado de nuevo a MyLinkedList
	    list.tolist(array);

	    return list;
	}

	
	private void mergeSort(Familia[] array, int bajo, int alto, int type_order, String atributo) {
	    if (bajo < alto) {
	        // Encuentra el punto medio del arreglo
	        int medio = (bajo + alto) / 2;

	        // Ordenamos recursivamente las dos mitades
	        mergeSort(array, bajo, medio, type_order, atributo);
	        mergeSort(array, medio + 1, alto, type_order, atributo);

	        // Realiza el merge (fusion)
	        fusionar_arrays(array, bajo, medio, alto, type_order, atributo);
	    }
	}

	// Metodo que fusiona arreglos
	private void fusionar_arrays(Familia[] array, int bajo, int medio, int alto, int type_order, String atributo) {
	    // Calcular tamaños de las dos sublistas
	    int tamanio_list1 = medio - bajo + 1;
	    int tamanio_list2 = alto - medio;

	    // Dividimos en arreglos temporales
	    Familia[] left = new Familia[tamanio_list1];
	    Familia[] right = new Familia[tamanio_list2];

	    // Copiar los datos a los arreglos temporales
	    //  	arraycopy(Object src, int srcPos, Object dest, int destPos, int length)  
	    System.arraycopy(array, bajo, left, 0, tamanio_list1);
	    System.arraycopy(array, medio + 1, right, 0, tamanio_list2);

	    // Establece los indices para recorrer y comparar los arreglos
	    int i = 0, j = 0, k = bajo;
	    while (i < tamanio_list1 && j < tamanio_list2) {
	        if (verify(right[j], left[i], type_order, atributo)) {
	            array[k] = left[i];
	            i++;
	        } else {
	            array[k] = right[j];
	            j++;
	        }
	        k++;
	    }
	    // Copiar los elementos restantes de left[], si los hay
	    while (i < tamanio_list1) {
	        array[k] = left[i];
	        i++;
	        k++;
	    }
	    // Copiar los elementos restantes de right[], si los hay
	    while (j < tamanio_list2) {
	        array[k] = right[j];
	        j++;
	        k++;
	    }
	}
	
	

}
