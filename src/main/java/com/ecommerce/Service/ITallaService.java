package com.ecommerce.Service;


import com.ecommerce.Model.TallaModel;
import java.util.List;
import java.util.Optional;

public interface ITallaService {

    String crearTalla(TallaModel talla);

    List<TallaModel> listarTalla();
    Optional<TallaModel> obtenerTallaPorId(Integer idTalla);
    String actualizarTallaPorId(TallaModel talla, Integer idTalla);
}
