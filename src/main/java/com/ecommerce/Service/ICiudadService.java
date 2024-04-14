package com.ecommerce.Service;

import com.ecommerce.Model.CiudadModel;

import java.util.List;
import java.util.Optional;

public interface ICiudadService {

    String crearCiudad(CiudadModel ciudad);

    List<CiudadModel> listarCiudad();
    Optional<CiudadModel> obtenerCiudadPorId(Integer idCiudad);
    String actualizarCiudadPorId(CiudadModel ciudad, Integer idCiudad);
}
