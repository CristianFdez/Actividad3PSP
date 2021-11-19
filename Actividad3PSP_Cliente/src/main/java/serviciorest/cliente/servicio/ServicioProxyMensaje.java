package serviciorest.cliente.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServicioProxyMensaje {

	public static final String URL = "http://localhost:8081/";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public String obtener(String path){
		String mensaje = restTemplate.getForObject(URL + path, String.class);
		return mensaje;
		
	}
}
