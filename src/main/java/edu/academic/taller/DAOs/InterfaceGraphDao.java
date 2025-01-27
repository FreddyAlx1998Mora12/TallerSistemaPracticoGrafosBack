package edu.academic.taller.DAOs;

import java.io.FileNotFoundException;

public interface InterfaceGraphDao<T> {
	
	void drawGraph(String data) throws Exception;
	
	String loadGraph() throws FileNotFoundException, Exception;

}
