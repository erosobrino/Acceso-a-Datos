import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.stream.JsonParser;
import javax.net.ssl.HttpsURLConnection;

public class PrincipalJsonEro {
	public File prediccion = new File("D:\\Ciclo\\Acceso a datos\\EjerJson\\src\\prediccion.json");

	// Ero Sobrino Dorado
	public static void main(String[] args) {
		Jsonn j = new Jsonn();
		PrincipalJsonEro p = new PrincipalJsonEro();
		JsonObject json = null;

//		if (j.abreFuncionTiempo(true)) {// true carga siempre
//			json = (JsonObject) j.leeJSON(p.prediccion.getAbsolutePath());
//		} else {
//			json = j.prediccionCiudad("o rosal");
//			System.out.println(j.conseguirIDPrediccion(json));
//			System.out.println(j.conseguirNombreCiudad(json));
//			json = j.prediccionCoordenadas(42.24, -8.72);
//			json = j.nPrediccionProximasCiudad(42.24, -8.72, 3);
//			try {
//				j.escribeJSON(json);
//			} catch (FileNotFoundException e) {
//			}
//		}		
//		System.out.println(json.toString());
//		j.consigueDatosVariasCiudades(json);
//		System.out.println(j.conseguirIDPrediccion(json));
//		System.out.println(j.conseguirNombreCiudad(json));
//		System.out.println(j.conseguirCoordenadas(json));
//		System.out.println(j.conseguirFechTempHumNubVelPron(json));
//
//		j.preguntas(20, "hard");
//		j.eventosPorLocalidadKmCant("vigo", 25, 5);
//		JsonArray eventos = j.eventosPorLocalidadKmCant("vigo", 25, 5);
//		j.tiempoEnEventos(eventos);
//		j.informacionDetalladaEventos(eventos);
//		j.informacionDetalladaUbicacion(eventos);
//		for (JsonValue evento : (JsonArray) eventos) {
//			j.distanciaCiudadAEvento("vigo", (JsonObject) evento);
//		}
//
//		j.consigueDatosVariasCiudades(j.nPrediccionProximasCiudad(42.232819, -8.72264, 8));
//		JsonArray jsonarr = j.eventosPorLocalidadKmCant("vigo", 100, 10);
//		j.tiempoEnEventos(jsonarr);
	}
}

class Jsonn {
	public File prediccion = new File("D:\\Ciclo\\Acceso a datos\\EjerJson\\src\\prediccion.json");

	public ArrayList<TiempoEvento> tiempoEnEventos(JsonArray eventos) {// Ejer 17
		ArrayList<TiempoEvento> tiempoEventos = new ArrayList<>();
		for (int i = 0; i < eventos.size(); i++) {
			JsonObject evento = eventos.getJsonObject(i);
			TiempoEvento tmEvento = new TiempoEvento();
			if (evento.containsKey("title") && evento.containsKey("city_name")) {
				tmEvento.setNombreEvento(evento.getString("title"));
				tmEvento.setCiudad(evento.getString("city_name"));
				JsonObject prediccion = prediccionCiudad(evento.getString("city_name"));
				tmEvento.setPrediccion(conseguirFechTempHumNubVelPron(prediccion));
			}
			tiempoEventos.add(tmEvento);
		}
		return tiempoEventos;
	}

	public String distanciaCiudadAEvento(String ciudad, JsonObject evento) {// Ejer 16
		String destino;
		if (evento.containsKey("city_name")) {
			destino = evento.getString("city_name");
			String ruta = "{\"destination_addresses\":[\"Pontevedra, España\"],\"origin_addresses\":[\"Vigo, Pontevedra, España\"],\"rows\":[{\"elements\":[{\"distance\":{\"text\":\"27,9 km\",\"value\":27871},\"duration\":{\"text\":\"27 min\",\"value\":1625},\"status\":\"OK\"}]}],\"status\":\"OK\"}";
			JsonObject calculoRuta = Json.createReader(new StringReader(ruta)).readObject();
			if (calculoRuta.containsKey("rows")) {
				JsonArray rows = calculoRuta.getJsonArray("rows");
				JsonObject datos = rows.getJsonObject(0);
				JsonArray elements = datos.getJsonArray("elements");
				JsonObject datos2 = elements.getJsonObject(0);
				JsonObject distancia = datos2.getJsonObject("distance");
				System.out.println(distancia.getString("text"));
			}
		}
		return "";
	}

	public ArrayList<InformacionUbicacion> informacionDetalladaUbicacion(JsonArray eventos) {// Ejer 15
		ArrayList<InformacionUbicacion> info = new ArrayList<>();
		for (int i = 0; i < eventos.size(); i++) {
			InformacionUbicacion informacion = new InformacionUbicacion();
			JsonObject evento = eventos.getJsonObject(i);
			if (evento.containsKey("latitude")) {
				informacion.setLatitud(evento.getString("latitude"));
			}
			if (evento.containsKey("longitude")) {
				informacion.setLongitud(evento.getString("longitude"));
			}
			if (evento.containsKey("city_name")) {
				informacion.setCiudad(evento.getString("city_name"));
			}
			if (evento.containsKey("country_name")) {
				informacion.setPais(evento.getString("country_name"));
			}
			info.add(informacion);
		}
		return info;
	}

	public ArrayList<InformacionEvento> informacionDetalladaEventos(JsonArray eventos) {// Ejer 15
		ArrayList<InformacionEvento> info = new ArrayList<>();
		for (int i = 0; i < eventos.size(); i++) {
			InformacionEvento informacion = new InformacionEvento();
			JsonObject evento = eventos.getJsonObject(i);
			if (evento.containsKey("title")) {
				informacion.setTitulo(evento.getString("title"));
			}
			if (evento.containsKey("description")) {
				if (evento.getString("description") == "null") {
					informacion.setDescripcion("Sin descripccion");
				} else {
					informacion.setDescripcion(evento.getString("description"));
				}
			}
			if (evento.containsKey("url")) {
				informacion.setUrl(evento.getString("url"));
			}
			info.add(informacion);
		}
		return info;
	}

	public JsonArray eventosPorLocalidadKmCant(String ciudad, int km, int cantidad) {// Ejer 14
		String ruta = "http://api.eventful.com/json/events/search?l=" + ciudad + "&units=km&within=" + km
				+ "&page_size=" + cantidad + "&app_key=c2tPtVFTrSk8xnQS";
		JsonArray eventos = null;
		JsonObject datos = (JsonObject) leeJSON(ruta);
		if (datos.containsKey("events")) {
			if (datos.getJsonObject("events").containsKey("event")) {
				eventos = datos.getJsonObject("events").getJsonArray("event");
			}
		}
		return eventos;
	}

	public ArrayList<Pregunta> preguntas(int cantidad, String dificultad) {// Ejer 13
		ArrayList<Pregunta> preguntasArrayList=new ArrayList<>();
		String ruta = "https://opentdb.com/api.php?amount=" + cantidad + "&category=18&difficulty=" + dificultad;
		JsonObject datos = (JsonObject) leeJSON(ruta);
		System.out.println(datos.toString());
		if (datos.containsKey("results")) {
			JsonArray ArrayListPreguntas = datos.getJsonArray("results");
			for (int i = 0; i < ArrayListPreguntas.size(); i++) {
				Pregunta preg=new Pregunta();
				JsonObject pregunta = ArrayListPreguntas.getJsonObject(i);
				if (pregunta.containsKey("question")) {
					preg.setPregunta(pregunta.getString("question"));
				}
				if (pregunta.containsKey("correct_answer")) {
					preg.setCorrecta(pregunta.getString("correct_answer") + "*");
				}
				if (pregunta.containsKey("incorrect_answers")) {
					ArrayList<String> incorrectas=new ArrayList<>();
					JsonArray ArrayListIncorrectas = pregunta.getJsonArray("incorrect_answers");
					for (int j = 0; j < ArrayListIncorrectas.size(); j++) {
						incorrectas.add(ArrayListIncorrectas.getString(j));
					}
					preg.setIncorrectas(incorrectas);
				}
				preguntasArrayList.add(preg);
			}
		}
		return preguntasArrayList;
	}

	public ArrayList<DatosCiudad> consigueDatosVariasCiudades(JsonObject predicciones) {// Ejer 9
		ArrayList<DatosCiudad> datosCiudades = new ArrayList<>();
		int cantidad = 0;
		if (predicciones.containsKey("count")) {
			cantidad = predicciones.getInt("count");
		}
		if (predicciones.containsKey("list")) {
			JsonArray ArrayListPredicciones = predicciones.getJsonArray("list");
			for (int i = 0; i < cantidad; i++) {
				JsonObject prediccion = ArrayListPredicciones.getJsonObject(i);
				DatosCiudad datosCiudad = new DatosCiudad();
				datosCiudad.setNombreCiudad(conseguirNombreCiudad(prediccion));
				datosCiudad.setPrediccion(conseguirFechTempHumNubVelPron(prediccion));
				datosCiudades.add(datosCiudad);
			}
		}
		return datosCiudades;
	}

	public Prediccion conseguirFechTempHumNubVelPron(JsonObject prediccion) {// Ejer 8
		Prediccion predic = new Prediccion();
		if (prediccion != null) {
			if (prediccion.containsKey("dt")) {
				predic.setFecha(unixTimeToString(prediccion.getInt("dt")));
			}
			if (prediccion.containsKey("main")) {
				JsonObject principal = prediccion.getJsonObject("main");
				if (principal.containsKey("temp")) {
					predic.setTemperatura(principal.getJsonNumber("temp").doubleValue());
				}
				if (principal.containsKey("humidity")) {
					predic.setHumedad(principal.getInt("humidity"));
				}
			}
			if (prediccion.containsKey("clouds")) {
				JsonObject nubes = prediccion.getJsonObject("clouds");
				if (nubes.containsKey("all")) {
					predic.setNubes(nubes.getInt("all"));
				}
			}
			if (prediccion.containsKey("wind")) {
				JsonObject viento = prediccion.getJsonObject("wind");
				if (viento.containsKey("speed")) {
					predic.setViento(viento.getInt("speed"));
				}
			}
			if (prediccion.containsKey("weather")) {
				JsonArray tiempo = prediccion.getJsonArray("weather");
				if ((tiempo.getJsonObject(0)).containsKey("main")) {
					predic.setPronostico((tiempo.getJsonObject(0)).getString("main"));
				}
			}
		}
		return predic;
	}

	public Coordenadas conseguirCoordenadas(JsonObject prediccion) {// Ejer 7
		Coordenadas coordenadas = new Coordenadas();
		if (prediccion.containsKey("coord")) {
			JsonObject coordenadasJson = prediccion.getJsonObject("coord");
			double latitud = coordenadasJson.getJsonNumber("lat").doubleValue();
			double longitud = coordenadasJson.getJsonNumber("lon").doubleValue();
			coordenadas.setLatitud(latitud);
			coordenadas.setLongitud(longitud);
		}
		return coordenadas;
	}

	public String conseguirNombreCiudad(JsonObject prediccion) {// Ejer 6
		if (prediccion.containsKey("name")) {
			return prediccion.getString("name");
		}
		return "";
	}

	public int conseguirIDPrediccion(JsonObject prediccion) {// Ejer 5
		if (prediccion.containsKey("id")) {
			return prediccion.getInt("id");
		}
		return -1;
	}

	public JsonObject nPrediccionProximasCiudad(double latitud, double longitud, int cantidad) {// Ejer 3
		String ruta = ("https://api.openweathermap.org/data/2.5/find?lat=" + latitud + "&lon=" + longitud + "&cnt="
				+ cantidad + "&APPID=8f8dccaf02657071004202f05c1fdce0&units=metric");
		return (JsonObject) leeJSON(ruta);
	}

	public JsonObject prediccionCoordenadas(double latitud, double longitud) {// Ejer 2
		String ruta = ("https://api.openweathermap.org/data/2.5/weather?lat=" + latitud + "&lon=" + longitud
				+ "&APPID=8f8dccaf02657071004202f05c1fdce0&units=metric");
		return (JsonObject) leeJSON(ruta);
	}

	public JsonObject prediccionCiudad(String ciudad) {// Ejer 1
		String ruta = ("https://api.openweathermap.org/data/2.5/weather?q=" + ciudad
				+ "&APPID=8f8dccaf02657071004202f05c1fdce0&units=metric");
		return (JsonObject) leeJSON(ruta);
	}

	public JsonValue leeJSON(String ruta) {
		JsonReader reader = null;
		JsonValue jsonV = null;
		try {
			if (ruta.toLowerCase().startsWith("http://")) {
				URL url = new URL(ruta);
				InputStream is = url.openStream();
				reader = Json.createReader(is);
			} else if (ruta.toLowerCase().startsWith("https://")) {
				URL url = new URL(ruta);
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				InputStream is = conn.getInputStream();
				reader = Json.createReader(is);
			} else {
				reader = Json.createReader(new FileReader(ruta));
			}
			jsonV = reader.read();
		} catch (IOException e) {
//			System.out.println("Error procesando documento Json" + e.getLocalizedMessage());
			System.out.println("No se ha encontrado informacion");
		}
		if (reader != null)
			reader.close();
		return jsonV;
	}

	public String unixTimeToString(long unixTime) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return Instant.ofEpochSecond(unixTime).atZone(ZoneId.of("GMT+1")).format(formatter);
	}

	public boolean abreFuncionTiempo(boolean bandera) {
		if (bandera) {
			return false;
		}
		JsonObject json = (JsonObject) leeJSON(prediccion.getAbsolutePath());
		Date dt = new Date(prediccion.lastModified());
//		System.out.println(sdf.format(dt));
//		System.out.println(sdf.format(System.currentTimeMillis()));
		long tiempo = System.currentTimeMillis() - dt.getTime();
		if (tiempo >= 300000) {
			return false;
		}
		return true;
	}

	public void escribeJSON(JsonValue json) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(prediccion);
		JsonWriter writer = Json.createWriter(pw);
		if (json.getValueType() == JsonValue.ValueType.OBJECT)
			writer.writeObject((JsonObject) json);
		else if (json.getValueType() == JsonValue.ValueType.ARRAY)
			writer.writeArray((JsonArray) json);
		else
			System.out.println("No se soporta la escritura");
		writer.close();
	}
}

class Coordenadas {
	private double latitud;
	private double longitud;

	double getLatitud() {
		return latitud;
	}

	void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	double getLongitud() {
		return longitud;
	}

	void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public Coordenadas() {
	}

	@Override
	public String toString() {
		return "Latitud: " + this.latitud + " Longitud: " + this.longitud;
	}

}

class Prediccion {
	private String fecha;
	private double temperatura;
	private int humedad;
	private int nubes;
	private int viento;
	private String pronostico;

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getViento() {
		return viento;
	}

	public void setViento(int viento) {
		this.viento = viento;
	}

	public String getPronostico() {
		return pronostico;
	}

	public void setPronostico(String pronostico) {
		this.pronostico = pronostico;
	}

	public double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}

	public int getHumedad() {
		return humedad;
	}

	public void setHumedad(int humedad) {
		this.humedad = humedad;
	}

	public int getNubes() {
		return nubes;
	}

	public void setNubes(int nubes) {
		this.nubes = nubes;
	}

	public Prediccion() {
	}
}

class DatosCiudad {
	private String nombreCiudad;
	private Prediccion prediccion;

	public String getNombreCiudad() {
		return nombreCiudad;
	}

	public void setNombreCiudad(String nombreCiudad) {
		this.nombreCiudad = nombreCiudad;
	}

	public Prediccion getPrediccion() {
		return prediccion;
	}

	public void setPrediccion(Prediccion prediccion) {
		this.prediccion = prediccion;
	}

	public DatosCiudad() {

	}
}

class Pregunta {
	private String pregunta;
	private String correcta;
	private ArrayList<String> incorrectas;

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getCorrecta() {
		return correcta;
	}

	public void setCorrecta(String correcta) {
		this.correcta = correcta;
	}

	public ArrayList<String> getIncorrectas() {
		return incorrectas;
	}

	public void setIncorrectas(ArrayList<String> incorrectas) {
		this.incorrectas = incorrectas;
	}

	public Pregunta() {

	}
}

class TiempoEvento {
	private String nombreEvento;
	private String ciudad;
	private Prediccion prediccion;

	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public Prediccion getPrediccion() {
		return prediccion;
	}

	public void setPrediccion(Prediccion prediccion) {
		this.prediccion = prediccion;
	}

	public TiempoEvento() {
	}
}

class InformacionUbicacion {
	private String latitud;
	private String longitud;
	private String ciudad;
	private String pais;

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public InformacionUbicacion() {
	}
}

class InformacionEvento {
	private String titulo;
	private String descripcion;
	private String url;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public InformacionEvento() {
	}
}