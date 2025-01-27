package edu.academic.taller.rest;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.academic.taller.Services.RutaService;
import edu.academic.taller.models.Ruta;
import edu.academic.taller.models.Ruta;
import edu.academic.taller.models.list.MyLinkedList;

@Path("/ruta")
public class RutaApi {

	RutaService rutServic = new RutaService();

	// lista todas las familias censadas
	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllEstacions() {
		HashMap map = new HashMap<>();
//		PersonaService personaService = new PersonaService();
		map.put("msg", "OK");
		map.put("data", rutServic.listAll().toArray());

		return Response.ok(map).build();
	}

	@Path("/save")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(HashMap request) { // data en el hashmap, el cuerpo del json a receptar o que envia el usuario

		// Objetos
		Ruta ruta = new Ruta();

		// Capturar variables
		ruta.setDireccionPuntoParada(request.get(("descripcion")).toString());
		ruta.setLatitud(request.get(("lat")).toString());
		ruta.setLongitud(request.get(("long")).toString());

		// Capturar la lista de rutas (Si en caso tiene)
//		System.out.println(request.get("rutas").toString());
//		HashMap rutas = (HashMap) request.get(("rutas"));

		rutServic.setRuta(ruta);

		HashMap res = new HashMap<>(); // Para construir un cuerpo de response

		try {
//			fs.guardarCenso(); // guardo correctamente
			rutServic.save();
			res.put("msg", "OK");
			res.put("data", "Linea de Transporte registrada correctamente");
			return Response.ok(res).build();

		} catch (Exception e) {
			// TODO: handle exception
			res.put("msg", "ERROR");
			res.put("data", "Hubo un error al intentar registrar una linea de transporte " + e.toString());
			return Response.status(Status.BAD_REQUEST).entity(res).build(); // retorna un estado de tipo STATUS (Enum)
																			// de la entidad Buildr
		}
	}

	@Path("/update")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(HashMap request) throws IndexOutOfBoundsException, Exception {

		HashMap res = new HashMap<>(); // Para construir un cuerpo de response
		// Validation
		int idE = (int) request.get(("idEstacion"));
		Ruta ruta = rutServic.buscarporId(idE);

		ruta.setDireccionPuntoParada(request.get(("descripcion")).toString());
		ruta.setLatitud(request.get(("lat")).toString());
		ruta.setLongitud(request.get(("long")).toString());

		try {
			rutServic.setRuta(ruta);
			rutServic.update(ruta, idE);

			return Response.ok(res).build();
//			return Response.ok(res).build(); 
		} catch (Exception e) {
			res.put("msg", e.getLocalizedMessage());
			res.put("cause", e.getCause());
			res.put("S", e.getStackTrace());
			return Response.status(Status.BAD_REQUEST).entity(res).build(); // retorna un estado de tipo STATUS (Enum)
		}
	}

}
