package com.ecommerce.Service;

import com.ecommerce.Model.EnvioModel;

import java.util.List;
import java.util.Optional;
public interface IEnvioService {

    String crearEnvio(EnvioModel envio);

    List<EnvioModel> listarEnvio();
    Optional<EnvioModel> obtenerEnvioPorId(Integer idEnvio);
    String actualizarEnvioPorId(EnvioModel envio, Integer idEnvio);


}
