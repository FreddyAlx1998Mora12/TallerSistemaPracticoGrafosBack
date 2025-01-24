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

import edu.academic.taller.Services.EstacionService;
import edu.academic.taller.Services.RutaService;
import edu.academic.taller.models.Estacion;
import edu.academic.taller.models.Ruta;
import edu.academic.taller.models.list.MyLinkedList;

@Path("/estacion")
public class EstacionApi {

	EstacionService estacionService = new EstacionService();

	// lista todas las familias censadas
	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllEstacions() {
		HashMap map = new HashMap<>();
//		PersonaService personaService = new PersonaService();
		map.put("msg", "OK");
		map.put("data", estacionService.listAll().toArray());

		return Response.ok(map).build();
	}


	@Path("/save")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(HashMap request) { // data en el hashmap, el cuerpo del json a receptar o que envia el usuario

		// Objetos
		Estacion linea_estacion = new Estacion();

		// Capturar variables		
		linea_estacion.setCodigoEstacion(request.get(("codig_lineaEstacion")).toString());
		linea_estacion.setHoraInicio(request.get(("hora_inicio")).toString());
		linea_estacion.setHoraFin(request.get(("hora_fin")).toString());
		linea_estacion.setNroUnidades((int) request.get(("total_unidades")));
		linea_estacion.setListRutas(new MyLinkedList<>());
		// Capturar la lista de rutas (Si en caso tiene)		
//		System.out.println(request.get("rutas").toString());
//		HashMap rutas = (HashMap) request.get(("rutas"));
		
		estacionService.setEstacion(linea_estacion);
		
		HashMap res = new HashMap<>(); // Para construir un cuerpo de response

		try {
//			fs.guardarCenso(); // guardo correctamente
			estacionService.save();
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
		Estacion estacion = estacionService.buscarporId(idE);
		
		estacion.setCodigoEstacion(request.get(("codig_lineaEstacion")).toString());
		estacion.setHoraInicio(request.get(("hora_inicio")).toString());
		estacion.setHoraFin(request.get(("hora_fin")).toString());
		estacion.setNroUnidades((int) request.get(("total_unidades")));
		
		try {
			estacionService.setEstacion(estacion);
			estacionService.update(estacion, idE);
			
			return Response.ok(res).build();
//			return Response.ok(res).build(); 
		} catch (Exception e) {
			res.put("msg", e.getLocalizedMessage());
			res.put("cause", e.getCause());
			res.put("S", e.getStackTrace());
			return Response.status(Status.BAD_REQUEST).entity(res).build(); // retorna un estado de tipo STATUS (Enum)
		}
	}
	
	@Path("/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response agregarRuta(HashMap request) throws IndexOutOfBoundsException, Exception { 
		
		RutaService rs = new RutaService();
			
		HashMap res = new HashMap<>(); // Para construir un cuerpo de response
		// Validation
		int idE = (int) request.get(("idEstacion"));
		Estacion estacion = estacionService.buscarporId(idE-1);
		Ruta ruta = new Ruta();
		
		HashMap rutas = (HashMap) request.get(("rutas"));
		
		ruta.setDireccionPuntoParada(rutas.get("descripcion").toString());
		ruta.setLatitud(rutas.get("lat").toString());
		ruta.setLongitud(rutas.get("long").toString());
		
		rs.setRuta(ruta);
		
		try {
			rs.save();
			estacionService.setEstacion(estacion);
//			estacion.getListRutas().add(ruta);
			estacionService.agregarRuta(ruta);
			
			estacionService.update(estacion, idE);
			
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
