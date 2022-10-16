/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad01ficheroscaracteres;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Iván Estévez Sabucedo
 */
public class Config {

    /**
     * Crea un método que:
     *
     * - Reciba un mapa de cadenas de texto en formato clave-valor ------------
     * - Cree un fichero basado en caracteres en la rutaFichero ---------------
     * - Por cada par clave-valor, debe añadir una nueva línea en el fichero con
     * el formato clave=valor
     *
     * @param mapa
     * @param rutaFichero
     */
    public void crearConfigFile(HashMap<String, String> mapa, String rutaFichero) {
        File arquivo = new File(rutaFichero);
        try (
                FileWriter fw = new FileWriter(arquivo);
                BufferedWriter bw = new BufferedWriter(fw);) {
            for (String clave : mapa.keySet()) {
                bw.write(String.format("%s=%s\n", clave, mapa.get(clave)));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Crea un método que lea el fichero de configuración app.config y encuentre
     * el valor de la clave dada por parámetro. Si la encuentra, devuelve el
     * valor. Si no, devuelve null.
     *
     * @param rutaFichero
     * @param clave
     * @return
     */
    public String leerConfig(String rutaFichero, String clave) {
        String valor = null;
        File arquivo = new File(rutaFichero);
        if (arquivo.exists()) {
            
            String line;
            HashMap<String, String> mapa = new HashMap<String, String>();

            try (
                    FileReader fr = new FileReader(arquivo);
                    BufferedReader br = new BufferedReader(fr);) {
                while ((line = br.readLine()) != null) {
                    String[] parClaveValor = line.split("=", 2);
                    String key = parClaveValor[0];
                    String value = parClaveValor[1];
                    mapa.put(key, value);
                }
                valor = mapa.get(clave);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return valor;
    }
}
