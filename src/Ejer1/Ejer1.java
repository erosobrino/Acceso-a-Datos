package Ejer1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

public class Ejer1 {

	public static void main(String[] args) {
		Ejer1 ejer = new Ejer1();
		Document doc = ejer.creaArbol("D:/Ciclo/Acceso a datos/Tema2/src/peliculas.xml");

//		ejer.getDato(doc,"titulo");//1
//		ejer.getDato(doc, "titulo");//2
//		ejer.getTituloNombreApellidoGenero(doc);//3
//		ejer.arbolRecursivo(doc, doc.getChildNodes(), -1);//4
//		ejer.peliculasNDirectores(doc, 1);//5
//		System.out.println("Hay "+ejer.cantidadGenerosDistintos(doc)+" generos distintos");//6

//		ejer.añadeEliminaAtributoaPelicula(doc, "Dune", "Edad", true);//7
//		muestraTituloAtributos(doc);
//		ejer.añadeEliminaAtributoaPelicula(doc, "Dune", "Edad", false);
//		muestraTituloAtributos(doc);

//		doc = ejer.añadeDom(doc, "Depredador", "1987", "John", "Tiernan", "Accion", "en");//8
//		muestraTituloAtributos(doc);
//		guarda(ejer, doc,"salida");

//		ejer.modificarDom(doc, "nombre", "Larry", "Lana");//9
//		guarda(ejer, doc,"salida");

//		doc=ejer.añadirDirector(doc,"Dune", "Alfredo","Landa");//10
//		guarda(ejer, doc,"salida");

//		doc = ejer.borraElemntoPadre(doc, "Dune", "titulo", "pelicula");// 11
//		guarda(ejer, doc,"salida");

		Document compañia=null;
		guarda(ejer, compañia, "compañia");
		compañia=ejer.inicializaCompañia(compañia);
		compañia=ejer.añadeEmpleado(compañia, "1", "ero", "sobrino", "ero", "1");
		guarda(ejer, compañia, "compañia");
	}
	
	public Document inicializaCompañia(Document doc) {
		Element compañia=doc.createElement("compañia");
		return doc;
	}//Falla inicializa y añade
	
	public Document añadeEmpleado(Document doc,String id, String nombre, String apellidos, String apodo, String salario) {
		Element nodoEmpleado=doc.createElement("empleado");
		nodoEmpleado.setAttribute("id", id);
		nodoEmpleado.appendChild(doc.createTextNode("\n"));
		Element nodoNombre=doc.createElement("nombre");
		Text textNombre=doc.createTextNode(nombre);
		nodoNombre.appendChild(textNombre);
		Element nodoApellidos=doc.createElement("apellidos");
		Text textApellidos=doc.createTextNode(apellidos);
		nodoApellidos.appendChild(textApellidos);
		Element nodoApodo=doc.createElement("apodo");
		Text textApodo=doc.createTextNode(apodo);
		nodoApodo.appendChild(textApodo);
		Element nodoSalario=doc.createElement("salario");
		Text textSalario=doc.createTextNode(salario);
		nodoSalario.appendChild(textSalario);
		nodoEmpleado.appendChild(nodoNombre);
		nodoEmpleado.appendChild(doc.createTextNode("\n"));
		nodoEmpleado.appendChild(nodoApellidos);
		nodoEmpleado.appendChild(doc.createTextNode("\n"));
		nodoEmpleado.appendChild(nodoApodo);
		nodoEmpleado.appendChild(doc.createTextNode("\n"));
		nodoEmpleado.appendChild(nodoSalario);
		nodoEmpleado.appendChild(doc.createTextNode("\n"));
		
		doc.getFirstChild().appendChild(nodoEmpleado);
		return doc;
	}

	public Document borraElemntoPadre(Document doc, String nombre, String tipoNombre, String tipoPadre) {
		NodeList datos = doc.getElementsByTagName(tipoNombre);
		for (int i = 0; i < datos.getLength(); i++) {
			if (datos.item(i).getFirstChild().getNodeValue().equals(nombre)) {
				datos.item(i).getParentNode().getParentNode().removeChild(datos.item(i).getParentNode());
			}
		}
		return doc;
	}

	public Document añadirDirector(Document doc, String titulo, String nombre, String apellido) {
		Element nodoDirector = doc.createElement("director");
		nodoDirector.appendChild(doc.createTextNode("\n"));
		Element nodoNombre = doc.createElement("nombre");
		Text textNodoNombre = doc.createTextNode(nombre);
		nodoNombre.appendChild(textNodoNombre);
		Element nodoApellido = doc.createElement("apellido");
		Text textNodoApellido = doc.createTextNode(apellido);
		nodoApellido.appendChild(textNodoApellido);
		nodoDirector.appendChild(nodoNombre);
		nodoDirector.appendChild(doc.createTextNode("\n"));
		nodoDirector.appendChild(nodoApellido);
		nodoDirector.appendChild(doc.createTextNode("\n"));
		NodeList peliculas = doc.getElementsByTagName("pelicula");
		for (int i = 0; i < peliculas.getLength(); i++) {
			if (peliculas.item(i).getChildNodes().item(1).getFirstChild().getNodeValue().equals(titulo)) {
				peliculas.item(i).appendChild(nodoDirector);
			}
		}

		return doc;
	}

	public void modificarDom(Document doc, String nodoModificar, String textoAntiguo, String textoNuevo) {
		NodeList nombres = doc.getElementsByTagName(nodoModificar);
		for (int i = 0; i < nombres.getLength(); i++) {
			if (nombres.item(i).getFirstChild().getNodeValue().equals(textoAntiguo)) {
				nombres.item(i).getFirstChild().setNodeValue(textoNuevo);
			}
		}
	}

	public void guardarDom(Document doc, String tituloSalida) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, ClassCastException, FileNotFoundException {
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		DOMImplementationLS ls = (DOMImplementationLS) registry.getDOMImplementation("XML 3.0 LS 3.0");
		LSOutput output = ls.createLSOutput();
		output.setEncoding("UTF-8");
		output.setByteStream(new FileOutputStream(tituloSalida));
		LSSerializer serializer = ls.createLSSerializer();
		serializer.setNewLine("\r\n");
		serializer.getDomConfig().setParameter("format-pretty-print", true);
		serializer.write(doc, output);
	}

	public Document añadeDom(Document doc, String titulo, String año, String nombre, String apellido, String genero,
			String idioma) {
		try {
			Element nodoPelicula = doc.createElement("pelicula");
			nodoPelicula.setAttribute("año", año);
			nodoPelicula.setAttribute("genero", genero);
			nodoPelicula.setAttribute("idioma", idioma);
			nodoPelicula.appendChild(doc.createTextNode("\n"));

			Element nodoTitulo = doc.createElement("titulo");
			Text textNodoTitulo = doc.createTextNode(titulo);
			nodoTitulo.appendChild(textNodoTitulo);
			nodoPelicula.appendChild(nodoTitulo);
			nodoPelicula.appendChild(doc.createTextNode("\n"));

			Element nodoDirector = doc.createElement("director");
			nodoDirector.appendChild(doc.createTextNode("\n"));

			Element nodoNombre = doc.createElement("nombre");
			Text textNodoNombre = doc.createTextNode(nombre);
			nodoNombre.appendChild(textNodoNombre);
			nodoDirector.appendChild(nodoNombre);
			nodoDirector.appendChild(doc.createTextNode("\n"));

			Element nodoApellido = doc.createElement("apellido");
			Text textNodoApellido = doc.createTextNode(apellido);
			nodoApellido.appendChild(textNodoApellido);
			nodoDirector.appendChild(nodoApellido);
			nodoDirector.appendChild(doc.createTextNode("\n"));

			nodoPelicula.appendChild(nodoDirector);
			Node raiz = doc.getFirstChild();
			raiz.appendChild(nodoPelicula);
			System.out.println(raiz.getNodeName());
		} catch (DOMException e) {
			System.out.println("Error al crear Dom" + e.getMessage());
		}
		return doc;
	}

	public static void muestraTituloAtributos(Document doc) {
		NodeList peliculas = doc.getElementsByTagName("pelicula");
		for (int i = 0; i < peliculas.getLength(); i++) {
			System.out.println(peliculas.item(i).getChildNodes().item(1).getFirstChild().getNodeValue());
			NamedNodeMap atributos = peliculas.item(i).getAttributes();
			for (int j = 0; j < atributos.getLength(); j++) {
				System.out.println("\t" + atributos.item(j).getNodeName());
			}
			System.out.println();
		}
	}

	private void añadeEliminaAtributoaPelicula(Document doc, String titulo, String atributo, boolean añadir) {
		NodeList peliculas = doc.getElementsByTagName("pelicula");
		boolean existe = false;
		for (int i = 0; i < peliculas.getLength(); i++) {
			NodeList datos = peliculas.item(i).getChildNodes();
			for (int k = 0; k < datos.getLength(); k++) {
				if (datos.item(k).getNodeName() == "titulo") {
					if (datos.item(k).getFirstChild().getNodeValue().equals(titulo)) {
						if (peliculas.item(i).hasAttributes()) {
							NamedNodeMap atributos = peliculas.item(i).getAttributes();
							if (añadir) {
								for (int j = 0; j < atributos.getLength(); j++) {
									if (!existe) {
										if (atributos.item(j).getNodeName().equals(atributo)) {
											existe = true;
										}
									}
								}
								if (!existe) {
									((Element) peliculas.item(i)).setAttribute(atributo, "");
								}
							} else {
								((Element) peliculas.item(i)).removeAttribute(atributo);
							}
						} else {
							if (añadir) {
								((Element) peliculas.item(i)).setAttribute(atributo, "");
							}
						}
					}
				}
			}
		}
	}

	public int cantidadGenerosDistintos(Document doc) {
		HashMap<String, Integer> generos = new HashMap<>();
		NodeList peliculas = doc.getElementsByTagName("pelicula");
		for (int i = 0; i < peliculas.getLength(); i++) {
			NamedNodeMap atributos = peliculas.item(i).getAttributes();
			for (int j = 0; j < atributos.getLength(); j++) {
				if (atributos.item(j).getNodeName().equals("genero")) {
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
		return generos.size();
	}

	// Corregido
	public void peliculasNDirectores(Document doc, int n) {
		int cont = 0;
		String titulo = "";
		NodeList peliculas = doc.getElementsByTagName("pelicula");
		for (int i = 0; i < peliculas.getLength(); i++) {
			titulo = "";
			NodeList datos = peliculas.item(i).getChildNodes();
			for (int j = 0; j < datos.getLength(); j++) {
				if (datos.item(j).getNodeName().equals("director")) {
					cont++;
				}
				if (datos.item(j).getNodeName().equals("titulo")) {
					titulo = datos.item(j).getFirstChild().getNodeValue();
				}
			}
			if (cont >= n) {
				System.out.println(titulo);
			}
			cont = 0;
		}
	}

	// Corregido
	public void arbolRecursivo(NodeList padre, int pro) {
		pro++;
		for (int i = 0; i < padre.getLength(); i++) {
			for (int j = 0; j < pro; j++) {
				System.out.print("\t");
			}
			System.out.println(padre.item(i).getNodeType() + " " + padre.item(i).getNodeName());
			if (padre.item(i).hasChildNodes()) {
				arbolRecursivo(padre.item(i).getChildNodes(), pro);
			}
		}
	}

	// Corregido
	public void getDato(Document doc, String tag) {
		NodeList titulos = doc.getElementsByTagName(tag);
		for (int i = 0; i < titulos.getLength(); i++) {
			System.out.println(tag + ": " + titulos.item(i).getFirstChild().getNodeValue());
		}
	}

	// Corregido
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

	// Corregido
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

	public static void guarda(Ejer1 ejer, Document doc, String nombre) {
		try {
			ejer.guardarDom(doc, "D:\\Ciclo\\Acceso a datos\\Tema2\\src\\" + nombre + ".xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
