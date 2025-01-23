package edu.academic.taller.Services;

import edu.academic.taller.DAOs.impl.EstacionDao;
import edu.academic.taller.models.Estacion;
import edu.academic.taller.models.list.MyLinkedList;

/**
 * Clase que brinda servicios, interactua como intermediario entre la capa logica de negocio
 * y la capa de datos, DAO's
 */
public class EstacionService {
	
	private EstacionDao pdObj;
	
	public EstacionService() {
		pdObj = new EstacionDao();
	}
	
	public Estacion getEstacion() { 
		return pdObj.getEstacion();
	}
	
	public void setEstacion(Estacion est) {
		pdObj.setEstacion(est);
	}
	
	public MyLinkedList listAll() {
		return pdObj.getLista_estacion();
	}
	
//	Operacion de guardar
	public boolean save() throws Exception{
		return pdObj.save();
	}
	
	public Boolean update(Estacion p, Integer id) throws Exception{
		return pdObj.updatebyId(id, p);
	}
	
	public void delete(int id) throws Exception{
		pdObj.deletebyId(id);
	}
}
