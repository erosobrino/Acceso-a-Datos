package Ejer1;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class Ejer1 {

	public static void main(String[] args) {
		Ejer1 ejer = new Ejer1();
		Document doc = ejer.creaArbol("D:/Ciclo/Acceso a datos/Tema2/src/peliculas.xml");

//		ejer.getDato(doc,"titulo");
//		ejer.getTituloNombreApellidoGenero(doc);
//		ejer.arbolRecursivo(doc, doc.getChildNodes(), -1);
//		ejer.peliculasNDirectores(doc, 2);
//		ejer.cantidadGenerosDistintos(doc);

		ejer.añadeAtributoaPelicula(doc, "Matrix", "puntuacion");
		NodeList peliculas=doc.getElementsByTagName("pelicula");
		for (int i = 0; i < peliculas.getLength(); i++) {
			NamedNodeMap atributos=peliculas.item(i).getAttributes();
			for (int j = 0; j < atributos.getLength(); j++) {
				System.out.println(atributos.item(j).getNodeName());
			}
		}//no funciona

	}

	private void añadeAtributoaPelicula(Document doc, String titulo, String atributo) {
		NodeList peliculas = doc.getElementsByTagName("pelicula");
		boolean existe = false;
		for (int i = 0; i < peliculas.getLength(); i++) {
			NodeList datos = peliculas.item(i).getChildNodes();
			for (int k = 0; k < datos.getLength(); k++) {
				if (datos.item(k).getNodeName() == "titulo") {
					if (datos.item(k).getFirstChild().getNodeValue().equals(titulo)) {
						if (peliculas.item(i).hasAttributes()) {
							NamedNodeMap atributos = peliculas.item(i).getAttributes();
							for (int j = 0; j < atributos.getLength(); j++) {
								if (!existe) {
									if (atributos.item(j).getNodeName().equals(atributo)) {
										existe = true;
									}
								}
							}
							if (!existe) {
								((Element) peliculas.item(i)).setAttribute("sddffg", "sddffg");
								System.out.println("affvadf");
							}
						} else {
							((Element) peliculas.item(i)).setAttribute("sddffg", "sddffg");
							System.out.println("affvadf");
						}
					}
				}//
			}
		}
	}

	public void cantidadGenerosDistintos(Document doc) {
		HashMap<String, Integer> generos = new HashMap<>();
		NodeList peliculas = doc.getElementsByTagName("pelicula");
		for (int i = 0; i < peliculas.getLength(); i++) {
			NamedNodeMap atributos = peliculas.item(i).getAttributes();
			for (int j = 0; j < atributos.getLength(); j++) {
				if (atributos.item(j).getNodeName() == "genero") {
//					System.out.println(atributos.item(j).getNodeValue());
					if (generos.containsKey(atributos.item(j).getNodeValue())) {
						generos.put(atributos.item(j).getNodeValue(),
								generos.get(atributos.item(j).getNodeValue()) + 1);
					} else {
						generos.putIfAbsent(atributos.item(j).getNodeValue(), 1);
					}
				}
			}
		}
		for (String genero : generos.keySet()) {
			System.out.println(genero + " " + generos.get(genero));
		}
	}

	public void peliculasNDirectores(Document doc, int n) {
		int cont = 0;
		String titulo = "";
		NodeList peliculas = doc.getElementsByTagName("pelicula");
		for (int i = 0; i < peliculas.getLength(); i++) {
			NodeList datos = peliculas.item(i).getChildNodes();
			for (int j = 0; j < datos.getLength(); j++) {
				if (datos.item(j).getNodeName() == "director") {
					cont++;
				}
				if (datos.item(j).getNodeName() == "titulo") {
					titulo = datos.item(j).getFirstChild().getNodeValue();
				}
			}
			if (cont >= n) {
				System.out.println(titulo);
			}
			cont = 0;
		}
	}

	public void arbolRecursivo(Node doc, NodeList padre, int pro) {
		pro++;
		for (int i = 0; i < padre.getLength(); i++) {
			for (int j = 0; j < pro; j++) {
				System.out.print("\t");
			}
			System.out.println(padre.item(i).getNodeType() + " " + padre.item(i).getNodeName());
			if (padre.item(i).hasChildNodes()) {
				arbolRecursivo(doc, padre.item(i).getChildNodes(), pro);
			}
		}
	}

	public void getDato(Document doc, String tag) {
		NodeList titulos = doc.getElementsByTagName(tag);
		for (int i = 0; i < titulos.getLength(); i++) {
			System.out.println(tag + ": " + titulos.item(i).getFirstChild().getNodeValue());
		}
	}

	public void getTituloNombreApellidoGenero(Document doc) {
		NodeList titulos = doc.getElementsByTagName("titulo");
		for (int i = 0; i < titulos.getLength(); i++) {
			System.out.println("Titulo: " + titulos.item(i).getFirstChild().getNodeValue());
			if (titulos.item(i).getParentNode().hasAttributes()) {
				NamedNodeMap atributos = titulos.item(i).getParentNode().getAttributes();
				for (int j = 0; j < atributos.getLength(); j++) {
					if (atributos.item(j).getNodeName() == "genero")
						System.out.println("Genero: " + atributos.item(j).getNodeValue());
				}
			}

			NodeList datos = titulos.item(i).getParentNode().getChildNodes();
			for (int j = 0; j < datos.getLength(); j++) {
				if (datos.item(j).getNodeName() == "director") {
					NodeList datosDirector = datos.item(j).getChildNodes();
					for (int k = 0; k < datosDirector.getLength(); k++) {
						if (datosDirector.item(k).getNodeName() == "nombre") {
							System.out.println("Nombre: " + datosDirector.item(k).getFirstChild().getNodeValue());
						}
						if (datosDirector.item(k).getNodeName() == "apellido") {
							System.out.println("Apellido: " + datosDirector.item(k).getFirstChild().getNodeValue());
						}
					}
				}
			}
			System.out.println();
		}
	}

	public Document creaArbol(String ruta) {
		Document doc = null;
		try {
			DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
			factoria.setIgnoringComments(true);
			DocumentBuilder builder = factoria.newDocumentBuilder();
			doc = builder.parse(ruta);
		} catch (Exception e) {
			System.out.println("Error generando el árbol DOM: " + e.getMessage());
		}
		return doc;
	}

}
