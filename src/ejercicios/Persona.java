package ejercicios;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Persona {
	private int id;
	private String nombre;
	private boolean casado;
	private String sexo;

//	public Persona(int id, String nombre, boolean casado, String sexo) {//No funciona con XML, solo con JSON
//		super();
//		this.id = id;
//		this.nombre = nombre;
//		this.casado = casado;
//		this.sexo = sexo;
//	}

	@XmlElement(name="sexoGalego")
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

//	@XmlAttribute
	@XmlElement(name="idGalego")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement(name="nombreGalego")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlElement(name="casadoGalego")
	public boolean isCasado() {
		return casado;
	}

	public void setCasado(boolean casado) {
		this.casado = casado;
	}
}
