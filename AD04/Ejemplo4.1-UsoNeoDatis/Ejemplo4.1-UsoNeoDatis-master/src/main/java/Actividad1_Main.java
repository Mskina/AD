/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mrnov
 */
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.OID;
import org.neodatis.odb.Objects;

import clases.Jugadores;
import clases.Paises;

//Principal 
public class Actividad1_Main {// CREACIÓN Y LLENADO DE BD

//Creacion y relleno de las clases en la bd db4o

	public static void main(String[] args) {
		// Instancias de paises para almacenar en la DB:
		Paises pais1 = new Paises(1, "España");
		Paises pais2 = new Paises(2, "Italia");
		Paises pais3 = new Paises(3, "Suiza");
		Paises pais4 = new Paises(4, "EEUU");
		// instancias de jugadores para almacenar en BD
		Jugadores j1 = new Jugadores("Maria", "voleibol", pais1, 14);
		Jugadores j2 = new Jugadores("Miguel", "tenis", pais2, 15);
		Jugadores j3 = new Jugadores("Mario", "baloncesto", pais3, 15);
		Jugadores j4 = new Jugadores("Alicia", "tenis", pais4, 14);
		/*
		 * La conexión la realizo con un objeto de clase ODB, en la que indico la ruta
		 * donde tengo la base de datos. Esto sirve para abrirla como para crear una
		 * nueva
		 */

		// CRUD: create, read, update, delete.

		// 1. Crear el conector con la base de datos
		ODB odb = ODBFactory.open("EQUIPOS.DB");
		// Una nueva o existente:
		// ODB odb = ODBFactory.open("C:\\bds\\EQUIPOS.DB");

		// 2. CRUD: Create (almaceno los objetos en la BD)
		OID object_id = odb.store(j1);
		System.out.println("Se ha almacenado el j1 con OID: " + object_id.getObjectId() + "clase: "
				+ object_id.getClassId() + " type: " + object_id.getType());
		OID object_id2 = odb.store(j2);
		odb.store(j3);
		odb.store(j4);

		// 3. CRUD: Read all (recupero todos los objetos)
		// Genero un conjunto de objetos y los traigo del ODB conectado
		Objects<Jugadores> objectsJug = odb.getObjects(Jugadores.class);

		// Información por consola:
		System.out.println("\nHay " + objectsJug.size() + " Jugadores en la BD:");

		// JUGADORES
		// Se muestran los objetos Jugador
		for (Jugadores jugador : objectsJug) {
			System.out.println("Nombre jugador: " + jugador.getNombre());
		}

//		int i = 1; // contador para mostrar listados los objetos
//		while (objectsJug.hasNext()) {
//			// Creo un objeto Jugadores y almaceno ahí el objeto que recupero de la BD
//			Jugadores jug = (Jugadores) objectsJug.next();
//			// Imprimo las propiedades que me interesan de ese objeto
//			System.out.println((i++) + " - " + "Nombre: " + jug.getNombre() + ", Deporte: " + jug.getDeporte()
//					+ ", Pais: " + jug.getPais().getNombrePais() + ", Edad: " + jug.getEdad());
//		}

		// Repetimos el prceso con los países, esta vez empleando while
		// Recuperamos todos los países
		Objects<Paises> objectsPaises = odb.getObjects(Paises.class);
		System.out.println("\nHay " + objectsPaises.size() + " Paises en la BD:");

		// Los mostramos por consola
		int j = 1; // contador para mostrar listados los objetos
		// visualizar los objetos
		while (objectsPaises.hasNext()) {
			// Creo un objeto Paises y almaceno ahí el objeto que recupero de la BD
			Paises pais = (Paises) objectsPaises.next();
			// Imprimo las propiedades que me interesan de ese objeto
			System.out.println((j++) + " - " + "ID: " + pais.getId() + ", Pais: " + pais.getNombrePais());
		}

		odb.close(); // Cerramos la base de datos
	}
}