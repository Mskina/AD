/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad01actividad5;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Iván Estévez Sabucedo
 */
public class PersonaHandler extends DefaultHandler {

    private ArrayList<Persona> personas = new ArrayList();
    private Persona persona;
    private StringBuilder sb = new StringBuilder();

    private ArrayList<Persona> personasNS = new ArrayList();
    private ArrayList<Persona> personasNSA = new ArrayList();

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        sb.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
//            case "personas":
//                break;
            case "persona":
                personas.add(persona);
                break;
            case "nombre":
                persona.setNombre(sb.toString());
                break;
            case "dni":
                persona.setDni(sb.toString());
                break;
            case "edad":
                persona.setEdad(Integer.parseInt(sb.toString()));
                break;
            case "salario":
                persona.setSalario(Float.parseFloat(sb.toString()));
                break;
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
//            case "personas":
//                break;
            case "persona":
                persona = new Persona();
                persona.setId(Long.parseLong(attributes.getValue("id")));
                persona.setBorrado(Boolean.parseBoolean(attributes.getValue("borrado")));
                break;
            case "nombre":
                sb.delete(0, sb.length());
                break;
            case "dni":
                sb.delete(0, sb.length());
                break;
            case "edad":
                sb.delete(0, sb.length());
                break;
            case "salario":
                sb.delete(0, sb.length());
                break;
        }

    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        super.endPrefixMapping(prefix); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        super.startPrefixMapping(prefix, uri); //To change body of generated methods, choose Tools | Templates.
    }
}
