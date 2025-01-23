package edu.academic.taller;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class Main {
	
	public static final String BASE_URI = "http://localhost:8080/myapp/";
	
	public static final String filePath = "src/main/resources/media/"; 
	
	public static HttpServer startServer() {

        final ResourceConfig rc = new ResourceConfig().packages("edu.academic.project.models.rest");

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }
	
	public static void main(String[] args) throws IOException, IndexOutOfBoundsException{
		
		// Levantar servidor		
		try {
            final HttpServer server = startServer();
            System.out.println(String.format("Jersey app started with WADL available at "
                    + "%sapplication.wadl\nSERVER UPP...!!\nHit enter to stop it...", BASE_URI));
            System.in.read();
            server.stop();
        } catch (Exception ex) {
            System.out.println("Error en el servidor" +ex);
        }
	}

}