package Ejer1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Principal1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JDBC database1 = new JDBC();
		try {
//			database1.consultaAlumnos("add");
			database1.consultaAlumnosPorNombre("a");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

class JDBC {
	private Connection conexion;

	public void consultaAlumnosPorNombre(String cadenaContenida) throws SQLException {
		String query = "select * from alumnos where nombre like '%" + cadenaContenida + "%';";
		abrirConexion("add", "localhost", "root", "");
		Statement stmt = this.conexion.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			System.out.println(rs.getString("nombre"));
		}
		stmt.close();
		cerrarConexion();
	}

	public void consultaAlumnos(String bd) throws SQLException {
		String query = "select * from aulas"; // Consulta a ejecutar
		abrirConexion("add", "localhost", "root", "");
		Statement stmt = this.conexion.createStatement();
		ResultSet rs = stmt.executeQuery(query); // Se ejecuta la consulta
		while (rs.next()) { // Mientras queden filas en rs (el m�todo next devuelve true) recorremos las
							// filas
			System.out.println(rs.getInt(1) + "\t" + // Se obtiene datos en funci�n del n�mero de columna
					rs.getString("nombreAula") + "\t" + rs.getInt("puestos")); // o de su nombre
		}
		stmt.close(); // Se cierra el Statement
		cerrarConexion(); // Se cierra la conexi�n
	}

	public void abrirConexion(String bd, String servidor, String usuario, String password) {
		try {
			String url = String.format("jdbc:mariadb://%s:3306/%s", servidor, bd);
			this.conexion = DriverManager.getConnection(url, usuario, password); // Establecemos la conexi�n con la BD
			if (this.conexion != null) {
//				System.out.println("Conectado a la base de datos " + bd + " en " + servidor);
			} else
				System.out.println("NO se ha conectado a la base de datos " + bd + " en " + servidor);
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getLocalizedMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("C�digo error: " + e.getErrorCode());
		}
	}

	public void cerrarConexion() {
		try {
			this.conexion.close();
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexi�n: " + e.getLocalizedMessage());
		}
	}
}