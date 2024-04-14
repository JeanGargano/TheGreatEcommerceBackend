package com.ecommerce.Service;


import com.ecommerce.Model.TelefonoUsuarioModel;
import java.util.List;
import java.util.Optional;

public interface ITelefonoUsuarioService {

    String crearTelefonoUsuario(TelefonoUsuarioModel telefonoUsuario);

    List<TelefonoUsuarioModel> listarTelefonoUsuario();
    Optional<TelefonoUsuarioModel> obtenerTelefonoUsuarioPorId(Integer idTelefonoUsuario);
    String actualizarTelefonoUsuarioPorId(TelefonoUsuarioModel telefonoUsuario, Integer idTelefonoUsuario);
}
