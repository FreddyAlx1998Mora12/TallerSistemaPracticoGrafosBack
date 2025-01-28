package edu.academic.taller.DAOs;

import java.io.FileNotFoundException;

public interface InterfaceGraphDao<T> {
	
	void drawGraph() throws Exception;
	
	String readGraph() throws FileNotFoundException, Exception;
	
	String loadGraph() throws FileNotFoundException, Exception;

}
