/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.udc.teis.plataforma;

import es.udc.teis.model.Version;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author maria
 */
public class EjemploDOMCreate {

    private static final String VERSIONES_TAG = "versiones";
    private static final String VERSION_TAG = "version";
    private static final String VERSION_NOMBRE_TAG = "nombre";
    private static final String VERSION_API_TAG = "api";
    private static final String VERSION_ATT_NUMERO = "numero";

    private static final String VERSIONES_OUTPUT_FILE = Paths.get("src", "docs", "versiones_output_XmlCtrlDom.xml").toString();
    //  private static final String VERSIONES_DTD_FILE = "versiones.dtd";

    public static void main(String[] args) {
        try {

            ArrayList<Version> versions = crearVersions();


            /*
            Ejemplo de cómo se podría utilizar la clase XmlCtrlDom de la plataforma a distancia
             */
            Document document = XmlCtrlDom.instanciarDocument();
            
            
            Element root = document.createElement(VERSIONES_TAG);
            document.appendChild(root);

            for (Version version : versions) {
                //desde el document creamos un nuevo elemento
                Element eVersion = document.createElement(VERSION_TAG);
                eVersion.setAttribute(VERSION_ATT_NUMERO, String.valueOf(version.getNumero()));

                XmlCtrlDom.addElementConTexto(document, eVersion, VERSION_NOMBRE_TAG, version.getNombre());
                XmlCtrlDom.addElementConTexto(document, eVersion, VERSION_API_TAG, String.valueOf(version.getApi()));

                root.appendChild(eVersion);
            }

            File destino = new File(VERSIONES_OUTPUT_FILE);

            XmlCtrlDom.escribirDocumentATextXml(document, destino);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            System.err.println("Ha ocurrido una exception: " + e.getMessage());
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            System.err.println("Ha ocurrido una exception: " + e.getMessage());
        } catch (TransformerException e) {
            e.printStackTrace();
            System.err.println("Ha ocurrido una exception: " + e.getMessage());
        }

    }

    private static ArrayList<Version> crearVersions() {
        ArrayList<Version> versions = new ArrayList<>();

        Version versionA = new Version(1, "nombreA", 2);

        Version versionB = new Version(1.5, "nombreB", 3);
        Version versionC = new Version(2, "nombreC", 4);
        versions.add(versionA);
        versions.add(versionB);
        versions.add(versionC);

        return versions;
    }

}
