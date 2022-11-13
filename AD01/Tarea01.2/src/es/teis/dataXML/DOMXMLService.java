/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.teis.dataXML;

import es.teis.data.exceptions.LecturaException;
import es.teis.model.Partido;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Crear una clase DOMXMLService que implemente IXMLService que lea un documento
 * XML con DOM. La ruta del fichero a leer viene por parámetro. Debe extraer la
 * información de los partidos presentados y devolver un ArrayList con aquellos
 * partidos cuyo porcentaje de votos supera un umbral que viene por parámetro.
 * Si se genera alguna excepción, de cualquier tipo, se creará una excepción de
 * tipo LecturaException que incluirá el mensaje de la excepción original y la
 * ruta del fichero.
 *
 * @author Iván Estévez Sabucedo
 */
public class DOMXMLService implements IXMLService {

    /**
     * Lee un documento XML a partir de la ruta y filtra aquellos partidos que
     * no superen el umbral de votos.
     *
     * @param ruta en la que se encuentra el documento.
     * @param umbral porcentaje mínimo de voto de los partidos.
     * @return una lista con los partidos que superal el umbral.
     * @throws LecturaException cuando ocurre cualquier error en la lectura del
     * fichero. Contiene la ruta del fichero que genera el error.
     */
    @Override
    public ArrayList<Partido> leerPartidos(String ruta, float umbral) throws LecturaException {
        ArrayList<Partido> partidos = new ArrayList<>();

        try {
            Document doc = instanciarDocument(new File(ruta));

            NodeList nList = doc.getElementsByTagName(PARTIDO_TAG);

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    long id = Long.parseLong(element.getAttribute(PARTIDO_ATT_ID));
                    String nombre = getValorEtiqueta(PARTIDO_NOMBRE_TAG, element);
                    int votos = Integer.parseInt(getValorEtiqueta(PARTIDO_VOTOS_NUM_TAG, element));
                    float porcentaje = Float.parseFloat(getValorEtiqueta(PARTIDO_VOTOS_PORC_TAG, element));

                    if (porcentaje > umbral) {
                        partidos.add(new Partido(id, nombre, votos, porcentaje));
                    }
                }
            }
        } catch (Exception e) {
            throw new LecturaException(e.getMessage(), ruta);
        }
        return partidos;
    }

    /**
     * Instancia un objeto Document para trabajar con una esctructura en árbol
     * del XML en memoria. Para ello, crea la Factory, el DocumentBuilder y
     * parsea el XML, devolviendo un Document. También realiza el método
     * normalize().
     *
     * @param fXmlFile File XML que queremos tratar.
     * @return Document creado
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private Document instanciarDocument(File fXmlFile) throws ParserConfigurationException, SAXException, IOException {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fXmlFile);
        doc.getDocumentElement().normalize();
        return doc;
    }

    /**
     * Obtiene el texto de una etiqueta XML.
     *
     * @param etiqueta de la que queremos obtener el texto
     * @param elemento en el que se encuentra la etiqueta
     * @return el texto en la etiqueta
     */
    private String getValorEtiqueta(String etiqueta, Element elemento) {
        Node nValue = elemento.getElementsByTagName(etiqueta).item(0);
        return nValue.getChildNodes().item(0).getNodeValue();
    }
}
