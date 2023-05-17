/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Bean.java to edit this template
 */
package alumno.alumnobean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;



/**
 *
 * @author usuario
 */
public class AlumnoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6551701680984577918L;
	private PropertyChangeSupport propertySupport;

	/*****************************************************
	 * Propiedades del Bean. Crearemos una propiedad por cada campo de la tabla de
	 * la base de datos del siguiente modo:
	 *
	 * DNI: String Nombre: String Apellidos: String Direccion: String FechaNac: Date
	 */
	public AlumnoBean() {
		propertySupport = new PropertyChangeSupport(this);
		try {
			recargarFilas();
		} catch (ClassNotFoundException ex) {
			this.DNI = "";
			this.Nombre = "";
			this.Apellidos = "";
			this.Direccion = "";
		System.err.println("Ha ocurrido una exception: " + ex.getMessage());
		}
	}

	protected String DNI;

	/**
	 * Get the value of DNI
	 *
	 * @return the value of DNI
	 */
	public String getDNI() {
		return DNI;
	}

	/**
	 * Set the value of DNI
	 *
	 * @param DNI new value of DNI
	 */
	public void setDNI(String DNI) {
		this.DNI = DNI;
	}

	protected String Nombre;

	/**
	 * Get the value of Nombre
	 *
	 * @return the value of Nombre
	 */
	public String getNombre() {
		return Nombre;
	}

	/**
	 * Set the value of Nombre
	 *
	 * @param Nombre new value of Nombre
	 */
	public void setNombre(String Nombre) {
		this.Nombre = Nombre;
	}

	protected String Apellidos;

	/**
	 * Get the value of Apellidos
	 *
	 * @return the value of Apellidos
	 */
	public String getApellidos() {
		return Apellidos;
	}

	/**
	 * Set the value of Apellidos
	 *
	 * @param Apellidos new value of Apellidos
	 */
	public void setApellidos(String Apellidos) {
		this.Apellidos = Apellidos;
	}

	protected String Direccion;

	/**
	 * Get the value of Direccion
	 *
	 * @return the value of Direccion
	 */
	public String getDireccion() {
		return Direccion;
	}

	/**
	 * Set the value of Direccion
	 *
	 * @param Direccion new value of Direccion
	 */
	public void setDireccion(String Direccion) {
		this.Direccion = Direccion;
	}

	protected Date FechaNac;

	/**
	 * Get the value of FechaNac
	 *
	 * @return the value of FechaNac
	 */
	public Date getFechaNac() {
		return FechaNac;
	}

	/**
	 * Set the value of FechaNac
	 *
	 * @param FechaNac new value of FechaNac
	 */
	public void setFechaNac(Date FechaNac) {
		this.FechaNac = FechaNac;
	}

	/*******************************************************
	 * Definimos los métodos y atributos privados del componente que usaremos para
	 * darle funcionalidad.
	 *
	 */

	/*****************************************************
	 * Clase auxiliar que usaremos para crear un vector privado de alumnos.
	 */
	private class Alumno {
		String DNI;
		String Nombre;
		String Apellidos;
		String Direccion;
		Date FechaNac;

		public Alumno() {
		}

		public Alumno(String nDNI, String nNombre, String nApellidos, String nDireccion, Date nFechaNac) {
			this.DNI = nDNI;
			this.Nombre = nNombre;
			this.Apellidos = nApellidos;
			this.Direccion = nDireccion;
			this.FechaNac = nFechaNac;
		}

		@Override
		public String toString() {
			return "Alumno [DNI=" + DNI + ", Nombre=" + Nombre + ", Apellidos=" + Apellidos + ", Direccion=" + Direccion
					+ ", FechaNac=" + FechaNac + "]";
		}

	}

	/******************************************************
	 * Usaremos un vector auxiliar para cargar la información de la tabla de forma
	 * que tengamos acceso a los datos sin necesidad de estar conectados
	 * constantemente
	 */
	private Vector<Alumno> Alumnos = new Vector<Alumno>();

	/*******************************************************
	 * Actualiza el contenido de la tabla en el vector de alumnos Las propiedades
	 * contienen el valor del primer elementos de la tabla
	 */
	private void recargarFilas() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/alumnos", "root", "");
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("select * from alumnos");
			while (rs.next()) {
				Alumno a = new Alumno(rs.getString("DNI"), rs.getString("Nombre"), rs.getString("Apellidos"),
						rs.getString("Direccion"), rs.getDate("FechaNac"));

				Alumnos.add(a);

				System.out.println(a);
			}
			Alumno a = new Alumno();
			a = (Alumno) Alumnos.elementAt(1);
			this.DNI = a.DNI;
			this.Nombre = a.Nombre;
			this.Apellidos = a.Apellidos;
			this.Direccion = a.Direccion;
			this.FechaNac = a.FechaNac;
			rs.close();
			con.close();
		} catch (SQLException ex) {
			this.DNI = "";
			this.Nombre = "";
			this.Apellidos = "";
			this.Direccion = "";
			System.err.println("Ha ocurrido una exception: " + ex.getMessage());
		}
	}

	/********************************************************
	 *
	 * @param i numero de la fila a cargar en las propiedades del componente
	 */
	public void seleccionarFila(int i) {
		if (i <= Alumnos.size()) {
			Alumno a = new Alumno();
			a = (Alumno) Alumnos.elementAt(i);
			this.DNI = a.DNI;
			this.Nombre = a.Nombre;
			this.Apellidos = a.Apellidos;
			this.Direccion = a.Direccion;
			this.FechaNac = a.FechaNac;
		} else {
			this.DNI = "";
			this.Nombre = "";
			this.Apellidos = "";
			this.Direccion = "";
		}
	}

	/********************************************************
	 *
	 * @param nDNI DNI A buscar, se carga en las propiedades del componente
	 */
	public void seleccionarDNI(String nDNI) {
		Alumno a = new Alumno();
		int i = 0;

		this.DNI = "";
		this.Nombre = "";
		this.Apellidos = "";
		this.Direccion = "";
		while (this.DNI.equals("") && i <= Alumnos.size()) {
			a = (Alumno) Alumnos.elementAt(i);
			if (a.DNI.equals(nDNI)) {
				this.DNI = a.DNI;
				this.Nombre = a.Nombre;
				this.Apellidos = a.Apellidos;
				this.Direccion = a.Direccion;
				this.FechaNac = a.FechaNac;
			}
		}
	}

	/*********************************************************************
	 * Código para añadir un nuevo alumno a la base de datos. cada vez que se
	 * modifca el estado de la BD se genera un evento para que se recargue el
	 * componente.
	 */

	private BDModificadaListener receptor;





	public void addBDModificadaListener(BDModificadaListener receptor) {
		this.receptor = receptor;
	}

	public void removeBDModificadaListener(BDModificadaListener receptor) {
		this.receptor = null;
	}

	/*******************************************************
	 * Método que añade un alumno a la base de datos añade un registro a la base de
	 * datos formado a partir de los valores de las propiedades del componente.
	 *
	 * Se presupone que se han usado los métodos set para configurar adecuadamente
	 * las propiedades con los datos del nuevo registro.
	 */
	public void addAlumno() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/alumnos", "root", "");
			PreparedStatement s = con.prepareStatement("insert into alumnos values (?,?,?,?,?)");

			s.setString(1, DNI);
			s.setString(2, Nombre);
			s.setString(3, Apellidos);
			s.setString(4, Direccion);
			s.setDate(5, FechaNac);

			s.executeUpdate();
			recargarFilas();
			receptor.capturarBDModificada(new BDModificadaEvent(this));
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		catch(Exception e){
		e.printStackTrace();	
		}

	}

	/*******************************************************
	 *
	 * @param listener
	 */

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertySupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertySupport.removePropertyChangeListener(listener);
	}

	/* new */

	/********************************************************
	 *
	 * @param i numero de la fila a cargar en las propiedades del componente
	 */
	public boolean insert()

	{
		Connection con = null;

		boolean exito = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost/alumnos", "root", "");

			con.setAutoCommit(false);
			PreparedStatement stmt = con.prepareStatement(
					"insert into alumnos(DNI, Nombre, Apellidos, Direccion, FechaNac) values (?,?,?,?,?)");

			stmt.setString(1, DNI);
			stmt.setString(2, Nombre);
			stmt.setString(3, Apellidos);
			stmt.setString(4, Direccion);
			stmt.setDate(5, FechaNac);

			
			int rowCount = stmt.executeUpdate();
			con.commit();			
			
			
			
			recargarFilas();
			receptor.capturarBDModificada(new BDModificadaEvent(this));
			exito = (rowCount == 1);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {

			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch (SQLException excep) {
					excep.printStackTrace();
				}
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exito;

	}

}
