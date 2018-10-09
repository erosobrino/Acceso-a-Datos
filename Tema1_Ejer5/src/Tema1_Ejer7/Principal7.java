package Tema1_Ejer7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class Principal7 {

	ArrayList<String> ordenado;

	public static void main(String[] args) {
		Principal7 p = new Principal7();
		File f = new File("D:/Ciclo/Acceso a datos/Tema1/ejer7.txt");

		try {
			p.tratamientoDeArchivos(f, 'n');
			p.tratamientoDeArchivos(f, 'A');
			p.tratamientoDeArchivos(f, 'D');
			p.tratamientoDeArchivos(f, 'a');
			p.tratamientoDeArchivos(f, 'd');
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<String> cargarLineas(File f) throws FileNotFoundException {
		ArrayList<String> ordenado = new ArrayList<>();
		try (Scanner fichIn = new Scanner(f)) {
			while (fichIn.hasNextLine()) {
				ordenado.add(fichIn.nextLine());
			}
		}
		return ordenado;
	}
	
	public void tratamientoDeArchivos(File f, char opcion) throws FileNotFoundException, IOException {
		switch (opcion) {
		case 'n':
			int numLineas = 0;
			String linea;
			int cantidadPalabras = 0;
			try (Scanner fichIn = new Scanner(f)) {
				while (fichIn.hasNextLine()) {
					linea = fichIn.nextLine();
					cantidadPalabras += linea.split(" ").length;
					numLineas++;
				}
			}
			System.out.println("Tiene " + numLineas + " lineas y " + cantidadPalabras + " palabras\n");
			break;
		case 'A':
			ordenado = cargarLineas(f);
			for (String i : ordenado) {
				System.out.print(i + " ");
			}
			System.out.println();
			Collections.sort(ordenado);
			for (String i : ordenado) {
				System.out.print(i + " ");
			}
			System.out.println();
			System.out.println();
			try (FileWriter fichOut = new FileWriter(
					new File("D:/Ciclo/Acceso a datos/Tema1/ejer7Prueb/salida1.txt"))) {
				for (int i = 0; i < ordenado.size(); i++) {
					fichOut.write(ordenado.get(i) + "\n");
				}
			}
			break;
		case 'D':
			ordenado = cargarLineas(f);
			for (String i : ordenado) {
				System.out.print(i + " ");
			}
			System.out.println();
			Collections.sort(ordenado,Collections.reverseOrder());
			for (String i : ordenado) {
				System.out.print(i + " ");
			}
			System.out.println();
			System.out.println();
			try (FileWriter fichOut = new FileWriter(
					new File("D:/Ciclo/Acceso a datos/Tema1/ejer7Prueb/salida2.txt"))) {
				for (int i = 0; i < ordenado.size(); i++) {
					fichOut.write(ordenado.get(i) + "\n");
				}
			}
			break;
		case 'a':
			ordenado = cargarLineas(f);
			for (String i : ordenado) {
				System.out.print(i + " ");
			}
			System.out.println();
			Collections.sort(ordenado,String.CASE_INSENSITIVE_ORDER);
			for (String i : ordenado) {
				System.out.print(i + " ");
			}
			System.out.println();
			System.out.println();
			try (FileWriter fichOut = new FileWriter(
					new File("D:/Ciclo/Acceso a datos/Tema1/ejer7Prueb/salida3.txt"))) {
				for (int i = 0; i < ordenado.size(); i++) {
					fichOut.write(ordenado.get(i) + "\n");
				}
			}
			break;
		case 'd':
			ordenado = cargarLineas(f);
			for (String i : ordenado) {
				System.out.print(i + " ");
			}
			System.out.println();
			Collections.sort(ordenado, String.CASE_INSENSITIVE_ORDER.reversed());
			for (String i : ordenado) {
				System.out.print(i + " ");
			}
			System.out.println();
			System.out.println();
			try (FileWriter fichOut = new FileWriter(
					new File("D:/Ciclo/Acceso a datos/Tema1/ejer7Prueb/salida4.txt"))) {
				for (int i = 0; i < ordenado.size(); i++) {
					fichOut.write(ordenado.get(i) + "\n");
				}
			}
			break;
		}
	}

}
