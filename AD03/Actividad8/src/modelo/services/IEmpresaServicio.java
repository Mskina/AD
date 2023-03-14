package modelo.services;

import java.util.List;

import modelo.Empresa;

public interface IEmpresaServicio {

	public boolean crearEmpresa(Empresa empresa);

	public List<Empresa> listarEmpresas();

}
