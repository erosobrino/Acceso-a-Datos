package Tema1_Ejer11;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Principal11 {

	public static void main(String[] args) {
		File fileIn = new File("D:/Ciclo/Acceso a datos/Tema1/documento.txt");
		File fileOut = new File("D:/Ciclo/Acceso a datos/Tema1/Resultado8.txt");
		Principal11 p = new Principal11();

		try {
			p.copiadoBuffered(fileIn, fileOut);
			System.out.println();
			p.copiadoFile(fileIn, fileOut, 10);
			p.copiadoFile(fileIn, fileOut, 100);
			p.copiadoFile(fileIn, fileOut, 1000);
			p.copiadoFile(fileIn, fileOut, 10000);
			p.copiadoFile(fileIn, fileOut, 100000);
			p.copiadoFile(fileIn, fileOut, 1000000);
		} catch (IOException e) {
		}
	}

	public void copiadoBuffered(File fileIn, File fileOut) throws IOException {
		fileOut.createNewFile();
		long tiempoInicio = System.currentTimeMillis();
		try (FileInputStream fin = new FileInputStream(fileIn)) {
			try (BufferedInputStream in = new BufferedInputStream(fin)) {
				try (FileOutputStream fOut = new FileOutputStream(fileOut)) {
					try (BufferedOutputStream out = new BufferedOutputStream(fOut)) {
						int valor = 0;
						while ((valor = in.read()) != -1) {
							out.write(valor);
						}
					}
				}
			}
		}
		System.out.printf("Tarda: %d\n", (System.currentTimeMillis() - tiempoInicio));
	}

	public void copiadoFile(File fileIn, File fileOut, int cantidad) throws FileNotFoundException, IOException {
		fileOut.createNewFile();
		long tiempoInicio = System.currentTimeMillis();
		try (FileInputStream in = new FileInputStream(fileIn)) {
			try (FileOutputStream out = new FileOutputStream(fileOut)) {
				int i;
				byte[] buffer = new byte[cantidad];
				while ((i = in.read(buffer)) != -1) {
//					System.out.println((char)i);
					out.write(buffer, 0, i);
				}
			}
		}
		System.out.printf("Buffer de %10d Tarda: %d\n", cantidad, (System.currentTimeMillis() - tiempoInicio));

	}
}
