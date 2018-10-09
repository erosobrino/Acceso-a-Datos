package Tema1_Ejer10;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

class Persona implements Serializable {
	public String nombre;

	public Persona(String nombre) {
		this.nombre = nombre;
	}

	public void muestraDatos() {
		System.out.println(nombre);
	}
}

class Depart implements Serializable {
	public String nombreDepartamento;

	public Depart(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	public void muestraDatos() {
		System.out.println(nombreDepartamento);
	}
}

public class Principal10 {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		Principal10 p = new Principal10();
		ArrayList<Object> todos = new ArrayList<>();
		File file = new File("D:/Ciclo/Acceso a datos/Tema1/ejer10.dat");
		try {
			todos = p.abrirArchivo(file);
		} catch (Exception e) {
		}
		int eleccion = 0;
		do {
			System.out.println("1.Añadir Persona o Departamento");
			System.out.println("2.Consultar");
			System.out.println("3.Borrar Persona o Departamento");
			System.out.println("4.Salir");
			System.out.println("Escribe tu eleccion");
			try {
				eleccion = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
			}
			switch (eleccion) {
			case 1:
				int opcion = p.pedirNumero();
				todos.add(p.introducirDatos(opcion));
				try {
					p.guardarDatos(file, todos);
				} catch (Exception e1) {}
				System.out.println();
				break;
			case 2:
				p.muestraDatos(todos);
				System.out.println();
				break;
			case 3:
				Class<?> clase = null;
				int numClase = p.pedirNumero();
				if (numClase == 1) {
					clase = Persona.class;
				}
				if (numClase == 2) {
					clase = Depart.class;
				}
				System.out.println("Introduce su codigo");
				int codigo = -1;
				try {
					codigo = Integer.parseInt(sc.nextLine());
				} catch (Exception e) {
				}
				p.borraDatos(codigo, clase, todos);
				try {
					p.guardarDatos(file, todos);
				} catch (Exception e1) {}
				System.out.println();
				break;
			case 4:
				try {
					p.guardarDatos(file, todos);
				} catch (Exception e) {}
				break;
			case 5:

				break;
			default:
				System.out.println("Debes introducir un valor valido\n");
				break;
			}
		} while (eleccion != 4);
	}

	public int pedirNumero() {
		int num = -1;
		do {
			System.out.println("1.Persona");
			System.out.println("2.Departamento");
			System.out.println("Introduce tu eleccion");
			try {
				num = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
				System.out.println("Valor incorrecto\n");
			}
		} while (num < 1 || num > 2);
		return num;
	}

	public Object introducirDatos(int eleccion) {
		Object obj = null;
		System.out.println("Introduce el nombre");
		String nombre = sc.nextLine();
		if (eleccion == 1) {
			obj = new Persona(nombre);
		}
		if (eleccion == 2) {
			obj = new Depart(nombre);
		}
		return obj;
	}

	public void muestraDatos(ArrayList<Object> todos) {
		System.out.println("Personas:");
		int cont1 = 0, cont2 = 0;
		for (Object obj : todos) {
			if (obj.getClass() == Persona.class) {
				System.out.print((cont1 + 1) + " ");
				((Persona) obj).muestraDatos();
				cont1++;
			}
		}
		System.out.println("Hay " + cont1);
		System.out.println("\nDepartamentos:");
		for (Object obj : todos) {
			if (obj.getClass() == Depart.class) {
				System.out.print((cont2 + 1) + " ");
				((Depart) obj).muestraDatos();
				cont2++;
			}
		}
		System.out.println("Hay " + cont2);
	}

	public ArrayList<Object> borraDatos(int codigo, Class<?> clase, ArrayList<Object> todos) {
		ArrayList<Object> personas = new ArrayList<>();
		ArrayList<Object> departamentos = new ArrayList<>();
		for (int i = 0; i < todos.size(); i++) {
			if (todos.get(i).getClass() == Persona.class) {
				personas.add(todos.get(i));
			}
			if (todos.get(i).getClass() == Depart.class) {
				departamentos.add(todos.get(i));
			}
		}
		if (clase == Persona.class) {
			try {
				personas.remove(codigo - 1);
			} catch (Exception e) {
				System.out.println("No existe el codigo");
			}
		}
		if (clase == Depart.class) {
			try {
				departamentos.remove(codigo - 1);
			} catch (Exception e) {
				System.out.println("No existe el codigo");
			}
		}
		todos.removeAll(todos);
		todos.addAll(personas);
		todos.addAll(departamentos);

		return todos;
	}

	public ArrayList<Object> abrirArchivo(File f) throws IOException, ClassNotFoundException {
		FileInputStream fin = null;
		ObjectInputStream in = null;
		ArrayList<Object> todos = new ArrayList<>();
		try {
			fin = new FileInputStream(f);
			in = new ObjectInputStream(fin);
			try {
				while (true) {
					Object obj = in.readObject();
					if (obj.getClass() == Persona.class) {
						Persona perso = (Persona) obj;
						todos.add(perso);
					}
					if (obj.getClass() == Depart.class) {
						Depart depart = (Depart) obj;
						todos.add(depart);
					}
				}
			} catch (EOFException e) {
				System.err.println("Fin del dichero");
			}
		} finally {
			if (fin != null) {
				fin.close();
			}
			if (in != null) {
				in.close();
			}
		}
		return todos;
	}

	public void guardarDatos(File f, ArrayList<Object> todos) throws IOException, ClassNotFoundException {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(f);
			out = new ObjectOutputStream(fos);
			for (int i = 0; i < todos.size(); i++) {
				if (todos.get(i).getClass() == Persona.class) {
					Persona perso = (Persona) todos.get(i);
					out.writeObject(perso);
				}
				if (todos.get(i).getClass() == Depart.class) {
					Depart depart = (Depart) todos.get(i);
					out.writeObject(depart);
				}
//				out.writeObject(todos.get(i));
			}
			System.out.println("Se han guardado correctamente");
		} finally {
			if (fos != null) {
				fos.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
}
