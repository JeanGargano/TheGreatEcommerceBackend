package com.ecommerce.Service;

import com.ecommerce.Model.DisenioModel;
import com.ecommerce.Model.Dto.DisenioDTO;

import java.util.List;
import java.util.Optional;
public interface IDisenioService {

    String crearDisenio(DisenioModel diseno);

    List<DisenioModel> listarDisenio();
    Optional<DisenioDTO> obtenerDisenioPorId(Integer idDisenio);
    String actualizarDisenioPorId(DisenioModel disenio, Integer idDisenio);
}
