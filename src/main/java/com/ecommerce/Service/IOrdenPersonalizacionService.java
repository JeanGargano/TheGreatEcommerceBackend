package com.ecommerce.Service;


import com.ecommerce.Model.OrdenPersonalizacionModel;
import java.util.List;
import java.util.Optional;

public interface IOrdenPersonalizacionService {

    String crearOrdenPersonalizacion(OrdenPersonalizacionModel ordenPersonalizacion);

    List<OrdenPersonalizacionModel> listarOrdenPersonalizacion();
    Optional<OrdenPersonalizacionModel> obtenerOrdenPersonalizacionPorId(Integer idOrdenPersonalizacion);
    String actualizarOrdenPersonalizacionPorId(OrdenPersonalizacionModel ordenPersonalizacion, Integer idOrdenPersonalizacion);
}
