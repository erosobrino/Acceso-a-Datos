import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//Validado

public class Principal5 {

	public static void main(String[] args) {
		Principal5 p=new Principal5();
		File f=new File("D:/Ciclo/Acceso a datos/Tema1/ejer5.txt");
		
		String cadena="ero";
		
		try {
			p.buscarCadena(f,cadena);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buscarCadena(File f,String cadena) throws FileNotFoundException, IOException {
		try (FileReader fichIn=new FileReader(f)){
			int i;
			int posCad=0;
			int veces=0;
			int linea=1;
			while ((i=fichIn.read())!=-1) {
				if (i==cadena.charAt(posCad)) {
					posCad++;
					//System.out.println(posCad);
				}else {
					posCad=0;
				}
				if (posCad==cadena.length()) {
					System.out.println("Linea: "+linea);
					posCad=0;
					veces++;
				}
				if (i=='\n') {
					linea++;
				}
			}System.out.println("Aparece "+veces);
		}
	}
}
