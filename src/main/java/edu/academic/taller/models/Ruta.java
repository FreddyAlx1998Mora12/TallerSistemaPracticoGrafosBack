package edu.academic.taller.models;

/**
 * Clase que identifica un punto o parada de una unidad de transporte
 */
public class Ruta {
	private int idRuta;
	private String descripcion; // Describe barrio, nombre de parada,
	private String latitud, longitud; // Coordenadas

	public int getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(int idRuta) {
		this.idRuta = idRuta;
	}

	public String getDireccionPuntoParada() {
		return descripcion;
	}

	public void setDireccionPuntoParada(String direccionPuntoParada) {
		this.descripcion = direccionPuntoParada;
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
