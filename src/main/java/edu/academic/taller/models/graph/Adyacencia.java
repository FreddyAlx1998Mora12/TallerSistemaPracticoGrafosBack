package edu.academic.taller.models.graph;

/**
 * Clase Adyacencia que identifica a que otros vertices estan conectados un
 * vertice En otras palabras son las aristas
 */
public class Adyacencia {

	private Integer vertice_destino; // vertice destino

	private Float peso; // weight peso de adyacencia o arista

	public Adyacencia() {
	}

	public Adyacencia(Integer vertice_destino, Float peso) {
		this.vertice_destino = vertice_destino;
		this.peso = peso;
	}

	public Integer getVertice_destino() {
		return vertice_destino;
	}

	public void setVertice_destino(Integer vertice_destino) {
		this.vertice_destino = vertice_destino;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}

}
