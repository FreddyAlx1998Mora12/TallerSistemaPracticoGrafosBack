package edu.academic.taller.models.graph;

import java.lang.reflect.Array;
import java.util.HashMap;

import edu.academic.taller.exceptions.LabelException;
import edu.academic.taller.models.list.MyLinkedList;

public class GraphLabelDirect<E> extends GraphDirect {

	protected E label[]; // etiqueta
	protected HashMap<E, Integer> dictVertices;
	private Class<E> clazz;

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

}
