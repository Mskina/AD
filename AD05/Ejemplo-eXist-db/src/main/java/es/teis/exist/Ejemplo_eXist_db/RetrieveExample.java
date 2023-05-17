package es.teis.exist.Ejemplo_eXist_db;

import javax.xml.transform.OutputKeys;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import es.teis.exist.util.ConnectionManager;
import es.teis.exist.util.MyDataSource;

//http://exist-db.org/exist/apps/doc/devguide_xmldb
public class RetrieveExample {

	/**
	 * args[0] Should be the name of the collection to access
	 * 
	 * args[1] Should be the name of the resource to read from the collection
	 * 
	 */
	public static void main(String args[]) throws Exception {

		MyDataSource dataSource = ConnectionManager.getDataSource();

		Class cl = Class.forName(dataSource.getDriver());
		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");

		DatabaseManager.registerDatabase(database);

		Collection col = null;
		XMLResource res = null;
		try {
			// get the collection
			col = DatabaseManager.getCollection(dataSource.getUrl() + args[0]);

			col.setProperty(OutputKeys.INDENT, "yes");
			res = (XMLResource) col.getResource(args[1]);

			if (res == null) {
				System.out.println("document not found!");
			} else {
				System.out.println(res.getContent());
			}
		} finally {
			// dont forget to clean up!

			if (res != null) {
				try {
					((EXistResource) res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}

			if (col != null) {
				try {
					col.close();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
	}
}
