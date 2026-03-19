package dev.fixfis.appmascotas.entidad;

public class Mascota {
	private String tipo;
	private String color;
	private String peso;
	private int id;
	private String nombre;

	public void setTipo(String tipo){
		this.tipo = tipo;
	}

	public String getTipo(){
		return tipo;
	}

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return color;
	}

	public void setPeso(String peso){
		this.peso = peso;
	}

	public String getPeso(){
		return peso;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setNombre(String nombre){
		this.nombre = nombre;
	}

	public String getNombre(){
		return nombre;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"tipo = '" + tipo + '\'' + 
			",color = '" + color + '\'' + 
			",peso = '" + peso + '\'' + 
			",id = '" + id + '\'' + 
			",nombre = '" + nombre + '\'' + 
			"}";
		}
}
