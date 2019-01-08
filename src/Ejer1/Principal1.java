package Ejer1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import org.mariadb.jdbc.Driver;

public class Principal1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JDBC database1 = new JDBC();
		try {
//			database1.consultaAlumnos("add");
//			database1.consultaAlumnosPorNombre("%a%");
//			database1.darAltaAlumno("ero", "sobrino", 180, 5);
//			database1.darAltaAula(1, "sdffb", 30);
//			database1.borraAlumno(19);
//			database1.borraAula(1);
//			database1.modificaAlumnoPorID(18, "asfb");
//			database1.modificaAulaId(11, "asd");
//			database1.aulasConAlumnos();
//			database1.alumnoPorNombreAltura("%a%", 120, true);
//			database1.alumnoPorNombreAltura("%a%", 120, false);
//			database1.abrirConexion("add", "localhost", "root", "");
//			for (int i = 1; i < 10000001; i *= 10) {
//				database1.tiempoEjecucion1(i);
//				System.out.print(" ");
//				database1.tiempoEjecucion2(i);
//				System.out.println();
//			}
//			database1.cerrarConexion();
//			database1.anadirColumna("alumnos", "pruebas1", "text", "not null");
//			database1.abrirConexion("", "localhost", "root", "");
//			database1.getInfo("add");
//			database1.cerrarConexion();
//			database1.abrirConexion("add", "localhost", "root", "");
//			String non = "ray";
//			database1.getInfoConsulta("select *, nombre as " + non + " from alumnos");
//			database1.cerrarConexion();
//			database1.abrirConexion("add", "localhost", "root", "");
//			database1.listaDrivers();
//			database1.cerrarConexion();
//			database1.abrirConexion("add", "localhost", "root", "");
//			database1.trasnsaccionInsercion(new String[] { "INSERT INTO aulas VALUES (5, 'Física', 23)",
//					"INSERT INTO aula VALUES (6, 'Química', 34)" });
//			database1.cerrarConexion();
			database1.abrirConexion("add", "localhost", "root", "");
			database1.leeObjetosBinarios();
			database1.cerrarConexion();
		} catch (Exception e) {
			System.out.println("Se ha producido un error: " + e.getLocalizedMessage());
		}
	}
}

class JDBC {
	private Connection conexion;
	private PreparedStatement ps = null;

	// Ejer13
	
	public void escribeObjetosBinarios() throws IOException,SQLException{
		
	}
	public void leeObjetosBinarios() throws IOException, SQLException {
		String query = "select * from imagenes";
		int i=0;
		Statement stmt = this.conexion.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			System.out.println(rs.getString("nombre"));
			try (FileOutputStream fos = new FileOutputStream("D:\\Ciclo\\Acceso a datos\\"+rs.getString("nombre"));
					) {
				InputStream is=rs.getBinaryStream("imagen");
				while ((i=is.read())!=-1) {
					fos.write(i);
				}
				
				is.close();
			}
		}
		stmt.close();
	}

	// Ejer12
	public void trasnsaccionInsercion(String[] consultas) {
		// Haciendo un commit antes
		try {
			this.conexion.setAutoCommit(false);
			Statement st = this.conexion.createStatement();
			for (int i = 0; i < consultas.length; i++) {
				st.executeUpdate(consultas[i]);
			}
			this.conexion.commit();
			// si no se produce ningún error se confirman los cambios
		} catch (SQLException e) {
			System.out.println("Se ha producido un error: " + e.getLocalizedMessage());
			try {
				if (this.conexion != null) {
					System.out.println("Se deshacen los cambios mediante un rollback");
					this.conexion.rollback();
					// si se ha producido un error los cambios se deshacen
				}
			} catch (SQLException e2) {
				System.out.println("Error al realizar el rollback " + e.getLocalizedMessage());
			}
		}
	}

	// Ejer11 falta
	public void listaDrivers() throws SQLException {
	}

	// Ejer10
	public void getInfoConsulta(String consulta) throws SQLException {
		Statement st = this.conexion.createStatement();
		ResultSet filas = st.executeQuery(consulta);
		ResultSetMetaData rsmd = filas.getMetaData();
		System.out.printf("%2s %10s %9s %10s %10s %10s\n", "Num", "Nombre", "Alias", "TipoDatos", "AutoInc", "Nulos");
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			System.out.println(String.format("%2d %10s %10s %10s %10s %10s", i, rsmd.getColumnName(i),
					rsmd.getColumnLabel(i), rsmd.getColumnTypeName(i), rsmd.isAutoIncrement(i), rsmd.isNullable(i)));
		}
	}

	// Ejer9
	public void getInfo(String bd) {
		DatabaseMetaData dbmt;
		ResultSet tablas, procedimientos, claves, columnas, catalogo;
		try {
			System.out.println("a:");
			dbmt = this.conexion.getMetaData();
			System.out.printf("%s %s\n", dbmt.getDriverName(), dbmt.getDriverVersion());
			System.out.println(dbmt.getConnection());
			System.out.println(dbmt.getUserName());
			System.out.printf("%s %s\n", dbmt.getDatabaseProductName(), dbmt.getDatabaseProductVersion());
			System.out.println(dbmt.getSQLKeywords());
			System.out.println("\nb:");
			catalogo = dbmt.getCatalogs();
			while (catalogo.next()) {
				System.out.println(catalogo.getString("TABLE_CAT"));
			}
			System.out.println("\nc:");
			tablas = dbmt.getTables(bd, null, null, null);
			while (tablas.next()) {
				System.out.println(
						String.format("%s %s", tablas.getString("TABLE_NAME"), tablas.getString("TABLE_TYPE")));
			}
			System.out.println("\nd:");
			tablas = dbmt.getTables(bd, null, null, null);
			while (tablas.next()) {
				if (tablas.getString("TABLE_TYPE").equals("VIEW")) {
					System.out.println(
							String.format("%s %s", tablas.getString("TABLE_NAME"), tablas.getString("TABLE_TYPE")));
				}
			}
			System.out.println("\ne:");
			catalogo = dbmt.getCatalogs();
			while (catalogo.next()) {
				System.out.println(catalogo.getString("TABLE_CAT"));
				tablas = dbmt.getTables(catalogo.getString("TABLE_CAT"), null, null, null);
				while (tablas.next()) {
					System.out.println(
							String.format("%s %s", tablas.getString("TABLE_NAME"), tablas.getString("TABLE_TYPE")));
				}
			}
			System.out.println("\nf:");
			procedimientos = dbmt.getProcedures(bd, null, null);
			while (procedimientos.next()) {
				System.out.println(procedimientos.getString("PROCEDURE_NAME"));
			}
			System.out.println("\ng:");
			tablas = dbmt.getTables(bd, null, null, null);
			System.out.println(bd);
			while (tablas.next()) {
				int cont = 0;
				if (tablas.getString("TABLE_NAME").startsWith("a")) {
					System.out.println(
							String.format("%s %s", tablas.getString("TABLE_NAME"), tablas.getString("TABLE_TYPE")));
					columnas = dbmt.getColumns(bd, null, tablas.getString("TABLE_NAME"), null);
					while (columnas.next()) {
						cont++;
						System.out.println(String.format("%d %s %s %d %s %s", cont, columnas.getString("COLUMN_NAME"),
								columnas.getString("TYPE_NAME"), columnas.getInt("COLUMN_SIZE"),
								columnas.getString("IS_NULLABLE"), columnas.getString("IS_AUTOINCREMENT")));
					}
				}
			}
			System.out.println("\nh:");
			tablas = dbmt.getTables(bd, null, null, null);
			while (tablas.next()) {
				claves = dbmt.getPrimaryKeys(bd, null, tablas.getString("TABLE_NAME"));
				while (claves.next()) {
					System.out.println(claves.getString("COLUMN_NAME"));
				}

				claves = dbmt.getExportedKeys(bd, null, tablas.getString("TABLE_NAME"));
				while (claves.next()) {
					System.out.println(claves.getString("FK_NAME"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Error obteniendo datos " + e.getLocalizedMessage());
		}
	}

	// Ejer8
	public void anadirColumna(String tabla, String nombreCampo, String tipoDato, String propiedades)
			throws SQLException {
		String query = "ALTER TABLE " + tabla + " ADD " + nombreCampo + " " + tipoDato + " " + propiedades;
		abrirConexion("add", "localhost", "root", "");
		Statement sta = this.conexion.createStatement();
		sta.executeUpdate(query);
		sta.close();
		cerrarConexion();
	}

	// Ejer7
	public void tiempoEjecucion1(int veces) throws SQLException {
//	24 1
//	3 1
//	16 31
//	204 200
//	1617 1832
//	17687 17501
//	181336 177965
		String patronNombre = "%a%";
		int altura = 120;
		long tiempoInicio = System.currentTimeMillis();
		String query = "select nombre from alumnos where nombre like ? and altura>?";
		if (this.ps == null)
			this.ps = this.conexion.prepareStatement(query);
		ps.setString(1, patronNombre);
		ps.setInt(2, altura);
		for (int i = 0; i < veces; i++) {
			ResultSet resultado = ps.executeQuery();
			while (resultado.next()) {
//				System.out.println(resultado.getString("nombre"));
			}
		}
		System.out.print(System.currentTimeMillis() - tiempoInicio);
	}

	public void tiempoEjecucion2(int veces) throws SQLException {
		String patronNombre = "%a%";
		int altura = 120;
		Statement stmt = this.conexion.createStatement();
		long tiempoInicio = System.currentTimeMillis();
		for (int i = 0; i < veces; i++) {
			String query = "select nombre from alumnos where nombre like '" + patronNombre + "' and altura>" + altura;
			ResultSet resultado = stmt.executeQuery(query);
			while (resultado.next()) {
//				System.out.println(resultado.getString("nombre"));
			}
		}
		stmt.close();
		System.out.print(System.currentTimeMillis() - tiempoInicio);
	}

	// Ejer6
	public void alumnoPorNombreAltura(String patronNombre, int altura, boolean preparada) throws SQLException {
		abrirConexion("add", "localhost", "root", "");
		if (preparada) {
			String query = "select nombre from alumnos where nombre like ? and altura>?";
			if (this.ps == null)
				this.ps = this.conexion.prepareStatement(query);
			ps.setString(1, patronNombre);
			ps.setInt(2, altura);
			ResultSet resultado = ps.executeQuery();
			while (resultado.next()) {
				System.out.println(resultado.getString("nombre"));
			}
		} else {
			Statement stmt = this.conexion.createStatement();
			String query = "select nombre from alumnos where nombre like '" + patronNombre + "' and altura>" + altura;
			ResultSet resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				System.out.println(resultado.getString("nombre"));
			}
			stmt.close();
		}
		cerrarConexion();
	}

	// Ejer5
	public void aulasConAlumnos() throws SQLException {
		String query = "select * from aulas where numero in(select distinct aula from alumnos);";
		String query2 = "select nombre from asignaturas where cod not in (select asignatura from notas);";
		String query3 = "select nombre from alumnos where codigo in(select distinct alumno from notas where nota>=5);";
		abrirConexion("add", "localhost", "root", "");
		Statement stmt = this.conexion.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			System.out.printf("%2d %12s %3d\n", rs.getInt("numero"), rs.getString("nombreAula"), rs.getInt("puestos"));
		}
		ResultSet rs2 = stmt.executeQuery(query2);
		System.out.println();
		while (rs2.next()) {
			System.out.println(rs2.getString("nombre"));
		}
		ResultSet rs3 = stmt.executeQuery(query3);
		System.out.println();
		while (rs3.next()) {
			System.out.println(rs3.getString("nombre"));
		}
		stmt.close();
		cerrarConexion();
	}

	// Ejer4
	public void modificaAlumnoPorID(int id, String nombre) throws SQLException {
		String query = "update alumnos set nombre='" + nombre + "' where codigo=" + id;
		abrirConexion("add", "localhost", "root", "");
		Statement stmt = this.conexion.createStatement();
		stmt.executeUpdate(query);
		stmt.close();
		cerrarConexion();
	}

	public void modificaAulaId(int id, String nombre) throws SQLException {
		String query = "update aulas set nombreAula='" + nombre + "' where numero=" + id;
		abrirConexion("add", "localhost", "root", "");
		Statement stmt = this.conexion.createStatement();
		stmt.executeUpdate(query);
		stmt.close();
		cerrarConexion();
	}

	// Ejer3
	public void borraAlumno(int id) throws SQLException {
		borrarElementoID(id, "alumnos");
	}

	public void borraAula(int id) throws SQLException {
		borrarElementoID(id, "aulas");
	}

	public void borrarElementoID(int id, String tabla) throws SQLException {
		String nombreClave = "";
		switch (tabla) {
		case "alumnos":
			nombreClave = "codigo";
			break;
		case "aulas":
			nombreClave = "numero";
			break;
		}
		if (nombreClave.length() > 0) {
			String query = "delete from " + tabla + " where " + nombreClave + "=" + id;
			abrirConexion("add", "localhost", "root", "");
			Statement stmt = this.conexion.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
			cerrarConexion();
		}
	}

	// Ejer2
	public void darAltaAlumno(String nombre, String apellido, int altura, int aula) throws SQLException {
		String query = "insert into alumnos(nombre,apellidos,altura,aula)" + " values('" + nombre + "','" + apellido
				+ "'," + altura + "," + aula + ");";
		abrirConexion("add", "localhost", "root", "");
		Statement stmt = this.conexion.createStatement();
		stmt.executeUpdate(query);
		stmt.close();
		cerrarConexion();
	}

	public void darAltaAula(int numero, String nombre, int puestos) throws SQLException {
		String query = "insert into aulas values(" + numero + ",'" + nombre + "'," + puestos + ");";
		abrirConexion("add", "localhost", "root", "");
		Statement stmt = this.conexion.createStatement();
		stmt.executeUpdate(query);
		stmt.close();
		cerrarConexion();
	}

	// Ejer1
	public void consultaAlumnosPorNombre(String cadenaContenida) throws SQLException {
		String query = "select * from alumnos where nombre like '" + cadenaContenida + "';";
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
		while (rs.next()) { // Mientras queden filas en rs (el método next devuelve true) recorremos las
							// filas
			System.out.println(rs.getInt(1) + "\t" + // Se obtiene datos en función del número de columna
					rs.getString("nombreAula") + "\t" + rs.getInt("puestos")); // o de su nombre
		}
		stmt.close(); // Se cierra el Statement
		cerrarConexion(); // Se cierra la conexión
	}

	public void abrirConexion(String bd, String servidor, String usuario, String password) {
		try {
			String url = String.format("jdbc:mariadb://%s:3306/%s", servidor, bd);
			this.conexion = DriverManager.getConnection(url, usuario, password); // Establecemos la conexión con la BD
			if (this.conexion != null) {
//				System.out.println("Conectado a la base de datos " + bd + " en " + servidor);
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