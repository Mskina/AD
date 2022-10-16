/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaficheros;

import java.io.File;

/**
 *
 * @author mskin
 */
public class PruebaFicheros {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
        try {
            // Creamos el objeto que encapsula el fichero
            File fichero = new File("miFichero.txt");

            // A partir del objeto File creamos el fichero físicamente
            if (fichero.createNewFile()) {
                System.out.println("El fichero se ha creado correctamente");
            } else {
                System.out.println("No ha podido ser creado el fichero");
            }
        } catch (Exception ioe) {
            System.out.println(ioe.getMessage());
        }

        //Pausa de 5 segundos para ver cómo aparece y desaparece el archivo
        Thread.sleep(5000);

        // Borrar fichero
        File fichero = new File("miFichero.txt");
        if (fichero.exists()) {
            fichero.delete();
            System.out.println("Archivo borrado");
        }

        try {
            // Declaración de variables
            String directorio = "prueba";
            String varios = "carpeta1/carpeta2/carpeta3";

            // Crear un directorio
            boolean exito = (new File(directorio)).mkdir();
            if (exito) {
                System.out.println("Directorio: " + directorio + " creado");
            }

            // Crear varios directorios
            exito = (new File(varios)).mkdirs();
            if (exito) {
                System.out.println("Directorios: " + varios + " creados");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        //Pausa para visualizar el directorio
        Thread.sleep(5000);

        //Borrar directorio, en este caso carpeta1 e inferiores
        File directorioRecursivo = new File("carpeta1");
        File directorioUnico = new File ("prueba");

        // FileUtils non aparece para importar...
        // Borrado: https://es.stackoverflow.com/a/494701/259921
        deleteFile(directorioRecursivo);
        deleteFile(directorioUnico);

    }

    /**
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else {
                File f[] = file.listFiles();
                for (int i = 0; i < f.length; i++) {
                    deleteFile(f[i]);
                }
                file.delete();
            }
        } else {
            System.out.println("El archivo no existe!");
        }
    }
}
