import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
//Validado
public class Principal4 {

	long startTime;
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
//		File f = new File("D:/Ciclo/Acceso a datos/Tema1/ejer3.txt");
		File f = new File("D:/dummy.txt");
		Principal4 p = new Principal4();
		try {
			p.masUsado(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Tiempo: "+(double)((System.currentTimeMillis()-startTime))/1000);
	}

	public void masUsado(File f) throws FileNotFoundException, IOException {
		HashMap<Integer, Integer> caracteres = new HashMap<>();
		try (FileReader fichIn = new FileReader(f)) {
			int i;
			while ((i = fichIn.read()) != -1) {
				if (caracteres.containsKey(i)) {
					caracteres.put(i, caracteres.get(i) + 1);
				}else {
					caracteres.putIfAbsent(i, 1);
				}
//				System.out.println((char) i);
			}
		}
//		System.out.println(caracteres);

		int masUsado = -1;
		int numVeces = 0;

//		Iterator it=caracteres.entrySet().iterator();
//		while (it.hasNext()) {
//			Map.Entry e=(Map.Entry)it.next();
//			System.out.println(e.getKey()+" "+e.getValue());
//			if ((int)e.getValue()>numVeces) {
//				numVeces=(int)e.getValue();
//				masUsado=(int)e.getKey();
//			}
//		}
		
		System.out.println("Tiempo: "+(double)((System.currentTimeMillis()-startTime))/1000);
		for (int clave : caracteres.keySet()) {
			if (caracteres.get(clave)>numVeces) {
				numVeces=caracteres.get(clave);
				masUsado=clave;
			}
		}
		
//		System.out.println();
		System.out.println("El mas usado es: "+(char)masUsado + "\nUn total de " + numVeces+" veces");
	}
}
