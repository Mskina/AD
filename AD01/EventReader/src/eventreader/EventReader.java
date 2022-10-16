/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventreader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author mskin
 */
public class EventReader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // primero crea un nuevo XMLInputFactory
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();

        // Configura un nuevo eventReader a partir del fichero XML
        InputStream in = null;
        try {
            in = new FileInputStream("books.xml");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

            // repetitiva que recorre todos los eventos
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                // si el evento es el inicio del nodo titulo
                // avanzo un evento para obtener el titulo del libro
                if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart() == "title") {
                        Iterator iterator = ((StartElement) event).getAttributes(); //StartElement casteado
                        while (iterator.hasNext()) {
                            Attribute attribute = (Attribute) iterator.next();
                            QName name = attribute.getName();
                            String value = attribute.getValue();
                            System.out.println("Atributo name/valor: " + "/" + value);
                        }
                        event = eventReader.nextEvent();
                        System.out.println((String) event.asCharacters().getData());
                    }
                } else if (event.getEventType() == XMLStreamConstants.END_DOCUMENT) {
                    System.out.println("fin del documento");
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
