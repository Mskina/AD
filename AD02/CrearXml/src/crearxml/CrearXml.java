/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crearxml;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author mskin
 */
public class CrearXml {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            //Elemento raíz
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("root");
            doc.appendChild(rootElement);
            
            //Primer elemento
            Element elemento1 = doc.createElement("elemento1");
            rootElement.appendChild(elemento1);
            
            //Se agrega un atributo al nodo elemento y su valor
            Attr attr = doc.createAttribute("id");
            attr.setValue("valor del atributo");
            elemento1.setAttributeNode(attr);
            Element elemento2 = doc.createElement("elemento2");
            elemento2.setTextContent("Contenido del elemento 2");
            rootElement.appendChild(elemento2);
            
            //Se escribe el contenido del XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("./prueba.xml"));
            transformer.transform(source, result);
            
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

}
