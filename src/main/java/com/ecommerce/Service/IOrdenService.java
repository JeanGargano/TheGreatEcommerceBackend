package com.ecommerce.Service;

import com.ecommerce.Model.Dto.OrdenModelDTO;
import com.ecommerce.Model.OrdenModel;

import java.util.List;
import java.util.Optional;

public interface IOrdenService {

    String crearOrden(OrdenModelDTO orden);

    List<OrdenModel> listarOrden();
    Optional<OrdenModel> obtenerOrdenPorId(Integer idOrden);
    String actualizarOrdenPorId(OrdenModel orden, Integer idOrden);

    Optional<String> listarInformacion(Integer idOrden);
}
