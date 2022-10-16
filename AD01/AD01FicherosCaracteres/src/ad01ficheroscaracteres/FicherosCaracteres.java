/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad01ficheroscaracteres;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Iván Estévez Sabucedo
 */
public class FicherosCaracteres {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final String rutaFichero = ".\\app.config";
        Config c = new Config();

        File arquivo = new File(rutaFichero);

        if (!arquivo.exists()) {
            HashMap<String, String> mapa = new HashMap<String, String>();
            mapa.put("start", "true");
            mapa.put("persistencia", "true");
            mapa.put("max_franjas", "4");
            c.crearConfigFile(mapa, rutaFichero);
        }

        if (arquivo.exists()) {
            Scanner scan = new Scanner(System.in);
            System.out.print("Escribe unha chave (key): ");
            String entrada = scan.nextLine();
            while (!entrada.equals("exit")) {
                System.out.printf(">> O valor de %s é %s\n", entrada, c.leerConfig(rutaFichero, entrada));
                System.out.print("Outra: ");
                entrada = scan.nextLine();
            }
            System.out.println("Deica!");
        }
    }
}
