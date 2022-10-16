/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad.teis.persistencia;

import ad.teis.model.Persona;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Crea una clase DataIOPersistencia que implemente la interfaz IPersistencia
 * con DataInputStream y DataOutputStream.
 *
 * @author Iván Estévez Sabucedo
 */
public class DataIOPersistencia implements IPersistencia {

    /**
     * Escribe los atributos de Persona, de uno en uno, en un fichero
     * establecido por ruta
     */
    @Override
    public void escribirPersona(Persona persona, String ruta) {
        try (
                FileOutputStream fos = new FileOutputStream(ruta);
                DataOutputStream dos = new DataOutputStream(fos);) {
            dos.writeLong(persona.getId());
            dos.writeUTF(persona.getDni());
            dos.writeUTF(persona.getNombre());
            dos.writeByte(persona.getEdad());
            dos.writeFloat(persona.getSalario());
            System.out.println("Creada");
        } catch (FileNotFoundException ex) {
            System.out.println(">> ERROR: No se ha encontrado el archivo");
        } catch (IOException ex) {
            System.out.println(">> ERROR E/S");
        }
    }

    /**
     * Lee del fichero ruta, los atributos de Persona, de uno en uno, en el
     * mismo orden que se han escrito. Con ellos, crea un objeto de tipo
     * Persona.
     */
    @Override
    public Persona leerDatos(String ruta) {
        Persona persona = null;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(ruta));) {
            long id = dis.readLong();
            String dni = dis.readUTF();
            String nombre = dis.readUTF();
            byte edad = dis.readByte();
            float salario = dis.readFloat();
            persona = new Persona(id, dni, nombre, edad, salario);
        } catch (FileNotFoundException ex) {
            System.out.println(">> ERROR: No se ha encontrado el archivo");
        } catch (IOException ex) {
            System.out.println(">> ERROR E/S");
        }
        return persona;
    }

}
