package edu.academic.taller.models.graph;

import edu.academic.taller.exceptions.OverflowException;

public class GraphNoDirect extends GraphDirect{

	public GraphNoDirect(Integer n_vert) {
		// GraphDirect tiene un constructor por defect
		super(n_vert);
	}
	
	@Override
	public void add_edge(Integer v1, Integer v2, Float weight) throws Exception {
		// TODO Auto-generated method stub
//		super.add_edge(v1, v2, weight);
		
		if(v1.intValue() <= nro_vertice() && v2.intValue() <= nro_vertice()) {
			if(!is_edge(v1, v2)) {
//				nro_edg ++;
//				Integer nro_edg = getNro_edg();
//				nro_edg++;
				setNro_edg(nro_vertice()+1);
				
				Adyacencia new_ady = new Adyacencia(); //
				new_ady.setPeso(weight);
				new_ady.setVertice_destino(v2);
				getListAdyacencia()[v1].add(new_ady);
				
				Adyacencia new_ady2 = new Adyacencia(); //
				new_ady2.setPeso(weight);
				new_ady2.setVertice_destino(v2);
				
				getListAdyacencia()[v2].add(new_ady2);
			}
		}else {
			throw new OverflowException("Los vertices no estan conectados o no tienen el mismo pes...");
		}
	}

}
