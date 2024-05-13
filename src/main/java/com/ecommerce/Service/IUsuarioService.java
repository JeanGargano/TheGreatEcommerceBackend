package com.ecommerce.Service;


import com.ecommerce.Model.Dto.UsuarioModelDto;
import com.ecommerce.Model.Enums.TipoUsuario;
import com.ecommerce.Model.OrdenPersonalizacionModel;
import com.ecommerce.Model.UsuarioModel;

import java.util.List;
import java.util.Optional;
public interface IUsuarioService {

    String crearUsuario(UsuarioModel usuario);

    List<UsuarioModel> listarUsuario();
    Optional<UsuarioModel> obtenerUsuarioPorId(Integer idUsuario);
    String actualizarUsuarioPorId(UsuarioModel usuario, Integer idUsuario);

    Optional<UsuarioModel> verificarUsuario(UsuarioModelDto usuarioDto);

    List<String> listarDiseniadores();

    UsuarioModel asignarRol(Integer idUsuario, TipoUsuario rol, TipoUsuario rolUsuario);


    OrdenPersonalizacionModel asignarDiseniador(Integer idOrdenPersonalizacion, Integer idUsuario, TipoUsuario rolUsuario);


}
