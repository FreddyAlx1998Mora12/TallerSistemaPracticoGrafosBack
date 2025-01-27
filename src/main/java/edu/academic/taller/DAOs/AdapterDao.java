package edu.academic.taller.DAOs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.academic.taller.models.list.MyLinkedList;

public class AdapterDao<T> implements InterfaceDao<T> {

//	Variables para manejar la logica de negocio
	private Class<T> claseSerializer;
	private Gson g;
	public static String filePath = "src/main/resources/media/"; // para almacenar los archivos

//	Constructor
	public AdapterDao(Class clazz) {
		this.claseSerializer = clazz;
		GsonBuilder gb = new GsonBuilder();
		this.g = gb.setPrettyPrinting().create();
	}

	@Override
	public void persist(T object) throws Exception {
		MyLinkedList<T> list = listAll();

		list.add(object); // agrega a la lista de mylinkedList en
		String info = g.toJson(list.toArray());

		// PERSISTE EN UN ARCHIVO
		saveFile(info);
	}

	@Override
	public void merge(T object, Integer index) throws Exception {
		// Objetivo: Actualiza un dato
		// 1. Buscar el dato por id
		// 2. guardar la nueva informacion
		// persistir en el file

		// lista todos
		MyLinkedList<T> list = listAll();
		// 1.busca y actualizar un elemento por id
		list.updateElement(index, object);
		// 2.
		String info = g.toJson(list.toArray());
		System.out.println("Dato a modificar: " + info);
		// 3.
		saveFile(info);
	}

	@Override
	public MyLinkedList listAll() {

		MyLinkedList list = new MyLinkedList<>();
		try {
			String data = readFile(); // lee toda la informacion del archivo y la convierte en un string
			if (!data.isEmpty()) {
				T[] matrix = (T[]) g.fromJson(data, java.lang.reflect.Array.newInstance(claseSerializer, 0).getClass()); // la
																															// data
																															// la
																															// convierte
																															// a
																															// un
																															// array,
																															// y
																															// posterior
																															// a
																															// formato
																															// JSON,
																															// dicho
																															// formato
																															// sera
																															// un
																															// array
																															// de
																															// tipoT

				list.tolist(matrix); // el array convierte a una list enlazada
//				list = g.fromJson(data, new TypeToken<MyLinkedList<T>>(){}.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public T get(Integer id) throws Exception {
		// Obtener por id
		MyLinkedList<T> list = listAll(); // listAll es de esta clase, abarca todos los elementos agregados
		return list.get(id);

		/*
		 * MyLinkedList<T> list = listAll(); if (!list.isEmptyLinkedList()) { T[] matriz
		 * = list.toArray(); for (int i = 0; i < matriz.length; i++) { if
		 * (getId(matriz[i]).intValue() == id.intValue()) { return matriz[i]; } } }
		 * return null;
		 */
	}

	@Override
	public void delete(int index) throws Exception {
		// TODO Auto-generated method stub
		MyLinkedList<T> list = listAll();
		list.remove(index);
		System.out.println("Elemento en posicion index : " + index + ", se elimino correctamente");

		String info = g.toJson(list.toArray());
		// 3.
		saveFile(info); // guarda
	}

	/*
	 * ######################################################## OPERACIONES QUE
	 * SIMULARAN UNA BASE DE DATOS
	 * ########################################################
	 */
	/**
	 * Funcion para la escritura de datos en formatoJSON
	 * 
	 * @param datoJson
	 * @throws Exception
	 * @throws IOException
	 */
	private void saveFile(String datoJson) throws Exception, IOException {
		// 1. Crear un Objeto File o Archivo para almacenar los datos
		File file = new File(filePath + claseSerializer.getSimpleName() + ".json");
		// 2. Objeto como tipo cursor para la escritura
		FileWriter fw = new FileWriter(file);
		try { // Usamos try-with-resources para cerrar automáticamente el FileWriter
			fw.write(datoJson);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			System.out.println("Error al escribir en el archivo: " + e.getMessage());
		}

	}

	private String readFile() throws FileNotFoundException, Exception {
		// 1. Crear objeto
		File file = new File(filePath + claseSerializer.getSimpleName() + ".json");
		// 2. Clase facilitador de lectura (fileReader lector de archivo(file archivo)))
		Scanner in = new Scanner(new FileReader(file));
		StringBuilder sb = new StringBuilder();

		while (in.hasNext()) {
			sb.append(in.nextLine()).append("\n");
		}
		in.close(); // cerrar siempre
		return sb.toString().trim();
	}

	public void UpdateFile(MyLinkedList<T> dataList) throws Exception {
		// 1. Crear un Objeto File o Archivo para almacenar los datos
		File file = new File(filePath + claseSerializer.getSimpleName() + ".json");
		String info = g.toJson(dataList.toArray());
		// 2. Objeto como tipo cursor para la escritura
		FileWriter fw = new FileWriter(file);
		try { // Usamos try-with-resources para cerrar automáticamente el FileWriter
			fw.write(info);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			System.out.println("Error al escribir en el archivo: " + e.getMessage());
		}
	}

	// metodo que retorna un valor int (obtiene el identificador del objeto),
	// parametro objeto tipo dato generico T
	private Integer getId(T obj) throws Exception {
		try {
			// Declara una variable tipo method, un lang.reflect
			Method method = null;
			for (Method m : claseSerializer.getMethods()) { // obtiene los metodos de la clase o objeto a serializar,
															// itera cada
				// metodo
				if (m.getName().equalsIgnoreCase("getId")) { // verifica que el metodo actual tenga el atributo tal ""
																// ejm : el en objeto Persona getIdPersona
					method = m; // asigna lo que devuelve el getId
					break;
				}
			}
			// si no encuentra el metodo, vuelve a iterar pero esta vez eleando a la clase
			// padre
			if (method == null) {
				for (Method m : claseSerializer.getSuperclass().getMethods()) {
					if (m.getName().equalsIgnoreCase("getId")) {
						method = m;
						break;
					}
				}
			}

			// si exite el method retorna una invoke
			if (method != null)
				return (Integer) method.invoke(obj);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocurrio algun error en la obtencion de getIdent de la clase, " + e.getMessage());
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

}
