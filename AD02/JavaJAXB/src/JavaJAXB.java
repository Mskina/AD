import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
public class JavaJAXB {
    private static final String LIBRERIA_XML = "./libreria-jaxb.xml";
    public static void main(String[] args) throws JAXBException, IOException {
        // Lista de LIbros
        ArrayList<Libro> libroLista = new ArrayList<Libro>();
        // Creamos varios libros
        Libro libro1 = new Libro();
        libro1.setIsbn("978-0060554736");
        libro1.setNombre("The Game");
        libro1.setAutor("Neil Strauss");
        libro1.setEditorial("Harpercollins");
        libroLista.add(libro1);
        Libro libro2 = new Libro();
        libro2.setIsbn("978-3832180577");
        libro2.setNombre("Feuchtgebiete");
        libro2.setAutor("Charlotte Roche");
        libro2.setEditorial("Dumont Buchverlag");
        libroLista.add(libro2);
        // Se crea La libreria y se le asigna la lista de libros
        Libreria libreria = new Libreria();
        libreria.setNombre("LIbreria sin limite");
        libreria.setLugar("Barrio Obrero");
        libreria.setListaLibro(libroLista);
        // Creamos un contexto de la clase JAXB y lo intanciamos
       JAXBContext context = JAXBContext.newInstance(Libreria.class);
       Marshaller m = context.createMarshaller();
       m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
       // Lo creamos con system out
       m.marshal(libreria, System.out);
       // Escribimos en el archivo
       m.marshal(libreria, new File(LIBRERIA_XML));
       // Obtenemos las variables obtenidas del XML creado anteriormente
       System.out.println();
       System.out.println("Salida del XML: ");
       Unmarshaller um = context.createUnmarshaller();
       Libreria libreria2 = (Libreria) um.unmarshal(new FileReader(LIBRERIA_XML));
       ArrayList<Libro> lista = libreria2.getListaLibro();
       for (Libro libro : lista) {
       System.out.println("Libro: " + libro.getNombre() + " de " + libro.getAutor());
   }
}
}