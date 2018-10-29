import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PrincipalSax {

	public static void main(String[] args) {
		PrincipalSax p = new PrincipalSax();
		try {
			p.getSax("D:/Ciclo/Acceso a datos/Tema2/src/peliculas.xml");
		} catch (ParserConfigurationException | SAXException | IOException e) {
		}

	}

	public void getSax(String entradaXML) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		ParserSAX1 parserSax1 = new ParserSAX1();
		ParserSAX2 parserSax2 = new ParserSAX2();
		ParserSAX3 parserSax3 = new ParserSAX3(2);
		ParserSAX4 parserSax4 = new ParserSAX4();
		parser.parse(entradaXML, parserSax1);
		parser.parse(entradaXML, parserSax2);
		parser.parse(entradaXML, parserSax3);
		parser.parse(entradaXML, parserSax4);
	}
}

class ParserSAX4 extends DefaultHandler {
	boolean existe = false;
	
	ArrayList<String> generos = new ArrayList<>();

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("pelicula")) {
			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("genero")) {
					if (!generos.contains(attributes.getValue(i))) {
						generos.add(attributes.getValue(i));
					}
				}
			}
		}
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println(generos.size());
	}
}

class ParserSAX3 extends DefaultHandler {
	int cont = 0;
	String titulo;
	int cantidad;
	boolean banderaTitulo = false;
	boolean banderaPelicula = false;

	public ParserSAX3(int cant) {
		cantidad = cant;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("titulo")) {
			banderaTitulo = true;
		}
		if (qName.equals("director")) {
			cont++;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("pelicula")) {
			banderaPelicula = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (banderaTitulo) {
			titulo = new String(ch, start, length);
			banderaTitulo = false;
		}
		if (banderaPelicula) {
			if (cont >= cantidad) {
				System.out.println(titulo);
			}
			cont = 0;
			banderaPelicula = false;
		}
	}
}

class ParserSAX2 extends DefaultHandler {
	boolean bandera = false;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("titulo") || qName.equals("nombre") || qName.equals("apellido")) {
			System.out.print(qName.toUpperCase() + ": ");
			bandera = true;
		}
		if (qName.equals("pelicula")) {
			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("genero")) {
					System.out.println("Genero: " + attributes.getValue(i));
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("pelicula")) {
			System.out.println();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (bandera) {
			String texto = new String(ch, start, length);
			System.out.println(texto);
			bandera = false;
		}
	}
}

class ParserSAX1 extends DefaultHandler {
	@Override
	public void startDocument() throws SAXException {
		System.out.println("Empiezo a leer");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.print("<" + qName + ">");
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.print("</" + qName + ">");
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String titulo = new String(ch, start, length);
		System.out.print(titulo);
	}
}
