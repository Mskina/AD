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

    final int TAMANHO_PERSONA = 232;
    final int INCREMENTO_HASTA_SALARIO = 227;
    final int INCREMENTO_HASTA_BORRADO = 231;

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

            // Boolean: 1 byte
            raf.writeBoolean(persona.isBorrado());

            // 8 + 18 + 200 + 1 + 4 +1 = 232
            System.out.println("Escritura correcta.");
        } catch (FileNotFoundException ex) {
            System.out.println(">> ERROR escribirPersona");
        } catch (IOException ex) {
            System.out.println(">> ERROR escribirPersona");
        }
    }

    @Override
    public Persona leerDatos(String ruta) {
        Persona persona = null;
        try (RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) {
            raf.seek(0); // Tendríamos que cambiarlo si quisiésemos acceder a otra posición
            long id = raf.readLong();
//            String dni = "";
//            for (int i = 0; i < 9; i++) {
//                dni += raf.readChar();
//            }
//            String nombre = "";
//            for (int i = 0; i < 100; i++) {
//                nombre += raf.readChar();
//            }
            // Uso el método de la profesora, que emplea StringBuilder
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 9; i++) {
                sb.append(raf.readChar());
            }
            String dni = sb.toString();
            sb = new StringBuilder();
            for (int i = 0; i < 100; i++) {
                sb.append(raf.readChar());
            }
            String nombre = sb.toString();

            byte edad = raf.readByte();
            float salario = raf.readFloat();
            boolean borrado = raf.readBoolean();
            persona = new Persona(id, dni, nombre, edad, salario, borrado);
        } catch (FileNotFoundException ex) {
            System.out.println(">> ERROR leerDatos");
        } catch (IOException ex) {
            System.out.println(">> ERROR leerDatos");
        }
        return persona;
    }

    /**
     * Debe escribir los datos de todas las personas en el mismo orden que
     * escribirPersona. Si el fichero tiene datos, debe comenzar a escribir
     * desde el final y no sobreescribir todo el fichero. Prueba desde el Main
     * el método.
     *
     * @param personas
     * @param ruta
     */
    public void escribirPersonas(ArrayList<Persona> personas, String ruta) {
        try (
                RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) {
            raf.seek(raf.length());
            for (Persona p : personas) {
                raf.writeLong(p.getId());
                StringBuilder sb = new StringBuilder(p.getDni());
                sb.setLength(9);
                raf.writeChars(sb.toString());
                sb = new StringBuilder(p.getNombre());
                sb.setLength(100);
                raf.writeChars(sb.toString());
                raf.writeByte(p.getEdad());
                raf.writeFloat(p.getSalario());
                raf.writeBoolean(p.isBorrado());
            }
        } catch (FileNotFoundException ex) {
            System.out.println(">> ERROR escribirPersonas");
        } catch (IOException ex) {
            System.out.println(">> ERROR escribirPersonas");
        }
    }

    /**
     * Debe leer un fichero desde el principio y construir un ArrayList de
     * Persona. Prueba desde el Main el método.
     *
     * @param ruta
     * @return
     */
    public ArrayList<Persona> leerTodo(String ruta) {
        ArrayList<Persona> personas = new ArrayList<Persona>();

        try (
                RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) {
            raf.seek(0);

            while (raf.getFilePointer() < raf.length()) {
                long id = raf.readLong();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 9; i++) {
                    sb.append(raf.readChar());
                }
                String dni = sb.toString();
                sb = new StringBuilder();
                for (int i = 0; i < 100; i++) {
                    sb.append(raf.readChar());
                }
                String nombre = sb.toString();

                byte edad = raf.readByte();
                float salario = raf.readFloat();
                boolean borrado = raf.readBoolean();
                personas.add(new Persona(id, dni, nombre, edad, salario, borrado));
            }
        } catch (FileNotFoundException ex) {
            System.out.println(">> ERROR leerTodo");
        } catch (IOException ex) {
            System.out.println(">> ERROR leerTodo");
        }
        return personas;
    }

    /**
     * Debe posicionarse en la persona que ocupa la posición indicada por el
     * parámetro posicion y devolver un objeto Persona con los datos
     * correspondientes. Si posicion = 1, el puntero del fichero deberá situarse
     * en el byte 0, si posicion =2, el puntero debe situarse en el byte= número
     * de bytes que ocupa cada persona, etc. Prueba desde el Main el método.
     *
     * @param posicion
     * @param ruta
     * @return
     */
    public Persona leerPersona(int posicion, String ruta) {
        Persona persona = null;
        long posLong = (posicion - 1) * TAMANHO_PERSONA;
        try (
                RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) {
            raf.seek(posLong);
            long id = raf.readLong();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 9; i++) {
                sb.append(raf.readChar());
            }
            String dni = sb.toString();
            sb = new StringBuilder();
            for (int i = 0; i < 100; i++) {
                sb.append(raf.readChar());
            }
            String nombre = sb.toString();

            byte edad = raf.readByte();
            float salario = raf.readFloat();
            boolean borrado = raf.readBoolean();
            persona = new Persona(id, dni, nombre, edad, salario, borrado);
        } catch (FileNotFoundException ex) {
            System.out.println(">> ERROR leerPersona");
        } catch (IOException ex) {
            System.out.println(">> ERROR leerPersona");
        }
        return persona;
    }

    /**
     * Debe añadir un objeto Persona en la posición indicada. La posicion =1,
     * indica posicionarse en el byte cero, etc. Si ya existía una persona en
     * esa posición, se sobrescribirá. Prueba desde el Main el método
     *
     * @param posicion
     * @param ruta
     * @param persona
     * @return
     */
    public Persona add(int posicion, String ruta, Persona persona) {
        long posLong = (posicion - 1) * TAMANHO_PERSONA;

        try (
                RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) {
            raf.seek(posLong);
            raf.writeLong(persona.getId());
            StringBuilder sb = new StringBuilder(persona.getDni());
            sb.setLength(9);
            raf.writeChars(sb.toString());
            sb = new StringBuilder(persona.getNombre());
            sb.setLength(100);
            raf.writeChars(sb.toString());
            raf.writeByte(persona.getEdad());
            raf.writeFloat(persona.getSalario());
            raf.writeBoolean(persona.isBorrado());
        } catch (FileNotFoundException ex) {
            System.out.println(">> ERROR add");
        } catch (IOException ex) {
            System.out.println(">> ERROR add");
        }
        return persona; // Ida y vuelta?
    }

    /**
     * Debe situarse en la Persona que ocupa la posicion indicada (mismas
     * consideraciones que en los apartados anteriores con posición), leer su
     * salario e incrementarlo en la cantidad incremento. Devuelve el nuevo
     * salario. Prueba desde el Main el método.
     *
     * @param posicion
     * @param ruta
     * @param incremento
     * @return
     */
    public float sumarSalario(int posicion, String ruta, float incremento) {
        float salario = 0;
        long posLong = ((posicion - 1) * TAMANHO_PERSONA) + INCREMENTO_HASTA_SALARIO;
        try (
                RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) {
            raf.seek(posLong);
            salario = raf.readFloat() + incremento;
            // Volvemos a posicion para escribir salario actualizado (salario + incremento)
            raf.seek(posLong);
            raf.writeFloat(salario);
        } catch (FileNotFoundException ex) {
            System.out.println(">> ERROR sumarSalario");
        } catch (IOException ex) {
            System.out.println(">> ERROR sumarSalario");
        }
        return salario;
    }

    /**
     * En lugar de borrar un registro físicamente, vamos a marcarlo como borrado
     * con un campo booleano indicando si está borrado o no. Para ello,
     * tendremos que añadir un atributo borrado a la clase Persona y modificar
     * los métodos que hemos creado para escribir y leer ese campo. Lo
     * guardaremos en último lugar, tras el salario. Tendremos que tener en
     * cuenta que se modifica la longitud en bytes del registro de una objeto
     * Persona. El método borrar buscará en el fichero indicado por ruta, la
     * Persona en la posición indicada en el parámetro de entrada y establecerá
     * el valor borrado en el fichero. Prueba desde el Main el método.
     *
     * @param posicion
     * @param ruta
     * @param borrado
     * @return <code>true</code> si se hace el cambio y <code>false</code> en
     * caso contrario
     */
    public boolean marcarBorrado(int posicion, String ruta, boolean borrado) {
        boolean realizado = false;
        long posLong = ((posicion - 1) * TAMANHO_PERSONA) + INCREMENTO_HASTA_BORRADO;
        try (
                RandomAccessFile raf = new RandomAccessFile(ruta, "rw")) {
            raf.seek(posLong);
            raf.writeBoolean(borrado);
            realizado = true;
        } catch (FileNotFoundException ex) {
            System.out.println(">> ERROR marcarBorrado" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println(">> ERROR marcarBorrado" + ex.getMessage());
        }
        return realizado;
    }

}
