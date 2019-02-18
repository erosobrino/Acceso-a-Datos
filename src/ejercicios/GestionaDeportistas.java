package ejercicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/deportistas")
public class GestionaDeportistas {
	private Connection conexion;
	private PreparedStatement ps = null;

	ArrayList<Deportista> deportistas = new ArrayList<>();
	String bd = "ad_tema6";
	String servidor = "localhost";
	String usuario = "root";
	String password = "";

	//Fallan xml
	
	// 11
	@GET
	@Path("/deportes")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<String> getDeportes() throws SQLException {
		ArrayList<String> deportes = new ArrayList<>();
		String query = "SELECT distinct deporte FROM deportistas";
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			deportes.add(rs.getString("deporte"));
		}
		return deportes;
	}

	// 10
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

	// 9
	@GET
	@Path("/deporte/{nombre}/activos")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<Deportista> getDeporteActivos(@PathParam("nombre") String nombreDeporte) {
		String query = "SELECT * from deportistas where deporte='" + nombreDeporte + "' and activo=1;";
		realizaConsultaModificaDeportistas(query);
		return deportistas;
	}

	// 8
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

	// 7
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

	private void realizaConsultaModificaDeportistas(String query) {
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
			}
		} catch (SQLException e) {
		}
		cerrarConexion();
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