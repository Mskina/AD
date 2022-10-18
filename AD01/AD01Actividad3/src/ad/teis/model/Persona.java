/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad.teis.model;

import java.io.Serializable;

/**
 *
 * @author Iván Estévez Sabucedo
 */
public class Persona implements Serializable {

    private long id;
    private String dni;
    private String nombre;
    private byte edad;
    private float salario;
    private boolean borrado;
    
    public Persona(long id, String dni, String nombre, byte edad, float salario) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
        this.salario = salario;
    }
    
    public Persona(long id, String dni, String nombre, byte edad, float salario, boolean borrado) {
        this(id, dni, nombre, edad, salario);
        this.borrado = borrado;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the edad
     */
    public byte getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(byte edad) {
        this.edad = edad;
    }

    /**
     * @return the salario
     */
    public float getSalario() {
        return salario;
    }

    /**
     * @param salario the salario to set
     */
    public void setSalario(float salario) {
        this.salario = salario;
    }

    /**
     * @return the borrado
     */
    public boolean isBorrado() {
        return borrado;
    }

    /**
     * @param borrado the borrado to set
     */
    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

}
