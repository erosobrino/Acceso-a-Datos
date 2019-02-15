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

	ArrayList<Deportista> deportistas = new ArrayList<>();
	String bd = "ad_tema6";
	String servidor = "localhost";
	String usuario = "root";
	String password = "";

	@GET
	@Path("/activos2")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ArrayList<Deportista> getActivos2() {
		JDBC database = new JDBC();
		database.abrirConexion(bd, servidor, usuario, password);
		try {
			database.consultarActivos();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		database.cerrarConexion();
		return deportistas;
	}

	// 5
	@GET
	@Path("/activos")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ArrayList<Deportista> getActivos() {
		ArrayList<Deportista> deportistasActivos = new ArrayList<>();
		JDBC database = new JDBC();
		database.abrirConexion(bd, servidor, usuario, password);
		try {
			database.consultarTodos();
			for (Deportista deportista : deportistas) {
				if (deportista.isActivo()) {
					deportistasActivos.add(deportista);
				}
			}
		} catch (SQLException e) {
		}
		database.cerrarConexion();
		return deportistasActivos;
	}

	// 4
	@GET
	@Path("/deporte/{nombre}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ArrayList<Deportista> getDeporteNombre(@PathParam("nombre") String deporte) {
		JDBC database = new JDBC();
		ArrayList<Deportista> deportistasDeporte = new ArrayList<>();
		database.abrirConexion(bd, servidor, usuario, password);
		try {
			database.consultarTodos();
			for (Deportista deportista : deportistas) {
				if (deportista.getDeporte().toUpperCase().equals(deporte.toUpperCase())) {
					deportistasDeporte.add(deportista);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		database.cerrarConexion();
		return deportistasDeporte;
	}

	// 3
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Deportista getID(@PathParam("id") int id) {
		JDBC database = new JDBC();
		Deportista deport = null;
		database.abrirConexion(bd, servidor, usuario, password);
		try {
			database.consultarTodos();
			for (Deportista deportista : deportistas) {
				if (deportista.getId() == id) {
					deport = deportista;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		database.cerrarConexion();
		return deport;
	}

	// 2
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ArrayList<Deportista> getTodos() {
		JDBC database = new JDBC();
		database.abrirConexion(bd, servidor, usuario, password);
		try {
			database.consultarTodos();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		database.cerrarConexion();
		return deportistas;
	}

	class JDBC {
		private Connection conexion;
		private PreparedStatement ps = null;

		private void consultarTodos() throws SQLException {
			String query = "SELECT * from deportistas;";
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
		}

		private void consultarActivos() throws SQLException {
			String query = "SELECT * from deportistas where activo=1;";
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
}