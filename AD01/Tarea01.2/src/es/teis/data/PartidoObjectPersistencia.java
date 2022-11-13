/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.teis.data;

import es.teis.model.Partido;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Crear una clase PartidoObjectPersistencia que implemente IPersistencia con
 * ObjectInputStream y ObjectOutputStream. El método escribir, sobrescribirá el
 * fichero desde el comienzo sea cual sea su contenido. En el método leerTodo,
 * controla EOFException para detectar el fin del fichero y salir del bucle de
 * lectura.
 *
 * @author Iván Estévez Sabucedo
 */
public class PartidoObjectPersistencia implements IPersistencia {

    @Override
    public void escribir(ArrayList<Partido> partidos, String ruta) {
        try (
                FileOutputStream fos = new FileOutputStream(ruta);
                ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            for (Partido p : partidos) {
                oos.writeObject(p);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(">> ERROR: No se ha encontrado el archivo");
        } catch (IOException ex) {
            System.out.println(">> ERROR " + ex.getMessage());
        }
    }

    @Override
    public ArrayList<Partido> leerTodo(String ruta) {
        ArrayList<Partido> partidos = new ArrayList<Partido>();
        try (
                FileInputStream fos = new FileInputStream(ruta);
                ObjectInputStream ois = new ObjectInputStream(fos);) {
            while (true) {
                partidos.add((Partido) ois.readObject());
            }

        } catch (EOFException e) {
            // Tal y como indica el enunciado, se captura la excepción.
            // Llegamos al final del fichero y salimos del bucle.
        } catch (FileNotFoundException ex) {
            System.out.println(">> ERROR: No se ha encontrado el archivo");
        } catch (IOException ex) {
            System.out.println(">> ERROR " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(">> ERROR: No se ha encontrado la clase");
        }
        return partidos;
    }

}
