package Tema1_Ejer9;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

class alumno {
	public String nombre;
	public int fechaNacimiento;
	public int codigo;

	public alumno(String nombre, int fechaNacimiento, int codigo) {
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.codigo = codigo;
	}
}

public class Principal9 {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		Principal9 p = new Principal9();
		File file = new File("D:/Ciclo/Acceso a datos/Tema1/ejer9.dat");
		ArrayList<alumno> alumnos = null;
		try {
			alumnos = p.abrirArchivo(file);
		} catch (IOException e1) {
			System.out.println("Error al abrir");
		}
		int eleccion = -1;
		do {
			System.out.println("1.Dar de alta nuevos alumnos");
			System.out.println("2.Consultar alumnos");
			System.out.println("3.Modificar alumnos");
			System.out.println("4.Borrar alumnos");
			System.out.println("5.Salir");
			System.out.println("Escribe tu eleccion");
			try {
				eleccion = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
			}
			switch (eleccion) {
			case 1:
				try {
					alumnos = p.darAltaAlumno(alumnos);
				} catch (Exception e) {
				}
				break;
			case 2:
				try {
					p.consultarAlumnos(alumnos);
				} catch (Exception e) {
				}
				break;
			case 3:
				try {
					alumnos = p.modificarAlumnos(alumnos);
				} catch (Exception e) {
				}
				break;
			case 4:
				try {
					alumnos = p.borrarAlumnos(alumnos);
				} catch (Exception e) {
				}
				break;
			case 5:
				try {
					p.guardarDatos(file, alumnos);
				} catch (IOException e) {
					System.out.println("Error al guardar");
					e.printStackTrace();
				}
				break;
			default:
				System.out.println("Debes introducir un valor valido");
				break;
			}
		} while (eleccion != 5);

	}
	
	private alumno crearAlumno(ArrayList<alumno> alumnos) {
		boolean errorDatos = false;
		alumno alumno = null;
		do {
			System.out.println("Introduce el nombre");
			String nombre = sc.nextLine();
			System.out.println("Introduce la fecha de nacimiento (ddmmaaaa)");
			try {
				int fecha = Integer.parseInt(sc.nextLine());
				System.out.println("Introduce su codigo");
				int codigo = Integer.parseInt(sc.nextLine());
				
				String fechaStr=fecha+"";
	            SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyy");
	            formatoFecha.setLenient(false);
	            formatoFecha.parse(fechaStr);
	            
				alumno = new alumno(nombre, fecha, codigo);
				errorDatos = false;
			} catch (Exception e) {
				System.out.println("Error, tanto la fecha como el codigo deben ser numeros positivos, en formato valido");
				errorDatos = true;
			}
		} while (errorDatos);
		return alumno;
	}

	public ArrayList<alumno> darAltaAlumno(ArrayList<alumno> alumnos) {
		alumno a = crearAlumno(alumnos);
		alumnos.add(a);
		System.out.println("Se ha añadido\n");
		return alumnos;
	}

	public void consultarAlumnos(ArrayList<alumno> alumnos) throws IOException {
		for (int i = 0; i < alumnos.size(); i++) {
			String fechaStr = alumnos.get(i).fechaNacimiento + "";
			if (fechaStr.length() == 8) {
				fechaStr = fechaStr.substring(0, 2) + "/" + fechaStr.substring(2, 4) + "/" + fechaStr.substring(4);
			}
			System.out.printf("%2d Nombre: %s  Fecha nacimiento: %s  Codigo: %d\n", (i + 1), alumnos.get(i).nombre,
					fechaStr, alumnos.get(i).codigo);
		}
		System.out.println();
	}

	public int pedirIndice(ArrayList<alumno> alumnos) {
		boolean bandera = false;
		int indice = -1;
		do {
			System.out.println("Introduce el indice a modificar, empieza en 1, hay " + alumnos.size());
			try {
				indice = Integer.parseInt(sc.nextLine());
				indice--;
				bandera = false;
				if (indice >= 0 && indice < alumnos.size()) {
					return indice;
				}else {
					System.out.println("El alumno no existe");
				}
			} catch (Exception e) {
				System.out.println("Debes introducir un numero valido");
				bandera = true;
			}
		} while (bandera);
		return -1;
	}

	public ArrayList<alumno> modificarAlumnos(ArrayList<alumno> alumnos) {
		alumno a = crearAlumno(alumnos);
		alumnos.set(pedirIndice(alumnos), a);
		System.out.println("Se ha modificado\n");
		return alumnos;
	}


	public ArrayList<alumno> borrarAlumnos(ArrayList<alumno> alumnos) {
		alumnos.remove(pedirIndice(alumnos));
		System.out.println("Se ha borrado\n");
		return alumnos;
	}

	public void guardarDatos(File f, ArrayList<alumno> alumnos) throws IOException {
		FileOutputStream fos = null;
		DataOutputStream fichOut = null;
		try {
			fos = new FileOutputStream(f);
			fichOut = new DataOutputStream(fos);
			for (alumno alum : alumnos) {
				try {
					fichOut.writeUTF(alum.nombre);
					fichOut.writeInt(alum.fechaNacimiento);
					fichOut.writeInt(alum.codigo);
				} catch (IOException e) {
					System.out.println("Error guardando: " + e.getLocalizedMessage());
				}
			}
		} finally {
			if (fichOut != null)
				fichOut.close();
			if (fos != null)
				fos.close();
		}
	}

	public ArrayList<alumno> abrirArchivo(File f) throws IOException {
		ArrayList<alumno> alumnos = new ArrayList<>();
		FileInputStream fin = null;
		DataInputStream fichIn = null;
		try {
			fin = new FileInputStream(f);
			fichIn = new DataInputStream(fin);
			try {
				while (true) {
					alumno a = new alumno(fichIn.readUTF(), fichIn.readInt(), fichIn.readInt());
					alumnos.add(a);
				}
			} catch (EOFException e) {
//				System.out.println("Fin de fichero");
			}
		} finally {
			if (fichIn != null)
				fichIn.close();
			if (fin != null)
				fin.close();
		}

		return alumnos;

	}
}
