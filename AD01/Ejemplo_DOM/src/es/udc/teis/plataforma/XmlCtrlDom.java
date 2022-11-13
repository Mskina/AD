/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.udc.teis.plataforma;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author mfernandez
 * Sección 6.4 de la plataforma a distancia
 */
public class XmlCtrlDom {

    public static Document instanciarDocument() throws ParserConfigurationException {

        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        return doc;
    }

    public static void escribirDocumentATextXml(Document doc, File file)
            throws TransformerException {
        Transformer trans = TransformerFactory.newInstance().newTransformer();//faltan paréntesis
        trans.setOutputProperty(OutputKeys.INDENT, "yes"); //indent con mayúsculas
        StreamResult result = new StreamResult(file);
        DOMSource source = new DOMSource(doc);
        trans.transform(source, result);
    }

    public static Document instanciarDocument(File fXmlFile)
            throws ParserConfigurationException,
            SAXException,
            IOException {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fXmlFile); //faltan paréntesis
        doc.getDocumentElement().normalize(); //faltan paréntesis
        return doc;
    }

    public static String getValorEtiqueta(String etiqueta, Element elemento) {
        Node nValue = elemento.getElementsByTagName(etiqueta).item(0); //Node en lugar de Nodo
        return nValue.getChildNodes().item(0).getNodeValue(); //Faltan paréntesis y sobran espacios
    }

    public static Element getElementEtiqueta(String etiqueta, Element elemento) { //Element en lugar de Elemento
        return (Element) elemento.getElementsByTagName(etiqueta).item(0);
    }

    /*Método extra*/
      public static void addElementConTexto(Document document, Node padre, String tag, String text) {
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
