package Ejer1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.mariadb.jdbc.Driver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

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
//			database1.abrirConexion("add", "localhost", "root", "");
//			database1.leeObjetosBinarios("D:\\Ciclo\\Acceso a datos");
//			database1.escribeObjetosBinarios("D:\\Descargas\\imagen.png");
//			database1.cerrarConexion();
//			database1.abrirConexionUCANACCESS("D:/Ciclo/Acceso a datos/2 Ev/Alumnos.mdb");
//			database1.abrirConexion("add", "localhost", "root", "");
//			database1.ejecutaGetAulasSuma(10,"o");
//			database1.cerrarConexion();
//			database1.abrirConexion("add", "localhost", "root", "");
//			database1.buscaCadenaEnBD("add", "Lar");
//			database1.cerrarConexion();
//			database1.creaXML("D:\\Ciclo\\Acceso a datos\\tabla.xml", "add", "alumnos");

//			database1.abrirSQLite("D:\\Ciclo\\Acceso a datos", "sql.db");
////			database1.aulasConNPuestos(30, true);
////			database1.insertarAula(3, "wfsdg", 3);
//			database1.insertaAulaReemplaza(1, "1111sdtggerth1rettrrrrtt", 2);
//			database1.buscaPorNombreEnMySQLySQLite("add","D:\\Ciclo\\Acceso a datos","sql.db","F");
//			database1.cerrarConexion();
//			database1.insercionEnAmbasConRollBack("add", "D:\\Ciclo\\Acceso a datos", "sql.db", 1, "sdfsdf", 6);
//			database1.introduceDatosFechas("add", "D:\\Ciclo\\Acceso a datos", "sql.db", "ddddddddaaaaaaaaaaaaaaaaaaaaaaaae",
//					"1968-10-23 12:45:37.123");
//			database1.introduceDatosFechas("add", "D:\\Ciclo\\Acceso a datos", "sql.db", "dddddddde",
//					"19681023124537123");
			database1.introduceDatosFechas("add", "D:\\Ciclo\\Acceso a datos", "sql.db", "dddddddde", "");

		} catch (Exception e) {
			System.out.println("Se ha producido un error: " + e.getLocalizedMessage());
		}
	}
}

class JDBC {
	private Connection conexion;
	private PreparedStatement ps = null;

	// b) Con los parametros añade la cadena recortada a los 1o caracteres maximos,
	// con la normale no se añade y en sqlite se añade entera
	// c)Con los parametros se añade bien la fecha, en sqlite se añade la cadena
	// como esta y de forma normal da error en el formato de la fecha
	// d) Con sqlite tiene una hora distinta. Con datetime(current_timestamp, +1hour )
	// e) Con los parametros añade una hora con todo 0, en sqlite lo deja en blanco
	// y con la normal da error en el valor
	// Ejer10
	public void introduceDatosFechas(String bd, String rutaSQlite, String nombreBDSQLite, String nombre, String fecha)
			throws ClassNotFoundException, SQLException {
		String query = "INSERT into fechas values('" + nombre + "','" + fecha + "');";
		String query2 = "INSERT INTO fechas VALUES('" + nombre + "',CURRENT_TIMESTAMP);";
		Statement stmt;
		abrirConexion(bd, "localhost", "root", null);
		stmt = this.conexion.createStatement();
//		stmt.executeUpdate(query);
		stmt.executeUpdate(query2);
		cerrarConexion();

		try {
			String url = String.format(
					"jdbc:mariadb://%s:3306/%s?jdbcCompliantTruncation=false&zeroDateTimeBehavior=convertToNull",
					"localhost", bd);
			this.conexion = DriverManager.getConnection(url, "root", ""); // Establecemos la conexión con la BD
			stmt = this.conexion.createStatement();
//			stmt.executeUpdate(query);
			stmt.executeUpdate(query2);
			cerrarConexion();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getLocalizedMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("Código error: " + e.getErrorCode());
		}

		abrirSQLite(rutaSQlite, nombreBDSQLite);
		stmt = this.conexion.createStatement();
//		stmt.executeUpdate(query);
		stmt.executeUpdate(query2);
		cerrarConexion();
	}

	// Ejer9
	public void insercionEnAmbasConRollBack(String bd, String rutaSQlite, String nombreBDSQLite, int numero,
			String nombre, int puestos) throws ClassNotFoundException {
		String query = "INSERT into aulas values(" + numero + ",'" + nombre + "'," + puestos + ");";
		boolean insertaEnMYSQL = false;
		abrirConexion(bd, "localhost", "root", null);
		Statement stmt;
		try {
			stmt = this.conexion.createStatement();
			stmt.executeUpdate(query);
			cerrarConexion();
			insertaEnMYSQL = true;
			abrirSQLite(rutaSQlite, nombreBDSQLite);
			stmt = this.conexion.createStatement();
			stmt.executeQuery(query);
		} catch (SQLException e) {
			if (insertaEnMYSQL) {
				try {
					abrirConexion(bd, "localhost", "root", null);
					stmt = this.conexion.createStatement();
					stmt.executeUpdate("DELETE from aulas where numero=" + numero + ";");
				} catch (SQLException e1) {
				}
				System.out.println("Se hace rollback");
			}
		}
		cerrarConexion();
	}

	// Ejer8
	public void buscaPorNombreEnMySQLySQLite(String bd, String rutaSQlite, String nombreBDSQLite, String cadena)
			throws SQLException, ClassNotFoundException {
		abrirConexion(bd, "localhost", "root", null);
		String query = "select * from aulas where nombreAula like \"%" + cadena + "%\";";
		Statement stmt = this.conexion.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			System.out.println(rs.getInt("numero") + " " + rs.getString("nombreAula") + " " + rs.getInt("puestos"));
		}
		System.out.println();
		abrirSQLite(rutaSQlite, nombreBDSQLite);
		rs = stmt.executeQuery(query);
		while (rs.next()) {
			System.out.println(rs.getInt("numero") + " " + rs.getString("nombreAula") + " " + rs.getInt("puestos"));
		}
		cerrarConexion();
	}

	// Ejer6
	public void insertaAulaReemplaza(int numero, String nombre, int puestos) throws SQLException {
		boolean encontrado = false;
		ArrayList<Integer> indices = new ArrayList<>();
		String nombreAntiguo = "";
		String puestosAntiguo = "";
		String query = "SELECT * from aulas;";
		Statement stmt = this.conexion.createStatement();
		ResultSet rs = stmt.executeQuery(query); // Se ejecuta la consulta
		while (rs.next()) {
			indices.add(rs.getInt("numero"));
			if (!encontrado) {
				if (rs.getInt("numero") == numero) {
					encontrado = true;
					nombreAntiguo = rs.getString("nombreAula");
					puestosAntiguo = rs.getString("puestos");
				}
			}
		}
		if (encontrado) {
			String query3 = "DELETE FROM AULAS WHERE NUMERO = " + numero + ";";
			stmt.executeUpdate(query3);
			boolean cambiado = false;
			int i = 0;
			while (!cambiado) {
				if (!indices.contains(i)) {
					String query2 = "INSERT into aulas values(" + i + ",'" + nombreAntiguo + "'," + puestosAntiguo
							+ ");";
					stmt.executeUpdate(query2);
					cambiado = true;
				} else {
					i++;
				}
			}
		}

		String query2 = "INSERT into aulas values(" + numero + ",'" + nombre + "'," + puestos + ");";
		stmt.executeUpdate(query2);

		stmt.close();
	}

	// Ejer5
	public void insertarAula(int numero, String nombre, int puestos) throws SQLException {
		String query = "INSERT into aulas values(" + numero + ",'" + nombre + "'," + puestos + ");";
		Statement stmt = this.conexion.createStatement();
		stmt.executeUpdate(query);
		stmt.close();
	}

	// Ejer4
	public void aulasConNPuestos(int cantidad, boolean preparada) throws SQLException {
		if (preparada) {
			String query = "SELECT * from aulas WHERE PUESTOS>?;";
			if (this.ps == null)
				this.ps = this.conexion.prepareStatement(query);
			ps.setInt(1, cantidad);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out
						.println(rs.getInt("numero") + "\t" + rs.getString("nombreAula") + "\t" + rs.getInt("puestos"));
			}
		} else {
			String query = "SELECT * from aulas WHERE PUESTOS>" + cantidad + ";";
			Statement stmt = this.conexion.createStatement();
			ResultSet rs = stmt.executeQuery(query); // Se ejecuta la consulta
			while (rs.next()) {
				System.out
						.println(rs.getInt("numero") + "\t" + rs.getString("nombreAula") + "\t" + rs.getInt("puestos"));
			}
			stmt.close(); //
		}
	}

	public void abrirSQLite(String rutaBaseDeDatos, String baseDeDatos) throws ClassNotFoundException {
		try {
			Class.forName("org.sqlite.JDBC");
			this.conexion = DriverManager.getConnection("jdbc:sqlite:/" + rutaBaseDeDatos + "/" + baseDeDatos);
			if (this.conexion != null) {
//				System.out.println("Conectado a la base de datos " + bd + " en " + servidor);
			} else
				System.out.println("NO se ha conectado a la base de datos");
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getLocalizedMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("Código error: " + e.getErrorCode());
		}
	}

	// Ejer3
	// SELECT * from aulas ORDER BY PUESTOS DESC LIMIT 3;

	// Ejer17
	public void creaXML(String ruta, String bd, String nombreTabla) throws FileNotFoundException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException, SQLException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();// 12 Crea archivo con compañia
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		}
		Document tabla = builder.newDocument();
		Element datos = tabla.createElement(nombreTabla);
		datos.appendChild(tabla.createTextNode("\n"));
		tabla.appendChild(datos);
		abrirConexion(bd, "localhost", "root", "");
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery("select * from " + nombreTabla);
		int cont = 1;
		while (rs.next()) {
			ResultSetMetaData rsmd = rs.getMetaData();
			Element elemento = tabla.createElement("Elemento" + cont);
			datos.appendChild(elemento);
			cont++;
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				Element hijo = tabla.createElement(rsmd.getColumnName(i));
				Text texto = tabla.createTextNode(rs.getString(i));
				hijo.appendChild(texto);
				hijo.appendChild(tabla.createTextNode("\n"));
				elemento.appendChild(hijo);
			}
			System.out.println();
		}

		cerrarConexion();
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		DOMImplementationLS ls = (DOMImplementationLS) registry.getDOMImplementation("XML 3.0 LS 3.0");
		LSOutput output = ls.createLSOutput();
		output.setEncoding("UTF-8");
		output.setByteStream(new FileOutputStream(ruta));
		LSSerializer serializer = ls.createLSSerializer();
		serializer.setNewLine("\r\n");
		serializer.getDomConfig().setParameter("format-pretty-print", true);
		serializer.write(tabla, output);
	}

	// Ejer16
	public void buscaCadenaEnBD(String bd, String cadena) throws SQLException {
		DatabaseMetaData dbmt;
		ResultSet tablas;
		dbmt = this.conexion.getMetaData();
		tablas = dbmt.getTables(bd, null, null, null);
		System.out.println("Cadena: " + cadena + "\n");
		while (tablas.next()) {
			if (tablas.getString("TABLE_TYPE").equals("TABLE")) {
				String nombreTabla = tablas.getString("TABLE_NAME");
				String query = "select * from " + nombreTabla;
				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {
					ResultSetMetaData rsmd = rs.getMetaData();
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						if (rsmd.getColumnTypeName(i).equals("CHAR") || rsmd.getColumnTypeName(i).equals("VARCHAR")) {
							if (rs.getString(i) != null) {
								if (rs.getString(i).toUpperCase().contains(cadena.toUpperCase())) {
									System.out.println("BD: " + bd);
									System.out.println("Tabla: " + nombreTabla);
									System.out.println("Columna: " + rsmd.getColumnName(i));
									System.out.println(rs.getString(i) + "\n");
								}
							}
						}
					}
				}
			}
		}
	}

	// Ejer15
	public void ejecutaGetAulasSuma(int cantidadPuestos, String cadena) throws SQLException {
		CallableStatement cs = this.conexion.prepareCall("CALL getAulas(?,?)");
		cs.setInt(1, cantidadPuestos);
		cs.setString(2, cadena);
		ResultSet resultado = cs.executeQuery();
		while (resultado.next()) {
			System.out.println(resultado.getInt(1) + "\t" + resultado.getString("nombreAula") + "\t"
					+ resultado.getInt("puestos"));
		}
		Statement stmt = this.conexion.createStatement();
		stmt.executeQuery("SELECT suma() INTO @res;");
		ResultSet rs = stmt.executeQuery("SELECT @res;");
		while (rs.next()) {
			System.out.println(rs.getInt("@res"));
		}
	}

	// Ejer14
	public void abrirConexionUCANACCESS(String rutaBD) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			this.conexion = DriverManager.getConnection("jdbc:ucanaccess://" + rutaBD + ";memory=false");
			Statement stmt = this.conexion.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Alumnos");
			while (rs.next()) {
				System.out.print(rs.getString("Codigo") + " ");
				System.out.println(rs.getString("Nombre"));
			}
			stmt.close();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getLocalizedMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("Código error: " + e.getErrorCode());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cerrarConexion();
	}

	// Ejer13
	public void escribeObjetosBinarios(String ruta) throws IOException, SQLException {
		String query = "insert into imagenes values (?,?);";
		File f = new File(ruta);
		if (this.ps == null)
			this.ps = this.conexion.prepareStatement(query);
		ps.setString(1, f.getName());
		ps.setBinaryStream(2, new FileInputStream(f));
		ps.executeQuery();
	}

	public void leeObjetosBinarios(String ruta) throws IOException, SQLException {
		String query = "select * from imagenes";
		int i = 0;
		Statement stmt = this.conexion.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			System.out.println(rs.getString("nombre"));
			try (FileOutputStream fos = new FileOutputStream(ruta + "\\" + rs.getString("nombre"));) {
				InputStream is = rs.getBinaryStream("imagen");
				while ((i = is.read()) != -1) {
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

	// Ejer11
	public void listaDrivers() throws SQLException {
		Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = (Driver) drivers.nextElement();
			System.out.println(driver.getClass().getName());
		}
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