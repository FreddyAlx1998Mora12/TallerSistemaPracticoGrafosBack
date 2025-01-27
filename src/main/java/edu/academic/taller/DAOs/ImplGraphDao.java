package edu.academic.taller.DAOs;

import java.io.FileNotFoundException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class ImplGraphDao<T> implements InterfaceGraphDao<T>{
	
	private Class<T> claseSerializer; // Clase que identifica el tipo de clase que tendra el grafo
	private Gson g; // Objeto para guardar en formato json
	public static String filePath = "src/main/resources/graphs/"; // para almacenar los archivos json
	
	// constructor
	public ImplGraphDao(Class clazz) {
		this.claseSerializer = clazz;
		GsonBuilder gb = new GsonBuilder();
		this.g = gb.setPrettyPrinting().create();
	}
	
	/**
	 * Metodo que permite dibujar el grafo ya etiquetado
	 */
	@Override
	public void drawGraph(String data) throws Exception {
		
		
	}

	/**
	 * Metodo que permitira cargar el grafo
	 */
	@Override
	public String loadGraph() throws FileNotFoundException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
