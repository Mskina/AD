package consultas;

import java.util.ArrayList;
import java.util.List;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.OID;
import org.neodatis.odb.ObjectValues;
import org.neodatis.odb.Objects;
import org.neodatis.odb.Values;
import org.neodatis.odb.core.oid.OIDFactory;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.IValuesQuery;
import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Or;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

import clases.Jugadores;

public class Consultas {

	private static ODB odb = null;

	public static void main(String[] args) {
		odb = ODBFactory.open("EQUIPOS.DB");

		Objects<Jugadores> objects = odb.getObjects(Jugadores.class);
		int i = 1;
		System.out.println("En la BD hay: ");
		while (objects.hasNext()) {
			Jugadores j = objects.next();
			System.out.println((i++) + ": " + j.toString());
		}

		// read(3);
		// filtrar();
		// consultaCompleja();
		consultaCompleja2();

//		List<String> campos = new ArrayList<String>();
//		campos.add("nombre");
//		campos.add("edad");
//		campos.add("pais.nombrePais");
//		values(campos, Jugadores.class);
//		
//		valuesAnd();

	}

	private static void read(long id) {
		// Ejemplo de leer (read) el objeto con OID 3
		OID oid = OIDFactory.buildObjectOID(id);
		Jugadores jug = (Jugadores) odb.getObjectFromId(oid);
		if (jug != null) {
			System.out.println("jugador recuperado con id: " + id + " " + jug);
		} else {
			System.out.println("No se ha encontrado el jugador con id: " + id);
		}
	}

	private static void filtrar() {
		// Ejemplo. Búsqueda de todos los objetos de la clase
		// Jugadores que de atributo ‘deporte’ tienen puesto ‘tenis’
		ICriterion criterio = Where.equal("deporte", "tenis");

		IQuery query = new CriteriaQuery(Jugadores.class, criterio);

		// ordenar
		query.orderByAsc("nombre,edad");

		Objects<Jugadores> objects = odb.getObjects(query);

		while (objects.hasNext()) {
			Jugadores jugador = objects.next();
			System.out.println("Resultado deporte tenis: " + jugador);
		}
	}

	private static void consultaCompleja() {

		ICriterion criterio = new And().add(Where.equal("pais.nombrePais", "EEUU"))
				.add(Where.equal("deporte", "tenis"));

		IQuery query = new CriteriaQuery(Jugadores.class, criterio);

		Objects<Jugadores> objects = odb.getObjects(query);

		while (objects.hasNext()) {
			Jugadores jugador = objects.next();
			System.out.println("Resultado EEUU y tenis: " + jugador);
		}
	}

	private static void consultaCompleja2() {
		// Ejemplo. Se quiere buscar objetos de la clase Jugadores
		// que tengan:
		// >=14 años
		// y (España O Italia O Francia)
		ICriterion criterio = new And().add(Where.ge("edad", 14))
				.add(new Or().add(Where.equal("pais.nombrePais", "España"))
						.add(Where.equal("pais.nombrePais", "Italia")).add(Where.equal("pais.nombrePais", "Francia")));

		IQuery query = new CriteriaQuery(Jugadores.class, criterio);
		Objects<Jugadores> objects = odb.getObjects(query);

		while (objects.hasNext()) {
			Jugadores jugador = objects.next();
			System.out.println("Resultado edad=14 y (España o Italia o Francia) " + jugador);
		}
	}

	private static void values(List<String> campos, Class clase) {

		ValuesCriteriaQuery valuesCriteriaQuery = new ValuesCriteriaQuery(clase);
		IValuesQuery ivc = null;
		for (String campo : campos) {
			if (ivc == null) {
				ivc = valuesCriteriaQuery.field(campo);
			} else {
				ivc = ivc.field(campo);
			}
		}

		Values values = odb.getValues(ivc);

		while (values.hasNext()) {
			ObjectValues objectValues = (ObjectValues) values.next();
			System.out.println("Values bruto: " + objectValues);
			System.out.printf("Nombre : %s, Edad : %s País: %s %n", objectValues.getByAlias("nombre"),
					objectValues.getByIndex(1), objectValues.getByIndex(2));
		}

	}

	private static void valuesAnd() {
		Values values = odb.getValues(new ValuesCriteriaQuery(Jugadores.class,
				new And().add(Where.equal("pais.nombrePais", "Italia")).add(Where.equal("edad", 15))).field("nombre")
		// .field("ciudad")
		);

		for (ObjectValues valor : values) {
			System.out.println("Resultado values + And:");
			System.out.println(valor.toString());
			System.out.println(valor.getByAlias("nombre"));
			System.out.println(valor.getByIndex(0));
		}
	}

}
