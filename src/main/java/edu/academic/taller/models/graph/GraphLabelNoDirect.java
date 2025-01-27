package edu.academic.taller.models.graph;

import edu.academic.taller.exceptions.LabelException;

public class GraphLabelNoDirect<E> extends GraphLabelDirect<E> {

	public GraphLabelNoDirect(Integer n_vert, Class<E> claseSerializ) {
		super(n_vert, claseSerializ);
	}
	
	/**
	 * Inserta una etiqueta arista
	 * @param v1 -> Objeto almacenado en un vertice
	 * @param v2 -> Objeto almacenado en un vertice
	 * @param weight -> peso de la arista
	 * @throws LabelException
	 * @throws Exception
	 */
	public void insertEdgeLabel(E v1, E v2, Float weight) throws LabelException, Exception {
		if (isLabelGraph()) {
			this.add_edge(getVerticeLabel(v1), getVerticeLabel(v2), weight);
			this.add_edge(getVerticeLabel(v2), getVerticeLabel(v1), weight);
		} else {
			throw new LabelException("Grafo para insertar no etiquetado");
		}
	}

	public void insertEdgeLabel(E v1, E v2) throws LabelException, Exception {
		// TODO Auto-generated method stub
		insertEdgeLabel(v1, v2, Float.NaN);
	}
}
