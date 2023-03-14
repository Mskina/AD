package main;

import java.time.LocalDateTime;
import java.util.List;

import modelo.EmpleadoFijo;
import modelo.EmpleadoTemporal;
import modelo.Empresa;
import modelo.services.EmpleadoServicio;
import modelo.services.EmpresaServicio;
import modelo.services.IEmpleadoServicio;
import modelo.services.IEmpresaServicio;

public class Main {

	public static void main(String[] args) {

		IEmpresaServicio empresaServicio = new EmpresaServicio();
		IEmpleadoServicio empleadoServicio = new EmpleadoServicio();

		// Creo una empresa

		System.out.println("---- ESTOY CREANDO UNA EMPRESA ----");

		Empresa empresa1 = new Empresa();
		empresa1.setCif("12345678A");
		empresa1.setNombre("Empresa 1");
		empresa1.setDireccion("Calle de la piruleta");
		empresa1.setTelefono("987654321");
		empresaServicio.crearEmpresa(empresa1);

		System.out.println("---- << EMPRESA CREADA >> ----");
		System.out.println();
		System.out.println("---- ESTOY CREANDO UN EMPLEADO ----");

		EmpleadoTemporal et1 = new EmpleadoTemporal();
		et1.setDni("dni1");
		et1.setNombre("manuel");
		et1.setTelefono("988");
		et1.setFechaInicio(LocalDateTime.of(2023, 3, 1, 0, 0)); // Año, mes, día, hora, minuto
		et1.setFechaFin(LocalDateTime.of(2023, 3, 31, 23, 0));
		et1.setPagoDia(50);
		et1.setSuplemento(12);
		et1.setPorcentaRetencion(0.09f);
		et1.setEmpresa(empresa1);
		empleadoServicio.crearEmpleado(et1);

		EmpleadoFijo ef1 = new EmpleadoFijo();
		ef1.setDni("dni2");
		ef1.setNombre("Manoel");
		ef1.setTelefono("987");
		ef1.setPorcentaRetencion(0.08f);
		ef1.setEmpresa(empresa1);
		empleadoServicio.crearEmpleado(ef1);

		System.out.println("---- << EMPLEADO CREADO >> ----");

		System.out.println();

		List<Empresa> lista = empresaServicio.listarEmpresas();
		for (Empresa e : lista) {
			System.out.println(e);
		}

	}

}
