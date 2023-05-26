package modelo.main.ud4;

import connection.util.ConnectionManager;
import connection.util.MyDataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.OracleDepartamento;
import oracle.jdbc.OracleResultSet;

public class MainOracle {

	public static void main(String[] args) {

		MyDataSource dataSource;

		Connection conn = null;
		try {
			dataSource = ConnectionManager.getDataSource("src/main/resources/db.properties");

			// Update url
			String url = "jdbc:oracle:thin:@192.168.56.102:1521/xepdb1";

			conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUser(), dataSource.getPwd());

			// Create Oracle DatabaseMetaData object
			DatabaseMetaData meta = conn.getMetaData();

			// gets driver info:
			System.out.println("JDBC driver version is " + meta.getDriverVersion());

			// Crear el select que permite obtener todos los objetos de la tabla dept_table
			PreparedStatement stmt = conn.prepareStatement("SELECT VALUE(d) FROM dept_table d");
			OracleResultSet rs = (OracleResultSet) stmt.executeQuery();
			while (rs.next()) {

				Object s = rs.getObject(1, OracleDepartamento.getOracleDataFactory());

				if (s != null) {
					if (s instanceof OracleDepartamento) {
						System.out.println(s);
					}

					else {
						System.out.println("Unknown type");
						System.out.println(s);
					}
				}
			}

		} catch (SQLException e) {
			System.err.println("Ha ocurrido una exception: " + e.getMessage());
			e.printStackTrace();
		}

		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.err.println("Ha ocurrido una exception cerrando la conexión: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

}
