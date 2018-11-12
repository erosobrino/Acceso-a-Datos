import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.net.ssl.HttpsURLConnection;

public class Principal {
	public File prediccion = new File("D:\\Ciclo\\Acceso a datos\\EjerJson\\src\\prediccion.json");

	public static void main(String[] args) {
		Jsonn j = new Jsonn();
		Principal p = new Principal();
		String ciudad = "vigo";
		JsonObject json=null;
		
		if (j.abreFuncionTiempo()) {
			json = (JsonObject) j.leeJSON(p.prediccion.getAbsolutePath());
		} else {
//			json=j.prediccionCiudad(ciudad);
//			System.out.println(json.toString());
			json = j.prediccionCoordenadas(42.24, -8.72);
//			System.out.println(json.toString());
//			json = j.nPrediccionProximasCiudad(42.24, -8.72, 3);
//			System.out.println(json.toString());
			try {
				j.escribeJSON(json);
			} catch (FileNotFoundException e) {
			}
		}

		System.out.println(j.conseguirIDPrediccion(json));
		System.out.println(j.conseguirNombreCiudad(json));
		System.out.println(j.conseguirCoordenadas(json));
	}
}

class Jsonn {
	public File prediccion = new File("D:\\Ciclo\\Acceso a datos\\EjerJson\\src\\prediccion.json");

	public String conseguirCoordenadas(JsonObject prediccion) {
		if (prediccion.containsKey("coord")) {
			JsonObject coordenadas = (JsonObject) prediccion.get("coord");
			double latitud = Double.parseDouble(coordenadas.get("lat").toString());
			double longitud = Double.parseDouble(coordenadas.get("lon").toString());
			return latitud + " " + longitud;
		}
		return "";
	}

	public String conseguirNombreCiudad(JsonObject prediccion) {
		if (prediccion.containsKey("name")) {
			return prediccion.getString("name");
		}
		return "";
	}

	public int conseguirIDPrediccion(JsonObject prediccion) {
		if (prediccion.containsKey("id")) {
			return Integer.parseInt(prediccion.get("id").toString());
		}
		return -1;
	}

	public JsonObject nPrediccionProximasCiudad(double latitud, double longitud, int cantidad) {
		JsonReader reader = null;
		JsonObject jsonO = null;
		try {
			URL url = new URL("https://api.openweathermap.org/data/2.5/find?lat=" + latitud + "&lon=" + longitud
					+ "&cnt=" + cantidad + "&APPID=8f8dccaf02657071004202f05c1fdce0");
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();
			reader = Json.createReader(is);
			jsonO = (JsonObject) reader.read();
		} catch (IOException e) {
			System.out.println("Error procesando documento Json" + e.getLocalizedMessage());
		}
		if (reader != null)
			reader.close();
		return jsonO;
	}

	public JsonObject prediccionCoordenadas(double latitud, double longitud) {
		JsonReader reader = null;
		JsonObject jsonO = null;
		try {
			URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + latitud + "&lon=" + longitud
					+ "&APPID=8f8dccaf02657071004202f05c1fdce0");
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();
			reader = Json.createReader(is);
			jsonO = (JsonObject) reader.read();
		} catch (IOException e) {
			System.out.println("Error procesando documento Json" + e.getLocalizedMessage());
		}
		if (reader != null)
			reader.close();
		return jsonO;
	}

	public JsonObject prediccionCiudad(String ciudad) {
		JsonReader reader = null;
		JsonObject jsonO = null;
		try {
			URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + ciudad
					+ "&APPID=8f8dccaf02657071004202f05c1fdce0");
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();
			reader = Json.createReader(is);
			jsonO = (JsonObject) reader.read();
		} catch (IOException e) {
			System.out.println("Error procesando documento Json" + e.getLocalizedMessage());
		}
		if (reader != null)
			reader.close();
		return jsonO;
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
			System.out.println("Error procesando documento Json" + e.getLocalizedMessage());
		}
		if (reader != null)
			reader.close();
		return jsonV;
	}

	public boolean abreFuncionTiempo() {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
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