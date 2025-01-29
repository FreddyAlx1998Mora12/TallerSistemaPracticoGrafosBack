package edu.academic.taller.Services;

import edu.academic.taller.DAOs.impl.RutaDao;
import edu.academic.taller.models.Ruta;
import edu.academic.taller.models.list.MyLinkedList;

/**
 * Clase que brinda servicios, interactua como intermediario entre la capa
 * logica de negocio y la capa de datos, DAO's
 */
public class RutaService {

	private RutaDao pdObj;

	public RutaService() {
		pdObj = new RutaDao();
	}

	public Ruta getRuta() {
		return pdObj.getRuta();
	}

	public void setRuta(Ruta est) {
		pdObj.setRuta(est);
	}

	public MyLinkedList listAll() {
		return pdObj.getLista_ruta();
	}

//	Operacion de guardar
	public boolean save() throws Exception {
		return pdObj.save();
	}

	public Boolean update(Ruta p, Integer id) throws Exception {
		return pdObj.updatebyId(id, p);
	}

	public void delete(int id) throws Exception {
		pdObj.deletebyId(id);
	}

	public Ruta buscarporId(int id) throws Exception {
		return pdObj.buscarporID(id);
	}

	public Ruta buscarpordescripcion(String id) throws Exception {
		return pdObj.buscarporDescripcion(id);
	}

	public boolean existRuta(Ruta r) throws Exception {
		return pdObj.existeRuta(r);
	}
	
	public Float calculoDistancia(Ruta r1, Ruta r2) throws Exception{
		return pdObj.calcularDistancia(r1, r2);
	}
}
