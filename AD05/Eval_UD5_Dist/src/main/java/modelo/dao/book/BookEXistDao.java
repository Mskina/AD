package modelo.dao.book;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.exist.xmldb.EXistResource;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XQueryService;

import modelo.Book;
import modelo.dao.AbstractGenericDao;
import modelo.exceptions.InstanceNotFoundException;
import util.ConnectionManager;
import util.MyDataSource;

public class BookEXistDao extends AbstractGenericDao<Book> implements IBookDao {
		
	private static final String ID_TAG = "id";
	private static final String AUTOR_TAG = "author";
	private static final String CATEGORIA_TAG = "category";
	private static final String TITULO_TAG = "title";
	
	private MyDataSource dataSource;

	public BookEXistDao() {
		this.dataSource = ConnectionManager.getDataSource();

		try {
			Class cl = Class.forName(dataSource.getDriver());

			Database database = (Database) cl.newInstance();
			database.setProperty("create-database", "true");

			DatabaseManager.registerDatabase(database);

		} catch (Exception e) {
			// TODO Si no hago nada con ellas, dejo una
			e.printStackTrace();
		}
	}

	@Override
	public boolean create(Book entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Book read(long id) throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Book entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Book entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Book> findAll() {
		Book book = null;
		List<Book> bookList = new ArrayList<>();

		try (Collection col = DatabaseManager.getCollection(dataSource.getUrl() + dataSource.getColeccionBooks(),
				dataSource.getUser(), dataSource.getPwd())) {
			XQueryService xqs = (XQueryService) col.getService("XQueryService", "1.0");
			xqs.setProperty("indent", "yes");

			CompiledExpression compiled = xqs.compile("doc(\"bookstore.xml\")//book");
			ResourceSet result = xqs.execute(compiled);

			ResourceIterator i = result.getIterator();
			Resource res = null;
			while (i.hasMoreResources()) {
				try {
					res = i.nextResource();

					System.out.println(res.getContent().toString());

					book = stringNodeToBook(res.getContent().toString());
					if (book != null) {
						bookList.add(book);
					}
				} finally {
					// dont forget to cleanup resources
					try {
						((EXistResource) res).freeResources();
					} catch (XMLDBException xe) {
						xe.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bookList;
	}

	private Book stringNodeToBook(String nodeString) {
		Element node = null;
		Book book = null;
		try {
			node = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new ByteArrayInputStream(nodeString.getBytes())).getDocumentElement();

			int id = Integer.parseInt(node.getAttribute(ID_TAG));
			String author = getElementText(node, AUTOR_TAG);
			String category = node.getAttribute(CATEGORIA_TAG);
			String title = getElementText(node, TITULO_TAG);

			book = new Book(author, category, title);
			book.setId(id);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return book;
	}

	private String getElementText(Element parent, String tag) {
		String texto = "";
		NodeList lista = parent.getElementsByTagName(tag);

		if (lista.getLength() > 0) {
			texto = lista.item(0).getTextContent();
		}

		return texto;
	}

}
