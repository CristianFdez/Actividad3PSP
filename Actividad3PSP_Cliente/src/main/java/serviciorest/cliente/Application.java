package serviciorest.cliente;

import java.util.List;
import java.util.Scanner;

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

//En esta clase definiremos en el método run(), el menú a elegir por el cliente y 
//la funcionalidad de cada una de las opciones.

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
		
		try (Scanner sc = new Scanner(System.in)){
			int opcion;
			String texto = "";
			boolean continuar = true;
			Videojuego videojuego = new Videojuego();
			
			do {
				escribirMenu();
				opcion = Integer.parseInt(sc.nextLine());
				
				switch (opcion) {
				case 1:
					System.out.println("************ ALTA VIDEOJUEGO *************");					
					System.out.println("Introduce el nombre del videojuego:");
					texto = sc.nextLine();
					videojuego.setNombre(texto);
					
					System.out.println("Introduce la compañia del videojuego:");
					texto = sc.nextLine();
					videojuego.setCompania(texto);
					
					System.out.println("Introduce la nota del videojuego:");
					texto = sc.nextLine();
					videojuego.setNota(Integer.parseInt(texto));	
					
					Videojuego vAlta = spv.alta(videojuego);
					
					if(vAlta == null) {
						System.out.println("run -> Videojuego NO dado de alta por que ya existe");
					}else {
						System.out.println("run -> Videojuego dado de alta " + vAlta);
					}					
					break;
					
				case 2:					
					System.out.println("********** BORRAR VIDEOJUEGO **************");
					System.out.println("Introduce el id del videojuego:");
					texto = sc.nextLine();
					
					boolean borrada = spv.borrar(Integer.parseInt(texto));
					System.out.println("run -> Videojuego con id " + Integer.parseInt(texto) + " borrado? " + borrada);					
					break;
					
				case 3:
					System.out.println("********* MODIFICAR VIDEOJUEGO *************");
					System.out.println("Introduce el id del videojuego:");
					texto = sc.nextLine();
					videojuego.setId(Integer.parseInt(texto));
					
					System.out.println("Introduce el nombre del videojuego:");
					texto = sc.nextLine();
					videojuego.setNombre(texto);
					
					System.out.println("Introduce la compañia del videojuego:");
					texto = sc.nextLine();
					videojuego.setCompania(texto);
					
					System.out.println("Introduce la nota del videojuego:");
					texto = sc.nextLine();
					videojuego.setNota(Integer.parseInt(texto));
					
					boolean modificada = spv.modificar(videojuego);
					System.out.println("run -> videojuego modificado? " + modificada);				
					break;
					
				case 4:
					System.out.println("************ GET VIDEOJUEGO ***************");
					System.out.println("Introduce el id del videojuego:");
					texto = sc.nextLine();
					videojuego = spv.obtener(Integer.parseInt(texto));
					
					if(videojuego != null) {
						System.out.println("run -> Videojuego con id " + Integer.parseInt(texto) + ":" + videojuego);
					}								
					break;
					
				case 5:
					System.out.println("********** LISTAR VIDEOJUEGOS ***************");
					List<Videojuego> listaVideojuego = spv.listar();
			
					listaVideojuego.forEach((v) -> System.out.println(v));
					break;
					
				case 6:
					System.out.println("******************************************");		
					System.out.println("******** Parando el cliente REST *********");
					pararAplicacion();
					continuar = false;
					break;
					
				default:
					System.out.println("Opción incorrecta");
				}
				
			}while(continuar);

		} catch (Exception e) {
			System.err.println("CLIENTE: Error -> " + e);
			e.printStackTrace();
		}
		
		System.out.println("CLIENTE: Fin del programa");	
	}
	
	private static void escribirMenu() {
		System.out.println();
		System.out.println("Elige la opción deseada:");
		System.out.println("--------------------------");
		System.out.println("1 = Dar de alta un videojuego");
		System.out.println("2 = Dar de baja un videojuego por ID");
		System.out.println("3 = Modificar un videojuego por ID");
		System.out.println("4 = Obtener un videojuego por ID");
		System.out.println("5 = Listar todos los videojuegos");
		System.out.println("6 = Salir de la aplicación");
		System.out.println("--------------------------");
		System.out.println("¿Qué opción eliges?");
	}
	
	public void pararAplicacion() {		
		SpringApplication.exit(context, () -> 0);
	}

}
