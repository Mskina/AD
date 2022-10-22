/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad01actividad5;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author Iván Estévez Sabucedo
 */
public class AD01Actividad5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        // TODO code application logic here

        /**
         * Leer el contenido del fichero personas.xml y almacenarlo en un
         * ArrayList<Persona>. Finalmente debe mostrar el contenido del
         * ArrayList recuperado. A tener en cuenta que el atributo borrado es
         * opcional. Si no existe, se asume que su valor es false.
         *
         *
         */
        SAXParserFactory factory = SAXParserFactory.newInstance();        
        SAXParser sp = factory.newSAXParser();
        File file = new File("personas.xml");
        PersonaHandler handler = new PersonaHandler();
        sp.parse(file, handler);

        ArrayList<Persona> personas = handler.getPersonas();

        System.out.println("-- ARRAY personas.xml");
        for (Persona p : personas) {
            System.out.println(p);
        }
        System.out.println("\n\n");

        /**
         * Leer el contenido del fichero personas_ns.xml y seleccionar separar
         * las personas en activo de las demás para almacenarlas en sendos
         * ArrayList<Persona>. Finalmente debe mostrar el contenido de los dos
         * ArrayList. Utiliza la URI y no el prefijo de los espacios de nombres
         * proporcionados. Ten en cuenta que el prefijo podría cambiar.
         *
         */
        
        SAXParserFactory factoryNS = SAXParserFactory.newInstance();
        factoryNS.setNamespaceAware(true);
        SAXParser spNS = factoryNS.newSAXParser();
        File fileNS = new File("personas_ns.xml");
        PersonaHandlerNS handlerNS = new PersonaHandlerNS();
        spNS.parse(fileNS, handlerNS);

        ArrayList<Persona> personasNS = handlerNS.getPersonasNS();
        ArrayList<Persona> personasNSA = handlerNS.getPersonasNSA();
        
        System.out.println("-- ARRAY personas_ns.xml");
        System.out.println("- Personas activas -");
        for (Persona p : personasNS) {
            System.out.println(p);
        }
        System.out.println("- Personas inactivas -");
        for (Persona p : personasNS) {
            System.out.println(p);
        }
    }

}
