package edu.academic.taller.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
import edu.academic.taller.models.Estacion;
import edu.academic.taller.models.Ruta;
import edu.academic.taller.models.graph.Adyacencia;
import edu.academic.taller.models.graph.GraphLabelDirect;
import edu.academic.taller.models.graph.GraphLabelNoDirect;

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
		HashMap res = new HashMap<>(); // Para construir un cuerpo de response

		try {

			// Capturar variables
			ruta.setDescripcion(request.get(("descripcion")).toString());
			ruta.setLatitud(Double.valueOf(request.get("lat").toString()));
			ruta.setLongitud(Double.valueOf(request.get("long").toString()));
			

			// Capturar la lista de rutas (Si en caso tiene)
//		System.out.println(request.get("rutas").toString());
//		HashMap rutas = (HashMap) request.get(("rutas"));

			rutServic.setRuta(ruta);

//			fs.guardarCenso(); // guardo correctamente
			rutServic.save();
			res.put("msg", "OK");
			res.put("data", "Ruta registrada correctamente");
			return Response.ok(res).build();

		} catch (Exception e) {
			// TODO: handle exception
			res.put("msg", "ERROR");
			res.put("data", "Hubo un error al intentar registrar una ruta " + e.toString());
			e.printStackTrace();
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
		int idE = (int) request.get(("idRuta"));
		Ruta ruta = rutServic.buscarporId(idE);

		ruta.setDescripcion(request.get(("descripcion")).toString());
		ruta.setLatitud((double) request.get(("latitud")));
		ruta.setLongitud((double) request.get(("longitud")));
		try {
			rutServic.setRuta(ruta);
			rutServic.update(ruta, idE-1);

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
	public Response delete(@PathParam("id") int id) throws IndexOutOfBoundsException, Exception {

		HashMap res = new HashMap<>();
		try {
			Ruta f = rutServic.buscarporId(id);
			System.out.println("Dato intentando eliminar " + f.getIdRuta());

			rutServic.delete(id); // elimina todo el dato completo

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
	
	@Path("/search/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchbyId(@PathParam("id") Integer txt) throws Exception {
		HashMap map = new HashMap<>();
//		PersonaService personaService = new PersonaService();

		try {
			if (txt.intValue() > 0) {
				Ruta persona = rutServic.buscarporId(txt);
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

	// POST
	@Path("/create/graph")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearGrafo() throws Exception {
		RutaService rs = new RutaService();
		Random r = new Random();
//		r.ints(1, 10);
//		System.out.println(r.nextInt(10));

		Map map = new HashMap<>();

		try {
			GraphLabelDirect<Ruta> grafo = new GraphLabelDirect(rutServic.listAll().getLength(), Ruta.class);

			// etiquetamos grafos
			Ruta[] arr_est = (Ruta[]) rutServic.listAll().toArray();

//				iteramos para etiquetar
			for (int i = 1; i <= arr_est.length; i++) {
				grafo.labelVertice(i, arr_est[i - 1]);
			}

			System.out.println("Grafo etiquetado: " + grafo.toString());

			System.out.println("Grafo es etiquetado?: " + grafo.isLabelGraph());

			// conectamos todos los vertices
	        for (int i = 0; i < arr_est.length - 1; i++) {
	            grafo.insertEdgeLabel(arr_est[i], arr_est[i + 1], rs.calculoDistancia(arr_est[i], arr_est[i + 1]));
	        }

	        // generamos aristas aleatorias adicionales
	        for (int i = 0; i < arr_est.length; i++) {
	            int n_r = r.nextInt(1, arr_est.length - 1);
	            if (n_r != i) { 
	                grafo.insertEdgeLabel(arr_est[i], arr_est[n_r], rs.calculoDistancia(arr_est[i], arr_est[n_r]));
	            }
	        }
			
			grafo.drawGraph();
			map.put("data", "Grafo creado correctmente.");
		} catch (Exception e) {
			// TODO: handle exception
			map.put("error", e.getLocalizedMessage());
			map.put("causa", e.getCause());
			e.printStackTrace();
		}
		// Creamos una instancia de grafo, un grafo dirigido con 5 vertices

//			System.out.println("Imprime grafo...\n"+grafo.toString());

		map.put("msg", "OK");

		return Response.ok(map).build();
	}
	
	// POST
		@Path("/load/graph")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public Response cargarGrafo() throws Exception {
			RutaService rs = new RutaService();
			// Integer es el id del vertice, Adyacencia son los objetos a las que tiene adyacencia el ID
			HashMap<Integer, Adyacencia[]> mapGraph;
			Map map = new HashMap<>();

			try {
				// Creamos una instancia de graph y empezamos a construir
				GraphLabelDirect<Ruta> grafo = new GraphLabelDirect(rutServic.listAll().getLength(), Ruta.class);
				System.out.println("Grafo antes de construirse, esta etiquetado"+grafo.isLabelGraph());
				
				// Arreglo de las rutas necesarios
				Ruta[] arr_rut = (Ruta[]) rutServic.listAll().toArray();
 				
				// Dict del load graph que existe en 
				mapGraph = grafo.loadGraph();
				
				// resultado 9 -> 
				System.out.println("longitud grafo.. nro vertices "+grafo.nro_vertice());
				System.out.println("longitud dict.."+mapGraph.size());
				
				// iteramos para etiquetar todos los objetos  ruta
				for (int i = 1; i <= arr_rut.length; i++) {
					grafo.labelVertice(i, arr_rut[i - 1]);
				}
				
				// recordemos que el id es el vertice origen
				mapGraph.forEach((id, adyacencias) -> {
//		            System.out.println("Clave: " + id + ", Valor: " + adyacencias);
		            for (Adyacencia ady : adyacencias) {
		            	// etiqueramos el grafo
						try {
							grafo.insertEdgeLabel(grafo.getLabel(id), grafo.getLabel(ady.getVertice_destino()), 
									rs.calculoDistancia(grafo.getLabel(id), grafo.getLabel(ady.getVertice_destino())));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
		        });
				
				// grafo construido, ejecutamos 
				// testeamos si esta el grafo construido
				System.out.println("Grafo construido, esta etiquetado? "+grafo.isLabelGraph());
				
				// implementar logica de bpp
				Integer[] nodos_dfs = grafo.dfs(3);

				System.out.println("Nodos visitados con algoritmo dfs");
				// mostramos las etiquetas 
				for (int i = 0; i < nodos_dfs.length-1; i++) {
					System.out.println("."+nodos_dfs[i]);
					System.out.println("-> "+grafo.getLabel(nodos_dfs[i]).getDescripcion());
				}
				

				map.put("data", "Grafo cargado correctmente.");
			} catch (Exception e) {
				// TODO: handle exception
				map.put("error", e.getLocalizedMessage());
				map.put("causa", e.getCause());
				e.printStackTrace();
			}
			// Creamos una instancia de grafo, un grafo dirigido con 5 vertices

//				System.out.println("Imprime grafo...\n"+grafo.toString());

			map.put("msg", "OK");

			return Response.ok(map).build();
		}

}
