package edu.academic.taller.models.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import edu.academic.taller.DAOs.InterfaceGraphDao;
import edu.academic.taller.exceptions.LabelException;
import edu.academic.taller.models.list.MyLinkedList;

public class GraphLabelDirect<E> extends GraphDirect implements InterfaceGraphDao<E> {

	protected E label[]; // etiqueta
	protected HashMap<E, Integer> dictVertices;
	private Class<E> clazz;

	public static String filePath = "src/main/resources/graphs/"; // para almacenar los archivos json

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

	// Realizar aqui el metodo de dibujar grafo, y cargar grafo
	@Override
	public void drawGraph() throws Exception {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		GsonBuilder gb = new GsonBuilder();
		Gson g = gb.setPrettyPrinting().create();

		sb.append("{\"Vertices\": [\n");

		// iteramos el hashmap
		for (int i = 1; i <= nro_vertice(); i++) {
			sb.append("{\n \"id\": " + i + ",\n\"label\" : ");
			sb.append(g.toJson(getLabel(i)) + "\n},\n");
		}

		// termina iteracion de vertices, se elimina la ultima coma
		if (sb.charAt(sb.length() - 2) == ',') {
			sb.delete(sb.length() - 2, sb.length());
		}

		// Adyacencias
		sb.append("],\n\"Edges\": [\n");

		for (int i = 1; i <= nro_vertice(); i++) {
			MyLinkedList<Adyacencia> ady = adyacencia(i);
			if (!ady.isEmptyLinkedList()) {
				// lista de adyacencia del vertice
				Adyacencia[] matrix_ady = ady.toArray();

				for (int j = 0; j < matrix_ady.length; j++) {
					Adyacencia aux = matrix_ady[j];
					sb.append("{ \"from\" : " + i + ", \"to\" : " + aux.getVertice_destino() + "},\n");
				}

			}

		}
		// termina iteracion de vertices, se elimina la ultima coma
		if (sb.charAt(sb.length() - 2) == ',') {
			sb.delete(sb.length() - 2, sb.length());
		}

		sb.append("\n]\n}");

		// 1. Crear un Objeto File o Archivo para almacenar los datos
		File file = new File(filePath + "Grafo_" + clazz.getSimpleName() + ".json");
		// 2. Objeto como tipo cursor para la escritura
		FileWriter fw = new FileWriter(file);
		try { // Usamos try-with-resources para cerrar automáticamente el FileWriter
			fw.write(sb.toString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			System.out.println("Error al escribir en el archivo: " + e.getMessage());
		}
	}

	@Override
	public String loadGraph() throws FileNotFoundException, Exception {
		// TODO Auto-generated method stub
		// Traer la data para deserializar
		System.out.println("Intentando deserializar metodo loadGraph GraphLabelDirect...");
		String file_json = readGraph();
		
		if (file_json.isEmpty()) {
			throw new FileNotFoundException("El archivo esta vacio o no se pudo leer, verifique que el archivo exista o contenga informacion.");
		}
		
		try {
			// Crear una instancia de HashMap o dict
			// intentar deserializar lo que contiene VERTICES
			// luego leer lo que dice nodos, y conectar segun el valor que esta
			Gson gson = new Gson();
            // Deserializar el JSON a un mapa que contenga los vértices y las aristas
			TypeToken<Map<String, MyLinkedList<Map<String, Object>>>> token = new TypeToken<Map<String, MyLinkedList<Map<String, Object>>>>() {};
            Map<String, MyLinkedList<Map<String, Object>>> graphData = gson.fromJson(file_json, token.getType());

            // Extraer los vértices y las aristas
            MyLinkedList<Map<String, Object>> vertices = graphData.get("Vertices");
            MyLinkedList<Map<String, Object>> edges = graphData.get("Edges");
            
            System.out.println("Intentando deserializar lo que contiene vertices..."+vertices.getClass().getTypeName());
            System.out.println("Intentando deserializar lo que contiene vertices..."+edges.getClass().getTypeName());
            
            // Crear un HashMap para almacenar los vértices
            HashMap<E, Integer> verticesMap = new HashMap<>();

            // Reconstruir los vértices a partir del JSON
            /*for (Map<String, Object> vertex : vertices) {
                int id = ((Double) vertex.get("id")).intValue();
                // Deserializar la etiqueta (label) como un objeto Estacion
                E estacion = gson.fromJson(gson.toJson(vertex.get("label")), E);
                // Añadir al HashMap
                verticesMap.put(estacion, id);
            }

            // Crear el grafo
            for (Map<String, Object> edge : edges) {
                int from = ((Double) edge.get("from")).intValue();
                int to = ((Double) edge.get("to")).intValue();

                // Aquí puedes reconstruir la relación de aristas entre los vértices
                // Por ejemplo, podrías tener un HashMap para almacenar las conexiones de los vértices
                System.out.println("Conexión de " + from + " a " + to);
            }

            // Imprimir los vértices para ver que se cargaron correctamente
            verticesMap.forEach((etiqueta, id) -> {
                System.out.println("Estación: " + etiqueta.getCodigoEstacion() + ", ID: " + id);
            });*/
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		return null;
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

}
