package edu.academic.taller.DAOs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.academic.taller.models.list.MyLinkedList;

public class AdapterDAO<T> implements InterfazDAO<T> {

	private Class<T> clazz;
	private Gson g;
	public static String filePath = "src/main/resources/media/"; // para almacenar los archivos

	public AdapterDAO(Class clazz) {
		this.clazz = clazz;
		GsonBuilder gb = new GsonBuilder();
		this.g = gb.setPrettyPrinting().create();
	}

	@Override
	public void persist(T object) throws Exception {

		MyLinkedList<T> list = listAll();
		list.add(object);
		String info = g.toJson(list.toArray());

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
		list.updateNode(index, object);
		// 2.
		String info = g.toJson(list.toArray());
		// System.out.println("Dato a modificar: " + info);
		// 3.
		saveFile(info);
	}

	@Override
	public MyLinkedList listAll() {
		// TODO Auto-generated method stub
		MyLinkedList list = new MyLinkedList<>();
		try {
			String data = readFile(); // lee toda la informacion del archivo y la convierte en un string
			T[] matrix = (T[]) g.fromJson(data, java.lang.reflect.Array.newInstance(clazz, 0).getClass()); // la data la
																											// convierte
																											// a un
																											// array, y
																											// posterior
																											// a formato
																											// JSON,
																											// dicho
																											// formato
																											// sera un
																											// array de
																											// tipoT

			list.tolist(matrix); // el array convierte a una list enlazada
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// Obtiene el objeto segun el id
	@Override
	public T get(Integer id) throws Exception {
		// Implementar segun lo necesario
		// MyLinkedList<T> list = listAll(); // listAll es de esta clase, abarca todos los elementos agregados
		// return list.get(id);
		MyLinkedList<T> list = listAll();
        if (!list.isEmptyLinkedList()) {
            T[] matriz = list.toArray();
            for (int i = 0; i < matriz.length; i++) {
                if (getIdent(matriz[i]).intValue() == id.intValue()) {
                    return matriz[i];
                }
            }
        }
        return null;
	}

	// metodos para leer archivos
	private String readFile() throws Exception, FileNotFoundException {

		File file = new File(filePath + clazz.getSimpleName() + ".json");
		Scanner in = new Scanner(new FileReader(file));
		StringBuilder sb = new StringBuilder();

		if (!file.exists()) {
			System.out.println("El archivo " + file.getName() + ", debe crear uno nuevo");
			throw new FileNotFoundException("Error, no existe el archivo que desea leer");
		}
		while (in.hasNext()) {
			sb.append(in.nextLine()).append("\n");
		}
		in.close(); // cerrar siempre
		return sb.toString().trim();
	}

	// metodo para escribir archivos
	private void saveFile(String data) throws Exception {
		File file = new File(filePath + clazz.getSimpleName() + ".json");

		FileWriter f = new FileWriter(file);
		try {
			f.write(data);
			f.flush();
			f.close();
		} catch (Exception e) {
			System.out.println("Error al escribir en el archivo: " + e.getMessage());
		}
//        f.close();
	}

	@Override
	public void delete(int index) throws Exception {
		MyLinkedList<T> list = listAll();
		list.remove(index);
		System.out.println("Elemento en posicion index : " + index + ", se elimino correctamente");

		String info = g.toJson(list.toArray());
		// 3.
		saveFile(info); // guarda
	}
	
	// metodo que retorna un valor int (obtiene el identificador del objeto), parametro objeto tipo dato generico T
	private Integer getIdent(T obj) throws Exception{
        try {
        	// Declara una variable tipo method, un lang.reflect        	
            Method method = null;
            for (Method m : clazz.getMethods()) { // obtiene los metodos de la clase o objeto a serializar, itera cada metodo
                if (m.getName().contains("getId")) { // verifica que el metodo actual tenga el atributo tal "" ejm : el en objeto Persona getIdPersona
                    method = m; // asigna lo que devuelve el getId
                    break;
                }
            }
            // si no encuentra el metodo, vuelve a iterar pero esta vez eleando a la clase padre
            if (method == null) {
                for (Method m : clazz.getSuperclass().getMethods()) {
                    if (m.getName().contains("getId")) {
                        method = m;
                        break;
                    }
                }
            }
            
            // si exite el method invoke el metodo que tiene el obj 
            if (method != null)
                return (Integer) method.invoke(obj);
        } catch (Exception e) {
            // TODO: handle exception
        	System.out.println("Ocurrio algun error en la obtencion de getIdent de la clase, "+e.getMessage());
        	e.printStackTrace();
            return -1;
        }
        return -1;
    }

}
