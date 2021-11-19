package serviciorest.modelo.entidad;

public class Videojuego {
	private int id, nota;
	private String nombre, compania;
	
	public Videojuego(int id, String nombre, String compania, int nota) {
		super();
		this.id = id;
		this.nota = nota;
		this.nombre = nombre;
		this.compania = compania;
	}

	public Videojuego() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCompania() {
		return compania;
	}

	public void setCompania(String compania) {
		this.compania = compania;
	}
	
	
}
