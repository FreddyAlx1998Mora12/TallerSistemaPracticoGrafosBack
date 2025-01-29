package edu.academic.taller.DAOs;

import java.io.FileNotFoundException;
import java.util.HashMap;

import edu.academic.taller.models.graph.Adyacencia;

public interface InterfaceGraphDao<T> {
	
	void drawGraph() throws Exception;
	
//	Float calculaDistanciaGeodesica(T v1, T v2) throws Exception;
	
	String readGraph() throws FileNotFoundException, Exception;
	
	HashMap<Integer, Adyacencia[]> loadGraph() throws FileNotFoundException, Exception;
	
	//utilidades
	//metodo bpp busqueda en profundidad
	Integer[] dfs(int idVertice) throws Exception;

}
