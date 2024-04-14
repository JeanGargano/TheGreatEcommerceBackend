package com.ecommerce.Service;

import com.ecommerce.Model.CategoriaModel;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {

    String crearCategoria(CategoriaModel categoria);

    List<CategoriaModel> listarCategoria();
    Optional<CategoriaModel> obtenerCategoriaPorId(Integer idCategoria);
    String actualizarCategoriaporId(CategoriaModel categoria, Integer idCategoria);
}

