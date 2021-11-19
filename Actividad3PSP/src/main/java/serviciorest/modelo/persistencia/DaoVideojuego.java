package serviciorest.modelo.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import serviciorest.modelo.entidad.Videojuego;

@Component
public class DaoVideojuego {
	public List<Videojuego> listaVideojuegos;
	public int contador;
	
	public DaoVideojuego() {
		listaVideojuegos = new ArrayList<Videojuego>();
		
		Videojuego juego1 = new Videojuego(contador++, "Spyro", "Insomniac Games", 8);
		Videojuego juego2 = new Videojuego(contador++, "Call of Duty", "Infinity War", 7);
		Videojuego juego3 = new Videojuego(contador++, "Battlefield", "Electronics Arts", 6);
		Videojuego juego4 = new Videojuego(contador++, "Sonic", "SEGA", 5);
		Videojuego juego5 = new Videojuego(contador++, "World of Warcraft", "Blizzard", 9);
		
		listaVideojuegos.add(juego1);
		listaVideojuegos.add(juego2);
		listaVideojuegos.add(juego3);
		listaVideojuegos.add(juego4);
		listaVideojuegos.add(juego5);
	}
	
	public Videojuego get(int posicion) {
		try {
			return listaVideojuegos.get(posicion);
		}catch(IndexOutOfBoundsException iobe) {
			System.out.println("Videojuego fuera de rango");
			return null;
		}
	}
	
//	public Videojuego get(String nombre) {
//		try {
//			return listaVideojuegos.get(id) =;
//		}catch(IndexOutOfBoundsException iobe) {
//			System.out.println("Videojuego fuera de rango");
//			return null;
//		}
//	}
	
	public List<Videojuego> list(){
		return listaVideojuegos;
	}
	
	public void add(Videojuego v) {
		v.setId(contador++);
		listaVideojuegos.add(v);
	}
	
	public Videojuego delete(int posicion) {
		try {
			return listaVideojuegos.remove(posicion);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("delete -> Persona fuera de rango");
			return null;
		}
	}
	
	public Videojuego update(Videojuego v) {
		try {
			Videojuego pAux = listaVideojuegos.get(v.getId());
			pAux.setNombre(v.getNombre());
			pAux.setCompania(v.getCompania());
			pAux.setNota(v.getNota());

			return pAux;
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("update -> Persona fuera de rango");
			return null;
		}
	}
	
	public List<Videojuego> listByNombre(String nombre){
		List<Videojuego> listaVideojuegoAux = new ArrayList<Videojuego>();
		for(Videojuego v : listaVideojuegos) {
			if(v.getNombre().equalsIgnoreCase(nombre)) {
				listaVideojuegoAux.add(v);
			}
		}
		return listaVideojuegoAux;
	}
}
