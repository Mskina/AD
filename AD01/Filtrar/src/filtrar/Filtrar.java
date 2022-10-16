/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtrar;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author mskin
 */
public class Filtrar implements FilenameFilter {   
    
    String extension;
    
    // Constructor
    Filtrar(String extension) {
        this.extension = extension;
    }

    public boolean accept(File dir, String name) {
        return name.endsWith(extension);
    }

    public static void main(String[] args) {
        
        //Creación de dos ficheros para probar el filtrado
        try {
            File ficheroPrueba1 = new File("ficheroPrueba1.txt");
            File ficheroPrueba2 = new File("ficheroPrueba2.txt");

            if (ficheroPrueba1.createNewFile()) {
                System.out.println("El fichero 1 se ha creado correctamente");
            } else {
                System.out.println("No ha podido ser creado el fichero 1");
            }
            if (ficheroPrueba2.createNewFile()) {
                System.out.println("El fichero 2 se ha creado correctamente");
            } else {
                System.out.println("No ha podido ser creado el fichero 2");
            }
        } catch (Exception ioe) {
            System.out.println(ioe.getMessage());
        }
        
        
        
        
        
        try {
            // Obtendremos el listado de los archivos de ese directorio
            String extensionBuscada = ".xml";
            
            File fichero = new File(".");
            String[] listadeArchivos = fichero.list();
            // Filtraremos por los de extension .txt
            listadeArchivos = fichero.list(new Filtrar(extensionBuscada));
            // Comprobamos el número de archivos en el listado
            int numarchivos = listadeArchivos.length;
            // Si no hay ninguno lo avisamos por consola
            if (numarchivos < 1) {
                System.out.println("No hay archivos que listar");
            } // Y si hay, escribimos su nombre por consola.
            else {
                for (int conta = 0; conta < listadeArchivos.length; conta++) {
                    System.out.println(listadeArchivos[conta]);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error al buscar en la ruta indicada");
        }
    }
}
