/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad.teis.model;

import ad.teis.persistencia.DataIOPersistencia;
import ad.teis.persistencia.IPersistencia;
import ad.teis.persistencia.ObjectPersistencia;
import ad.teis.persistencia.RandomAccessPersistencia;

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
        float salario = 1234.56f;

        Persona p = new Persona(id, dni, nombre, edad, salario);
        IPersistencia persistencia;

        // Escritura usando DataIOPersistencia
        String rutaDataIO = "D:\\GitHub\\AD\\AD01\\AD01Actividad3\\DataIO.dat";
        System.out.println("-- Escritura usando DataIOPersistencia -- ");
        persistencia = new DataIOPersistencia();
        persistencia.escribirPersona(p, rutaDataIO);

        // Lectura usando DataIOPersistencia 
        System.out.println("-- Lectura usando DataIOPersistencia -- ");
        Persona p1 = persistencia.leerDatos(rutaDataIO);
        printSaida(p1);

        // Escritura usando RandomAccessPersistencia
        String rutaRandomAccess = "D:\\GitHub\\AD\\AD01\\AD01Actividad3\\RandomAccess.dat";
        System.out.println("-- Escritura usando RandomAccessPersistencia -- ");
        persistencia = new RandomAccessPersistencia();
        persistencia.escribirPersona(p, rutaRandomAccess);

        // Lectura usando RandomAccessPersistencia
        System.out.println("-- Lectura usando RandomAccessPersistencia -- ");
        Persona p2 = persistencia.leerDatos(rutaRandomAccess);
        printSaida(p2);
        
        // Escritura usando ObjectPersistencia
        String rutaObject = "D:\\GitHub\\AD\\AD01\\AD01Actividad3\\Object.dat";
        System.out.println("-- Escritura usando Object -- ");
        persistencia = new ObjectPersistencia();
        persistencia.escribirPersona(p, rutaObject);

        // Lectura usando ObjectPersistencia
        System.out.println("-- Lectura usando ObjectPersistencia -- ");
        Persona p3 = persistencia.leerDatos(rutaObject);
        printSaida(p3);
    }
    
    /**
     * 
     * @param p persona para imprimir
     */
    static void printSaida(Persona p) {
        System.out.println("ID: " + p.getId());
        System.out.println("DNI: " + p.getDni());
        System.out.println("NOMBRE: " + p.getNombre());
        System.out.println("EDAD: " + p.getEdad());
        System.out.println("SALARIO: " + p.getSalario());
    }
}
