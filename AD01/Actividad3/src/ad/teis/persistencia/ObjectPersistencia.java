/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad.teis.persistencia;

import ad.teis.model.Persona;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Crea una clase ObjectPersistencia que implemente la interfaz Persistencia
 * usando ObjectInputStream y ObjectOutputStream.
 *
 * https://www.discoduroderoer.es/serializacion-de-objetos-en-java/
 *
 * @author Iván Estévez Sabucedo
 */
public class ObjectPersistencia implements IPersistencia {

    @Override
    public void escribirPersona(Persona persona, String ruta) {
        try (
                FileOutputStream fos = new FileOutputStream(ruta);
                ObjectOutputStream oos = new ObjectOutputStream(fos);) {

            oos.writeObject(persona);
            System.out.println("Escritura correcta.");
        } catch (FileNotFoundException ex) {
            System.out.println(">> ERROR Object: No se ha encontrado el archivo");
        } catch (IOException ex) {
            System.out.println(">> ERROR Object Escritura");
        }
    }

    @Override
    public Persona leerDatos(String ruta) {
        Persona persona = null;
        try (
                FileInputStream fos = new FileInputStream(ruta);
                ObjectInputStream oos = new ObjectInputStream(fos);) {

            persona = (Persona)oos.readObject();
        } catch (FileNotFoundException ex) {
            System.out.println(">> ERROR Object: No se ha encontrado el archivo");
        } catch (IOException ex) {
            System.out.println(">> ERROR Object Lectura");
        } catch (ClassNotFoundException ex) {
            System.out.println(">> ERROR Object: Error al crear la persona");;
        }
        return persona;
    }

}
