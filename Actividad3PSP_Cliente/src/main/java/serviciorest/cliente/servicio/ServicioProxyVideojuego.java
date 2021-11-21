package serviciorest.cliente.servicio;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import serviciorest.cliente.entidad.Videojuego;

//En esta clase definiremos la URL del servicio REST de videojuegos y
//los diferentes métodos para poder interactuar con dicho servicio REST

@Service
public class ServicioProxyVideojuego {

	public static final String URL = "http://localhost:8081/videojuegos/";

	@Autowired
	private RestTemplate restTemplate;
	
	public Videojuego obtener(int id){
		try {
			ResponseEntity<Videojuego> re = restTemplate.getForEntity(URL + id, Videojuego.class);
			HttpStatus hs= re.getStatusCode();
			if(hs == HttpStatus.OK) {	
				return re.getBody();
				
			}else {
				System.out.println("Respuesta no contemplada");
				return null;
			}
			
		}catch (HttpClientErrorException e) {
			System.out.println("El videojuego con id " + id + " NO se ha podiddo obtener");
		    System.out.println("El código de respuesta es " + e.getStatusCode());
		    return null;
		}
	}
	

	public Videojuego alta(Videojuego v){
		try {
			ResponseEntity<Videojuego> re = restTemplate.postForEntity(URL, v, Videojuego.class);
			System.out.println("alta -> Codigo de respuesta " + re.getStatusCode());
			return re.getBody();
			
		} catch (HttpClientErrorException e) {
			System.out.println("El videojuego NO se ha dado de alta");
		    System.out.println("El código de respuesta es " + e.getStatusCode());
		    return null;
		}
	}
	
	public boolean modificar(Videojuego v){
		try {
			restTemplate.put(URL + v.getId(), v, Videojuego.class);
			return true;
			
		} catch (HttpClientErrorException e) {
			System.out.println("El videojuego con id " + v.getId() + " NO se ha modificado");
		    System.out.println("El código de respuesta es " + e.getStatusCode());
		    return false;
		}
	}
	
	public boolean borrar(int id){
		try {
			restTemplate.delete(URL + id);
			return true;
			
		} catch (HttpClientErrorException e) {
			System.out.println("El videojuego con id " + id + " NO se ha borrado");
		    System.out.println("El código de respuesta es " + e.getStatusCode());
		    return false;
		}
	}

	public List<Videojuego> listar(){
		try {
			ResponseEntity<Videojuego[]> response = restTemplate.getForEntity(URL,Videojuego[].class);
			Videojuego[] arrayVideojuego = response.getBody();
			return Arrays.asList(arrayVideojuego);
			
		} catch (HttpClientErrorException e) {
			System.out.println("Error al obtener la lista de videojuegos");
			System.out.println("El código de respuesta es " + e.getStatusCode());
		    return null;
		}
	}
}


