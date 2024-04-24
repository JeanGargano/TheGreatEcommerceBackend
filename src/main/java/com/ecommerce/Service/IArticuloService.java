package com.ecommerce.Service;

import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.OrdenArticuloModel;

import java.util.List;
import java.util.Optional;

public interface IArticuloService {

    String crearArticulo(ArticuloModel articulo);

    List<ArticuloModel> listarArticulo();

    Optional<ArticuloModel> obtenerArticuloPorId(Integer idArticulo);

    String eliminarArticuloPorId(Integer idArticulo);

    String actualizarArticuloPorId(ArticuloModel Articulo, Integer idArticulo);

    List<ArticuloModel> obtenerPorCategoria(String nombreCategoria);

    String actualizarCantidadEnBd(OrdenArticuloModel ordenArticulo, ArticuloModel articulo);
}



