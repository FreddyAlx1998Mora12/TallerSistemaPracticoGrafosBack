package edu.academic.taller.models.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import edu.academic.taller.DAOs.InterfaceGraphDao;
import edu.academic.taller.exceptions.LabelException;
import edu.academic.taller.models.Ruta;
import edu.academic.taller.models.list.MyLinkedList;
import edu.academic.taller.models.queque.Queuque;

public class GraphLabelDirect<E> extends GraphDirect implements InterfaceGraphDao<E> {

	protected E label[]; // etiqueta
	protected HashMap<E, Integer> dictVertices;
	private Class<E> clazz;

	public static String filePath = "src/main/resources/graphs/"; // para almacenar los archivos json
	public static String filePath2 = "/home/freddy/Documentos/ProyectosDesarrollo/Python/SistemaTaller_RutaTransporte_Grafos/templates/components/lineas_rutas/"; // para
																																									// mostrar
																																									// grafo

	public GraphLabelDirect(Integer n_vert, Class<E> claseSerializ) {
		super(n_vert);
		this.clazz = claseSerializ;
		this.label = (E[]) Array.newInstance(claseSerializ, n_vert + 1);
		dictVertices = new HashMap<>();
	}

	// Metodos de Grafo etiquetado

	public Boolean is_edgeLabel(E v1, E v2) throws Exception {
		if (isLabelGraph()) {
			return is_edge(getVerticeLabel(v1), getVerticeLabel(v2));
		} else {
			throw new LabelException("Grafo no etiquetado");
		}
//		return null;
	}

	public void insertEdgeLabel(E v1, E v2) throws LabelException, Exception {
		if (isLabelGraph()) {
			this.add_edge(getVerticeLabel(v1), getVerticeLabel(v2), Float.NaN);
		} else {
			throw new LabelException("Grafo para insertar no etiquetado");
		}
	}

	public void insertEdgeLabel(E v1, E v2, Float weigth) throws LabelException, Exception {
		if (isLabelGraph()) {
			this.add_edge(getVerticeLabel(v1), getVerticeLabel(v2), weigth);
		} else {
			throw new LabelException("Grafo para insertar no etiquetado");
		}
	}

	public MyLinkedList<Adyacencia> adyacenciasLabel(E v1) throws Exception {
		if (isLabelGraph()) {
			return adyacencia(getVerticeLabel(v1));
		} else {
			throw new LabelException("Grafo para insertar no etiquetado");
		}
	}

	public void labelVertice(Integer v, E data) { // etiqueta vertices
		label[v] = data;
		dictVertices.put(data, v);
	}

	public Boolean isLabelGraph() { // verifica si el grafo esta etiquetado
		boolean bond = true;
		for (int i = 1; i < label.length; i++) {
			if (label[i] == null) {
				bond = false;
				break;
			}
		}

		return bond;
	}

	public Integer getVerticeLabel(E label) {
		return dictVertices.get(label); // return Integer, fijate en la instancia de hashmap
	}

	public E getLabel(Integer v1) {
//		return dictVertices.
		return label[v1];
	}

	@Override
	public String toString() {
//		return "GraphLabelDirect [label=" + Arrays.toString(label) + ", dictVertices=" + dictVertices + ", clazz=" + clazz
//				+ "]";

		String grafo = "";
		try {
			for (int i = 1; i <= nro_vertice(); i++) {
				grafo += "V" + i + " Etiqueta: {" + getLabel(i).toString() + ":\n"; // imprimir grafo
				// lista enlazada de adyaceencia de la posicion i
				MyLinkedList<Adyacencia> ady = adyacencia(i); // Se obtiene una lista enlazada de Adyacencia del vertice
																// en la pos i

				if (!ady.isEmptyLinkedList()) {
					// lista de adyacencia del vertice
					Adyacencia[] matrix_ady = ady.toArray();

					for (int j = 0; j < matrix_ady.length; j++) {
						Adyacencia aux = matrix_ady[j];
						grafo += "ady: V" + aux.getVertice_destino() + ", weight= " + aux.getPeso() + ", label: ["
								+ getLabel(aux.getVertice_destino()).toString() + "]\n";
					}
					grafo += "}";
				}

				// luego imprime lo que tiene la lista de adyacencia
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return grafo;
	}

	// Realizar aqui el metodo de dibujar grafo
	@Override
	public void drawGraph() throws Exception {
		// TODO Auto-generated method stub
		StringBuilder sb2 = new StringBuilder(); // para crear el script
		sb2.append("var nodes = new vis.DataSet([\n");

		GsonBuilder gb = new GsonBuilder();
		Gson g = gb.setPrettyPrinting().create();
		HashMap<Integer, Adyacencia[]> graphMap = new HashMap<>(); // para guardar en json

		// iteramos el hashmap
		for (int i = 1; i <= nro_vertice(); i++) {
			sb2.append("{ id:" + i + ", label: \"" + getDescripcionLabel(getLabel(i)) + "\"},\n");
		}
		sb2.append("\n]);\n");

		// Adyacencias
		sb2.append("var edges = new vis.DataSet([\n");

		for (int i = 1; i <= nro_vertice(); i++) {
			MyLinkedList<Adyacencia> ady = adyacencia(i);
			if (!ady.isEmptyLinkedList()) {
				// lista de adyacencia del vertice
				Adyacencia[] matrix_ady = ady.toArray();

				for (int j = 0; j < matrix_ady.length; j++) {
					Adyacencia aux = matrix_ady[j];
					sb2.append("{ from: " + i + ", to: " + aux.getVertice_destino() + ", label: \"" + aux.getPeso()
							+ "\"},\n");
				}

			}

		}
		sb2.append("\n]);\n");

		sb2.append("var container = document.getElementById(\"mynetwork\");\n" + "      var data = {\n"
				+ "        nodes: nodes,\n" + "        edges: edges,\n" + "      };\n" + "      var options = {};\n"
				+ "      var network = new vis.Network(container, data, options);");

		// AQUI CREA EL JSON PARA EL LOAD
		for (int i = 1; i <= nro_vertice(); i++) {
			MyLinkedList<Adyacencia> ady = adyacencia(i);
			if (!ady.isEmptyLinkedList()) {
				Adyacencia[] matrix_ady = ady.toArray();
				graphMap.put(i, matrix_ady); // Agregar al mapa
			}
		}
		String json = g.toJson(graphMap);

		// 1. Crear un Objeto File o Archivo para almacenar los datos
		File file = new File(filePath + "Grafo_" + clazz.getSimpleName() + ".json");
		File file2 = new File(filePath2 + "graph.js");
		// 2. Objeto como tipo cursor para la escritura
		FileWriter fw = new FileWriter(file);
		FileWriter fw2 = new FileWriter(file2);
		try {
			fw.write(json);
			fw.flush();
			fw.close();

			fw2.write(sb2.toString());
			fw2.flush();
			fw2.close();
		} catch (IOException e) {
			System.out.println("Error al escribir en el archivo: " + e.getMessage());
		}
	}

	@Override
	public HashMap<Integer, Adyacencia[]> loadGraph() throws FileNotFoundException, Exception {
		// Traer la data para deserializar

		System.out.println("Intentando deserializar metodo loadGraph GraphLabelDirect...");
		String file_json = readGraph();
		HashMap<Integer, Adyacencia[]> dict = null;
		GsonBuilder gb = new GsonBuilder();
		Gson g = gb.setPrettyPrinting().create();

		// Si el archivo no existe
		if (file_json.isEmpty()) {
			throw new FileNotFoundException(
					"El archivo esta vacio o no se pudo leer, verifique que el archivo exista o contenga informacion.");
		}

		try {
			if (!file_json.isEmpty()) {
				/* Para evitar estas instancias de objetos o clases utilizo typeToken
				 *Class<?> hashMapClass = HashMap.class;

            // Obtener el constructor predeterminado de HashMap
            Constructor<?> constructor = hashMapClass.getDeclaredConstructor();

            // Crear una instancia de HashMap usando el constructor
            HashMap<Integer, String> mapa = (HashMap<Integer, String>) constructor.newInstance();
				 */
				Type type = new TypeToken<HashMap<Integer, Adyacencia[]>>() {}.getType();
				HashMap<Integer, Adyacencia[]> matrix = g.fromJson(file_json, type);

				dict = matrix;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return dict;
	}

	@Override
	public String readGraph() throws FileNotFoundException, Exception {
		// TODO Auto-generated method stub
		// 1. Crear objeto
		File file = new File(filePath + "Grafo_" + clazz.getSimpleName() + ".json");
		// 2. Clase facilitador de lectura (fileReader lector de archivo(file archivo)))
		Scanner in = new Scanner(new FileReader(file));
		StringBuilder sb = new StringBuilder();

		while (in.hasNext()) {
			sb.append(in.nextLine()).append("\n");
		}
		in.close(); // cerrar siempre
		return sb.toString().trim();
	}

	// metodo que retorna un valor int (obtiene el identificador del objeto),
	// parametro objeto tipo dato generico T
	private String getDescripcionLabel(E obj) throws Exception {
		try {
			// Declara una variable tipo method, un lang.reflect
			Method method = null;
			for (Method m : clazz.getMethods()) { // obtiene los metodos de la clase o objeto a serializar, itera cada
													// metodo
				if (m.getName().contains("getDescripcion")) { // verifica que el metodo actual tenga el atributo tal ""
																// ejm : el en objeto Persona getIdPersona
					method = m; // asigna lo que devuelve el getId
					break;
				}
			}
			// si no encuentra el metodo, vuelve a iterar pero esta vez eleando a la clase
			// padre
			if (method == null) {
				for (Method m : clazz.getSuperclass().getMethods()) {
					if (m.getName().contains("getDescripcion")) {
						method = m;
						break;
					}
				}
			}

			// si exite el method invoke el metodo que tiene el obj
			if (method != null)
				return (String) method.invoke(obj);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocurrio algun error en la obtencion de getIdent de la clase, " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return null;
	}

	// metodo para invocar el metodo de latitud y longitud
	private Double getCoordenadasLabel(E obj, String metodo) throws Exception {
		try {
			// Declara una variable tipo method, un lang.reflect
			Method method = null;
			for (Method m : clazz.getMethods()) { // obtiene los metodos de la clase o objeto a serializar, itera cada
													// metodo
				if (m.getName().contains("get" + metodo)) { // verifica que el metodo actual tenga el atributo tal ""
															// ejm : el en objeto Persona getIdPersona
					method = m; // asigna lo que devuelve el getId
					break;
				}
			}
			// si no encuentra el metodo, vuelve a iterar pero esta vez eleando a la clase
			// padre
			if (method == null) {
				for (Method m : clazz.getSuperclass().getMethods()) {
					if (m.getName().contains("get" + metodo)) {
						method = m;
						break;
					}
				}
			}

			// si exite el method invoke el metodo que tiene el obj
			if (method != null)
				return (Double) method.invoke(obj);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocurrio algun error en la obtencion de getIdent de la clase, " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	public Integer[] dfs(int idVertice) throws Exception {
		MyLinkedList<Integer> nodos_visitados = new MyLinkedList<>();
		boolean[] visitados = new boolean[nro_vertice() + 1]; // Arreglo para marcar que vertice se visitto
//		System.out.println("longitud "+visitados.length);
		try {
			dfsRecursivo(idVertice, nodos_visitados, visitados);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return nodos_visitados.toArray();
	}

	private void dfsRecursivo(int idVertice, MyLinkedList<Integer> nodosVisitados, boolean[] visitados)
			throws Exception {
		visitados[idVertice] = true; // Marcamos el vértice como visitado
		nodosVisitados.add(idVertice); // Añadimos el vértice a la lista de nodos visitados

		System.out.println("Visitando vértice: " + idVertice);
		
		// Obtenemos las adyacencias del vértice actual
		MyLinkedList<Adyacencia> adyacencias = adyacencia(idVertice);

		// Recorremos todas las adyacencias del vértice actual
		for (Adyacencia ady : adyacencias.toArray()) {
			// Si el vértice destino no ha sido visitado, llamamos recursivamente
			if (!visitados[ady.getVertice_destino()]) {
				dfsRecursivo(ady.getVertice_destino(), nodosVisitados, visitados);
			}
		}
		
		// nodosVisitados.remove(0); //
	}

	public Float[] bellmanFord(int verticeOrigen) throws Exception {
		Float n_infinity = Float.MAX_VALUE;
		// creamos un arreglo para dist
		Float[] dist = (Float[]) Array.newInstance(Float.class, nro_vertice() + 1);

		for (int k = 1; k <= nro_vertice(); k++) {
			// inicializamos con el valor infinito para
			dist[k] = n_infinity;
		}

		dist[verticeOrigen] = 0.0f;

		// hacemos el relajo de aristas, suena medio gracios
		for (int i = 1; i <= nro_vertice(); i++) {
			for (int j = 1; j <= nro_vertice(); j++) {
				// obtenemos la lista de adyacencia de j
				MyLinkedList<Adyacencia> ady = adyacencia(j);
				if (!ady.isEmptyLinkedList()) {
					// iteramos sobre la ady
					Adyacencia[] arr_ady = ady.toArray();
					for (Adyacencia aux : arr_ady) {
						int v = aux.getVertice_destino();
						float weight = aux.getPeso();
						// Si encontramos un camino más corto hacia v, lo actualizamos
						System.out.println("V" + j + ", v:" + v + ", nVert" + nro_vertice());
						if (dist[j] != Float.POSITIVE_INFINITY && dist[i] + weight < dist[v]) {
							dist[v] = dist[j] + weight;
						}
					}
				}

			}

		}

		if (!tieneCicloNegativo(dist)) { // si no hay ciclos negativos retorna las distancias minimas
			return dist;
		}

		return null;
	}

	private boolean tieneCicloNegativo(Float[] dist) {
		// verificamos si existe ciclo negativos
		for (int i = 1; i <= nro_vertice(); i++) {
			MyLinkedList<Adyacencia> ady = adyacencia(i);
			if (!ady.isEmptyLinkedList()) {
				// Iteramos sobre las aristas de cada vértice
				Adyacencia[] matrix_ady = ady.toArray();
				for (Adyacencia aux : matrix_ady) {
					int v = aux.getVertice_destino();
					float weight = aux.getPeso();
					// Si podemos relajar una arista después de V-1 iteraciones, hay un ciclo
					// negativo
					if (dist[i] != Float.POSITIVE_INFINITY && dist[i] + weight < dist[v]) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public Float[][] floydWarshall() {
		int n = nro_vertice();
		Float[][] dist = new Float[n + 1][n + 1]; // Matriz de distancias

		// Inicializamos la matriz de distancias
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (i == j) {
					dist[i][j] = 0.0f; // es la diagonal
				} else {
					dist[i][j] = Float.POSITIVE_INFINITY; // Inicializamos con infinito
				}
			}
		}

		// Llenamos la matriz de distancias con los pesos de las aristas
		for (int i = 1; i <= n; i++) {
			MyLinkedList<Adyacencia> adyacencias = adyacencia(i);
			if (!adyacencias.isEmptyLinkedList()) {
				Adyacencia[] arr_ady = adyacencias.toArray();
				for (Adyacencia aux : arr_ady) {
					int destino = aux.getVertice_destino();
					float peso = aux.getPeso();
					dist[i][destino] = peso; // Asignamos el peso calculado
				}
			}
		}

		for (int k = 1; k <= n; k++) {
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					// Si el camino i -> k -> j es más corto que el camino directo i -> j
					if (dist[i][j] > dist[i][k] + dist[k][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
					}
				}
			}
		}

		return dist; // Devolvemos la matriz de distancias más cortas
	}

	// Método para mostrar la matriz de distancias
	public void imprimirDistancia(Float[][] dist) {
		int n = dist.length - 1; // Ajustamos el número de vértices
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (dist[i][j] == Float.POSITIVE_INFINITY) {
					System.out.print("∞ ");
				} else {
					System.out.print(dist[i][j] + " ");
				}
			}
			System.out.println();
		}
	}

//	@Override
	public Float calculaDistanciaGeodesica(E v1, E v2) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Para calcular la distancia Objeto v1: "+v1+", v2: "+v2);
		Double latitud_X = getCoordenadasLabel(v1, "Latitud");
		Double latitud_Y = getCoordenadasLabel(v2, "Latitud");

		Double longitud_X = getCoordenadasLabel(v1, "Longitud");
		Double longitud_Y = getCoordenadasLabel(v2, "Longitud");

		double lat1Rad = Math.toRadians(latitud_X.doubleValue());
		double lon1Rad = Math.toRadians(longitud_X.doubleValue());
		double lat2Rad = Math.toRadians(latitud_Y.doubleValue());
		double lon2Rad = Math.toRadians(longitud_Y.doubleValue());

		// Diferencias de latitud y longitud
		double deltaLat = lat2Rad - lat1Rad;
		double deltaLon = lon2Rad - lon1Rad;

		// Fórmula de Haversine, que mide distancia entre dos puntos de forma geodesica
		double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
				+ Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		
		// Distancia en kilómetros, 6371.0 equivale al radio del planeta, resultado en km
        Double distancia = (double) (Math.round((6371.0 * c)*100.0) / 100.0);
        Float floatValue = Float.valueOf(distancia.floatValue());

		return floatValue;
	}

}
