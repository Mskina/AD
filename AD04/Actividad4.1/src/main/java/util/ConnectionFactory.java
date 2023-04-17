package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

public class ConnectionFactory {
	private static final String RUTA = "src/main/resources/db.properties";
	private static final String CLAVE = "db.file";
	
	private static ODB connection = null;

	private ConnectionFactory() {
	}

	/**
	 * Singleton: https://es.wikipedia.org/wiki/Singleton#Java
	 * -> En caso de que haya hilos, se emplea synchronized
	 * 
	 * @return
	 */
	public static ODB getConnection() {
		// Si la conexión ya está abierta, la devuelvo como singleton (una misma para
		// todos los usos) --> La variable que queramos que sea singleton debe ser
		// STATIC
		if (connection != null) {
			return connection;
		}
		// En caso de que no exista...

		// Creo el objeto Properties
		Properties properties = new Properties();

		try {
			// Intento cargarlo, dándole la ruta relativa desde donde se ejecuta el programa
			properties.load(new FileInputStream(new File(RUTA)));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Asigno clave y obtengo valor		
		String valor = properties.getProperty(CLAVE);

		// Creo y asigno la conexión con la DB
		connection = ODBFactory.open(valor);

		// Devuelvo su valor
		return connection;
	}

	/**
	 * Si ya existe la conexión, la cierro y la pongo en nulo para poder volver a
	 * iniciarla en getConnection().
	 */
	public static void closeConnection() {
		if (connection != null) {
			connection.close();
			connection = null;
		}
	}

}
