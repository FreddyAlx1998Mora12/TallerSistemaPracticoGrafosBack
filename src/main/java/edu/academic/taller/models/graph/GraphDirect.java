package edu.academic.taller.models.graph;

import edu.academic.taller.exceptions.OverflowException;
import edu.academic.taller.models.list.MyLinkedList;

/**
 * Clase que describe un Grafo Dirigido, es decir tienen una sola direccion
 */
public class GraphDirect extends Graph{
	
	private Integer nro_vertice;
	private Integer nro_edg;
	private MyLinkedList<Adyacencia> listAdyacencia [];
	
	public GraphDirect(Integer n_vert) {
		this.nro_vertice = n_vert; // numero de vertices
		this.nro_edg = 0; // aristas por default
		listAdyacencia = new MyLinkedList[nro_vertice+1]; // tamanio de  lista adyacencia para cada vertice
		
		for (int i = 1; i <= nro_vertice; i++) {
			listAdyacencia[i] = new MyLinkedList<>(); // Para cada Adyacencia o mejor dicho para cada vertice se crea una lista de adyacencia
			// 1. V1 ady v2,v3,v4
			// 2. V2 ady v3,v5
			// 3. v3 ady v1, v4 
		}
	}
	
	// Getters de Graph and Setters de GraphDirect
	public void setNro_vertices(Integer nro_vertices) {
		this.nro_vertice = nro_vertices;
	}

	public void setNro_edg(Integer nro_edg) {
		this.nro_edg = nro_edg;
	}


	public MyLinkedList<Adyacencia>[] getListAdyacencia() {
		return listAdyacencia;
	}


	public void setListAdyacencia(MyLinkedList<Adyacencia>[] listAdyacencia) {
		this.listAdyacencia = listAdyacencia;
	}
	
	
	
	// Metodos de clase abstracta Graph
	@Override
	public Integer nro_vertice() {
		// TODO Auto-generated method stub
		return this.nro_vertice;
	}

	@Override
	public Integer nro_edges() {
		// TODO Auto-generated method stub
		return this.nro_edg;
	}
	
	@Override
	public Boolean is_edge(Integer v1, Integer v2) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(v1.intValue() <= nro_vertice && v2.intValue() <= nro_vertice) { // verifica que los vertices (nodos) pasados por paramteros no excedan el nro de vertices (ttoal)
			MyLinkedList<Adyacencia> list = listAdyacencia[v1];
			
			if (!list.isEmptyLinkedList()) {
				Adyacencia[] arr_ady = list.toArray();
				
				// iteramos el array
				for (int i = 0; i < arr_ady.length; i++) {
					Adyacencia aux = arr_ady[i];
					
					// verifico si estan conectadas
					if(aux.getVertice_destino() == v2.intValue()) {
						return flag = true;
//						break;
					}
				}
			}
		}else {
			throw new OverflowException("Los vertices estan fuera de rango");
		}
		return flag;
	}
	
	// Obtiene el peso arista entre un vertice v1 y v2
	@Override
	public Float wieght_edge(Integer v1, Integer v2) throws Exception{
		// TODO Auto-generated method stub
		Float weigFloat = null;
//		Float weigFloat = Float.NaN;
		
		if (is_edge(v1, v2)) {
			MyLinkedList<Adyacencia> list = listAdyacencia[v1];
			
			if (!list.isEmptyLinkedList()) {
				Adyacencia[] arr_ady = list.toArray();
				
				// iteramos el array
				for (int i = 0; i < arr_ady.length; i++) {
					Adyacencia aux = arr_ady[i];
					
					// verifico si estan conectadas
					if(aux.getVertice_destino() == v2.intValue()) {
						weigFloat = aux.getPeso();
						break;
					}
				}
			}
			
		}else {
			throw new OverflowException("Los vertices no estan conectados o no tienen el mismo pes...");
		}
		
		return null;
	}

	@Override
	public void add_edge(Integer v1, Integer v2) throws Exception {
		// TODO Auto-generated method stub
		this.add_edge(v1, v2, Float.NaN);
	}

	@Override
	public void add_edge(Integer v1, Integer v2, Float weight) throws Exception {
		// TODO Auto-generated method stub
		if(v1.intValue() <= nro_vertice && v2.intValue() <= nro_vertice) {
			if(!is_edge(v1, v2)) { // Si no son arista, logicamente el elemento v2 no debe constar en v1 para poderse agg
				nro_edg ++;
				Adyacencia new_ady = new Adyacencia(); //
				new_ady.setPeso(weight);
				new_ady.setVertice_destino(v2);
				
				listAdyacencia[v1].add(new_ady);
			}
		}else {
			throw new OverflowException("Los vertices no estan conectados o no tienen el mismo pes...");
		}
	}

	@Override
	public MyLinkedList<Adyacencia> adyacencia(Integer v1) {
		// TODO Auto-generated method stub
		return listAdyacencia[v1];
	}

}
