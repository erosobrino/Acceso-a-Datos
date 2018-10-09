import java.io.File;

public class Principal {

	public static void main(String[] args) {
		String ruta="C:\\Program Files (x86)\\Mozilla Maintenance Service";
		Principal p1 = new Principal();
		System.out.println("-----------------");
		p1.muestraDir(ruta);
		System.out.println("-----------------");
		p1.muestraFich(ruta);
		System.out.println("-----------------");
		p1.muestraTodo(ruta);
		System.out.println("-----------------");
	}

	public void separaFicheroDirect(String ruta, boolean direct) {
		File f = new File(ruta);
		File[] archivos=f.listFiles();
		
		for (File archivo:archivos) {
			if (archivo.isDirectory() && direct || archivo.isFile() && !direct) {
				System.out.println(archivo.getName());
			}
		}
	}
	public void muestraDir(String ruta) {
		separaFicheroDirect(ruta, true);
	}
	
	public void muestraFich(String ruta) {
		separaFicheroDirect(ruta, false);
	}
	
	public void muestraTodo(String ruta) {
		separaFicheroDirect(ruta, true);
		separaFicheroDirect(ruta, false);
	}
}
