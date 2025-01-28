package edu.academic.taller.models;

/**
 * Clase que identifica un punto o parada de una unidad de transporte
 */
public class Ruta {
	private int idRuta;
	private String descripcion; // Describe barrio, nombre de parada,
	private Double latitud, longitud; // Coordenadas

	public int getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(int idRuta) {
		this.idRuta = idRuta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
}
