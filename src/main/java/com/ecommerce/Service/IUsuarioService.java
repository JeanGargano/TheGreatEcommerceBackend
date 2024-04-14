package com.ecommerce.Service;


import com.ecommerce.Model.UsuarioModel;

import java.util.List;
import java.util.Optional;
public interface IUsuarioService {

    String crearUsuario(UsuarioModel usuario);

    List<UsuarioModel> listarUsuario();
    Optional<UsuarioModel> obtenerUsuarioPorId(Integer idUsuario);
    String actualizarUsuarioPorId(UsuarioModel usuario, Integer idUsuario);
}
