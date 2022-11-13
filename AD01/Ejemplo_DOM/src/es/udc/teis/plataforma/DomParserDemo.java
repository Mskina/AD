/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.udc.teis.plataforma;

import java.io.File;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 *
 * @author maria
 */
public class DomParserDemo {

    public static void main(String[] args) {
        try {
            File inputFile = new File(Paths.get("src", "docs", "clase.xml").toString());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :"
                    + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("alumno");
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("numero de alumno : " + eElement.getAttribute("numero"));
                    System.out.println("nombre : " + eElement.getElementsByTagName("nombre").item(0).getTextContent());
                    System.out.println("apellido :" + eElement.getElementsByTagName("apellido").item(0).getTextContent());
                    System.out.println("apodo : " + eElement.getElementsByTagName("apodo").item(0).getTextContent());
                    System.out.println("marcas : " + eElement.getElementsByTagName("marcas").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
