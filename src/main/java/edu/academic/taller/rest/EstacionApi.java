package edu.academic.taller.rest;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

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
import edu.academic.taller.models.graph.GraphLabelDirect;
import edu.academic.taller.models.graph.GraphLabelNoDirect;
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

	@Path("/search/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchbyId(@PathParam("id") Integer txt) throws Exception {
		HashMap map = new HashMap<>();
//		PersonaService personaService = new PersonaService();

		try {
			if (txt.intValue() > 0) {
				Estacion persona = estacionService.buscarporId(txt);
				map.put("msg", "OK");
				if (persona.equals(null)) {
					// debe retornar un arreglo vacio
					map.put("data", "No existe la estacion con el ID");
				} else
					map.put("data", persona);

//				return Response.ok(map).build();
			} else {
				map.put("msg", "Invalid");
				map.put("data", "El id que ingreso es invalido");
				return Response.status(Status.NOT_ACCEPTABLE).entity(map).build();
			}
		} catch (Exception e) {
			// TODO: handle exception
			map.put("msg", "Error");
			map.put("data", e.getCause());
			return Response.status(Status.BAD_REQUEST).entity(map).build();
		}
//		return null;
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
//		linea_estacion.setListRutas(new MyLinkedList<>());
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
			estacionService.update(estacion, idE - 1);

			return Response.ok(res).build();
//			return Response.ok(res).build(); 
		} catch (Exception e) {
			res.put("msg", e.getLocalizedMessage());
			res.put("cause", e.getCause());
			res.put("S", e.getStackTrace());
			return Response.status(Status.BAD_REQUEST).entity(res).build(); // retorna un estado de tipo STATUS (Enum)
		}
	}

	@Path("/delete/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id) throws IndexOutOfBoundsException, Exception { // data en
																									// el
																									// hashmap,
																									// el
																									// cuerpo
																									// del
																									// json
																									// a
																									// receptar
																									// o que
																									// envia
																									// el
																									// usuario
		HashMap res = new HashMap<>();
		try {
			Estacion f = estacionService.buscarporId(id);
			System.out.println("Dato intentando eliminar " + f.getCodigoEstacion());

			estacionService.delete(id); // elimina todo el dato completo

		} catch (Exception e) {
			res.put("msg", e.getLocalizedMessage());
			res.put("cause", e.getCause());
			res.put("StackTrace", e.getStackTrace());
			return Response.status(Status.BAD_REQUEST).entity(res).build(); // retorna un estado de tipo STATUS (Enum)
		}
		res.put("msg", "OK");
		res.put("data", "Dato eliminado correctamente");
		return Response.ok(res).build();
	}

	@Path("/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response agregarRuta(HashMap request) throws IndexOutOfBoundsException, Exception {

		RutaService rs = new RutaService();
		EstacionService ser = new EstacionService();

		HashMap res = new HashMap<>(); // Para construir un cuerpo de response
		// Validation
		int idE = (int) request.get(("idEstacion"));
		System.out.println("id buscad:" + idE);
		Estacion estacion = ser.buscarporId(idE);
		System.out.println("Objeto estacion es nulo?" + estacion.equals(null));
		Ruta ruta = new Ruta();

		HashMap rutas = (HashMap) request.get(("rutas"));

		ruta.setDireccionPuntoParada(rutas.get("descripcion").toString());
		ruta.setLatitud(rutas.get("lat").toString());
		ruta.setLongitud(rutas.get("long").toString());

		System.out.println("Captura en el metodo add el objeto de rutas a ingresar: " + ruta.getDireccionPuntoParada()
				+ " ," + ruta.getLatitud() + ", " + ruta.getLongitud());

		try {
			System.out.println(
					"Envia el siguiente objeto: " + estacion.getCodigoEstacion() + ", " + estacion.getIdEstacion());
			ser.setEstacion(estacion);

			System.out.println("Existe ruta?: " + rs.existRuta(ruta));
			if (!rs.existRuta(ruta)) { // Si la ruta no existe
				rs.setRuta(ruta);
				rs.save();
			} else {
				ruta = rs.buscarpordescripcion(ruta.getDireccionPuntoParada());
				System.out.println("Si existe ruta: " + ruta);
				System.out.println("Si existe ruta: " + ruta.getIdRuta());
			}

//			ser.agregarRuta(ruta);

			return Response.ok(res).build();
//			return Response.ok(res).build(); 
		} catch (Exception e) {
			res.put("msg", e.getLocalizedMessage());
			res.put("cause", e.getCause());
			res.put("S", e.getStackTrace());
			return Response.status(Status.BAD_REQUEST).entity(res).build(); // retorna un estado de tipo STATUS (Enum)
		}
	}

	// POST
	@Path("/testLabelNoDirect")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGraphLabelNoDirect() throws Exception {
		RutaService rs = new RutaService();
		
		Map map = new HashMap<>();

		try {
			GraphLabelNoDirect<Estacion> grafo = new GraphLabelNoDirect(estacionService.listAll().getLength(), Estacion.class);

			// etiquetamos grafos
			Estacion[] arr_est = (Estacion[]) estacionService.listAll().toArray();
			
//			iteramos para etiquetar
			for (int i = 1; i <= arr_est.length; i++) {
				grafo.labelVertice(i, arr_est[i-1]);
			}
			
			System.out.println("Grafo etiquetado: "+grafo.toString());
			
			System.out.println("Grfo es etiquetado?: "+grafo.isLabelGraph());

//			 conectamos vertices
			grafo.insertEdgeLabel(arr_est[0], arr_est[1]);
			grafo.insertEdgeLabel(arr_est[1], arr_est[2]);
			grafo.insertEdgeLabel(arr_est[1], arr_est[3]);
//			grafo.insertEdgeLabel("Lorena", "Victor");
//			grafo.add_edge(grafo.getIndexVerticeLabel("Victor"), grafo.getIndexVerticeLabel("Lorena"));
//			grafo.insertEdgeLabel("Victor", "Lilian");
//			grafo.insertEdgeLabel("Lorena", "Lilian");
//			grafo.insertEdgeLabel("Freddy", "Cristhian");
//			grafo.insertEdgeLabel("Freddy", "Diego");

			System.out.println(grafo.toString());
			map.put("data", grafo.toString());
		} catch (Exception e) {
			// TODO: handle exception
			map.put("error", e.getLocalizedMessage());
			map.put("causa", e.getCause());
			e.printStackTrace();
		}
		// Creamos una instancia de grafo, un grafo dirigido con 5 vertices

//		System.out.println("Imprime grafo...\n"+grafo.toString());

		map.put("msg", "OK");

		return Response.ok(map).build();
	}

}
