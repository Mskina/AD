/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad.teis.model;

import ad.teis.persistencia.DataIOPersistencia;
import ad.teis.persistencia.IPersistencia;

/**
 *
 * @author Iván Estévez Sabucedo
 */
public class Main {

    public static void main(String[] args) {

        long id = 12345;
        String dni = "44477431P";
        String nombre = "Iván Estévez Sabucedo";
        byte edad = 35;
        float salario = 123456;

        String RUTA = "D:\\GitHub\\AD\\AD01\\AD01Actividad3\\archivo.dat";

        IPersistencia diop = new DataIOPersistencia();

        Persona p1 = new Persona(id, dni, nombre, edad, salario);

        diop.escribirPersona(p1, RUTA);
        
        Persona p1b = diop.leerDatos(RUTA);
        System.out.println(p1b.getNombre());
        

    }

}
