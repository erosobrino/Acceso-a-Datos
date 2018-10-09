import java.io.File;

public class Principal2 {

	public static void main(String[] args) {
		String ruta = "D:/Ciclo";
		Principal2 p1 = new Principal2();
		p1.comandoFind(ruta);
	}

	public void comandoFind(String ruta) {
		File f = new File(ruta);
		if (f.isDirectory()) {
			File[] archivos = f.listFiles();
			for (File archivo : archivos) {
				if (archivo.isDirectory()) {
					System.out.println(archivo.getName());
					comandoFind(archivo.getPath());
				} else {
					System.out.println(archivo.getName());
				}
			}
		} else if (f.exists())
			System.out.println(f.getAbsolutePath());
	}
}
