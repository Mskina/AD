/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad.teis.persistencia;

import ad.teis.model.Persona;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Crea una clase RandomAccessPersistencia que implemente la interfaz
 * IPersistencia con RandomAccessFile. Podéis ayudaros de este recurso:
 * https://www.discoduroderoer.es/como-leer-y-escribir-un-fichero-con-randomaccessfile/
 *
 * @author Iván Estévez Sabucedo
 */
public class RandomAccessPersistencia implements IPersistencia {

    @Override
    public void escribirPersona(Persona persona, String ruta) {
        try (RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) {
            // Long: 8 bytes
            raf.writeLong(persona.getId());

            // String: 2 bytes por cada caracter.
            StringBuffer sb1 = new StringBuffer(persona.getDni());
            sb1.setLength(9); // Un dni con 8 números y un carácter [A-Z] 
            raf.writeChars(sb1.toString());

            StringBuffer sb2 = new StringBuffer(persona.getNombre());
            sb2.setLength(100);
            raf.writeChars(sb2.toString());

            // Byte: 1 byte
            raf.writeByte(persona.getEdad());

            // Float: 4 bytes
            raf.writeFloat(persona.getSalario());

            // 8 + 18 + 200 + 1 + 4 = 231
            System.out.println("Escritura correcta.");
        } catch (FileNotFoundException ex) {
            System.out.println(">> ERROR RandomAccess: No se ha encontrado el archivo");
        } catch (IOException ex) {
            System.out.println(">> ERROR RandomAccess Escritura");
        }
    }

    @Override
    public Persona leerDatos(String ruta) {
        Persona persona = null;
        try (RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) {
            raf.seek(0); // Tendríamos que cambiarlo si quisiésemos acceder a otra posición
            long id = raf.readLong();
            String dni = "";
            for (int i = 0; i < 9; i++) {
                dni += raf.readChar();
            }
            String nombre = "";
            for (int i = 0; i < 100; i++) {
                nombre += raf.readChar();
            }
            byte edad = raf.readByte();
            float salario = raf.readFloat();
            persona = new Persona(id, dni, nombre, edad, salario);
        } catch (FileNotFoundException ex) {
            System.out.println(">> ERROR RandomAccess: No se ha encontrado el archivo");
        } catch (IOException ex) {
            System.out.println(">> ERROR RandomAccess Lectura");
        }
        return persona;
    }

}
