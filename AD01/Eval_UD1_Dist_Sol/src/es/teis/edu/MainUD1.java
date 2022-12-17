/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package es.teis.edu;

import es.teis.data.exceptions.LecturaException;
import es.teis.model.Partido;
import java.nio.file.Paths;
import java.util.ArrayList;
import es.teis.dataXML.PartidosHandler;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author maria
 */
public class MainUD1 {

    private static final String ELECCIONES_INPUT_FILE = Paths.get("src", "docs", "elecciones.xml").toString();
    private static final String ELECCIONES_OUTPUT_FILE_XML = Paths.get("src", "docs", "elecciones_output.xml").toString();

    private static final String ELECCIONES_OUTPUT_FILE_CSV_BK = Paths.get("src", "docs", "elecciones_output.csv.bk").toString();
    private static final String ELECCIONES_OUTPUT_FILE_CSV = Paths.get("src", "docs", "elecciones_output.csv").toString();

    public static void main(String[] args) {
        if (Files.exists(Paths.get(ELECCIONES_INPUT_FILE))) {
            try {
                ArrayList<Partido> partidos;
                //Leemos con SAX src/docs/elecciones.xml
                partidos = leerPartidosConSAX(ELECCIONES_INPUT_FILE);
                //Creamos un fichero de texto con los ids de los partidos separados por comas
                escribirIdPartidosCSV(partidos, ELECCIONES_OUTPUT_FILE_CSV);
                //Escribimos solo algunos datos de los partidos en XML mediante DOM
                escribirPartidosConDOM(partidos, ELECCIONES_OUTPUT_FILE_XML);
            } catch (LecturaException ex) {
                System.err.println("Se ha capturado una excepción. " + ex.getMessage());
            }
        }

    }

    /**
     * Lee mediante SAX el fichero indicado en ruta (en nuestro caso
     * src/docs/elecciones.xml) y crea un ArrayList<Partido>
     *
     * @param ruta cadena de texto con la ruta al fichero que será leído
     * mediante SAX
     * @return ArrayList<Partido>
     */
    private static ArrayList<Partido> leerPartidosConSAX(String ruta) throws LecturaException {
        try {
            ArrayList<Partido> partidos = new ArrayList<Partido>();

            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

            SAXParser saxParser = saxParserFactory.newSAXParser();
            File file = new File(ruta);
            PartidosHandler handler = new PartidosHandler();
            saxParser.parse(file, handler);
            partidos = handler.getPartidos();

            mostrar(partidos);
            return partidos;
        } catch (Exception ex) {
            throw new LecturaException(ex.getMessage(), ruta);
        }
    }

    private static void escribirIdPartidosCSV(ArrayList<Partido> partidos, String ruta) {

        String cadenaCSV = partidosListToCSVIds(partidos);
        System.out.println("La cadena generada " + cadenaCSV + "debe escribirse en un fichero de texto indicado en ruta");
        //completar 

        try ( FileWriter fw = new FileWriter(ruta);  BufferedWriter bw = new BufferedWriter(fw);) {
            bw.write(cadenaCSV);
        } catch (IOException ex) {
            System.err.println("Se ha capturado una excepción. " + ex.getMessage());
        }
        //Para escribir ficheros de caracteres usamos FileWriter y mayor rendimiento con BufferedWriter
    }

    private static void escribirPartidosConDOM(ArrayList<Partido> partidos, String ruta) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            //Crea un document con un elmento raiz
            Document document = implementation.createDocument(null, PartidosHandler.PARTIDOS_TAG, null);

            Element root = document.getDocumentElement();
            for (Partido partido : partidos) {
                //completar ...
                Element epartido = document.createElement(PartidosHandler.PARTIDO_TAG);
                addElementConTexto(document, epartido, PartidosHandler.PARTIDO_NOMBRE_TAG, partido.getNombre());
                epartido.setAttribute(PartidosHandler.PARTIDO_ATT_ID,String.valueOf( partido.getId()));
                
                root.appendChild(epartido);
            }

            TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
            //Espacios para indentar cada línea
            fabricaTransformador.setAttribute("indent-number", 4);
            Transformer transformador = fabricaTransformador.newTransformer();
            //Insertar saltos de línea al final de cada línea
            //https://docs.oracle.com/javase/8/docs/api/javax/xml/transform/OutputKeys.html
            transformador.setOutputProperty(OutputKeys.INDENT, "yes");
            //El origen de la transformación es el document
            Source origen = new DOMSource(document);
            //El destino será un stream a un fichero
            Result destino = new StreamResult(ruta);
            transformador.transform(origen, destino);

        } catch (TransformerConfigurationException ex) {
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
        } catch (TransformerException | ParserConfigurationException ex) {
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
        }

    }

    private static void mostrar(ArrayList<Partido> partidos) {
        System.out.println("Se han leído los siguientes partidos: ");
        for (Partido partido : partidos) {
            System.out.println(partido);

        }
    }

    /**
     * Recibe una lista de objetos Partido, obtiene sus ids y los convierte a
     * cadena de texto separados por comas
     *
     * @param partidos
     * @return una lista separada por comas de los ids de los partidos
     */
    private static String partidosListToCSVIds(ArrayList<Partido> partidos) {
        String[] partidosArray = new String[partidos.size()];
        for (int i = 0; i < partidos.size(); i++) {
            partidosArray[i] = String.valueOf(partidos.get(i).getId());
        }

        String cadenaCSV = String.join(",", partidosArray);
        return cadenaCSV;
    }

    /**
     * Añade a un nodo padre, un nodo hijo con la etiqueta tag y al hijo le
     * añade un nodo texto
     *
     * @param document
     * @param padre: nodo padre
     * @param tag: texto de etiqueta para hijo
     * @param text: texto contenido entre las etiquetas hijo
     */
    private static void addElementConTexto(Document document, Node padre, String tag, String text) {
        //Creamos un nuevo nodo de tipo elemento desde document
        Node node = document.createElement(tag);
        //Creamos un nuevo nodo de tipo texto también desde document
        Node nodeTexto = document.createTextNode(text);
        //añadimos a un nodo padre el nodo elemento
        padre.appendChild(node);
        //Añadimos al nodo elemento su nodo hijo de tipo texto
        node.appendChild(nodeTexto);
    }
}
