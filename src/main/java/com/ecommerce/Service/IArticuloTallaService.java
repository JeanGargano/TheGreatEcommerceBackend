package com.ecommerce.Service;

import com.ecommerce.Model.ArticuloTallaModel;

import java.util.List;
import java.util.Optional;



public interface IArticuloTallaService {

    String crearArticuloTalla(ArticuloTallaModel articuloTalla);

    List<ArticuloTallaModel> listarArticuloTalla();
    Optional<ArticuloTallaModel> obtenerArticuloTallaPorId(Integer idArticuloTalla);
    String actualizarArticuloTallaPorId(ArticuloTallaModel articuloTalla, Integer idArticuloTalla);
}

