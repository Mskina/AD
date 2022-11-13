/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.udc.teis.plataforma;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author maria
 */
public class EjemploByteArrayInputStream {

    /*Sección 6.4.2  Ejemplo "completo" */
    public static void main(String[] args) {
        try {
            StringBuilder xmlStringBuilder = new StringBuilder();
            //Se añadiría el documento XML en formato string
            xmlStringBuilder.append("<?xml version='1.0'?> <clase><alumno numero = '393'>" +
                    "      <nombre> Luis </nombre>\n" +
                    "      <apellido> Luna </apellido>\n" +
                    "      <apodo> Na </apodo>\n" +
                    "      <marcas> 85 </marcas>\n" +
                    "   </alumno> </clase>");
            
            //Se crea un stream de bytes de lectura a partir de esa representación del documento como cadena de texto
            ByteArrayInputStream input = new ByteArrayInputStream(
                    xmlStringBuilder.toString().getBytes("UTF-8"));
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(input);
            
            Element root = doc.getDocumentElement();
            //Podría seguir procesándose el documento como un documento DOM como en la clase 
            //EjemploDOMLeer...
            
            System.out.println("Root element: " + root.getTagName());
        } catch (SAXException ex) {
           ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }
    }

}
