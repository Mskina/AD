/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listalibros;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 * El programa java que recorre el documento para extraer los titulos y el
 * atributo lang es el siguiente
 *
 * @author mskin
 */
public class ListaLibros {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws javax.xml.stream.XMLStreamException
     */
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        // Creamos el flujo
        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        XMLStreamReader xmlsr = xmlif.createXMLStreamReader(new FileReader("books.xml"));
        String tag = null;
        int eventType;
        System.out.println("Lista de libros");
        
        // iteramos con el cursor a lo largo del documento
        while (xmlsr.hasNext()) {
            eventType = xmlsr.next();
            switch (eventType) {
                case XMLEvent.START_ELEMENT:
                    tag = xmlsr.getLocalName();
                    if (tag.equals("title")) {
                        System.out.println(xmlsr.getElementText() + " idioma&nbsp; " + xmlsr.getAttributeValue(0));
                    }
                    break;
                case XMLEvent.END_DOCUMENT:
                    System.out.println("Fin del documento");
                    break;
            }
        }
    }

}
