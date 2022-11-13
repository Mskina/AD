/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package es.teis.edu;

import es.teis.data.IPersistencia;
import es.teis.data.PartidoObjectPersistencia;
import es.teis.data.exceptions.LecturaException;
import es.teis.dataXML.DOMXMLService;
import es.teis.dataXML.IXMLService;
import es.teis.model.Partido;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Se encarga de crear objetos de tipo IXMLService para leer el documento XML y
 * extraer un ArrayList de partidos. A continuación, deberá crear un objeto que
 * implemente IPersistencia y crear un archivo con ObjectOutputStream.
 * Finalmente, deberá leer los datos escritos en el fichero de salida.
 *
 * @author Iván Estévez Sabucedo
 */
public class Main {

    private static String ELECCIONES_INPUT_FILE = Paths.get("src", "docs", "elecciones.xml").toString();
    private static String ELECCIONES_OUTPUT_FILE = Paths.get("src", "docs", "elecciones_output.dat").toString();

    private static float UMBRAL_PORCENTAJE = 3;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Crea un objeto IXMLService, lee el documento XML y extrae un ArrayList de partidos.
        IXMLService xmlService = new DOMXMLService();
        ArrayList<Partido> partidos = null;

        try {
            partidos = xmlService.leerPartidos(ELECCIONES_INPUT_FILE, UMBRAL_PORCENTAJE);
        } catch (LecturaException e) {
            System.out.println("Se ha encontrado una excepción en el archivo " + e.getRutaFichero());
            System.exit(-1);
        }

        // Crea un objeto IPersistencia y lee el .dat generado.
        IPersistencia persistencia = new PartidoObjectPersistencia();
        persistencia.escribir(partidos, ELECCIONES_OUTPUT_FILE);

        // Muestra por consola el archivo leido.
        mostrar(persistencia.leerTodo(ELECCIONES_OUTPUT_FILE));
    }

    private static void mostrar(ArrayList<Partido> partidos) {
        System.out.println("Se han leído: ");
        for (Partido partido : partidos) {
            System.out.println(partido);
        }
    }

}
