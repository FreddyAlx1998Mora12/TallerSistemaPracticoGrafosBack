package edu.academic.taller.models;

import edu.academic.taller.models.list.MyLinkedList;

/**
 * Clase que identifica una Estacion o Lineas de Transporte
 * Contiene una lista de Rutas, Ejmpl:
 * L2 (Estacion) : [Sauces, Motupe, Pitas, Terminal...] (Rutas)
 * L8 (Estacion) : [CiudadVictoria, La Dolorosa, Obrapia, ...] (Rutas)
 */
public class Estacion {
	
	private int idEstacion;
	private String codigoEstacion; // Codigo que identifica una linea de transporte (L2)
	private String horaInicio; // Hora de inicio de actividades
	private String horaFin; // Hora de finalizacion de actividades
	private int nroUnidades; // Nro. de Unidades para tal Linea
	
	private MyLinkedList<Ruta> listRutas; // Lista de Rutass
	
	public int getIdEstacion() {
		return idEstacion;
	}
	public void setIdEstacion(int idEstacion) {
		this.idEstacion = idEstacion;
	}
	public String getCodigoEstacion() {
		return codigoEstacion;
	}
	public void setCodigoEstacion(String codigoEstacion) {
		this.codigoEstacion = codigoEstacion;
	}
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	public int getNroUnidades() {
		return nroUnidades;
	}
	public void setNroUnidades(int nroUnidades) {
		this.nroUnidades = nroUnidades;
	}
	public MyLinkedList<Ruta> getListRutas() {
		return listRutas;
	}
	public void setListRutas(MyLinkedList<Ruta> listRutas) {
		this.listRutas = listRutas;
	}

}
