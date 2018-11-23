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

public class PrincipalJson {
	public File prediccion = new File("D:\\Ciclo\\Acceso a datos\\EjerJson\\src\\prediccion.json");
	//Ero Sobrino Dorado
	public static void main(String[] args) {
		Jsonn j = new Jsonn();
		PrincipalJson p = new PrincipalJson();
		JsonObject json = null;

//		if (j.abreFuncionTiempo(true)) {// true carga siempre
//			json = (JsonObject) j.leeJSON(p.prediccion.getAbsolutePath());
//		} else {
//			json = j.prediccionCiudad("o rosal");
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

//		j.preguntas(20, "hard");
//		j.eventosPorLocalidadKmCant("vigo", 25, 5);
//		JsonArray eventos = j.eventosPorLocalidadKmCant("vigo", 25, 5);
//		j.tiempoEnEventos(eventos);
//		j.informacionDetalladaEventos(eventos);
//		j.informacionDetalladaUbicacion(eventos);
//		for (JsonValue evento : (JsonArray) eventos) {
//			j.distanciaCiudadAEvento("vigo", (JsonObject) evento);
//		}

//		j.consigueDatosVariasCiudades(j.nPrediccionProximasCiudad(42.232819, -8.72264, 8));
		JsonArray jsonarr = j.eventosPorLocalidadKmCant("vigo", 100, 10);
		j.tiempoEnEventos(jsonarr);
	}
}

class Jsonn {
	public File prediccion = new File("D:\\Ciclo\\Acceso a datos\\EjerJson\\src\\prediccion.json");

	public void tiempoEnEventos(JsonArray eventos) {// Ejer 17
		for (int i = 0; i < eventos.size(); i++) {
			JsonObject evento = (JsonObject) eventos.get(i);
			if (evento.containsKey("title") && evento.containsKey("city_name")) {
				System.out.println(evento.getString("title"));
				System.out.println(evento.getString("city_name"));
				JsonObject prediccion = prediccionCiudad(evento.getString("city_name"));
				System.out.println(conseguirFechTempHumNubVelPron(prediccion) + "\n");
			}
		}
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

	public void informacionDetalladaUbicacion(JsonArray eventos) {// Ejer 15
		for (int i = 0; i < eventos.size(); i++) {
			JsonObject evento = eventos.getJsonObject(i);
			if (evento.containsKey("latitude")) {
				System.out.println(evento.get("latitude"));
			}
			if (evento.containsKey("longitude")) {
				System.out.println(evento.get("longitude"));
			}
			if (evento.containsKey("city_name")) {
				System.out.println(evento.getString("city_name"));
			}
			if (evento.containsKey("country_name")) {
				System.out.println(evento.getString("country_name"));
			}
			System.out.println();
		}
	}

	public void informacionDetalladaEventos(JsonArray eventos) {// Ejer 15
		for (int i = 0; i < eventos.size(); i++) {
			JsonObject evento = eventos.getJsonObject(i);
			if (evento.containsKey("title")) {
				System.out.println(evento.getString("title"));
			}
			if (evento.containsKey("description")) {
				if (evento.get("description").toString() == "null") {
					System.out.println("Sin descripccion");
				} else {
					System.out.println(evento.get("description").toString().substring(2,
							evento.get("description").toString().length() - 1));
				}
			}
			if (evento.containsKey("url")) {
				System.out.println(evento.getString("url"));
			}
			System.out.println();
		}
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

	public void preguntas(int cantidad, String dificultad) {// Ejer 13
		String ruta = "https://opentdb.com/api.php?amount=" + cantidad + "&category=18&difficulty=" + dificultad;
		JsonObject datos = (JsonObject) leeJSON(ruta);
		System.out.println(datos.toString());
		if (datos.containsKey("results")) {
			JsonArray ArrayListPreguntas = datos.getJsonArray("results");
			for (int i = 0; i < ArrayListPreguntas.size(); i++) {
				JsonObject pregunta = (JsonObject) ArrayListPreguntas.get(i);
				if (pregunta.containsKey("question")) {
					System.out.println("Pregunta: " + pregunta.getString("question"));
				}
				if (pregunta.containsKey("correct_answer")) {
					System.out.println(pregunta.getString("correct_answer") + "*");
				}
				if (pregunta.containsKey("incorrect_answers")) {
					JsonArray ArrayListIncorrectas = pregunta.getJsonArray("incorrect_answers");
					for (int j = 0; j < ArrayListIncorrectas.size(); j++) {
						System.out.println(ArrayListIncorrectas.getString(j));
					}
				}
				System.out.println();
			}
		}

	}

	public void consigueDatosVariasCiudades(JsonObject predicciones) {// Ejer 9
		int cantidad = 0;
		if (predicciones.containsKey("count")) {
			cantidad = predicciones.getInt("count");
		}
		if (predicciones.containsKey("list")) {
			JsonArray ArrayListPredicciones = predicciones.getJsonArray("list");
			for (int i = 0; i < cantidad; i++) {
				JsonObject prediccion = (JsonObject) ArrayListPredicciones.get(i);
				System.out.println(conseguirNombreCiudad(prediccion));
				System.out.println(conseguirFechTempHumNubVelPron(prediccion));
				System.out.println();
			}
		}
	}

	public String conseguirFechTempHumNubVelPron(JsonObject prediccion) {// Ejer 8
		String cadena = "";
		if (prediccion != null) {
			if (prediccion.containsKey("dt")) {
				cadena += "Fecha: " + unixTimeToString(prediccion.getInt("dt"));
			}
			if (prediccion.containsKey("main")) {
				JsonObject principal = prediccion.getJsonObject("main");
				if (principal.containsKey("temp")) {
					cadena += "\nTemperatura: " + principal.get("temp");
				}
				if (principal.containsKey("humidity")) {
					cadena += "\nHumedad: " + principal.getInt("humidity");
				}
			}
			if (prediccion.containsKey("clouds")) {
				JsonObject nubes = prediccion.getJsonObject("clouds");
				if (nubes.containsKey("all")) {
					cadena += "\nProbabilidad Nubes: " + nubes.getInt("all");
				}
			}
			if (prediccion.containsKey("wind")) {
				JsonObject viento = prediccion.getJsonObject("wind");
				if (viento.containsKey("speed")) {
					cadena += "\nVelocidad Viento: " + viento.getInt("speed");
				}
			}
			if (prediccion.containsKey("weather")) {
				JsonArray tiempo = prediccion.getJsonArray("weather");
				if (((JsonObject) (tiempo.get(0))).containsKey("main")) {
					cadena += "\nPronostico: " + ((JsonObject) (tiempo.get(0))).getString("main");
				}
			}
		}
		return cadena;
	}

	public String conseguirCoordenadas(JsonObject prediccion) {// Ejer 7
		if (prediccion.containsKey("coord")) {
			JsonObject coordenadas = (JsonObject) prediccion.get("coord");
			double latitud = Double.parseDouble(coordenadas.get("lat").toString());
			double longitud = Double.parseDouble(coordenadas.get("lon").toString());
			return latitud + " " + longitud;
		}
		return "";
	}

	public String conseguirNombreCiudad(JsonObject prediccion) {// Ejer 6
		if (prediccion.containsKey("name")) {
			return prediccion.getString("name");
		}
		return "";
	}

	public int conseguirIDPrediccion(JsonObject prediccion) {// Ejer 5
		if (prediccion.containsKey("id")) {
			return Integer.parseInt(prediccion.get("id").toString());
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