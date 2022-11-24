/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad.teis.model;

import ad.teis.persistencia.RandomAccessPersistencia;
import java.util.ArrayList;

/**
 *
 * @author Iván Estévez Sabucedo
 */
public class Main {

    /**
     * Main para 1.4
     *
     * @param args
     */
    public static void main(String[] args) {

        ArrayList<Persona> personas = new ArrayList<>();
        String RUTA = "arrayList.dat";

        Persona p1 = new Persona(1, "44477431P", "Iván", (byte) 35, 123456f);
        Persona p2 = new Persona(2, "34940218K", "Sabela", (byte) 61, 123456f);
        Persona p3 = new Persona(3, "76697169N", "JR", (byte) 70, 123456f);
        Persona p4 = new Persona(4, "   Michi ", "Bandi", (byte) 1, 0.01f);

        personas.add(p1);
        personas.add(p2);
        personas.add(p3);
        personas.add(p4);

        RandomAccessPersistencia random = new RandomAccessPersistencia();
        //random.escribirPersonas(personas, RUTA);

//        ArrayList<Persona> personasLeidas = random.leerTodo(RUTA);
//        for (Persona p : personasLeidas) {
//            printSaida(p);
//            System.out.println("\n**************");
//        }
//        Persona personaLeida = random.leerPersona(4, RUTA);
//        printSaida(personaLeida);
//        random.add(9, RUTA, p4);
//        
//        ArrayList<Persona> personasLeidas = random.leerTodo(RUTA);
//        for (Persona p : personasLeidas) {
//            printSaida(p);
//            System.out.println("\n**************");
//        }
        //random.sumarSalario(3, RUTA, 0.02f);
        random.marcarBorrado(1, RUTA, true);
        //System.out.println(random.leerPersona(1, RUTA).isBorrado());

        ArrayList<Persona> personasLeidas = random.leerTodo(RUTA);
        for (Persona p : personasLeidas) {
            printSaida(p);
            System.out.println("\n**************");
        }

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
        System.out.println("BORRADO: " + p.isBorrado());
    }
}

/*
    // Main para 1.3
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
    }*/
