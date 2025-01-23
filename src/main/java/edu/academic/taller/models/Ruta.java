package edu.academic.taller.models;

public class Ruta {
	private int idRuta;
	private String direccionPuntoParada; // Describe barrio, nombre de parada,
	private String latitud, longitud; // Coordenadas
	
	public int getIdRuta() {
		return idRuta;
	}
	public void setIdRuta(int idRuta) {
		this.idRuta = idRuta;
	}
	public String getDireccionPuntoParada() {
		return direccionPuntoParada;
	}
	public void setDireccionPuntoParada(String direccionPuntoParada) {
		this.direccionPuntoParada = direccionPuntoParada;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
}
