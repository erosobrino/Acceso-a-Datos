package Tema1_Ejer8;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Principal8 {

	public static void main(String[] args) {
		File fileIn = new File("D:/Ciclo/Acceso a datos/Tema1/documento.txt");
		File fileOut = new File("D:/Ciclo/Acceso a datos/Tema1/Resultado8.txt");
		Principal8 p = new Principal8();

		try {
			p.copiadoBinario(fileIn, fileOut,1);
			p.copiadoBinario(fileIn, fileOut,10);
			p.copiadoBinario(fileIn, fileOut,100);
			p.copiadoBinario(fileIn, fileOut,1000);
			p.copiadoBinario(fileIn, fileOut,10000);
			p.copiadoBinario(fileIn, fileOut,100000);
			p.copiadoBinario(fileIn, fileOut,1000000);
			p.copiadoBinario(fileIn, fileOut,10000000);
			p.copiadoBinario(fileIn, fileOut,100000000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void copiadoBinario(File fileIn, File fileOut, int cantidad) throws FileNotFoundException, IOException {
		fileOut.createNewFile();
		long tiempoInicio=System.currentTimeMillis();
		try (FileInputStream in = new FileInputStream(fileIn)) {
			try (FileOutputStream out = new FileOutputStream(fileOut)) {
				int i;
				byte [] buffer=new byte[cantidad];
				while ((i = in.read(buffer)) != -1) {
//					System.out.println((char)i);
					out.write(buffer,0,i);
				}
			}
		}
		System.out.printf("Buffer de %10d Tarda: %d\n",cantidad,(System.currentTimeMillis()-tiempoInicio));

	}

}
