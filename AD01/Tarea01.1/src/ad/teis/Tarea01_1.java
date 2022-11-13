/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ad.teis;

import ad.teis.model.Persona;
import ad.teis.persistencia.RandomAccessPersistencia;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;

/**
 *
 * @author Iván Estévez Sabucedo
 */
public class Tarea01_1 {

    public static final String PERSONAS_FILE = Paths.get("src", "docs", "personasConBorrados.dat").toString();
    private static final String PERSONAS_FILE_BK = Paths.get("src", "docs", "personasConBorrados.dat.bk").toString();
    private static final String PERSONAS_FILE_DESTINO = Paths.get("src", "docs",
            "destinoRandom.dat.").toString();

    /**
     * Implementación de Tarea01.1 de Accesso a datos
     */
    private static void cribarBorrados() {

        RandomAccessPersistencia random = new RandomAccessPersistencia();

        // Si PERSONAS_FILE no existe, obtenemos la copia de seguridad PERSONAS_FILE_BK
        if (!Files.exists(Paths.get(PERSONAS_FILE))) {
            try {
                Files.copy(Paths.get(PERSONAS_FILE_BK), Paths.get(PERSONAS_FILE));
            } catch (IOException ex) {
                //ex.printStackTrace();
                System.out.println(""
                        + ">> ERROR \n"
                        + ">>> Proceso: restauración de la copia de seguridad \n"
                        + ">>> +info: " + ex.getMessage());
            }
        }

        // Si existe, creamos una copia de seguridad en PERSONAS_FILE_BK
        // REPLACE_EXISTING sobreescribe el fichero de destino si existiese
        try {
            Files.copy(Paths.get(PERSONAS_FILE), Paths.get(PERSONAS_FILE_BK), REPLACE_EXISTING);
        } catch (IOException ex) {
            //ex.printStackTrace();
            System.out.println(""
                    + ">> ERROR \n"
                    + ">>> Proceso: creación de la copia de seguridad \n"
                    + ">>> +info: " + ex.getMessage());
        }

        // Leemos el contenido de PERSONAS_FILE
        ArrayList<Persona> personasTotal = new ArrayList<>();
        personasTotal = random.leerTodo(PERSONAS_FILE);

        // Creamos un array para las que no están marcadas como borradas
        ArrayList<Persona> personasNoBorradas = new ArrayList<>();

        for (Persona p : personasTotal) {
            // Si no está borrada, se añade al array
            if (!p.isBorrado()) {
                personasNoBorradas.add(p);
            }
        }
        
        // Eliminamos PERSONAS_FILE_DESTINO si existe        
        try {
            Files.delete(Paths.get(PERSONAS_FILE_DESTINO));
        } catch (IOException ex) {
            //ex.printStackTrace();
            System.out.println(""
                    + ">> ERROR \n"
                    + ">>> Proceso: eliminación del fichero ya cribado \n"
                    + ">>> +info: " + ex.getMessage());
        }

        // Escribimos en PERSONAS_FILE_DESTINO las personas no borradas
        random.escribirPersonas(personasNoBorradas, PERSONAS_FILE_DESTINO);

        // Eliminamos PERSONAS_FILE
        try {
            Files.delete(Paths.get(PERSONAS_FILE));
        } catch (IOException ex) {
            //ex.printStackTrace();
            System.out.println(""
                    + ">> ERROR \n"
                    + ">>> Proceso: eliminación del fichero original \n"
                    + ">>> +info: " + ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ArrayList<Persona> personasRecuperadas = new ArrayList<>();
        RandomAccessPersistencia random = new RandomAccessPersistencia();

        cribarBorrados();
        personasRecuperadas = random.leerTodo(PERSONAS_FILE_DESTINO);
        int contador = 1;
        for (Persona p : personasRecuperadas) {
            System.out.println("Persona recuperada " + contador + ": " + p);
            contador++;
        }

    }

}
