package Ejer3;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Principal3 {
//	Fallaba i en vez de contador
	public int numOcurrencias(File f, char letra) throws IOException {
		int cont=0;
		try (FileReader fichIn = new FileReader(f)) {
			int i;
			while ((i = fichIn.read()) != -1) {
//				System.out.print((char)i);
				if ((char)i==letra) {
					cont++;
				}
			}
		}
		return cont;
	}

	public static void main(String[] args) {
		File f=new File("D:/Ciclo/Acceso a datos/Tema1/ejer3.txt");
		char letra='f';
		Principal3 princ=new Principal3();
		try {
			System.out.println(princ.numOcurrencias(f, letra));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
