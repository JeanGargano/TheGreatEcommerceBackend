package com.ecommerce.Service;


import com.ecommerce.Model.ArticuloDTO.ArticuloModelDTO;
import com.ecommerce.Model.OrdenArticuloModel;

import java.util.List;
import java.util.Optional;


public interface IOrdenArticuloService {


    String crearOrdenArticulo(ArticuloModelDTO ordenArticulo);

    List<OrdenArticuloModel> listarOrdenArticulo();
    Optional<OrdenArticuloModel> obtenerOrdenArticuloPorId(Integer idOrdenArticulo);
    String actualizarOrdenArticulo(OrdenArticuloModel ordenArticulo, Integer idOrdenArticulo);
}
