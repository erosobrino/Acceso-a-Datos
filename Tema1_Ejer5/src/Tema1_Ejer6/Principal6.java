package Tema1_Ejer6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Principal6 {

	public static void main(String[] args) {
		Principal6 p = new Principal6();
		File f = new File("D:/Ciclo/Acceso a datos/Tema1/ejer6.txt");
		File f2 = new File("D:/Ciclo/Acceso a datos/Tema1/ejer6.txt");
		File[] files = new File[2];
		files[0] = f;
		files[1] = f2;

		int caracteres = 30;
		int lineas = 3;

		try {
			p.dividirFicheroCaract(caracteres, f);
			p.dividirFicheroLinea(lineas, f);
			p.unirFicheros(files);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dividirFicheroCaract(int caracteres, File f) throws FileNotFoundException, IOException {
		try (FileReader fichIn = new FileReader(f)) {
			int i;
			int cont = 1;
			String cadena = "";
			while ((i = fichIn.read()) != -1) {
				cadena += (char) i;
				if (cadena.length() >= caracteres) {
					try (FileWriter fichOut = new FileWriter(
							new File("D:/Ciclo/Acceso a datos/Tema1/ejer6Prueb/salidaCaract" + cont + ".txt"))) {
						fichOut.write(cadena);
//						System.out.println(cadena);
					}
					cont++;
					cadena = "";
				}
			}
			try (FileWriter fichOut = new FileWriter(
					new File("D:/Ciclo/Acceso a datos/Tema1/ejer6Prueb/salidaCaract" + cont + ".txt"))) {
				fichOut.write(cadena);
//				System.out.println(cadena);
				System.out.println("Caracteres terminado");
			}
		}
	}

	public void dividirFicheroLinea(int lineas, File f) throws IOException {
		try (Scanner sc = new Scanner(f)) {
			int cont = 0;
			int archivos = 1;
			String cadena = "";
			while (sc.hasNext()) {
				cadena += sc.nextLine() + "\n";
				cont++;
//				System.out.println(cont);
				if (cont >= lineas) {
					try (FileWriter fichOut = new FileWriter(
							new File("D:/Ciclo/Acceso a datos/Tema1/ejer6Prueb/salidaLineas" + archivos + ".txt"))) {
						fichOut.write(cadena);
//						System.out.println(cadena);
						cadena = "";
						cont = 0;
						archivos++;
					}
				}
			}
			if (cont > 0) {
				try (FileWriter fichOut = new FileWriter(
						new File("D:/Ciclo/Acceso a datos/Tema1/ejer6Prueb/salidaLineas" + archivos + ".txt"))) {
					fichOut.write(cadena);
				}
			}
			System.out.println("Filas terminado");
		}
	}

	public void unirFicheros(File[] files) throws IOException {
		try (FileWriter fichOut = new FileWriter(
				new File("D:/Ciclo/Acceso a datos/Tema1/ejer6Prueb/salidaUnion.txt"))) {
			for (int i = 0; i < files.length; i++) {
				try (FileReader fichIn = new FileReader(files[i])) {
					int j;
					while ((j = fichIn.read()) != -1) {
						fichOut.write((char) j);
					}
				}
				fichOut.write("\n");
			}
		}
		System.out.println("Union terminado");
	}
}
