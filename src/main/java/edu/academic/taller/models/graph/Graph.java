package edu.academic.taller.models.graph;

import edu.academic.taller.models.list.MyLinkedList;

/**
 * Es una clase abstracta que identifica un grafo, contiene metodos como
 * nro_vertices
 */
public abstract class Graph {

	// metodo abstract, el grafo es el numero de vertices que contiene
	public abstract Integer nro_vertice();

	// metodo abstract. son las aristas o numero de aristas que contiene
	public abstract Integer nro_edges();

	// metodo abstract que compara si son aristas o mejor dicho verificar si estan
	// conectado un vertice a otro vertice
	public abstract Boolean is_edge(Integer v1, Integer v2) throws Exception;

	// peso de arista o adyacencia
	public abstract Float wieght_edge(Integer v1, Integer v2) throws Exception;

	// Metodos de Operaciones
	// aniade una arista, desde v1, v2
	public abstract void add_edge(Integer v1, Integer v2) throws Exception;

	public abstract void add_edge(Integer v1, Integer v2, Float weight) throws Exception;

	// Es una lista de adyacencia, sus vertices o enlaces con que otros grafos estan
	// conectados
	public abstract MyLinkedList<Adyacencia> adyacencia(Integer v1);

	@Override
	public String toString() {
		String grafo = "";

		try {
			// Los numeros de vertices o enlaces que tiene este grafo
			for (int i = 1; i < this.nro_vertice(); i++) {

				grafo += "V" + i + "\n";

				// Lista de todos sus vertices que tenga el vertice en la posicion i (o nodos)
				MyLinkedList<Adyacencia> list_adyacencias = adyacencia(i);

				// Si la lista no esta vacia, es por que tiene vertices conectads
				if (!list_adyacencias.isEmptyLinkedList()) {

					// Convertimos esta lista a un arreglo para manipular mejor
					Adyacencia[] ady = list_adyacencias.toArray();

					for (int j = 0; j < ady.length; j++) {
						// Adyacencia o vertice
						Adyacencia adyacencia = ady[j];

						grafo += "adyacencia: " + "V" + adyacencia.getVertice_destino() + ", peso de adyacencia: "
								+ adyacencia.getPeso() + "\n";

					}
				}

			}

		} catch (Exception e) {
			// TODO: handle exception

		}

		return grafo;
	}

}
