/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad.teis.persistencia;

import ad.teis.model.Persona;

/**
 *
 * @author Iván Estévez Sabucedo
 */
public interface IPersistencia {

    /**
     * Escribe los atributos de Persona, de uno en uno, en un fichero
     * establecido por ruta.
     *
     * @param persona objeto persona que queremos guardar
     * @param ruta en la que creamos el fichero
     */
    void escribirPersona(Persona persona, String ruta);

    /**
     * Lee del fichero ruta, los atributos de Persona, de uno en uno, en el
     * mismo orden que se han escrito. Con ellos, crea un objeto de tipo
     * Persona.
     *
     * @param ruta en la que se encuentra el fichero
     * @return objeto Persona
     */
    Persona leerDatos(String ruta);
}
