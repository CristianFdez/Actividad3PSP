package serviciorest.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import serviciorest.cliente.entidad.Videojuego;
import serviciorest.cliente.servicio.ServicioProxyMensaje;
import serviciorest.cliente.servicio.ServicioProxyVideojuego;

@SpringBootApplication
public class Application implements CommandLineRunner{

	@Autowired
	private ServicioProxyVideojuego spv;
	
	@Autowired
	private ServicioProxyMensaje spm;
	
	@Autowired
	private ApplicationContext context;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	public static void main(String[] args) {
		System.out.println("Cliente -> Cargando el contexto de Spring");
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("****** Arrancando el cliente REST ******");
		System.out.println("*************  MENSAJE *****************");
		String mensaje = spm.obtener("mensaje");
		System.out.println("run -> Mensaje: " + mensaje);
		
		System.out.println("***********  MENSAJE HTML **************");
		String mensajeHTML = spm.obtener("mensajeHTML");
		System.out.println("run -> Mensaje: " + mensajeHTML);
		
		System.out.println("*********** ALTA VIDEOJUEGO ***************");
		Videojuego videojuego = new Videojuego();
		videojuego.setNombre("Fortnite");
		videojuego.setCompania("Epic Games");
		videojuego.setNota(10);
		
		Videojuego vAlta = spv.alta(videojuego);
		System.out.println("run -> Videojuego dado de alta " + vAlta);
		
		System.out.println("************ GET VIDEOJUEGO ***************");
		videojuego = spv.obtener(vAlta.getId());
		System.out.println("run -> Videojuego con id " + vAlta.getId() + ":" + videojuego);
		
		System.out.println("************ GET VIDEOJUEGO ERRÓNEO ***************");
		videojuego = spv.obtener(20);
		System.out.println("run -> Videojuego con id 20: " + videojuego);
		
		System.out.println("********* MODIFICAR VIDEOJUEGO *************");	
		Videojuego vModificar = new Videojuego();
		vModificar.setId(vAlta.getId());
		vModificar.setNombre("World of Warcraft");
		vModificar.setCompania("Blizzard");
		vModificar.setNota(10);
		boolean modificada = spv.modificar(vModificar);
		System.out.println("run -> videojuego modificado? " + modificada);
		
		System.out.println("********* MODIFICAR VIDEOJUEGO ERRÓNEO*************");			
		vModificar.setNombre("FIFA");
		vModificar.setCompania("EA Sports");
		vModificar.setId(20);
		vModificar.setNota(6);
		modificada = spv.modificar(vModificar);
		System.out.println("run -> videojuego modificado? " + modificada);
		
		System.out.println("********** BORRAR VIDEOJUEGO **************");
		boolean borrada = spv.borrar(vAlta.getId());
		System.out.println("run -> Videojuego con id " + vAlta.getId() + " borrado?" + borrada);	
		
		System.out.println("******** BORRAR VIDEOJUEGO ERRÓNEO *******");
		borrada = spv.borrar(20);
		System.out.println("run -> Persona con id 20 borrada? " + borrada);		
		
		System.out.println("********** LISTAR VIDEOJUEGOS ***************");
		List<Videojuego> listaVideojuego = spv.listar(null);

		listaVideojuego.forEach((v) -> System.out.println(v));
		
		System.out.println("******* LISTAR VIDEOJUEGOS POR NOMBRE *******");
		listaVideojuego = spv.listar("World of Warcraft");
		listaVideojuego.forEach((v) -> System.out.println(v));
		
		System.out.println("******************************************");		
		System.out.println("******** Parando el cliente REST *********");	

		pararAplicacion();
		
	}
	
	public void pararAplicacion() {		
		SpringApplication.exit(context, () -> 0);
	}

}
