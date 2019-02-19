package ejercicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;

@Path("/deportistas")
public class GestionaDeportistas {
	private Connection conexion;
	private PreparedStatement ps = null;

	ArrayList<Deportista> deportistas = new ArrayList<>();
	String bd = "ad_tema6";
	String servidor = "localhost";
	String usuario = "root";
	String password = "";

	// 17
	@DELETE
	@Path("/del/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ArrayList<Deportista> eliminaDeportista(@PathParam("id") int id) {
		try {
			String query = "SELECT * FROM deportistas WHERE id=" + id + ";";
			realizaConsultaModificaDeportistas(query);
			query = "DELETE FROM deportistas WHERE id=" + id + ";";
			abrirConexion(bd, servidor, usuario, password);
			Statement stmt = conexion.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			return null;
		}
		cerrarConexion();
		return deportistas;
	}

	// 16
	@PUT
	@Path("/")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response actualizaDeportista(Deportista deportista) {
		String query = "UPDATE deportistas SET nombre='" + deportista.nombre + "', activo=" + deportista.activo
				+ ",genero='" + deportista.genero + "', deporte='" + deportista.deporte + "' where id=" + deportista.id
				+ ";";
		return realizaConsultaModificaDeportistas(query);
	}

	// 15
	@POST
	@Path("/adds")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response introduceVariosDeportistas(ArrayList<Deportista> deportistasNuevos) {
		boolean error = false;
		for (Deportista deportista : deportistasNuevos) {
			String query = "INSERT into deportistas (nombre,activo,genero,deporte) values" + "('" + deportista.nombre
					+ "'," + deportista.activo + "," + "'" + deportista.genero + "','" + deportista.deporte + "');";
			if (realizaConsultaModificaDeportistas(query) != Response.status(Status.OK).entity("Correcto")
					.type(MediaType.TEXT_PLAIN).build() && error == false)
				error = true;
		}
		if (error)
			return Response.status(Status.OK).entity("Correcto").type(MediaType.TEXT_PLAIN).build();
		else
			return Response.status(Status.OK).entity("Error en alguna insercion").type(MediaType.TEXT_PLAIN).build();
	}

	// 14
	@POST
	@Path("/")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addDeportistaForm(@FormParam("nombre") String nombre, @FormParam("genero") String genero,
			@FormParam("activo") boolean activo, @FormParam("deporte") String deporte, @FormParam("id") int id) {
		try {
			abrirConexion(bd, servidor, usuario, password);
			Statement stmt = conexion.createStatement();
			String query = "INSERT into deportistas (nombre,activo,genero,deporte) values" + "('" + nombre + "',"
					+ activo + "," + "'" + genero + "','" + deporte + "');";
			stmt.executeUpdate(query);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity("Error al añadir el deportista")
					.type(MediaType.TEXT_PLAIN).build();
		}
		cerrarConexion();
		return Response.status(Status.OK).entity("Insercion correcta").type(MediaType.TEXT_PLAIN).build();
	}

	// 13
	@POST
	@Path("/")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response addDeportista(Deportista deportista) {
		String query = "INSERT into deportistas (nombre,activo,genero,deporte) values" + "('" + deportista.nombre + "',"
				+ deportista.activo + "," + "'" + deportista.genero + "','" + deportista.deporte + "');";
		return realizaConsultaModificaDeportistas(query);
	}

	// 12
	@GET
	@Path("/deportes")
	@Produces({ MediaType.APPLICATION_JSON })
	public ArrayList<String> getDeportes() throws SQLException {
		abrirConexion(bd, servidor, usuario, password);
		ArrayList<String> deportes = new ArrayList<>();
		String query = "SELECT distinct deporte FROM deportistas";
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			deportes.add(rs.getString("deporte"));
		}
		cerrarConexion();
		return deportes;
	}

	// 11
	@GET
	@Path("/sdepor")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public int getCantidad() throws SQLException {
		abrirConexion(bd, servidor, usuario, password);
		String query = "SELECT distinct COUNT(id) FROM deportistas;";
		int cantidad = 0;
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next())
			cantidad = rs.getInt(1);
		cerrarConexion();
		return cantidad;
	}

	// 10
	@GET
	@Path("/deporte/{nombre}/activos")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<Deportista> getDeporteActivos(@PathParam("nombre") String nombreDeporte) {
		String query = "SELECT * from deportistas where deporte='" + nombreDeporte + "' and activo=1;";
		realizaConsultaModificaDeportistas(query);
		return deportistas;
	}

	// 9
	@GET
	@Path("/xg")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<ArrayList<Deportista>> getSeparadoGenero() {
		ArrayList<ArrayList<Deportista>> deportXG = new ArrayList<>();
		ArrayList<Deportista> deportH = new ArrayList<>();
		ArrayList<Deportista> deportM = new ArrayList<>();
		String query = "SELECT * from deportistas where genero='masculino'";
		abrirConexion(bd, servidor, usuario, password);
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Deportista deportista = new Deportista();
				deportista.setId(rs.getInt("id"));
				deportista.setNombre(rs.getString("nombre"));
				deportista.setActivo(rs.getBoolean("activo"));
				deportista.setGenero(rs.getString("genero"));
				deportista.setDeporte(rs.getString("deporte"));
				deportH.add(deportista);
			}
			query = "SELECT * from deportistas where genero='femenino'";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Deportista deportista = new Deportista();
				deportista.setId(rs.getInt("id"));
				deportista.setNombre(rs.getString("nombre"));
				deportista.setActivo(rs.getBoolean("activo"));
				deportista.setGenero(rs.getString("genero"));
				deportista.setDeporte(rs.getString("deporte"));
				deportM.add(deportista);
			}
		} catch (SQLException e) {
		}
		cerrarConexion();
		deportXG.add(deportH);
		deportXG.add(deportM);
		return deportXG;
	}

	// 8
	@GET
	@Path("/femeninos")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<Deportista> getFemenino() {
		String query = "SELECT * from deportistas where genero='femenino'";
		realizaConsultaModificaDeportistas(query);
		return deportistas;
	}

	// 7
	@GET
	@Path("/masculinos")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<Deportista> getMasculino() {
		String query = "SELECT * from deportistas where genero='masculino'";
		realizaConsultaModificaDeportistas(query);
		return deportistas;
	}

	// 6
	@GET
	@Path("/retirados")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<Deportista> getRetirados() {
		String query = "SELECT * from deportistas where activo=0;";
		realizaConsultaModificaDeportistas(query);
		return deportistas;
	}

	// 5
	@GET
	@Path("/activos")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ArrayList<Deportista> getActivos() {
		String query = "SELECT * from deportistas where activo=1;";
		realizaConsultaModificaDeportistas(query);
		return deportistas;
	}

	// 4
	@GET
	@Path("/deporte/{nombre}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ArrayList<Deportista> getDeporteNombre(@PathParam("nombre") String deporte) {
		String query = "SELECT * from deportistas where deporte='" + deporte + "';";
		realizaConsultaModificaDeportistas(query);
		return deportistas;
	}

	// 3
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ArrayList<Deportista> getID(@PathParam("id") int id) {
		String query = "SELECT * from deportistas where id=" + id;
		realizaConsultaModificaDeportistas(query);
		return deportistas;
	}

	// 2
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ArrayList<Deportista> getTodos() throws SQLException {
		String query = "SELECT * from deportistas;";
		realizaConsultaModificaDeportistas(query);
		return deportistas;
	}

	private Response realizaConsultaModificaDeportistas(String query) {
		abrirConexion(bd, servidor, usuario, password);
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			deportistas.clear();
			while (rs.next()) {
				Deportista deportista = new Deportista();
				deportista.setId(rs.getInt("id"));
				deportista.setNombre(rs.getString("nombre"));
				deportista.setActivo(rs.getBoolean("activo"));
				deportista.setGenero(rs.getString("genero"));
				deportista.setDeporte(rs.getString("deporte"));
				deportistas.add(deportista);
				cerrarConexion();
			}
		} catch (SQLException e) {
			cerrarConexion();
			e.printStackTrace();
			return Response.status(Status.OK).entity("Error").type(MediaType.TEXT_PLAIN).build();
		}
		return Response.status(Status.OK).entity("Correcto").type(MediaType.TEXT_PLAIN).build();
	}

	public void abrirConexion(String bd, String servidor, String usuario, String password) {
		try {
			try {
				Class.forName("org.mariadb.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String url = String.format("jdbc:mariadb://%s:3306/%s", servidor, bd);
			this.conexion = DriverManager.getConnection(url, usuario, password); // Establecemos la conexión con la
																					// BD
			if (this.conexion != null) {
			} else
				System.out.println("NO se ha conectado a la base de datos " + bd + " en " + servidor);
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getLocalizedMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("Código error: " + e.getErrorCode());
		}
	}

	public void cerrarConexion() {
		try {
			this.conexion.close();
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexión: " + e.getLocalizedMessage());
		}
	}
}