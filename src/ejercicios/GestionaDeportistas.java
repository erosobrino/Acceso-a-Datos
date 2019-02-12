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
	@Path("/e")
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

		public void abrirConexion(String bd, String servidor, String usuario, String password) {
			try {
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