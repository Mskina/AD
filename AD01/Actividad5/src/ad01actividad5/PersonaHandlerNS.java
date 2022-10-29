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
public class PersonaHandlerNS extends DefaultHandler {

    private final String DEFAULT_URI = "http://www.personas.com";
    private final String ACTIVE_URI = "http://www.personas.com/active";

    private Persona persona;
    private StringBuilder sb = new StringBuilder();

    private ArrayList<Persona> personasNS = new ArrayList();
    private ArrayList<Persona> personasNSA = new ArrayList();

    public ArrayList<Persona> getPersonasNS() {
        return personasNS;
    }

    public ArrayList<Persona> getPersonasNSA() {
        return personasNSA;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        sb.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (localName) {
//            case "personas":
//                break;
            case "persona":
                if (uri.equals(ACTIVE_URI)) {
                    personasNSA.add(persona);
                }
                if (uri.equals(DEFAULT_URI)) {
                    personasNS.add(persona);
                }
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
        switch (localName) {
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
}
