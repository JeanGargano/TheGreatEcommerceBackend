package com.ecommerce.Service;

import com.ecommerce.Model.ComentarioModel;

import java.util.List;
import java.util.Optional;



public interface IComentarioService {

    String crearComentario(ComentarioModel comentario);

    List<ComentarioModel> listarComentario();
    Optional<ComentarioModel> obtenerComentarioPorId(Integer idComentario);
    String actualizarComentarioPorId(ComentarioModel comentario, Integer idComentario);
}

