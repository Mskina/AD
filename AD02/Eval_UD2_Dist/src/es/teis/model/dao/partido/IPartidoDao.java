/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.teis.model.dao.partido;

import es.teis.model.Partido;
import es.teis.model.dao.IGenericDao;

/**
 *
 * @author dominator
 */
public interface IPartidoDao extends IGenericDao<Partido>{
    
    
    public boolean partidoExists(String nombre);
    
    public boolean transferirVotos(String nombreOrigen, String nombreDestino, int cantidadVotos);
}
